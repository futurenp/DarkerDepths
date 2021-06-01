package com.naterbobber.darkerdepths.common.world.gen.feature;

import com.mojang.serialization.Codec;
import com.naterbobber.darkerdepths.core.registries.DDBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;

public class RandomMossyPatch extends Feature<NoFeatureConfig> {

    public RandomMossyPatch(Codec<NoFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(ISeedReader world, ChunkGenerator generator, Random rand, BlockPos pos, NoFeatureConfig config) {
        while (world.isAirBlock(pos) && pos.getY() > 2) {
            pos = pos.down();
        }
        if (world.isAirBlock(pos)) {
            return false;
        } else {
            BlockPos.Mutable mutable = new BlockPos.Mutable();
            for (int x = -4; x <= 4; x++) {
                for (int z = -4; z <= 4; z++) {
                    mutable.setAndOffset(pos, x + rand.nextInt(2) - rand.nextInt(12), 0, z + rand.nextInt(15) - rand.nextInt(2));
                    if (world.isAirBlock(mutable.down())) {
                        return false;
                    }
                    this.setBlockState(world, mutable, DDBlocks.MOSSY_GRIMESTONE.get().getDefaultState());
                }
            }
            return true;
        }
    }
}
