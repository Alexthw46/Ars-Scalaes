package alexthw.ars_scalaes.pehkui;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import virtuoel.pehkui.api.ScaleData;

public class ResizeEffect extends MobEffect {
    protected ResizeEffect() {
        super(MobEffectCategory.NEUTRAL, 0);
    }

    @Override
    public void removeAttributeModifiers(LivingEntity pLivingEntity, AttributeMap pAttributeMap, int pAmplifier) {
        final ScaleData data = PkCompatHandler.magicScale.getScaleData(pLivingEntity);
        data.resetScale();
        super.removeAttributeModifiers(pLivingEntity, pAttributeMap, pAmplifier);
    }

}
