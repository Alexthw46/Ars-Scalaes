package alexthw.ars_scalaes.identity;

import alexthw.ars_elemental.registry.ModEntities;
import alexthw.ars_scalaes.identity.ability.FirenandoAbility;
import alexthw.ars_scalaes.identity.ability.WealdWalkerAbility;
import alexthw.ars_scalaes.identity.rendering.FirenandoProvider;
import alexthw.ars_scalaes.identity.rendering.MermaidProvider;
import draylar.identity.ability.AbilityRegistry;
import draylar.identity.api.variant.TypeProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;

import java.util.Map;

public class ElementalModule {
    public static void initAbilities() {
        AbilityRegistry.register(ModEntities.FLASHING_WEALD_WALKER.get(), new WealdWalkerAbility<>());
        AbilityRegistry.register(ModEntities.FIRENANDO_ENTITY.get(), new FirenandoAbility());
    }

    public static void variants(Map<EntityType<? extends LivingEntity>, TypeProvider<?>> variants) {
        variants.put(ModEntities.FIRENANDO_ENTITY.get(), new FirenandoProvider());
        variants.put(ModEntities.SIREN_ENTITY.get(), new MermaidProvider());
    }

}
