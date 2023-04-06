package alexthw.ars_scalaes.hexerei;

import net.joefoxe.hexerei.client.renderer.entity.model.BroomStickBaseModel;
import net.joefoxe.hexerei.item.custom.BroomItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import static alexthw.ars_scalaes.datagen.ArsProviders.prefix;

public class ArchwoodBroomStick extends BroomItem {
    public ArchwoodBroomStick(String woodType, Properties properties) {
        super(woodType, properties);
    }

    @OnlyIn(Dist.CLIENT)
    public void bakeModels() {
        EntityModelSet context = Minecraft.getInstance().getEntityModels();
        this.model = new HexereiModels.ArchwoodStick(context.bakeLayer(HexereiModels.ArchwoodStick.LAYER_LOCATION));
        this.outter_model = new BroomStickBaseModel(context.bakeLayer(BroomStickBaseModel.POWER_LAYER_LOCATION));
        this.texture = prefix("textures/entity/archwood_broom_stick.png");
        this.dye_texture = prefix("textures/entity/archwood_broom_stick.png");
    }

}
