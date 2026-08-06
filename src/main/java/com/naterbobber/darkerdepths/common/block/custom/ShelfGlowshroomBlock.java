package com.naterbobber.darkerdepths.common.block.custom;

import com.naterbobber.darkerdepths.common.block.generic.ISunlightSensitiveGlowshroomBlock;
import com.naterbobber.darkerdepths.common.init.DDBlocks;
import com.naterbobber.darkerdepths.common.init.DDParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.StemBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.BlockHitResult;

public class ShelfGlowshroomBlock extends BaseShelfGlowshroomBlock implements ISunlightSensitiveGlowshroomBlock {

    public ShelfGlowshroomBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if(!state.getValue(LARGE)) {
            if(stack.is(Items.BONE_MEAL)) {
                if(!player.isCreative()) {
                    stack.shrink(1);
                }
                if(level instanceof ServerLevel serverLevel) {
                   spawnParticlesAndSound(serverLevel, pos, ParticleTypes.HAPPY_VILLAGER, 12);
                }
                level.setBlockAndUpdate(pos, state.setValue(LARGE, true));

                return ItemInteractionResult.SUCCESS;
            }
        }

        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        checkSunlight(level, pos);

        var chance = state.getValue(LARGE) ? 0.5 : 0.35;
        if(random.nextDouble() > chance) return;

        var cropPos = pos.relative(state.getValue(FACING).getOpposite()).above();
        var crop = level.getBlockState(cropPos);
        var cropBlock = crop.getBlock();

        if(crop.hasProperty(BlockStateProperties.AGE_7)) {
            if(crop.getValue(BlockStateProperties.AGE_7) == 7 && !(cropBlock instanceof StemBlock)) return;
            crop.randomTick(level, cropPos, random);
            spawnParticlesAndSound(level, cropPos, DDParticleTypes.GLOWSHROOM_GROWTH.get(), 5);
        }
        else if(crop.hasProperty(BlockStateProperties.AGE_15)) {
            int distance = 0;

            while(level.getBlockState(cropPos.above(distance + 1)).getBlock().equals(cropBlock) && distance < 15) {
                distance++;
            }

            if(distance >= 2 && (crop.is(Blocks.SUGAR_CANE) || crop.is(Blocks.CACTUS))) return;

            var topCropPos = cropPos.above(distance);
            level.getBlockState(topCropPos).randomTick(level, topCropPos, random);

            spawnParticlesAndSound(level, topCropPos, DDParticleTypes.GLOWSHROOM_GROWTH.get(), 8);
        }
    }

    @Override
    public BlockState getDeadGlowshroomState(BlockState existingState) {
        return DDBlocks.DEAD_SHELF_GLOWSHROOM.get()
                .defaultBlockState()
                .setValue(LARGE, existingState.getValue(LARGE))
                .setValue(FACING, existingState.getValue(FACING));
    }

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
