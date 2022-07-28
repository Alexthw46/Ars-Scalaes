package alexthw.ars_scalaes.identity.rendering;

import alexthw.ars_scalaes.identity.IVariantColorProvider;
import draylar.identity.api.variant.TypeProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.Level;

public class ColorVariantProvider<CV extends LivingEntity & IVariantColorProvider> extends TypeProvider<CV> {
    @Override
    public int getVariantData(CV cv) {
        return DyeColor.valueOf(cv.getColor().toUpperCase()).getId();
    }

    @Override
    public CV create(EntityType<CV> entityType, Level level, int i) {
        CV starbuncle = entityType.create(level);
        if (starbuncle == null) return null;
        if (i < 0) {
            starbuncle.setColor(DyeColor.byId(-i).getName().toLowerCase());
        } else {
            starbuncle.setColor(DyeColor.byId(i).getName().toLowerCase());
        }
        return starbuncle;
    }

    @Override
    public int getFallbackData() {
        return DyeColor.ORANGE.getId();
    }

    @Override
    public int getRange() {
        return DyeColor.BLACK.getId();
    }

    @Override
    public Component modifyText(CV entity, MutableComponent mutableComponent) {
        return mutableComponent.append(entity.getColor());
    }

}
