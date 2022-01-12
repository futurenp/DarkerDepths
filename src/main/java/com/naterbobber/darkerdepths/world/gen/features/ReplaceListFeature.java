package com.naterbobber.darkerdepths.world.gen.features;

import com.mojang.serialization.Codec;
import com.naterbobber.darkerdepths.world.gen.features.config.ReplaceListConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

import javax.annotation.Nullable;
import java.util.Random;

public class ReplaceListFeature extends Feature<ReplaceListConfig> {

    public ReplaceListFeature(Codec<ReplaceListConfig> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<ReplaceListConfig> context) {
        ReplaceListConfig config = context.config();
        WorldGenLevel worldgenlevel = context.level();
        Random random = context.random();
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
                if (config.targetList.contains(blockstate)) {
                    this.setBlock(worldgenlevel, blockpos1, config.replaceState);
                    flag = true;
                }
            }

            return flag;
        }
    }

    @Nullable
    private static BlockPos findTarget(LevelAccessor p_66635_, BlockPos.MutableBlockPos mutable, ReplaceListConfig config) {
        while(mutable.getY() > p_66635_.getMinBuildHeight() + 1) {
            BlockState blockstate = p_66635_.getBlockState(mutable);
            if (config.targetList.contains(blockstate)) {
                return mutable;
            }

            mutable.move(Direction.DOWN);
        }

        return null;
    }
}
