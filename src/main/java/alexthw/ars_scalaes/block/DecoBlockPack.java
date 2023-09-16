package alexthw.ars_scalaes.block;

import com.hollingsworth.arsnouveau.ArsNouveau;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

import static alexthw.ars_scalaes.registry.ModRegistry.addBlock;

public class DecoBlockPack {
    final DeferredRegister<Block> registry;
    public final String basename;
    final net.minecraft.world.level.block.state.BlockBehaviour.Properties props;
    final Supplier<Block> full;
    final RegistryObject<Block> slab;
    final RegistryObject<Block> stair;
    RegistryObject<Block> wall = null;
    RegistryObject<Block> fence = null;
    RegistryObject<Block> fence_gate = null;

    public DecoBlockPack(DeferredRegister<Block> blocks, String basename, Properties props) {
        this.registry = blocks;
        this.basename = basename;
        this.props = props;
        this.full = () -> ForgeRegistries.BLOCKS.getValue(new ResourceLocation(ArsNouveau.MODID, basename));
        this.slab = addBlock(basename + "_slab", () -> new SlabBlock(props));
        this.stair = addBlock(basename + "_stairs", () -> new StairBlock(
                () -> this.full.get().defaultBlockState(), props));
    }

    public DecoBlockPack addWall() {
        this.wall = addBlock(this.basename + "_wall", () ->  new WallBlock(this.props));
        return this;
    }

    public DecoBlockPack addFence(WoodType type) {
        this.fence = addBlock(this.basename + "_fence", () ->  new FenceBlock(this.props));
        this.fence_gate = addBlock(this.basename + "_fence_gate", () ->  new FenceGateBlock(this.props, type));
        return this;
    }

    public Block getBlock() {
        return this.full.get();
    }

    public Block getSlab() {
        return this.slab.get();
    }

    public Block getStairs() {
        return this.stair.get();
    }

    public Block getWall() {
        return this.wall.get();
    }

    public Block getFence() {
        return this.fence.get();
    }

    public Block getFenceGate() {
        return this.fence_gate.get();
    }

}
