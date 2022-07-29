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
        return augmentSetOf(AugmentAmplify.INSTANCE);
    }

}
