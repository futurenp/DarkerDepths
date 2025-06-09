package com.naterbobber.darkerdepths.world.gen.features;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.naterbobber.darkerdepths.blocks.HangingDoublePlantBlock;
import com.naterbobber.darkerdepths.init.DDBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.AmethystClusterBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.levelgen.feature.DripstoneUtils;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import com.naterbobber.darkerdepths.world.gen.features.config.PetrifiedBranchConfig;
import net.minecraft.world.level.material.Fluids;

import java.util.List;

public class PetrifiedBranchFeature extends Feature<PetrifiedBranchConfig> {

    public PetrifiedBranchFeature(Codec<PetrifiedBranchConfig> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<PetrifiedBranchConfig> context) {
        WorldGenLevel world = context.level();
        BlockPos pos = context.origin();
        RandomSource random = context.random();
        PetrifiedBranchConfig config = context.config();
        if (!world.isEmptyBlock(pos) || !world.getBlockState(pos.above()).is(BlockTags.BASE_STONE_OVERWORLD)) {
            return false;
        } else {
            boolean flag = false;
            List<BlockPos> woodPos = Lists.newArrayList();
            List<BlockPos> porousPos = Lists.newArrayList();
            int height = UniformInt.of(config.minLength(), config.maxLength()).sample(random);
            for (int x = -1; x <= 1; x++) {
                for (int z = -1; z <= 1; z++) {
                    for (int y = -1; y <= height; y++) {
                        BlockPos blockPos = new BlockPos(pos.getX() + x, pos.getY() - y, pos.getZ() + z);
                        BlockState state = random.nextBoolean() && random.nextInt(10) == 0 ? DDBlocks.POROUS_PETRIFIED_LOG.get().defaultBlockState() : DDBlocks.PETRIFIED_LOG.get().defaultBlockState();
                        if (random.nextInt(3) == 0) {
                            if (state.is(DDBlocks.POROUS_PETRIFIED_LOG.get())) {
                                porousPos.add(blockPos);
                            }
                            if (world.isStateAtPosition(blockPos, DripstoneUtils::isEmptyOrWaterOrLava))  {
                                world.setBlock(blockPos, state, 2);
                                woodPos.add(blockPos);
                            }
                            flag = true;
                        }
                    }
                }
            }
            for (BlockPos blockPos : woodPos) {
                if (world.getFluidState(blockPos.below()).isEmpty() && world.getFluidState(blockPos.below(2)).isEmpty() && world.getBlockState(blockPos.below(2)).canBeReplaced() && world.getBlockState(blockPos.below()).canBeReplaced()) {
                    world.setBlock(blockPos.below(), DDBlocks.PETRIFIED_ROOTS_PLANT.get().defaultBlockState(), 2);
                    world.setBlock(blockPos.below(2), DDBlocks.PETRIFIED_ROOTS.get().defaultBlockState(), 2);
                }
                else if (world.getFluidState(blockPos.below()).isEmpty() && world.getBlockState(blockPos.below()).canBeReplaced() && (!world.getBlockState(blockPos.below(2)).is(DDBlocks.PETRIFIED_LOG.get()) || world.isEmptyBlock(blockPos.below(2)))) {
                    world.setBlock(blockPos.below(), DDBlocks.PETRIFIED_ROOTS.get().defaultBlockState(), 2);
                }
                else if (world.getFluidState(blockPos.below()).isEmpty() && world.getBlockState(blockPos.below()).canBeReplaced() && world.getBlockState(blockPos.below(2)).is(DDBlocks.PETRIFIED_LOG.get())) {
                    world.setBlock(blockPos.below(), DDBlocks.PETRIFIED_ROOTS_PLANT.get().defaultBlockState(), 2);
                }
            }
            if (flag) {
                if (random.nextInt(5) == 0) {
                    for (Direction direction : Direction.values()) {
                        for (BlockPos blockPos : porousPos) {
                            if (random.nextBoolean() && world.isStateAtPosition(blockPos.relative(direction), DripstoneUtils::isEmptyOrWater)) {
                                world.setBlock(blockPos.relative(direction), DDBlocks.AMBER.get().defaultBlockState().setValue(AmethystClusterBlock.WATERLOGGED, world.getFluidState(blockPos.relative(direction)).getType() == Fluids.WATER).setValue(AmethystClusterBlock.FACING, direction), 2);
                            }
                        }
                    }
                }
            }
            return flag;
        }
    }
}
