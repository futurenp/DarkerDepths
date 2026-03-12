package com.naterbobber.darkerdepths.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

@OnlyIn(Dist.CLIENT)
public class GeyserBurstSmokeParticle extends TextureSheetParticle {
    private static final int NORMAL_LIFETIME = 10;
    private static final int BOOSTED_LIFETIME = 16;
    private static final float NORMAL_SIZE = 0.6f;
    private static final float BOOSTED_SIZE = 0.75f;

    protected GeyserBurstSmokeParticle(ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, int lifetime, float quadsize) {
        super(level, x, y, z, xSpeed, ySpeed, zSpeed);
        this.x = x;
        this.y = y;
        this.z = z;
        this.xd = xSpeed;
        this.yd = ySpeed;
        this.zd = zSpeed;
        this.lifetime = lifetime;
        this.quadSize = quadsize;
        this.gravity = 0.035F;
    }

    protected static GeyserBurstSmokeParticle normalBurstParticle(ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
        return new GeyserBurstSmokeParticle(level, x, y, z, xSpeed, ySpeed, zSpeed, NORMAL_LIFETIME, NORMAL_SIZE);
    }
    protected static GeyserBurstSmokeParticle boostedBurstParticle(ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
        return new GeyserBurstSmokeParticle(level, x, y, z, xSpeed * 1.2, ySpeed * 1.2, zSpeed * 1.2, BOOSTED_LIFETIME, BOOSTED_SIZE);
    }

    @Override
    public void tick() {
        super.tick();

        this.scale(0.93F);
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    @Override
    public int getLightColor(float partialTick) {
        var level = (int)(((float)(lifetime - age) / (float)lifetime) * 255F);
        if(level < 100) {
            level = 100;
        }
        return level;
    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<SimpleParticleType> {
        protected final SpriteSet spriteSet;

        public Provider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Override
        public @Nullable Particle createParticle(SimpleParticleType simpleParticleType, ClientLevel clientLevel, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            var particle = normalBurstParticle(clientLevel, x, y, z, xSpeed, ySpeed, zSpeed);
            particle.pickSprite(this.spriteSet);
            return particle;
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static class BoostedProvider implements ParticleProvider<SimpleParticleType> {
        protected final SpriteSet spriteSet;

        public BoostedProvider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Override
        public @Nullable Particle createParticle(SimpleParticleType simpleParticleType, ClientLevel clientLevel, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            var particle = boostedBurstParticle(clientLevel, x, y, z, xSpeed, ySpeed, zSpeed);
            particle.pickSprite(this.spriteSet);
            return particle;
        }
    }


}
