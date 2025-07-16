package com.naterbobber.darkerdepths.client.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.blocks.TombBlock;
import com.naterbobber.darkerdepths.blocks.blockentities.TombBlockEntity;
import com.naterbobber.darkerdepths.client.models.TombModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;

public class TombBlockEntityRenderer implements BlockEntityRenderer<TombBlockEntity> {

    private static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation(DarkerDepths.MODID, "textures/entity/tomb/tomb.png");
    private final TombModel model;

    public TombBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        this.model = new TombModel(context.bakeLayer(createModelLayerLocation()));
    }

    public static ModelLayerLocation createModelLayerLocation() {
        return new ModelLayerLocation(new ResourceLocation(DarkerDepths.MODID, "tomb"), "main");
    }

    @Override
    public void render(TombBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        BlockState blockState = blockEntity.getBlockState();
        if (!blockState.hasProperty(TombBlock.FACING)) {
            return;
        }
        Direction facing = blockState.getValue(TombBlock.FACING);

        poseStack.pushPose();
        poseStack.translate(0.0, 1.5, 0.0);

        switch (facing) {
            case NORTH -> poseStack.translate(0.5, 0.0, 1.0);  // (0.5 + 1.0, 1.5, 0.5 - 1.0)
            case EAST  -> poseStack.translate(0.0, 0.0, 0.5);  // (0.5 - 1.0, 1.5, 0.5 - 1.0)
            case SOUTH -> poseStack.translate(0.5, 0.0, 0.0);  // (0.5 - 1.0, 1.5, 0.5 + 1.0)
            case WEST  -> poseStack.translate(1.0, 0.0, 0.5);   // (0.5 + 1.0, 1.5, 0.5 + 1.0)
        }

        poseStack.mulPose(com.mojang.math.Axis.YP.rotationDegrees(facing.toYRot()));
        poseStack.mulPose(com.mojang.math.Axis.XP.rotationDegrees(180));

        VertexConsumer vertexConsumer = bufferSource.getBuffer(this.model.renderType(TEXTURE_LOCATION));

        this.model.renderToBuffer(poseStack, vertexConsumer, packedLight, packedOverlay, 1.0F, 1.0F, 1.0F, 1.0F);

        poseStack.popPose();
    }
}
