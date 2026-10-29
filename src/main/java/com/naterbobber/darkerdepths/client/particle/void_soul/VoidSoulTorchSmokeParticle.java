package com.naterbobber.darkerdepths.client.particle.void_soul;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.particle.SpriteSet;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class VoidSoulTorchSmokeParticle extends VoidSoulFlameParticle {
    private final VoidSoulFlameParticle flame;
    private final SpriteSet sprites;
    private final float initialSize;
    private final float drift;

    public VoidSoulTorchSmokeParticle(VoidSoulFlameParticle flame, SpriteSet sprites) {
        super(flame);
        this.flame = flame;
        this.sprites = sprites;
        this.initialSize = this.quadSize;
        this.drift = (this.random.nextFloat() - 0.5F) * 0.075F;
        this.setColor(0.0F, 0.0F, 0.0F);

    }

    @Override
    public void render(VertexConsumer buffer, Camera camera, float partialTick) {
        this.flame.copyVisualStateTo(this);
        float progress = Math.min(1.0F, (this.age + partialTick) / this.lifetime);
        this.setSprite(this.sprites.get(1 + Math.min(7, (int) (progress * 8)), 8));
        this.quadSize = this.initialSize * (0.35F + progress * 0.45F);
        this.alpha = (float) Math.sin(progress * Math.PI) * 0.65F;
        var forward = camera.getLookVector();
        var up = camera.getUpVector();
        var left = camera.getLeftVector();
        double rise = 0.10D + progress * 0.12D;
        double sideways = this.drift * progress;
        double offsetX = forward.x() * 0.002D + up.x() * rise + left.x() * sideways;
        double offsetY = forward.y() * 0.002D + up.y() * rise + left.y() * sideways;
        double offsetZ = forward.z() * 0.002D + up.z() * rise + left.z() * sideways;
        this.x += offsetX;
        this.xo += offsetX;
        this.y += offsetY;
        this.yo += offsetY;
        this.z += offsetZ;
        this.zo += offsetZ;
        super.render(buffer, camera, partialTick);
    }
}
