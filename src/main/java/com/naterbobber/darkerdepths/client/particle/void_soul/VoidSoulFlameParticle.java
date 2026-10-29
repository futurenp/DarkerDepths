package com.naterbobber.darkerdepths.client.particle.void_soul;

import com.naterbobber.darkerdepths.client.render.DDParticleRenderTypes;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.core.particles.SimpleParticleType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;


@OnlyIn(Dist.CLIENT)
public class VoidSoulFlameParticle extends TextureSheetParticle {
    private VoidSoulTorchSmokeParticle smoke;
    private final double originX;
    private final double originY;
    private final double originZ;
    private final float initialSize;
    private double driftTargetX;
    private double driftTargetZ;
    private float flicker = 1.0F;
    private float flickerTarget = 1.0F;
    private int ticksUntilDriftChange;

    protected VoidSoulFlameParticle(VoidSoulFlameParticle source) {
        super(source.level, source.x, source.y, source.z);
        source.copyVisualStateTo(this);
        this.originX = source.originX;
        this.originY = source.originY;
        this.originZ = source.originZ;
        this.initialSize = source.initialSize;
    }

    protected void copyVisualStateTo(VoidSoulFlameParticle target) {
        target.x = this.x;
        target.y = this.y;
        target.z = this.z;
        target.xo = this.xo;
        target.yo = this.yo;
        target.zo = this.zo;
        target.quadSize = this.quadSize;
        target.roll = this.roll;
        target.oRoll = this.oRoll;
        target.alpha = this.alpha;
        target.age = this.age;
        target.lifetime = this.lifetime;
        target.setSprite(this.sprite);
    }

    @Override
    public void render(VertexConsumer buffer, Camera camera, float partialTick) {
        if (this.smoke != null) {
            this.smoke.render(buffer, camera, partialTick);
        }
        super.render(buffer, camera, partialTick);

    }

    protected VoidSoulFlameParticle(ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
        super(level, x, y, z, xSpeed, ySpeed, zSpeed);

        
        double randomRange = 0.025;
        this.x += (this.random.nextDouble() * randomRange - randomRange / 2);
        this.z += (this.random.nextDouble() * randomRange - randomRange / 2);
        this.quadSize *= 2f + (this.random.nextFloat()/2);
        this.originX = this.x;
        this.originY = this.y;
        this.originZ = this.z;
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        this.initialSize = this.quadSize;
        this.lifetime = 20;
        this.gravity = 0.0F;
    }

    @Override
    public void tick() {
        if (this.age++ >= this.lifetime) {
            this.remove();
            return;
        }

        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;

        float progress = (float) this.age / this.lifetime;
        if (this.ticksUntilDriftChange-- <= 0) {
            this.driftTargetX = (this.random.nextDouble() - 0.5D) * 0.016D;
            this.driftTargetZ = (this.random.nextDouble() - 0.5D) * 0.016D;
            this.flickerTarget = 1.0F + (this.random.nextFloat() - 0.5F) * 0.05F;
            this.ticksUntilDriftChange = 3 + this.random.nextInt(4);
        }

        this.x += (this.originX + this.driftTargetX - this.x) * 0.2D;
        this.z += (this.originZ + this.driftTargetZ - this.z) * 0.2D;
        this.y = this.originY + progress * 0.06D;
        this.flicker += (this.flickerTarget - this.flicker) * 0.25F;
        this.quadSize = this.initialSize * (1.0F - progress * 0.4F) * this.flicker;
        this.alpha = Math.min(1.0F, (1.0F - progress) / 0.25F);
    }

    @Override
    public ParticleRenderType getRenderType() {
        return DDParticleRenderTypes.getParticleGlowThroughFog();
    }

    @Override
    protected int getLightColor(float partialTick) {
        return LightTexture.FULL_BRIGHT;
    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public Provider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Override
        public TextureSheetParticle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            var particle = new VoidSoulFlameParticle(level, x, y, z, xSpeed, ySpeed, zSpeed);
            particle.setSprite(this.spriteSet.get(0, 8));
            particle.smoke = new VoidSoulTorchSmokeParticle(particle, this.spriteSet);
            return particle;
        }
    }
}
