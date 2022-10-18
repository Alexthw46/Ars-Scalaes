package alexthw.ars_scalaes.glyph;

import alexthw.ars_scalaes.pehkui.PkCompatHandler;
import com.hollingsworth.arsnouveau.api.spell.SpellContext;
import com.hollingsworth.arsnouveau.api.spell.SpellResolver;
import com.hollingsworth.arsnouveau.api.spell.SpellStats;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.common.ForgeConfigSpec;
import virtuoel.pehkui.api.ScaleData;

public class EffectShrink extends PehkuiEffect {

    public static final EffectShrink INSTANCE = new EffectShrink();

    public EffectShrink() {
        super("shrink", "Shrink");
    }

    @Override
    public void onResolveEntity(EntityHitResult rayTraceResult, Level world, LivingEntity shooter, SpellStats spellStats, SpellContext spellContext, SpellResolver resolver) {

        ScaleData data = PkCompatHandler.magicScale.getScaleData(rayTraceResult.getEntity());
        float cScale = data.getScale();
        super.onResolveEntity(rayTraceResult, world, shooter, spellStats, spellContext, resolver);

        data.setTargetScale((float) Math.max(minScaling.get(), cScale / (1.2 + spellStats.getAmpMultiplier())));

    }

    @Override
    public void buildConfig(ForgeConfigSpec.Builder builder) {
        super.buildConfig(builder);
        minScaling = builder.comment("Define the minimum size that can be reached.").defineInRange("min_scaling", 0.2D, 0.01D, 1D);
    }
}
