package alexthw.ars_scalaes.glyph;

import com.hollingsworth.arsnouveau.api.spell.*;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentAmplify;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentDampen;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.common.ForgeConfigSpec;
import org.jetbrains.annotations.NotNull;
import virtuoel.pehkui.api.ScaleData;
import virtuoel.pehkui.api.ScaleType;
import virtuoel.pehkui.api.ScaleTypes;

import java.util.Set;

public class EffectResize extends PehkuiEffect {

    public static EffectResize INSTANCE = new EffectResize();

    public EffectResize() {
        super("resize", "Resize");
    }

    @Override
    public void onResolveEntity(EntityHitResult rayTraceResult, Level world, LivingEntity shooter, SpellStats spellStats, SpellContext spellContext, SpellResolver resolver) {

        if (rayTraceResult.getEntity() instanceof LivingEntity living) {

            ScaleType type = ScaleTypes.BASE;
            final ScaleData data = type.getScaleData(living);

            data.setTargetScale((float) Math.max(minScaling.get(), Math.min(maxScaling.get(), (
                    1 + spellStats.getAmpMultiplier() /
                            (spellStats.getAmpMultiplier() < 0 ? 2 * scalingFactor.get() : scalingFactor.get())
            ))));

        }

    }

    @Override
    public SpellTier getTier() {
        return SpellTier.TWO;
    }

    @NotNull
    @Override
    public Set<AbstractAugment> getCompatibleAugments() {
        return augmentSetOf(AugmentAmplify.INSTANCE, AugmentDampen.INSTANCE);
    }

    @Override
    public void buildConfig(ForgeConfigSpec.Builder builder) {
        super.buildConfig(builder);
        minScaling = builder.comment("Define the minimum size that can be reached.").defineInRange("min_scaling", 0.5D, 0.01D, 1D);
        maxScaling = builder.comment("Define the maximum size that can be reached.").defineInRange("max_scaling", 7D, 1.1D, 30D);
        scalingFactor = builder.comment("Define the factor of the amplification -> size conversion. The value is doubled for shrinking.").defineInRange("scaling_factor", 5D, 1D, 10D);
    }

}
