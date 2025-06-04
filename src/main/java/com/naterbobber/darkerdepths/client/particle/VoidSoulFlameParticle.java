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
        super(level, x, y, z, xSpeed, ySpeed, zSpeed);

        this.xd = xSpeed;
        this.yd = ySpeed + (Math.random() * 2.0D - 1.0D) * 0.04D + 0.04D;
        this.zd = zSpeed;

        this.xd += (Math.random() * 2.0D - 1.0D) * 0.02D;
        this.zd += (Math.random() * 2.0D - 1.0D) * 0.02D;

        float darkHue = 0.6F + this.random.nextFloat() * 0.2F; //brightness


        // size and lifetime
        this.quadSize *= 0.5F + (this.random.nextFloat() / 2.0F);
        this.lifetime = (int)(8.0D / (Math.random() * 0.8D + 0.2D)) + 4;
        this.gravity = 0.0F;

        this.x = this.x + (Math.random() - 0.5D) * 0.04D;
        this.z = this.z + (Math.random() - 0.5D) * 0.04D;

    }

    @Override
    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;

        //age
        if (this.age++ >= this.lifetime) {
            this.remove();
            return;
        }


        this.move(this.xd, this.yd, this.zd);

        //drag
        this.xd *= 0.9D;
        this.yd *= 0.95D;
        this.zd *= 0.9D;

        if (this.onGround) {
            this.xd *= 0.7D;
            this.zd *= 0.7D;
        }

        float progress = (float)this.age / (float)this.lifetime;
        this.alpha = Math.max(0.0f, 1.0f - progress * progress);


    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

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
