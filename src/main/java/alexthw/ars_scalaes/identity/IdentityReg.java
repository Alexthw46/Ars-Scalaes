package alexthw.ars_scalaes.identity;

import alexthw.ars_scalaes.identity.ability.*;
import alexthw.ars_scalaes.identity.rendering.ColorVariantProvider;
import alexthw.ars_scalaes.identity.rendering.StarbuncleTypeProvider;
import alexthw.ars_scalaes.identity.tick_handlers.StalkerTickHandler;
import alexthw.ars_scalaes.identity.tick_handlers.WhirlSprigTickHandler;
import com.hollingsworth.arsnouveau.common.entity.*;
import com.hollingsworth.arsnouveau.setup.registry.ModEntities;
import draylar.identity.ability.AbilityRegistry;
import draylar.identity.api.IdentityTickHandlers;
import draylar.identity.api.PlayerIdentity;
import draylar.identity.api.variant.IdentityType;
import draylar.identity.api.variant.TypeProvider;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;
import net.minecraftforge.registries.RegistryObject;

import java.util.Map;

import static alexthw.ars_scalaes.registry.ModRegistry.EFFECTS;


public class IdentityReg {

    public static RegistryObject<MobEffect> MORPH;

    public static void preInit() {
        MinecraftForge.EVENT_BUS.register(IdentityReg.class);
        MORPH = EFFECTS.register("morph", MorphEffect::new);
    }

    public static void postInit() {
        Map<EntityType<? extends LivingEntity>, TypeProvider<?>> variants = ObfuscationReflectionHelper.getPrivateValue(IdentityType.class, new IdentityType<>(null, 0), "VARIANT_BY_TYPE");
        initAbilities();
        if (variants != null) initVariants(variants);
    }

    private static void initVariants(Map<EntityType<? extends LivingEntity>, TypeProvider<?>> variants) {

        variants.put(ModEntities.STARBUNCLE_TYPE.get(), new StarbuncleTypeProvider());

        variants.put(ModEntities.WHIRLISPRIG_TYPE.get(), new ColorVariantProvider<Whirlisprig>() {
            @Override
            public int getRange() {
                return 3;
            }
        });
        variants.put(ModEntities.ENTITY_DRYGMY.get(), new ColorVariantProvider<EntityDrygmy>() {
            @Override
            public int getRange() {
                return EntityDrygmy.COLORS.length - 1;
            }
        });
        variants.put(ModEntities.ENTITY_BOOKWYRM_TYPE.get(), new ColorVariantProvider<EntityBookwyrm>() {
            @Override
            public int getRange() {
                return EntityBookwyrm.COLORS.length - 1;
            }

        });
        variants.put(ModEntities.ENTITY_WIXIE_TYPE.get(), new ColorVariantProvider<EntityWixie>() {
            @Override
            public int getRange() {
                return EntityWixie.COLORS.length - 1;
            }
        });

        if (ModList.get().isLoaded("ars_elemental")) ElementalModule.variants(variants);

    }


    public static void initAbilities() {
        AbilityRegistry.register(ModEntities.ENTITY_BLAZING_WEALD.get(), new WealdWalkerAbility<>());
        AbilityRegistry.register(ModEntities.ENTITY_CASCADING_WEALD.get(), new WealdWalkerAbility<>());
        AbilityRegistry.register(ModEntities.ENTITY_FLOURISHING_WEALD.get(), new WealdWalkerAbility<>());
        AbilityRegistry.register(ModEntities.ENTITY_VEXING_WEALD.get(), new WealdWalkerAbility<>());
        AbilityRegistry.register(ModEntities.WILDEN_HUNTER.get(), new WildenHunterAbility());
        AbilityRegistry.register(ModEntities.WILDEN_STALKER.get(), new WildenStalkerAbility());

        AbilityRegistry.register(ModEntities.STARBUNCLE_TYPE.get(), new StarbuncleAbility<>());
        AbilityRegistry.register(ModEntities.WHIRLISPRIG_TYPE.get(), new WhirlisprigAbility<>());
        AbilityRegistry.register(ModEntities.ENTITY_WIXIE_TYPE.get(), new WixieAbility());

        IdentityTickHandlers.register(ModEntities.WHIRLISPRIG_TYPE.get(), new WhirlSprigTickHandler());
        IdentityTickHandlers.register(ModEntities.WILDEN_STALKER.get(), new StalkerTickHandler());

        if (ModList.get().isLoaded("ars_elemental")) ElementalModule.initAbilities();
    }

    @SubscribeEvent
    public static void damageImmunity(LivingAttackEvent event) {
        if (event.getEntity() instanceof Player p && event.getSource().is(DamageTypes.SWEET_BERRY_BUSH)) {
            if (PlayerIdentity.getIdentity(p) instanceof Starbuncle) event.setCanceled(true);
        }
    }


}
