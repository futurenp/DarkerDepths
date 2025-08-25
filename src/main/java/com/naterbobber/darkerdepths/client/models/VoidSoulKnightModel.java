package com.naterbobber.darkerdepths.client.models;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.naterbobber.darkerdepths.entities.VoidSoulKnightEntity;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;



public class VoidSoulKnightModel<T extends VoidSoulKnightEntity> extends EntityModel<T> {
	private final ModelPart body;
	private final ModelPart head;
	private final ModelPart left_arm;
	private final ModelPart right_arm;
	private final ModelPart left_leg;
	private final ModelPart right_leg;

	public VoidSoulKnightModel(ModelPart root) {
		//this.root = root.getChild("root");
		this.body = root.getChild("body");
		this.head = root.getChild("head");
		this.left_arm = root.getChild("left_arm");
		this.right_arm = root.getChild("right_arm");
		this.left_leg = root.getChild("left_leg");
		this.right_leg = root.getChild("right_leg");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		// Get the main root part definition
		PartDefinition partdefinition = meshdefinition.getRoot();

		// Add body parts as direct children of the main root
		// The Y-offset of 24.0F is standard for humanoid models
		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 26).addBox(-7.0F, -13.0F, -4.5F, 14.0F, 16.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-7.0F, -13.0F, -4.5F, 14.0F, 18.0F, 8.0F, new CubeDeformation(0.2F)), PartPose.offset(0.0F, 3.0F, 0.0F));

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(44, 0).addBox(-4.0F, -10.0F, -4.0F, 8.0F, 10.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(24, 73).addBox(-3.5F, -7.0F, -4.2F, 7.0F, 7.0F, 0.0F, new CubeDeformation(0.0F))
				.texOffs(44, 18).addBox(-4.5F, -8.0F, -4.5F, 9.0F, 9.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -10.0F, -1.0F));

		PartDefinition left_arm = partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(44, 67).addBox(-2.75F, -1.725F, -2.475F, 5.0F, 16.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(0, 73).mirror().addBox(-2.75F, -2.775F, -3.025F, 6.0F, 9.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(9.75F, -7.275F, -0.525F));

		PartDefinition right_arm = partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(0, 73).addBox(-3.25F, -2.775F, -3.025F, 6.0F, 9.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(44, 67).mirror().addBox(-2.25F, -1.725F, -2.475F, 5.0F, 16.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-9.75F, -7.275F, -0.525F));

		PartDefinition left_leg = partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(22, 50).mirror().addBox(-3.5F, 0.0F, -2.0F, 6.0F, 18.0F, 5.0F, new CubeDeformation(0.2F)).mirror(false)
				.texOffs(0, 50).mirror().addBox(-3.5F, 0.0F, -2.0F, 6.0F, 18.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(3.5F, 6.0F, -1.0F));

		PartDefinition right_leg = partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(22, 50).addBox(-2.5F, 0.0F, -2.0F, 6.0F, 18.0F, 5.0F, new CubeDeformation(0.2F))
				.texOffs(0, 50).addBox(-2.5F, 0.0F, -2.0F, 6.0F, 18.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.5F, 6.0F, -1.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.head.xRot = headPitch * ((float)Math.PI / 180F);
		this.head.yRot = netHeadYaw * ((float)Math.PI / 180F);
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