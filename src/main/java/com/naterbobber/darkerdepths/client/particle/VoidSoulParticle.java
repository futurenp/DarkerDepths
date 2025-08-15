package com.naterbobber.darkerdepths.client.particle;

import com.naterbobber.darkerdepths.init.DDParticleTypes;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;
import java.util.Random;

public class VoidSoulParticle extends TextureSheetParticle {
    private static final Random RANDOM = new Random();
    private final float originalScale;
    private float rotation;
    private int lingeringTime;
    private boolean canBeBottled;

    protected VoidSoulParticle(ClientLevel level, double x, double y, double z,
                               double xSpeed, double ySpeed, double zSpeed) {
        super(level, x, y, z, xSpeed, ySpeed, zSpeed);

        // Set particle properties
        this.lifetime = 1200; // 60 seconds at 20 TPS
        this.lingeringTime = this.lifetime;
        this.canBeBottled = true;

        // Visual properties
        this.quadSize = 0.3F + RANDOM.nextFloat() * 0.2F;
        this.originalScale = this.quadSize;

        // Physics
        this.friction = 0.98F;
        this.gravity = -0.001F; // Slight upward drift
        this.hasPhysics = true;
    }

    @Override
    public void tick() {
        super.tick();

        // Floating motion
        double time = (this.lifetime - this.age) / (double)this.lifetime;
        this.yd += Math.sin(this.age * 0.1) * 0.001;
        this.xd += (RANDOM.nextDouble() - 0.5) * 0.001;
        this.zd += (RANDOM.nextDouble() - 0.5) * 0.001;

        // Scale animation - pulse slightly
        float pulse = 1.0F + (float)Math.sin(this.age * 0.2) * 0.1F;
        this.quadSize = this.originalScale * pulse;

        // Alpha fade near end of life
        if (this.age > this.lifetime * 0.8F) {
            this.alpha = (this.lifetime - this.age) / (float)(this.lifetime * 0.2F) * 0.8F;
        }

        // Check for bottle interaction
        checkForBottleInteraction();
    }

    private void checkForBottleInteraction() {
        if (!this.canBeBottled) return;

        // Get nearby players
        List<Player> nearbyPlayers = this.level.getEntitiesOfClass(Player.class,
                new AABB(this.x - 2, this.y - 2, this.z - 2, this.x + 2, this.y + 2, this.z + 2));

        for (Player player : nearbyPlayers) {
            ItemStack heldItem = player.getMainHandItem();
            if (heldItem.getItem() == Items.GLASS_BOTTLE) {
                double distance = player.distanceToSqr(this.x, this.y, this.z);
                if (distance < 4.0D) { // Within 2 blocks
                    captureInBottle(player, heldItem);
                    return;
                }
            }
        }
    }

    private void captureInBottle(Player player, ItemStack bottleStack) {
        if (player.level().isClientSide) return;

        // Replace the bottle in player's inventory (consume the bottle)
        bottleStack.shrink(1);

        // Remove the particle
        this.remove();

        // Play capture sound
        player.level().playSound(null, player.blockPosition(),
                SoundEvents.BOTTLE_FILL, SoundSource.PLAYERS, 0.8F, 1.2F);

        // Spawn capture effect - using soul particles for better visual feedback
        VoidSoulHelper.spawnParticleEffect(player.level(), this.x, this.y, this.z,
                ParticleTypes.SOUL, 10, 0.5, 0, 0.05, 0);
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    public boolean canBeBottled() {
        return this.canBeBottled;
    }

    public void setCanBeBottled(boolean canBeBottled) {
        this.canBeBottled = canBeBottled;
    }

    // Fixed the factory class name and reference
    @OnlyIn(Dist.CLIENT)
    public static class VoidSoulFactory implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public VoidSoulFactory(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Override
        public TextureSheetParticle createParticle(SimpleParticleType type, ClientLevel level,
                                                   double x, double y, double z,
                                                   double xSpeed, double ySpeed, double zSpeed) {
            VoidSoulParticle particle = new VoidSoulParticle(level, x, y, z, xSpeed, ySpeed, zSpeed);
            particle.pickSprite(this.spriteSet);
            return particle;
        }
    }

    // Moved VoidSoulHelper outside as a separate static class for better organization
    public static class VoidSoulHelper {

        // Spawn custom void soul particles with random motion
        public static void spawnVoidSoulParticles(Level level, double x, double y, double z, int count) {
            spawnParticleEffect(level, x, y, z, DDParticleTypes.VOID_SOUL.get(), count, 2.0,
                    0.05, 0.05, 0.05); // Random velocities
        }

        // Generic particle spawning method for flexibility
        public static void spawnParticleEffect(Level level, double x, double y, double z,
                                               ParticleOptions particleType, int count, double spread,
                                               double velX, double velY, double velZ) {
            if (level.isClientSide) {
                for (int i = 0; i < count; i++) {
                    // Calculate random offsets based on spread
                    double offsetX = (level.random.nextDouble() - 0.5) * spread;
                    double offsetY = (level.random.nextDouble() - 0.5) * spread;
                    double offsetZ = (level.random.nextDouble() - 0.5) * spread;

                    // Calculate velocities (can be random or fixed)
                    double finalVelX = velX == 0 ? 0 : (level.random.nextDouble() - 0.5) * velX;
                    double finalVelY = velY == 0 ? 0 : (velY > 0.01 ? velY : level.random.nextDouble() * velY);
                    double finalVelZ = velZ == 0 ? 0 : (level.random.nextDouble() - 0.5) * velZ;

                    level.addParticle(particleType,
                            x + offsetX, y + offsetY, z + offsetZ,
                            finalVelX, finalVelY, finalVelZ);
                }
            }
        }
    }
}