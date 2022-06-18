package alexthw.ars_scalaes.datagen;

import alexthw.ars_scalaes.ArsNouveauRegistry;
import alexthw.ars_scalaes.ArsScalaes;
import alexthw.ars_scalaes.glyph.EffectExpand;
import alexthw.ars_scalaes.glyph.EffectResize;
import alexthw.ars_scalaes.glyph.EffectShrink;
import com.hollingsworth.arsnouveau.api.enchanting_apparatus.EnchantingApparatusRecipe;
import com.hollingsworth.arsnouveau.api.familiar.AbstractFamiliarHolder;
import com.hollingsworth.arsnouveau.api.ritual.AbstractRitual;
import com.hollingsworth.arsnouveau.api.spell.AbstractSpellPart;
import com.hollingsworth.arsnouveau.common.crafting.recipes.GlyphRecipe;
import com.hollingsworth.arsnouveau.common.crafting.recipes.ImbuementRecipe;
import com.hollingsworth.arsnouveau.common.datagen.ApparatusRecipeProvider;
import com.hollingsworth.arsnouveau.common.datagen.GlyphRecipeProvider;
import com.hollingsworth.arsnouveau.common.datagen.ImbuementRecipeProvider;
import com.hollingsworth.arsnouveau.common.datagen.patchouli.*;
import com.hollingsworth.arsnouveau.setup.ItemsRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.ItemLike;

import java.io.IOException;
import java.nio.file.Path;

public class ArsProviders {

    public static class GlyphProvider extends GlyphRecipeProvider {

        public GlyphProvider(DataGenerator generatorIn) {
            super(generatorIn);
        }

        @Override
        public void run(HashCache cache) throws IOException {

            Path output = this.generator.getOutputFolder();

            add(get(EffectResize.INSTANCE).withItem(ItemsRegistry.MANIPULATION_ESSENCE).withItem(ItemsRegistry.ABJURATION_ESSENCE).withItem(Items.BROWN_MUSHROOM));
            add(get(EffectShrink.INSTANCE).withItem(ItemsRegistry.MANIPULATION_ESSENCE).withItem(ItemsRegistry.ABJURATION_ESSENCE).withItem(Items.TURTLE_EGG));
            add(get(EffectExpand.INSTANCE).withItem(ItemsRegistry.MANIPULATION_ESSENCE).withItem(ItemsRegistry.ABJURATION_ESSENCE).withItem(Items.PUFFERFISH));

            for (GlyphRecipe recipe : recipes) {
                Path path = getScribeGlyphPath(output, recipe.output.getItem());
                DataProvider.save(GSON, cache, recipe.asRecipe(), path);
            }

        }

        @Override
        public String getName() {
            return "ArsScalaes Glyph Recipes";
        }
    }

    public static class EnchantingAppProvider extends ApparatusRecipeProvider {

        public EnchantingAppProvider(DataGenerator generatorIn) {
            super(generatorIn);
        }

        @Override
        public void run(HashCache cache) throws IOException {


            Path output = this.generator.getOutputFolder();
            for (EnchantingApparatusRecipe g : recipes){
                if (g != null){
                    Path path = getRecipePath(output, g.getId().getPath());
                    DataProvider.save(GSON, cache, g.asRecipe(), path);
                }
            }

        }

        protected static Path getRecipePath(Path pathIn, String str){
            return pathIn.resolve("data/"+ root +"/recipes/" + str + ".json");
        }

        @Override
        public String getName() {
            return "ArsScalaes Apparatus";
        }
    }

    public static class ImbuementProvider extends ImbuementRecipeProvider {

        public ImbuementProvider(DataGenerator generatorIn){
            super(generatorIn);
        }

        @Override
        public void run(HashCache cache) throws IOException {

            Path output = generator.getOutputFolder();
            for(ImbuementRecipe g : recipes){
                Path path = getRecipePath(output, g.getId().getPath());
                DataProvider.save(GSON, cache, g.asRecipe(), path);
            }

        }

