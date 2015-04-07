[
    new MagicWhenDiesTrigger() {
        @Override
        public MagicEvent executeTrigger(final MagicGame game, final MagicPermanent permanent, final MagicPermanent died) {
            return new MagicEvent(
                permanent,
                this,
                "Exile SN, then shuffle all creature cards from PN's graveyard into his or her library."
            );
        }
        @Override
        public void executeEvent(final MagicGame game, final MagicEvent event) {
            final MagicCard card = event.getPermanent().getCard();
            game.doAction(new MagicRemoveCardAction(card, MagicLocationType.Graveyard));
            game.doAction(new MagicMoveCardAction(card, MagicLocationType.Graveyard, MagicLocationType.Exile));
            game.filterCards(event.getPlayer(),MagicTargetFilterFactory.CREATURE_CARD_FROM_GRAVEYARD) each {
                game.doAction(new MagicRemoveCardAction(it, MagicLocationType.Graveyard));
                game.doAction(new MagicMoveCardAction(it, MagicLocationType.Graveyard, MagicLocationType.OwnersLibrary));
            }
        }
    }
]
