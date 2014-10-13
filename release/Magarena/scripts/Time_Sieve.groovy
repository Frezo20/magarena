def FIVE_ARTIFACTS_CONDITION = new MagicCondition() {
    public boolean accept(final MagicSource source) {
             source.getController().getNrOfPermanents(MagicType.Artifact) >= 5;
    }
};

[
    new MagicPermanentActivation(
        [
            FIVE_ARTIFACTS_CONDITION,
        ],
        new MagicActivationHints(MagicTiming.Main),
        "Turn"
    ) {
        @Override
        public Iterable<MagicEvent> getCostEvent(final MagicPermanent source) {
            return [
                new MagicTapEvent(source), 
                new MagicSacrificePermanentEvent(source,MagicTargetChoice.SACRIFICE_ARTIFACT),
                new MagicSacrificePermanentEvent(source,MagicTargetChoice.SACRIFICE_ARTIFACT),   
                new MagicSacrificePermanentEvent(source,MagicTargetChoice.SACRIFICE_ARTIFACT),
                new MagicSacrificePermanentEvent(source,MagicTargetChoice.SACRIFICE_ARTIFACT),
                new MagicSacrificePermanentEvent(source,MagicTargetChoice.SACRIFICE_ARTIFACT)
            ];
        }
        @Override
        public MagicEvent getPermanentEvent(final MagicPermanent source,final MagicPayedCost payedCost) {
            return new MagicEvent(
                source,
                this,
                "PN takes an extra turn after this one."
            );
        }
        @Override
        public void executeEvent(final MagicGame game, final MagicEvent event) {
            game.doAction(new MagicChangeExtraTurnsAction(event.getPlayer(),1));
        }
    }
]
