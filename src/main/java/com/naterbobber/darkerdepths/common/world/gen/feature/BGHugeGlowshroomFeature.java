package com.naterbobber.darkerdepths.common.world.gen.feature;

import com.mojang.serialization.Codec;
import com.naterbobber.darkerdepths.core.registries.DDBlocks;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;

//<>

public class BGHugeGlowshroomFeature extends Feature<NoFeatureConfig> {
    public BGHugeGlowshroomFeature(Codec<NoFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(ISeedReader world, ChunkGenerator generator, Random rand, BlockPos pos, NoFeatureConfig config) {
        BlockPos.Mutable blockPos = new BlockPos.Mutable();
        if (!world.getBlockState(pos).isAir() && !world.getBlockState(pos.down()).matchesBlock(DDBlocks.MOSSY_GRIMESTONE.get()) || world.getBlockState(pos.down()).matchesBlock(DDBlocks.GLOWSHROOM_BLOCK.get())) {
            return false;
        } else {
            int maxHeight = MathHelper.nextInt(rand, 2, 5);
            for (int height = 0; height < maxHeight; height++) {
                blockPos.setPos(pos).move(Direction.UP, height);
                this.setBlockState(world, blockPos, DDBlocks.GLOWSHROOM_STEM.get().getDefaultState());
            }
            if (maxHeight > 3) {
                this.generateLargeCap(world, blockPos);
            } else {
                this.generateSmallCap(world, blockPos);
            }
        }

        return true;
    }

    private void generateLargeCap(IWorld world, BlockPos.Mutable mutable) {
        this.generate2DCap(world, mutable, Direction.UP, 1, false);
        this.generate2DCap(world, mutable, Direction.NORTH, 1, true);
        this.generate2DCap(world, mutable, Direction.SOUTH, 1, true);
        this.generate2DCap(world, mutable, Direction.WEST, 1, true);
        this.generate2DCap(world, mutable, Direction.EAST, 1, true);
        this.generate2DCap(world, mutable, Direction.NORTH, 1, Direction.WEST, 1);
        this.generate2DCap(world, mutable, Direction.NORTH, 1, Direction.EAST, 1);
        this.generate2DCap(world, mutable, Direction.SOUTH, 1, Direction.WEST, 1);
        this.generate2DCap(world, mutable, Direction.SOUTH, 1, Direction.EAST, 1);
        this.generate2DCap(world, mutable, Direction.NORTH, 2, Direction.WEST, 1);
        this.generate2DCap(world, mutable, Direction.NORTH, 2, true);
        this.generate2DCap(world, mutable, Direction.NORTH, 2, Direction.EAST, 1);
        this.generate2DCap(world, mutable, Direction.SOUTH, 2, Direction.WEST, 1);
        this.generate2DCap(world, mutable, Direction.SOUTH, 2, true);
        this.generate2DCap(world, mutable, Direction.SOUTH, 2, Direction.EAST, 1);
        this.generate2DCap(world, mutable, Direction.WEST, 2, Direction.NORTH, 1);
        this.generate2DCap(world, mutable, Direction.WEST, 2, true);
        this.generate2DCap(world, mutable, Direction.WEST, 2, Direction.SOUTH, 1);
        this.generate2DCap(world, mutable, Direction.EAST, 2, Direction.NORTH, 1);
        this.generate2DCap(world, mutable, Direction.EAST, 2, true);
        this.generate2DCap(world, mutable, Direction.EAST, 2, Direction.SOUTH, 1);
        mutable.move(Direction.DOWN);
        this.generate2DCap(world, mutable, Direction.NORTH, 2, Direction.WEST, 1);
        this.generate2DCap(world, mutable, Direction.NORTH, 2, true);
        this.generate2DCap(world, mutable, Direction.NORTH, 2, Direction.EAST, 1);
        this.generate2DCap(world, mutable, Direction.NORTH, 2, Direction.WEST, 2);
        this.generate2DCap(world, mutable, Direction.SOUTH, 2, Direction.WEST, 1);
        this.generate2DCap(world, mutable, Direction.SOUTH, 2, true);
        this.generate2DCap(world, mutable, Direction.SOUTH, 2, Direction.EAST, 1);
        this.generate2DCap(world, mutable, Direction.SOUTH, 2, Direction.EAST, 2);
        this.generate2DCap(world, mutable, Direction.WEST, 2, Direction.NORTH, 1);
        this.generate2DCap(world, mutable, Direction.WEST, 2, true);
        this.generate2DCap(world, mutable, Direction.WEST, 2, Direction.SOUTH, 1);
        this.generate2DCap(world, mutable, Direction.WEST, 2, Direction.SOUTH, 2);
        this.generate2DCap(world, mutable, Direction.EAST, 2, Direction.NORTH, 1);
        this.generate2DCap(world, mutable, Direction.EAST, 2, true);
        this.generate2DCap(world, mutable, Direction.EAST, 2, Direction.SOUTH, 1);
        this.generate2DCap(world, mutable, Direction.EAST, 2, Direction.NORTH, 2);
        mutable.move(Direction.DOWN);
        this.generate2DCap(world, mutable, Direction.NORTH, 2, Direction.WEST, 1);
        this.generate2DCap(world, mutable, Direction.NORTH, 2, true);
        this.generate2DCap(world, mutable, Direction.NORTH, 2, Direction.EAST, 1);
        this.generate2DCap(world, mutable, Direction.NORTH, 2, Direction.WEST, 2);
        this.generate2DCap(world, mutable, Direction.SOUTH, 2, Direction.WEST, 1);
        this.generate2DCap(world, mutable, Direction.SOUTH, 2, true);
        this.generate2DCap(world, mutable, Direction.SOUTH, 2, Direction.EAST, 1);
        this.generate2DCap(world, mutable, Direction.SOUTH, 2, Direction.EAST, 2);
        this.generate2DCap(world, mutable, Direction.WEST, 2, Direction.NORTH, 1);
        this.generate2DCap(world, mutable, Direction.WEST, 2, true);
        this.generate2DCap(world, mutable, Direction.WEST, 2, Direction.SOUTH, 1);
        this.generate2DCap(world, mutable, Direction.WEST, 2, Direction.SOUTH, 2);
        this.generate2DCap(world, mutable, Direction.EAST, 2, Direction.NORTH, 1);
        this.generate2DCap(world, mutable, Direction.EAST, 2, true);
        this.generate2DCap(world, mutable, Direction.EAST, 2, Direction.SOUTH, 1);
        this.generate2DCap(world, mutable, Direction.EAST, 2, Direction.NORTH, 2);
    }

    private void generateSmallCap(IWorld world, BlockPos.Mutable mutable) {
//        for (int x = -1; x <= 1; x++) {
//            for (int z = -1; z <= 1; z++) {
//                for (int y = 0; y < 2; y++) {
//                    BlockPos.Mutable mutable1 = new BlockPos.Mutable();
//                    mutable1.setAndOffset(mutable, x, y, z);
//                    this.setBlockState(world, mutable1, DDBlocks.GLOWSHROOM_BLOCK.get().getDefaultState());
//                }
//            }
//        }
        this.generate2DCap(world, mutable, Direction.UP, 1, false);
        this.generate2DCap(world, mutable, Direction.NORTH, 1, true);
        this.generate2DCap(world, mutable, Direction.SOUTH, 1, true);
        this.generate2DCap(world, mutable, Direction.WEST, 1, true);
        this.generate2DCap(world, mutable, Direction.EAST, 1, true);
        this.generate2DCap(world, mutable, Direction.NORTH, 1, Direction.WEST, 1);
        this.generate2DCap(world, mutable, Direction.NORTH, 1, Direction.EAST, 1);
        this.generate2DCap(world, mutable, Direction.SOUTH, 1, Direction.WEST, 1);
        this.generate2DCap(world, mutable, Direction.SOUTH, 1, Direction.EAST, 1);
        mutable.move(Direction.DOWN);
        this.generate2DCap(world, mutable, Direction.NORTH, 1, true);
        this.generate2DCap(world, mutable, Direction.SOUTH, 1, true);
        this.generate2DCap(world, mutable, Direction.WEST, 1, true);
        this.generate2DCap(world, mutable, Direction.EAST, 1, true);
        this.generate2DCap(world, mutable, Direction.NORTH, 1, Direction.WEST, 1);
        this.generate2DCap(world, mutable, Direction.NORTH, 1, Direction.EAST, 1);
        this.generate2DCap(world, mutable, Direction.SOUTH, 1, Direction.WEST, 1);
        this.generate2DCap(world, mutable, Direction.SOUTH, 1, Direction.EAST, 1);
    }

    private void generate2DCap(IWorld world, BlockPos.Mutable mutable, Direction xzSide, int xzOffset, Direction ySize, int yOffset) {
        if (this.isValidPosition(world, mutable)) {
            mutable.move(xzSide, xzOffset).move(ySize, yOffset);
            this.setBlockState(world, mutable, DDBlocks.GLOWSHROOM_BLOCK.get().getDefaultState());
            mutable.move(xzSide.getOpposite(), xzOffset).move(ySize.getOpposite(), yOffset);
        }
    }

    private void generate2DCap(IWorld world, BlockPos.Mutable mutable, Direction side, int offset, boolean shouldReturn) {
        if (this.isValidPosition(world, mutable)) {
            mutable.move(side, offset);
            this.setBlockState(world, mutable, DDBlocks.GLOWSHROOM_BLOCK.get().getDefaultState());
            if (shouldReturn) {
                mutable.move(side.getOpposite(), offset);
            }
        }
    }

    public boolean isValidPosition(IWorld world, BlockPos pos) {
        return !world.getBlockState(pos).isAir();
    }
}