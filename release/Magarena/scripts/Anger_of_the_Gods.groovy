[
    new MagicSpellCardEvent() {
        @Override
        public MagicEvent getEvent(final MagicCardOnStack cardOnStack,final MagicPayedCost payedCost) {
            return new MagicEvent(
                cardOnStack,
                this,
                "SN deals 3 damage to each creature. " + 
                "If a creature dealt damage this way would die this turn, exile it instead."
            );
        }
        @Override
        public void executeEvent(final MagicGame game, final MagicEvent event) {
            final Collection<MagicPermanent> targets = game.filterPermanents(
                event.getPlayer(),
                MagicTargetFilterFactory.CREATURE
            );
            for (final MagicPermanent target : targets) {
                final MagicDamage damage=new MagicDamage(event.getSource(),target,3);
                game.doAction(new MagicDealDamageAction(damage));
                if (damage.getDealtAmount() > 0) {
                    game.doAction(new MagicAddTurnTriggerAction(
                        target, 
                        MagicWhenSelfLeavesPlayTrigger.IfDieExileInstead
                    ));
                }
            }
        }
    }
]
