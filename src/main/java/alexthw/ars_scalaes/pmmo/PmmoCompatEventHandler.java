package alexthw.ars_scalaes.pmmo;

import alexthw.ars_scalaes.ConfigHandler;
import com.hollingsworth.arsnouveau.api.event.SpellDamageEvent;
import com.hollingsworth.arsnouveau.api.perk.PerkAttributes;
import com.hollingsworth.arsnouveau.api.spell.AbstractAugment;
import com.hollingsworth.arsnouveau.api.spell.AbstractEffect;
import com.hollingsworth.arsnouveau.api.spell.AbstractSpellPart;
import com.hollingsworth.arsnouveau.api.spell.Spell;
import harmonised.pmmo.api.APIUtils;
import harmonised.pmmo.api.enums.PerkSide;
import harmonised.pmmo.api.perks.Perk;
import harmonised.pmmo.util.Functions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.UUID;
import java.util.function.BiFunction;

import static alexthw.ars_scalaes.datagen.ArsProviders.prefix;

public class PmmoCompatEventHandler {

    public static void setupPerks() {

        CompoundTag regenDefaults = new CompoundTag();
        regenDefaults.putDouble(APIUtils.MAX_BOOST, 100d);
        regenDefaults.putDouble(APIUtils.PER_LEVEL, .5d);
        regenDefaults.putBoolean(APIUtils.MULTIPLICATIVE, false);
        regenDefaults.putString("skill", "magic");
        var RegenPerk = Perk.begin().addDefaults(regenDefaults).setStart(MANA_REGEN).setStop(MANA_REGEN_TERM).build();

        APIUtils.registerPerk(prefix("mana_regen"), RegenPerk, PerkSide.BOTH);

        CompoundTag manaDefaults = new CompoundTag();
        manaDefaults.putDouble(APIUtils.MAX_BOOST, 3000d);
        manaDefaults.putDouble(APIUtils.PER_LEVEL, 5.0d);
        manaDefaults.putBoolean(APIUtils.MULTIPLICATIVE, false);
        manaDefaults.putString("skill", "magic");

        var ManaPerk = Perk.begin().addDefaults(manaDefaults).setStart(MANA_BOOST).setStop(MANA_BOOST_TERM).build();
        APIUtils.registerPerk(prefix("mana_boost"), ManaPerk, PerkSide.BOTH);

        CompoundTag damageDefaults = new CompoundTag();
        damageDefaults.putDouble(APIUtils.MAX_BOOST, 0d);
        damageDefaults.putDouble(APIUtils.PER_LEVEL, 0.2d);
        damageDefaults.putBoolean(APIUtils.MULTIPLICATIVE, false);
        damageDefaults.putString("skill", "magic");

        var DamagePerk = Perk.begin().addDefaults(damageDefaults).setStart(DAMAGE_BOOST).setStop(DAMAGE_BOOST_TERM).build();
        APIUtils.registerPerk(prefix("spell_damage_boost"), DamagePerk, PerkSide.BOTH);

    }


