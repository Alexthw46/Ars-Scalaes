package alexthw.ars_scalaes.identity;

import alexthw.ars_scalaes.identity.ability.StarbuncleAbility;
import alexthw.ars_scalaes.identity.ability.WealdWalkerAbility;
import alexthw.ars_scalaes.identity.ability.WhirlisprigAbility;
import alexthw.ars_scalaes.identity.ability.WixieAbility;
import com.hollingsworth.arsnouveau.common.entity.ModEntities;
import draylar.identity.ability.AbilityRegistry;
import net.minecraftforge.fml.ModList;


public class IdentityReg {

    public static void init() {
        AbilityRegistry.register(ModEntities.ENTITY_BLAZING_WEALD, new WealdWalkerAbility<>());
        AbilityRegistry.register(ModEntities.ENTITY_CASCADING_WEALD, new WealdWalkerAbility<>());
        AbilityRegistry.register(ModEntities.ENTITY_FLOURISHING_WEALD, new WealdWalkerAbility<>());
        AbilityRegistry.register(ModEntities.ENTITY_VEXING_WEALD, new WealdWalkerAbility<>());

        AbilityRegistry.register(ModEntities.STARBUNCLE_TYPE, new StarbuncleAbility<>());
        AbilityRegistry.register(ModEntities.WHIRLISPRIG_TYPE, new WhirlisprigAbility<>());
        AbilityRegistry.register(ModEntities.ENTITY_WIXIE_TYPE, new WixieAbility());

        if (ModList.get().isLoaded("ars_elemental")) ElementalModule.initAbilities();
    }


}
