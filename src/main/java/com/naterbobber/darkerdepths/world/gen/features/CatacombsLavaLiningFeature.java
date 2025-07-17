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
        RandomSource random = context.random(); // Re-introduce random here, as placeCluster will need it

        int blocksPlaced = 0;
        int searchRadius = 8; // Adjust as needed

        for (int x = -searchRadius; x <= searchRadius; x++) {
            for (int y = -searchRadius; y <= searchRadius; y++) {
                for (int z = -searchRadius; z <= searchRadius; z++) {
                    BlockPos currentPos = origin.offset(x, y, z);
                    BlockState blockState = world.getBlockState(currentPos);

                    if (blockState.is(Blocks.LAVA)) {
                        // Iterate through all six cardinal directions
                        for (Direction direction : Direction.values()) {
                            BlockPos adjacentPos = currentPos.relative(direction);
                            BlockState adjacentBlockState = world.getBlockState(adjacentPos);

                            // Define the blocks that you want to be replaced by darkslate.
                            if (!adjacentBlockState.is(Blocks.LAVA) &&
                                    (adjacentBlockState.is(BlockTags.BASE_STONE_OVERWORLD) // Includes stone, deepslate, etc.
                                            || adjacentBlockState.is(DDBlocks.ARID_DEEPSLATE.get())
                                            // Don't include DDBlocks.DUSKROCK.get() here if you want to replace it *with* limestone
                                            || adjacentBlockState.is(DDBlocks.ARIDROCK.get())
                                    )) {

                                // Replace with your actual darkslate block
                                world.setBlock(adjacentPos, DDBlocks.DARKSLATE.get().defaultBlockState(), 2);
                                blocksPlaced++;

                                if(world.getBlockState(adjacentPos.above()).is(DDBlocks.DRY_SPROUTS.get())){
                                    world.setBlock(adjacentPos.above(), Blocks.AIR.defaultBlockState(), 2);
                                }

                                // Place limestone patch centered around this newly placed darkslate block
                                // Pass the necessary context to placeCluster
                                placeCluster(world, adjacentPos, random);
                            }
                        }
                    }
                }
            }
        }
        return blocksPlaced > 0;
    }

    /**
     * Places a cluster of Limestone, replacing specified blocks in the area.
     * @param world The world to place blocks in.
     * @param centerPos The center position for the cluster.
     * @param random The RandomSource for generating the cluster shape.
     */
    public void placeCluster(WorldGenLevel world, BlockPos centerPos, RandomSource random){
        int clusterRadius = 1 + random.nextInt(2); // Example: cluster radius 3-5 blocks
        int replacedCount = 0; // Track how many blocks were replaced by limestone

        // Iterate in a sphere around the centerPos
        for (int x = -clusterRadius; x <= clusterRadius; x++) {
            for (int y = -clusterRadius; y <= clusterRadius; y++) {
                for (int z = -clusterRadius; z <= clusterRadius; z++) {
                    // Check if the current position is within the sphere
                    if (x * x + y * y + z * z <= clusterRadius * clusterRadius) {
                        BlockPos currentClusterPos = centerPos.offset(x, y, z);
                        BlockState currentClusterBlockState = world.getBlockState(currentClusterPos);

                        // Define blocks that can be replaced by Limestone
                        // IMPORTANT: Don't replace lava or your new darkslate with limestone immediately.
                        // Decide if you want limestone to replace BASE_STONE_OVERWORLD (e.g., vanilla stone, deepslate)
                        // or only your custom ones.
                        if (!currentClusterBlockState.is(Blocks.LAVA) && // Don't replace lava
                                !currentClusterBlockState.is(DDBlocks.DARKSLATE.get()) && // Don't replace your newly placed darkslate
                                (currentClusterBlockState.is(DDBlocks.ARIDROCK.get()) ||
                                        currentClusterBlockState.is(DDBlocks.ARID_DEEPSLATE.get()) ||
                                        currentClusterBlockState.is(BlockTags.BASE_STONE_OVERWORLD) // This includes vanilla deepslate, stone, etc.
                                        // Add other blocks if you want them replaced by limestone, e.g., Blocks.GRAVEL
                                )) {

                            // You might want to add a small chance here too, if you don't want 100% replacement
                            // if (random.nextFloat() < 0.9F) { // Example: 90% chance to replace in the cluster
                            world.setBlock(currentClusterPos, DDBlocks.DUSKROCK.get().defaultBlockState(), 2);
                            replacedCount++;
                            // }
                        }
                    }
                }
            }
        }
        // Optionally, return replacedCount or do something with it, e.g., for debugging
        // if (replacedCount > 0) { System.out.println("Placed " + replacedCount + " limestone blocks at " + centerPos); }
    }
}