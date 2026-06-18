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

import java.util.function.Predicate;

@OnlyIn(Dist.CLIENT)
public class DDRenderLayer<T extends GeoAnimatable> extends GeoRenderLayer<T> {
    protected final RenderType renderType;
    private int minBrightness = 0;
    private Predicate<T> renderPredicate = animatable -> true;

    protected DDRenderLayer(GeoRenderer<T> renderer, RenderType renderType) {
        super(renderer);
        this.renderType = renderType;
    }

    public static <T extends GeoAnimatable> DDRenderLayer<T> withType(GeoRenderer<T> renderer, RenderType renderType) {
        return new DDRenderLayer<>(renderer, renderType);
    }

    @Override
    public void render(PoseStack poseStack, T animatable, BakedGeoModel bakedModel, RenderType ignoredRenderType,
                       MultiBufferSource bufferSource, VertexConsumer ignoredBuffer, float partialTick,
                       int packedLight, int packedOverlay) {
        if(!shouldRender(animatable)) return;

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

        if(minBrightness > 0) {
            int currentBlockLight = LightTexture.block(packedLight);
            int skyLight = LightTexture.sky(packedLight);
            int finalBlockLight = Math.max(currentBlockLight, minBrightness);
            packedLight = LightTexture.pack(finalBlockLight, skyLight);
        }

        var vertexConsumer = bufferSource.getBuffer(currentRenderType);
        var renderer = this.getRenderer();
        int color = renderer.getRenderColor(animatable, partialTick, packedLight).argbInt();

        renderer.reRender(
                bakedModel,
                poseStack,
                bufferSource,
                animatable,
                currentRenderType,
                vertexConsumer,
                partialTick,
                packedLight,
                packedOverlay,
                color);
    }

    protected boolean shouldRender(T animatable) {
        return renderPredicate.test(animatable);
    }

    public DDRenderLayer<T> setRenderPredicate(Predicate<T> predicate) {
        this.renderPredicate = predicate;
        return this;
    }

    public DDRenderLayer<T> setMinBrightness(int minBrightness) {
        this.minBrightness = minBrightness;
        return this;
    }
}
