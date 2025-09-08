package com.naterbobber.darkerdepths.client.render.renderers.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;


public class DDCustomRenderTypeLayer<T extends GeoAnimatable> extends GeoRenderLayer<T> {
    private final RenderType renderType;

    public DDCustomRenderTypeLayer(GeoRenderer<T> renderer, RenderType renderType) {
        super(renderer);
        this.renderType = renderType;
    }

    @Override
    public void render(PoseStack poseStack, T animatable, BakedGeoModel bakedModel, RenderType ignoredRenderType,
                       MultiBufferSource bufferSource, VertexConsumer ignoredBuffer, float partialTick,
                       int packedLight, int packedOverlay) {
        VertexConsumer buffer = bufferSource.getBuffer(this.renderType);
        getRenderer().reRender(bakedModel, poseStack, bufferSource, animatable, this.renderType,
                buffer, partialTick, 15728880, packedOverlay, 1);
    }
}
