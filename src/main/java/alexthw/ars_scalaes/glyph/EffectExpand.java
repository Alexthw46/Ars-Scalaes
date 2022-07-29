package alexthw.ars_scalaes.glyph;

import com.hollingsworth.arsnouveau.api.spell.SpellContext;
import com.hollingsworth.arsnouveau.api.spell.SpellResolver;
import com.hollingsworth.arsnouveau.api.spell.SpellStats;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.common.ForgeConfigSpec;
import org.jetbrains.annotations.Nullable;
import virtuoel.pehkui.api.ScaleData;
import virtuoel.pehkui.api.ScaleTypes;

public class EffectExpand extends PehkuiEffect {

    public static EffectExpand INSTANCE = new EffectExpand();

    public EffectExpand() {
        super("expand", "Expand");
    }

    @Override
    public void onResolveEntity(EntityHitResult rayTraceResult, Level world, @Nullable LivingEntity shooter, SpellStats spellStats, SpellContext spellContext, SpellResolver resolver) {

        ScaleData data = ScaleTypes.BASE.getScaleData(rayTraceResult.getEntity());

        data.setTargetScale((float) Math.min(maxScaling.get(), (data.getScale() * (1.2 + spellStats.getAmpMultiplier()))));

    }

    @Override
    public void buildConfig(ForgeConfigSpec.Builder builder) {
        super.buildConfig(builder);
        maxScaling = builder.comment("Define the maximum size that can be reached.").defineInRange("max_scaling", 15D, 1.1D, 30D);
    }

}
