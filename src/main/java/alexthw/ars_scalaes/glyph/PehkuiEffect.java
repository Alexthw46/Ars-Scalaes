package alexthw.ars_scalaes.glyph;

import alexthw.ars_scalaes.pehkui.PkCompatHandler;
import com.hollingsworth.arsnouveau.api.spell.*;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.common.ForgeConfigSpec;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

import static alexthw.ars_scalaes.datagen.ArsProviders.prefix;

public class PehkuiEffect extends AbstractEffect implements IPotionEffect {
    public ForgeConfigSpec.BooleanValue isTimeLimited;
    public ForgeConfigSpec.DoubleValue minScaling;
    public ForgeConfigSpec.DoubleValue maxScaling;
    public ForgeConfigSpec.DoubleValue scalingFactor;

    public PehkuiEffect(String tag, String description) {
        super(prefix("glyph_" + tag), description);
    }

    @Override
    public void onResolveEntity(EntityHitResult rayTraceResult, Level world, @NotNull LivingEntity shooter, SpellStats spellStats, SpellContext spellContext, SpellResolver resolver) {
        if (isTimeLimited.get() && rayTraceResult.getEntity() instanceof LivingEntity living && (living != shooter || spellStats.getDurationMultiplier() < 1)) {
            ((IPotionEffect) this).applyConfigPotion(living, PkCompatHandler.RESIZE.get(), spellStats, false);
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

    @Override
    public int getBaseDuration() {
        return POTION_TIME == null ? 30 : POTION_TIME.get();
    }

    @Override
    public int getExtendTimeDuration() {
        return EXTEND_TIME == null ? 8 : EXTEND_TIME.get();
    }
}
