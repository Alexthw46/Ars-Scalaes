package alexthw.ars_scalaes.identity.rendering;

import com.hollingsworth.arsnouveau.api.client.IVariantColorProvider;
import com.hollingsworth.arsnouveau.common.entity.EntityBookwyrm;
import com.hollingsworth.arsnouveau.common.entity.EntityDrygmy;
import com.hollingsworth.arsnouveau.common.entity.EntityWixie;
import com.hollingsworth.arsnouveau.common.entity.ModEntities;
import draylar.identity.api.variant.TypeProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unchecked")
public class ColorVariantProvider<CV extends LivingEntity & IVariantColorProvider> extends TypeProvider<CV> {
    @Override
    public int getVariantData(CV cv) {
        List<String> colors = validColors.getOrDefault(cv.getType(), null);
        if (colors != null) {
            return colors.indexOf(cv.getColor(cv).toLowerCase());
        }
        return -1;
    }

    @Override
    public CV create(EntityType<CV> entityType, Level level, int i) {
        CV summon = entityType.create(level);
        if (summon == null) return null;
        if (i >= 0) {
            List<String> colors = validColors.getOrDefault(entityType, null);
            if (colors != null && i < colors.size())
                summon.setColor(colors.get(i), summon);
        }
        return summon;
    }

    @Override
    public int getFallbackData() {
        return 0;
    }

    @Override
    public int getRange() {
        return 16;
    }

    @Override
    public Component modifyText(CV entity, MutableComponent mutableComponent) {
        return mutableComponent.append(" " + entity.getColor(entity));
    }

    public static final Map<EntityType<?>, List<String>> validColors = new HashMap<>();

    static {
        validColors.put(ModEntities.ENTITY_WIXIE_TYPE.get(), List.of(EntityWixie.COLORS));
        validColors.put(ModEntities.ENTITY_DRYGMY.get(), List.of(EntityDrygmy.COLORS));
        validColors.put(ModEntities.ENTITY_BOOKWYRM_TYPE.get(), List.of(EntityBookwyrm.COLORS));
        validColors.put(ModEntities.WHIRLISPRIG_TYPE.get(), List.of(new String[]{"summer", "winter", "autumn", "spring"}));
    }

}
