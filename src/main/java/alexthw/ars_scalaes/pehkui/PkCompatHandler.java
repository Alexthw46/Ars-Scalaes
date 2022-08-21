package alexthw.ars_scalaes.pehkui;

import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.registries.RegistryObject;

import static alexthw.ars_scalaes.registry.ModRegistry.EFFECTS;

public class PkCompatHandler {

    public static RegistryObject<MobEffect> RESIZE;

    public static void init() {
        RESIZE = EFFECTS.register("resize", ResizeEffect::new);
    }

}
