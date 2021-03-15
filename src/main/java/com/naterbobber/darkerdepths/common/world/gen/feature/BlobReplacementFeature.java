package com.naterbobber.darkerdepths.common.world.gen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.structure.StructureManager;

import javax.annotation.Nullable;
import java.util.Random;

//<>

public class BlobReplacementFeature extends Feature<BlobReplacementConfig> {
    public BlobReplacementFeature(Codec<BlobReplacementConfig> codec) {
        super(codec);
    }

    @Override
    public boolean func_230362_a_(ISeedReader worldIn, StructureManager structureManager, ChunkGenerator generator, Random rand, BlockPos pos, BlobReplacementConfig config) {
        BlockPos position = placeBlockOn(worldIn, pos.toMutable().func_239620_a_(Direction.Axis.Y, 1, worldIn.getHeight() - 1), config);
        if (position == null) {
            return false;
        } else {
            Vector3i reach = reach(rand, config);
            int radius = Math.max(reach.getX(), Math.max(reach.getY(), reach.getZ()));
            boolean shouldPlace = false;

            for(BlockPos distance : BlockPos.func_239583_a_(position, reach.getX(), reach.getY(), reach.getZ())) {
                if (distance.manhattanDistance(position) > radius) {
                    break;
                }

                BlockState blockstate = worldIn.getBlockState(distance);
                if (config.target.contains(blockstate)) {
                    this.func_230367_a_(worldIn, distance, config.blockState);
                    shouldPlace = true;
                }
            }

            return shouldPlace;
        }
    }

    @Nullable
    private static BlockPos placeBlockOn(IWorld worldIn, BlockPos.Mutable pos, BlobReplacementConfig config) {
        while(pos.getY() > 1) {
            BlockState blockstate = worldIn.getBlockState(pos);
            if (config.target.contains(blockstate)) {
                return pos;
            }

            pos.move(Direction.DOWN);
        }

        return null;
    }

    private static Vector3i reach(Random rand, BlobReplacementConfig config) {
        return new Vector3i(config.minimumReach.getX() + rand.nextInt(config.maximumReach.getX() - config.minimumReach.getX() + 1), config.minimumReach.getY() + rand.nextInt(config.maximumReach.getY() - config.minimumReach.getY() + 1), config.minimumReach.getZ() + rand.nextInt(config.maximumReach.getZ() - config.minimumReach.getZ() + 1));
    }
}