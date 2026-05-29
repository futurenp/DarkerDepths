package com.naterbobber.darkerdepths.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.FastColor;
import net.minecraft.util.RandomSource;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ColoredAshParticle extends BaseAshSmokeParticle {
    private final BrightnessBehavior brightnessBehavior;

    protected ColoredAshParticle(ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, float quadSizeMultiplier, SpriteSet sprites, float rCol, float gCol, float bCol, BrightnessBehavior brightnessBehavior) {
        super(level, x, y, z, 0.1F, -0.1F, 0.1F, xSpeed, ySpeed, zSpeed, quadSizeMultiplier, sprites, 0.0F, 20, 0.0125F, false);
        this.rCol = rCol;
        this.gCol = gCol;
        this.bCol = bCol;
        this.brightnessBehavior = brightnessBehavior;
    }

    protected static ColoredAshParticle coloredAshParticle(ClientLevel level, double x, double y, double z, float quadSizeMultiplier, SpriteSet sprites, float rCol, float gCol, float bCol, BrightnessBehavior brightnessBehavior) {
        RandomSource randomsource = level.random;
        double d0 = randomsource.nextDouble() * -1.9 * randomsource.nextDouble() * 0.1;
        double d1 = randomsource.nextDouble() * -0.5 * randomsource.nextDouble() * 0.1 * 5.0;
        double d2 = randomsource.nextDouble() * -1.9 * randomsource.nextDouble() * 0.1;
        return new ColoredAshParticle(level, x, y, z, d0, d1, d2, quadSizeMultiplier * 1.5F, sprites, rCol, gCol, bCol, brightnessBehavior);
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    @Override
    public int getLightColor(float partialTick) {
        switch (brightnessBehavior) {
            case FULL_BRIGHT -> {
                return 255;
            }
            case FADE -> {
                return (int)(((float)(lifetime - age) / (float)lifetime) * 255F);
            }
            case STANDARD -> {
                return super.getLightColor(partialTick);
            }
        }

        return 0;
    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;
        private final float rCol;
        private final float gCol;
        private final float bCol;
        private final BrightnessBehavior brightnessBehavior;


        public Provider(SpriteSet sprites, float rCol, float gCol, float bCol, BrightnessBehavior brightnessBehavior) {
            this.sprites = sprites;
            this.rCol = rCol;
            this.gCol = gCol;
            this.bCol = bCol;
            this.brightnessBehavior = brightnessBehavior;
        }

        public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return coloredAshParticle(level,x, y, z, 1.0F, this.sprites, rCol, gCol, bCol, brightnessBehavior);
        }
    }

    public enum BrightnessBehavior {
        FULL_BRIGHT,
        FADE,
        STANDARD
    }
}
