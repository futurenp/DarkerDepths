package com.naterbobber.darkerdepths.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ScorcherSearchlightParticle extends RisingParticle {
    protected ScorcherSearchlightParticle(ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
        super(level, x, y, z, xSpeed, ySpeed, zSpeed);
        this.quadSize = 0.15F + level.getRandom().nextFloat()/10;
        this.lifetime = (int)((double)6.0F / (Math.random() * 0.8 + 0.2)) + 4;
    }

    @Override
    public int getLightColor(float partialTick) {
        return (int)(((float)(lifetime - age) / (float)lifetime) * 255F/1.5 + 255/3F);
    }

    @Override
    public void tick() {
        super.tick();
        if(rCol < 1F) {
            this.rCol += 0.1f;
        }
    }

    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    public void move(double pX, double pY, double pZ) {
        this.setBoundingBox(this.getBoundingBox().move(pX, pY, pZ));
        this.setLocationFromBoundingbox();
    }

    public float getQuadSize(float pScaleFactor) {
        float $$1 = ((float)this.age + pScaleFactor) / (float)this.lifetime;
        return this.quadSize * (1.0F - $$1 * $$1 * 0.5F);
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
