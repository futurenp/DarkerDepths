package com.naterbobber.darkerdepths.block.blockentities;

import com.naterbobber.darkerdepths.init.DDBlockEntityTypes;
import com.naterbobber.darkerdepths.init.DDMobEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
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
                var activeRegen = livingEntity.getEffect(MobEffects.REGENERATION);

                if(activeRegen == null){
                    level.playSound(null, livingEntity.getOnPos(), SoundEvents.BEACON_POWER_SELECT, SoundSource.BLOCKS, 0.6F, 1.3F);
                    //todo
                    //summon particles on the player
                }

                if (activeRegen == null || activeRegen.getDuration() <= 80) {
                    var regenEffect = new MobEffectInstance(MobEffects.REGENERATION, 320, 0);
                    livingEntity.addEffect(regenEffect);
                }
            }
        }
    }
}
