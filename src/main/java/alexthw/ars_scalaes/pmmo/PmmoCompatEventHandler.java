package alexthw.ars_scalaes.pmmo;

import alexthw.ars_scalaes.ConfigHandler;
import com.hollingsworth.arsnouveau.api.event.*;
import com.hollingsworth.arsnouveau.api.spell.AbstractEffect;
import com.hollingsworth.arsnouveau.api.spell.AbstractSpellPart;
import com.hollingsworth.arsnouveau.api.spell.Spell;
import harmonised.pmmo.api.APIUtils;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class PmmoCompatEventHandler {

    @SubscribeEvent
    public static void awardSpellCastXp(SpellCastEvent event)
    {
        LivingEntity entity = event.getEntity();
        if (entity instanceof ServerPlayer player) {

            Spell spell = event.spell;
            int manaCost = 0;
            boolean hasEffect = false;
            for (AbstractSpellPart spellpart : spell.recipe) {
                if (spellpart instanceof AbstractEffect) {
                    hasEffect = true;
                    manaCost += spellpart.getDefaultManaCost();
                }
            }
            if (hasEffect) {
                long xpAward = (long) (ConfigHandler.Common.MANA_XP.get() * manaCost);
                APIUtils.addXp("magic", player, xpAward);
            }
        }
    }


    @SubscribeEvent
    public static void dmgMultiplierByLevel(SpellModifierEvent event){
        if (event.caster instanceof Player player){
            int magicLevel = APIUtils.getLevel("magic", player);
            double magicProficiency = magicLevel * ConfigHandler.Common.LEVEL_TO_SPELL_DMG.get();
            event.builder.addDamageModifier(magicProficiency);
        }
    }

    @SubscribeEvent
    public static void dmgReductionByLevel(SpellDamageEvent event){

        if (event.target instanceof Player player) {
            int magicLevel = APIUtils.getLevel("magic", player);
            int enduranceLevel = APIUtils.getLevel("endurance", player);
            event.damage = (float) (event.damage * (1 - (magicLevel + enduranceLevel) * ConfigHandler.Common.LEVEL_TO_SPELL_RES.get()));
        }

    }

    @SubscribeEvent
    public static void maxManaByLevel(MaxManaCalcEvent event) {

        if (event.getEntity() instanceof Player player) {
            int magicLevel = APIUtils.getLevel("magic", player);
            int maxMana = event.getMax();
            double manaBonus = 1.0D + magicLevel * ConfigHandler.Common.MAX_BONUS.get();
            event.setMax((int) (maxMana * manaBonus));
        }

    }

    @SubscribeEvent
    public static void manaRegenByLevel(ManaRegenCalcEvent event) {

        if (event.getEntity() instanceof Player player) {
            double magicLevel = APIUtils.getLevel("magic", player);
            double regen = event.getRegen();
            double manaBonus = 1.0D + magicLevel * ConfigHandler.Common.REGEN_BONUS.get();
            event.setRegen(regen * manaBonus);
        }

    }


}
