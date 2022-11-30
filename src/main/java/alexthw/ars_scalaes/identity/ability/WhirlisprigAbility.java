package alexthw.ars_scalaes.identity.ability;

import alexthw.ars_scalaes.ConfigHandler;
import com.hollingsworth.arsnouveau.api.spell.EntitySpellResolver;
import com.hollingsworth.arsnouveau.api.spell.Spell;
import com.hollingsworth.arsnouveau.api.spell.SpellContext;
import com.hollingsworth.arsnouveau.api.spell.wrapped_caster.LivingCaster;
import com.hollingsworth.arsnouveau.client.particle.ParticleColor;
import com.hollingsworth.arsnouveau.common.entity.Whirlisprig;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentAOE;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectGrow;
import com.hollingsworth.arsnouveau.setup.ItemsRegistry;
import draylar.identity.ability.IdentityAbility;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;

public class WhirlisprigAbility<W extends Whirlisprig> extends IdentityAbility<W> {

    final ParticleColor color = new ParticleColor(50, 250, 55);
    final Spell spell = new Spell(EffectGrow.INSTANCE, AugmentAOE.INSTANCE, AugmentAOE.INSTANCE, AugmentAOE.INSTANCE);

    @Override
    public void onUse(Player player, W w, Level level) {
        EntitySpellResolver resolver = new EntitySpellResolver(new SpellContext(level, spell, player, new LivingCaster(player)).withColors(color));
        if (resolver.postEvent()) {
            return;
        }
        resolver.onResolveEffect(level, new BlockHitResult(player.position(), Direction.DOWN, player.blockPosition().below(), true));
    }

    @Override
    public int getCooldown(W entity) {
        return ConfigHandler.Common.WHIRLI_COOLDOWN.get();
    }

    @Override
    public Item getIcon() {
        return ItemsRegistry.WHIRLISPRIG_CHARM.asItem();
    }
}
