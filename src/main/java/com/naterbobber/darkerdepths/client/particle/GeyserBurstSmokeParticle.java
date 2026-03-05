package com.naterbobber.darkerdepths.client.particle;

import com.naterbobber.darkerdepths.init.DDParticleTypes;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

@OnlyIn(Dist.CLIENT)
public class GeyserBurstSmokeParticle extends TextureSheetParticle {

    protected GeyserBurstSmokeParticle(ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
        super(level, x, y, z, xSpeed, ySpeed, zSpeed);
        this.x = x;
        this.y = y;
        this.z = z;
        this.xd = xSpeed;
        this.yd = ySpeed;
        this.zd = zSpeed;
        this.quadSize = 0.75f;
        this.lifetime = 15;
        this.gravity = 0.035F;
    }

    protected GeyserBurstSmokeParticle(ClientLevel level, double x, double y, double z) {
        super(level, x, y, z, 0, 1, 0);
        this.quadSize = 0.75f;
        this.lifetime = 15;
        this.gravity = 0.035F;
    }

    @Override
    public void tick() {
        super.tick();
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
            GeyserBurstSmokeParticle particle = new GeyserBurstSmokeParticle(clientLevel, x, y, z, xSpeed, ySpeed, zSpeed);
            particle.pickSprite(this.spriteSet);
            return particle;
        }
    }


}
