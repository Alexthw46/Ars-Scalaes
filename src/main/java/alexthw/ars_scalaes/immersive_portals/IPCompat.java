package alexthw.ars_scalaes.immersive_portals;

import com.hollingsworth.arsnouveau.common.advancement.ANCriteriaTriggers;
import com.hollingsworth.arsnouveau.common.items.WarpScroll;
import com.hollingsworth.arsnouveau.setup.BlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
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

    public static RegistryObject<Item> IMMERSIVE_WARP_SCROLL;

    public static void init() {
        MinecraftForge.EVENT_BUS.register(IPCompat.class);
        IMMERSIVE_WARP_SCROLL = ITEMS.register("immersive_warp_scroll", () -> new WarpScroll() {
            @Override
            public boolean isFoil(ItemStack stack) {
                return true;
            }

            @Override
            public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity) {
                if (entity.getCommandSenderWorld().isClientSide)
                    return false;

                String displayName = stack.hasCustomHoverName() ? stack.getHoverName().getString() : "";
                WarpScrollData data = WarpScrollData.get(stack);
                if (data.isValid()
                    && data.canTeleportWithDim(entity.getCommandSenderWorld().dimension().location().toString())
                    && BlockRegistry.PORTAL_BLOCK.trySpawnPortal(entity.getCommandSenderWorld(), entity.blockPosition(), data, displayName)) {
                    BlockPos pos = entity.blockPosition();
                    ServerLevel world = (ServerLevel) entity.getCommandSenderWorld();
                    world.sendParticles(ParticleTypes.PORTAL, pos.getX(), pos.getY() + 1.0, pos.getZ(),
                            10, (world.random.nextDouble() - 0.5D) * 2.0D, -world.random.nextDouble(), (world.random.nextDouble() - 0.5D) * 2.0D, 0.1f);
                    world.playSound(null, pos, SoundEvents.ILLUSIONER_CAST_SPELL, SoundSource.NEUTRAL, 1.0f, 1.0f);
                    ANCriteriaTriggers.rewardNearbyPlayers(ANCriteriaTriggers.CREATE_PORTAL, world, pos, 4);
                    stack.shrink(1);
                    return true;
                }
                return false;
            }
        });
    }

    public static void createPortal(Entity portal) {

        if (portal != null) {
            //get item entities around the spot and fetch the nearest warp scroll one
            portal.level.getEntities(portal, portal.getBoundingBox().inflate(4), entity -> entity instanceof ItemEntity ie && (ie.getItem().getItem() == IMMERSIVE_WARP_SCROLL.get())).stream().findFirst().ifPresent(entity -> {
                ItemStack scroll = ((ItemEntity) entity).getItem();
                WarpScroll.WarpScrollData data = WarpScroll.WarpScrollData.get(scroll);
                if (data.getPos() == null) {
                    return;
                }
                //applies the data from the scroll to the immersive portal entity
                if (portal instanceof Portal immersivePortal) {
                    if (data.canTeleportWithDim(portal.level))
                        immersivePortal.setDestinationDimension(ResourceKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(data.getDimension())));
                    immersivePortal.setDestination(new Vec3(data.getPos().getX(), data.getPos().getY(), data.getPos().getZ()));
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
