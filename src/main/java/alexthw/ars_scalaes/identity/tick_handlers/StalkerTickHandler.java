package alexthw.ars_scalaes.identity.tick_handlers;

import com.hollingsworth.arsnouveau.common.entity.WildenStalker;
import draylar.identity.api.IdentityTickHandler;
import net.minecraft.world.entity.player.Player;

public class StalkerTickHandler implements IdentityTickHandler<WildenStalker> {

    @Override
    public void tick(Player player, WildenStalker stalker) {
        if (player.level.isClientSide()) {
            if (stalker.isFlying() && player.isOnGround() || !player.isOnGround() && !stalker.isFlying())
                stalker.setFlying(player.isOnGround());
        } else {
        }
    }

}
