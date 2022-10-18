package alexthw.ars_scalaes.pehkui;

import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.registries.RegistryObject;
import virtuoel.pehkui.api.*;

import static alexthw.ars_scalaes.datagen.ArsProviders.prefix;
import static alexthw.ars_scalaes.registry.ModRegistry.EFFECTS;

public class PkCompatHandler {

    public static RegistryObject<MobEffect> RESIZE;

    public static void init() {
        ScaleTypes.BASE.getDefaultBaseValueModifiers().add(magicMod);
        RESIZE = EFFECTS.register("resize", ResizeEffect::new);
    }


    public static final ScaleModifier magicMod = ScaleRegistries.register(ScaleRegistries.SCALE_MODIFIERS, prefix("spell_scale_modifier"), new ScaleModifier() {
        @Override
        public float modifyScale(final ScaleData scaleData, float modifiedScale, final float delta) {
            return magicScale.getScaleData(scaleData.getEntity()).getScale(delta) * modifiedScale;
        }
    });

    public static final ScaleType magicScale = ScaleRegistries.register(ScaleRegistries.SCALE_TYPES, prefix("magic_resize"), ScaleType.Builder.create().affectsDimensions().addDependentModifier(magicMod).build());

}

