package alexthw.ars_scalaes.identity.ability;

import com.hollingsworth.arsnouveau.api.spell.Spell;
import com.hollingsworth.arsnouveau.api.spell.SpellContext;
import com.hollingsworth.arsnouveau.api.spell.SpellResolver;
import com.hollingsworth.arsnouveau.api.spell.wrapped_caster.LivingCaster;
import com.hollingsworth.arsnouveau.common.entity.WildenStalker;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentDurationDown;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectGlide;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectLaunch;
import com.hollingsworth.arsnouveau.common.spell.method.MethodSelf;
import com.hollingsworth.arsnouveau.setup.ItemsRegistry;
import draylar.identity.ability.IdentityAbility;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;

public class WildenStalkerAbility extends IdentityAbility<WildenStalker> {

    static final Spell summon = new Spell().add(MethodSelf.INSTANCE)
            .add(EffectLaunch.INSTANCE, 2)
            .add(EffectGlide.INSTANCE)
            .add(AugmentDurationDown.INSTANCE, 1);

    @Override
    public void onUse(Player player, WildenStalker wildenHunter, Level level) {

        level.playSound(null, player.blockPosition(), SoundEvents.BAT_TAKEOFF, SoundSource.HOSTILE, 1.0f, 0.3f);

        SpellResolver resolver = new SpellResolver(new SpellContext(level, summon, player, new LivingCaster(player)));
        if (!resolver.postEvent()) {
            resolver.onResolveEffect(level, new EntityHitResult(player));
        }

    }

    @Override
    public Item getIcon() {
        return ItemsRegistry.WILDEN_WING.asItem();
    }

    @Override
    public int getCooldown(WildenStalker entity) {
        return 1300;
    }

}