package alexthw.ars_scalaes.glyph;

import com.hollingsworth.arsnouveau.api.spell.SpellContext;
import com.hollingsworth.arsnouveau.api.spell.SpellResolver;
import com.hollingsworth.arsnouveau.api.spell.SpellStats;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.common.ForgeConfigSpec;
import virtuoel.pehkui.api.ScaleData;
import virtuoel.pehkui.api.ScaleTypes;

public class EffectShrink extends PehkuiEffect {

    public static EffectShrink INSTANCE = new EffectShrink();

    public EffectShrink() {
        super("shrink", "Shrink");
    }

    @Override
    public void onResolveEntity(EntityHitResult rayTraceResult, Level world, LivingEntity shooter, SpellStats spellStats, SpellContext spellContext, SpellResolver resolver) {

        ScaleData data = ScaleTypes.BASE.getScaleData(rayTraceResult.getEntity());

        data.setTargetScale((float) Math.max(minScaling.get(), data.getScale() / (1.2 + spellStats.getAmpMultiplier())));
        super.onResolveEntity(rayTraceResult, world, shooter, spellStats, spellContext, resolver);

    }

    @Override
    public void buildConfig(ForgeConfigSpec.Builder builder) {
        super.buildConfig(builder);
        minScaling = builder.comment("Define the minimum size that can be reached.").defineInRange("min_scaling", 0.2D, 0.01D, 1D);
    }
}
