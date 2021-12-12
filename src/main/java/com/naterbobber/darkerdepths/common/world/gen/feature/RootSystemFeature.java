package com.naterbobber.darkerdepths.common.world.gen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ITag;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.TreeFeature;

import java.util.Random;
import java.util.function.Predicate;

//<>

public class RootSystemFeature extends Feature<RootSystemFeatureConfig> {
    public RootSystemFeature(Codec<RootSystemFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, RootSystemFeatureConfig config) {
        if (!reader.getBlockState(pos).isAir()) {
            return false;
        } else {
            BlockPos.Mutable mutable = pos.toMutable();
            if (this.generateTreeAndRoots(reader, generator, config, rand, mutable, pos)) {
                this.generateHangingRoots(reader, config, rand, pos, mutable);
            }

            return true;
        }
    }

    private boolean hasSpaceForTree(ISeedReader readerIn, RootSystemFeatureConfig config, BlockPos pos) {
        BlockPos.Mutable mutable = pos.toMutable();

        for (int i = 1; i <= config.requiredVerticalSpaceForTree; i++) {
            mutable.move(Direction.UP);
            BlockState state = readerIn.getBlockState(mutable);
            if (!this.isAirOrWater(state, i, config.allowedVerticalWaterForTree)) {
                return false;
            }
        }

        return true;
    }

    private boolean isAirOrWater(BlockState state, int height, int allowedVerticalWaterForTree) {
        return state.isAir() || height <= allowedVerticalWaterForTree && state.getFluidState().isTagged(FluidTags.WATER);
    }

    private boolean generateTreeAndRoots(ISeedReader readerIn, ChunkGenerator generator, RootSystemFeatureConfig config, Random random, BlockPos.Mutable mutable, BlockPos pos) {
        int x = pos.getX();
        int z = pos.getZ();

        for (int i = 0; i < config.maxRootColumnHeight; i++) {
            mutable.move(Direction.UP);
            if (TreeFeature.isReplaceableAt(readerIn, mutable)) {
                if (this.hasSpaceForTree(readerIn, config, mutable)) {
                    BlockPos below = mutable.down();
                    if (readerIn.getFluidState(below).isTagged(FluidTags.LAVA) || !readerIn.getBlockState(below).getMaterial().isSolid()) {
                        return false;
                    }

                    if (this.generateFeature(readerIn, generator, config, random, mutable)) {
                        return true;
                    }
                }
            } else {
                this.generateRoots(readerIn, config, random, x, z, mutable);
            }
        }

        return false;
    }

    private boolean generateFeature(ISeedReader reader, ChunkGenerator generator, RootSystemFeatureConfig config, Random random, BlockPos pos) {
        return config.feature.get().generate(reader, generator, random, pos);
    }

    private void generateRoots(ISeedReader readerIn, RootSystemFeatureConfig config, Random random, int x, int z, BlockPos.Mutable mutable) {
        int radius = config.rootRadius;
        ITag<Block> tag = BlockTags.getCollection().get(config.rootReplaceable);
        Predicate<BlockState> predicate = tag == null ? state -> true : state -> state.isIn(tag);

        for (int i = 0; i < config.hangingRootPlacementAttempts; i++) {
            mutable.setAndOffset(mutable, random.nextInt(radius) - random.nextInt(radius), 0, random.nextInt(radius) - random.nextInt(radius));
            if (predicate.test(readerIn.getBlockState(mutable))) {
                readerIn.setBlockState(mutable, config.rootStateProvider.getBlockState(random, mutable), 2);
            }

            mutable.setX(x);
            mutable.setZ(z);
        }
    }

    private void generateHangingRoots(ISeedReader readerIn, RootSystemFeatureConfig config, Random random, BlockPos pos, BlockPos.Mutable mutable) {
        int radius = config.hangingRootRadius;
        int verticalSpan = config.hangingRootVerticalSpan;

        for (int i = 0; i < config.hangingRootPlacementAttempts; i++) {
            mutable.setAndOffset(pos, random.nextInt(radius) - random.nextInt(radius), random.nextInt(verticalSpan) - random.nextInt(verticalSpan), random.nextInt(radius) - random.nextInt(radius));
            if (readerIn.isAirBlock(mutable)) {
                BlockState state = config.hangingRootStateProvider.getBlockState(random, mutable);
                if (state.isValidPosition(readerIn, mutable) && readerIn.getBlockState(mutable.up()).isSolidSide(readerIn, mutable, Direction.DOWN)) {
                    readerIn.setBlockState(mutable, state, 2);
                }
            }
        }
    }
}