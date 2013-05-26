[
    new MagicPermanentActivation(
        [
            MagicCondition.CAN_TAP_CONDITION,
            MagicConditionFactory.ChargeCountersAtLeast(1)
        ],
        new MagicActivationHints(MagicTiming.Draw),
        "Draw"
    ) {

        @Override
        public MagicEvent[] getCostEvent(final MagicPermanent source) {
            return [new MagicTapEvent(source)];
        }

        @Override
        public MagicEvent getPermanentEvent(final MagicPermanent source, final MagicPayedCost payedCost) {
            return new MagicEvent(
                source,
                this,
                "Each player draws a card for each charge counter on SN."
            );
        }

        @Override
        public void executeEvent(final MagicGame game, final MagicEvent event) {
            final MagicPermanent source = event.getPermanent();
            final int amount = source.getCounters(MagicCounterType.Charge);
            for (final MagicPlayer player : game.getPlayers()) {
                game.doAction(new MagicDrawAction(player,amount));
            }
        }
    }
]
