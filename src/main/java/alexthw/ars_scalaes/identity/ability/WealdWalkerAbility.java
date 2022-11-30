package alexthw.ars_scalaes.identity.ability;

import alexthw.ars_scalaes.ConfigHandler;
import com.hollingsworth.arsnouveau.ArsNouveau;
import com.hollingsworth.arsnouveau.api.ArsNouveauAPI;
import com.hollingsworth.arsnouveau.api.spell.EntitySpellResolver;
import com.hollingsworth.arsnouveau.api.spell.SpellContext;
import com.hollingsworth.arsnouveau.api.spell.wrapped_caster.LivingCaster;
import com.hollingsworth.arsnouveau.common.entity.EntityProjectileSpell;
import com.hollingsworth.arsnouveau.common.entity.WealdWalker;
import com.hollingsworth.arsnouveau.common.items.RitualTablet;
import com.hollingsworth.arsnouveau.common.lib.RitualLib;
import draylar.identity.ability.IdentityAbility;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public class WealdWalkerAbility<E extends WealdWalker> extends IdentityAbility<E> {
    public WealdWalkerAbility() {
    }

    @Override
    public void onUse(Player player, E e, Level level) {
        EntitySpellResolver resolver = new EntitySpellResolver(new SpellContext(level, e.spell, player, new LivingCaster(player)).withColors(e.color));
        if (resolver.postEvent()) {
            return;
        }
        EntityProjectileSpell projectileSpell = new EntityProjectileSpell(level, resolver);
        projectileSpell.shoot(player, player.getXRot(), player.getYRot(), 0.0F, 1.0f, 0.8f);
        level.addFreshEntity(projectileSpell);

    }

    @Override
    public Item getIcon() {
        return getRitualItem(RitualLib.AWAKENING);
    }

    public RitualTablet getRitualItem(String name) {
        return ArsNouveauAPI.getInstance().getRitualItemMap().get(new ResourceLocation(ArsNouveau.MODID, name));
    }

    @Override
    public int getCooldown(E entity) {
        return ConfigHandler.Common.WW_COOLDOWN.get();
    }
}
