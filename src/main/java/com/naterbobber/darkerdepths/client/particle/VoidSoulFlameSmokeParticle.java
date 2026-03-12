package com.naterbobber.darkerdepths.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.SimpleParticleType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;


@OnlyIn(Dist.CLIENT)
public class VoidSoulFlameSmokeParticle extends TextureSheetParticle {
    protected VoidSoulFlameSmokeParticle(ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
        super(level, x, y, z, xSpeed, ySpeed, zSpeed);

        double randomRange = 0.025;
        this.x += (Math.random() * randomRange - randomRange/2);
        this.z += (Math.random() * randomRange - randomRange/2);
        this.y -= .02f;
        this.quadSize *= 2f + (this.random.nextFloat()/2);
        this.lifetime = 20;
        this.gravity = 0.0F;
    }

    @Override
    public void tick() {
        if (this.age++ >= this.lifetime) {
            this.remove();
            return;
        }

        final float growthRate = 0.975f;
        final float compensateHeight = 0.00f;

        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;

        this.y += compensateHeight;

        this.quadSize *= growthRate;
    }

    @Override
    public ParticleRenderType getRenderType(){ return ParticleRenderType.PARTICLE_SHEET_LIT; }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public Provider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Override
        public TextureSheetParticle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            VoidSoulFlameSmokeParticle particle = new VoidSoulFlameSmokeParticle(level, x, y, z, xSpeed, ySpeed, zSpeed);
            particle.pickSprite(this.spriteSet);
            return particle;
        }
    }
}
