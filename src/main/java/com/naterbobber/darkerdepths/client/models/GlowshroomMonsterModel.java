package com.naterbobber.darkerdepths.client.models;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.naterbobber.darkerdepths.entities.GlowshroomMonsterEntity;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GlowshroomMonsterModel<T extends GlowshroomMonsterEntity> extends EntityModel<T> {
    private final ModelPart head;
    private final ModelPart body;
    private final ModelPart left_arm;
    private final ModelPart right_arm;
    private final ModelPart left_leg;
    private final ModelPart right_leg;



    public GlowshroomMonsterModel(ModelPart root) {
        this.head = root.getChild("head");
        this.body = root.getChild("body");
        this.left_arm = root.getChild("left_arm");
        this.right_arm = root.getChild("right_arm");
        this.left_leg = root.getChild("left_leg");
        this.right_leg = root.getChild("right_leg");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-6.0F, -8.0F, -12.0F, 12.0F, 10.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 4.0F, -11.0F));

        PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(48, 0).addBox(-7.0F, 0.0F, -9.0F, 14.0F, 6.0F, 13.0F, new CubeDeformation(0.0F))
                .texOffs(60, 19).addBox(-7.0F, -4.0F, -9.0F, 14.0F, 4.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.0F, -4.0F));

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 22).addBox(-9.0F, -7.5F, -12.0F, 18.0F, 15.0F, 24.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 6.0F, 0.0F, -0.48F, 0.0F, 0.0F));

        PartDefinition body_glowshroom1 = body.addOrReplaceChild("body_glowshroom1", CubeListBuilder.create().texOffs(88, 65).addBox(-2.0F, -3.5F, -2.0F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(64, 72).addBox(-6.0F, -12.5F, -6.0F, 12.0F, 9.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, -6.5F, -2.0F, 0.0436F, 0.0F, 0.0873F));

        PartDefinition body_glowshroom2 = body.addOrReplaceChild("body_glowshroom2", CubeListBuilder.create().texOffs(62, 61).addBox(-1.0F, -2.5F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(64, 61).addBox(-3.0F, -7.5F, -3.0F, 6.0F, 5.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, -7.0F, 9.0F, -0.1309F, 0.0F, -0.0873F));

        PartDefinition body_glowshroom3 = body.addOrReplaceChild("body_glowshroom3", CubeListBuilder.create().texOffs(62, 61).mirror().addBox(-1.0F, -3.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(64, 61).mirror().addBox(-3.0F, -8.0F, -3.0F, 6.0F, 5.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(8.5F, 1.3719F, 0.7403F, -0.2182F, 0.0F, 0.6981F));

        PartDefinition body_glowshroom4 = body.addOrReplaceChild("body_glowshroom4", CubeListBuilder.create().texOffs(62, 61).addBox(-1.0F, -3.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(64, 61).addBox(-3.0F, -8.0F, -3.0F, 6.0F, 5.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-8.5F, -3.1544F, 4.0209F, -0.2182F, 0.0F, -0.6981F));

        PartDefinition body_glowshroom5 = body.addOrReplaceChild("body_glowshroom5", CubeListBuilder.create().texOffs(62, 61).addBox(-1.0F, -3.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(64, 61).addBox(-3.0F, -8.0F, -3.0F, 6.0F, 5.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.0F, -7.0F, -7.0F, 0.1745F, 0.0F, -0.1745F));

        PartDefinition left_arm = partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(32, 61).addBox(0.0F, -4.0F, -4.0F, 8.0F, 26.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(9.0F, 2.0F, -10.0F));

        PartDefinition left_arm_glowshroom = left_arm.addOrReplaceChild("left_arm_glowshroom", CubeListBuilder.create().texOffs(62, 61).addBox(-1.0F, -4.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(64, 61).addBox(-3.0F, -9.0F, -3.0F, 6.0F, 5.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.0F, -1.5F, -1.0F, 0.1309F, 0.0F, 0.0873F));

        PartDefinition right_arm = partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(0, 61).addBox(-8.0F, -4.0F, -4.0F, 8.0F, 26.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(-9.0F, 2.0F, -10.0F));

        PartDefinition right_arm_glowshroom = right_arm.addOrReplaceChild("right_arm_glowshroom", CubeListBuilder.create().texOffs(62, 61).mirror().addBox(-1.0F, -4.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(64, 61).mirror().addBox(-3.0F, -9.0F, -3.0F, 6.0F, 5.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-6.0F, 6.0F, 2.0F, -0.2182F, 0.0F, -0.5236F));

        PartDefinition left_leg = partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(32, 95).addBox(-4.0F, -2.0F, -4.0F, 8.0F, 13.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(8.0F, 13.0F, 9.0F));

        PartDefinition right_leg = partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 95).addBox(-4.0F, -2.0F, -4.0F, 8.0F, 13.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(-8.0F, 13.0F, 9.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.head.xRot = headPitch * ((float)Math.PI / 180F);
        this.head.yRot = netHeadYaw * ((float)Math.PI / 180F);
        this.body.xRot = ((float)100);
        this.right_leg.xRot = Mth.cos(limbSwing * 0.6662F) * limbSwingAmount;
        this.left_leg.xRot = Mth.cos(limbSwing * 0.6662F + (float)Math.PI) * limbSwingAmount;
        this.right_arm.xRot = Mth.cos(limbSwing * 0.6662F + (float)Math.PI) * limbSwingAmount;
        this.left_arm.xRot = Mth.cos(limbSwing * 0.6662F) * limbSwingAmount;
        int i = entity.getAttackTick();
        if (i > 0) {
            this.left_arm.xRot = Mth.lerp(i, -0.2F, -0.5F);
            this.right_arm.xRot = Mth.lerp(i, -0.2F, -0.5F);
        }
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        head.render(poseStack, buffer, packedLight, packedOverlay);
        body.render(poseStack, buffer, packedLight, packedOverlay);
        left_arm.render(poseStack, buffer, packedLight, packedOverlay);
        right_arm.render(poseStack, buffer, packedLight, packedOverlay);
        left_leg.render(poseStack, buffer, packedLight, packedOverlay);
        right_leg.render(poseStack, buffer, packedLight, packedOverlay);
    }
}
