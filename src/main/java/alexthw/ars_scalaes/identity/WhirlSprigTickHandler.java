package alexthw.ars_scalaes.identity;

import com.hollingsworth.arsnouveau.common.entity.Whirlisprig;
import draylar.identity.api.IdentityTickHandler;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;

public class WhirlSprigTickHandler implements IdentityTickHandler<Whirlisprig> {
    @Override
    public void tick(Player player, Whirlisprig whirlisprig) {
        if (player.level.isClientSide()) {

        } else {
            player.addEffect(new MobEffectInstance(MobEffects.JUMP, 40, 2, true, false));
        }
    }
}
