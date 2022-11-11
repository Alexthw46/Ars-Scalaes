package alexthw.ars_scalaes;

import com.hollingsworth.arsnouveau.api.event.SpellDamageEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ArsScalaes.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CommonEvents {

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void makeMagic(SpellDamageEvent.Pre event) {
        if (ConfigHandler.Common.ALL_MAGIC.get()) {
            event.damageSource.setMagic();
        }
    }

}
