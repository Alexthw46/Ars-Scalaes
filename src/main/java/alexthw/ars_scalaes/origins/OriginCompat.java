package alexthw.ars_scalaes.origins;

import alexthw.ars_scalaes.ArsNouveauRegistry;
import com.hollingsworth.arsnouveau.api.event.ManaRegenCalcEvent;
import com.hollingsworth.arsnouveau.api.event.SpellCastEvent;
import com.hollingsworth.arsnouveau.api.mana.IManaCap;
import com.hollingsworth.arsnouveau.common.items.SpellBook;
import com.hollingsworth.arsnouveau.setup.config.ServerConfig;
import com.hollingsworth.arsnouveau.setup.registry.CapabilityRegistry;
import com.hollingsworth.arsnouveau.setup.registry.ModPotions;
import io.github.edwinmindcraft.apoli.api.component.IPowerContainer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.Optional;

import static alexthw.ars_scalaes.datagen.ArsProviders.prefix;

public class OriginCompat {


    public static void init() {
        MinecraftForge.EVENT_BUS.register(OriginCompat.class);
    }

    @SubscribeEvent
    public static void negateCasting(SpellCastEvent event) {
        if (true) return; //temporarily disabled
        if (event.context.getCasterTool().getItem() instanceof SpellBook && event.getEntity() instanceof Player player && IPowerContainer.get(player).isPresent()) {
            Optional<IPowerContainer> container = IPowerContainer.get(player).resolve();
            if (container.isPresent() && container.get().hasPower(prefix("book_inept"))) {
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public static void nerfRegen(ManaRegenCalcEvent event) {
        LivingEntity entity = event.getEntity();
        Optional<IPowerContainer> container = IPowerContainer.get(entity).resolve();
        if (container.isPresent() && container.get().hasPower(prefix("source_addict_2"))) {
            //recalculate mana regen to after nerf
            double regen = event.getRegen();

            //2x boost from potions, by reapplying the effect bonus
            var effect = entity.getEffect(ModPotions.MANA_REGEN_EFFECT.get());
            if (effect != null)
                regen += ServerConfig.MANA_REGEN_POTION.get() * (1 + effect.getAmplifier());

            event.setRegen(regen);
        }
    }

    @SubscribeEvent
    public static void painToGain(LivingHurtEvent event) {
        if (event.getSource().is(ArsNouveauRegistry.FORGE_MAGIC) && event.getEntity() instanceof Player player && IPowerContainer.get(player).isPresent()) {
            Optional<IPowerContainer> container = IPowerContainer.get(player).resolve();
            if (container.isPresent() && container.get().hasPower(prefix("mana_absorber"))) {
                IManaCap mana = CapabilityRegistry.getMana(player).resolve().orElse(null);
                if (mana == null) return;
                event.setAmount(event.getAmount() * 0.5f);
                mana.addMana((int) (event.getAmount() * 25));
            }
        }
    }

}
