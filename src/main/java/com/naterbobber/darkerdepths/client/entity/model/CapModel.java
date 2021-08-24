package com.naterbobber.darkerdepths.client.entity.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.IHasHead;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

//<>

@OnlyIn(Dist.CLIENT)
public class CapModel<T extends LivingEntity> extends BipedModel<T> implements IHasHead {
	private final ModelRenderer group;

	public CapModel() {
		super(1.0F);
		textureWidth = 64;
		textureHeight = 64;

		group = new ModelRenderer(this);
		group.setRotationPoint(0.0F, 20.0F, 0.0F);
		group.setTextureOffset(0, 48).addBox(-4.0F, -18.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.0F, true);

		ModelRenderer capCube = new ModelRenderer(this);
		capCube.setRotationPoint(-15.0F, 7.0F, 19.0F);
		group.addChild(capCube);
		setRotationAngle(capCube, -0.3054F, 0.0F, 0.0F);
		capCube.setTextureOffset(0, 0).addBox(9.0F, -14.0F, -28.0F, 12.0F, 9.0F, 12.0F, 0.0F, false);
	}

	@Override
	public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		this.group.copyModelAngles(this.bipedHead);
		this.group.render(matrixStack, buffer, packedLight, packedOverlay);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}