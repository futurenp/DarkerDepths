package com.naterbobber.darkerdepths.common.world.gen.feature;

import com.mojang.serialization.Codec;
import com.naterbobber.darkerdepths.core.registries.DDBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.ProbabilityConfig;

import java.util.Random;

public class HugeGlowshroomFeature extends Feature<NoFeatureConfig> {

    public HugeGlowshroomFeature(Codec<NoFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(ISeedReader world, ChunkGenerator generator, Random rand, BlockPos pos, NoFeatureConfig config) {
        if (world.isAirBlock(pos.down()) || world.getBlockState(pos.down()) == DDBlocks.GLOWSHROOM_BLOCK.get().getDefaultState() || !world.isAirBlock(pos.up())) {
            return false;
        } else {
            int height = MathHelper.nextInt(rand, 1, 2);
            BlockPos.Mutable mutable = new BlockPos.Mutable();
            for (int i = 0; i <= height; i++) {
                for (int x = (int) -1.5; x < 1.5; x++) {
                    for (int z = (int) -1.5; z < 1.5; z++) {
                        for (int k = 0; k <= height; k++) {
                                mutable.setPos(pos).move(Direction.UP, i);
                                this.setBlockState(world, mutable, DDBlocks.GLOWSHROOM_STEM.get().getDefaultState());
                                mutable.setAndOffset(pos, x, height, z).move(Direction.UP, i);
                                this.setBlockState(world, mutable, DDBlocks.GLOWSHROOM_BLOCK.get().getDefaultState());
                        }
                    }
                }
            }
            return true;
        }
    }

}
