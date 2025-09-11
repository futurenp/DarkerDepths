package com.naterbobber.darkerdepths.blocks.blockentities;

import com.naterbobber.darkerdepths.blocks.ParanoiaAltarBlock;
import com.naterbobber.darkerdepths.config.DDConfigs;
import com.naterbobber.darkerdepths.entities.BodySnatcherEntity;
import com.naterbobber.darkerdepths.init.DDBlockEntityTypes;
import com.naterbobber.darkerdepths.init.DDEntityTypes;
import com.naterbobber.darkerdepths.init.DDMobEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.RenderUtils;

import java.util.List;

public class ParanoiaAltarBlockEntity extends BlockEntity implements GeoBlockEntity {
    private final AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);
    private int tickCounter = 0;

    public ParanoiaAltarBlockEntity(BlockPos pos, BlockState state) {
        super(DDBlockEntityTypes.PARANOIA_ALTAR.get(), pos, state);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar registrar) {
        registrar.add(new AnimationController<>(this, "controller", 0, this::animationPredicate));
    }

    private PlayState animationPredicate(AnimationState<ParanoiaAltarBlockEntity> state) {
        state.getController().setAnimation(RawAnimation.begin().then("idle", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Override
    public double getTick(Object blockEntity) {
        return RenderUtils.getCurrentTick();
    }

    public void tick(Level level, BlockPos pos, BlockState state) {
        if (!state.getValue(ParanoiaAltarBlock.ENABLED) || level.isClientSide) return;
        if (tickCounter++ != 100) return;

        tickCounter = 0;

        int radiusHorizontal = DDConfigs.PARANOIA_ALTAR_RADIUS_HORIZONTAL.get();
        int radiusY = DDConfigs.PARANOIA_ALTAR_RADIUS_VERTICAL.get();

        AABB finalArea = new AABB(pos).inflate(radiusHorizontal, radiusY, radiusHorizontal);

        List<Player> players = level.getEntitiesOfClass(Player.class, finalArea);
        int playerMobCap = 6;
        int catacombsMobCap = players.size() * playerMobCap;

        for (Player player : players) {
            if(player.isSpectator()) continue;
            if(player.isCreative() && !DDConfigs.PARANOIA_ALTAR_EFFECTS_CREATIVE.get()) continue;

            player.addEffect(new MobEffectInstance(DDMobEffects.PARANOIA.get(), 320, 0, false, false, true));

            if(level.getRandom().nextDouble() > .33) continue;

            List<BodySnatcherEntity> catacombsBodySnatcherList = level.getEntitiesOfClass(BodySnatcherEntity.class, finalArea);
            List<BodySnatcherEntity> playerBodySnatcherList = level.getEntitiesOfClass(BodySnatcherEntity.class, player.getBoundingBox().inflate(28));

            if(state.getValue(
                    ParanoiaAltarBlock.LOCKED) &&
                    catacombsBodySnatcherList.size() <= catacombsMobCap &&
                    playerBodySnatcherList.size() <= playerMobCap
            ) {
                spawnMobInValidPosition(level, player, new BodySnatcherEntity(DDEntityTypes.BODY_SNATCHER.get(), level));
            }
        }
    }

    private void spawnMobInValidPosition(Level level, Player player, Entity entity) {
        boolean found = false;
        BlockPos pos;
        int height = 6;
        int spawnAttempts = 20;

        do {
            pos = getRandomPositionAroundPlayer(player, 16.0, 24.0, height, level.getRandom());

            if(level.getBlockState(pos).isAir()) {
                for(int i = 0; i < height; i++) {
                    BlockPos nextPos = pos.relative(Direction.Axis.Y, -i);
                    if(!level.getBlockState(nextPos).isAir()) {
                        found = true;
                        pos = nextPos.above();
                        break;
                    }
                }
            }
            spawnAttempts--;

        } while (!found && spawnAttempts > 0);

        if(found) {
            entity.setPos(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
            level.addFreshEntity(entity);
        }
    }

    private BlockPos getRandomPositionAroundPlayer(Player player, double minRadius, double maxRadius, int height, RandomSource random) {
        AABB minHorizontalArea = player.getBoundingBox().inflate(minRadius);
        AABB maxHorizontalArea = player.getBoundingBox().inflate(maxRadius);
        AABB verticalArea = player.getBoundingBox().inflate((double) height / 2);

        AABB minArea = new AABB(
                minHorizontalArea.minX,
                verticalArea.minY,
                minHorizontalArea.minZ,
                minHorizontalArea.maxX,
                verticalArea.maxY,
                minHorizontalArea.maxZ
        );

        AABB maxArea = new AABB(
                maxHorizontalArea.minX,
                verticalArea.minY,
                maxHorizontalArea.minZ,
                maxHorizontalArea.maxX,
                verticalArea.maxY,
                maxHorizontalArea.maxZ
        );

        Vec3 position;

        do {
            position = new Vec3(
                    maxArea.minX + random.nextDouble() * maxArea.getXsize(),
                    maxArea.minY + random.nextDouble() * height,
                    maxArea.minZ + random.nextDouble() * maxArea.getZsize()
            );

        } while (minArea.contains(position));

        return BlockPos.containing(position);
    }


}