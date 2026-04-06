package com.naterbobber.darkerdepths.client.particle.geyser;

import com.naterbobber.darkerdepths.block.DDBlockStateProperties;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.CampfireSmokeParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

@OnlyIn(Dist.CLIENT)
public class GeyserPassiveSmokeParticle extends CampfireSmokeParticle {
    private final BlockPos geyserPos;
    private final Direction moveDirection;
    private final double targetMinSpeed;
    private final double targetMaxSpeed = 0.7;
    private final int easeInDuration = 10;
    private int burstProgress = 0;
    private boolean boosted;
    private BurstPhase burstPhase = BurstPhase.PASSIVE;

    protected GeyserPassiveSmokeParticle(ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
        super(level, x, y, z, xSpeed, ySpeed, zSpeed, false);

        this.moveDirection = Direction.getNearest(xSpeed, ySpeed, zSpeed);
        this.targetMinSpeed = Math.max(Math.max(Math.abs(xSpeed), Math.abs(ySpeed)), Math.abs(zSpeed));

        Direction oppositeDirection = this.moveDirection.getOpposite();
        BlockPos startPos = BlockPos.containing(x, y, z);
        this.geyserPos = startPos.relative(oppositeDirection);
    }

    @Override
    public void tick() {
        super.tick();

        if (this.burstPhase == BurstPhase.PASSIVE) {
            BlockState state = this.level.getBlockState(this.geyserPos);
            if(!state.hasProperty(DDBlockStateProperties.BURSTING)) {
                burstPhase = BurstPhase.DONE;
            }

            boolean isGeyserBursting = state.hasProperty(DDBlockStateProperties.BURSTING) && state.getValue(DDBlockStateProperties.BURSTING);

            if (isGeyserBursting) {
                this.boosted = state.hasProperty(DDBlockStateProperties.BOOSTED) && state.getValue(DDBlockStateProperties.BOOSTED);
                this.burstPhase = BurstPhase.EASING_IN;
            }
        }

        if (this.burstPhase == BurstPhase.EASING_IN) {
            this.burstProgress++;

            double progressRatio = (double) this.burstProgress / this.easeInDuration;
            double currentEasedSpeed = Mth.lerp(progressRatio, this.targetMinSpeed, boosted ? this.targetMaxSpeed * 1.5 : this.targetMaxSpeed);

            applyMainSpeed(currentEasedSpeed);

            if (this.burstProgress >= this.easeInDuration) {
                this.burstPhase = BurstPhase.SLOWING_DOWN;
            }
        }

        else if (this.burstPhase == BurstPhase.SLOWING_DOWN) {
            slowMainDirection();
        }
    }

    private void applyMainSpeed(double absoluteSpeed) {
        if (this.moveDirection.getStepX() != 0) this.xd = absoluteSpeed * this.moveDirection.getStepX();
        if (this.moveDirection.getStepY() != 0) this.yd = absoluteSpeed * this.moveDirection.getStepY();
        if (this.moveDirection.getStepZ() != 0) this.zd = absoluteSpeed * this.moveDirection.getStepZ();
    }

    private void slowMainDirection() {
        double friction = boosted ? 0.95 : 0.94;

        if (this.moveDirection.getStepX() != 0) {
            this.xd *= friction;
            if (Math.abs(this.xd) < this.targetMinSpeed) {
                this.xd = this.targetMinSpeed * this.moveDirection.getStepX();
            }
        }

        if (this.moveDirection.getStepY() != 0) {
            this.yd *= friction;
            if (Math.abs(this.yd) < this.targetMinSpeed) {
                this.yd = this.targetMinSpeed * this.moveDirection.getStepY();
            }
        }

        if (this.moveDirection.getStepZ() != 0) {
            this.zd *= friction;
            if (Math.abs(this.zd) < this.targetMinSpeed) {
                this.zd = this.targetMinSpeed * this.moveDirection.getStepZ();
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<SimpleParticleType> {
        protected final SpriteSet spriteSet;

        public Provider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Override
        public @Nullable Particle createParticle(SimpleParticleType simpleParticleType, ClientLevel clientLevel, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            var particle = new GeyserPassiveSmokeParticle(clientLevel, x, y, z, xSpeed, ySpeed, zSpeed);
            particle.pickSprite(this.spriteSet);
            return particle;
        }
    }

    private enum BurstPhase {
        PASSIVE,
        EASING_IN,
        SLOWING_DOWN,
        DONE
    }
}