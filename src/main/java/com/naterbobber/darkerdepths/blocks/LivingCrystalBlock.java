package com.naterbobber.darkerdepths.blocks;

import com.naterbobber.darkerdepths.init.DDBlockStateProperties;
import com.naterbobber.darkerdepths.init.DDBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class LivingCrystalBlock extends Block {

    public LivingCrystalBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos blockPos, RandomSource random) {
        if (random.nextInt(5) == 0) {
            Direction direction = Direction.getRandom(random);
            BlockPos blockpos = blockPos.relative(direction);
            BlockState blockstate = level.getBlockState(blockpos);
            BlockState block = null;
            int relativeCrack;
            if (blockstate.is(BlockTags.DIAMOND_ORES)) {
                block = DDBlocks.LIVING_CRYSTAL.get().defaultBlockState();
            } else if (blockstate.is(DDBlocks.STONE_MELON.get()) && (relativeCrack = blockstate.getValue(DDBlockStateProperties.CRACKED)) <= 2) {
                if (relativeCrack == 2) {
                    block = DDBlocks.CRYSTAL_MELON.get().defaultBlockState();
                } else {
                    block = DDBlocks.STONE_MELON.get().defaultBlockState().setValue(DDBlockStateProperties.CRYSTAL_LEVEL, relativeCrack + 1);
                }
                level.playSound(null, blockpos, SoundEvents.AMETHYST_BLOCK_PLACE, SoundSource.BLOCKS, 1.0F, 1.0F);
            }

            if (block != null) {
                level.setBlockAndUpdate(blockpos, block);
            }
        }
    }

}
