package alexthw.ars_scalaes.pmmo;

import alexthw.ars_scalaes.ConfigHandler;
import com.hollingsworth.arsnouveau.api.event.SpellDamageEvent;
import com.hollingsworth.arsnouveau.api.event.SpellResolveEvent;
import com.hollingsworth.arsnouveau.api.perk.PerkAttributes;
import com.hollingsworth.arsnouveau.api.spell.AbstractAugment;
import com.hollingsworth.arsnouveau.api.spell.AbstractEffect;
import com.hollingsworth.arsnouveau.api.spell.AbstractSpellPart;
import com.hollingsworth.arsnouveau.api.spell.Spell;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectLinger;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectWall;
import harmonised.pmmo.api.APIUtils;
import harmonised.pmmo.api.enums.PerkSide;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.apache.commons.lang3.function.TriFunction;

import java.util.UUID;

import static alexthw.ars_scalaes.datagen.ArsProviders.prefix;

public class PmmoCompatEventHandler {
    public static void setupPerks() {
        CompoundTag regenDefaults = new CompoundTag();
        regenDefaults.putDouble(APIUtils.MAX_BOOST, 100d);
        regenDefaults.putDouble(APIUtils.PER_LEVEL, .5d);
        APIUtils.registerPerk(prefix("mana_regen"), regenDefaults, (player, tag, integer) -> true, MANA_REGEN, MANA_REGEN_TERM, PerkSide.BOTH);

        CompoundTag manaDefaults = new CompoundTag();
        manaDefaults.putDouble(APIUtils.MAX_BOOST, 3000d);
        manaDefaults.putDouble(APIUtils.PER_LEVEL, 5.0d);
        APIUtils.registerPerk(prefix("mana_boost"), manaDefaults, (player, tag, integer) -> true, MANA_BOOST, MANA_BOOST_TERM, PerkSide.BOTH);

        CompoundTag damageDefaults = new CompoundTag();
        damageDefaults.putDouble(APIUtils.MAX_BOOST, 0d);
        damageDefaults.putDouble(APIUtils.PER_LEVEL, 0.2d);
        APIUtils.registerPerk(prefix("spell_damage_boost"), damageDefaults, (player, tag, integer) -> true, DAMAGE_BOOST, DAMAGE_BOOST_TERM, PerkSide.BOTH);

    }

    @SubscribeEvent
    public static void awardSpellCastXp(SpellResolveEvent.Post event) {

        if (event.shooter instanceof ServerPlayer player && !event.isCanceled()) {
            Spell spell = event.spell;
            int manaCost = 0;
            boolean hasEffect = false;
            for (AbstractSpellPart spellPart : spell.recipe) {
                if (!(spellPart instanceof AbstractAugment)) {
                    if (spellPart instanceof AbstractEffect) hasEffect = true;
                    if (spellPart == EffectLinger.INSTANCE || spellPart == EffectWall.INSTANCE) break;
                    manaCost += spellPart.getCastingCost();
                }
            }
            if (hasEffect) {
                long xpAward = (long) (ConfigHandler.Common.MANA_XP.get() * manaCost);
                APIUtils.addXp("magic", player, xpAward);
            }
        }
    }

    @SubscribeEvent
    public static void dmgAdjustByLevel(SpellDamageEvent.Pre event) {
        if (event.caster instanceof Player player) {
            int magicLevel = APIUtils.getLevel("magic", player);
            float magicProficiency = (float) (magicLevel * ConfigHandler.Common.LEVEL_TO_SPELL_DMG.get());
            event.damage = event.damage * (1 + magicProficiency);
        }
        if (event.target instanceof Player player) {
            int magicLevel = APIUtils.getLevel("magic", player);
            int enduranceLevel = APIUtils.getLevel("endurance", player);
            double mitigation = 1 - (magicLevel + enduranceLevel) * ConfigHandler.Common.LEVEL_TO_SPELL_RES.get();
            event.damage = (float) Math.max(0, event.damage * mitigation);
        }
    }

    private static final CompoundTag NONE = new CompoundTag();

    private static final UUID manaRegenModifierID = UUID.fromString("57552ec6-c5d4-4df1-987e-bd99acb41fa9");
    private static final UUID manaMaxModifierID = UUID.fromString("654dec09-e05e-4eb1-a255-7a76447322be");
    private static final UUID damageModifierID = UUID.fromString("3c9f428c-d3dd-11ed-afa1-0242ac120002");


