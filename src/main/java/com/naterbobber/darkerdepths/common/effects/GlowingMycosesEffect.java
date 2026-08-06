package com.naterbobber.darkerdepths.common.effects;

import com.naterbobber.darkerdepths.common.init.DDParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.StemBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.common.Tags;

public class GlowingMycosesEffect extends MobEffect {

    public GlowingMycosesEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean applyEffectTick(LivingEntity livingEntity, int amplifier) {
        var level = livingEntity.level();
        var random = level.getRandom();

        var stateOn = livingEntity.getBlockStateOn();

        if(!stateOn.is(Tags.Blocks.VILLAGER_FARMLANDS)) return true;

        var cropPos = livingEntity.blockPosition().above();
        var crop = level.getBlockState(cropPos);
        var cropBlock = crop.getBlock();

        if(crop.hasProperty(BlockStateProperties.AGE_7)) {
            int age = crop.getValue(BlockStateProperties.AGE_7);

            if(level instanceof ServerLevel serverLevel) {
                if(cropBlock instanceof StemBlock) {
                    crop.randomTick(serverLevel, cropPos, random);
                    return true;
                }

                if(age == 7) return true;
                if(random.nextInt(10) != 0) return true;

                serverLevel.setBlock(cropPos, crop.setValue(BlockStateProperties.AGE_7, age + 1), Block.UPDATE_ALL);
                spawnParticlesAndSound(serverLevel, cropPos, DDParticleTypes.GLOWSHROOM_GROWTH.get(), 5);
            }
        }

        return super.applyEffectTick(livingEntity, amplifier);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int tickCount, int amplifier) {
        return tickCount % 20 == 0;
    }


    //todo
    //move this into a class so shelf glowshrooms can use it too
    public static void spawnParticlesAndSound(ServerLevel level, BlockPos pos, SimpleParticleType particle, int count) {
        level.playSound(null, pos, SoundEvents.BONE_MEAL_USE, SoundSource.BLOCKS, 1.0F, 1.0F);

        var random = level.getRandom();

        for(int i = 0; i < count; ++i) {
            double x = pos.getX() + random.nextDouble();
            double y = pos.getY() + random.nextDouble();
            double z = pos.getZ() + random.nextDouble();
            level.sendParticles(particle, x, y, z, 1, 0.0, 0.0, 0.0, 0.01);

        }
    }
}
