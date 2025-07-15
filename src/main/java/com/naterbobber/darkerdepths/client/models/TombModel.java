package com.naterbobber.darkerdepths.client.models;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.RenderType;

// Changed to extend Model instead of EntityModel
public class TombModel extends Model {
	private final ModelPart bottom;
	private final ModelPart top;
	private final ModelPart bone;

	public TombModel(ModelPart root) {
		// We must call the super constructor. This RenderType is standard for block/entity models.
		super(RenderType::entityCutoutNoCull);
		this.bone = root.getChild("bone");
		this.top = this.bone.getChild("top");
		this.bottom = this.bone.getChild("bottom");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition bone = partdefinition.addOrReplaceChild("bone", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 24.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition top = bone.addOrReplaceChild("top", CubeListBuilder.create().texOffs(1, 0).addBox(-23.0F, -10.0F, -4.0F, 46.0F, 5.0F, 24.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -5.0F, -8.0F));

		PartDefinition bottom = bone.addOrReplaceChild("bottom", CubeListBuilder.create().texOffs(1, 29).addBox(-22.0F, -3.0F, -19.0F, 44.0F, 3.0F, 22.0F, new CubeDeformation(0.0F))
				.texOffs(1, 54).addBox(-21.0F, -10.0F, -18.0F, 42.0F, 7.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(1, 63).addBox(-21.0F, -10.0F, 0.0F, 42.0F, 7.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(1, 72).addBox(19.0F, -10.0F, -16.0F, 2.0F, 7.0F, 16.0F, new CubeDeformation(0.0F))
				.texOffs(37, 72).addBox(-21.0F, -10.0F, -16.0F, 2.0F, 7.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 8.0F));

		return LayerDefinition.create(meshdefinition, 256, 128);
	}

	// The setupAnim method is removed as it's part of EntityModel and not needed.

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		bone.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}
