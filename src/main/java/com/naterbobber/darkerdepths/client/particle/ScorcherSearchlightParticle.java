package com.naterbobber.darkerdepths.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.FlameParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class ScorcherSearchlightParticle extends FlameParticle {
    protected ScorcherSearchlightParticle(ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
        super(level, x, y, z, xSpeed, ySpeed, zSpeed);
        this.rCol = 1F;
        this.gCol = 0.7F;
        this.bCol = 0.33F;
        this.quadSize = 0.45F + level.getRandom().nextFloat()/4;
        this.lifetime = (int)((double)6.0F / (Math.random() * 0.8 + 0.2)) + 4;
    }

    @Override
    public int getLightColor(float partialTick) {
        return (int)(((float)(lifetime - age) / (float)lifetime) * 255F);
    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprite;

        public Provider(SpriteSet sprites) {
            this.sprite = sprites;
        }

        public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            ScorcherSearchlightParticle particle = new ScorcherSearchlightParticle(level, x, y, z, xSpeed, ySpeed, zSpeed);
            particle.pickSprite(this.sprite);
            return particle;
        }
    }
}
