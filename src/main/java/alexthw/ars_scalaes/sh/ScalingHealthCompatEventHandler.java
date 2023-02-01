package alexthw.ars_scalaes.sh;

import alexthw.ars_scalaes.ConfigHandler;
import com.hollingsworth.arsnouveau.api.event.SpellModifierEvent;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.silentchaos512.scalinghealth.utils.config.SHPlayers;

public class ScalingHealthCompatEventHandler {

    @SubscribeEvent
    public static void scalingSpellDamage(SpellModifierEvent event){

        if (event.caster instanceof Player player) {
            if (SHPlayers.getPlayerData(player) == null) return;
            int magicLevel = SHPlayers.getPlayerData(player).getPowerCrystals();
            double magicProficiency = magicLevel * ConfigHandler.Common.SCALING_SPELL_DMG.get();
            event.builder.addDamageModifier(magicProficiency);
        }

    }

}
