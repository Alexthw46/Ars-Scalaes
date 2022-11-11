package alexthw.ars_scalaes.identity;

import alexthw.ars_elemental.common.entity.FirenandoEntity;
import alexthw.ars_elemental.common.entity.MermaidEntity;
import alexthw.ars_elemental.registry.ModEntities;
import alexthw.ars_scalaes.identity.ability.FirenandoAbility;
import alexthw.ars_scalaes.identity.ability.WealdWalkerAbility;
import alexthw.ars_scalaes.identity.rendering.ColorVariantProvider;
import draylar.identity.ability.AbilityRegistry;
import draylar.identity.api.variant.TypeProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;

import java.util.Arrays;
import java.util.Map;

import static alexthw.ars_scalaes.identity.rendering.ColorVariantProvider.validColors;

public class ElementalModule {
    public static void initAbilities() {
        AbilityRegistry.register(ModEntities.FLASHING_WEALD_WALKER.get(), new WealdWalkerAbility<>());
        AbilityRegistry.register(ModEntities.FIRENANDO_ENTITY.get(), new FirenandoAbility());
    }

    public static void variants(Map<EntityType<? extends LivingEntity>, TypeProvider<?>> variants) {
        variants.put(ModEntities.FIRENANDO_ENTITY.get(), new ColorVariantProvider<FirenandoEntity>() {
            @Override
            public int getRange() {
                return 1;
            }
        });
        variants.put(ModEntities.SIREN_ENTITY.get(), new ColorVariantProvider<MermaidEntity>() {
            @Override
            public int getRange() {
                return MermaidEntity.Variants.values().length - 1;
            }
        });
    }

    static {
        validColors.put(ModEntities.FIRENANDO_ENTITY.get(), Arrays.stream(FirenandoEntity.Variants.values()).map(FirenandoEntity.Variants::toString).toList());
        validColors.put(ModEntities.SIREN_ENTITY.get(), Arrays.stream(MermaidEntity.Variants.values()).map(MermaidEntity.Variants::toString).toList());
    }

}
