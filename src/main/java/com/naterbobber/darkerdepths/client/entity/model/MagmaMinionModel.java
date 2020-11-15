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
    private final ModelRenderer right_arm;
    private final ModelRenderer bone6;
    private final ModelRenderer left_arm;
    private final ModelRenderer bone2;

    public MagmaMinionModel() {
        textureWidth = 64;
        textureHeight = 64;

        head = new ModelRenderer(this);
        head.setRotationPoint(0.0F, 12.0F, 4.0F);
        head.setTextureOffset(0, 34).addBox(-4.0F, -5.0F, -8.0F, 8.0F, 5.0F, 8.0F, 0.0F, false);
        head.setTextureOffset(3, 27).addBox(-4.0F, 0.0F, -8.0F, 8.0F, 2.0F, 5.0F, 0.0F, false);

        body = new ModelRenderer(this);
        body.setRotationPoint(0.0F, 15.0F, 0.0F);
        body.setTextureOffset(0, 47).addBox(-4.0F, -3.0F, -4.0F, 8.0F, 9.0F, 8.0F, 0.0F, false);

        right_leg = new ModelRenderer(this);
        right_leg.setRotationPoint(2.5F, 21.0F, 0.0F);
        right_leg.setTextureOffset(35, 36).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 3.0F, 3.0F, 0.0F, false);

        left_leg = new ModelRenderer(this);
        left_leg.setRotationPoint(-2.5F, 21.0F, 0.0F);
        left_leg.setTextureOffset(35, 30).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 3.0F, 3.0F, 0.0F, false);

        right_arm = new ModelRenderer(this);
        right_arm.setRotationPoint(-5.5F, 14.5F, 0.0F);
        setRotationAngle(right_arm, 0.0F, 0.0F, 0.3491F);
        right_arm.setTextureOffset(51, 48).addBox(-1.5F, -1.5F, -1.5F, 3.0F, 5.0F, 3.0F, 0.0F, false);

        bone6 = new ModelRenderer(this);
        bone6.setRotationPoint(-0.5F, 4.5F, 0.0F);
        right_arm.addChild(bone6);
        setRotationAngle(bone6, 0.0F, 0.0F, -0.3491F);
        bone6.setTextureOffset(32, 53).addBox(-2.0F, -2.0F, -2.5F, 4.0F, 6.0F, 5.0F, 0.0F, false);

        left_arm = new ModelRenderer(this);
        left_arm.setRotationPoint(5.5F, 14.5F, 0.0F);
        setRotationAngle(left_arm, 0.0F, 0.0F, -0.3491F);
        left_arm.setTextureOffset(51, 56).addBox(-1.5F, -1.5F, -1.5F, 3.0F, 5.0F, 3.0F, 0.0F, false);

        bone2 = new ModelRenderer(this);
        bone2.setRotationPoint(0.5F, 4.5F, 0.0F);
        left_arm.addChild(bone2);
        setRotationAngle(bone2, 0.0F, 0.0F, 0.3491F);
        bone2.setTextureOffset(32, 42).addBox(-2.0F, -2.0F, -2.5F, 4.0F, 6.0F, 5.0F, 0.0F, false);
    }

    @Override
    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.head.rotateAngleX = netHeadYaw * ((float)Math.PI / 180F);
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
        right_arm.render(matrixStack, buffer, packedLight, packedOverlay);
        left_arm.render(matrixStack, buffer, packedLight, packedOverlay);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}