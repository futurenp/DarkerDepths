package com.naterbobber.darkerdepths.world.gen.features;

import com.mojang.serialization.Codec;
import com.naterbobber.darkerdepths.blocks.HangingDoublePlantBlock;
import com.naterbobber.darkerdepths.init.DDBlockTags;
import com.naterbobber.darkerdepths.init.DDBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.AmethystClusterBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import java.util.Random;

public class PetrifiedBranchFeature extends Feature<NoneFeatureConfiguration> {

    public PetrifiedBranchFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel world = context.level();
        BlockPos pos = context.origin();
        Random random = context.random();
        if (!world.getBlockState(pos.above()).is(DDBlockTags.SANDY_GROUND_REPLACEABLE)) {
            return false;
        } else {
            BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
            for (int i = 0; i < UniformInt.of(10, 20).sample(random); i++) {
                mutable.setWithOffset(pos, random.nextInt(2) - random.nextInt(2), 0, random.nextInt(2) - random.nextInt(2)).move(Direction.DOWN, i);
                spawnLogs(world, mutable, random);
            }
            return true;
        }
    }

    private void spawnLogs(LevelAccessor world, BlockPos pos, Random random) {
        if (world.isEmptyBlock(pos)) {
            this.setBlock(world, pos, DDBlocks.PETRIFIED_LOG.get().defaultBlockState());
            if (world.isEmptyBlock(pos.below()) && world.isEmptyBlock(pos.below(2))) {
                HangingDoublePlantBlock root = (HangingDoublePlantBlock) DDBlocks.LONG_ROOTS.get();
                root.placeAt(world, pos.below());
            }
        }
        if (random.nextInt(10) == 0) {
            if (world.isEmptyBlock(pos)) {
                this.setBlock(world, pos, DDBlocks.POROUS_PETRIFIED_LOG.get().defaultBlockState());
                if (random.nextInt(5) == 0) {
                    for (Direction direction : Direction.values()) {
                        if (world.isEmptyBlock(pos.relative(direction)) && random.nextBoolean()) {
                            this.setBlock(world, pos.relative(direction), DDBlocks.AMBER.get().defaultBlockState().setValue(AmethystClusterBlock.FACING, direction));
                        }
                    }
                }
            }
        }
    }
}
