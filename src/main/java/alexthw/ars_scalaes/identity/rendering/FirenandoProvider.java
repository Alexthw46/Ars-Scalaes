package alexthw.ars_scalaes.identity.rendering;

import alexthw.ars_elemental.common.entity.FirenandoEntity;
import draylar.identity.api.variant.TypeProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class FirenandoProvider extends TypeProvider<FirenandoEntity> {
    @Override
    public int getVariantData(FirenandoEntity living) {
        return FirenandoEntity.Variants.valueOf(living.getColor().toUpperCase()).ordinal();
    }

    @Override
    public FirenandoEntity create(EntityType<FirenandoEntity> entityType, Level level, int i) {
        FirenandoEntity entity = new FirenandoEntity(level);
        entity.setColor(FirenandoEntity.Variants.values()[i].toString());
        return entity;
    }

    @Override
    public int getFallbackData() {
        return 0;
    }

    @Override
    public int getRange() {
        return 2;
    }

    @Override
    public Component modifyText(FirenandoEntity living, MutableComponent mutableComponent) {
        return mutableComponent;
    }
}
