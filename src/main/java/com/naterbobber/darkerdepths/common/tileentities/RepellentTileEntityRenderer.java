package com.naterbobber.darkerdepths.common.tileentities;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.naterbobber.darkerdepths.core.DarkerDepths;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.model.RenderMaterial;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;

public class RepellentTileEntityRenderer extends TileEntityRenderer<RepellentTileEntity> {
    private final ModelRenderer bone = new ModelRenderer(64, 48, 0, 0);

    public RepellentTileEntityRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn);
        this.bone.setRotationPoint(0.0F, 16.0F, 0.0F);
        this.bone.setTextureOffset(0,0).addBox(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.0F, false);
    }

    @Override
    public void render(RepellentTileEntity tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
        float f = tileEntityIn.getActiveRotation(0.0F);
        matrixStackIn.push();
//        matrixStackIn.translate(0.5D, -0.5D, 0.5D);
//        matrixStackIn.rotate(new Quaternion(Vector3f.YP.rotationDegrees(f * 45.0F)));
        matrixStackIn.translate(0.5D, -0.5D, 0.5D);
        matrixStackIn.rotate(new Quaternion(Vector3f.YP.rotationDegrees(f * 45.0F)));
        ResourceLocation resourceLocation = new ResourceLocation(DarkerDepths.MODID, "textures/misc/orb.png");
        IVertexBuilder buffer = bufferIn.getBuffer(RenderType.getEntityCutout(resourceLocation));
        this.bone.render(matrixStackIn, buffer, combinedLightIn, combinedOverlayIn);
        matrixStackIn.pop();
    }
}
