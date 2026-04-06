package com.naterbobber.darkerdepths.client.particle.geyser;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.PlayerCloudParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

@OnlyIn(Dist.CLIENT)
public class GeyserBurstMistParticle extends PlayerCloudParticle {
    protected GeyserBurstMistParticle(ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, SpriteSet sprites) {
        super(level, x, y, z, xSpeed, ySpeed, zSpeed, sprites);
        this.lifetime = level.getRandom().nextInt(10) + 15;

        this.xd = setSpread(level, xSpeed);
        this.yd = setSpread(level, ySpeed);
        this.zd = setSpread(level, zSpeed);
    }

    @Override
    public void tick() {
        super.tick();
        slowMainDirection();
    }

    private void slowMainDirection(){
        var direction = Math.max(Math.max(xd, zd), yd);
        if(xd == direction) {
            xd *= 0.98;
        }
        if(yd == direction) {
            yd *= 0.98;
        }
        if(zd == direction) {
            zd *= 0.98;
        }
    }

    private static double setSpread(ClientLevel level, double directionSpeed) {
        if(directionSpeed == 0) {
            return (level.getRandom().nextFloat() - 0.5) * 0.28;
        }
        return directionSpeed;
    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<SimpleParticleType> {
        protected final SpriteSet spriteSet;

        public Provider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Override
        public @Nullable Particle createParticle(SimpleParticleType simpleParticleType, ClientLevel clientLevel, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            var particle = new GeyserBurstMistParticle(clientLevel, x, y, z, xSpeed, ySpeed, zSpeed, spriteSet);
            particle.pickSprite(this.spriteSet);
            return particle;
        }
    }
}
