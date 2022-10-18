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

public class EffectExpand extends PehkuiEffect {

    public static final EffectExpand INSTANCE = new EffectExpand();

    public EffectExpand() {
        super("expand", "Expand");
    }

    @Override
    public void onResolveEntity(EntityHitResult rayTraceResult, Level world, LivingEntity shooter, SpellStats spellStats, SpellContext spellContext, SpellResolver resolver) {

        ScaleData data = PkCompatHandler.magicScale.getScaleData(rayTraceResult.getEntity());
        float cScale = data.getScale();

        super.onResolveEntity(rayTraceResult, world, shooter, spellStats, spellContext, resolver);

        data.setTargetScale((float) Math.min(maxScaling.get(), (cScale * (1.2 + spellStats.getAmpMultiplier()))));

    }

    @Override
    public void buildConfig(ForgeConfigSpec.Builder builder) {
        super.buildConfig(builder);
        maxScaling = builder.comment("Define the maximum size that can be reached.").defineInRange("max_scaling", 15D, 1.1D, 30D);
    }

}
