[
    new MagicWhenSelfTurnedFaceUpTrigger() {
        @Override
        public MagicEvent executeTrigger(final MagicGame game,final MagicPermanent permanent,final MagicPermanent otherPermanent) {
            return new MagicEvent(
                permanent,
                this,
                "Return all other tapped creatures to their owners' hands."
            )
        }

        @Override
        public void executeEvent(final MagicGame game, final MagicEvent event) {
            final Collection<MagicPermanent> tapped=
                game.filterPermanents(event.getPlayer(),MagicTargetFilterFactory.TAPPED_CREATURE);
            for (final MagicPermanent creature : tapped) {
                game.doAction(new MagicRemoveFromPlayAction(creature,MagicLocationType.OwnersHand));
            }
        }
    }
]
