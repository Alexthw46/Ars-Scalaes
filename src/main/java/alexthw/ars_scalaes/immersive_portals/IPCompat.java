package alexthw.ars_scalaes.immersive_portals;

import com.hollingsworth.arsnouveau.common.items.WarpScroll;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.RegistryObject;
import qouteall.imm_ptl.core.portal.Portal;

import static alexthw.ars_scalaes.registry.ModRegistry.ITEMS;
import static qouteall.imm_ptl.core.commands.PortalCommand.reloadPortal;

public class IPCompat {

    public static RegistryObject<Item> IMMERSIVE_WARP_SCROLL, IMMERSIVE_STABILIZED_WARP_SCROLL;

    public static void init() {
        MinecraftForge.EVENT_BUS.register(IPCompat.class);
        IMMERSIVE_WARP_SCROLL = ITEMS.register("immersive_warp_scroll", () -> new ImmersiveWarpScroll(false));
        IMMERSIVE_STABILIZED_WARP_SCROLL = ITEMS.register("immersive_stabilized_warp_scroll", () -> new ImmersiveWarpScroll(true));

    }

    public static void createPortal(Entity portal) {

        if (portal != null) {
            //get item entities around the spot and fetch the nearest warp scroll one
            portal.level.getEntities(portal, portal.getBoundingBox().inflate(4), entity -> entity instanceof ItemEntity ie && (ie.getItem().getItem() instanceof ImmersiveWarpScroll)).stream().findFirst().ifPresent(entity -> {
                ItemStack scrollStack = ((ItemEntity) entity).getItem();
                WarpScroll.WarpScrollData data = WarpScroll.WarpScrollData.get(scrollStack);
                if (data.getPos() == null || !(scrollStack.getItem() instanceof ImmersiveWarpScroll scrollItem)) {
                    return;
                }

                //applies the data from the scroll to the immersive portal entity
                if (portal instanceof Portal immersivePortal) {
                    var portalHeight = immersivePortal.getBoundingBox().maxY - immersivePortal.getBoundingBox().minY;
                    if (scrollItem.allowCrossDim || data.canTeleportWithDim(portal.level))
                        immersivePortal.setDestinationDimension(ResourceKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(data.getDimension())));
                    immersivePortal.setDestination(new Vec3(data.getPos().getX(), data.getPos().getY() + portalHeight / 2, data.getPos().getZ()));
                    //if (data.getRotation() != null) immersivePortal.setRotationTransformation(new Quaternion(new Vector3f(0, 1, 0), data.getRotation().y, true));
                    reloadPortal(immersivePortal);
                }

            });
        }

    }

    @SubscribeEvent
    public static void registerCommands(RegisterCommandsEvent event) {
        ArsPortalCommand.register(event.getDispatcher());
    }

}
