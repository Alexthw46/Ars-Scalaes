package alexthw.ars_scalaes.identity;

import alexthw.ars_scalaes.identity.alternative.Woodwalkers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.RegistryObject;

import static alexthw.ars_scalaes.registry.ModRegistry.EFFECTS;

public class MorphingAbstraction {

    public static RegistryObject<MobEffect> MORPH;

    public static void preInit() {
        MORPH = EFFECTS.register("morph", MorphEffect::new);
    }

    public static void morphInto(Level world, ServerPlayer player, LivingEntity living) {
        if (ModList.get().isLoaded("identity")) {
            IdentityReg.morphInto(world, player, living);
        } else if (ModList.get().isLoaded("walkers")) {
            Woodwalkers.morphInto(world, player, living);
        }
    }

    public static void postInit() {
        if (ModList.get().isLoaded("identity")) {
            IdentityReg.postInit();
        } else if (ModList.get().isLoaded("walkers")) {
            Woodwalkers.register();
        }
    }


}
