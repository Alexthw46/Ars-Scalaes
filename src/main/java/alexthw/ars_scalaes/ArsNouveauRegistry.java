package alexthw.ars_scalaes;

import alexthw.ars_scalaes.glyph.EffectExpand;
import alexthw.ars_scalaes.glyph.EffectResize;
import alexthw.ars_scalaes.glyph.EffectShrink;
import com.hollingsworth.arsnouveau.api.ArsNouveauAPI;
import com.hollingsworth.arsnouveau.api.spell.AbstractSpellPart;

import java.util.ArrayList;
import java.util.List;

public class ArsNouveauRegistry {

    public static List<AbstractSpellPart> registeredSpells = new ArrayList<>(); //this will come handy for datagen

    public static void registerPehkuiGlyphs() {
        register(EffectResize.INSTANCE);
        register(EffectExpand.INSTANCE);
        register(EffectShrink.INSTANCE);
    }

    public static void register(AbstractSpellPart spellPart){
        ArsNouveauAPI.getInstance().registerSpell(spellPart);
        registeredSpells.add(spellPart);
    }

}
