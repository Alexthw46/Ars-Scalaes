package alexthw.ars_scalaes.identity.ability;

import alexthw.ars_scalaes.ConfigHandler;
import com.hollingsworth.arsnouveau.api.spell.Spell;
import com.hollingsworth.arsnouveau.api.spell.SpellContext;
import com.hollingsworth.arsnouveau.api.spell.SpellResolver;
import com.hollingsworth.arsnouveau.api.spell.wrapped_caster.LivingCaster;
import com.hollingsworth.arsnouveau.common.entity.WildenHunter;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentExtendTime;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectSummonWolves;
import com.hollingsworth.arsnouveau.setup.registry.ItemsRegistry;
import com.hollingsworth.arsnouveau.setup.registry.ModPotions;
import draylar.identity.ability.IdentityAbility;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;

public class WildenHunterAbility extends IdentityAbility<WildenHunter> {

    static final Spell summon = new Spell(EffectSummonWolves.INSTANCE, AugmentExtendTime.INSTANCE);

    @Override
    public void onUse(Player player, WildenHunter wildenHunter, Level level) {

        level.playSound(null, player.blockPosition(), SoundEvents.WOLF_HOWL, SoundSource.HOSTILE, 1.0f, 0.3f);

        SpellResolver resolver = new SpellResolver(new SpellContext(level, summon, player, new LivingCaster(player)));
        if (!resolver.postEvent()) {
            resolver.onResolveEffect(level, new EntityHitResult(player));
            player.removeEffect(ModPotions.SUMMONING_SICKNESS_EFFECT.get());
        }
    }

    @Override
    public Item getIcon() {
        return ItemsRegistry.WILDEN_HORN.asItem();
    }

    @Override
    public int getCooldown(WildenHunter entity) {
        return ConfigHandler.Common.WIL_HUNTER_COOLDOWN.get();
    }
}
