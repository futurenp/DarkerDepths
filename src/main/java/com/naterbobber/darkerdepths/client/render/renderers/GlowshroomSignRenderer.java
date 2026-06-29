package com.naterbobber.darkerdepths.client.render.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.naterbobber.darkerdepths.DarkerDepths;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.properties.WoodType;

public class GlowshroomSignRenderer extends SignRenderer implements IEmissiveSign {
    private static final ResourceLocation glowTexture = DarkerDepths.id("textures/entity/signs/glowshroom_glow.png");

    public GlowshroomSignRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    protected void renderSign(PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay, WoodType woodType, Model model) {
        renderSignWithGlow(poseStack, buffer, packedLight, packedOverlay, woodType, model, glowTexture, getSignModelRenderScale());
    }

    @Override
    public void renderGlowSignModel(PoseStack poseStack, int packedLight, int packedOverlay, Model model, VertexConsumer vertexConsumer) {
        if(model instanceof SignModel signModel) {
            signModel.root.render(poseStack, vertexConsumer, packedLight, packedOverlay);
        }
    }

    @Override
    public Material getSignMaterial(WoodType woodType) {
        return Sheets.getSignMaterial(woodType);
    }
}
