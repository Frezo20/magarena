package magic.model.action;

import magic.ai.ArtificialScoringSystem;
import magic.model.MagicAbility;
import magic.model.MagicGame;
import magic.model.MagicPermanent;
import magic.model.MagicPlayer;
import magic.model.MagicType;
import magic.model.event.MagicSoulbondEvent;
import magic.model.target.MagicTargetFilter;
import magic.model.trigger.MagicTrigger;
import magic.model.trigger.MagicTriggerType;

public abstract class MagicPutIntoPlayAction extends MagicAction {

    private MagicPermanent permanent = MagicPermanent.NONE;
    private MagicPermanent enchantedPermanent = MagicPermanent.NONE;

    @Override
    public void doAction(final MagicGame game) {
        permanent=createPermanent(game);
        permanent.getFirstController().addPermanent(permanent);
        game.update();

        final int score=ArtificialScoringSystem.getTurnScore(game)-permanent.getStaticScore();
                
        if (enchantedPermanent.isValid()) {
            enchantedPermanent.addAura(permanent);
            permanent.setEnchantedCreature(enchantedPermanent);            
        }

        game.addTriggers(permanent);
        game.addStatics(permanent);
    
        final MagicPlayer controller = permanent.getController();

        //execute come into play triggers
        for (final MagicTrigger<MagicPlayer> trigger : permanent.getCardDefinition().getComeIntoPlayTriggers()) {
            game.executeTrigger(trigger,permanent,permanent,controller);
        }

        //execute other come into player triggers
        game.executeTrigger(MagicTriggerType.WhenOtherComesIntoPlay,permanent);
        
        // Soulbond
        if (permanent.isCreature() && 
            controller.getNrOfPermanentsWithType(MagicType.Creature) > 1) {
            final boolean hasSoulbond = permanent.hasAbility(MagicAbility.Soulbond);
            if ((hasSoulbond &&
                game.filterPermanents(controller,MagicTargetFilter.TARGET_UNPAIRED_CREATURE_YOU_CONTROL).size() > 1)
                ||
                (!hasSoulbond &&
                game.filterPermanents(controller,MagicTargetFilter.TARGET_UNPAIRED_SOULBOND_CREATURE).size() > 0)) {
                game.addEvent(new MagicSoulbondEvent(permanent,hasSoulbond));
            }
        }
        
        setScore(controller,permanent.getScore()+permanent.getStaticScore()+score);
        
        game.checkLegendRule(permanent);
        game.setStateCheckRequired();
    }

    @Override
    public void undoAction(final MagicGame game) {
        if (enchantedPermanent.isValid()) {            
            enchantedPermanent.removeAura(permanent);
            permanent.setEnchantedCreature(MagicPermanent.NONE);
        }
        permanent.getFirstController().removePermanent(permanent);
        game.removeTriggers(permanent);
        game.removeStatics(permanent);
    }
    
    void setEnchantedPermanent(final MagicPermanent aEnchantedPermanent) {
        enchantedPermanent = aEnchantedPermanent;
    }
    
    protected abstract MagicPermanent createPermanent(final MagicGame game);
    
    public MagicPermanent getPermanent() {
        return permanent;
    }
    
    @Override
    public String toString() {
        if (enchantedPermanent.isValid()) {
            return getClass().getSimpleName()+" ("+permanent+','+enchantedPermanent+')';
        } else { 
            return getClass().getSimpleName()+" ("+permanent+')';
        }
    }
}
