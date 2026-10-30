package com.naterbobber.darkerdepths.client.render.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.client.render.DDRenderTypes;
import com.naterbobber.darkerdepths.common.block.blockentities.VoidSoulTorchBlockEntity;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.world.level.block.WallTorchBlock;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class VoidSoulTorchBlockEntityRenderer implements BlockEntityRenderer<VoidSoulTorchBlockEntity> {
    public static final ModelResourceLocation STANDING_GLOW = ModelResourceLocation.standalone(DarkerDepths.id("block/void_soul_torch_glow"));
    public static final ModelResourceLocation WALL_GLOW = ModelResourceLocation.standalone(DarkerDepths.id("block/wall_void_soul_torch_glow"));
    public static final ModelResourceLocation STANDING_SHELL = ModelResourceLocation.standalone(DarkerDepths.id("block/void_soul_torch_shell"));
    public static final ModelResourceLocation WALL_SHELL = ModelResourceLocation.standalone(DarkerDepths.id("block/wall_void_soul_torch_shell"));
    private final BlockRenderDispatcher renderer;
    private final RenderType cubeRenderType;
    private final RenderType renderType;

    public VoidSoulTorchBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        this.renderer = context.getBlockRenderDispatcher();
        this.cubeRenderType = DDRenderTypes.torchShell(TextureAtlas.LOCATION_BLOCKS);
        this.renderType = DDRenderTypes.emissiveTransparentFogOverride(TextureAtlas.LOCATION_BLOCKS);
    }

    @Override
    public void render(VoidSoulTorchBlockEntity torch, float partialTick, PoseStack poseStack, MultiBufferSource buffers, int packedLight, int packedOverlay) {
        var state = torch.getBlockState();
        boolean wall = state.hasProperty(WallTorchBlock.FACING);
        var modelManager = this.renderer.getBlockModelShaper().getModelManager();
        var model = modelManager.getModel(wall ? WALL_GLOW : STANDING_GLOW);
        var shell = modelManager.getModel(wall ? WALL_SHELL : STANDING_SHELL);

        poseStack.pushPose();
        if (wall) {
            var facing = state.getValue(WallTorchBlock.FACING);
            poseStack.translate(0.5D, 0.5D, 0.5D);
            poseStack.mulPose(Axis.YP.rotationDegrees(-facing.toYRot() - 90.0F));
            poseStack.translate(-0.5D, -0.5D, -0.5D);
        }
        poseStack.pushPose();
        double shellX = wall ? (2.0D + Math.sin(Math.PI / 8.0D) * 2.0D) / 16.0D : 0.5D;
        double shellY = wall ? (9.0D + Math.cos(Math.PI / 8.0D) * 2.0D) / 16.0D : 9.0D / 16.0D;
        poseStack.translate(shellX, shellY, 0.5D);
        poseStack.scale(-1.0F, -1.0F, -1.0F);
        poseStack.translate(-shellX, -shellY, -0.5D);
        this.renderer.getModelRenderer().renderModel(
                poseStack.last(),
                buffers.getBuffer(this.cubeRenderType),
                state,
                shell,
                1.0F, 1.0F, 1.0F,
                LightTexture.FULL_BRIGHT,
                packedOverlay);
        poseStack.popPose();
        this.renderer.getModelRenderer().renderModel(
                poseStack.last(),
                buffers.getBuffer(this.renderType),
                state,
                model,
                1.0F, 1.0F, 1.0F,
                LightTexture.FULL_BRIGHT,
                packedOverlay);
        poseStack.popPose();
    }
}
