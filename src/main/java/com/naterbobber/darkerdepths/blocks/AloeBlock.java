package com.naterbobber.darkerdepths.blocks;

import com.naterbobber.darkerdepths.init.DDBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Random;

public class AloeBlock extends BushBlock implements BonemealableBlock {
    public static final IntegerProperty STAGE = BlockStateProperties.STAGE;
    protected static final VoxelShape SHAPE = Block.box(2, 0, 2, 14, 12, 14);
    protected static final VoxelShape SMALL_SHAPE = Block.box(4, 0, 4, 12, 10, 12);

    public AloeBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(STAGE, 0));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        if (state.getValue(STAGE) == 0) {
            return SMALL_SHAPE;
        } else {
            return SHAPE;
        }
    }

    @Override
    public void randomTick(BlockState state, ServerLevel worldIn, BlockPos pos, Random random) {
        if (random.nextInt(6) == 0 && worldIn.getBlockState(pos.below()) == DDBlocks.LUSH_ARIDROCK.get().defaultBlockState()) {
            worldIn.setBlock(pos, state.setValue(STAGE, 1), 2);
        }
    }

    @Override
    public boolean isValidBonemealTarget(BlockGetter worldIn, BlockPos pos, BlockState state, boolean isClient) {
        return worldIn.getBlockState(pos.below()) == DDBlocks.LUSH_ARIDROCK.get().defaultBlockState();
    }

    @Override
    public boolean isBonemealSuccess(Level worldIn, Random random, BlockPos pos, BlockState state) {
        return state.getValue(STAGE) == 0;
    }

    @Override
    public void performBonemeal(ServerLevel worldIn, Random p_50894_, BlockPos pos, BlockState state) {
        if (worldIn.getBlockState(pos.below()) == DDBlocks.LUSH_ARIDROCK.get().defaultBlockState()) {
            worldIn.setBlock(pos, state.setValue(STAGE, 1), 2);
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(STAGE);
    }
}
