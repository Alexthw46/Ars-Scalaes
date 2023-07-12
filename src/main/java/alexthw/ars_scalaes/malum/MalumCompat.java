package alexthw.ars_scalaes.malum;

import com.hollingsworth.arsnouveau.api.item.ICasterTool;
import com.hollingsworth.arsnouveau.common.enchantment.EnchantmentRegistry;
import com.hollingsworth.arsnouveau.common.light.LightManager;
import com.sammy.malum.common.entity.boomerang.ScytheBoomerangEntity;
import com.sammy.malum.registry.common.entity.EntityRegistry;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.RegistryObject;

import static alexthw.ars_scalaes.registry.ModRegistry.ITEMS;
import static alexthw.ars_scalaes.registry.ModRegistry.addTabProp;
import static com.hollingsworth.arsnouveau.common.event.ReactiveEvents.castSpell;

public class MalumCompat {

    public static void postInit() {
        LightManager.register(EntityRegistry.NATURAL_SPIRIT.get(), (p) -> 8);

        LightManager.register(EntityRegistry.ETHERIC_NITRATE.get(), (p) -> 15);
        LightManager.register(EntityRegistry.VIVID_NITRATE.get(), (p) -> 15);
    }

    public static void init() {
        MinecraftForge.EVENT_BUS.register(MalumCompat.class);

        ENCHANTER_SCYTHE = ITEMS.register("enchanter_scythe", () -> new EnchanterScythe(Tiers.NETHERITE, -4F, 0F, 3.0F, addTabProp()));

    }

    static RegistryObject<Item> ENCHANTER_SCYTHE;


    @SubscribeEvent
    public static void workaround(LivingDamageEvent event) {
        if (event.getSource().getDirectEntity() instanceof ScytheBoomerangEntity scytheBoomerang)
            if (scytheBoomerang.getItem().getItem() instanceof ICasterTool && scytheBoomerang.getOwner() instanceof LivingEntity owner) {
                scytheBoomerang.getItem().getItem().hurtEnemy(scytheBoomerang.getItem(), event.getEntity(), owner);
            } else if (event.getSource().getEntity() instanceof LivingEntity living && scytheBoomerang.getItem().getEnchantmentLevel(EnchantmentRegistry.REACTIVE_ENCHANTMENT.get()) > 0) {
                castSpell(living, scytheBoomerang.getItem());
            }
    }
}
