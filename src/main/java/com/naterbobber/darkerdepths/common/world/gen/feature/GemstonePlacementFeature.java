package com.naterbobber.darkerdepths.common.world.gen.feature;

import com.mojang.serialization.Codec;
import com.naterbobber.darkerdepths.common.blocks.AbstractGemStoneBlock;
import com.naterbobber.darkerdepths.core.registries.DDBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.StructureManager;

import java.util.Random;

//<>

public class GemstonePlacementFeature extends Feature<NoFeatureConfig> {
    private static final Direction[] DIRECTIONS = Direction.values();

    public GemstonePlacementFeature(Codec<NoFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean func_230362_a_(ISeedReader worldIn, StructureManager structure, ChunkGenerator generator, Random rand, BlockPos pos, NoFeatureConfig config) {
        BlockState state = worldIn.getBlockState(pos);
        if (!isEmptyOrWater(worldIn, pos)) {
            return false;
        } else {
            for (Direction direction : DIRECTIONS) {
                if (AbstractGemStoneBlock.canAttachTo(worldIn, pos.offset(direction), direction)) {
                    if (state.isIn(Blocks.WATER)) {
                        worldIn.setBlockState(pos, DDBlocks.AMBER.get().getDefaultState().with(AbstractGemStoneBlock.FACING, direction.getOpposite()).with(BlockStateProperties.WATERLOGGED, true), 2);
                    } else {
                        worldIn.setBlockState(pos, DDBlocks.AMBER.get().getDefaultState().with(AbstractGemStoneBlock.FACING, direction.getOpposite()), 2);
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
        return state.isAir() || state.isIn(Blocks.WATER);
    }
}