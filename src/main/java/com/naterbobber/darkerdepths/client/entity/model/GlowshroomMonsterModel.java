package com.naterbobber.darkerdepths.client.entity.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.naterbobber.darkerdepths.entities.GlowshroomMonsterEntity;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.QuadrupedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class GlowshroomMonsterModel <T extends GlowshroomMonsterEntity> extends EntityModel<T> {
    private final ModelRenderer head;
    private final ModelRenderer jaw;
    private final ModelRenderer body;
    private final ModelRenderer body_glowshroom1;
    private final ModelRenderer body_glowshroom2;
    private final ModelRenderer body_glowshroom3;
    private final ModelRenderer body_glowshroom4;
    private final ModelRenderer body_glowshroom5;
    private final ModelRenderer left_arm;
    private final ModelRenderer left_arm_glowshroom;
    private final ModelRenderer right_arm;
    private final ModelRenderer right_arm_glowshroom;
    private final ModelRenderer left_leg;
    private final ModelRenderer right_leg;

    public GlowshroomMonsterModel() {
        textureWidth = 128;
        textureHeight = 128;

        head = new ModelRenderer(this);
        head.setRotationPoint(0.0F, 4.0F, -11.0F);
        head.setTextureOffset(0, 0).addBox(-6.0F, -8.0F, -12.0F, 12.0F, 10.0F, 12.0F, 0.0F, false);

        jaw = new ModelRenderer(this);
        jaw.setRotationPoint(0.0F, 2.0F, -4.0F);
        head.addChild(jaw);
        jaw.setTextureOffset(48, 0).addBox(-7.0F, 0.0F, -9.0F, 14.0F, 6.0F, 13.0F, 0.0F, false);
        jaw.setTextureOffset(60, 19).addBox(-7.0F, -4.0F, -9.0F, 14.0F, 4.0F, 13.0F, 0.0F, false);

        body = new ModelRenderer(this);
        body.setRotationPoint(0.0F, 6.0F, 0.0F);
        setRotationAngle(body, -0.48F, 0.0F, 0.0F);
        body.setTextureOffset(0, 22).addBox(-9.0F, -7.5F, -12.0F, 18.0F, 15.0F, 24.0F, 0.0F, false);

        body_glowshroom1 = new ModelRenderer(this);
        body_glowshroom1.setRotationPoint(4.0F, -6.5F, -2.0F);
        body.addChild(body_glowshroom1);
        setRotationAngle(body_glowshroom1, 0.0436F, 0.0F, 0.0873F);
        body_glowshroom1.setTextureOffset(88, 65).addBox(-2.0F, -3.5F, -2.0F, 4.0F, 3.0F, 4.0F, 0.0F, false);
        body_glowshroom1.setTextureOffset(64, 72).addBox(-6.0F, -12.5F, -6.0F, 12.0F, 9.0F, 12.0F, 0.0F, false);

        body_glowshroom2 = new ModelRenderer(this);
        body_glowshroom2.setRotationPoint(-1.0F, -7.0F, 9.0F);
        body.addChild(body_glowshroom2);
        setRotationAngle(body_glowshroom2, -0.1309F, 0.0F, -0.0873F);
        body_glowshroom2.setTextureOffset(62, 61).addBox(-1.0F, -2.5F, -1.0F, 2.0F, 4.0F, 2.0F, 0.0F, false);
        body_glowshroom2.setTextureOffset(64, 61).addBox(-3.0F, -7.5F, -3.0F, 6.0F, 5.0F, 6.0F, 0.0F, false);

        body_glowshroom3 = new ModelRenderer(this);
        body_glowshroom3.setRotationPoint(8.5F, 1.3719F, 0.7403F);
        body.addChild(body_glowshroom3);
        setRotationAngle(body_glowshroom3, -0.2182F, 0.0F, 0.6981F);
        body_glowshroom3.setTextureOffset(62, 61).addBox(-1.0F, -3.0F, -1.0F, 2.0F, 4.0F, 2.0F, 0.0F, true);
        body_glowshroom3.setTextureOffset(64, 61).addBox(-3.0F, -8.0F, -3.0F, 6.0F, 5.0F, 6.0F, 0.0F, true);

        body_glowshroom4 = new ModelRenderer(this);
        body_glowshroom4.setRotationPoint(-8.5F, -3.1544F, 4.0209F);
        body.addChild(body_glowshroom4);
        setRotationAngle(body_glowshroom4, -0.2182F, 0.0F, -0.6981F);
        body_glowshroom4.setTextureOffset(62, 61).addBox(-1.0F, -3.0F, -1.0F, 2.0F, 4.0F, 2.0F, 0.0F, false);
        body_glowshroom4.setTextureOffset(64, 61).addBox(-3.0F, -8.0F, -3.0F, 6.0F, 5.0F, 6.0F, 0.0F, false);

        body_glowshroom5 = new ModelRenderer(this);
        body_glowshroom5.setRotationPoint(-6.0F, -7.0F, -7.0F);
        body.addChild(body_glowshroom5);
        setRotationAngle(body_glowshroom5, 0.1745F, 0.0F, -0.1745F);
        body_glowshroom5.setTextureOffset(62, 61).addBox(-1.0F, -3.0F, -1.0F, 2.0F, 4.0F, 2.0F, 0.0F, false);
        body_glowshroom5.setTextureOffset(64, 61).addBox(-3.0F, -8.0F, -3.0F, 6.0F, 5.0F, 6.0F, 0.0F, false);

        left_arm = new ModelRenderer(this);
        left_arm.setRotationPoint(9.0F, 2.0F, -10.0F);
        left_arm.setTextureOffset(32, 61).addBox(0.0F, -4.0F, -4.0F, 8.0F, 26.0F, 8.0F, 0.0F, false);

        left_arm_glowshroom = new ModelRenderer(this);
        left_arm_glowshroom.setRotationPoint(5.0F, -1.5F, -1.0F);
        left_arm.addChild(left_arm_glowshroom);
        setRotationAngle(left_arm_glowshroom, 0.1309F, 0.0F, 0.0873F);
        left_arm_glowshroom.setTextureOffset(62, 61).addBox(-1.0F, -4.0F, -1.0F, 2.0F, 4.0F, 2.0F, 0.0F, false);
        left_arm_glowshroom.setTextureOffset(64, 61).addBox(-3.0F, -9.0F, -3.0F, 6.0F, 5.0F, 6.0F, 0.0F, false);

        right_arm = new ModelRenderer(this);
        right_arm.setRotationPoint(-9.0F, 2.0F, -10.0F);
        right_arm.setTextureOffset(0, 61).addBox(-8.0F, -4.0F, -4.0F, 8.0F, 26.0F, 8.0F, 0.0F, false);

        right_arm_glowshroom = new ModelRenderer(this);
        right_arm_glowshroom.setRotationPoint(-6.0F, 6.0F, 2.0F);
        right_arm.addChild(right_arm_glowshroom);
        setRotationAngle(right_arm_glowshroom, -0.2182F, 0.0F, -0.5236F);
        right_arm_glowshroom.setTextureOffset(62, 61).addBox(-1.0F, -4.0F, -1.0F, 2.0F, 4.0F, 2.0F, 0.0F, true);
        right_arm_glowshroom.setTextureOffset(64, 61).addBox(-3.0F, -9.0F, -3.0F, 6.0F, 5.0F, 6.0F, 0.0F, true);

        left_leg = new ModelRenderer(this);
        left_leg.setRotationPoint(8.0F, 13.0F, 9.0F);
        left_leg.setTextureOffset(32, 95).addBox(-4.0F, -2.0F, -4.0F, 8.0F, 13.0F, 8.0F, 0.0F, false);

        right_leg = new ModelRenderer(this);
        right_leg.setRotationPoint(-8.0F, 13.0F, 9.0F);
        right_leg.setTextureOffset(0, 95).addBox(-4.0F, -2.0F, -4.0F, 8.0F, 13.0F, 8.0F, 0.0F, false);
    }

    @Override
    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.head.rotateAngleX = headPitch * ((float)Math.PI / 180F);
        this.head.rotateAngleY = netHeadYaw * ((float)Math.PI / 180F);
        this.body.rotateAngleX = ((float)3);
        this.right_leg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.left_leg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
        this.right_arm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
        this.left_arm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
    }

    @Override
    public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        head.render(matrixStack, buffer, packedLight, packedOverlay);
        body.render(matrixStack, buffer, packedLight, packedOverlay);
        left_arm.render(matrixStack, buffer, packedLight, packedOverlay);
        right_arm.render(matrixStack, buffer, packedLight, packedOverlay);
        left_leg.render(matrixStack, buffer, packedLight, packedOverlay);
        right_leg.render(matrixStack, buffer, packedLight, packedOverlay);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}

