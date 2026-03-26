package com.naterbobber.darkerdepths.worldgen.feature.features;

import com.mojang.serialization.Codec;
import com.naterbobber.darkerdepths.init.DDBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;

public class GeyserFeature extends Feature<SimpleBlockConfiguration> {

    public GeyserFeature(Codec<SimpleBlockConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<SimpleBlockConfiguration> context) {
        WorldGenLevel world = context.level();
        BlockPos blockPos = context.origin();
        if ((world.getBlockState(blockPos.above()).is(Blocks.LAVA) || world.getBlockState(blockPos.above()).is(Blocks.WATER))
                && world.getBlockState(blockPos).canOcclude()
                && world.getBlockState(blockPos.below()).canOcclude()){
            this.setBlock(world, blockPos, DDBlocks.GEYSER.get().defaultBlockState());
            this.setBlock(world, blockPos.below(), DDBlocks.SCORCHED_REMAINS_BLOCK.get().defaultBlockState());
            return true;
        } else {
            return false;
        }
    }
}
