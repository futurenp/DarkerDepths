package com.naterbobber.darkerdepths.world.gen.features;

import com.mojang.serialization.Codec;
import com.naterbobber.darkerdepths.init.DDBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.AmethystClusterBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class GemstoneFeature extends Feature<NoneFeatureConfiguration> {

    public GemstoneFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel worldIn = context.level();
        BlockPos pos = context.origin();
        BlockState state = worldIn.getBlockState(pos);
        if (!worldIn.isStateAtPosition(pos, blockstate -> blockstate.isAir() || blockstate.is(Blocks.WATER))) {
            return false;
        } else {
            for (Direction direction : Direction.values()) {
                if (AmethystClusterBlock.isFaceFull(worldIn.getBlockState(pos.relative(direction)).getCollisionShape(worldIn, pos.relative(direction)), direction.getOpposite())) {
                    worldIn.setBlock(pos, DDBlocks.AMBER_CLUSTER.get().defaultBlockState().setValue(AmethystClusterBlock.FACING, direction.getOpposite()).setValue(BlockStateProperties.WATERLOGGED, state.is(Blocks.WATER)), 2);
                    return true;
                }
            }
        }

        return false;
    }
}
