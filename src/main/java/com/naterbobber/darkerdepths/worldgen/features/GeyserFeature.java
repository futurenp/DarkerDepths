package com.naterbobber.darkerdepths.worldgen.features;

import com.mojang.serialization.Codec;
import com.naterbobber.darkerdepths.init.DDBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class GeyserFeature extends Feature<NoneFeatureConfiguration> {

    public GeyserFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel world = context.level();
        BlockPos blockPos = context.origin();
        if ((world.getBlockState(blockPos.above()).is(Blocks.LAVA) || world.getBlockState(blockPos.above()).is(Blocks.WATER)) && world.getBlockState(blockPos).canOcclude()) {
            this.setBlock(world, blockPos, DDBlocks.GEYSER.get().defaultBlockState());
            return true;
        } else {
            return false;
        }
    }
}
