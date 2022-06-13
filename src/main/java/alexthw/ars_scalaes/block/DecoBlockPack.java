package alexthw.ars_scalaes.block;

import com.hollingsworth.arsnouveau.setup.BlockRegistry;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import static alexthw.ars_scalaes.registry.ModRegistry.addBlock;

public class DecoBlockPack {
    DeferredRegister<Block> registry;
    public String basename;
    net.minecraft.world.level.block.state.BlockBehaviour.Properties props;
    RegistryObject<Block> full;
    RegistryObject<Block> slab;
    RegistryObject<Block> stair;
    RegistryObject<Block> wall = null;
    RegistryObject<Block> fence = null;
    RegistryObject<Block> fence_gate = null;

    public DecoBlockPack(DeferredRegister<Block> blocks, String basename, Properties props) {
        this.registry = blocks;
        this.basename = basename;
        this.props = props;
        //this.full = addBlock(basename, () -> new DecoBlock(props));
        this.slab = addBlock(basename + "_slab", () -> new SlabBlock(props));
        this.stair = addBlock(basename + "_stairs", () ->  new StairBlock(
                () -> BlockRegistry.ARCANE_STONE.defaultBlockState(), props));
    }

    public DecoBlockPack addWall() {
        this.wall = addBlock(this.basename + "_wall", () ->  new WallBlock(this.props));
        return this;
    }

    public DecoBlockPack addFence() {
        this.fence = addBlock(this.basename + "_fence", () ->  new FenceBlock(this.props));
        this.fence_gate = addBlock(this.basename + "_fence_gate", () ->  new FenceGateBlock(this.props));
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
