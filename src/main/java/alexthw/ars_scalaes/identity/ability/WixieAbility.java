package alexthw.ars_scalaes.identity.ability;

import com.hollingsworth.arsnouveau.common.entity.EntityWixie;
import com.hollingsworth.arsnouveau.common.potions.ModPotions;
import com.hollingsworth.arsnouveau.setup.ItemsRegistry;
import draylar.identity.ability.IdentityAbility;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

public class WixieAbility extends IdentityAbility<EntityWixie> {

    public static ArrayList<MobEffect> goodEffectTable = new ArrayList<>(Arrays.asList(
            MobEffects.SATURATION, MobEffects.MOVEMENT_SPEED, MobEffects.DIG_SPEED, MobEffects.DAMAGE_BOOST,
            MobEffects.ABSORPTION, MobEffects.FIRE_RESISTANCE, MobEffects.REGENERATION, MobEffects.DOLPHINS_GRACE,
            ModPotions.MANA_REGEN_EFFECT.get(), ModPotions.DEFENCE_EFFECT.get()
    ));

    public static ArrayList<MobEffect> badEffectTable = new ArrayList<>(Arrays.asList(
            MobEffects.MOVEMENT_SLOWDOWN, MobEffects.WEAKNESS, MobEffects.LEVITATION, MobEffects.POISON,
            MobEffects.CONFUSION, MobEffects.BLINDNESS, MobEffects.DARKNESS, MobEffects.DIG_SLOWDOWN, MobEffects.HARM,
            ModPotions.FREEZING_EFFECT.get(), ModPotions.BLAST_EFFECT.get(), ModPotions.HEX_EFFECT.get()
    ));

    @Override
    public void onUse(Player player, EntityWixie entityWixie, Level level) {
        MobEffect effect = (player.isCrouching() ? goodEffectTable : badEffectTable).get(player.getRandom().nextInt(badEffectTable.size()));

        ThrownPotion thrownpotion = new ThrownPotion(level, player);
        ItemStack stckToThrow = getThrownStack(effect);
        thrownpotion.setItem(stckToThrow);
        thrownpotion.shootFromRotation(player, player.getXRot(), player.getYRot(), -20.0F, 0.5F, 1.0F);
        level.addFreshEntity(thrownpotion);
    }

    private ItemStack getThrownStack(MobEffect effect) {
        ItemStack splashStack = new ItemStack(Items.SPLASH_POTION);
        PotionUtils.setPotion(splashStack, Potions.EMPTY);
        PotionUtils.setCustomEffects(splashStack, Set.of(new MobEffectInstance(effect, 200, 1)));
        return splashStack;
    }


    @Override
    public int getCooldown(EntityWixie entity) {
        return 100;
    }

    @Override
    public Item getIcon() {
        return ItemsRegistry.WIXIE_CHARM.asItem();
    }
}
