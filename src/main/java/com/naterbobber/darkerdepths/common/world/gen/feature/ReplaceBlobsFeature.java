package com.naterbobber.darkerdepths.common.world.gen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;

import javax.annotation.Nullable;
import java.util.Random;

//<>

public class ReplaceBlobsFeature extends Feature<ReplaceBlobsFeatureConfig> {
    public ReplaceBlobsFeature(Codec<ReplaceBlobsFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, ReplaceBlobsFeatureConfig config) {
        BlockPos target = moveDownToTarget(reader, pos.toMutable().clampAxisCoordinate(Direction.Axis.Y, 1, reader.getHeight() - 1), config);
        if (target == null) {
            return false;
        } else {
            int x = config.getRadius().get(rand);
            int y = config.getRadius().get(rand);
            int z = config.getRadius().get(rand);
            int radius = Math.max(x, Math.max(y, z));
            boolean shouldGenerate = false;

            for (BlockPos positions : BlockPos.getProximitySortedBoxPositionsIterator(target, x, y, z)) {
                if (positions.manhattanDistance(target) > radius) {
                    break;
                }

                BlockState state = reader.getBlockState(positions);
                if (config.target.contains(state)) {
                    this.setBlockState(reader, positions, config.state);
                    shouldGenerate = true;
                }
            }

            return shouldGenerate;
        }
    }

    @Nullable
    private static BlockPos moveDownToTarget(IWorld worldIn, BlockPos.Mutable mutable, ReplaceBlobsFeatureConfig config) {
        while (mutable.getY() > 1) {
            BlockState state = worldIn.getBlockState(mutable);
            if (config.target.contains(state)) {
                return mutable;
            }

            mutable.move(Direction.DOWN);
        }

        return null;
    }
}