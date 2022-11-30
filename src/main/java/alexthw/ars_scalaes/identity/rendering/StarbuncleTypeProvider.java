package alexthw.ars_scalaes.identity.rendering;

import com.hollingsworth.arsnouveau.common.entity.Starbuncle;
import draylar.identity.api.variant.TypeProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

import java.util.List;

public class StarbuncleTypeProvider extends TypeProvider<Starbuncle> {

    static final List<String> colorCache = List.of(Starbuncle.carbyColors);

    @Override
    public int getVariantData(Starbuncle starbuncle) {
        int isTamed = starbuncle.isTamed() ? 1 : -1;
        return isTamed * colorCache.indexOf(starbuncle.getColor(starbuncle).toLowerCase());
    }

    @Override
    public Starbuncle create(EntityType<Starbuncle> entityType, Level level, int i) {
        Starbuncle starbuncle = new Starbuncle(entityType, level);
        if (i < colorCache.size()) {
            String color = colorCache.get(Math.abs(i)).toLowerCase();
            if (i < 0) {
                starbuncle.setColor(color,starbuncle);
            } else {
                starbuncle.setTamed(true);
                starbuncle.setColor(color,starbuncle);
            }
        }
        return starbuncle;
    }

    @Override
    public int getFallbackData() {
        return 1;
    }

    @Override
    public int getRange() {
        return Starbuncle.carbyColors.length - 1;
    }

    @Override
    public Component modifyText(Starbuncle starbuncle, MutableComponent mutableComponent) {
        return mutableComponent.append(" " + starbuncle.getColor(starbuncle));
    }

}
