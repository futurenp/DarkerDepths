package com.naterbobber.darkerdepths.client.render.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.block.DDBlockStateProperties;
import com.naterbobber.darkerdepths.block.blockentities.TombBlockEntity;
import com.naterbobber.darkerdepths.block.custom.TombBlock;
import com.naterbobber.darkerdepths.client.models.TombModel;
import com.naterbobber.darkerdepths.util.Colors;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.model.data.ModelData;
import net.neoforged.neoforge.common.Tags;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

import java.util.HashMap;
import java.util.Map;

import static net.minecraft.world.phys.AABB.encapsulatingFullBlocks;

@OnlyIn(Dist.CLIENT)
public class TombBlockEntityRenderer extends GeoBlockRenderer<TombBlockEntity> {
    public final EntityRenderDispatcher dispatcher;

    public static final Map<String, ModelResourceLocation> BED_MODELS = new HashMap<>();

    static {
        for (var color : Colors.BASE_16) {
            BED_MODELS.put(color, ModelResourceLocation.standalone(DarkerDepths.id("block/tomb_bed_" + color)));
        }
    }

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
        var hasBed = tombBlockEntity.getBlockState().getValue(DDBlockStateProperties.BED);

        if (!hasBed) {
            return;
        }

        var bed = tombBlockEntity.getItemStack();
        var textureName = getBedTextureName(bed);

        if(textureName.equals("none")) {
            return;
        }

        var bakedModel = Minecraft.getInstance().getModelManager().getModel(BED_MODELS.get(textureName));
        var consumer = bufferSource.getBuffer(RenderType.solid());

        poseStack.pushPose();
        poseStack.translate(-0.5, 0.0, 0.0);

        Minecraft.getInstance().getBlockRenderer().getModelRenderer().renderModel(
                poseStack.last(),
                consumer,
                null,
                bakedModel,
                1.0F, 1.0F, 1.0F,
                packedLight,
                OverlayTexture.NO_OVERLAY,
                ModelData.EMPTY,
                RenderType.solid()
        );

        poseStack.popPose();
    }

    public static String getBedTextureName(ItemStack stack) {
        return switch (stack) {
            case ItemStack s when s.is(Tags.Items.DYED_WHITE) -> "white";
            case ItemStack s when s.is(Tags.Items.DYED_LIGHT_GRAY) -> "light_gray";
            case ItemStack s when s.is(Tags.Items.DYED_GRAY) -> "gray";
            case ItemStack s when s.is(Tags.Items.DYED_BLACK) -> "black";
            case ItemStack s when s.is(Tags.Items.DYED_BROWN) -> "brown";
            case ItemStack s when s.is(Tags.Items.DYED_RED) -> "red";
            case ItemStack s when s.is(Tags.Items.DYED_ORANGE) -> "orange";
            case ItemStack s when s.is(Tags.Items.DYED_YELLOW) -> "yellow";
            case ItemStack s when s.is(Tags.Items.DYED_LIME) -> "lime";
            case ItemStack s when s.is(Tags.Items.DYED_GREEN) -> "green";
            case ItemStack s when s.is(Tags.Items.DYED_CYAN) -> "cyan";
            case ItemStack s when s.is(Tags.Items.DYED_LIGHT_BLUE) -> "light_blue";
            case ItemStack s when s.is(Tags.Items.DYED_BLUE) -> "blue";
            case ItemStack s when s.is(Tags.Items.DYED_PURPLE) -> "purple";
            case ItemStack s when s.is(Tags.Items.DYED_MAGENTA) -> "magenta";
            case ItemStack s when s.is(Tags.Items.DYED_PINK) -> "pink";
            default -> "none";
        };
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