package alexthw.ars_scalaes.datagen;

import alexthw.ars_scalaes.registry.ModRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.BiConsumer;

public class ModLootTables extends LootTableProvider {

    public ModLootTables(DataGenerator dataGeneratorIn) {
        super(dataGeneratorIn.getPackOutput(), new HashSet<>(), List.of(new SubProviderEntry(BlockLootTable::new, LootContextParamSets.BLOCK)));
    }

    public static class BlockLootTable extends BlockLootSubProvider {
        public final List<Block> list = new ArrayList<>();

        protected BlockLootTable() {
            super(Set.of(), FeatureFlags.REGISTRY.allFlags(), new HashMap<>());
        }

        @Override
        public void generate(@NotNull BiConsumer<ResourceLocation, LootTable.Builder> p_249322_) {
            this.generate();
            Set<ResourceLocation> set = new HashSet<>();

            for (Block block : list) {
                if (block.isEnabled(this.enabledFeatures)) {
                    ResourceLocation resourcelocation = block.getLootTable();
                    if (resourcelocation != BuiltInLootTables.EMPTY && set.add(resourcelocation)) {
                        LootTable.Builder loottable$builder = this.map.remove(resourcelocation);
                        if (loottable$builder == null) {
                            continue;
                        }

                        p_249322_.accept(resourcelocation, loottable$builder);
                    }
                }
            }
        }

        @Override
        protected void generate() {
            Set<RegistryObject<Block>> blocks = new HashSet<>(ModRegistry.BLOCKS.getEntries());
            blocks.forEach((b) -> this.registerDropSelf(b.get()));
        }

        public void registerDropSelf(Block block) {
            this.list.add(block);
            this.dropSelf(block);
        }

        protected @NotNull Iterable<Block> getKnownBlocks() {
            return this.list;
        }
    }
}