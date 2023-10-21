package alexthw.ars_scalaes.immersive_portals;

import com.hollingsworth.arsnouveau.common.items.WarpScroll;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
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
                    var portalX = immersivePortal.getBoundingBox().maxX - immersivePortal.getBoundingBox().minX;
                    var portalZ = immersivePortal.getBoundingBox().maxZ - immersivePortal.getBoundingBox().minZ;
                    float destX = data.getPos().getX();
                    float destY = data.getPos().getY();
                    float destZ = data.getPos().getZ();
                    float rotation = Math.round(data.getRotation().y / 90);
                    if (portalX < portalZ)
                        rotation += 1;
                    if (portalHeight >= 1) {
                        destY += (float) (portalHeight / 2);
                    } else {
                        destY += 0.5F;
                    }
                    if (portalX < 1 || portalX % 2 == 1) {
                        destX += 0.5F;
                    }
                    if (portalZ < 1 || portalZ % 2 == 1) {
                        destZ += 0.5F;
                    }
                    if (Math.abs(rotation % 2) == 1 && (portalX % 2 == 0 || portalZ % 2 == 0)) {
                        var trigo = ((rotation - 2) % 4 + 2 == 1);
                        if (trigo && portalZ >= 1.0) {
                            destX += 0.5F;
                            destZ += 0.5F;
                        } else if (trigo && portalX >= 1.0) {
                            destX += 0.5F;
                            destZ -= 0.5F;
                        } else if (!trigo && portalZ >= 1.0) {
                            destX -= 0.5F;
                            destZ += 0.5F;
                        } else if (!trigo && portalX >= 1.0) {
                            destX += 0.5F;
                            destZ += 0.5F;
                        }
                    }
                    if (scrollItem.allowCrossDim || data.canTeleportWithDim(portal.level))
                        immersivePortal.setDestinationDimension(ResourceKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(data.getDimension())));
                    immersivePortal.setDestination(new Vec3(destX, destY, destZ));
                    immersivePortal.setRotationTransformation(new Quaternion(new Vector3f(0, 1, 0), rotation * 90, true));
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
