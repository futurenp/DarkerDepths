package com.naterbobber.darkerdepths.client.screen_effects;

import com.naterbobber.darkerdepths.client.screen_effects.render.SoulBindingBlackoutRenderer;
import com.naterbobber.darkerdepths.init.DDSoundEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.DeathScreen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.sounds.SoundSource;

public class SoulBindingBlackoutHandler {
    private static boolean hasDied = false;
    private static boolean isActive = false;

    public static void tick() {
        var mc = Minecraft.getInstance();

        if (mc.isPaused() || mc.screen instanceof DeathScreen || !isActive()) {
            return;
        }

        handleRespawn();
        
        if(isActive()) {
            SoulBindingBlackoutRenderer.updateFrame();
        }
    }

    public static void start() {
        var player = Minecraft.getInstance().player;

        if (player == null) return;

        player.level().playSound(
                player,
                player.getX(), player.getY(), player.getZ(),
                DDSoundEvents.BLOCK_DEATH_ANCHOR_SOUL_BINDING.get(),
                SoundSource.PLAYERS,
                1.0f,
                1.0f
        );

        isActive = true;
    }

    public static void stopOverlay() {
        isActive = false;
        hasDied = false;
        SoulBindingBlackoutRenderer.reset();
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

    public static boolean isActive() {
        return isActive;
    }
}
