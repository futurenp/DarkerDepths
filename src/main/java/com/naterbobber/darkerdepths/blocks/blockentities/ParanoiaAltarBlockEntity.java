package com.naterbobber.darkerdepths.blocks.blockentities;

import com.naterbobber.darkerdepths.blocks.ParanoiaAltarBlock;
import com.naterbobber.darkerdepths.init.DDBlockEntityTypes;
import com.naterbobber.darkerdepths.init.DDMobEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.RenderUtils;

import java.util.List;

public class ParanoiaAltarBlockEntity extends BlockEntity implements GeoBlockEntity {
    private final AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);
    private final int EFFECT_RADIUS = 72;
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

        AABB finalArea = new AABB(pos).inflate(EFFECT_RADIUS);
        List<Player> players = level.getEntitiesOfClass(Player.class, finalArea);

        for (Player player : players) {
            if(player.isSpectator()) continue;
            player.addEffect(new MobEffectInstance(DDMobEffects.PARANOIA.get(), 320, 0, false, false, true));
        }
    }


}