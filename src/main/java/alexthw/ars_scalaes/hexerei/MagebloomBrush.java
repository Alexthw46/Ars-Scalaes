package alexthw.ars_scalaes.hexerei;

import com.hollingsworth.arsnouveau.client.particle.GlowParticleData;
import com.hollingsworth.arsnouveau.client.particle.ParticleColor;
import com.hollingsworth.arsnouveau.common.capability.CapabilityRegistry;
import net.joefoxe.hexerei.client.renderer.entity.custom.BroomEntity;
import net.joefoxe.hexerei.item.custom.BroomBrushItem;
import net.joefoxe.hexerei.particle.ModParticleTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Tuple;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import static alexthw.ars_scalaes.datagen.ArsProviders.prefix;

public class MagebloomBrush extends BroomBrushItem {
    public MagebloomBrush(Properties properties) {
        super(properties);
    }

    @Override
    public void onBrushDamage(BroomEntity broom, RandomSource random) {
        ItemStack brush = broom.getModule(BroomEntity.BroomSlot.BRUSH);
        if (broom.getFirstPassenger() instanceof Player player && !brush.isEmpty() && brush.getDamageValue() >= 2) {
            CapabilityRegistry.getMana(player).ifPresent((mana) -> {
                if (mana.getCurrentMana() >= 200) {
                    mana.removeMana(200);
                    brush.setDamageValue(brush.getDamageValue() - 2);
                }
            });
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void bakeModels() {
        EntityModelSet context = Minecraft.getInstance().getEntityModels();
        this.model = new HexereiModels.MagebloomBrush(context.bakeLayer(HexereiModels.MagebloomBrush.LAYER_LOCATION));
        this.texture = prefix("textures/entity/archwood_broom.png");
        this.dye_texture = null;
        this.list = new ArrayList<>();
        this.list.add(new Tuple<>(GlowParticleData.createData(ParticleColor.defaultParticleColor(), 0.3f, 0.6f, 40), 5));
        this.list.add(new Tuple<>(GlowParticleData.createData(ParticleColor.defaultParticleColor(), 0.2f, 0.4f, 20), 2));
        this.list.add(new Tuple<>(ModParticleTypes.BROOM.get(), 8));
        this.list.add(new Tuple<>(ModParticleTypes.BROOM_2.get(), 50));
        this.list.add(new Tuple<>(ModParticleTypes.BROOM_3.get(), 50));
        this.list.add(new Tuple<>(ModParticleTypes.BROOM_4.get(), 50));
    }

    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flagIn) {
        tooltip.add(Component.translatable("tooltip.hexerei.broom_attachments").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(10066329))));
        tooltip.add(Component.translatable("tooltip.ars_scalaes.magebloom_brush").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(10066329))));
        super.appendHoverText(stack, world, tooltip, flagIn);
    }

    @Override
    public boolean shouldGlow(@Nullable Level level, ItemStack brushStack) {
        return brushStack.getDamageValue() < 10;
    }
}
