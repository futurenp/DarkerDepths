package com.naterbobber.darkerdepths.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.client.fog.modifiers.ScorcherFlashModifier;
import com.naterbobber.darkerdepths.entities.ScorcherEntity;
import com.naterbobber.darkerdepths.init.DDEntityTypes;
import com.naterbobber.darkerdepths.init.DDParticleTypes; // Make sure this is imported
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;

@OnlyIn(Dist.CLIENT)
@EventBusSubscriber(modid = DarkerDepths.MOD_ID, value = Dist.CLIENT)
public class ScorcherCameraRenderer {

    private static ScorcherEntity scorcher;
    private static boolean wasFlashedLastFrame = false;

    private static int lastParticleTick = -1;

    @SubscribeEvent
    public static void onRenderLevel(RenderLevelStageEvent event) {
        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_LEVEL) return;

        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null || mc.player == null || !mc.player.isAlive() || !ScorcherFlashModifier.isFlashed()) {
            if (scorcher != null) {
                scorcher = null;
                wasFlashedLastFrame = false;
            }
            return;
        }

        if (scorcher == null) {
            scorcher = DDEntityTypes.SCORCHER.get().create(mc.level);
            if (scorcher != null) {
                scorcher.setId(999999);
                scorcher.triggerAnim("shake_controller", "attack");
                wasFlashedLastFrame = true;
            }
        }

        if(scorcher == null) {
            return;
        }

        Vec3 camPos = event.getCamera().getPosition();

        if (!mc.isPaused() && mc.player.tickCount != lastParticleTick) {
            lastParticleTick = mc.player.tickCount;

            Vector3f forward = event.getCamera().getLookVector();
            Vector3f up = event.getCamera().getUpVector();
            Vector3f left = event.getCamera().getLeftVector();

            double spawnDistance = 2.0;
            float angle = (float) (Math.random() * Math.PI * 2);
            float radialSpeed = 0.075f + (float) Math.random() * 0.025f;
            float approachSpeed = 0.15f + (float) Math.random() * 0.075f;

            double driftX = up.x() * Math.sin(angle) * radialSpeed + left.x() * Math.cos(angle) * radialSpeed;
            double driftY = up.y() * Math.sin(angle) * radialSpeed + left.y() * Math.cos(angle) * radialSpeed;
            double driftZ = up.z() * Math.sin(angle) * radialSpeed + left.z() * Math.cos(angle) * radialSpeed;

            double velX = driftX - (forward.x() * approachSpeed);
            double velY = driftY - (forward.y() * approachSpeed);
            double velZ = driftZ - (forward.z() * approachSpeed);

            double spawnX = camPos.x + forward.x() * spawnDistance;
            double spawnY = camPos.y + forward.y() * spawnDistance;
            double spawnZ = camPos.z + forward.z() * spawnDistance;

            mc.level.addAlwaysVisibleParticle(DDParticleTypes.SCORCHER_SEARCHLIGHT.get(), true, spawnX, spawnY, spawnZ, velX, velY, velZ);

        }

        scorcher.setPos(camPos.x, camPos.y, camPos.z);
        scorcher.xo = camPos.x;
        scorcher.yo = camPos.y;
        scorcher.zo = camPos.z;

        float fixedYaw = 180f;
        scorcher.setYRot(fixedYaw);
        scorcher.yRotO = fixedYaw;
        scorcher.setXRot(0);
        scorcher.xRotO = 0;
        scorcher.yHeadRot = fixedYaw;
        scorcher.yHeadRotO = fixedYaw;
        scorcher.yBodyRot = fixedYaw;
        scorcher.yBodyRotO = fixedYaw;

        scorcher.tickCount = mc.player.tickCount;

        EntityRenderDispatcher dispatcher = mc.getEntityRenderDispatcher();
        MultiBufferSource.BufferSource bufferSource = mc.renderBuffers().bufferSource();

        float pTick = mc.getTimer().getGameTimeDeltaPartialTick(true);

        int remaining = ScorcherFlashModifier.getTicksRemaining();
        int maxTime = ScorcherFlashModifier.getMaxTicks();
        float elapsedTicks = (maxTime - remaining) + pTick;
        float zTranslation = calculateZTranslation(elapsedTicks, maxTime);

        PoseStack screenStack = new PoseStack();
        screenStack.translate(0.0, -0.5, zTranslation);

        bufferSource.endBatch();

        RenderSystem.clear(GL11.GL_DEPTH_BUFFER_BIT, Minecraft.ON_OSX);

        dispatcher.render(
                scorcher,
                0.0,
                0.0,
                0.0,
                fixedYaw,
                pTick,
                screenStack,
                bufferSource,
                LightTexture.FULL_BRIGHT
        );

        bufferSource.endBatch();
    }

    private static float calculateZTranslation(float time, float maxTime) {
        if (maxTime < 20) return -1.5f;

        float rushInDuration = 8.0f;
        float settleDuration = 8.0f;
        float jumpScareDuration = 6.0f;

        if (time < rushInDuration) {
            float t = time / rushInDuration;
            float ease = 1.0f - (float)Math.pow(1.0f - t, 3);
            return -60.0f + (59.0f * ease);
        }
        else if (time < rushInDuration + settleDuration) {
            float t = (time - rushInDuration) / settleDuration;
            float ease = t * t * (3.0f - 2.0f * t);
            return -1.0f - (0.5f * ease);
        }
        else if (time < maxTime - jumpScareDuration) {
            return -1.5f;
        }
        else {
            float t = (time - (maxTime - jumpScareDuration)) / jumpScareDuration;
            float ease = t * t * t;
            return -1.5f + (3.5f * ease);
        }
    }
}