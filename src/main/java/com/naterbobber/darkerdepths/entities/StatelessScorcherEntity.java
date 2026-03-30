package com.naterbobber.darkerdepths.entities;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.Level;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animatable.stateless.StatelessGeoEntity;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

public class StatelessScorcherEntity extends Mob implements StatelessGeoEntity {
    protected static final RawAnimation SHAKE_ANIM = RawAnimation.begin().thenLoop("attack");
    private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);

    public StatelessScorcherEntity(EntityType<? extends Mob> entityType, Level level) {
        super(entityType, level);
    }


    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {

    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return geoCache;
    }

    @Override
    public void tick() {
        super.tick();
        if (level().isClientSide()) {
            playAnimation(SHAKE_ANIM);
        }
    }
}
