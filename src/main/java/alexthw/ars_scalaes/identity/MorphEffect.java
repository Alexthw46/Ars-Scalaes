package alexthw.ars_scalaes.identity;

import draylar.identity.api.PlayerIdentity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;

public class MorphEffect extends MobEffect {

    protected MorphEffect() {
        super(MobEffectCategory.NEUTRAL, 0);
    }

    @Override
    public void removeAttributeModifiers(LivingEntity pLivingEntity, AttributeMap pAttributeMap, int pAmplifier) {
        if (pLivingEntity instanceof ServerPlayer player)
            PlayerIdentity.updateIdentity(player, null, null);
        super.removeAttributeModifiers(pLivingEntity, pAttributeMap, pAmplifier);
    }

}
