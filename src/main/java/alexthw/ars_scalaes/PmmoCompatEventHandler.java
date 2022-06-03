package alexthw.ars_scalaes;

import com.hollingsworth.arsnouveau.api.event.*;
import com.hollingsworth.arsnouveau.api.spell.AbstractEffect;
import com.hollingsworth.arsnouveau.api.spell.AbstractSpellPart;
import com.hollingsworth.arsnouveau.api.spell.Spell;
import harmonised.pmmo.skills.Skill;
import harmonised.pmmo.util.XP;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class PmmoCompatEventHandler {

    @SubscribeEvent
    public static void awardSpellCastXp(SpellCastEvent event)
    {
        LivingEntity entity = event.getEntityLiving();
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
                double xpAward = ConfigHandler.Common.MANA_XP.get() * manaCost;
                XP.awardXp(player, Skill.MAGIC.name, null, xpAward, false, false, false);
            }
        }
    }


    @SubscribeEvent
    public static void dmgMultiplierByLevel(SpellModifierEvent event){
        if (event.caster instanceof Player player){
            int magicLevel = Skill.getLevel(Skill.MAGIC.name, player.getUUID());
            double magicProficiency = magicLevel * ConfigHandler.Common.LEVEL_TO_SPELL_DMG.get();
            event.builder.addDamageModifier(magicProficiency);
        }
    }

    @SubscribeEvent
    public static void dmgReductionByLevel(SpellDamageEvent event){
        if (event.target instanceof Player player) {
            int magicLevel = Skill.getLevel(Skill.MAGIC.name, player.getUUID());
            int enduranceLevel = Skill.getLevel(Skill.ENDURANCE.name, player.getUUID());
            event.damage = (float) (event.damage * (1 - (magicLevel + enduranceLevel) * ConfigHandler.Common.LEVEL_TO_SPELL_RES.get()));
        }
    }

    @SubscribeEvent
    public static void maxManaByLevel(MaxManaCalcEvent event)
    {
        int magicLevel = Skill.getLevel(Skill.MAGIC.name, event.getEntity().getUUID());
        int maxMana = event.getMax();
        double manaBonus = 1.0 + magicLevel * ConfigHandler.Common.MAX_BONUS.get();
        event.setMax((int)(maxMana * manaBonus));
    }
    @SubscribeEvent
    public static void manaRegenByLevel(ManaRegenCalcEvent event)
    {
        double magicLevel = Skill.getLevel(Skill.MAGIC.name, event.getEntity().getUUID());
        double regen = event.getRegen();
        double manaBonus = 1.0 + magicLevel * ConfigHandler.Common.REGEN_BONUS.get();
        event.setRegen(regen * manaBonus);
    }


}
