package alexthw.ars_scalaes.glyph;

import com.hollingsworth.arsnouveau.api.spell.*;
import com.hollingsworth.arsnouveau.client.particle.ParticleUtil;
import com.hollingsworth.arsnouveau.common.entity.familiar.FamiliarEntity;
import draylar.identity.api.PlayerIdentity;
import draylar.identity.api.variant.IdentityType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.common.ForgeConfigSpec;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class EffectMorph extends AbstractEffect {

    public static final EffectMorph INSTANCE = new EffectMorph();
    public ForgeConfigSpec.BooleanValue isTimeLimited;

    @Override
    public boolean isRenderAsIcon() {
        return false;
    }

    public EffectMorph() {

        super("morph", "Morph Identity");
    }

    @Override
    public void onResolveEntity(EntityHitResult rayTraceResult, Level world, @NotNull LivingEntity shooter, SpellStats spellStats, SpellContext spellContext, SpellResolver resolver) {

        if (shooter instanceof ServerPlayer player && isRealPlayer(shooter) && rayTraceResult.getEntity() instanceof LivingEntity living) {
            if (living instanceof FamiliarEntity) return;
            if (living == shooter) {
                PlayerIdentity.updateIdentity(player, null, null);
                ((ServerLevel) world).sendParticles(ParticleTypes.LARGE_SMOKE, shooter.getX(), shooter.getY() + 0.5, shooter.getZ(), 30,
                        ParticleUtil.inRange(-0.1, 0.1), ParticleUtil.inRange(-0.1, 0.1), ParticleUtil.inRange(-0.1, 0.1), 0.3);
                return;
            }
            if (!(living instanceof Player) && living.getMaxHealth() < GENERIC_INT.get()) {
                IdentityType<?> type = IdentityType.from(living);
                if (type != null) {
                    if (PlayerIdentity.updateIdentity(player, type, type.create(world))) {
                        ((ServerLevel) world).sendParticles(ParticleTypes.LARGE_SMOKE, shooter.getX(), shooter.getY() + 0.5, shooter.getZ(), 30,
                                ParticleUtil.inRange(-0.1, 0.1), ParticleUtil.inRange(-0.1, 0.1), ParticleUtil.inRange(-0.1, 0.1), 0.3);
                        if (isTimeLimited.get()) applyConfigPotion(living, IdentityReg.MORPH.get(), spellStats, false);
                    }
                }
            }
        }

    }

    @Override
    public void buildConfig(ForgeConfigSpec.Builder builder) {
        super.buildConfig(builder);
        isTimeLimited = builder.comment("Enable a timer on the resize effects. Caster will return to original self when potion effect is removed.").define("limitedMorphTime", false);
        addPotionConfig(builder, 120);
        addExtendTimeConfig(builder, 60);
        GENERIC_INT = builder.comment("Morph will only allow you to transform is the target have less maximum hp than this value.").defineInRange("max_hp_morph", 100, 20, Integer.MAX_VALUE);
    }

    @Override
    public int getDefaultManaCost() {
        return 200;
    }

    @NotNull
    @Override
    public Set<AbstractAugment> getCompatibleAugments() {
        return getSummonAugments();
    }

    @Override
    public SpellTier getTier() {
        return SpellTier.TWO;
    }
}
