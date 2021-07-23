package com.naterbobber.darkerdepths.common.blocks;

import com.naterbobber.darkerdepths.core.registries.DDBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BushBlock;
import net.minecraft.block.IGrowable;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public class AloeBlock extends BushBlock implements IGrowable {
    public static final IntegerProperty STAGE = BlockStateProperties.STAGE_0_1;
    protected static final VoxelShape SHAPE = Block.makeCuboidShape(2, 0, 2, 14, 12, 14);
    protected static final VoxelShape SMALL_SHAPE = Block.makeCuboidShape(4, 0, 4, 12, 10, 12);

    public AloeBlock(Properties properties) {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(STAGE, 0));
    }

    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        if (state.get(STAGE) == 0) {
            return SMALL_SHAPE;
        } else {
            return SHAPE;
        }
    }

    @Override
    public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
        if (random.nextInt(6) == 0 && worldIn.getBlockState(pos.down()) == DDBlocks.LUSH_ARIDROCK.get().getDefaultState()) {
            worldIn.setBlockState(pos, state.with(STAGE, 1), 2);
        }
    }

    @Override
    public boolean canGrow(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient) {
        return worldIn.getBlockState(pos.down()) == DDBlocks.LUSH_ARIDROCK.get().getDefaultState();
    }

    @Override
    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, BlockState state) {
        int i = state.get(STAGE);
        return i == 0;
    }

    @Override
    public void grow(ServerWorld worldIn, Random rand, BlockPos pos, BlockState state) {
        if (worldIn.getBlockState(pos.down()) == DDBlocks.LUSH_ARIDROCK.get().getDefaultState()) {
            worldIn.setBlockState(pos, state.with(STAGE, 1), 2);
        }
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        super.fillStateContainer(builder);
        builder.add(STAGE);
    }
}
