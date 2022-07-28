package alexthw.ars_scalaes.glyph;

import com.hollingsworth.arsnouveau.api.spell.AbstractAugment;
import com.hollingsworth.arsnouveau.api.spell.AbstractEffect;
import com.hollingsworth.arsnouveau.api.spell.SpellTier;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentAmplify;
import net.minecraftforge.common.ForgeConfigSpec;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

import static alexthw.ars_scalaes.datagen.ArsProviders.prefix;

public class PehkuiEffect extends AbstractEffect {

    public ForgeConfigSpec.DoubleValue minScaling;
    public ForgeConfigSpec.DoubleValue maxScaling;
    public ForgeConfigSpec.DoubleValue scalingFactor;

    public PehkuiEffect(String tag, String description) {
        super(prefix("glyph_" + tag), description);
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
