package com.naterbobber.darkerdepths.client.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleOptions;

public class DDClientParticleUtils {
    public static void addDistanceParticle(
            ParticleOptions options,
            double maxDistance,
            double x,
            double y,
            double z,
            double xSpeed,
            double ySpeed,
            double zSpeed
    ) {
        var minecraft = Minecraft.getInstance();
        var camera = minecraft.gameRenderer.getMainCamera();

        if (camera.getPosition().distanceToSqr(x, y, z) > maxDistance * maxDistance) {
            return;
        }

        minecraft.particleEngine.createParticle(
                options,
                x,
                y,
                z,
                xSpeed,
                ySpeed,
                zSpeed
        );
    }
}
