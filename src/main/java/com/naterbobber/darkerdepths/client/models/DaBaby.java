package com.naterbobber.darkerdepths.client.models;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DaBaby<T extends LivingEntity> extends HumanoidModel<T> {
    private final ModelPart cap;

    public DaBaby(ModelPart root) {
        super(root);
        this.cap = root.getChild("cap");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = HumanoidModel.createMesh(CubeDeformation.NONE, 0.0F);
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition cap = partdefinition.addOrReplaceChild("cap", CubeListBuilder.create(), PartPose.offset(-8.0F, 19.0F, 10.0F));

        PartDefinition cube_r1 = cap.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(35, 0).addBox(1.0F, -8.0F, -15.0F, 12.0F, 9.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-7.0F, -2.0F, 8.0F, -0.3054F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        poseStack.pushPose();
        this.cap.copyFrom(this.head);
        this.cap.render(poseStack, buffer, packedLight, packedOverlay);
        poseStack.popPose();
    }
}