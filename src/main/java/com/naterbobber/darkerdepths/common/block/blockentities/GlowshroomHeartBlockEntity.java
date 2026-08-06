package com.naterbobber.darkerdepths.common.block.blockentities;

import com.naterbobber.darkerdepths.common.api.GlowshroomMycosesHandler;
import com.naterbobber.darkerdepths.common.init.DDBlockEntityTypes;
import com.naterbobber.darkerdepths.common.init.DDMobEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class GlowshroomHeartBlockEntity extends BlockEntity {
    public GlowshroomHeartBlockEntity(BlockPos pos, BlockState state) {
        super(DDBlockEntityTypes.GLOWSHROOM_HEART.get(), pos, state);
    }

    public void clientTick(Level level, BlockPos blockPos, BlockState blockState) {
        //todo
        //circular particle ring?
    }

    public void tick(Level level, BlockPos blockPos, BlockState blockState) {
        if(level.getGameTime() % 40 != 0){
            return;
        }

        var area = AABB.ofSize(blockPos.getCenter(), 8, 8, 8);
        var entities = level.getEntities(null, area);

        for(var entity : entities) {
            if(entity instanceof LivingEntity livingEntity && livingEntity.hasEffect(DDMobEffects.GLOWING_MYCOSES)) {
                var activeRegen = livingEntity.getEffect(DDMobEffects.GLOWING_MYCOSES_ACTIVE);

                if (activeRegen == null || activeRegen.getDuration() <= 80) {
                    GlowshroomMycosesHandler.giveMycoses(livingEntity, 320, 0, activeRegen == null);
                }
            }
        }
    }
}
