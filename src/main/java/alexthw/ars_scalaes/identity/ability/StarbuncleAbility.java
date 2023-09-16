package alexthw.ars_scalaes.identity.ability;

import alexthw.ars_scalaes.ConfigHandler;
import com.hollingsworth.arsnouveau.common.entity.Starbuncle;
import com.hollingsworth.arsnouveau.setup.registry.ItemsRegistry;
import draylar.identity.ability.IdentityAbility;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public class StarbuncleAbility<S extends Starbuncle> extends IdentityAbility<S> {
    @Override
    public void onUse(Player player, S s, Level level) {
        player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 3000, 1 ,false,false,false));
    }

    @Override
    public int getCooldown(S entity) {
        return ConfigHandler.Common.STARBY_COOLDOWN.get();
    }

    @Override
    public Item getIcon() {
        return ItemsRegistry.STARBUNCLE_CHARM.asItem();
    }
}