    public static final TriFunction<Player, CompoundTag, Integer, CompoundTag> MANA_REGEN = (player, nbt, level) -> {
        double maxRegenBoost = nbt.contains(APIUtils.MAX_BOOST) ? nbt.getDouble(APIUtils.MAX_BOOST) : 100d;
        double boostPerLevel = nbt.contains(APIUtils.PER_LEVEL) ? nbt.getDouble(APIUtils.PER_LEVEL) : .50d;
        AttributeInstance manaAttribute = player.getAttribute(PerkAttributes.MANA_REGEN_BONUS.get());

        double regenBoost = Math.min(maxRegenBoost, level * boostPerLevel);

        if (manaAttribute != null) {
            var modifier = manaAttribute.getModifier(manaRegenModifierID);
            if (modifier == null || modifier.getAmount() != regenBoost) {
                AttributeModifier speedModifier = new AttributeModifier(manaRegenModifierID, "Mana Regen bonus thanks to Magic Level", regenBoost, AttributeModifier.Operation.ADDITION);
                manaAttribute.removeModifier(manaRegenModifierID);
                manaAttribute.addPermanentModifier(speedModifier);
            }
        }
        return NONE;
    };

    public static final TriFunction<Player, CompoundTag, Integer, CompoundTag> MANA_REGEN_TERM = (p, nbt, l) -> {
        AttributeInstance manaAttribute = p.getAttribute(PerkAttributes.MANA_REGEN_BONUS.get());
        if (manaAttribute != null)
            manaAttribute.removeModifier(manaRegenModifierID);
        return NONE;
    };

    public static final TriFunction<Player, CompoundTag, Integer, CompoundTag> MANA_BOOST = (player, nbt, level) -> {
        double maxManaBoost = nbt.contains(APIUtils.MAX_BOOST) ? nbt.getDouble(APIUtils.MAX_BOOST) : 3000d;
        double boostPerLevel = nbt.contains(APIUtils.PER_LEVEL) ? nbt.getDouble(APIUtils.PER_LEVEL) : 3.0d;
        AttributeInstance manaAttribute = player.getAttribute(PerkAttributes.FLAT_MANA_BONUS.get());

        int manaBoost = (int) Math.min(maxManaBoost, level * boostPerLevel);

        if (manaAttribute != null) {
            var modifier = manaAttribute.getModifier(manaMaxModifierID);
            if (modifier == null || modifier.getAmount() != manaBoost) {
                AttributeModifier manaModifier = new AttributeModifier(manaMaxModifierID, "Max Mana bonus thanks to Magic Level", manaBoost, AttributeModifier.Operation.ADDITION);
                manaAttribute.removeModifier(manaMaxModifierID);
                manaAttribute.addPermanentModifier(manaModifier);
            }
        }

        return NONE;
    };

    public static final TriFunction<Player, CompoundTag, Integer, CompoundTag> MANA_BOOST_TERM = (p, nbt, l) -> {
        AttributeInstance manaAttribute = p.getAttribute(PerkAttributes.FLAT_MANA_BONUS.get());
        if (manaAttribute != null)
            manaAttribute.removeModifier(manaMaxModifierID);
        return NONE;
    };

    public static final TriFunction<Player, CompoundTag, Integer, CompoundTag> DAMAGE_BOOST = (player, nbt, level) -> {
        double maxDamageBoost = nbt.contains(APIUtils.MAX_BOOST) ? nbt.getDouble(APIUtils.MAX_BOOST) : 0d;
        double boostPerLevel = nbt.contains(APIUtils.PER_LEVEL) ? nbt.getDouble(APIUtils.PER_LEVEL) : 0.2d;
        AttributeInstance manaAttribute = player.getAttribute(PerkAttributes.SPELL_DAMAGE_BONUS.get());

        int damageBoost = (int) Math.min(maxDamageBoost, level * boostPerLevel);

        if (manaAttribute != null) {
            var modifier = manaAttribute.getModifier(damageModifierID);
            if (modifier == null || modifier.getAmount() != damageBoost) {
                AttributeModifier manaModifier = new AttributeModifier(damageModifierID, "Spell Damage bonus thanks to Magic Level", damageBoost, AttributeModifier.Operation.ADDITION);
                manaAttribute.removeModifier(manaMaxModifierID);
                manaAttribute.addPermanentModifier(manaModifier);
            }
        }

        return NONE;
    };

    public static final TriFunction<Player, CompoundTag, Integer, CompoundTag> DAMAGE_BOOST_TERM = (p, nbt, l) -> {
        AttributeInstance manaAttribute = p.getAttribute(PerkAttributes.SPELL_DAMAGE_BONUS.get());
        if (manaAttribute != null)
            manaAttribute.removeModifier(manaMaxModifierID);
        return NONE;
    };

}
