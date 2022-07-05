package alexthw.ars_scalaes.datagen;

import alexthw.ars_scalaes.ArsScalaes;
import com.hollingsworth.arsnouveau.common.datagen.BlockTagProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import static alexthw.ars_scalaes.registry.ModRegistry.BLOCKS;

public class ModBlockTagsProvider extends BlockTagsProvider {
    public ModBlockTagsProvider(DataGenerator generatorIn, ExistingFileHelper existingFileHelper) {
        super(generatorIn, ArsScalaes.MODID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        tag(BlockTags.SLABS).add(getModBlocks(b -> b instanceof SlabBlock));
        tag(BlockTags.STAIRS).add(getModBlocks(b -> b instanceof StairBlock));
        tag(BlockTags.WALLS).add(getModBlocks(b -> b instanceof WallBlock));

        tag(BlockTagProvider.DECORATIVE_AN).add(getModBlocks(b -> b instanceof WallBlock || b instanceof StairBlock || b instanceof SlabBlock));
    }


    @Nonnull
    private Block[] getModBlocks(Predicate<Block> predicate) {
        List<Block> ret = new ArrayList<>(Collections.emptyList());
        BLOCKS.getEntries().stream()
                .filter(b -> predicate.test(b.get())).forEach(b -> ret.add(b.get()));
        return ret.toArray(new Block[0]);
    }
}
