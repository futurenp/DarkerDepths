package com.naterbobber.darkerdepths.client.render.renderers.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.entity.Entity;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;
import software.bernie.geckolib.util.ClientUtil;


public class DDRenderLayer<T extends GeoAnimatable> extends GeoRenderLayer<T> {
    private final RenderType renderType;
    private int brightness = -1;

    public DDRenderLayer(GeoRenderer<T> renderer, RenderType renderType) {
        super(renderer);
        this.renderType = renderType;
    }

    public DDRenderLayer(GeoRenderer<T> renderer, RenderType renderType, int brightness) {
        super(renderer);
        this.renderType = renderType;
        this.brightness = brightness;
    }

    public static <T extends GeoAnimatable> DDRenderLayer<T> withType(GeoRenderer<T> renderer, RenderType renderType) {
        return new DDRenderLayer<T>(renderer, renderType);
    }

    @Override
    public void render(PoseStack poseStack, T animatable, BakedGeoModel bakedModel, RenderType ignoredRenderType,
                       MultiBufferSource bufferSource, VertexConsumer ignoredBuffer, float partialTick,
                       int packedLight, int packedOverlay) {
        var currentRenderType = this.renderType;
        var texture = this.getTextureResource(animatable);

        if(animatable instanceof Entity entity) {
            if (entity.isInvisible()) {
                if (!entity.isInvisibleTo(ClientUtil.getClientPlayer())) {
                    currentRenderType = RenderType.itemEntityTranslucentCull(texture);
                } else {
                    currentRenderType = Minecraft.getInstance().shouldEntityAppearGlowing(entity) ? RenderType.outline(texture) : null;
                }
            }
        }

        if (currentRenderType == null) {
            return;
        }

        VertexConsumer buffer = bufferSource.getBuffer(currentRenderType);

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
                currentRenderType,
                buffer,
                partialTick,
                packedLight,
                packedOverlay,
                this.getRenderer().getRenderColor(animatable, partialTick, packedLight).argbInt());
    }
}
