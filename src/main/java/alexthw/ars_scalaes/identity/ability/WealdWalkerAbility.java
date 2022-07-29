package alexthw.ars_scalaes.identity.ability;

import com.hollingsworth.arsnouveau.api.ArsNouveauAPI;
import com.hollingsworth.arsnouveau.api.spell.EntitySpellResolver;
import com.hollingsworth.arsnouveau.api.spell.SpellContext;
import com.hollingsworth.arsnouveau.common.entity.EntityProjectileSpell;
import com.hollingsworth.arsnouveau.common.entity.WealdWalker;
import com.hollingsworth.arsnouveau.common.lib.RitualLib;
import draylar.identity.ability.IdentityAbility;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public class WealdWalkerAbility<E extends WealdWalker> extends IdentityAbility<E> {
    public WealdWalkerAbility() {
    }

    @Override
    public void onUse(Player player, E e, Level level) {
        EntitySpellResolver resolver = new EntitySpellResolver(new SpellContext(e.spell, player).withColors(e.color.toWrapper()));
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

    public Item getRitualItem(String id) {
        return ArsNouveauAPI.getInstance().getRitualItemMap().get(id);
    }

    @Override
    public int getCooldown(E entity) {
        return 100;
    }
}
