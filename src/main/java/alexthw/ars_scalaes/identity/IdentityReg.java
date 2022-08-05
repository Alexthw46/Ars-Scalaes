package alexthw.ars_scalaes.identity;

import alexthw.ars_scalaes.identity.ability.StarbuncleAbility;
import alexthw.ars_scalaes.identity.ability.WealdWalkerAbility;
import alexthw.ars_scalaes.identity.ability.WhirlisprigAbility;
import alexthw.ars_scalaes.identity.ability.WixieAbility;
import com.hollingsworth.arsnouveau.common.entity.ModEntities;
import draylar.identity.ability.AbilityRegistry;
import net.minecraftforge.fml.ModList;
import draylar.identity.api.IdentityTickHandlers;
import draylar.identity.api.variant.IdentityType;
import draylar.identity.api.variant.TypeProvider;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;
import net.minecraftforge.registries.RegistryObject;

import java.util.Map;

import static alexthw.ars_scalaes.registry.ModRegistry.EFFECTS;


public class IdentityReg {

    public static RegistryObject<MobEffect> MORPH;

    public static void preInit() {
        MORPH = EFFECTS.register("morph", MorphEffect::new);
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

        if (ModList.get().isLoaded("ars_elemental")) ElementalModule.initAbilities();
    }


}
