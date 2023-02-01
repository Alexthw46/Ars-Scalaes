package alexthw.ars_scalaes.hexcast;

import alexthw.ars_scalaes.registry.ModRegistry;
import at.petrak.hexcasting.common.items.ItemStaff;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;

import static alexthw.ars_scalaes.registry.ModRegistry.ITEMS;

public class HexCompat {

    public static void init() {
        ARCH_WAND = ITEMS.register("archwood_staff", () -> new ItemStaff(ModRegistry.addTabProp()));
    }

    static RegistryObject<Item> ARCH_WAND;

}
