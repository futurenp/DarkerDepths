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
public class GeyserBurstSmokeParticle extends PlayerCloudParticle {
    private float color;

    protected GeyserBurstSmokeParticle(ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, SpriteSet sprites, float color) {
        super(level, x, y, z, xSpeed, ySpeed, zSpeed, sprites);
        this.lifetime = level.getRandom().nextInt(8) + 14;

        this.xd = setSpread(level, xSpeed);
        this.yd = setSpread(level, ySpeed);
        this.zd = setSpread(level, zSpeed);
        this.color = Math.min(1, color + level.getRandom().nextFloat() * color);
        updateColor();
    }

    @Override
    public void tick() {
        super.tick();
        slowMainDirection();
        color *= 0.98F;
        updateColor();
    }

    private void updateColor(){
        setColor(color, color, color);
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

    @Override
    protected int getLightColor(float partialTick) {
        var lightColor = super.getLightColor(partialTick);
        if (lightColor < 50) {
            lightColor = 80;
        }
        return lightColor;
    }

    private static double setSpread(ClientLevel level, double directionSpeed) {
        if(directionSpeed == 0) {
            return (level.getRandom().nextFloat() - 0.5) * 0.16;
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
            var particle = new GeyserBurstSmokeParticle(clientLevel, x, y, z, xSpeed, ySpeed, zSpeed, spriteSet, 0.55F);
            particle.pickSprite(this.spriteSet);
            return particle;
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static class LavaProvider implements ParticleProvider<SimpleParticleType> {
        protected final SpriteSet spriteSet;

        public LavaProvider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Override
        public @Nullable Particle createParticle(SimpleParticleType simpleParticleType, ClientLevel clientLevel, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            var particle = new GeyserBurstSmokeParticle(clientLevel, x, y, z, xSpeed, ySpeed, zSpeed, spriteSet, 0.4F);
            particle.pickSprite(this.spriteSet);
            return particle;
        }
    }
}
