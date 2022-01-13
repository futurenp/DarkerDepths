package com.naterbobber.darkerdepths.world.gen.features;

import com.mojang.serialization.Codec;
import com.naterbobber.darkerdepths.init.DDBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import java.util.Random;

public class AridBoulderFeature extends Feature<NoneFeatureConfiguration> {

    public AridBoulderFeature(Codec<NoneFeatureConfiguration> codece) {
        super(codece);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        BlockPos blockpos = context.origin();
        WorldGenLevel world = context.level();
        Random random = context.random();

        if (!world.isEmptyBlock(blockpos) || !world.getBlockState(blockpos.below()).is(BlockTags.BASE_STONE_OVERWORLD)) {
            return false;
        } else {
            boolean flag = false;
            int radius = 8;
            int height = Mth.nextInt(random, 8, 16);
            for (int x = -radius; x <= radius; x++) {
                for (int z = -radius; z <= radius; z++) {
                    for (int y = 0; y <= height; y++) {
                        BlockPos blockPos = new BlockPos(blockpos.getX() + x, blockpos.getY() + y, blockpos.getZ() + z);
                        if (y > 1) {
                            if (y * (x * x) + ((y * y) / 4) + y * (z * z) <= radius * radius) {
                                Block block = y > height / 2 ? DDBlocks.ARIDROCK.get() : DDBlocks.LIMESTONE.get();
                                world.setBlock(blockPos, block.defaultBlockState(), 2);
                            }
                        }
                    }
                }
            }
            return flag;
        }
    }
}
