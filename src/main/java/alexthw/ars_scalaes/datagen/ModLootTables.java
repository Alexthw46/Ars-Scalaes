package alexthw.ars_scalaes.datagen;

import alexthw.ars_scalaes.registry.ModRegistry;
import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTables;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ModLootTables extends LootTableProvider {
    private final List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootContextParamSet>> tables;

    public ModLootTables(DataGenerator dataGeneratorIn) {
        super(dataGeneratorIn);
        this.tables = ImmutableList.of(Pair.of(ModLootTables.BlockLootTable::new, LootContextParamSets.BLOCK));
    }

    protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootContextParamSet>> getTables() {
        return this.tables;
    }

    protected void validate(Map<ResourceLocation, LootTable> map, ValidationContext validationTracker) {
        map.forEach((resourceLocation, lootTable) -> LootTables.validate(validationTracker, resourceLocation, lootTable));
    }

    public @NotNull String getName() {
        return "Ars Scalaes Loot Tables";
    }

    public static class BlockLootTable extends BlockLoot {
        public final List<Block> list = new ArrayList<>();

        public BlockLootTable() {
        }

        protected void addTables() {
            Set<RegistryObject<Block>> blocks = new HashSet<>(ModRegistry.BLOCKS.getEntries());
            blocks.forEach((b) -> this.registerDropSelf(b.get()));
        }

        public void registerDropSelf(Block block) {
            this.list.add(block);
            this.dropSelf(block);
        }

        protected Iterable<Block> getKnownBlocks() {
            return this.list;
        }
    }
}