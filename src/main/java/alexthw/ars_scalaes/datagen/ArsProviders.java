package alexthw.ars_scalaes.datagen;

import alexthw.ars_scalaes.ArsScalaes;
import alexthw.ars_scalaes.glyph.*;
import alexthw.ars_scalaes.immersive_portals.IPCompat;
import com.hollingsworth.arsnouveau.api.enchanting_apparatus.EnchantingApparatusRecipe;
import com.hollingsworth.arsnouveau.api.familiar.AbstractFamiliarHolder;
import com.hollingsworth.arsnouveau.api.ritual.AbstractRitual;
import com.hollingsworth.arsnouveau.api.spell.AbstractCastMethod;
import com.hollingsworth.arsnouveau.api.spell.AbstractEffect;
import com.hollingsworth.arsnouveau.api.spell.AbstractSpellPart;
import com.hollingsworth.arsnouveau.common.crafting.recipes.GlyphRecipe;
import com.hollingsworth.arsnouveau.common.crafting.recipes.ImbuementRecipe;
import com.hollingsworth.arsnouveau.common.datagen.ApparatusRecipeProvider;
import com.hollingsworth.arsnouveau.common.datagen.GlyphRecipeProvider;
import com.hollingsworth.arsnouveau.common.datagen.ImbuementRecipeProvider;
import com.hollingsworth.arsnouveau.common.datagen.PatchouliProvider;
import com.hollingsworth.arsnouveau.common.datagen.patchouli.*;
import com.hollingsworth.arsnouveau.setup.registry.ItemsRegistry;
import com.sammy.malum.MalumMod;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.ItemLike;
import virtuoel.pehkui.Pehkui;

import java.io.IOException;
import java.nio.file.Path;

import static com.hollingsworth.arsnouveau.setup.registry.RegistryHelper.getRegistryName;


@SuppressWarnings("unused")
public class ArsProviders {

    public static class GlyphProvider extends GlyphRecipeProvider {

        public GlyphProvider(DataGenerator generatorIn) {
            super(generatorIn);
        }

        @Override
        public void collectJsons(CachedOutput pOutput) {

            //add(get(EffectResize.INSTANCE).withItem(ItemsRegistry.MANIPULATION_ESSENCE).withItem(ItemsRegistry.ABJURATION_ESSENCE).withItem(Items.BROWN_MUSHROOM));
            //add(get(EffectShrink.INSTANCE).withItem(ItemsRegistry.MANIPULATION_ESSENCE).withItem(ItemsRegistry.ABJURATION_ESSENCE).withItem(Items.TURTLE_EGG));
            //add(get(EffectExpand.INSTANCE).withItem(ItemsRegistry.MANIPULATION_ESSENCE).withItem(ItemsRegistry.ABJURATION_ESSENCE).withItem(Items.PUFFERFISH));
            //add(get(EffectMorph.INSTANCE).withItem(ItemsRegistry.MANIPULATION_ESSENCE).withItem(ItemsRegistry.ABJURATION_ESSENCE).withItem(ItemsRegistry.WILDEN_HORN).withItem(ItemsRegistry.WILDEN_WING));
            //add(get(EffectSoulShatter.INSTANCE).withItem(ItemsRegistry.MANIPULATION_ESSENCE).withItem(ItemRegistry.WICKED_SPIRIT).withItem(ItemRegistry.SOUL_STAINED_STEEL_SWORD));

            for (GlyphRecipe recipe : recipes) {
                Path path = getScribeGlyphPath(output, recipe.output.getItem());
                saveStable(pOutput, recipe.asRecipe(), path);
            }

        }

        protected static Path getScribeGlyphPath(Path pathIn, Item glyph) {
            return pathIn.resolve("data/" + root + "/recipes/" + getRegistryName(glyph).getPath() + ".json");

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
        public void collectJsons(CachedOutput pOutput) {

            recipes.add(builder().withResult(IPCompat.IMMERSIVE_WARP_SCROLL.get().getDefaultInstance())
                    .keepNbtOfReagent(true)
                    .withSourceCost(9000)
                    .withReagent(Ingredient.of(ItemsRegistry.WARP_SCROLL, ItemsRegistry.STABLE_WARP_SCROLL))
                    .build());

            for (EnchantingApparatusRecipe g : recipes) {
                if (g != null) {
                    Path path = getRecipePath(output, g.getId().getPath());
                    saveStable(pOutput, g.asRecipe(), path);
                }
            }

        }

        protected static Path getRecipePath(Path pathIn, String str) {
            return pathIn.resolve("data/" + root + "/recipes/" + str + ".json");
        }

        @Override
        public String getName() {
            return "ArsScalaes Apparatus";
        }
    }

    public static class ImbuementProvider extends ImbuementRecipeProvider {

        public ImbuementProvider(DataGenerator generatorIn) {
            super(generatorIn);
        }

        @Override
        public void collectJsons(CachedOutput pOutput) {

            for (ImbuementRecipe g : recipes) {
                Path path = getRecipePath(output, g.getId().getPath());
                saveStable(pOutput, g.asRecipe(), path);
            }

        }

