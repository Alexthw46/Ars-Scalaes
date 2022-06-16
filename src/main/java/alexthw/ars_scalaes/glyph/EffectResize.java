package alexthw.ars_scalaes.glyph;

import com.hollingsworth.arsnouveau.api.spell.*;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentAmplify;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentDampen;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import virtuoel.pehkui.api.ScaleData;
import virtuoel.pehkui.api.ScaleType;
import virtuoel.pehkui.api.ScaleTypes;

import java.util.Set;

public class EffectResize extends AbstractEffect {

    public static EffectResize INSTANCE = new EffectResize();

    public EffectResize() {
        super("resize", "Resize");
    }

    @Override
    public void onResolveEntity(EntityHitResult rayTraceResult, Level world, @Nullable LivingEntity shooter, SpellStats spellStats, SpellContext spellContext) {

        if (rayTraceResult.getEntity() instanceof LivingEntity living) {

            ScaleType type = ScaleTypes.BASE;
            final ScaleData data = type.getScaleData(living);

            data.setTargetScale((float) Math.max(0.2, Math.min(10, (1 + spellStats.getAmpMultiplier() / 5))));

        }

        super.onResolveEntity(rayTraceResult, world, shooter, spellStats, spellContext);
    }

    @Override
    public int getDefaultManaCost() {
        return 100;
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
}
