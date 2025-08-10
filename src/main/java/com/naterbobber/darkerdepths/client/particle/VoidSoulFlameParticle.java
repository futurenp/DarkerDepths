package com.naterbobber.darkerdepths.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;


@OnlyIn(Dist.CLIENT)
public class VoidSoulFlameParticle extends TextureSheetParticle {
    protected VoidSoulFlameParticle(ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
        super(level, x + (Math.random() / 20 - 0.025) , y - .05f, z + (Math.random() / 20 - 0.025), xSpeed, ySpeed, zSpeed);

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

        final float growthRate = 1.0025f;
        final float alphaRate = 0.05f;

        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;

        this.quadSize *= growthRate;
        this.alpha *= Math.max(0.1f, 1.0f - alphaRate);
    }

    @Override
    public ParticleRenderType getRenderType(){ return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT; }

    @OnlyIn(Dist.CLIENT)
    public static class VoidSoulFlameFactory implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public VoidSoulFlameFactory(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Override
        public TextureSheetParticle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            VoidSoulFlameParticle particle = new VoidSoulFlameParticle(level, x, y, z, xSpeed, ySpeed, zSpeed);
            particle.pickSprite(this.spriteSet);
            return particle;
        }
    }
}
