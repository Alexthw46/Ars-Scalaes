package alexthw.ars_scalaes.origins.apoli;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.edwinmindcraft.apoli.api.IDynamicFeatureConfiguration;
import io.github.edwinmindcraft.apoli.api.power.configuration.ConfiguredItemAction;
import io.github.edwinmindcraft.apoli.api.power.configuration.ConfiguredItemCondition;
import net.minecraft.core.Holder;

public record ModifyCraftResultConfiguration(Holder<ConfiguredItemCondition<?, ?>> itemCondition,
                                             Holder<ConfiguredItemAction<?, ?>> itemAction)
        implements IDynamicFeatureConfiguration {

    public static final Codec<ModifyCraftResultConfiguration> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            ConfiguredItemCondition.optional("item_condition").forGetter(ModifyCraftResultConfiguration::itemCondition),
            ConfiguredItemAction.optional("item_action").forGetter(ModifyCraftResultConfiguration::itemAction)
    ).apply(instance, ModifyCraftResultConfiguration::new));

}