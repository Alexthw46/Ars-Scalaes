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
        add("itemGroup.ars_scalaes","Ars Scalaes - Scaling and Stairs");
        decoBlockPackLang(ModRegistry.ARCANE_STONE, "Arcane Stone");
        decoBlockPackLang(ModRegistry.ARCANE_BRICK, "Arcane Bricks");
        decoBlockPackLang(ModRegistry.ARCANE_STONE_CLOVER,"Arcane Brick: Clover");
        decoBlockPackLang(ModRegistry.ARCANE_STONE_GOLD,"Gilded Arcane Stone");
        decoBlockPackLang(ModRegistry.ARCANE_STONE_GOLD_ALT,"Gilded Brick: Alternative");
        decoBlockPackLang(ModRegistry.ARCANE_STONE_GOLD_ASHLAR,"Gilded Brick: Ashlar");
        decoBlockPackLang(ModRegistry.ARCANE_STONE_GOLD_BASKET,"Gilded Brick: Basketweave");
        decoBlockPackLang(ModRegistry.ARCANE_STONE_GOLD_CLOVER,"Gilded Brick: Clover");
        decoBlockPackLang(ModRegistry.ARCANE_STONE_GOLD_HERRING,"Gilded Brick: Herringbone");
        decoBlockPackLang(ModRegistry.ARCANE_STONE_GOLD_MOSAIC,"Gilded Brick: Mosaic");
        decoBlockPackLang(ModRegistry.ARCANE_STONE_GOLD_SLAB,"Gilded Slabs");
        decoBlockPackLang(ModRegistry.ARCANE_BRICK_ALT,"Arcane Brick: Alternative");
        decoBlockPackLang(ModRegistry.ARCANE_BRICK_BASKET,"Arcane Brick: Basketweave");
        decoBlockPackLang(ModRegistry.ARCANE_BRICK_HERRING,"Arcane Brick: Herringbone");
        decoBlockPackLang(ModRegistry.ARCANE_BRICK_MOSAIC,"Arcane Brick: Mosaic");
        decoBlockPackLang(ModRegistry.ARCANE_STONE_SMOOTH,"Arcane Brick: Smooth");
        decoBlockPackLang(ModRegistry.ARCANE_BRICK_SMOOTH_ALT,"Arcane Brick: Smooth Alternate");
        decoBlockPackLang(ModRegistry.ARCANE_BRICK_SMOOTH_ASHLAR,"Arcane Brick: Smooth Ashlar");
        decoBlockPackLang(ModRegistry.ARCANE_BRICK_SMOOTH_CLOVER,"Arcane Brick: Smooth Clover");
        decoBlockPackLang(ModRegistry.ARCANE_BRICK_SMOOTH_BASKET,"Arcane Brick: Smooth Basketweave");
        decoBlockPackLang(ModRegistry.ARCANE_BRICK_SMOOTH_HERRING,"Arcane Brick: Smooth Herringbone");
        decoBlockPackLang(ModRegistry.ARCANE_BRICK_SMOOTH_MOSAIC,"Arcane Brick: Smooth Mosaic");
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
