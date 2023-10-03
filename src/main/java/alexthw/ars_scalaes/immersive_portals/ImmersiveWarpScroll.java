package alexthw.ars_scalaes.immersive_portals;

import com.hollingsworth.arsnouveau.common.items.ModItem;
import com.hollingsworth.arsnouveau.common.items.WarpScroll;
import com.hollingsworth.arsnouveau.setup.config.ServerConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.ItemHandlerHelper;

import javax.annotation.Nullable;
import java.util.List;

public class ImmersiveWarpScroll extends ModItem {

    final boolean allowCrossDim;

    public ImmersiveWarpScroll(boolean allowCrossDim) {
        super();
        this.allowCrossDim = allowCrossDim;
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return true;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (hand == InteractionHand.OFF_HAND)
            return new InteractionResultHolder<>(InteractionResult.SUCCESS, stack);

        if (world.isClientSide())
            return new InteractionResultHolder<>(InteractionResult.SUCCESS, stack);

        if (player.isShiftKeyDown()) {
            ItemStack newWarpStack = stack.split(1);
            WarpScroll.WarpScrollData newData = WarpScroll.WarpScrollData.get(newWarpStack);
            newData.setData(player.blockPosition(), player.getCommandSenderWorld().dimension().location().toString(), player.getRotationVector());
            if (stack.getCount() == 1) {
                stack = newWarpStack;
            } else {
                ItemHandlerHelper.giveItemToPlayer(player, newWarpStack);
            }
            player.sendSystemMessage(Component.translatable("ars_nouveau.warp_scroll.recorded"));
        }
        return new InteractionResultHolder<>(InteractionResult.SUCCESS, stack);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag p_77624_4_) {
        WarpScroll.WarpScrollData data = WarpScroll.WarpScrollData.get(stack);
        if (!data.isValid()) {
            tooltip.add(Component.translatable("ars_nouveau.warp_scroll.no_location"));
            return;
        }
        BlockPos pos = data.getPos();
        if (pos == null) return;
        tooltip.add(Component.translatable("ars_nouveau.position", pos.getX(), pos.getY(), pos.getZ()));
        if (!ServerConfig.ENABLE_WARP_PORTALS.get()) {
            tooltip.add(Component.translatable("ars_nouveau.warp_scroll.disabled_warp_portal").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
        }
    }


}

