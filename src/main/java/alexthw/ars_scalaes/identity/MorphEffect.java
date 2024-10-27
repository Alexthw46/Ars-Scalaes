package alexthw.ars_scalaes.identity;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import org.jetbrains.annotations.NotNull;

public class MorphEffect extends MobEffect {

    protected MorphEffect() {
        super(MobEffectCategory.NEUTRAL, 0);
    }

    @Override
    public void removeAttributeModifiers(@NotNull LivingEntity pLivingEntity, @NotNull AttributeMap pAttributeMap, int pAmplifier) {
        if (pLivingEntity instanceof ServerPlayer player)
            MorphingAbstraction.morphInto(player.level(), player,null);
        super.removeAttributeModifiers(pLivingEntity, pAttributeMap, pAmplifier);
    }

}
