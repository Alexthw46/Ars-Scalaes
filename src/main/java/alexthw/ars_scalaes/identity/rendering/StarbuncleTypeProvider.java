package alexthw.ars_scalaes.identity.rendering;

import com.hollingsworth.arsnouveau.common.entity.Starbuncle;
import draylar.identity.api.variant.TypeProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.Level;

public class StarbuncleTypeProvider extends TypeProvider<Starbuncle> {

    @Override
    public int getVariantData(Starbuncle starbuncle) {
        int isTamed = starbuncle.isTamed() ? 1 : -1;
        return isTamed * DyeColor.valueOf(starbuncle.getColor().toUpperCase()).getId();
    }

    @Override
    public Starbuncle create(EntityType<Starbuncle> entityType, Level level, int i) {
        Starbuncle starbuncle = new Starbuncle(entityType, level);
        if (i < 0) {
            starbuncle.setColor(DyeColor.byId(-i).getName().toLowerCase());
        } else {
            starbuncle.setTamed(true);
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
    public Component modifyText(Starbuncle starbuncle, MutableComponent mutableComponent) {
        return mutableComponent.append(starbuncle.getColor());
    }
}
