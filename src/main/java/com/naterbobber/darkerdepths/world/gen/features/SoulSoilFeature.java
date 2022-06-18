package com.naterbobber.darkerdepths.world.gen.features;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import org.apache.commons.compress.utils.Lists;

import java.util.List;
import java.util.Random;

public class SoulSoilFeature extends Feature<NoneFeatureConfiguration> {

    public SoulSoilFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel world = context.level();
        BlockPos blockPos = context.origin();
        RandomSource random = context.random();
        if (!world.getBlockState(blockPos).is(BlockTags.BASE_STONE_OVERWORLD)) {
            return false;
        } else {
            List<BlockPos> replacePos = Lists.newArrayList();
            for (int x = -8; x <= 8; x++) {
                for (int z = -8; z <= 8; z++) {
                    for (int y = -2; y <= 2; y++) {
                        BlockPos pos = new BlockPos(blockPos.getX() + x, blockPos.getY() + y, blockPos.getZ() + z);
                        for (Direction direction : Direction.values()) {
                            if (world.getBlockState(pos).is(BlockTags.BASE_STONE_OVERWORLD) && world.getBlockState(pos.relative(direction)).is(Blocks.LAVA) && direction != Direction.DOWN) {
                                replacePos.add(pos);
                            }
                        }
                    }
                }
            }
            for (BlockPos blockPos1 : replacePos) {
                world.setBlock(blockPos1, Blocks.SOUL_SOIL.defaultBlockState(), 2);
            }
            return true;
        }
    }
}
