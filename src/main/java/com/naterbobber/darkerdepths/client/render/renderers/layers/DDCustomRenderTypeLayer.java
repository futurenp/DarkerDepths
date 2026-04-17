package com.naterbobber.darkerdepths.client.render.renderers.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EndermanRenderer;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;


public class DDCustomRenderTypeLayer<T extends GeoAnimatable> extends GeoRenderLayer<T> {
    private final RenderType renderType;
    private int brightness = -1;

    public DDCustomRenderTypeLayer(GeoRenderer<T> renderer, RenderType renderType) {
        super(renderer);
        this.renderType = renderType;
    }

    public DDCustomRenderTypeLayer(GeoRenderer<T> renderer, RenderType renderType, int brightness) {
        super(renderer);
        this.renderType = renderType;
        this.brightness = brightness;
    }

    @Override
    public void render(PoseStack poseStack, T animatable, BakedGeoModel bakedModel, RenderType ignoredRenderType,
                       MultiBufferSource bufferSource, VertexConsumer ignoredBuffer, float partialTick,
                       int packedLight, int packedOverlay) {
        VertexConsumer buffer = bufferSource.getBuffer(this.renderType);

        if(brightness != -1) {
            int currentBlockLight = LightTexture.block(packedLight);
            int skyLight = LightTexture.sky(packedLight);
            int finalBlockLight = Math.max(currentBlockLight, brightness);
            packedLight = LightTexture.pack(finalBlockLight, skyLight);
        }

        this.getRenderer().reRender(
                bakedModel,
                poseStack,
                bufferSource,
                animatable,
                this.renderType,
                buffer,
                partialTick,
                packedLight,
                packedOverlay,
                this.getRenderer().getRenderColor(animatable, partialTick, packedLight).argbInt());
    }
}