        protected Path getRecipePath(Path pathIn, String str) {
            return pathIn.resolve("data/" + root + "/recipes/" + str + ".json");
        }

        @Override
        public String getName() {
            return "ArsScalaes Imbuement";
        }

    }

    public static class ScalaesPatchouliProvider extends PatchouliProvider {

        public ScalaesPatchouliProvider(DataGenerator generatorIn) {
            super(generatorIn);
        }

        @Override
        public void collectJsons(CachedOutput pOutput) {

            addGlyphPage(EffectResize.INSTANCE, Pehkui.MOD_ID);
            addGlyphPage(EffectExpand.INSTANCE, Pehkui.MOD_ID);
            addGlyphPage(EffectShrink.INSTANCE, Pehkui.MOD_ID);
            addGlyphPage(EffectMorph.INSTANCE, "identity");
            addGlyphPage(EffectSoulShatter.INSTANCE, MalumMod.MALUM);

            for (PatchouliPage patchouliPage : pages) {
                saveStable(pOutput, patchouliPage.build(), patchouliPage.path());
            }

        }

        @Override
        public PatchouliPage addBasicItem(ItemLike item, ResourceLocation category, IPatchouliPage recipePage) {
            PatchouliBuilder builder = new PatchouliBuilder(category, item.asItem().getDescriptionId())
                    .withIcon(item.asItem())
                    .withPage(new TextPage(root + ".page." + getRegistryName(item.asItem()).getPath()))
                    .withPage(recipePage);
            var page = new PatchouliPage(builder, getPath(category, getRegistryName(item.asItem()).getPath()));
            this.pages.add(page);
            return page;
        }

        public void addFamiliarPage(AbstractFamiliarHolder familiarHolder) {
            PatchouliBuilder builder = new PatchouliBuilder(FAMILIARS, "entity." + root + "." + familiarHolder.getRegistryName().getPath())
                    .withIcon(root + ":" + familiarHolder.getRegistryName().getPath())
                    .withTextPage(root + ".familiar_desc." + familiarHolder.getRegistryName().getPath())
                    .withPage(new EntityPage(familiarHolder.getRegistryName().toString()));
            this.pages.add(new PatchouliPage(builder, getPath(FAMILIARS, familiarHolder.getRegistryName().getPath())));
        }

        public void addRitualPage(AbstractRitual ritual) {
            PatchouliBuilder builder = new PatchouliBuilder(RITUALS, "item." + root + '.' + ritual.getRegistryName().getPath())
                    .withIcon(ritual.getRegistryName().toString())
                    .withTextPage(ritual.getDescriptionKey())
                    .withPage(new CraftingPage(root + ":tablet_" + ritual.getRegistryName().getPath()));

            this.pages.add(new PatchouliPage(builder, getPath(RITUALS, ritual.getRegistryName().getPath())));
        }

        public void addEnchantmentPage(Enchantment enchantment) {
            PatchouliBuilder builder = new PatchouliBuilder(ENCHANTMENTS, enchantment.getDescriptionId())
                    .withIcon(getRegistryName(Items.ENCHANTED_BOOK).toString())
                    .withTextPage(root + ".enchantment_desc." + getRegistryName(enchantment).getPath());

            for (int i = enchantment.getMinLevel(); i <= enchantment.getMaxLevel(); i++) {
                builder.withPage(new EnchantingPage("ars_nouveau:" + getRegistryName(enchantment).getPath() + "_" + i));
            }
            this.pages.add(new PatchouliPage(builder, getPath(ENCHANTMENTS, getRegistryName(enchantment).getPath())));
        }

        public void addGlyphPage(AbstractSpellPart spellPart, String modid) {
            ResourceLocation category = switch (spellPart.defaultTier().value) {
                case 1 -> GLYPHS_1;
                case 2 -> GLYPHS_2;
                default -> GLYPHS_3;
            };
            PatchouliBuilder builder = new PatchouliBuilder(category, spellPart.getName())
                    .withName(root + ".glyph_name." + spellPart.getRegistryName().getPath())
                    .withIcon(spellPart.getRegistryName().toString())
                    .withSortNum(spellPart instanceof AbstractCastMethod ? 1 : spellPart instanceof AbstractEffect ? 2 : 3)
                    .withPage(new TextPage(root + ".glyph_desc." + spellPart.getRegistryName().getPath()))
                    .withPage(new GlyphScribePage(spellPart))
                    .withProperty("flag", "mod:" + modid);
            this.pages.add(new PatchouliPage(builder, getPath(category, spellPart.getRegistryName().getPath())));
        }

        /**
         * Gets a name for this provider, to use in logging.
         */
        @Override
        public String getName() {
            return "ArsScalaes Patchouli Datagen";
        }


    }

    static final String root = ArsScalaes.MODID;

    public static ResourceLocation prefix(String path) {
        return new ResourceLocation(ArsScalaes.MODID, path);
    }

}
