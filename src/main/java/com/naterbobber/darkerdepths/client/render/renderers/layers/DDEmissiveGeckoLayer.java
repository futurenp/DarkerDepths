package com.naterbobber.darkerdepths.client.render.renderers.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Mob;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

public class DDEmissiveGeckoLayer<T extends Mob & GeoEntity> extends GeoRenderLayer<T> {
    private ResourceLocation texture;
    public DDEmissiveGeckoLayer(GeoEntityRenderer<T> entityRendererIn, ResourceLocation texture) {
        super(entityRendererIn);
        this.setEmissiveTexture(texture);
    }

    private void setEmissiveTexture(ResourceLocation texture) {
        this.texture = texture;
    }

    public ResourceLocation getEmissiveTexture(){
        return this.texture;
    }

    @Override
    public void render(PoseStack poseStack, T animatable, BakedGeoModel bakedModel, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
        RenderType innerRenderType = RenderType.eyes(getEmissiveTexture());
        VertexConsumer innerBuffer = bufferSource.getBuffer(innerRenderType);

        getRenderer().actuallyRender(poseStack, animatable, bakedModel, innerRenderType, bufferSource, innerBuffer,
                false, partialTick, 15728880, packedOverlay, 1.0f, 1.0f, 1.0f, 1.0f);
    }
}