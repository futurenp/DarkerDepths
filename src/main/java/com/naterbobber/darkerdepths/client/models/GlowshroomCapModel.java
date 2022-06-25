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
public class GlowshroomCapModel<T extends LivingEntity> extends HumanoidModel<T> {
	public ModelPart Head;

	public GlowshroomCapModel(ModelPart root) {
		super(root);
		this.Head = root.getChild("Head");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = HumanoidModel.createMesh(CubeDeformation.NONE, 0.0F);
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition Head = partdefinition.addOrReplaceChild("Head", CubeListBuilder.create(), PartPose.ZERO);

		PartDefinition Hat = Head.addOrReplaceChild("Hat", CubeListBuilder.create().texOffs(0, 0).addBox(-6.0F, -3.5F, -5.0F, 12.0F, 9.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -9.0F, 0.0F, -0.2793F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float p_102038_, float p_102039_, float p_102040_, float p_102041_) {
		poseStack.pushPose();
		this.Head.copyFrom(this.head);
		if (this.young) {
			poseStack.scale(0.75F, 0.75F, 0.75F);
			this.Head.setPos(0.0F, 15.0F, 0.0F);
		}
		this.Head.render(poseStack, buffer, packedLight, packedOverlay);
		poseStack.popPose();
	}
}