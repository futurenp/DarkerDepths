package com.naterbobber.darkerdepths.client.render.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.naterbobber.darkerdepths.block.blockentities.TombBlockEntity;
import com.naterbobber.darkerdepths.block.custom.TombBlock;
import com.naterbobber.darkerdepths.client.models.TombModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

import static net.minecraft.world.phys.AABB.encapsulatingFullBlocks;

@OnlyIn(Dist.CLIENT)
public class TombBlockEntityRenderer extends GeoBlockRenderer<TombBlockEntity> {
    public final EntityRenderDispatcher dispatcher;

    public TombBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        super(new TombModel());
        this.dispatcher = context.getEntityRenderer();
    }

    @Override
    public void actuallyRender(PoseStack poseStack, TombBlockEntity animatable, BakedGeoModel model, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, int color) {
        super.actuallyRender(poseStack, animatable, model, renderType, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, color);

        if (animatable.isInhabited()) {
            renderSkeleton(animatable, partialTick, poseStack, bufferSource, packedLight);
        }
    }

    private void renderSkeleton(TombBlockEntity blockEntity, float partialTick, PoseStack matrices, MultiBufferSource bufferSource, int packedLight) {
        Level level = blockEntity.getLevel();
        if (level == null) return;

        Skeleton skeleton = new Skeleton(EntityType.SKELETON, level);
        skeleton.yHeadRot = 0.0f;
        skeleton.yHeadRotO = 0.0f;

        // Set the stored item to the skeleton's main hand
        ItemStack storedItem = blockEntity.getStoredItem();
        if (!storedItem.isEmpty()) {
            skeleton.setItemInHand(InteractionHand.MAIN_HAND, storedItem.copy());
        }

        matrices.pushPose();
        matrices.translate(1.0, 0.335, 0.5);

        matrices.mulPose(Axis.XP.rotationDegrees(-90f));
        matrices.mulPose(Axis.ZP.rotationDegrees(90f));

        // Render the skeleton with its held item
        this.dispatcher.render(skeleton, 0, 0, 0, 0, partialTick, matrices, bufferSource, packedLight);

        matrices.popPose();
    }

    @Override
    public AABB getRenderBoundingBox(TombBlockEntity blockEntity) {
        BlockPos pos = blockEntity.getBlockPos();
        BlockState state = blockEntity.getBlockState();

        if (state.getBlock() instanceof TombBlock) {
            Direction facing = state.getValue(TombBlock.FACING);

            return switch (facing) {
                case SOUTH -> encapsulatingFullBlocks(pos.offset(-2, -8, -1), pos.offset(3, 3, 8));
                case EAST -> encapsulatingFullBlocks(pos.offset(-1, -8, -2), pos.offset(8, 3, 3));
                case WEST -> encapsulatingFullBlocks(pos.offset(-7, -8, -2), pos.offset(2, 3, 3));
                default -> encapsulatingFullBlocks(pos.offset(-2, -8, -7), pos.offset(3, 3, 2));
            };
        }

        return super.getRenderBoundingBox(blockEntity);
    }

    @Override
    public boolean shouldRenderOffScreen(TombBlockEntity model) {
        return true;
    }
}