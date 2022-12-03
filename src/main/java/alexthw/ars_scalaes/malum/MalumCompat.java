package alexthw.ars_scalaes.malum;

import com.hollingsworth.arsnouveau.common.light.LightManager;
import com.sammy.malum.core.setup.content.entity.EntityRegistry;

public class MalumCompat {

    public static void init(){
        LightManager.register(EntityRegistry.NATURAL_SOUL.get(), (p) -> 8);
        LightManager.register(EntityRegistry.NATURAL_SPIRIT.get(), (p) -> 8);

        LightManager.register(EntityRegistry.ETHERIC_NITRATE.get(), (p) -> 15);
        LightManager.register(EntityRegistry.VIVID_NITRATE.get(), (p) -> 15);
    }

}
