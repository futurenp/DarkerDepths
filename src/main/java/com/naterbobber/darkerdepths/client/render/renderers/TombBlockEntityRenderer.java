package com.naterbobber.darkerdepths.client.render.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.block.DDBlockStateProperties;
import com.naterbobber.darkerdepths.block.blockentities.TombBlockEntity;
import com.naterbobber.darkerdepths.block.blockstates.BedState;
import com.naterbobber.darkerdepths.block.custom.TombBlock;
import com.naterbobber.darkerdepths.client.models.TombModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
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

        if(animatable.hasBed()) {
            renderBed(animatable, partialTick, poseStack, bufferSource, packedLight);
        }
    }

    private void renderBed(TombBlockEntity tombBlockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        var bedState = tombBlockEntity.getBlockState().getValue(DDBlockStateProperties.BED);

        if (bedState == BedState.NONE) {
            return;
        }

        var texture = DarkerDepths.id("textures/entity/tomb/colors/" + bedState.getSerializedName() + ".png");
        var consumer = bufferSource.getBuffer(RenderType.entityCutout(texture));

        poseStack.pushPose();
        poseStack.translate(0.0, 0.335, 0.0);

        var pose = poseStack.last();

        float length = 38.0F / 16.0F;
        float width = 1F;
        float height = 3/16F;

        consumer.addVertex(pose, -length / 2, height, width)
                .setColor(255, 255, 255, 255)
                .setUv(1.0F, 0.0F)
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setLight(packedLight)
                .setNormal(pose, 0.0F, 1.0F, 0.0F);

        consumer.addVertex(pose, length / 2, height, width)
                .setColor(255, 255, 255, 255)
                .setUv(0.0F, 0.0F)
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setLight(packedLight)
                .setNormal(pose, 0.0F, 1.0F, 0.0F);

        consumer.addVertex(pose, length / 2, height, 0.0F)
                .setColor(255, 255, 255, 255)
                .setUv(0.0F, 1.0F)
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setLight(packedLight)
                .setNormal(pose, 0.0F, 1.0F, 0.0F);

        consumer.addVertex(pose, -length / 2, height, 0.0F)
                .setColor(255, 255, 255, 255)
                .setUv(1.0F, 1.0F)
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setLight(packedLight)
                .setNormal(pose, 0.0F, 1.0F, 0.0F);

        poseStack.popPose();
    }

    private void renderSkeleton(TombBlockEntity tombBlockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        var level = tombBlockEntity.getLevel();
        if (level == null) return;

        var skeleton = new Skeleton(EntityType.SKELETON, level);
        skeleton.yHeadRot = 0.0f;
        skeleton.yHeadRotO = 0.0f;

        var storedItem = tombBlockEntity.getStoredItem();
        if (!storedItem.isEmpty()) {
            skeleton.setItemInHand(InteractionHand.MAIN_HAND, storedItem.copy());
        }

        poseStack.pushPose();
        poseStack.translate(1.0, 0.335, 0.5);

        poseStack.mulPose(Axis.XP.rotationDegrees(-90f));
        poseStack.mulPose(Axis.ZP.rotationDegrees(90f));

        this.dispatcher.render(skeleton, 0, 0, 0, 0, partialTick, poseStack, bufferSource, packedLight);

        poseStack.popPose();
    }

    @Override
    public AABB getRenderBoundingBox(TombBlockEntity blockEntity) {
        BlockPos pos = blockEntity.getBlockPos();
        BlockState state = blockEntity.getBlockState();

        if (state.getBlock() instanceof TombBlock) {
            Direction facing = state.getValue(HorizontalDirectionalBlock.FACING);

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