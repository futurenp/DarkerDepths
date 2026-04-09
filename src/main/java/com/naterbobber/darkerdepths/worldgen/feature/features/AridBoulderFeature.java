package com.naterbobber.darkerdepths.worldgen.feature.features;

import com.mojang.serialization.Codec;
import com.naterbobber.darkerdepths.init.DDBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.DripstoneUtils;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import java.util.function.Predicate;

public class AridBoulderFeature extends Feature<NoneFeatureConfiguration> {

    public AridBoulderFeature(Codec<NoneFeatureConfiguration> codece) {
        super(codece);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        BlockPos blockpos = context.origin();
        WorldGenLevel world = context.level();
        RandomSource random = context.random();
        if (!world.isEmptyBlock(blockpos) || !world.getBlockState(blockpos.below()).is(DDBlocks.ARIDROCK.get())) {
            return false;
        } else {
            boolean flag = false;
            int radius = 7;
            int height = Mth.nextInt(random, 8, 10);
            for (int x = -radius; x <= radius; x++) {
                for (int z = -radius; z <= radius; z++) {
                    for (int y = 0; y <= height; y++) {
                        BlockPos blockPos = new BlockPos(blockpos.getX() + x, blockpos.getY() - 3 + y, blockpos.getZ() + z);
                        if (y > 1) {
                            flag = this.generateBoulder(world, flag, radius, x, z, y, blockPos);
                        }
                    }
                }
            }
            return flag;
        }
    }

    private boolean generateBoulder(WorldGenLevel world, boolean flag, int radius, int x, int z, int y, BlockPos blockPos) {
        if (y * (x * x) + ((y * y) / 4) + y * (z * z) <= radius * radius) {
            if (world.isStateAtPosition(blockPos.below(), (state) -> DripstoneUtils.isEmptyOrWater(state) || state.is(DDBlocks.DRY_SPROUTS.get()))) {
                if (blockPos.below().getY() < world.getMinBuildHeight()) {
                    return false;
                }
                return this.generateBoulder(world, flag, radius, x / 2, z / 2, y, blockPos.below());
            }

            replaceLavaColumn(world, blockPos.below());

            Predicate<BlockState> canBeReplaced = (blockState) ->
                    DripstoneUtils.isEmptyOrWaterOrLava(blockState) ||
                            blockState.is(BlockTags.BASE_STONE_OVERWORLD) ||
                            blockState.is(DDBlocks.DRY_SPROUTS.get()) ||
                            blockState.is(Blocks.GLOW_LICHEN);

            if (canBeReplaced.test(world.getBlockState(blockPos))) {
                Block block = y % 4 == 0 ? DDBlocks.ARIDROCK.get() : Blocks.PACKED_MUD;
                world.setBlock(blockPos, block.defaultBlockState(), 2);
                flag = true;
            }
        }
        return flag;
    }

    private void replaceLavaColumn(WorldGenLevel world, BlockPos pos) {
        if (pos.getY() < world.getMinBuildHeight()) {
            return;
        }

        if (world.getBlockState(pos).is(Blocks.LAVA)) {
            world.setBlock(pos, DDBlocks.DARKSLATE.get().defaultBlockState(), 2);
            replaceLavaColumn(world, pos.below());
        }
    }
}