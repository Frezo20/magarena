package magic.card;

import magic.model.MagicGame;
import magic.model.MagicPermanent;
import magic.model.MagicPlayer;
import magic.model.action.MagicTapAction;
import magic.model.event.MagicEvent;
import magic.model.trigger.MagicAtUpkeepTrigger;

public class Narcolepsy {
    public static final MagicAtUpkeepTrigger T = new MagicAtUpkeepTrigger() {
        @Override
        public MagicEvent executeTrigger(final MagicGame game,final MagicPermanent permanent,final MagicPlayer upkeepPlayer) {
            final MagicPermanent enchantedCreature=permanent.getEnchantedCreature();
            return (enchantedCreature.isValid() && enchantedCreature.isUntapped()) ?
                new MagicEvent(
                    permanent,
                    this,
                    "If "+enchantedCreature+" is untapped, tap it."
                ):
                MagicEvent.NONE;
        }
        @Override
        public void executeEvent(
                final MagicGame game,
                final MagicEvent event,
                final Object[] choiceResults) {
            final MagicPermanent permanent=event.getPermanent();
            final MagicPermanent enchantedCreature=permanent.getEnchantedCreature();
            if (enchantedCreature.isValid()) {
                game.doAction(new MagicTapAction(enchantedCreature,true));
            }
        }        
    };
}
