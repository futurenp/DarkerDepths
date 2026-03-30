package com.naterbobber.darkerdepths.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.entities.ScorcherEntity;
import com.naterbobber.darkerdepths.entities.StatelessScorcherEntity;
import com.naterbobber.darkerdepths.init.DDEntityTypes;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;

// We use the GAME bus for in-game render events, restricted strictly to the physical client
@EventBusSubscriber(modid = DarkerDepths.MOD_ID, value = Dist.CLIENT)
public class CameraMobRenderer {

    // Cache the dummy entity so we don't create a new one every single frame
    private static StatelessScorcherEntity dummyZombie;

    @SubscribeEvent
    public static void onRenderLevel(RenderLevelStageEvent event) {
        // We only want to draw our dummy after the real world entities are drawn
        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_ENTITIES) return;

        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null || mc.player == null) return;

        // 1. Initialize the dummy entity once
        if (dummyZombie == null) {
            dummyZombie = DDEntityTypes.STATELESS_SCORCHER.get().create(mc.level);
        }

        PoseStack poseStack = event.getPoseStack();
        Camera camera = event.getCamera();
        Vec3 cameraPos = camera.getPosition();

        // 2. Position the mob
        // Get the direction the camera is looking, scale it by 3 blocks, and add it to the camera's world position
// Convert the JOML Vector3f to a Minecraft Vec3, then scale it!
        Vec3 forwardVector = new Vec3(camera.getLookVector()).scale(3.0);
        Vec3 targetWorldPos = cameraPos.add(forwardVector);

        // Minecraft's PoseStack during this event is already centered at the camera's exact coordinates.
        // Therefore, the coordinates we pass to the renderer must be relative to the camera, not absolute world coordinates.
        double renderX = targetWorldPos.x - cameraPos.x;
        double renderY = targetWorldPos.y - cameraPos.y;
        double renderZ = targetWorldPos.z - cameraPos.z;

        float partialTick = event.getPartialTick().getGameTimeDeltaTicks();

        float targetYaw = camera.getYRot() + 180f; // Face the camera
        float targetPitch = -camera.getXRot();
        // 3. Make the dummy look back at the camera
        dummyZombie.setYRot(targetYaw);
        dummyZombie.setXRot(targetPitch);
        dummyZombie.yHeadRot = targetYaw;
        dummyZombie.yBodyRot = targetYaw;

// 3. Set the PREVIOUS tick rotations (This is the magic fix!)
        dummyZombie.yRotO = targetYaw;
        dummyZombie.xRotO = targetPitch;
        dummyZombie.yHeadRotO = targetYaw;
        dummyZombie.yBodyRotO = targetYaw;

//        DarkerDepths.LOGGER.info(camera.getYRot() + " " + camera.getXRot() + " " + camera.getRoll());

        // Update its internal position (important for lighting/culling math internally)
        dummyZombie.setPos(targetWorldPos.x, targetWorldPos.y, targetWorldPos.z);
        dummyZombie.triggerAnim("shake_controller", "shake");

        // 4. Render the Entity
        EntityRenderDispatcher dispatcher = mc.getEntityRenderDispatcher();
        MultiBufferSource.BufferSource bufferSource = mc.renderBuffers().bufferSource();

        // We use LightTexture.FULL_BRIGHT here to make it glow in the dark.
        // If you want it to match the environment's shadows, use: dispatcher.getPackedLightCoords(dummyZombie, event.getPartialTick().getGameTimeDeltaTicks())
        int light = LightTexture.FULL_BRIGHT;

        dispatcher.render(
                dummyZombie,
                renderX,
                renderY - 1.5,
                renderZ,
                0F,
                partialTick, // Use the real partial tick here!
                poseStack,
                bufferSource,
                LightTexture.FULL_BRIGHT
        );
    }
}