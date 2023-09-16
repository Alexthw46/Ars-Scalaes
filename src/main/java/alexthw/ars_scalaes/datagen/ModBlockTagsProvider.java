package alexthw.ars_scalaes.datagen;

import alexthw.ars_scalaes.ArsScalaes;
import com.hollingsworth.arsnouveau.common.datagen.BlockTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;

import static alexthw.ars_scalaes.registry.ModRegistry.BLOCKS;

public class ModBlockTagsProvider extends BlockTagsProvider {
    public ModBlockTagsProvider(DataGenerator generatorIn, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper existingFileHelper) {
        super(generatorIn.getPackOutput(), lookupProvider ,ArsScalaes.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
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
