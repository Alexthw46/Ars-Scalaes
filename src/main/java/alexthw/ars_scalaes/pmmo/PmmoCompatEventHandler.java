package alexthw.ars_scalaes.pmmo;

import alexthw.ars_scalaes.ConfigHandler;
import com.hollingsworth.arsnouveau.api.event.*;
import com.hollingsworth.arsnouveau.api.mana.ManaAttributes;
import com.hollingsworth.arsnouveau.api.spell.AbstractEffect;
import com.hollingsworth.arsnouveau.api.spell.AbstractSpellPart;
import com.hollingsworth.arsnouveau.api.spell.Spell;
import harmonised.pmmo.api.APIUtils;
import harmonised.pmmo.api.enums.PerkSide;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import org.apache.commons.lang3.function.TriFunction;

import java.util.UUID;

import static alexthw.ars_scalaes.datagen.ArsProviders.prefix;

public class PmmoCompatEventHandler {

    @SubscribeEvent
    public static void setupPerks(FMLCommonSetupEvent event) {
        APIUtils.registerPerk(prefix("mana_regen"), MANA_REGEN, MANA_REGEN_TERM, PerkSide.BOTH);
        APIUtils.registerPerk(prefix("mana_boost"), MANA_BOOST, MANA_BOOST_TERM, PerkSide.BOTH);
    }


    @SubscribeEvent
    public static void awardSpellCastXp(SpellCastEvent event) {
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
    public static void dmgMultiplierByLevel(SpellModifierEvent event) {
        if (event.caster instanceof Player player) {
            int magicLevel = APIUtils.getLevel("magic", player);
            double magicProficiency = magicLevel * ConfigHandler.Common.LEVEL_TO_SPELL_DMG.get();
            event.builder.addDamageModifier(magicProficiency);
        }
    }

    @SubscribeEvent
    public static void dmgReductionByLevel(SpellDamageEvent event) {

        if (event.target instanceof Player player) {
            int magicLevel = APIUtils.getLevel("magic", player);
            int enduranceLevel = APIUtils.getLevel("endurance", player);
            event.damage = (float) (event.damage * (1 - (magicLevel + enduranceLevel) * ConfigHandler.Common.LEVEL_TO_SPELL_RES.get()));
        }

    }

    @Deprecated
    public static void maxManaByLevel(MaxManaCalcEvent event) {

        if (event.getEntity() instanceof Player player) {
            int magicLevel = APIUtils.getLevel("magic", player);
            int maxMana = event.getMax();
            double manaBonus = 1.0D + magicLevel * ConfigHandler.Common.MAX_BONUS.get();
            event.setMax((int) (maxMana * manaBonus));
        }

    }

    @Deprecated
    public static void manaRegenByLevel(ManaRegenCalcEvent event) {

        if (event.getEntity() instanceof Player player) {
            double magicLevel = APIUtils.getLevel("magic", player);
            double regen = event.getRegen();
            double manaBonus = 1.0D + magicLevel * ConfigHandler.Common.REGEN_BONUS.get();
            event.setRegen(regen * manaBonus);
        }

    }

    private static final CompoundTag NONE = new CompoundTag();

    private static final UUID manaRegenModifierID = UUID.fromString("57552ec6-c5d4-4df1-987e-bd99acb41fa9");
    private static final UUID manaMaxModifierID = UUID.fromString("654dec09-e05e-4eb1-a255-7a76447322be");


    public static final TriFunction<Player, CompoundTag, Integer, CompoundTag> MANA_REGEN = (player, nbt, level) -> {
        double maxRegenBoost = nbt.contains(APIUtils.MAX_BOOST) ? nbt.getDouble(APIUtils.MAX_BOOST) : 100d;
        double boostPerLevel = nbt.contains(APIUtils.PER_LEVEL) ? nbt.getDouble(APIUtils.PER_LEVEL) : .06d;
        AttributeInstance manaAttribute = player.getAttribute(ManaAttributes.MANA_REGEN.get());

        if (manaAttribute != null) {
            int regenBoost = (int) Math.min(maxRegenBoost, level * boostPerLevel);


            if (manaAttribute.getModifier(manaRegenModifierID) == null || manaAttribute.getModifier(manaRegenModifierID).getAmount() != regenBoost) {
                AttributeModifier speedModifier = new AttributeModifier(manaRegenModifierID, "Mana Regen bonus thanks to Magic Level", regenBoost, AttributeModifier.Operation.ADDITION);
                manaAttribute.removeModifier(manaRegenModifierID);
                manaAttribute.addPermanentModifier(speedModifier);
            }

        }
        return NONE;
    };

    public static final TriFunction<Player, CompoundTag, Integer, CompoundTag> MANA_REGEN_TERM = (p, nbt, l) -> {
        AttributeInstance manaAttribute = p.getAttribute(ManaAttributes.MANA_REGEN.get());
        if (manaAttribute != null)
            manaAttribute.removeModifier(manaRegenModifierID);
        return NONE;
    };

    public static final TriFunction<Player, CompoundTag, Integer, CompoundTag> MANA_BOOST = (player, nbt, level) -> {
        double maxManaBoost = nbt.contains(APIUtils.MAX_BOOST) ? nbt.getDouble(APIUtils.MAX_BOOST) : 3000d;
        double boostPerLevel = nbt.contains(APIUtils.PER_LEVEL) ? nbt.getDouble(APIUtils.PER_LEVEL) : 3.0d;
        AttributeInstance manaAttribute = player.getAttribute(ManaAttributes.MAX_MANA.get());

        double manaBoost = Math.min(maxManaBoost, level * boostPerLevel);

        if (manaAttribute.getModifier(manaMaxModifierID) == null || manaAttribute.getModifier(manaMaxModifierID).getAmount() != manaBoost) {
            AttributeModifier manaModifier = new AttributeModifier(manaMaxModifierID, "Max Mana bonus thanks to Magic Level", manaBoost, AttributeModifier.Operation.ADDITION);
            manaAttribute.removeModifier(manaMaxModifierID);
            manaAttribute.addPermanentModifier(manaModifier);
        }

        return NONE;
    };

    public static final TriFunction<Player, CompoundTag, Integer, CompoundTag> MANA_BOOST_TERM = (p, nbt, l) -> {
        AttributeInstance manaAttribute = p.getAttribute(ManaAttributes.MAX_MANA.get());
        if (manaAttribute != null)
            manaAttribute.removeModifier(manaMaxModifierID);
        return NONE;
    };

}
