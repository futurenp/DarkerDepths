package com.naterbobber.darkerdepths.events;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.naterbobber.darkerdepths.DarkerDepths;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.DeathScreen;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = DarkerDepths.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ClientDeathAnchorAnimationOverlay {

    private static boolean isOverlayActive = false;
    private static int currentFrame = 1;
    private static int startFrame = 1;
    private static long lastFrameTime = 0;
    public static final int FRAME_COUNT = 200;
    private static final int FRAME_DURATION_MS = 50;
    public static final List<ResourceLocation> ANIMATION_FRAMES = new ArrayList<>();

    static {
        for (int i = startFrame; i < FRAME_COUNT; i++) {
            ResourceLocation frameLocation = ResourceLocation.fromNamespaceAndPath(DarkerDepths.MOD_ID,
                    "textures/gui/death_anchor_overlay/frame_" + i + ".png");
            ANIMATION_FRAMES.add(frameLocation);
        }
    }

    @SubscribeEvent
    public static void onRenderGuiOverlay(RenderGuiOverlayEvent.Post event) {
        if (!isOverlayActive) return;

        Minecraft mc = Minecraft.getInstance();
        GuiGraphics guiGraphics = event.getGuiGraphics();

        int screenWidth = mc.getWindow().getGuiScaledWidth();
        int screenHeight = mc.getWindow().getGuiScaledHeight();

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

        if (!mc.isWindowActive()) {
            return;
        }

        long currentTime = System.currentTimeMillis();

        if (currentTime - lastFrameTime >= FRAME_DURATION_MS) {
            currentFrame++;
            if (currentFrame >= ANIMATION_FRAMES.size()) {
                stopOverlay();
            }
            lastFrameTime = currentTime;
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
    }

    public static boolean isOverlayActive() {
        return isOverlayActive;
    }

    public static int getCurrentFrame() {
        return currentFrame;
    }

    public static void setFrame(int frame) {
        if (frame >= 0 && frame < ANIMATION_FRAMES.size()) {
            currentFrame = frame;
        }
    }
}