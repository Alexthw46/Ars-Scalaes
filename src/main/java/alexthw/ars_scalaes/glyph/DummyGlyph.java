package alexthw.ars_scalaes.glyph;

import com.hollingsworth.arsnouveau.api.spell.AbstractAugment;
import com.hollingsworth.arsnouveau.api.spell.AbstractEffect;
import com.hollingsworth.arsnouveau.api.spell.SpellTier;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

import static alexthw.ars_scalaes.datagen.ArsProviders.prefix;

public class DummyGlyph extends AbstractEffect {

    public DummyGlyph(String tag) {
        super(prefix("glyph_" + tag), "");
    }

    @Override
    public int getDefaultManaCost() {
        return 0;
    }

    @NotNull
    @Override
    protected Set<AbstractAugment> getCompatibleAugments() {
        return setOf();
    }

    @Override
    public SpellTier getTier() {
        return SpellTier.CREATIVE;
    }

}
