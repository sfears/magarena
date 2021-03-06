[
    new ThisCombatDamagePlayerTrigger() {
        @Override
        public MagicEvent executeTrigger(final MagicGame game,final MagicPermanent permanent,final MagicDamage damage) {
            return new MagicEvent(
                permanent,
                this,
                "PN reveals the top three cards of his or her library. " +
                "Put all land cards revealed this way into PN's hand and the rest into PN's graveyard."
            );
        }
        @Override
        public void executeEvent(final MagicGame game, final MagicEvent event) {
            final MagicCardList top3 = event.getPlayer().getLibrary().getCardsFromTop(3) ;
            game.doAction(new RevealAction(top3));
            for (final MagicCard top : top3) {
                game.doAction(new ShiftCardAction(
                    top,
                    MagicLocationType.OwnersLibrary,
                    top.hasType(MagicType.Land) ?
                        MagicLocationType.OwnersHand :
                        MagicLocationType.Graveyard
                ));
            }
        }
    }
]
