package alexthw.ars_scalaes.identity;

import alexthw.ars_elemental.registry.ModEntities;
import alexthw.ars_scalaes.identity.ability.FirenandoAbility;
import alexthw.ars_scalaes.identity.ability.WealdWalkerAbility;
import draylar.identity.ability.AbilityRegistry;

public class ElementalModule {
    public static void initAbilities() {
        AbilityRegistry.register(ModEntities.FLASHING_WEALD_WALKER.get(), new WealdWalkerAbility<>());
        AbilityRegistry.register(ModEntities.FIRENANDO_ENTITY.get(), new FirenandoAbility());
    }

}
