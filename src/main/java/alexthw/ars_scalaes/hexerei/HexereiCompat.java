package alexthw.ars_scalaes.hexerei;

import net.joefoxe.hexerei.client.renderer.entity.BroomType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

import static alexthw.ars_scalaes.registry.ModRegistry.ITEMS;


public class HexereiCompat {

    public static void init() {
        MinecraftForge.EVENT_BUS.register(HexereiCompat.class);

        ARCHWOOD_BROOM = ITEMS.register("archwood_broom", () -> new ArchwoodBroomStick("archwood", new Item.Properties().stacksTo(1)));
        MAGEBLOOM_BRUSH = ITEMS.register("magebloom_brush", () -> new MagebloomBrush(new Item.Properties().durability(100)));
        WET_MAGEBLOOM_BRUSH = ITEMS.register("wet_magebloom_brush", () -> new Item(new Item.Properties()) {
            public void appendHoverText(@NotNull ItemStack stack, @Nullable Level world, @NotNull List<Component> tooltip, @NotNull TooltipFlag flagIn) {
                tooltip.add(Component.translatable("tooltip.hexerei.wet_broom_brush").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(10066329))));
                super.appendHoverText(stack, world, tooltip, flagIn);
            }
        });

    }

    @OnlyIn(Dist.CLIENT)
    public static void layerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(HexereiModels.MagebloomBrush.LAYER_LOCATION, HexereiModels.MagebloomBrush::createBodyLayerNone);
        event.registerLayerDefinition(HexereiModels.ArchwoodStick.LAYER_LOCATION, HexereiModels.ArchwoodStick::createBodyLayerNone);
    }

    public static void postInit() {
        BroomType.create("archwood", ARCHWOOD_BROOM.get(), 0.6f);
    }

    public static RegistryObject<Item> ARCHWOOD_BROOM, MAGEBLOOM_BRUSH, WET_MAGEBLOOM_BRUSH;

}
