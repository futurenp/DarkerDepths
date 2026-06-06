package com.naterbobber.darkerdepths.client.screen_effects.render;

import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.client.screen_effects.SoulBindingBlackoutHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.event.RenderGuiLayerEvent;

import java.util.ArrayList;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class SoulBindingBlackoutRenderer {
    private static int currentFrame = 0;
    public static final List<ResourceLocation> ANIMATION_FRAMES = new ArrayList<>();

    static {
        for (int i = 1; i <= 200; i++) {
            var frameLocation = DarkerDepths.id("textures/gui/death_anchor_overlay/frame_" + i + ".png");
            ANIMATION_FRAMES.add(frameLocation);
        }
    }

    public static void updateFrame() {
        if (currentFrame < ANIMATION_FRAMES.size() - 1) {
            currentFrame++;

        } else
        {
            SoulBindingBlackoutHandler.stopOverlay();
        }
    }

    public static void render(GuiGraphics guiGraphics, Window window) {
        if(!SoulBindingBlackoutHandler.isActive()) return;
        var poseStack = guiGraphics.pose();
        poseStack.pushPose();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        renderTexturedOverlay(guiGraphics, window.getGuiScaledWidth(), window.getGuiScaledHeight());
        RenderSystem.disableBlend();

        poseStack.popPose();
    }

    private static void renderTexturedOverlay(GuiGraphics guiGraphics, int screenWidth, int screenHeight) {
        if (ANIMATION_FRAMES.isEmpty()) return;

        ResourceLocation currentTexture = ANIMATION_FRAMES.get(currentFrame);
        guiGraphics.blit(currentTexture, 0, 0, 0, 0, screenWidth, screenHeight, screenWidth, screenHeight);
    }

    public static void reset() {
        currentFrame = 1;
    }

}