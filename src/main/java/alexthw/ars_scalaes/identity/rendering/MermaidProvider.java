package alexthw.ars_scalaes.identity.rendering;

import alexthw.ars_elemental.common.entity.MermaidEntity;
import draylar.identity.api.variant.TypeProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class MermaidProvider extends TypeProvider<MermaidEntity> {

    @Override
    public int getVariantData(MermaidEntity living) {
        return MermaidEntity.Variants.valueOf(living.getColor().toUpperCase()).ordinal();
    }

    @Override
    public MermaidEntity create(EntityType<MermaidEntity> entityType, Level level, int i) {
        MermaidEntity entity = new MermaidEntity(level, false);
        entity.setColor(MermaidEntity.Variants.values()[i].toString());
        return entity;
    }

    @Override
    public int getFallbackData() {
        return 0;
    }

    @Override
    public int getRange() {
        return 4;
    }

    @Override
    public Component modifyText(MermaidEntity mermaidEntity, MutableComponent mutableComponent) {
        return mutableComponent;
    }
}
