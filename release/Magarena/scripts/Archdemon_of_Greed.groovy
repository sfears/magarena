[
    new MagicAtUpkeepTrigger() {
        @Override
        public MagicEvent executeTrigger(final MagicGame game,final MagicPermanent permanent,final MagicPlayer upkeepPlayer) {
            return permanent.isController(upkeepPlayer) ?
                new MagicEvent(
                    permanent,
                    this,
                    "PN sacrifices a human. If you can't, tap SN and it deals 9 damage to you."
                ):
                MagicEvent.NONE;
        }

        @Override
        public void executeEvent(final MagicGame game, final MagicEvent event) {
            final MagicPermanent permanent=event.getPermanent();
            final MagicPlayer player=event.getPlayer();
            final MagicEvent sac = new MagicSacrificePermanentEvent(
                permanent,
                player,
                new MagicTargetChoice("a Human to sacrifice")
            );
            if (sac.hasOptions(game)) {
                game.addEvent(sac);
            } else {
                game.doAction(new MagicTapAction(permanent));
                game.doAction(new MagicDealDamageAction(
                    new MagicDamage(permanent, player, 9)
                ));
            }
        }
    }
]
