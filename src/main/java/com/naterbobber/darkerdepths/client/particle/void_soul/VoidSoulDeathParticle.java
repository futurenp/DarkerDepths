package com.naterbobber.darkerdepths.client.particle.void_soul;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class VoidSoulDeathParticle extends LargeSmokeParticle {
    protected VoidSoulDeathParticle(ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, SpriteSet sprites) {
        super(level, x, y, z, xSpeed, ySpeed, zSpeed, sprites);
        gravity = 0;
    }

    @Override
    public void tick() {
        super.tick();
        this.zd += 0.004F;
        this.xd += 0.004F;
    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public Provider(SpriteSet sprites) {
            this.sprites = sprites;
        }

        public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new VoidSoulDeathParticle(level, x, y, z, 0.006F, ySpeed, 0.01F, this.sprites);
        }
    }
}
