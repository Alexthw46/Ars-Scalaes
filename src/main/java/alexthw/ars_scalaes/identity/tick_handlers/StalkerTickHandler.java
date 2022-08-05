package alexthw.ars_scalaes.identity.tick_handlers;

import com.hollingsworth.arsnouveau.common.entity.WildenStalker;
import draylar.identity.api.IdentityTickHandler;
import net.minecraft.world.entity.player.Player;

public class StalkerTickHandler implements IdentityTickHandler<WildenStalker> {

    @Override
    public void tick(Player player, WildenStalker stalker) {
        if (player.level.isClientSide() && player.level.getGameTime() % 10 == 0) {
            if (player.isFallFlying() || player.isOnGround() || player.isInWater())
                stalker.setFlying(!player.isOnGround() && !player.isInWater());
        }
    }

}
