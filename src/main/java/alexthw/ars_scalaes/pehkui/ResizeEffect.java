package alexthw.ars_scalaes.pehkui;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import org.jetbrains.annotations.NotNull;
import virtuoel.pehkui.api.ScaleData;
import virtuoel.pehkui.api.ScaleType;

public class ResizeEffect extends MobEffect {
    protected ResizeEffect() {
        super(MobEffectCategory.NEUTRAL, 0);
    }

    @Override
    public void removeAttributeModifiers(@NotNull LivingEntity pLivingEntity, @NotNull AttributeMap pAttributeMap, int pAmplifier) {
        ScaleType type = PkCompatHandler.magicScale;
        final ScaleData data = type.getScaleData(pLivingEntity);
        data.resetScale();
        super.removeAttributeModifiers(pLivingEntity, pAttributeMap, pAmplifier);
    }

}
