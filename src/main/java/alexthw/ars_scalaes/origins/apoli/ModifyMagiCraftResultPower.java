package alexthw.ars_scalaes.origins.apoli;

import io.github.edwinmindcraft.apoli.api.component.IPowerContainer;
import io.github.edwinmindcraft.apoli.api.power.configuration.ConfiguredItemAction;
import io.github.edwinmindcraft.apoli.api.power.configuration.ConfiguredItemCondition;
import io.github.edwinmindcraft.apoli.api.power.configuration.ConfiguredPower;
import io.github.edwinmindcraft.apoli.api.power.factory.PowerFactory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.player.PlayerEvent;
import org.apache.commons.lang3.mutable.Mutable;
import org.apache.commons.lang3.mutable.MutableObject;


public class ModifyMagiCraftResultPower extends PowerFactory<ModifyCraftResultConfiguration> {
    public ModifyMagiCraftResultPower() {
        super(ModifyCraftResultConfiguration.CODEC);
    }

    public static boolean check(ConfiguredPower<ModifyCraftResultConfiguration, ModifyMagiCraftResultPower> cp, Level level, ItemStack stack) {
        return ConfiguredItemCondition.check(cp.getConfiguration().itemCondition(), level, stack);
    }

    public static ItemStack modify(Player player, ItemStack stack) {
        Mutable<ItemStack> mutable = new MutableObject<>(stack);
        IPowerContainer.getPowers(player, null).stream().filter((cp) -> check((ConfiguredPower) cp.get(), player.level(), stack)).map((cp) -> ((ModifyCraftResultConfiguration) ((ConfiguredPower<?, ?>) cp.get()).getConfiguration()).itemAction()).forEach((action) -> ConfiguredItemAction.execute(action, player.level(), mutable));
        return mutable.getValue();
    }


    public static class ModifyCraftResultEvent extends PlayerEvent {
        private ItemStack crafted;

        public ModifyCraftResultEvent(Player player, ItemStack crafted) {
            super(player);
            this.crafted = crafted;
        }

        public void setCrafted(ItemStack crafted) {
            this.crafted = crafted;
        }

        public ItemStack getCrafted() {
            return this.crafted;
        }
    }
}

