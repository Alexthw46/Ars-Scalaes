package alexthw.ars_scalaes.identity.ability;

import alexthw.ars_elemental.common.entity.FirenandoEntity;
import alexthw.ars_elemental.common.entity.spells.EntityHomingProjectile;
import alexthw.ars_elemental.common.glyphs.MethodHomingProjectile;
import alexthw.ars_elemental.registry.ModItems;
import com.hollingsworth.arsnouveau.api.spell.EntitySpellResolver;
import com.hollingsworth.arsnouveau.api.spell.SpellContext;
import com.hollingsworth.arsnouveau.client.particle.ParticleColor;
import draylar.identity.ability.IdentityAbility;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.function.Predicate;

public class FirenandoAbility extends IdentityAbility<FirenandoEntity> {

    private final ParticleColor color = new ParticleColor(250, 50, 15);
    private final ParticleColor colorAlt = new ParticleColor(15, 100, 200);

    @Override
    public void onUse(Player player, FirenandoEntity firenandoEntity, Level level) {
        ParticleColor spellColor = firenandoEntity.getColor().equals(FirenandoEntity.Variants.MAGMA.toString()) ? this.color : this.colorAlt;
        EntitySpellResolver resolver = new EntitySpellResolver((new SpellContext(level, firenandoEntity.spell, player)).withColors(spellColor));
        if (resolver.postEvent()) {
            return;
        }
        EntityHomingProjectile projectileSpell = new EntityHomingProjectile(level, resolver);
        List<Predicate<LivingEntity>> ignore = MethodHomingProjectile.basicIgnores(player, false, resolver.spell);
        ignore.add((entity) -> !(entity instanceof Enemy));

        projectileSpell.setColor(spellColor);
        projectileSpell.shoot(player, player.getXRot(), player.getYRot(), 0.0F, 0.8F, 0.8F);
        projectileSpell.setIgnored(ignore);
        level.addFreshEntity(projectileSpell);
    }

    @Override
    public int getCooldown(FirenandoEntity entity) {
        return 120;
    }

    @Override
    public Item getIcon() {
        return ModItems.FIRENANDO_CHARM.get();
    }
}
