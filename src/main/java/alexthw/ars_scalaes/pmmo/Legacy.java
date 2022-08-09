package alexthw.ars_scalaes.pmmo;

import alexthw.ars_scalaes.ConfigHandler;
import com.hollingsworth.arsnouveau.api.event.ManaRegenCalcEvent;
import com.hollingsworth.arsnouveau.api.event.MaxManaCalcEvent;
import harmonised.pmmo.api.APIUtils;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class Legacy {

    @SubscribeEvent
    public static void maxManaByLevel(MaxManaCalcEvent event) {

        if (event.getEntityLiving() instanceof Player player) {
            int magicLevel = APIUtils.getLevel("magic", player);
            int maxMana = event.getMax();
            double manaBonus = 1.0D + magicLevel * ConfigHandler.Common.MAX_BONUS.get();
            event.setMax((int) (maxMana * manaBonus));
        }

    }

    @SubscribeEvent
    public static void manaRegenByLevel(ManaRegenCalcEvent event) {

        if (event.getEntityLiving() instanceof Player player) {
            double magicLevel = APIUtils.getLevel("magic", player);
            double regen = event.getRegen();
            double manaBonus = 1.0D + magicLevel * ConfigHandler.Common.REGEN_BONUS.get();
            event.setRegen(regen * manaBonus);
        }

    }

}
