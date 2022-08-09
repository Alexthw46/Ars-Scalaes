package alexthw.ars_scalaes.identity;

import alexthw.ars_scalaes.identity.ability.WildenHunterAbility;
import alexthw.ars_scalaes.identity.ability.*;
import com.hollingsworth.arsnouveau.common.entity.ModEntities;
import draylar.identity.ability.AbilityRegistry;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.RegistryObject;

import static alexthw.ars_scalaes.registry.ModRegistry.EFFECTS;


public class IdentityReg {

    public static RegistryObject<MobEffect> MORPH;

    public static void preInit() {
        MORPH = EFFECTS.register("morph", MorphEffect::new);
    }


    public static void initAbilities() {
        AbilityRegistry.register(ModEntities.ENTITY_BLAZING_WEALD, new WealdWalkerAbility<>());
        AbilityRegistry.register(ModEntities.ENTITY_CASCADING_WEALD, new WealdWalkerAbility<>());
        AbilityRegistry.register(ModEntities.ENTITY_FLOURISHING_WEALD, new WealdWalkerAbility<>());
        AbilityRegistry.register(ModEntities.ENTITY_VEXING_WEALD, new WealdWalkerAbility<>());
        AbilityRegistry.register(ModEntities.WILDEN_HUNTER, new WildenHunterAbility());
        AbilityRegistry.register(ModEntities.WILDEN_STALKER, new WildenStalkerAbility());

        AbilityRegistry.register(ModEntities.STARBUNCLE_TYPE, new StarbuncleAbility<>());
        AbilityRegistry.register(ModEntities.WHIRLISPRIG_TYPE, new WhirlisprigAbility<>());
        AbilityRegistry.register(ModEntities.ENTITY_WIXIE_TYPE, new WixieAbility());

        if (ModList.get().isLoaded("ars_elemental")) ElementalModule.initAbilities();
    }


}
