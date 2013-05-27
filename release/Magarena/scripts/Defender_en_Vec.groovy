[
    new MagicPermanentActivation(
        [MagicConditionFactory.ChargeCountersAtLeast(1)],
        new MagicActivationHints(MagicTiming.Pump),
        "Prevent"
    ) {

        @Override
        public MagicEvent[] getCostEvent(final MagicPermanent source) {
            return [
                new MagicRemoveCounterEvent(source,MagicCounterType.Charge,1)
            ];
        }

        @Override
        public MagicEvent getPermanentEvent(final MagicPermanent source,final MagicPayedCost payedCost) {
            return new MagicEvent(
                source,
                MagicTargetChoice.POS_TARGET_CREATURE_OR_PLAYER,
                MagicPreventTargetPicker.getInstance(),
                this,
                "Prevent the next 2 damage that would be dealt " +
                "to target creature or player\$ this turn."
            );
        }

        @Override
        public void executeEvent(final MagicGame game, final MagicEvent event) {
            event.processTarget(game,new MagicTargetAction() {
                public void doAction(final MagicTarget target) {
                    game.doAction(new MagicPreventDamageAction(target,2));
                }
            });
        }
    }
]
