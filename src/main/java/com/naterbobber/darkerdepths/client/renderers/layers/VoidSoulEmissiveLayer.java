package com.naterbobber.darkerdepths.client.renderers.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.entities.VoidSoulEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

public class VoidSoulEmissiveLayer extends GeoRenderLayer<VoidSoulEntity> {
    public VoidSoulEmissiveLayer(GeoEntityRenderer<VoidSoulEntity> entityRendererIn) {
        super(entityRendererIn);
    }

    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(DarkerDepths.MODID, "textures/entity/void_soul/void_soul_glowmask.png");

    @Override
    public void render(PoseStack poseStack, VoidSoulEntity animatable, BakedGeoModel bakedModel, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
        bakedModel.getBone("outer").ifPresent(bone -> bone.setHidden(true));
        bakedModel.getBone("inner").ifPresent(bone -> bone.setHidden(false));

        try {
            RenderType innerRenderType = RenderType.eyes(TEXTURE);
            VertexConsumer innerBuffer = bufferSource.getBuffer(innerRenderType);

            getRenderer().actuallyRender(poseStack, animatable, bakedModel, innerRenderType, bufferSource, innerBuffer,
                    false, partialTick, 15728880, packedOverlay, 1.0f, 1.0f, 1.0f, 1.0f);
        } finally {
            bakedModel.getBone("inner").ifPresent(bone -> bone.setHidden(true));
            bakedModel.getBone("outer").ifPresent(bone -> bone.setHidden(false));
        }
    }
}