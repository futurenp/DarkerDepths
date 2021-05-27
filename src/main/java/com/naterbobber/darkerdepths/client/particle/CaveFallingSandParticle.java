package com.naterbobber.darkerdepths.client.particle;

import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.RisingParticle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

//<>

@OnlyIn(Dist.CLIENT)
public class CaveFallingSandParticle extends RisingParticle {
    public CaveFallingSandParticle(ClientWorld world, double x, double y, double z, double motionX, double motionY, double motionZ, float scale, IAnimatedSprite spriteWithAge) {
        super(world, x, y, z, 0.1F, -0.1F, 0.1F, motionX, motionY, motionZ, scale, spriteWithAge, 1.0F, 20, -0.004D, false);

        this.particleAlpha = 1.0F;

        this.particleRed = 1.0F;
        this.particleGreen = 1.0F;
        this.particleBlue = 1.0F;

        this.particleScale = 0.1F;
    }

    @OnlyIn(Dist.CLIENT)
    public static class Factory implements IParticleFactory<BasicParticleType> {
        private final IAnimatedSprite sprite;

        public Factory(IAnimatedSprite sprite) {
            this.sprite = sprite;
        }

        @Nullable
        @Override
        public Particle makeParticle(BasicParticleType typeIn, ClientWorld worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new CaveFallingSandParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, 0.8F, this.sprite);
        }
    }
}