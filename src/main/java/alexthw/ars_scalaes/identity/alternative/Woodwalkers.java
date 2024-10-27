package alexthw.ars_scalaes.identity.alternative;


import alexthw.ars_scalaes.ArsScalaes;
import alexthw.ars_scalaes.identity.alternative.rendering.ColorVariantProvider;
import alexthw.ars_scalaes.identity.alternative.rendering.StarbuncleTypeProvider;
import com.hollingsworth.arsnouveau.common.entity.EntityBookwyrm;
import com.hollingsworth.arsnouveau.common.entity.EntityDrygmy;
import com.hollingsworth.arsnouveau.common.entity.EntityWixie;
import com.hollingsworth.arsnouveau.common.entity.Whirlisprig;
import com.hollingsworth.arsnouveau.setup.registry.ModEntities;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import tocraft.walkers.api.variant.ShapeType;
import tocraft.walkers.api.variant.TypeProviderRegistry;
import tocraft.walkers.impl.PlayerDataProvider;
import tocraft.walkers.integrations.AbstractIntegration;
import tocraft.walkers.integrations.Integrations;

public class Woodwalkers extends AbstractIntegration {
    public static void morphInto(Level world, ServerPlayer player, LivingEntity living) {
        if (living == null) {
            ((PlayerDataProvider) player).walkers$updateShapes(null);
            return;
        }
        ShapeType<LivingEntity> type = ShapeType.from(living);
        if (type != null) {
            ((PlayerDataProvider) player).walkers$updateShapes(type.create(world));
        }
    }


    //register variant providers
    public void registerTypeProvider() {
        //register variant providers
        TypeProviderRegistry.register(ModEntities.STARBUNCLE_TYPE.get(), new StarbuncleTypeProvider());

        TypeProviderRegistry.register(ModEntities.WHIRLISPRIG_TYPE.get(), new ColorVariantProvider<Whirlisprig>() {
            @Override
            public int getRange() {
                return 3;
            }
        });
        TypeProviderRegistry.register(ModEntities.ENTITY_DRYGMY.get(), new ColorVariantProvider<EntityDrygmy>() {
            @Override
            public int getRange() {
                return EntityDrygmy.COLORS.length - 1;
            }
        });
        TypeProviderRegistry.register(ModEntities.ENTITY_BOOKWYRM_TYPE.get(), new ColorVariantProvider<EntityBookwyrm>() {
            @Override
            public int getRange() {
                return EntityBookwyrm.COLORS.length - 1;
            }

        });
        TypeProviderRegistry.register(ModEntities.ENTITY_WIXIE_TYPE.get(), new ColorVariantProvider<EntityWixie>() {
            @Override
            public int getRange() {
                return EntityWixie.COLORS.length - 1;
            }
        });

    }

    public static void register() {
        Integrations.register(ArsScalaes.MODID, Woodwalkers::new);
    }

}
