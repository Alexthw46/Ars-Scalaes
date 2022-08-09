package alexthw.ars_scalaes.glyph;

import com.hollingsworth.arsnouveau.api.spell.*;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentAmplify;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;

import net.minecraftforge.common.ForgeConfigSpec;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class PehkuiEffect extends AbstractEffect {

    public ForgeConfigSpec.BooleanValue isTimeLimited;
    public ForgeConfigSpec.DoubleValue minScaling;
    public ForgeConfigSpec.DoubleValue maxScaling;
    public ForgeConfigSpec.DoubleValue scalingFactor;

    public PehkuiEffect(String tag, String description) {
        super(tag, description);
    }

    @Override
    public boolean isRenderAsIcon() {
        return false;
    }

    @Override
    public boolean wouldSucceed(HitResult rayTraceResult, Level world, LivingEntity shooter, SpellStats spellStats, SpellContext spellContext) {
        return rayTraceResult.getType() == HitResult.Type.ENTITY;
    }

    @Override
    public void onResolveEntity(EntityHitResult rayTraceResult, Level world, @NotNull LivingEntity shooter, SpellStats spellStats, SpellContext spellContext, SpellResolver resolver) {
        if (isTimeLimited.get() && rayTraceResult.getEntity() instanceof LivingEntity living && (living != shooter || spellStats.getDurationMultiplier() < 1)) {
            applyConfigPotion(living, PkCompatHandler.RESIZE.get(), spellStats, false);
        }
    }

    @Override
    public void buildConfig(ForgeConfigSpec.Builder builder) {
        super.buildConfig(builder);
        isTimeLimited = builder.comment("Enable a timer on the resize effects. Target will return to original size when potion effect is removed.").define("limitedSizeTime", true);
        addPotionConfig(builder, 120);
        addExtendTimeConfig(builder, 60);
    }

    @Override
    public int getDefaultManaCost() {
        return 100;
    }

    @Override
    public SpellTier getTier() {
        return SpellTier.THREE;
    }

    @NotNull
    @Override
    public Set<AbstractAugment> getCompatibleAugments() {
        return getPotionAugments();
    }

}
