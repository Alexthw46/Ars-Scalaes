package alexthw.ars_scalaes.datagen;

import alexthw.ars_scalaes.ArsScalaes;
import alexthw.ars_scalaes.block.DecoBlockPack;
import alexthw.ars_scalaes.registry.ModRegistry;
import com.hollingsworth.arsnouveau.ArsNouveau;
import com.hollingsworth.arsnouveau.common.block.ModBlock;
import com.hollingsworth.arsnouveau.setup.BlockRegistry;
import com.hollingsworth.arsnouveau.setup.ItemsRegistry;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.SingleItemRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider {
    public ModRecipeProvider(DataGenerator pGenerator) {
        super(pGenerator);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> pFinishedRecipeConsumer) {

        buildDecoPack(pFinishedRecipeConsumer, BlockRegistry.ARCANE_STONE, ModRegistry.ARCANE_STONE);
        buildDecoPack(pFinishedRecipeConsumer, BlockRegistry.AB_CLOVER, ModRegistry.ARCANE_STONE_CLOVER);
        buildDecoPack(pFinishedRecipeConsumer, BlockRegistry.AS_GOLD_CLOVER, ModRegistry.ARCANE_STONE_GOLD_CLOVER);
        buildDecoPack(pFinishedRecipeConsumer, BlockRegistry.AS_GOLD_STONE, ModRegistry.ARCANE_STONE_GOLD);
        buildDecoPack(pFinishedRecipeConsumer, BlockRegistry.AS_GOLD_ALT, ModRegistry.ARCANE_STONE_GOLD_ALT);
        buildDecoPack(pFinishedRecipeConsumer, BlockRegistry.AS_GOLD_ASHLAR, ModRegistry.ARCANE_STONE_GOLD_ASHLAR);
        buildDecoPack(pFinishedRecipeConsumer, BlockRegistry.AS_GOLD_BASKET, ModRegistry.ARCANE_STONE_GOLD_BASKET);
        buildDecoPack(pFinishedRecipeConsumer, BlockRegistry.AS_GOLD_HERRING, ModRegistry.ARCANE_STONE_GOLD_HERRING);
        buildDecoPack(pFinishedRecipeConsumer, BlockRegistry.AS_GOLD_MOSAIC, ModRegistry.ARCANE_STONE_GOLD_MOSAIC);
        buildDecoPack(pFinishedRecipeConsumer, BlockRegistry.AS_GOLD_SLAB, ModRegistry.ARCANE_STONE_GOLD_SLAB);

        buildDecoPack(pFinishedRecipeConsumer, BlockRegistry.ARCANE_BRICKS, ModRegistry.ARCANE_BRICK);
        buildDecoPack(pFinishedRecipeConsumer, BlockRegistry.AB_ALTERNATE, ModRegistry.ARCANE_BRICK_ALT);
        buildDecoPack(pFinishedRecipeConsumer, BlockRegistry.AB_BASKET, ModRegistry.ARCANE_BRICK_BASKET);
        buildDecoPack(pFinishedRecipeConsumer, BlockRegistry.AB_HERRING, ModRegistry.ARCANE_BRICK_HERRING);
        buildDecoPack(pFinishedRecipeConsumer, BlockRegistry.AB_MOSAIC, ModRegistry.ARCANE_BRICK_MOSAIC);

        buildDecoPack(pFinishedRecipeConsumer, BlockRegistry.AB_SMOOTH, ModRegistry.ARCANE_STONE_SMOOTH);
        buildDecoPack(pFinishedRecipeConsumer, BlockRegistry.AB_SMOOTH_ALTERNATING, ModRegistry.ARCANE_BRICK_SMOOTH_ALT);
        buildDecoPack(pFinishedRecipeConsumer, BlockRegistry.AB_SMOOTH_ASHLAR, ModRegistry.ARCANE_BRICK_SMOOTH_ASHLAR);
        buildDecoPack(pFinishedRecipeConsumer, BlockRegistry.AB_SMOOTH_BASKET, ModRegistry.ARCANE_BRICK_SMOOTH_BASKET);
        buildDecoPack(pFinishedRecipeConsumer, BlockRegistry.AB_SMOOTH_CLOVER, ModRegistry.ARCANE_BRICK_SMOOTH_CLOVER);
        buildDecoPack(pFinishedRecipeConsumer, BlockRegistry.AB_SMOOTH_HERRING, ModRegistry.ARCANE_BRICK_SMOOTH_HERRING);
        buildDecoPack(pFinishedRecipeConsumer, BlockRegistry.AB_SMOOTH_MOSAIC, ModRegistry.ARCANE_BRICK_SMOOTH_MOSAIC);
    }

    private void buildDecoPack(Consumer<FinishedRecipe> consumer, ModBlock block, DecoBlockPack decoBlockPack) {
        makeStonecutter(consumer, block, decoBlockPack.getSlab(), decoBlockPack.basename);
        makeStonecutter(consumer, block, decoBlockPack.getWall(), decoBlockPack.basename);
        makeStonecutter(consumer, block, decoBlockPack.getStairs(), decoBlockPack.basename);
        STONECUTTER_COUNTER = 0;
    }


    private static int STONECUTTER_COUNTER = 0;
    public static void makeStonecutter(Consumer<FinishedRecipe> consumer, ItemLike input, ItemLike output, String reg) {
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(input), output).unlockedBy("has_journal", InventoryChangeTrigger.TriggerInstance.hasItems(ItemsRegistry.WORN_NOTEBOOK)).save(consumer, new ResourceLocation(ArsScalaes.MODID, reg + "_" + STONECUTTER_COUNTER));
        STONECUTTER_COUNTER++;
    }

    @Override
    public String getName() {
        return "Ars Scalaes Recipes";
    }
}
