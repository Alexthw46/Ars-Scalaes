package alexthw.ars_scalaes.datagen;

import alexthw.ars_scalaes.ArsScalaes;
import alexthw.ars_scalaes.block.DecoBlockPack;
import alexthw.ars_scalaes.registry.ModRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;

public class ModLangProvider extends LanguageProvider {

    public ModLangProvider(DataGenerator gen, String locale) {
        super(gen, ArsScalaes.MODID, locale);
    }

    @Override
    protected void addTranslations() {
        add("itemGroup.ars_scalaes", "Ars Scalaes - Scaling and Stairs");
        decoBlockPackLang(ModRegistry.ARCANE_STONE, "Arcane Stone");
        decoBlockPackLang(ModRegistry.ARCANE_BRICK, "Arcane Bricks");
        decoBlockPackLang(ModRegistry.ARCANE_STONE_CLOVER, "Arcane Brick: Clover");
        decoBlockPackLang(ModRegistry.ARCANE_STONE_GOLD, "Gilded Arcane Stone");
        decoBlockPackLang(ModRegistry.ARCANE_STONE_GOLD_ALT, "Gilded Brick: Alternative");
        decoBlockPackLang(ModRegistry.ARCANE_STONE_GOLD_ASHLAR, "Gilded Brick: Ashlar");
        decoBlockPackLang(ModRegistry.ARCANE_STONE_GOLD_BASKET, "Gilded Brick: Basketweave");
        decoBlockPackLang(ModRegistry.ARCANE_STONE_GOLD_CLOVER, "Gilded Brick: Clover");
        decoBlockPackLang(ModRegistry.ARCANE_STONE_GOLD_HERRING, "Gilded Brick: Herringbone");
        decoBlockPackLang(ModRegistry.ARCANE_STONE_GOLD_MOSAIC, "Gilded Brick: Mosaic");
        decoBlockPackLang(ModRegistry.ARCANE_STONE_GOLD_SLAB, "Gilded Slabs");
        decoBlockPackLang(ModRegistry.ARCANE_BRICK_ALT, "Arcane Brick: Alternative");
        decoBlockPackLang(ModRegistry.ARCANE_BRICK_BASKET, "Arcane Brick: Basketweave");
        decoBlockPackLang(ModRegistry.ARCANE_BRICK_HERRING, "Arcane Brick: Herringbone");
        decoBlockPackLang(ModRegistry.ARCANE_BRICK_MOSAIC, "Arcane Brick: Mosaic");
        decoBlockPackLang(ModRegistry.ARCANE_STONE_SMOOTH, "Arcane Brick: Smooth");
        decoBlockPackLang(ModRegistry.ARCANE_BRICK_SMOOTH_ALT, "Arcane Brick: Smooth Alternate");
        decoBlockPackLang(ModRegistry.ARCANE_BRICK_SMOOTH_ASHLAR, "Arcane Brick: Smooth Ashlar");
        decoBlockPackLang(ModRegistry.ARCANE_BRICK_SMOOTH_CLOVER, "Arcane Brick: Smooth Clover");
        decoBlockPackLang(ModRegistry.ARCANE_BRICK_SMOOTH_BASKET, "Arcane Brick: Smooth Basketweave");
        decoBlockPackLang(ModRegistry.ARCANE_BRICK_SMOOTH_HERRING, "Arcane Brick: Smooth Herringbone");
        decoBlockPackLang(ModRegistry.ARCANE_BRICK_SMOOTH_MOSAIC, "Arcane Brick: Smooth Mosaic");

        add("item.ars_nouveau.glyph_resize", "Glyph of Resize");
        add("ars_nouveau.glyph_name.resize", "Resize");
        add("ars_nouveau.glyph_desc.resize", "Changes the size of a living entity. Amplify to grow, Dampen to shrink, do not augment to restore original size.");
        add("item.ars_nouveau.glyph_shrink", "Glyph of Shrink");
        add("ars_nouveau.glyph_name.shrink", "Shrink");
        add("ars_nouveau.glyph_desc.shrink", "Shrinks an entity. Unlike Resize, shrinking is cumulative with multiple casts.");
        add("item.ars_nouveau.glyph_expand", "Glyph of Expand");
        add("ars_nouveau.glyph_name.expand", "Expand");
        add("ars_nouveau.glyph_desc.expand", "Make an entity grow in size. Unlike Resize, expansion is cumulative with multiple casts.");
        add("ars_nouveau.glyph_name.morph", "Morph");
        add("ars_nouveau.glyph_desc.morph", "Morphs into the entity hit. Use on self to break the morph. Enabled when Identity is loaded.");

        add("effect.ars_scalaes.morph", "Morphed");
        add("effect.ars_scalaes.resize", "Resized");

    }

    private void decoBlockPackLang(DecoBlockPack decoBlockPack, String name) {
        add(decoBlockPack.getSlab(), name + " Slab");
        add(decoBlockPack.getStairs(), name + " Stairs");
        add(decoBlockPack.getWall(), name + " Wall");
    }

    @Override
    public String getName() {
        return "Ars Scalaes Lang";
    }
}
