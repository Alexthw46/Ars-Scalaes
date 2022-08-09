package alexthw.ars_scalaes.pmmo;

import com.hollingsworth.arsnouveau.api.mana.ManaAttributes;
import harmonised.pmmo.api.APIUtils;
import harmonised.pmmo.api.enums.PerkSide;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import org.apache.commons.lang3.function.TriFunction;

import java.util.UUID;

import static alexthw.ars_scalaes.datagen.ArsProviders.prefix;

public class Perk {

    public static void setupPerks() {
        APIUtils.registerPerk(prefix("mana_regen"), MANA_REGEN, MANA_REGEN_TERM, PerkSide.BOTH);
        APIUtils.registerPerk(prefix("mana_boost"), MANA_BOOST, MANA_BOOST_TERM, PerkSide.BOTH);
    }

    private static final CompoundTag NONE = new CompoundTag();

    private static final UUID manaRegenModifierID = UUID.fromString("57552ec6-c5d4-4df1-987e-bd99acb41fa9");
    private static final UUID manaMaxModifierID = UUID.fromString("654dec09-e05e-4eb1-a255-7a76447322be");

    public static final TriFunction<Player, CompoundTag, Integer, CompoundTag> MANA_REGEN = (player, nbt, level) -> {
        double maxRegenBoost = nbt.contains(APIUtils.MAX_BOOST) ? nbt.getDouble(APIUtils.MAX_BOOST) : 100d;
        double boostPerLevel = nbt.contains(APIUtils.PER_LEVEL) ? nbt.getDouble(APIUtils.PER_LEVEL) : .06d;
        AttributeInstance manaAttribute = player.getAttribute(ManaAttributes.MANA_REGEN.get());

        double regenBoost = Math.min(maxRegenBoost, level * boostPerLevel);

        if (manaAttribute != null && (manaAttribute.getModifier(manaRegenModifierID).getAmount() != regenBoost || manaAttribute.getModifier(manaRegenModifierID) == null)) {
            AttributeModifier speedModifier = new AttributeModifier(manaRegenModifierID, "Mana Regen bonus thanks to Magic Level", regenBoost, AttributeModifier.Operation.ADDITION);
            manaAttribute.removeModifier(manaRegenModifierID);
            manaAttribute.addPermanentModifier(speedModifier);
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

        int manaBoost = (int) Math.min(maxManaBoost, level * boostPerLevel);

        if (manaAttribute != null && (manaAttribute.getModifier(manaMaxModifierID) == null || manaAttribute.getModifier(manaMaxModifierID).getAmount() != manaBoost)) {
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
