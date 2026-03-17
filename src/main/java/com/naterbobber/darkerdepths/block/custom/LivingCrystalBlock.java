package com.naterbobber.darkerdepths.block.custom;

import com.naterbobber.darkerdepths.block.DDBlockStateProperties;
import com.naterbobber.darkerdepths.init.DDBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class LivingCrystalBlock extends Block {

    public LivingCrystalBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos blockPos, RandomSource random) {
        if (random.nextInt(5) != 0) {
            return;
        }

        BlockPos blockpos = blockPos.relative(Direction.getRandom(random));
        BlockState blockstate = level.getBlockState(blockpos);
        BlockState block = null;

        if (blockstate.is(BlockTags.DIAMOND_ORES)) {
            block = DDBlocks.CRYSTAL_HUSK.get().defaultBlockState().setValue(DDBlockStateProperties.CRYSTAL_GROWTH_LEVEL, 1);
            level.playSound(null, blockpos, SoundEvents.AMETHYST_BLOCK_PLACE, SoundSource.BLOCKS, 1.0F, 1.0F);
            level.levelEvent(2001, blockpos, getId(blockstate));
        }
        else if (blockstate.is(Blocks.MELON)) {
            block = DDBlocks.STONE_MELON.get().defaultBlockState();
        }
        else if (blockstate.is(DDBlocks.STONE_MELON.get())) {
            int stoneMelonGrowthLevel = blockstate.getValue(DDBlockStateProperties.MELON_GROWTH_LEVEL);

            if (stoneMelonGrowthLevel >= 2) {
                block = DDBlocks.CRYSTAL_MELON.get().defaultBlockState();
                level.setBlock(blockPos, DDBlocks.CRYSTAL_HUSK.get().defaultBlockState(), 2);
            }
            else {
                block = blockstate.setValue(DDBlockStateProperties.MELON_GROWTH_LEVEL, stoneMelonGrowthLevel + 1);
            }

            level.playSound(null, blockpos, SoundEvents.AMETHYST_BLOCK_PLACE, SoundSource.BLOCKS, 1.0F, 1.0F);
            level.levelEvent(2001, blockpos, getId(blockstate));
        }

        if (block != null) {
            level.setBlockAndUpdate(blockpos, block);
        }
    }

}
