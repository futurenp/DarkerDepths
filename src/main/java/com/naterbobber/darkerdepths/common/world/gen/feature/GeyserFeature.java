package com.naterbobber.darkerdepths.common.world.gen.feature;

import com.mojang.serialization.Codec;
import com.naterbobber.darkerdepths.core.registries.DDBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;

//<>

public class GeyserFeature extends Feature<NoFeatureConfig> {
    public GeyserFeature(Codec<NoFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(ISeedReader world, ChunkGenerator generator, Random rand, BlockPos pos, NoFeatureConfig config) {
//        if (world.isAirBlock(pos.down())) {
//            return false;
//        } else

        if (world.getBlockState(pos.up()).matchesBlock(Blocks.LAVA) && world.getBlockState(pos).isSolid()) {
            this.setBlockState(world, pos, DDBlocks.GEYSER.get().getDefaultState());
            return true;
        } else {
            return false;
        }
    }
}
