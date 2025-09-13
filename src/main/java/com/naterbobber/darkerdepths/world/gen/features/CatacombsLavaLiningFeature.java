package com.naterbobber.darkerdepths.world.gen.features;

import com.mojang.serialization.Codec;
import com.naterbobber.darkerdepths.init.DDBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource; // Re-import RandomSource if placeCluster uses it
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class CatacombsLavaLiningFeature extends Feature<NoneFeatureConfiguration> {

    public CatacombsLavaLiningFeature(Codec<NoneFeatureConfiguration> p_65792_) {
        super(p_65792_);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel world = context.level();
        BlockPos origin = context.origin();
        RandomSource random = context.random();

        int blocksPlaced = 0;
        int searchRadius = 8;

        for (int x = -searchRadius; x <= searchRadius; x++) {
            for (int y = -searchRadius; y <= searchRadius; y++) {
                for (int z = -searchRadius; z <= searchRadius; z++) {
                    BlockPos currentPos = origin.offset(x, y, z);
                    BlockState blockState = world.getBlockState(currentPos);

                    if (blockState.is(Blocks.LAVA)) {
                        for (Direction direction : Direction.values()) {
                            BlockPos adjacentPos = currentPos.relative(direction);
                            BlockState adjacentBlockState = world.getBlockState(adjacentPos);

                            if (!adjacentBlockState.is(Blocks.LAVA) &&
                                    (adjacentBlockState.is(BlockTags.BASE_STONE_OVERWORLD)
                                            || adjacentBlockState.is(DDBlocks.ARID_DEEPSLATE.get())
                                            || adjacentBlockState.is(DDBlocks.ARIDROCK.get())
                                            || adjacentBlockState.is(DDBlocks.DUSKROCK.get())
                                            || adjacentBlockState.is(Blocks.PACKED_MUD)

                                    )) {


                                world.setBlock(adjacentPos, DDBlocks.DARKSLATE.get().defaultBlockState(), 2);
                                blocksPlaced++;

                                if(world.getBlockState(adjacentPos.above()).is(DDBlocks.DRY_SPROUTS.get())){
                                    world.setBlock(adjacentPos.above(), Blocks.AIR.defaultBlockState(), 2);
                                }

                                placeCluster(world, adjacentPos, random);
                            }
                        }
                    }
                }
            }
        }
        return blocksPlaced > 0;
    }

    public void placeCluster(WorldGenLevel world, BlockPos centerPos, RandomSource random){
        int clusterRadius = 1 + random.nextInt(2);
        int replacedCount = 0;

        for (int x = -clusterRadius; x <= clusterRadius; x++) {
            for (int y = -clusterRadius; y <= clusterRadius; y++) {
                for (int z = -clusterRadius; z <= clusterRadius; z++) {
                    if (x * x + y * y + z * z <= clusterRadius * clusterRadius) {
                        BlockPos currentClusterPos = centerPos.offset(x, y, z);
                        BlockState currentClusterBlockState = world.getBlockState(currentClusterPos);

                        if (!currentClusterBlockState.is(Blocks.LAVA) &&
                                !currentClusterBlockState.is(DDBlocks.DARKSLATE.get()) &&
                                (currentClusterBlockState.is(DDBlocks.ARIDROCK.get()) ||
                                        currentClusterBlockState.is(DDBlocks.ARID_DEEPSLATE.get()) ||
                                        currentClusterBlockState.is(BlockTags.BASE_STONE_OVERWORLD) ||
                                        currentClusterBlockState.is(Blocks.PACKED_MUD)
                                )) {

                            world.setBlock(currentClusterPos, DDBlocks.DUSKROCK.get().defaultBlockState(), 2);
                            replacedCount++;
                        }
                    }
                }
            }
        }
    }
}