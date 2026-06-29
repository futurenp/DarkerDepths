package com.naterbobber.darkerdepths.client.render.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.naterbobber.darkerdepths.client.render.DDRenderTypes;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.properties.WoodType;

import java.util.Objects;

public interface IEmissiveSign {
    default void renderSignWithGlow(PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay, WoodType woodType, Model model, ResourceLocation glowTexture, float scale) {
        poseStack.pushPose();
        poseStack.scale(scale, -scale, -scale);
        var material = getSignMaterial(woodType);
        Objects.requireNonNull(model);

        var vertexConsumer = material.buffer(buffer, model::renderType);
        renderGlowSignModel(poseStack, packedLight, packedOverlay, model, vertexConsumer);

        if (buffer instanceof MultiBufferSource.BufferSource bufferSource) {
            bufferSource.endBatch();
        }

        var vertexConsumerGlow = buffer.getBuffer(DDRenderTypes.emissiveTransparent(glowTexture));
        renderGlowSignModel(poseStack, packedLight, packedOverlay, model, vertexConsumerGlow);

        poseStack.popPose();
    }

    void renderGlowSignModel(PoseStack poseStack, int packedLight, int packedOverlay, Model model, VertexConsumer vertexConsumer);

    Material getSignMaterial(WoodType woodType);
}
