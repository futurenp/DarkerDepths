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

public class HugeGlowshroomFeature extends Feature<HugeGlowshroomConfig> {

    public HugeGlowshroomFeature(Codec<HugeGlowshroomConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(ISeedReader world, ChunkGenerator generator, Random rand, BlockPos pos, HugeGlowshroomConfig config) {
        if (world.getBlockState(pos.down()) == DDBlocks.GLOWSPURS.get().getDefaultState() || world.getBlockState(pos.down()) == DDBlocks.GLOWSHROOM.get().getDefaultState() || world.isAirBlock(pos.down()) || world.getBlockState(pos.down()) == DDBlocks.GLOWSHROOM_BLOCK.get().getDefaultState() || !world.isAirBlock(pos.up())) {
            return false;
        } else {
            int height = MathHelper.nextInt(rand, 1, 2);
            BlockPos.Mutable mutable = new BlockPos.Mutable();
            for (int i = 0; i <= height; i++) {
                for (int x = -1; x <= 1; x++) {
                    for (int z = -1; z <= 1; z++) {
                        for (int k = 0; k <= height; k++) {
                                mutable.setPos(pos).move(Direction.UP, i);
                                this.setBlockState(world, mutable, config.stem);
                                mutable.setAndOffset(pos, x, height, z).move(Direction.UP, i);
                                this.setBlockState(world, mutable, config.cap);
                        }
                    }
                }
            }
            return true;
        }
    }

}
