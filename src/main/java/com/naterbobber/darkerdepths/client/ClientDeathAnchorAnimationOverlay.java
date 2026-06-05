package com.naterbobber.darkerdepths.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.naterbobber.darkerdepths.DarkerDepths;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.DeathScreen;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderGuiLayerEvent;

import java.util.ArrayList;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class ClientDeathAnchorAnimationOverlay {

    private static boolean isOverlayActive = false;
    private static int currentFrame = 1;
    private static int startFrame = 1;
    private static long lastFrameTime = 0;
    public static final int FRAME_COUNT = 200;
    private static final int FRAME_DURATION_MS = 50;
    public static final List<ResourceLocation> ANIMATION_FRAMES = new ArrayList<>();
    private static boolean hasDied = false;

    static {
        for (int i = startFrame; i <= FRAME_COUNT; i++) {
            var frameLocation = ResourceLocation.fromNamespaceAndPath(DarkerDepths.MOD_ID,
                    "textures/gui/death_anchor_overlay/frame_" + i + ".png");
            ANIMATION_FRAMES.add(frameLocation);
        }
    }
        // next on the chopping block
    @SubscribeEvent
    public void onRenderGuiOverlay(RenderGuiLayerEvent.Post event) {
        if (!isOverlayActive) return;

        var mc = Minecraft.getInstance();
        var guiGraphics = event.getGuiGraphics();
        int screenWidth = mc.getWindow().getGuiScaledWidth();
        int screenHeight = mc.getWindow().getGuiScaledHeight();

        handleRespawn();
        updateAnimation();
        renderOverlay(guiGraphics, screenWidth, screenHeight);
    }

    private static void updateAnimation() {
        Minecraft mc = Minecraft.getInstance();

        if (mc.isPaused()) {
            return;
        }

        if (mc.screen instanceof DeathScreen) {
            return;
        }

        long currentTime = System.currentTimeMillis();

        if (currentTime - lastFrameTime >= FRAME_DURATION_MS) {

            if (currentFrame < ANIMATION_FRAMES.size() - 1) {
                currentFrame++;
                lastFrameTime = currentTime;

            } else
            {
                stopOverlay();
            }
        }
    }

    private static void handleRespawn() {
        var mc = Minecraft.getInstance();
        var entity = mc.cameraEntity;
        if(entity == null) return;
        if(!entity.isAlive() && !hasDied) {
            hasDied = true;
        }
        if(entity.isAlive() && hasDied) {
            stopOverlay();
        }
    }

    private static void renderOverlay(GuiGraphics guiGraphics, int screenWidth, int screenHeight) {
        PoseStack poseStack = guiGraphics.pose();

        poseStack.pushPose();

        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        renderTexturedOverlay(guiGraphics, screenWidth, screenHeight);
        RenderSystem.disableBlend();

        poseStack.popPose();
    }

    private static void renderTexturedOverlay(GuiGraphics guiGraphics, int screenWidth, int screenHeight) {
        if (ANIMATION_FRAMES.isEmpty()) return;

        ResourceLocation currentTexture = ANIMATION_FRAMES.get(currentFrame);
        guiGraphics.blit(currentTexture, 0, 0, 0, 0, screenWidth, screenHeight, screenWidth, screenHeight);
    }

    @OnlyIn(Dist.CLIENT)
    public static void startOverlay() {
        isOverlayActive = true;
        startFrame = 0;
        currentFrame = startFrame;
        lastFrameTime = System.currentTimeMillis();
    }

    public static void stopOverlay() {
        isOverlayActive = false;
        currentFrame = 0;
        hasDied = false;
    }
}