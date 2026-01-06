package com.naterbobber.darkerdepths.worldgen.features;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.naterbobber.darkerdepths.worldgen.features.config.ReplaceListLayeredConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

import javax.annotation.Nullable;

public class ReplaceListLayeredFeature extends Feature<ReplaceListLayeredConfig> {

    public ReplaceListLayeredFeature(Codec<ReplaceListLayeredConfig> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<ReplaceListLayeredConfig> context) {
        ReplaceListLayeredConfig config = context.config();
        ImmutableList<BlockState> layers = ImmutableList.copyOf(config.layers());
        WorldGenLevel worldgenlevel = context.level();
        RandomSource random = context.random();
        BlockPos blockpos = findTarget(worldgenlevel, context.origin().mutable().clamp(Direction.Axis.Y, worldgenlevel.getMinBuildHeight() + 1, worldgenlevel.getMaxBuildHeight() - 1), config);
        if (blockpos == null) {
            return false;
        } else {
            int i = config.radius().sample(random);
            int j = config.radius().sample(random);
            int k = config.radius().sample(random);
            int l = Math.max(i, Math.max(j, k));
            boolean flag = false;

            for(BlockPos blockpos1 : BlockPos.withinManhattan(blockpos, i, j, k)) {
                if (blockpos1.distManhattan(blockpos) > l) {
                    break;
                }

                BlockState blockstate = worldgenlevel.getBlockState(blockpos1);
                if (config.targetList().contains(blockstate)) {
                    int height = blockpos1.getY();
                    int size = layers.size();
                    BlockState blockToPlace = layers.get(Math.abs(height % size));

                    this.setBlock(worldgenlevel, blockpos1, blockToPlace);
                    flag = true;
                }
            }

            return flag;
        }
    }

    @Nullable
    private static BlockPos findTarget(LevelAccessor p_66635_, BlockPos.MutableBlockPos mutable, ReplaceListLayeredConfig config) {
        while(mutable.getY() > p_66635_.getMinBuildHeight() + 1) {
            BlockState blockstate = p_66635_.getBlockState(mutable);
            if (config.targetList().contains(blockstate)) {
                return mutable;
            }

            mutable.move(Direction.DOWN);
        }

        return null;
    }
}
