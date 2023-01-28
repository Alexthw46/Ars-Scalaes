package alexthw.ars_scalaes;

import alexthw.ars_scalaes.glyph.EffectExpand;
import alexthw.ars_scalaes.glyph.EffectMorph;
import alexthw.ars_scalaes.glyph.EffectResize;
import alexthw.ars_scalaes.glyph.EffectShrink;
import com.hollingsworth.arsnouveau.api.ArsNouveauAPI;
import com.hollingsworth.arsnouveau.api.spell.AbstractSpellPart;
import net.minecraftforge.fml.ModList;

import java.util.ArrayList;
import java.util.List;

public class ArsNouveauRegistry {

    public static final List<AbstractSpellPart> registeredSpells = new ArrayList<>(); //this will come handy for datagen

    public static void registerPehkuiGlyphs() {
        register(EffectResize.INSTANCE);
        register(EffectExpand.INSTANCE);
        register(EffectShrink.INSTANCE);
    }

    public static void register(AbstractSpellPart spellPart) {
        ArsNouveauAPI.getInstance().registerSpell(spellPart);
        registeredSpells.add(spellPart);
    }

    public static void registerIdentityGlyphs() {
        register(EffectMorph.INSTANCE);
    }

    public static void registerCompatGlyphs() {
        if (ModList.get().isLoaded("identity")) {
            registerIdentityGlyphs();
        }
        if (ModList.get().isLoaded("pehkui")) {
            registerPehkuiGlyphs();
        }
    }

}
