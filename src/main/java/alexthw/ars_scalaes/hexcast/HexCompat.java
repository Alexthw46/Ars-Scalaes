package alexthw.ars_scalaes.hexcast;

import alexthw.ars_scalaes.ConfigHandler.Common;
import alexthw.ars_scalaes.registry.ModRegistry;
import at.petrak.hexcasting.api.addldata.ADMediaHolder;
import at.petrak.hexcasting.common.items.ItemStaff;
import at.petrak.hexcasting.forge.cap.HexCapabilities;
import at.petrak.hexcasting.forge.cap.adimpl.CapStaticMediaHolder;
import com.hollingsworth.arsnouveau.common.datagen.ItemTagProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.common.util.NonNullSupplier;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.BooleanSupplier;

import static alexthw.ars_scalaes.registry.ModRegistry.ITEMS;
import static at.petrak.hexcasting.forge.cap.ForgeCapabilityHandler.MEDIA_STATIC_CAP;

public class HexCompat {

    public static void init() {
        MinecraftForge.EVENT_BUS.register(HexCompat.class);

        ARCH_WAND = ITEMS.register("archwood_staff", () -> new ItemStaff(ModRegistry.addTabProp()));
    }

    static RegistryObject<Item> ARCH_WAND;

    @SubscribeEvent
    public static void attachItemCaps(AttachCapabilitiesEvent<ItemStack> evt) {
        ItemStack stack = evt.getObject();

        if (stack.is(ItemTagProvider.SOURCE_GEM_TAG)) {
            evt.addCapability(MEDIA_STATIC_CAP, provide(stack, HexCapabilities.MEDIA, () ->
                    new CapStaticMediaHolder(Common.SOURCE_GEM_MEDIA, ADMediaHolder.AMETHYST_SHARD_PRIORITY,
                            stack)));
        }if (stack.is(ItemTagProvider.SOURCE_GEM_BLOCK_TAG)) {
            evt.addCapability(MEDIA_STATIC_CAP, provide(stack, HexCapabilities.MEDIA, () ->
                    new CapStaticMediaHolder(() -> (Common.SOURCE_GEM_MEDIA.get() * 4), ADMediaHolder.CHARGED_AMETHYST_PRIORITY,
                            stack)));
        }
    }

    private static <CAP> SimpleProvider<CAP> provide(ItemStack stack, Capability<CAP> capability,
                                                     NonNullSupplier<CAP> supplier) {
        return new SimpleProvider<>(stack::isEmpty, capability, LazyOptional.of(supplier));
    }

    private record SimpleProvider<CAP>(BooleanSupplier invalidated,
                                       Capability<CAP> capability,
                                       LazyOptional<CAP> instance) implements ICapabilityProvider {

        @NotNull
        @Override
        public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
            if (invalidated.getAsBoolean()) {
                return LazyOptional.empty();
            }

            return cap == capability ? instance.cast() : LazyOptional.empty();
        }
    }

}
