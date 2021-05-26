package com.naterbobber.darkerdepths.client.entity.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.naterbobber.darkerdepths.common.entities.MagmaMinionEntity;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;

public class MagmaMinionModel <T extends MagmaMinionEntity> extends EntityModel<T> {
    private final ModelRenderer head;
    private final ModelRenderer body;
    private final ModelRenderer right_leg;
    private final ModelRenderer left_leg;
    private final ModelRenderer left_arm;
    private final ModelRenderer left_arm2;
    private final ModelRenderer right_arm;
    private final ModelRenderer right_arm2;

    public MagmaMinionModel() {
        textureWidth = 64;
        textureHeight = 32;

        head = new ModelRenderer(this);
        head.setRotationPoint(0.0F, 13.0F, 1.0F);
        setRotationAngle(head, -0.2182F, 0.0F, 0.0F);
        head.setTextureOffset(28, 23).addBox(4.0F, -5.0F, -4.0F, 1.0F, 3.0F, 3.0F, 0.0F, false);
        head.setTextureOffset(0, 16).addBox(-4.0F, -5.0F, -4.0F, 8.0F, 5.0F, 6.0F, 0.0F, false);

        body = new ModelRenderer(this);
        body.setRotationPoint(0.0F, 24.0F, 0.0F);
        body.setTextureOffset(0, 0).addBox(-4.0F, -11.0F, -4.0F, 8.0F, 9.0F, 7.0F, 0.01F, false);
        body.setTextureOffset(32, 1).addBox(-4.0F, -12.0F, -4.0F, 8.0F, 1.0F, 0.0F, 0.0F, false);

        right_leg = new ModelRenderer(this);
        right_leg.setRotationPoint(-2.5F, 22.0F, -0.5F);
        right_leg.setTextureOffset(23, 0).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 2.0F, 3.0F, 0.0F, false);

        left_leg = new ModelRenderer(this);
        left_leg.setRotationPoint(2.5F, 22.0F, -0.5F);
        left_leg.setTextureOffset(23, 0).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 2.0F, 3.0F, 0.0F, true);

        left_arm = new ModelRenderer(this);
        left_arm.setRotationPoint(4.0F, 15.0F, -0.5F);
        setRotationAngle(left_arm, 0.0F, 0.0F, -0.4363F);
        left_arm.setTextureOffset(42, 5).addBox(0.0F, -1.0F, -1.5F, 3.0F, 5.0F, 3.0F, 0.0F, false);

        left_arm2 = new ModelRenderer(this);
        left_arm2.setRotationPoint(1.0F, 3.0F, 0.0F);
        left_arm.addChild(left_arm2);
        setRotationAngle(left_arm2, 0.0F, 0.0F, 0.3927F);
        left_arm2.setTextureOffset(46, 13).addBox(-1.0F, 0.0F, -2.5F, 4.0F, 5.0F, 5.0F, 0.0F, false);

        right_arm = new ModelRenderer(this);
        right_arm.setRotationPoint(-4.0F, 15.0F, -0.5F);
        setRotationAngle(right_arm, 0.0F, 0.0F, 0.4363F);
        right_arm.setTextureOffset(30, 5).addBox(-3.0F, -1.0F, -1.5F, 3.0F, 5.0F, 3.0F, 0.0F, true);

        right_arm2 = new ModelRenderer(this);
        right_arm2.setRotationPoint(-1.0F, 3.0F, 0.0F);
        right_arm.addChild(right_arm2);
        setRotationAngle(right_arm2, 0.0F, 0.0F, -0.3927F);
        right_arm2.setTextureOffset(28, 13).addBox(-3.0F, 0.0F, -2.5F, 4.0F, 5.0F, 5.0F, 0.0F, true);
    }

    @Override
    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.head.rotateAngleX = MathHelper.cos(limbSwing * 0.4F) * 0.6662F * limbSwingAmount;
        this.body.rotateAngleY = netHeadYaw * ((float)Math.PI / 180F);
        this.right_leg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.left_leg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
        this.right_arm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
        this.left_arm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
    }
    @Override
    public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        head.render(matrixStack, buffer, packedLight, packedOverlay);
        body.render(matrixStack, buffer, packedLight, packedOverlay);
        right_leg.render(matrixStack, buffer, packedLight, packedOverlay);
        left_leg.render(matrixStack, buffer, packedLight, packedOverlay);
        left_arm.render(matrixStack, buffer, packedLight, packedOverlay);
        right_arm.render(matrixStack, buffer, packedLight, packedOverlay);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}