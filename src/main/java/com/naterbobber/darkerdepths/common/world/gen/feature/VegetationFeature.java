package com.naterbobber.darkerdepths.common.world.gen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.BlockStateProvidingFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

import java.util.Random;

//<>

public class VegetationFeature extends Feature<BlockStateProvidingFeatureConfig> {
    public VegetationFeature(Codec<BlockStateProvidingFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, BlockStateProvidingFeatureConfig config) {
        return shouldGenerate(reader, rand, pos, config, 8, 4);
    }

    public static boolean shouldGenerate(IWorld worldIn, Random rand, BlockPos pos, BlockStateProvidingFeatureConfig config, int width, int height) {
        Block block = worldIn.getBlockState(pos).getBlock();
        BlockState state = worldIn.getBlockState(pos);
        if (!block.isValidPosition(state, worldIn, pos)) {
            return false;
        } else {
            int y = pos.getY();
            if (y >= 1 && y + 1 < 256) {
                int featureCount = 0;

                for(int index = 0; index < width * width; ++index) {
                    BlockPos blockpos = pos.add(rand.nextInt(width) - rand.nextInt(width), rand.nextInt(height) - rand.nextInt(height), rand.nextInt(width) - rand.nextInt(width));
                    BlockState blockstate = config.stateProvider.getBlockState(rand, blockpos);
                    if (!worldIn.isAirBlock(pos.down()) && worldIn.isAirBlock(blockpos) && blockpos.getY() > 0 && blockstate.isValidPosition(worldIn, blockpos)) {
                        worldIn.setBlockState(blockpos, blockstate, 2);
                        ++featureCount;
                    }
                }

                return featureCount > 0;
            } else {
                return false;
            }
        }
    }
}