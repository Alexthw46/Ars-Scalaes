package alexthw.ars_scalaes.glyph;

import com.hollingsworth.arsnouveau.api.spell.SpellContext;
import com.hollingsworth.arsnouveau.api.spell.SpellResolver;
import com.hollingsworth.arsnouveau.api.spell.SpellStats;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.Nullable;
import virtuoel.pehkui.api.ScaleData;
import virtuoel.pehkui.api.ScaleTypes;

import static alexthw.ars_scalaes.ConfigHandler.Common.MAX_SHRINKING;

public class EffectShrink extends PehkuiEffect {

    public static EffectShrink INSTANCE = new EffectShrink();

    public EffectShrink() {
        super("shrink", "Shrink");
    }

    @Override
    public void onResolveEntity(EntityHitResult rayTraceResult, Level world, @Nullable LivingEntity shooter, SpellStats spellStats, SpellContext spellContext, SpellResolver resolver) {

        ScaleData data = ScaleTypes.BASE.getScaleData(rayTraceResult.getEntity());

        data.setTargetScale((float) Math.max(MAX_SHRINKING.get(), data.getScale() / (1.2 + spellStats.getAmpMultiplier())));

    }

}
