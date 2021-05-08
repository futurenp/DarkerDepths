package com.naterbobber.darkerdepths.common.world.gen.feature;

import com.mojang.serialization.Codec;
import com.naterbobber.darkerdepths.common.blocks.AbstractGemStoneBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.structure.StructureManager;

import java.util.Random;

//<>

public class GemstonePlacementFeature extends Feature<GemstonePlacementConfig> {
    private static final Direction[] DIRECTIONS = Direction.values();

    public GemstonePlacementFeature(Codec<GemstonePlacementConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(ISeedReader worldIn, ChunkGenerator generator, Random rand, BlockPos pos, GemstonePlacementConfig configIn) {
        BlockState state = worldIn.getBlockState(pos);
        if (!isEmptyOrWater(worldIn, pos)) {
            return false;
        } else {
            for (Direction direction : DIRECTIONS) {
                if (AbstractGemStoneBlock.canAttachTo(worldIn, pos.offset(direction), direction)) {
                    if (state.matchesBlock(Blocks.WATER)) {
                        worldIn.setBlockState(pos, configIn.state.with(AbstractGemStoneBlock.FACING, direction.getOpposite()).with(BlockStateProperties.WATERLOGGED, true), 2);
                    } else {
                        worldIn.setBlockState(pos, configIn.state.with(AbstractGemStoneBlock.FACING, direction.getOpposite()), 2);
                    }
                    return true;
                }
            }
        }

        return false;
    }

    private boolean isEmptyOrWater(ISeedReader worldIn, BlockPos pos) {
        return worldIn.hasBlockState(pos, this::isEmptyOrWater);
    }

    private boolean isEmptyOrWater(BlockState state) {
        return state.isAir() || state.matchesBlock(Blocks.WATER);
    }
}