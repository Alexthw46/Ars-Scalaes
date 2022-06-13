package alexthw.ars_scalaes.datagen;

import alexthw.ars_scalaes.ArsScalaes;
import alexthw.ars_scalaes.registry.ModRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashSet;
import java.util.Set;

import static alexthw.ars_scalaes.datagen.ArsProviders.prefix;
import static alexthw.ars_scalaes.datagen.Setup.takeAll;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, ArsScalaes.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        Set<RegistryObject<Item>> items = new HashSet<>(ModRegistry.ITEMS.getEntries());
        takeAll(items, i -> i.get() instanceof BlockItem bi && bi.getBlock() instanceof FenceBlock).forEach(this::fenceBlockItem);
        takeAll(items, i -> i.get() instanceof BlockItem bi && bi.getBlock() instanceof WallBlock).forEach(this::wallBlockItem);
        takeAll(items, i -> i.get() instanceof BlockItem).forEach(this::blockItem);
    }

    private void blockItem(RegistryObject<Item> i) {
        String name = ForgeRegistries.ITEMS.getKey(i.get()).getPath();
        getBuilder(name).parent(new ModelFile.UncheckedModelFile(prefix("block/" + name)));
    }

    private void fenceBlockItem(RegistryObject<Item> i) {
        String name = ForgeRegistries.ITEMS.getKey(i.get()).getPath();
        String baseName = name.substring(0, name.length() - 6);
        fenceInventory(name, prefix("block/" + baseName));
    }
    private void wallBlockItem(RegistryObject<Item> i) {
        String name = ForgeRegistries.ITEMS.getKey(i.get()).getPath();
        String baseName = name.substring(0, name.length() - 5);
        wallInventory(name, prefix("block/" + baseName));
    }



}
