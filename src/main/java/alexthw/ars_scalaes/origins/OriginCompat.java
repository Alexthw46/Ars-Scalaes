package alexthw.ars_scalaes.origins;

import com.hollingsworth.arsnouveau.api.event.ManaRegenCalcEvent;
import com.hollingsworth.arsnouveau.api.event.SpellCastEvent;
import com.hollingsworth.arsnouveau.api.mana.IManaCap;
import com.hollingsworth.arsnouveau.api.perk.PerkAttributes;
import com.hollingsworth.arsnouveau.common.capability.CapabilityRegistry;
import com.hollingsworth.arsnouveau.common.items.SpellBook;
import com.hollingsworth.arsnouveau.common.potions.ModPotions;
import com.hollingsworth.arsnouveau.setup.config.ServerConfig;
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
            IManaCap mana = CapabilityRegistry.getMana(entity).resolve().orElse(null);
            if (mana == null) return;

            //recalculate mana regen to nerf
            double regen = ServerConfig.INIT_MANA_REGEN.get();

            if (entity.getAttribute(PerkAttributes.MANA_REGEN_BONUS.get()) != null)
                regen += entity.getAttributeValue(PerkAttributes.MANA_REGEN_BONUS.get());

            int tier = mana.getBookTier();
            double numGlyphs = mana.getGlyphBonus();
            regen += numGlyphs * ServerConfig.GLYPH_REGEN_BONUS.get();
            regen += tier * ServerConfig.TIER_REGEN_BONUS.get();

            //nerf regen from non-potion and enchantment sources, 2x boost from potions
            regen *= 0.75;
            var effect = entity.getEffect(ModPotions.MANA_REGEN_EFFECT.get());
            if (effect != null)
                regen -= ServerConfig.MANA_REGEN_POTION.get() * (1 + effect.getAmplifier());

            event.setRegen(event.getRegen() - regen);
        }
    }

    @SubscribeEvent
    public static void painToGain(LivingHurtEvent event) {
        if (event.getSource().isMagic() && event.getEntity() instanceof Player player && IPowerContainer.get(player).isPresent()) {
            Optional<IPowerContainer> container = IPowerContainer.get(player).resolve();
            if (container.isPresent() && container.get().hasPower(prefix("mana_absorber"))) {
                IManaCap mana = CapabilityRegistry.getMana(player).resolve().orElse(null);
                if (mana == null) return;
                mana.addMana((int) (event.getAmount() * 2));
            }
        }
    }

}
