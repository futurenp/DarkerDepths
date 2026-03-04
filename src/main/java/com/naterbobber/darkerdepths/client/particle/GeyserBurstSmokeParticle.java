package com.naterbobber.darkerdepths.client.particle;

import com.naterbobber.darkerdepths.init.DDParticleTypes;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

@OnlyIn(Dist.CLIENT)
public class GeyserBurstSmokeParticle extends TextureSheetParticle {

    protected GeyserBurstSmokeParticle(ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
        super(level, x, y, z, xSpeed, ySpeed, zSpeed);
    }

    protected GeyserBurstSmokeParticle(ClientLevel level, double x, double y, double z) {
        super(level, x, y, z, 0, 0, 0);
        this.yd = level.getRandom().nextDouble() + 3;
    }

    @Override
    public void tick() {
        move();
    }

    public void move() {
        this.x += this.xd;
        this.y += this.yd;
        this.z += this.zd;
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }


    @OnlyIn(Dist.CLIENT)
    public static class GeyserBurstSmokeParticleFactory implements ParticleProvider<SimpleParticleType> {
        protected final SpriteSet spriteSet;

        public GeyserBurstSmokeParticleFactory(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Override
        public @Nullable Particle createParticle(SimpleParticleType simpleParticleType, ClientLevel clientLevel, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            GeyserBurstSmokeParticle particle = new GeyserBurstSmokeParticle(clientLevel, x, y, z);
            particle.setColor(0.22F, 0.22F, 0.22F);
            particle.pickSprite(this.spriteSet);
            return particle;
        }
    }


}
