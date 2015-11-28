package magic.model.trigger;

import magic.model.MagicGame;
import magic.model.MagicPermanent;
import magic.model.event.MagicEvent;
import magic.model.event.MagicSourceEvent;
import magic.model.target.MagicTargetFilter;

public abstract class AttacksTrigger extends MagicTrigger<MagicPermanent> {
    public AttacksTrigger(final int priority) {
        super(priority);
    }

    public AttacksTrigger() {}

    public MagicTriggerType getType() {
        return MagicTriggerType.WhenAttacks;
    }

    public static AttacksTrigger create(final MagicTargetFilter<MagicPermanent> filter, final MagicSourceEvent sourceEvent) {
        return new AttacksTrigger() {
            public boolean accept(final MagicPermanent permanent, final MagicPermanent attacker) {
                return filter.accept(permanent, permanent.getController(), attacker);
            }
            @Override
            public MagicEvent executeTrigger(final MagicGame game,final MagicPermanent permanent, final MagicPermanent attacker) {
                return sourceEvent.getEvent(permanent, attacker);
            }
        };
    }

    public static AttacksTrigger createYou(final MagicTargetFilter<MagicPermanent> filter, final MagicSourceEvent sourceEvent) {
        return new AttacksTrigger() {
            public boolean accept(final MagicPermanent permanent, final MagicPermanent attacker) {
                return filter.accept(permanent, permanent.getController(), attacker) && permanent.isEnemy(attacker);
            }
            @Override
            public MagicEvent executeTrigger(final MagicGame game,final MagicPermanent permanent, final MagicPermanent attacker) {
                return sourceEvent.getEvent(permanent, attacker);
            }
        };
    }
}