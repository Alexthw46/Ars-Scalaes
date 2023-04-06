package alexthw.ars_scalaes.hexerei;

import com.hollingsworth.arsnouveau.api.RegistryHelper;
import com.hollingsworth.arsnouveau.common.light.LightManager;
import net.joefoxe.hexerei.client.renderer.entity.BroomType;
import net.joefoxe.hexerei.client.renderer.entity.ModEntityTypes;
import net.joefoxe.hexerei.client.renderer.entity.custom.BroomEntity;
import net.joefoxe.hexerei.item.custom.BroomBrushItem;
import net.joefoxe.hexerei.item.custom.KeychainItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.registries.RegistryObject;

import static alexthw.ars_scalaes.registry.ModRegistry.ITEMS;
import static alexthw.ars_scalaes.registry.ModRegistry.addTabProp;
import static com.hollingsworth.arsnouveau.setup.Config.ITEM_LIGHTMAP;

public class HexereiCompat {

    public static void init() {
        MinecraftForge.EVENT_BUS.register(HexereiCompat.class);

        ARCHWOOD_BROOM = ITEMS.register("archwood_broom", () -> new ArchwoodBroomStick("archwood", addTabProp().stacksTo(1)));
        MAGEBLOOM_BRUSH = ITEMS.register("magebloom_brush", () -> new MagebloomBrush(addTabProp().durability(100)));

    }

    @OnlyIn(Dist.CLIENT)
    public static void layerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(HexereiModels.MagebloomBrush.LAYER_LOCATION, HexereiModels.MagebloomBrush::createBodyLayerNone);
        event.registerLayerDefinition(HexereiModels.ArchwoodStick.LAYER_LOCATION, HexereiModels.ArchwoodStick::createBodyLayerNone);
    }

    public static void postInit() {
        BroomType.create("archwood", ARCHWOOD_BROOM.get(), 0.6f);

        LightManager.register(ModEntityTypes.BROOM.get(), (broom -> {
            if (broom.getModule(BroomEntity.BroomSlot.BRUSH).getItem() instanceof BroomBrushItem brush && brush.shouldGlow(broom.level, broom.getModule(BroomEntity.BroomSlot.BRUSH))) {
                    return 9;
            }
            if (broom.getModule(BroomEntity.BroomSlot.MISC).getItem() instanceof KeychainItem) {
                ItemStack keychain = broom.getModule(BroomEntity.BroomSlot.MISC);
                if (keychain.hasTag()) {
                    CompoundTag tag2 = keychain.getOrCreateTag();
                    if (tag2.contains("Items")) {
                        ListTag list = tag2.getList("Items", 10);
                        ResourceLocation other = RegistryHelper.getRegistryName(ItemStack.of(list.getCompound(0)).getItem());
                        return ITEM_LIGHTMAP.getOrDefault(other, 0);
                    }
                }
            }
            return 0;
        }));
    }

    public static RegistryObject<Item> ARCHWOOD_BROOM;
    public static RegistryObject<Item> MAGEBLOOM_BRUSH;

}