    @SubscribeEvent
    public static void awardSpellCastXp(SpellSuccessEvent event) {

        if (event.context.getUnwrappedCaster() instanceof ServerPlayer player && !event.isCanceled()) {
            Spell spell = event.spell;
            int manaCost = 0;
            boolean hasEffect = false;
            for (AbstractSpellPart spellPart : spell.recipe) {
                if (!(spellPart instanceof AbstractAugment)) {
                    if (spellPart instanceof AbstractEffect) hasEffect = true;
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


    public static final BiFunction<Player, CompoundTag, CompoundTag> MANA_REGEN = (player, nbt) -> {
        double maxRegenBoost = nbt.contains(APIUtils.MAX_BOOST) ? nbt.getDouble(APIUtils.MAX_BOOST) : 100d;
        double boostPerLevel = nbt.contains(APIUtils.PER_LEVEL) ? nbt.getDouble(APIUtils.PER_LEVEL) : .50d;
        AttributeInstance manaAttribute = player.getAttribute(PerkAttributes.MANA_REGEN_BONUS.get());

        int level = nbt.getInt("level");
        double regenBoost = Math.min(maxRegenBoost, level * boostPerLevel);
        AttributeModifier.Operation operation = nbt.getBoolean("multiplicative") ? AttributeModifier.Operation.MULTIPLY_BASE : AttributeModifier.Operation.ADDITION;

        if (manaAttribute != null) {
            var modifier = manaAttribute.getModifier(manaRegenModifierID);
            if (modifier == null || modifier.getAmount() != regenBoost) {
                AttributeModifier regenModifier = new AttributeModifier(manaRegenModifierID, "Mana Regen bonus thanks to Magic Level", regenBoost, operation);
                manaAttribute.removeModifier(manaRegenModifierID);
                manaAttribute.addPermanentModifier(regenModifier);
            }
        }
        return NONE;
    };

    public static final BiFunction<Player, CompoundTag, CompoundTag> MANA_REGEN_TERM = (p, nbt) -> {
        AttributeInstance manaAttribute = p.getAttribute(PerkAttributes.MANA_REGEN_BONUS.get());
        if (manaAttribute != null)
            manaAttribute.removeModifier(manaRegenModifierID);
        return NONE;
    };

    public static final BiFunction<Player, CompoundTag, CompoundTag> MANA_BOOST = (player, nbt) -> {
        double maxManaBoost = nbt.contains(APIUtils.MAX_BOOST) ? nbt.getDouble(APIUtils.MAX_BOOST) : 3000d;
        double boostPerLevel = nbt.contains(APIUtils.PER_LEVEL) ? nbt.getDouble(APIUtils.PER_LEVEL) : 3.0d;
        int level = nbt.getInt("level");

        int manaBoost = (int) Math.min(maxManaBoost, level * boostPerLevel);

        AttributeInstance manaAttribute = player.getAttribute(PerkAttributes.MAX_MANA.get());
        AttributeModifier.Operation operation = nbt.getBoolean("multiplicative") ? AttributeModifier.Operation.MULTIPLY_BASE : AttributeModifier.Operation.ADDITION;

        if (manaAttribute != null) {
            AttributeModifier manaModifier = new AttributeModifier(manaMaxModifierID, "Max Mana bonus thanks to Magic Level", manaBoost, operation);
            manaAttribute.removeModifier(manaMaxModifierID);
            manaAttribute.addPermanentModifier(manaModifier);
        }
        return NONE;
    };

    public static final BiFunction<Player, CompoundTag, CompoundTag> MANA_BOOST_TERM = (p, nbt) -> {
        AttributeInstance manaAttribute = p.getAttribute(PerkAttributes.MAX_MANA.get());
        if (manaAttribute != null)
            manaAttribute.removeModifier(manaMaxModifierID);
        return NONE;
    };

    public static final BiFunction<Player, CompoundTag, CompoundTag> DAMAGE_BOOST = (player, nbt) -> {
        double maxDamageBoost = nbt.contains(APIUtils.MAX_BOOST) ? nbt.getDouble(APIUtils.MAX_BOOST) : 0d;
        double boostPerLevel = nbt.contains(APIUtils.PER_LEVEL) ? nbt.getDouble(APIUtils.PER_LEVEL) : 0.2d;
        int level = nbt.getInt("level");

        int damageBoost = (int) Math.min(maxDamageBoost, level * boostPerLevel);
        AttributeInstance manaAttribute = player.getAttribute(PerkAttributes.SPELL_DAMAGE_BONUS.get());
        AttributeModifier.Operation operation = nbt.getBoolean("multiplicative") ? AttributeModifier.Operation.MULTIPLY_BASE : AttributeModifier.Operation.ADDITION;

        if (manaAttribute != null) {
            AttributeModifier manaModifier = new AttributeModifier(damageModifierID, "Spell Damage bonus thanks to Pmmo Magic Level", damageBoost, operation);
            manaAttribute.removeModifier(damageModifierID);
            manaAttribute.addPermanentModifier(manaModifier);
        }

        return NONE;
    };

    public static final BiFunction<Player, CompoundTag, CompoundTag> DAMAGE_BOOST_TERM = (p, nbt) -> {
        AttributeInstance manaAttribute = p.getAttribute(PerkAttributes.SPELL_DAMAGE_BONUS.get());
        if (manaAttribute != null)
            manaAttribute.removeModifier(damageModifierID);
        return NONE;
    };

}
