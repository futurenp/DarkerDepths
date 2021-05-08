package com.naterbobber.darkerdepths.common.world.gen.carver;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.Codec;
import com.naterbobber.darkerdepths.core.registries.DDBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.carver.CaveWorldCarver;
import net.minecraft.world.gen.feature.ProbabilityConfig;
import org.apache.commons.lang3.mutable.MutableBoolean;

import java.util.BitSet;
import java.util.Random;
import java.util.function.Function;

//<>

public class LargeCaveCarver extends CaveWorldCarver {
    public LargeCaveCarver(Codec<ProbabilityConfig> codec, int maxHeight) {
        super(codec, maxHeight);
    }

    //Cave Shape
    protected boolean func_230358_a_(IChunk chunk, Function<BlockPos, Biome> posToBiome, BitSet carvingMask, Random rand, BlockPos.Mutable mutable, BlockPos.Mutable mutable2, BlockPos.Mutable mutable3, int seaLevel, int mainChunkX, int mainChunkZ, int x, int z, int relativeX, int y, int relativeZ, MutableBoolean mutableBoolean) {
        int i = relativeX | relativeZ << 4 | y << 8;
        if (carvingMask.get(i)) {
            return false;
        }
        else {
            carvingMask.set(i);
            mutable.setPos(x, y, z);
            BlockState blockstate = chunk.getBlockState(mutable);
            BlockState blockstate1 = chunk.getBlockState(mutable2.setAndMove(mutable, Direction.UP));
            if (blockstate.matchesBlock(Blocks.GRASS_BLOCK) || blockstate.matchesBlock(Blocks.MYCELIUM)) {
                mutableBoolean.setTrue();
            }

            if (!this.canCarveBlock(blockstate, blockstate1)) {
                return false;
            } else if (y < 11) {
                chunk.setBlockState(mutable, LAVA.getBlockState(), false);
            }
                else {
                    chunk.setBlockState(mutable, CAVE_AIR, false);
                    if (mutableBoolean.isTrue()) {
                        mutable3.setAndMove(mutable, Direction.DOWN);
//                        if (chunk.getBlockState(mutable3).isIn(Blocks.DIRT)) {
//                            chunk.setBlockState(mutable3, posToBiome.apply(mutable).getSurfaceBuilderConfig().getTop(), false);
//                        }
                    }
                }
                return true;
            }
        }


    //Tunnel Width
    @Override
    protected float func_230359_a_(Random random) {
        float f = random.nextFloat() * 4.0F + random.nextFloat();
        if (random.nextInt(5) == 0) {
            f *= random.nextFloat() * random.nextFloat() * 2.5F + 1.0F;
        }
        return f;
    }

    //MaxCave
    protected int func_230357_a_() {
        return 20;
    }

    //Tunnel Height
    @Override
    protected double func_230360_b_() {
        return 0.7D;
    }

}