        protected Path getRecipePath(Path pathIn, String str){
            return pathIn.resolve("data/"+ root +"/recipes/" + str + ".json");
        }

        @Override
        public String getName() {
            return "ArsScalaes Imbuement";
        }

    }

    @SuppressWarnings("ConstantConditions")
    public static class PatchouliProvider extends com.hollingsworth.arsnouveau.common.datagen.PatchouliProvider {

        public PatchouliProvider(DataGenerator generatorIn) {
            super(generatorIn);
        }

        @Override
        public void run(HashCache cache) throws IOException {

            for (AbstractSpellPart spell : ArsNouveauRegistry.registeredSpells) {
                //addGlyphPage(spell);
            }

            //check the superclass for examples

            for (PatchouliPage patchouliPage : pages) {
                DataProvider.save(GSON, cache, patchouliPage.build(), patchouliPage.path());
            }

        }

        @Override
        public void addBasicItem(ItemLike item, ResourceLocation category, IPatchouliPage recipePage){
            PatchouliBuilder builder = new PatchouliBuilder(category, item.asItem().getDescriptionId())
                    .withIcon(item.asItem())
                    .withPage(new TextPage(root + ".page." + item.asItem().getRegistryName().getPath()))
                    .withPage(recipePage);
            this.pages.add(new PatchouliPage(builder, getPath(category, item.asItem().getRegistryName().getPath())));
        }

        public void addFamiliarPage(AbstractFamiliarHolder familiarHolder) {
            PatchouliBuilder builder = new PatchouliBuilder(FAMILIARS, "entity."+ root + "." + familiarHolder.getId() + "_familiar")
                    .withIcon("ars_nouveau:familiar_" + familiarHolder.getId())
                    .withTextPage("ars_nouveau.familiar_desc." + familiarHolder.getId())
                    .withPage(new EntityPage(prefix(familiarHolder.getEntityKey() + "_familiar").toString()));
            this.pages.add(new PatchouliPage(builder, getPath(FAMILIARS, familiarHolder.getId())));
        }

        public void addRitualPage(AbstractRitual ritual) {
            PatchouliBuilder builder = new PatchouliBuilder(RITUALS, "item.ars_nouveau.ritual_" + ritual.getID())
                    .withIcon("ars_nouveau:ritual_" + ritual.getID())
                    .withTextPage("ars_nouveau.ritual_desc." + ritual.getID())
                    .withPage(new CraftingPage(root + ":ritual_" + ritual.getID()));

            this.pages.add(new PatchouliPage(builder, getPath(RITUALS, ritual.getID())));
        }

        public void addEnchantmentPage(Enchantment enchantment) {
            PatchouliBuilder builder = new PatchouliBuilder(ENCHANTMENTS, enchantment.getDescriptionId())
                    .withIcon(Items.ENCHANTED_BOOK.getRegistryName().toString())
                    .withTextPage(root + ".enchantment_desc." + enchantment.getRegistryName().getPath());

            for (int i = enchantment.getMinLevel(); i <= enchantment.getMaxLevel(); i++) {
                builder.withPage(new EnchantingPage("ars_nouveau:" + enchantment.getRegistryName().getPath() + "_" + i));
            }
            this.pages.add(new PatchouliPage(builder, getPath(ENCHANTMENTS, enchantment.getRegistryName().getPath())));
        }

        /**
         * Gets a name for this provider, to use in logging.
         */
        @Override
        public String getName() {
            return "ArsScalaes Patchouli Datagen";
        }

        @Override
        public Path getPath(ResourceLocation category, String fileName) {
            return this.generator.getOutputFolder().resolve("data/"+ root +"/patchouli_books/scales/en_us/entries/" + category.getPath() + "/" + fileName + ".json");
        }

        ImbuementPage ImbuementPage(ItemLike item){
            return new ImbuementPage(root + ":imbuement_" + item.asItem().getRegistryName().getPath());
        }

    }

    static String root = ArsScalaes.MODID;
    static ResourceLocation prefix(String path){
        return new ResourceLocation(ArsScalaes.MODID, path);
    }

}
