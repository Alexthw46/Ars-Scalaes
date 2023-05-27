package alexthw.ars_scalaes.datagen;

import alexthw.ars_scalaes.ArsScalaes;
import alexthw.ars_scalaes.block.DecoBlock;
import alexthw.ars_scalaes.registry.ModRegistry;
import net.minecraft.core.Direction;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.block.state.properties.StairsShape;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashSet;
import java.util.Set;

import static alexthw.ars_scalaes.datagen.ArsProviders.prefix;
import static alexthw.ars_scalaes.datagen.Setup.takeAll;
@SuppressWarnings("ConstantConditions")
public class ModBlockStateProvider extends BlockStateProvider {

    public ModBlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, ArsScalaes.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        Set<RegistryObject<Block>> blocks = new HashSet<>(ModRegistry.BLOCKS.getEntries());
        takeAll(blocks, b -> b.get() instanceof DecoBlock).forEach(this::basicBlock);
        takeAll(blocks, b -> b.get() instanceof SlabBlock).forEach(this::slabBlock);
        takeAll(blocks, b -> b.get() instanceof StairBlock).forEach(this::stairsBlock);
        takeAll(blocks, b -> b.get() instanceof WallBlock).forEach(this::wallBlock);
        takeAll(blocks, b -> b.get() instanceof FenceBlock).forEach(this::fenceBlock);
        takeAll(blocks, b -> b.get() instanceof FenceGateBlock).forEach(this::fenceGateBlock);
    }

    public void wallBlock(RegistryObject<Block> blockRegistryObject) {
        String name = ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get()).getPath();
        String baseName = name.substring(0, name.length() - 5);
        wallBlock((WallBlock) blockRegistryObject.get(), prefix("block/" + baseName));
    }

    public void slabBlock(RegistryObject<Block> blockRegistryObject) {
        String name = ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get()).getPath();
        String baseName = name.substring(0, name.length() - 5);
        slabBlock((SlabBlock) blockRegistryObject.get(), prefix(baseName), prefix("block/" + baseName));
    }

    public void fenceGateBlock(RegistryObject<Block> blockRegistryObject) {
        String name = ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get()).getPath();
        String baseName = name.substring(0, name.length() - 11);
        fenceGateBlock((FenceGateBlock) blockRegistryObject.get(), prefix("block/" + baseName));
    }

    public void fenceBlock(RegistryObject<Block> blockRegistryObject) {
        String name = ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get()).getPath();
        String baseName = name.substring(0, name.length() - 6);
        fenceBlock((FenceBlock) blockRegistryObject.get(), prefix("block/" + baseName));
    }

    private void basicBlock(RegistryObject<Block> blockRegistryObject) {
        simpleBlock(blockRegistryObject.get());
    }

    public void stairsBlock(RegistryObject<Block> blockRegistryObject) {
        String name = ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get()).getPath();
        String baseName = name.substring(0, name.length() - 7);
        stairsBlock(blockRegistryObject, prefix("block/" + baseName));
    }

    public void stairsBlock(RegistryObject<Block> block, ResourceLocation side) {
        var baseName = block.getId();
        ResourceLocation bottom = side, top = side;
        ResourceLocation top_in = top, top_out = top;
        if (side.getPath().contains("gilded_sourcestone_large")) {
            bottom = prefix(bottom.getPath() + "_bot");
            top = prefix(top.getPath() + "_top");
            top_in = prefix(top.getPath() + "_in");
            top_out = prefix(top.getPath() + "_out");
        }
        ModelFile stairs = models().stairs(baseName.toString(), side, bottom, top);
        ModelFile stairsInner = models().stairsInner(baseName + "_inner", side, bottom, top_in);
        ModelFile stairsOuter = models().stairsOuter(baseName + "_outer", side, bottom, top_out);
        stairsBlock((StairBlock) block.get(), stairs, stairsInner, stairsOuter);
    }

    @Override
    public void stairsBlock(StairBlock block, ModelFile stairs, ModelFile stairsInner, ModelFile stairsOuter) {
        getVariantBuilder(block)
                .forAllStatesExcept(state -> {
                    boolean name = stairs.getLocation().getPath().contains("gilded_sourcestone_large");
                    Direction facing = state.getValue(StairBlock.FACING);
                    Half half = state.getValue(StairBlock.HALF);
                    StairsShape shape = state.getValue(StairBlock.SHAPE);
                    int yRot = (int) facing.getClockWise().toYRot(); // Stairs model is rotated 90 degrees clockwise for some reason
                    if (shape == StairsShape.INNER_LEFT || shape == StairsShape.OUTER_LEFT) {
                        yRot += 270; // Left facing stairs are rotated 90 degrees clockwise
                    }
                    if (shape != StairsShape.STRAIGHT && half == Half.TOP) {
                        yRot += 90; // Top stairs are rotated 90 degrees clockwise
                    }
                    yRot %= 360;
                    boolean uvlock = yRot != 0 || half == Half.TOP; // Don't set uvlock for states that have no rotation
                    return ConfiguredModel.builder()
                            .modelFile(shape == StairsShape.STRAIGHT ? stairs : shape == StairsShape.INNER_LEFT || shape == StairsShape.INNER_RIGHT ? stairsInner : stairsOuter)
                            .rotationX(half == Half.BOTTOM ? 0 : 180)
                            .rotationY(yRot)
                            .uvLock(!name && uvlock)
                            .build();
                }, StairBlock.WATERLOGGED);
    }
}
