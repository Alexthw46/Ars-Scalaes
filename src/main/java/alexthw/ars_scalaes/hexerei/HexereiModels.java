package alexthw.ars_scalaes.hexerei;

import net.joefoxe.hexerei.client.renderer.entity.model.BroomStickBaseModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;

import static alexthw.ars_scalaes.datagen.ArsProviders.prefix;

public class HexereiModels {

    static class ArchwoodStick extends BroomStickBaseModel {

        public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(prefix("archwood_broom_stick"), "main");
        public static final ModelLayerLocation POWER_LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("hexerei", "broom_power_stick_base"), "main");

        public ArchwoodStick(ModelPart root) {
            super(root);
        }

        public static LayerDefinition createBodyLayerNone() {
            return createBodyLayer(CubeDeformation.NONE);
        }

        public static LayerDefinition createBodyLayerEnlarge() {
            return createBodyLayer(new CubeDeformation(0.2F));
        }

        public static LayerDefinition createBodyLayer(CubeDeformation cube) {
            MeshDefinition meshdefinition = new MeshDefinition();
            PartDefinition partdefinition = meshdefinition.getRoot();
            PartDefinition Broom = partdefinition.addOrReplaceChild("Broom", CubeListBuilder.create().texOffs(0, 0).addBox(-11.2131F, -3.9639F, -0.8505F, 25.0F, 2.0F, 2.0F, cube).texOffs(28, 30).addBox(-21.6631F, -3.9739F, -1.2505F, 4.0F, 2.0F, 2.0F, cube), PartPose.offset(0.2131F, 24.9639F, -0.1495F));
            Broom.addOrReplaceChild("stick_r1", CubeListBuilder.create().texOffs(0, 22).addBox(-15.548F, -4.01F, -1.6294F, 5.0F, 2.0F, 2.0F, cube), PartPose.offsetAndRotation(-1.5056F, 0.0361F, 5.2711F, 0.0F, -0.4363F, 0.0F));
            Broom.addOrReplaceChild("stick_r2", CubeListBuilder.create().texOffs(30, 9).addBox(-17.398F, -4.0F, -2.4706F, 4.0F, 2.0F, 2.0F, cube), PartPose.offsetAndRotation(-1.7303F, 0.0361F, -6.1858F, 0.0F, 0.4363F, 0.0F));
            return LayerDefinition.create(meshdefinition, 64, 64);
        }
    }

}
