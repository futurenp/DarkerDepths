package com.naterbobber.darkerdepths.client.render.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.client.render.DDRenderTypes;
import com.naterbobber.darkerdepths.common.block.blockentities.DeathAnchorBlockEntity;
import com.naterbobber.darkerdepths.common.block.unique.DeathAnchorBlock;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DeathAnchorBlockEntityRenderer implements BlockEntityRenderer<DeathAnchorBlockEntity> {
    public static final ModelResourceLocation GLOW = ModelResourceLocation.standalone(DarkerDepths.id("block/death_anchor_glow"));
    private final BlockRenderDispatcher renderer;
    private final RenderType renderType;

    public DeathAnchorBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        this.renderer = context.getBlockRenderDispatcher();
        this.renderType = DDRenderTypes.emissiveTransparentFogOverride(TextureAtlas.LOCATION_BLOCKS);
    }

    @Override
    public void render(DeathAnchorBlockEntity anchor, float partialTick, PoseStack poseStack, MultiBufferSource buffers, int packedLight, int packedOverlay) {
        var state = anchor.getBlockState();
        if (!state.getValue(DeathAnchorBlock.POWERED)) {
            return;
        }

        var model = this.renderer.getBlockModelShaper().getModelManager().getModel(GLOW);
        this.renderer.getModelRenderer().renderModel(
                poseStack.last(),
                buffers.getBuffer(this.renderType),
                state,
                model,
                1.0F, 1.0F, 1.0F,
                LightTexture.FULL_BRIGHT,
                packedOverlay);
    }
}
