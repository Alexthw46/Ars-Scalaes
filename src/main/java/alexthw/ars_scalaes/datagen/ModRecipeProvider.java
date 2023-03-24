package alexthw.ars_scalaes.datagen;

import alexthw.ars_scalaes.ArsScalaes;
import alexthw.ars_scalaes.block.DecoBlockPack;
import alexthw.ars_scalaes.registry.ModRegistry;
import com.hollingsworth.arsnouveau.ArsNouveau;
import com.hollingsworth.arsnouveau.common.lib.LibBlockNames;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.SingleItemRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider {
    public ModRecipeProvider(DataGenerator pGenerator) {
        super(pGenerator);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> pFinishedRecipeConsumer) {

        buildDecoPack(pFinishedRecipeConsumer, LibBlockNames.SOURCESTONE, ModRegistry.SOURCESTONE);
        buildDecoPack(pFinishedRecipeConsumer, LibBlockNames.SOURCESTONE_MOSAIC, ModRegistry.SOURCESTONE_MOSAIC);
        buildDecoPack(pFinishedRecipeConsumer, LibBlockNames.SOURCESTONE_BASKET, ModRegistry.SOURCESTONE_BASKET);
        buildDecoPack(pFinishedRecipeConsumer, LibBlockNames.SOURCESTONE_ALTERNATING, ModRegistry.SOURCESTONE_ALTERNATING);
        buildDecoPack(pFinishedRecipeConsumer, LibBlockNames.SOURCESTONE_SMALL_BRICKS, ModRegistry.SOURCESTONE_SMALL_BRICKS);
        buildDecoPack(pFinishedRecipeConsumer, LibBlockNames.SOURCESTONE_LARGE_BRICKS, ModRegistry.SOURCESTONE_LARGE_BRICKS);

        buildDecoPack(pFinishedRecipeConsumer, LibBlockNames.GILDED_SOURCESTONE_MOSAIC, ModRegistry.GILDED_SOURCESTONE_MOSAIC);
        buildDecoPack(pFinishedRecipeConsumer, LibBlockNames.GILDED_SOURCESTONE_BASKET, ModRegistry.GILDED_SOURCESTONE_BASKET);
        buildDecoPack(pFinishedRecipeConsumer, LibBlockNames.GILDED_SOURCESTONE_ALTERNATING, ModRegistry.GILDED_SOURCESTONE_ALTERNATING);
        buildDecoPack(pFinishedRecipeConsumer, LibBlockNames.GILDED_SOURCESTONE_SMALL_BRICKS, ModRegistry.GILDED_SOURCESTONE_SMALL_BRICKS);
        buildDecoPack(pFinishedRecipeConsumer, LibBlockNames.GILDED_SOURCESTONE_LARGE_BRICKS, ModRegistry.GILDED_SOURCESTONE_LARGE_BRICKS);

        buildDecoPack(pFinishedRecipeConsumer, LibBlockNames.SMOOTH_SOURCESTONE_MOSAIC, ModRegistry.SMOOTH_SOURCESTONE_MOSAIC);
        buildDecoPack(pFinishedRecipeConsumer, LibBlockNames.SMOOTH_SOURCESTONE_BASKET, ModRegistry.SMOOTH_SOURCESTONE_BASKET);
        buildDecoPack(pFinishedRecipeConsumer, LibBlockNames.SMOOTH_SOURCESTONE_ALTERNATING, ModRegistry.SMOOTH_SOURCESTONE_ALTERNATING);
        buildDecoPack(pFinishedRecipeConsumer, LibBlockNames.SMOOTH_SOURCESTONE_SMALL_BRICKS, ModRegistry.SMOOTH_SOURCESTONE_SMALL_BRICKS);
        buildDecoPack(pFinishedRecipeConsumer, LibBlockNames.SMOOTH_SOURCESTONE_LARGE_BRICKS, ModRegistry.SMOOTH_SOURCESTONE_LARGE_BRICKS);

        buildDecoPack(pFinishedRecipeConsumer, LibBlockNames.SMOOTH_GILDED_SOURCESTONE_MOSAIC, ModRegistry.SMOOTH_GILDED_SOURCESTONE_MOSAIC);
        buildDecoPack(pFinishedRecipeConsumer, LibBlockNames.SMOOTH_GILDED_SOURCESTONE_BASKET, ModRegistry.SMOOTH_GILDED_SOURCESTONE_BASKET);
        buildDecoPack(pFinishedRecipeConsumer, LibBlockNames.SMOOTH_GILDED_SOURCESTONE_ALTERNATING, ModRegistry.SMOOTH_GILDED_SOURCESTONE_ALTERNATING);
        buildDecoPack(pFinishedRecipeConsumer, LibBlockNames.SMOOTH_GILDED_SOURCESTONE_SMALL_BRICKS, ModRegistry.SMOOTH_GILDED_SOURCESTONE_SMALL_BRICKS);
        buildDecoPack(pFinishedRecipeConsumer, LibBlockNames.SMOOTH_GILDED_SOURCESTONE_LARGE_BRICKS, ModRegistry.SMOOTH_GILDED_SOURCESTONE_LARGE_BRICKS);

    }

    private void buildDecoPack(Consumer<FinishedRecipe> consumer, String blockKey, DecoBlockPack decoBlockPack) {
        Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(ArsNouveau.MODID, blockKey));
        if (block == null) return;
        makeSlab(consumer, block, decoBlockPack.getSlab(), decoBlockPack.basename);
        makeWall(consumer, block, decoBlockPack.getWall(), decoBlockPack.basename);
        makeStairs(consumer, block, decoBlockPack.getStairs(), decoBlockPack.basename);

        makeStonecutter(consumer, block, decoBlockPack.getSlab(), 2, decoBlockPack.basename);
        makeStonecutter(consumer, block, decoBlockPack.getWall(), decoBlockPack.basename);
        makeStonecutter(consumer, block, decoBlockPack.getStairs(), decoBlockPack.basename);
        STONECUTTER_COUNTER = 0;
    }

    private void makeSlab(Consumer<FinishedRecipe> consumer, Block block, Block slab, String basename) {
        ShapedRecipeBuilder.shaped(slab, 6).pattern("BBB").define('B', block).unlockedBy("has_journal", InventoryChangeTrigger.TriggerInstance.hasItems(block)).save(consumer, new ResourceLocation(ArsScalaes.MODID, basename + "_slab"));
    }

    private void makeWall(Consumer<FinishedRecipe> consumer, Block block, Block wall, String basename) {
        ShapedRecipeBuilder.shaped(wall, 6).pattern("BBB").pattern("BBB").define('B', block).unlockedBy("has_journal", InventoryChangeTrigger.TriggerInstance.hasItems(block)).save(consumer, new ResourceLocation(ArsScalaes.MODID, basename + "_wall"));
    }

    private void makeStairs(Consumer<FinishedRecipe> consumer, Block block, Block stairs, String basename) {
        ShapedRecipeBuilder.shaped(stairs, 5).pattern("B  ").pattern("BB ").pattern("BBB").define('B', block).unlockedBy("has_journal", InventoryChangeTrigger.TriggerInstance.hasItems(block)).save(consumer, new ResourceLocation(ArsScalaes.MODID, basename + "_stair"));
    }

    private static int STONECUTTER_COUNTER = 0;

    public static void makeStonecutter(Consumer<FinishedRecipe> consumer, ItemLike input, ItemLike output, String reg) {
        makeStonecutter(consumer, input, output, 1, reg);
    }

    public static void makeStonecutter(Consumer<FinishedRecipe> consumer, ItemLike input, ItemLike output, int quantity, String reg) {
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(input), output, quantity).unlockedBy("has_journal", InventoryChangeTrigger.TriggerInstance.hasItems(input)).save(consumer, new ResourceLocation(ArsScalaes.MODID, reg + "_" + STONECUTTER_COUNTER));
        STONECUTTER_COUNTER++;
    }


    @Override
    public String getName() {
        return "Ars Scalaes Recipes";
    }
}
