package com.naterbobber.darkerdepths.client.particle;

import com.naterbobber.darkerdepths.core.registries.DDParticleTypes;
import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.IParticleRenderType;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.SpriteTexturedParticle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.IParticleData;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

//<>

@OnlyIn(Dist.CLIENT)
public class DrippingParticle extends SpriteTexturedParticle {
    private final Fluid fluid;
    protected boolean fullBright;

    private DrippingParticle(ClientWorld world, double x, double y, double z, Fluid fluid) {
        super(world, x, y, z);
        this.setSize(0.01F, 0.01F);
        this.particleGravity = 0.06F;
        this.fluid = fluid;
    }

    @Override
    public IParticleRenderType getRenderType() {
        return IParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    @Override
    protected int getBrightnessForRender(float partialTick) {
        return this.fullBright ? 240 : super.getBrightnessForRender(partialTick);
    }

    @Override
    public void tick() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        this.ageParticle();
        if (!this.isExpired) {
            this.motionY -= this.particleGravity;
            this.move(this.motionX, this.motionY, this.motionZ);
            this.updateMotion();
            if (!this.isExpired) {
                this.motionX *= 0.98F;
                this.motionY *= 0.98F;
                this.motionZ *= 0.98F;
                BlockPos blockpos = new BlockPos(this.posX, this.posY, this.posZ);
                FluidState fluidstate = this.world.getFluidState(blockpos);
                if (fluidstate.getFluid() == this.fluid && this.posY < (double)((float)blockpos.getY() + fluidstate.getActualHeight(this.world, blockpos))) {
                    this.setExpired();
                }
            }
        }
    }

    protected void ageParticle() {
        if (this.maxAge-- <= 0) {
            this.setExpired();
        }
    }

    protected void updateMotion() {
    }

    @OnlyIn(Dist.CLIENT)
    static class Dripping extends DrippingParticle {
        protected final IParticleData nextParticle;

        Dripping(ClientWorld world, double x, double y, double z, Fluid fluid, IParticleData nextParticle) {
            super(world, x, y, z, fluid);
            this.nextParticle = nextParticle;
            this.particleGravity *= 0.02F;
            this.maxAge = 40;
        }

        @Override
        protected void ageParticle() {
            if (this.maxAge-- <= 0) {
                this.setExpired();
                this.world.addParticle(this.nextParticle, this.posX, this.posY, this.posZ, this.motionX, this.motionY, this.motionZ);
            }
        }

        @Override
        protected void updateMotion() {
            this.motionX *= 0.02D;
            this.motionY *= 0.02D;
            this.motionZ *= 0.02D;
        }
    }

    @OnlyIn(Dist.CLIENT)
    static class FallingLiquid extends DrippingParticle.Falling {
        protected final IParticleData nextParticle;

        FallingLiquid(ClientWorld world, double x, double y, double z, Fluid fluid, IParticleData nextParticle) {
            super(world, x, y, z, fluid);
            this.nextParticle = nextParticle;
        }

        @Override
        protected void updateMotion() {
            if (this.onGround) {
                this.setExpired();
                this.world.addParticle(this.nextParticle, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
            }
        }
    }


    @OnlyIn(Dist.CLIENT)
    static class Falling extends DrippingParticle {
        Falling(ClientWorld world, double x, double y, double z, Fluid fluid) {
            this(world, x, y, z, fluid, (int)(64.0D/ (Math.random() * 0.8D + 0.2D)));
        }

        Falling(ClientWorld world, double x, double y, double z, Fluid fluid, int maxAge) {
            super(world, x, y, z, fluid);
            this.maxAge = maxAge;
        }

        @Override
        protected void updateMotion() {
            if (this.onGround) {
                this.setExpired();
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    static class Landing extends DrippingParticle {
        private Landing(ClientWorld world, double x, double y, double z, Fluid fluid) {
            super(world, x, y, z, fluid);
            this.maxAge = (int)(16.0D / (Math.random() * 0.8D + 0.2D));
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static class DrippingResinFactory implements IParticleFactory<BasicParticleType> {
        protected final IAnimatedSprite spriteSet;

        public DrippingResinFactory(IAnimatedSprite spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Nullable
        @Override
        public Particle makeParticle(BasicParticleType typeIn, ClientWorld worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            DrippingParticle dripping = new Dripping(worldIn, x, y, z, Fluids.EMPTY, DDParticleTypes.FALLING_RESIN.get());
            dripping.fullBright = true;
            dripping.particleGravity *= 0.01F;
            dripping.maxAge = 100;
            dripping.setColor(0.97F, 0.56F, 0.22F);
            dripping.selectSpriteRandomly(this.spriteSet);
            return dripping;
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static class FallingResinFactory implements IParticleFactory<BasicParticleType> {
        protected final IAnimatedSprite spriteSet;

        public FallingResinFactory(IAnimatedSprite spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Nullable
        @Override
        public Particle makeParticle(BasicParticleType typeIn, ClientWorld worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            DrippingParticle falling = new DrippingParticle.FallingLiquid(worldIn, x, y, z, Fluids.EMPTY, DDParticleTypes.LANDING_RESIN.get());
            falling.fullBright = true;
            falling.particleGravity = 0.01F;
            falling.setColor(0.97F, 0.56F, 0.22F);
            falling.selectSpriteRandomly(this.spriteSet);
            return falling;
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static class LandingResinFactory implements IParticleFactory<BasicParticleType> {
        protected final IAnimatedSprite spriteSet;

        public LandingResinFactory(IAnimatedSprite spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Nullable
        @Override
        public Particle makeParticle(BasicParticleType typeIn, ClientWorld worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            DrippingParticle landing = new DrippingParticle.Landing(worldIn, x, y, z, Fluids.EMPTY);
            landing.fullBright = true;
            landing.maxAge = (int)(28.0D / (Math.random() * 0.8D + 0.2D));
            landing.setColor(0.97F, 0.56F, 0.22F);
            landing.selectSpriteRandomly(this.spriteSet);
            return landing;
        }
    }
}