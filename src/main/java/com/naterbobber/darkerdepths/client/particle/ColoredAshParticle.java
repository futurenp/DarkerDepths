package com.naterbobber.darkerdepths.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.BaseAshSmokeParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.FastColor;
import net.minecraft.util.RandomSource;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ColoredAshParticle extends BaseAshSmokeParticle {
    private static final int DEFAULT_COLOR = 12235202;

    protected ColoredAshParticle(ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, float quadSizeMultiplier, SpriteSet sprites) {
        super(level, x, y, z, 0.1F, -0.1F, 0.1F, xSpeed, ySpeed, zSpeed, quadSizeMultiplier, sprites, 0.0F, 20, 0.0125F, false);
        this.rCol = (float) FastColor.ARGB32.red(DEFAULT_COLOR) / 255.0F;
        this.gCol = (float) FastColor.ARGB32.green(DEFAULT_COLOR) / 255.0F;
        this.bCol = (float) FastColor.ARGB32.blue(DEFAULT_COLOR) / 255.0F;
    }

    protected ColoredAshParticle(ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, float quadSizeMultiplier, SpriteSet sprites, float rCol, float gCol, float bCol) {
        super(level, x, y, z, 0.1F, -0.1F, 0.1F, xSpeed, ySpeed, zSpeed, quadSizeMultiplier, sprites, 0.0F, 20, 0.0125F, false);
        this.rCol = rCol;
        this.gCol = gCol;
        this.bCol = bCol;
    }

    protected static ColoredAshParticle coloredAshParticle(ClientLevel level, double x, double y, double z, float quadSizeMultiplier, SpriteSet sprites, float rCol, float gCol, float bCol) {
        RandomSource randomsource = level.random;
        double d0 = (double)randomsource.nextFloat() * -1.9 * (double)randomsource.nextFloat() * 0.1;
        double d1 = (double)randomsource.nextFloat() * (double)-0.5F * (double)randomsource.nextFloat() * 0.1 * (double)5.0F;
        double d2 = (double)randomsource.nextFloat() * -1.9 * (double)randomsource.nextFloat() * 0.1;
        return new ColoredAshParticle(level, x, y, z, d0, d1, d2, quadSizeMultiplier, sprites, rCol, gCol, bCol);
    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;
        float rCol;
        float gCol;
        float bCol;

        public Provider(SpriteSet sprites, float rCol, float gCol, float bCol) {
            this.sprites = sprites;
            this.rCol = rCol;
            this.gCol = gCol;
            this.bCol = bCol;
        }

        public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return coloredAshParticle(level,x, y, z, 1.0F, this.sprites, rCol, gCol, bCol);
        }
    }
}
