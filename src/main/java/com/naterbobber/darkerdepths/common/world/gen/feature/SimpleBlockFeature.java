package com.naterbobber.darkerdepths.common.world.gen.feature;

import com.mojang.serialization.Codec;
import com.naterbobber.darkerdepths.common.blocks.HangingDoublePlantBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.DoublePlantBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.structure.StructureManager;

import java.util.Random;

//<>

public class SimpleBlockFeature extends Feature<SimpleBlockConfig> {
    public SimpleBlockFeature(Codec<SimpleBlockConfig> config) {
        super(config);
    }

    @Override
    public boolean func_230362_a_(ISeedReader worldIn, StructureManager structureManager, ChunkGenerator generator, Random rand, BlockPos pos, SimpleBlockConfig configIn) {
        if ((configIn.placeOn.isEmpty() || configIn.placeOn.contains(worldIn.getBlockState(pos.down()))) && (configIn.placeIn.isEmpty() || configIn.placeIn.contains(worldIn.getBlockState(pos))) && (configIn.placeUnder.isEmpty() || configIn.placeUnder.contains(worldIn.getBlockState(pos.up())))) {
            BlockState state = configIn.toPlace.getBlockState(rand, pos);
            if (state.isValidPosition(worldIn, pos)) {
                if (state.getBlock() instanceof DoublePlantBlock) {
                    if (!worldIn.isAirBlock(pos.up())) {
                        return false;
                    }

                    DoublePlantBlock doublePlantBlock = (DoublePlantBlock)state.getBlock();
                    doublePlantBlock.placeAt(worldIn, pos, 2);
                } else if (state.getBlock() instanceof HangingDoublePlantBlock) {
                    if (!worldIn.isAirBlock(pos.down())) {
                        return false;
                    }

                    HangingDoublePlantBlock hangingDoublePlantBlock = (HangingDoublePlantBlock)state.getBlock();
                    hangingDoublePlantBlock.placeAt(worldIn, pos, 2);
                } else {
                    worldIn.setBlockState(pos, state, 2);
                }

                return true;
            }
        }

        return false;
    }
}