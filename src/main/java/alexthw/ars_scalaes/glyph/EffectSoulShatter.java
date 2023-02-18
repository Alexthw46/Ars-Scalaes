package alexthw.ars_scalaes.glyph;

import com.hollingsworth.arsnouveau.api.ANFakePlayer;
import com.hollingsworth.arsnouveau.api.spell.*;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentAmplify;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentDampen;
import com.sammy.malum.core.setup.content.DamageSourceRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.common.ForgeConfigSpec;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Set;

import static alexthw.ars_scalaes.datagen.ArsProviders.prefix;

public class EffectSoulShatter extends AbstractEffect implements IDamageEffect {

    public static final EffectSoulShatter INSTANCE = new EffectSoulShatter();

    public EffectSoulShatter() {
        super(prefix("glyph_soul_shatter"), "Soul Shatter");
    }

    @Override
    public void onResolveEntity(EntityHitResult rayTraceResult, Level world, @NotNull LivingEntity shooter, SpellStats spellStats, SpellContext spellContext, SpellResolver resolver) {
        if (!(rayTraceResult.getEntity() instanceof ItemEntity)) {
            double damage = DAMAGE.get() + AMP_VALUE.get() * spellStats.getAmpMultiplier();
            attemptDamage(world, shooter, spellStats, spellContext, resolver, rayTraceResult.getEntity(), buildDamageSource(world, shooter), (float) damage);
        }
    }

    public DamageSource buildDamageSource(Level world, LivingEntity shooter) {
        if (!(shooter instanceof Player)) shooter = ANFakePlayer.getPlayer((ServerLevel) world);
        return DamageSourceRegistry.causeSoulStrikeDamage(shooter);
    }

    public void buildConfig(ForgeConfigSpec.Builder builder) {
        super.buildConfig(builder);
        this.addDamageConfig(builder, 5.0);
        this.addAmpConfig(builder, 3.0);
    }

    @Override
    public int getDefaultManaCost() {
        return 30;
    }

    @Override
    protected void addDefaultAugmentLimits(Map<ResourceLocation, Integer> defaults) {
        defaults.put(AugmentAmplify.INSTANCE.getRegistryName(), 2);
    }

    @Override
    protected @NotNull Set<AbstractAugment> getCompatibleAugments() {
        return augmentSetOf(AugmentAmplify.INSTANCE, AugmentDampen.INSTANCE);
    }

    @Override
    public SpellTier defaultTier() {
        return SpellTier.TWO;
    }

}
