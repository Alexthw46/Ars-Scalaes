package alexthw.ars_scalaes.malum;

import com.hollingsworth.arsnouveau.api.item.ICasterTool;
import com.hollingsworth.arsnouveau.api.mana.IManaDiscountEquipment;
import com.hollingsworth.arsnouveau.api.spell.*;
import com.hollingsworth.arsnouveau.api.spell.wrapped_caster.IWrappedCaster;
import com.hollingsworth.arsnouveau.api.spell.wrapped_caster.LivingCaster;
import com.hollingsworth.arsnouveau.api.spell.wrapped_caster.PlayerCaster;
import com.hollingsworth.arsnouveau.common.perk.RepairingPerk;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentAmplify;
import com.hollingsworth.arsnouveau.common.spell.method.MethodTouch;
import com.hollingsworth.arsnouveau.common.util.PortUtil;
import com.sammy.malum.common.item.curiosities.weapons.MagicScytheItem;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class EnchanterScythe extends MagicScytheItem implements ICasterTool, IManaDiscountEquipment {
    public EnchanterScythe(Tier tier, float attackDamageIn, float attackSpeedIn, float magicDamageIn, Properties builderIn) {
        super(tier, attackDamageIn, attackSpeedIn, magicDamageIn, new Item.Properties());
    }

    @Override
    public void inventoryTick(@NotNull ItemStack stack, @NotNull Level world, @NotNull Entity entity, int p_77663_4_, boolean p_77663_5_) {
        super.inventoryTick(stack, world, entity, p_77663_4_, p_77663_5_);
        if (entity instanceof Player player)
            RepairingPerk.attemptRepair(stack, player);
    }

    @Override
    public boolean isScribedSpellValid(ISpellCaster caster, Player player, InteractionHand hand, ItemStack stack, Spell spell) {
        return spell.recipe.stream().noneMatch(s -> s instanceof AbstractCastMethod);
    }

    @Override
    public void sendInvalidMessage(Player player) {
        PortUtil.sendMessageNoSpam(player, Component.translatable("ars_nouveau.sword.invalid"));
    }

    @Override
    public boolean setSpell(ISpellCaster caster, Player player, InteractionHand hand, ItemStack stack, Spell spell) {
        ArrayList<AbstractSpellPart> recipe = new ArrayList<>();
        recipe.add(MethodTouch.INSTANCE);
        recipe.addAll(spell.recipe);
        recipe.add(AugmentAmplify.INSTANCE);
        spell.recipe = recipe;
        return ICasterTool.super.setSpell(caster, player, hand, stack, spell);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity entity) {
        ISpellCaster caster = getSpellCaster(stack);
        IWrappedCaster wrappedCaster = entity instanceof Player player ? new PlayerCaster(player) : new LivingCaster(entity);
        SpellContext context = new SpellContext(entity.level(), caster.modifySpellBeforeCasting(target.level(), entity, InteractionHand.MAIN_HAND, caster.getSpell()), entity, wrappedCaster);
        SpellResolver resolver = entity instanceof Player ? new SpellResolver(context) : new EntitySpellResolver(context);
        EntityHitResult entityRes = new EntityHitResult(target);
        resolver.onCastOnEntity(stack, entityRes.getEntity(), InteractionHand.MAIN_HAND);
        return super.hurtEnemy(stack, target, entity);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level worldIn, @NotNull List<Component> tooltip2, @NotNull TooltipFlag flagIn) {
        getInformation(stack, worldIn, tooltip2, flagIn);
        super.appendHoverText(stack, worldIn, tooltip2, flagIn);
    }

    @Override
    public int getManaDiscount(ItemStack i) {
       return AugmentAmplify.INSTANCE.getCastingCost();
    }
}
