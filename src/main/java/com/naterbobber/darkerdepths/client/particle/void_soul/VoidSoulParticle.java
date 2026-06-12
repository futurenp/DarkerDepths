package com.naterbobber.darkerdepths.client.particle.void_soul;

import com.naterbobber.darkerdepths.client.render.DDParticleRenderTypes;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class VoidSoulParticle extends TextureSheetParticle {
    private final SpriteSet sprites;
    private boolean reachedSpawnSize = false;
    private final float maxQuadSize;

    protected VoidSoulParticle(ClientLevel level, double x, double y, double z,
                               double xSpeed, double ySpeed, double zSpeed, SpriteSet sprites) {
        super(level, x, y, z, xSpeed, ySpeed, zSpeed);
        RandomSource random = level.getRandom();
        this.sprites = sprites;
        this.lifetime = 80;
        this.quadSize = 0.05F;
        this.maxQuadSize = 0.25F + random.nextFloat() * 0.125F;
        this.friction = 0.975F;
        this.hasPhysics = true;

        this.xd = xSpeed + (random.nextDouble() - 0.5) * 0.025;
        this.yd = 0F;
        this.zd = xSpeed + (random.nextDouble() - 0.5) * 0.025;
        this.gravity = 0.01F;
    }

    @Override
    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;

        if (this.age++ >= this.lifetime) {
            this.remove();
        } else {
            this.setSpriteFromAge(this.sprites);

            if(!reachedSpawnSize) {
                float newQuadSize = quadSize * 1.25F;

                if(newQuadSize >= maxQuadSize) {
                    quadSize = maxQuadSize;
                    reachedSpawnSize = true;
                } else {
                    quadSize = newQuadSize;
                }
            }

            this.yd -= 0.04 * (double)this.gravity;
            this.move(this.xd, this.yd, this.zd);
            if (this.speedUpWhenYMotionIsBlocked && this.y == this.yo) {
                this.xd *= 1.01;
                this.zd *= 1.01;
            }

            this.xd *= friction;
            this.yd *= friction;
            this.zd *= friction;
            if (this.onGround) {
                this.xd *= 0.7F;
                this.zd *= 0.7F;
            }

            this.quadSize *= 0.995F;

            float progress = (float) age / lifetime;
            if(progress > 0.75F) {
                float newAlpha = ((float) (lifetime - age) / (lifetime) * 4);
                setAlpha(newAlpha);
            }
        }
    }

    @Override
    protected int getLightColor(float partialTick) {
        return LightTexture.pack(15, 15);
    }

    @Override
    public ParticleRenderType getRenderType() {
        return DDParticleRenderTypes.PARTICLE_GLOW_THROUGH_FOG;
    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public Provider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Override
        public TextureSheetParticle createParticle(SimpleParticleType type, ClientLevel level,
                                                   double x, double y, double z,
                                                   double xSpeed, double ySpeed, double zSpeed) {
            var particle = new VoidSoulParticle(level, x, y, z, xSpeed, ySpeed, zSpeed, spriteSet);
            particle.pickSprite(this.spriteSet);
            return particle;
        }
    }
}