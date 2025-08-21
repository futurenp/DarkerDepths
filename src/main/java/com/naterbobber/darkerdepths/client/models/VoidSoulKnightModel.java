package com.naterbobber.darkerdepths.client.models;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.Entity;

public class VoidSoulKnightModel<T extends Entity> extends EntityModel<T> {
	private final ModelPart root;
	private final ModelPart body;
	private final ModelPart head;
	private final ModelPart left_arm;
	private final ModelPart right_arm;
	private final ModelPart left_leg;
	private final ModelPart right_leg;

	public VoidSoulKnightModel(ModelPart root) {
		this.root = root.getChild("root");
		this.body = this.root.getChild("body");
		this.head = this.root.getChild("head");
		this.left_arm = this.root.getChild("left_arm");
		this.right_arm = this.root.getChild("right_arm");
		this.left_leg = this.root.getChild("left_leg");
		this.right_leg = this.root.getChild("right_leg");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 18.0F, 0.0F));

		PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 26).addBox(-7.0F, -1.0F, -4.5F, 14.0F, 16.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-7.0F, -1.0F, -4.5F, 14.0F, 18.0F, 8.0F, new CubeDeformation(0.2F)), PartPose.offset(0.0F, -27.0F, 0.0F));

		PartDefinition head = root.addOrReplaceChild("head", CubeListBuilder.create().texOffs(44, 0).addBox(-4.0F, -10.0F, -4.0F, 8.0F, 10.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(24, 73).addBox(-3.5F, -7.0F, -4.2F, 7.0F, 7.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(44, 18).addBox(-4.5F, -8.0F, -4.5F, 9.0F, 9.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -28.0F, -1.0F));

		PartDefinition left_arm = root.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(44, 67).addBox(-2.75F, -1.725F, -2.475F, 5.0F, 16.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(0, 73).mirror().addBox(-2.75F, -2.775F, -3.025F, 6.0F, 9.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(9.75F, -25.275F, -0.525F));

		PartDefinition right_arm = root.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(0, 73).addBox(-3.25F, -2.775F, -3.025F, 6.0F, 9.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(44, 67).mirror().addBox(-2.25F, -1.725F, -2.475F, 5.0F, 16.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-9.75F, -25.275F, -0.525F));

		PartDefinition left_leg = root.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(22, 50).mirror().addBox(-3.5F, 0.0F, -2.0F, 6.0F, 18.0F, 5.0F, new CubeDeformation(0.2F)).mirror(false)
		.texOffs(0, 50).mirror().addBox(-3.5F, 0.0F, -2.0F, 6.0F, 18.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(3.5F, -12.0F, -1.0F));

		PartDefinition right_leg = root.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(22, 50).addBox(-2.5F, 0.0F, -2.0F, 6.0F, 18.0F, 5.0F, new CubeDeformation(0.2F))
		.texOffs(0, 50).addBox(-2.5F, 0.0F, -2.0F, 6.0F, 18.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.5F, -12.0F, -1.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		root.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}