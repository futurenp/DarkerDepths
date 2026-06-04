package com.naterbobber.darkerdepths.client.render.renderers.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.entity.Entity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;
import software.bernie.geckolib.util.ClientUtil;

@OnlyIn(Dist.CLIENT)
public class DDRenderLayer<T extends GeoAnimatable> extends GeoRenderLayer<T> {
    private final RenderType renderType;
    private int brightness = 0;

    private DDRenderLayer(GeoRenderer<T> renderer, RenderType renderType) {
        super(renderer);
        this.renderType = renderType;
    }

    private DDRenderLayer(GeoRenderer<T> renderer, RenderType renderType, int brightness) {
        super(renderer);
        this.renderType = renderType;
        this.brightness = brightness;
    }

    public static <T extends GeoAnimatable> DDRenderLayer<T> withType(GeoRenderer<T> renderer, RenderType renderType) {
        return new DDRenderLayer<>(renderer, renderType);
    }

    public static <T extends GeoAnimatable> DDRenderLayer<T> withBrightness(GeoRenderer<T> renderer, RenderType renderType, int brightness) {
        return new DDRenderLayer<>(renderer, renderType, brightness);
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

        if(brightness > 0) {
            int currentBlockLight = LightTexture.block(packedLight);
            int skyLight = LightTexture.sky(packedLight);
            int finalBlockLight = Math.max(currentBlockLight, brightness);
            packedLight = LightTexture.pack(finalBlockLight, skyLight);
        }

        var renderer = this.getRenderer();
        int color = renderer.getRenderColor(animatable, partialTick, packedLight).argbInt();

        renderer.reRender(
                bakedModel,
                poseStack,
                bufferSource,
                animatable,
                currentRenderType,
                buffer,
                partialTick,
                packedLight,
                packedOverlay,
                color);
    }
}
