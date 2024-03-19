package alexthw.ars_scalaes;

import alexthw.ars_scalaes.glyph.EffectExpand;
import alexthw.ars_scalaes.glyph.EffectMorph;
import alexthw.ars_scalaes.glyph.EffectResize;
import alexthw.ars_scalaes.glyph.EffectShrink;
import com.hollingsworth.arsnouveau.api.registry.GlyphRegistry;
import com.hollingsworth.arsnouveau.api.spell.AbstractSpellPart;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraftforge.fml.ModList;

import java.util.ArrayList;
import java.util.List;

public class ArsNouveauRegistry {

    public static TagKey<DamageType> FORGE_MAGIC = TagKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("forge", "is_magic"));

    public static final List<AbstractSpellPart> registeredSpells = new ArrayList<>(); //this will come handy for datagen

    public static void registerPehkuiGlyphs() {
        register(EffectResize.INSTANCE);
        register(EffectExpand.INSTANCE);
        register(EffectShrink.INSTANCE);
    }

    /*
    private static void registerMalumGlyphs() {
        register(EffectSoulShatter.INSTANCE);
    }
     */

    public static void registerIdentityGlyphs() {
        register(EffectMorph.INSTANCE);
    }

    public static void registerCompatGlyphs() {
        /*
        if (ModList.get().isLoaded("malum")) {
            registerMalumGlyphs();
        }
         */
        if (ModList.get().isLoaded("identity")) {
            registerIdentityGlyphs();
        }
        if (ModList.get().isLoaded("pehkui")) {
            registerPehkuiGlyphs();
        }
    }

    public static void register(AbstractSpellPart spellPart) {
        GlyphRegistry.registerSpell(spellPart);
        registeredSpells.add(spellPart);
    }

}
