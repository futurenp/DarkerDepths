package com.naterbobber.darkerdepths.common.worldgen.feature.features;

import com.mojang.serialization.Codec;
import com.naterbobber.darkerdepths.common.init.DDBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.DripstoneUtils;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import java.util.function.Predicate;

public class AridBoulderFeature extends Feature<NoneFeatureConfiguration> {
    private static final int SHAPE_RADIUS = 7;

    public AridBoulderFeature(Codec<NoneFeatureConfiguration> codece) {
        super(codece);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        var blockpos = context.origin();
        var world = context.level();
        var random = context.random();
        if (!world.isEmptyBlock(blockpos) || !world.getBlockState(blockpos.below()).is(DDBlocks.ARIDROCK)) {
            return false;
        } else {
            boolean flag = false;
            double thickness = Mth.nextDouble(random, 0.3D, 1D);
            int radius = Mth.ceil(SHAPE_RADIUS * thickness);
            int height = Mth.nextInt(random, 7, 14);
            for (int x = -radius; x <= radius; x++) {
                for (int z = -radius; z <= radius; z++) {
                    for (int y = 0; y <= height; y++) {
                        var blockPos = new BlockPos(blockpos.getX() + x, blockpos.getY() - 3 + y, blockpos.getZ() + z);
                        if (y > 1) {
                            flag = this.generateBoulder(world, flag, thickness, x, z, y, blockPos);
                        }
                    }
                }
            }
            return flag;
        }
    }

    private boolean generateBoulder(WorldGenLevel world, boolean flag, double thickness, int x, int z, int y, BlockPos blockPos) {
        double scaledX = x / thickness;
        double scaledZ = z / thickness;
        if (y * (scaledX * scaledX + scaledZ * scaledZ) + ((double) (y * y) / 4) <= SHAPE_RADIUS * SHAPE_RADIUS) {
            if (world.isStateAtPosition(blockPos.below(), (state) -> DripstoneUtils.isEmptyOrWater(state) || state.is(DDBlocks.DRY_SPROUTS.get()))) {
                if (blockPos.below().getY() < world.getMinBuildHeight()) {
                    return false;
                }
                return this.generateBoulder(world, flag, thickness, x / 2, z / 2, y, blockPos.below());
            }

            replaceLavaColumn(world, blockPos.below());

            var canBeReplaced = (Predicate<BlockState>) (blockState) ->
                    DripstoneUtils.isEmptyOrWaterOrLava(blockState) ||
                            blockState.is(BlockTags.BASE_STONE_OVERWORLD) ||
                            blockState.is(DDBlocks.DRY_SPROUTS.get()) ||
                            blockState.is(Blocks.GLOW_LICHEN);

            if (canBeReplaced.test(world.getBlockState(blockPos))) {
                var block = y % 4 == 0 ? DDBlocks.ARIDROCK.get() : DDBlocks.DRY_DIRT.get();
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
