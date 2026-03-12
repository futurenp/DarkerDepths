package com.naterbobber.darkerdepths.client.particle;

import com.naterbobber.darkerdepths.init.DDParticleTypes;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

@OnlyIn(Dist.CLIENT)
public class DrippingParticle extends TextureSheetParticle {
    private final Fluid fluid;
    protected boolean fullBright;

    public DrippingParticle(ClientLevel p_108323_, double p_108324_, double p_108325_, double p_108326_, Fluid fluid) {
        super(p_108323_, p_108324_, p_108325_, p_108326_);
        this.setSize(0.01F, 0.01F);
        this.gravity = 0.06F;
        this.fluid = fluid;
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    @Override
    protected int getLightColor(float p_107249_) {
        return this.fullBright ? 240 : super.getLightColor(p_107249_);
    }

    @Override
    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        this.ageParticle();
        if (!this.removed) {
            this.yd -= this.gravity;
            this.move(this.xd, this.yd, this.zd);
            this.updateMotion();
            if (!this.removed) {
                this.xd *= 0.98F;
                this.yd *= 0.98F;
                this.zd *= 0.98F;
                BlockPos blockpos = BlockPos.containing(this.x, this.y, this.z);
                FluidState fluidstate = this.level.getFluidState(blockpos);
                if (fluidstate.getType() == this.fluid && this.y < (double)((float)blockpos.getY() + fluidstate.getHeight(this.level, blockpos))) {
                    this.remove();
                }
            }
        }
    }

    protected void ageParticle() {
        if (this.lifetime-- <= 0) {
            this.remove();
        }
    }

    protected void updateMotion() {
    }

    @OnlyIn(Dist.CLIENT)
    static class Dripping extends DrippingParticle {
        protected final ParticleOptions nextParticle;

        Dripping(ClientLevel world, double x, double y, double z, Fluid fluid, ParticleOptions nextParticle) {
            super(world, x, y, z, fluid);
            this.nextParticle = nextParticle;
            this.gravity *= 0.02F;
            this.lifetime = 40;
        }

        @Override
        protected void ageParticle() {
            if (this.lifetime-- <= 0) {
                this.remove();
                this.level.addParticle(this.nextParticle, this.x, this.y, this.z, this.xd, this.yd, this.zd);
            }
        }

        @Override
        protected void updateMotion() {
            this.xd *= 0.02D;
            this.yd *= 0.02D;
            this.zd *= 0.02D;
        }
    }

    @OnlyIn(Dist.CLIENT)
    static class FallingLiquid extends Falling {
        protected final ParticleOptions nextParticle;

        FallingLiquid(ClientLevel world, double x, double y, double z, Fluid fluid, ParticleOptions nextParticle) {
            super(world, x, y, z, fluid);
            this.nextParticle = nextParticle;
        }

        @Override
        protected void updateMotion() {
            if (this.onGround) {
                this.remove();
                this.level.addParticle(this.nextParticle, this.x, this.y, this.z, 0.0D, 0.0D, 0.0D);
            }
        }
    }


    @OnlyIn(Dist.CLIENT)
    static class Falling extends DrippingParticle {
        Falling(ClientLevel world, double x, double y, double z, Fluid fluid) {
            this(world, x, y, z, fluid, (int)(64.0D/ (Math.random() * 0.8D + 0.2D)));
        }

        Falling(ClientLevel world, double x, double y, double z, Fluid fluid, int lifetime) {
            super(world, x, y, z, fluid);
            this.lifetime = lifetime;
        }

        @Override
        protected void updateMotion() {
            if (this.onGround) {
                this.remove();
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    static class Landing extends DrippingParticle {
        private Landing(ClientLevel world, double x, double y, double z, Fluid fluid) {
            super(world, x, y, z, fluid);
            this.lifetime = (int)(16.0D / (Math.random() * 0.8D + 0.2D));
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static class DrippingAmberProvider implements ParticleProvider<SimpleParticleType> {
        protected final SpriteSet spriteSet;

        public DrippingAmberProvider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Nullable
        @Override
        public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            DrippingParticle dripping = new Dripping(worldIn, x, y, z, Fluids.EMPTY, DDParticleTypes.FALLING_AMBER.get());
            dripping.fullBright = true;
            dripping.gravity *= 0.01F;
            dripping.lifetime = 100;
            dripping.setColor(0.97F, 0.56F, 0.22F);
            dripping.pickSprite(this.spriteSet);
            return dripping;
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static class FallingAmberProvider implements ParticleProvider<SimpleParticleType> {
        protected final SpriteSet spriteSet;

        public FallingAmberProvider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Nullable
        @Override
        public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            DrippingParticle falling = new FallingLiquid(worldIn, x, y, z, Fluids.EMPTY, DDParticleTypes.LANDING_AMBER.get());
            falling.fullBright = true;
            falling.gravity = 0.01F;
            falling.setColor(0.97F, 0.56F, 0.22F);
            falling.pickSprite(this.spriteSet);
            return falling;
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static class LandingAmberProvider implements ParticleProvider<SimpleParticleType> {
        protected final SpriteSet spriteSet;

        public LandingAmberProvider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Nullable
        @Override
        public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            DrippingParticle landing = new Landing(worldIn, x, y, z, Fluids.EMPTY);
            landing.fullBright = true;
            landing.lifetime = (int)(28.0D / (Math.random() * 0.8D + 0.2D));
            landing.setColor(0.97F, 0.56F, 0.22F);
            landing.pickSprite(this.spriteSet);
            return landing;
        }
    }
}
