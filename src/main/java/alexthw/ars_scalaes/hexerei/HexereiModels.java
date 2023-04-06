package alexthw.ars_scalaes.hexerei;

import net.joefoxe.hexerei.client.renderer.entity.model.BroomBrushBaseModel;
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
            PartDefinition Broom = partdefinition.addOrReplaceChild("Broom", CubeListBuilder.create().texOffs(0, 0).addBox(-11.2131F, -3.9639F, -0.8505F, 15.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                    .texOffs(10, 10).addBox(-21.6631F, -3.9739F, -1.2505F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                    .texOffs(10, 10).mirror().addBox(10.2369F, -3.9739F, -0.4505F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.2131F, 24.9639F, -0.1495F));

            Broom.addOrReplaceChild("stick_r1", CubeListBuilder.create().texOffs(0, 4).addBox(-0.9889F, -1.005F, -1.5952F, 5.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-14.6863F, -2.9689F, -0.9124F, 0.0F, -0.4363F, 0.0F));
            Broom.addOrReplaceChild("stick_r2", CubeListBuilder.create().texOffs(0, 8).addBox(-3.4273F, -0.995F, -1.7749F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-14.6863F, -2.9689F, -0.9124F, 0.0F, 0.4363F, 0.0F));
            Broom.addOrReplaceChild("stick_r3", CubeListBuilder.create().texOffs(0, 8).mirror().addBox(-0.5727F, -0.995F, -0.2251F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(7.26F, -2.9689F, 1.2115F, 0.0F, 0.4363F, 0.0F));
            Broom.addOrReplaceChild("stick_r4", CubeListBuilder.create().texOffs(0, 4).mirror().addBox(-4.0111F, -1.005F, -0.4048F, 5.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(7.26F, -2.9689F, 1.2115F, 0.0F, -0.4363F, 0.0F));

            return LayerDefinition.create(meshdefinition, 64, 64);
        }
    }

    static class MagebloomBrush extends BroomBrushBaseModel {
        public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(prefix("archwood_broom_brush"), "main");

        public MagebloomBrush(ModelPart root) {
            super(root);
        }

        public static LayerDefinition createBodyLayer(CubeDeformation cube) {
            return BroomBrushBaseModel.createBodyLayer(cube);
        }


    }

}
