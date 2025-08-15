package com.naterbobber.darkerdepths.blocks;

import com.naterbobber.darkerdepths.init.DDBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DeadBushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class DrySproutsBlock extends DeadBushBlock {
    protected static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 8.0D, 14.0D);

    public DrySproutsBlock(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter world, BlockPos pos) {
        return state.is(DDBlocks.ARIDROCK.get()) ||
                state.is(DDBlocks.DUSKROCK.get()) ||
                state.is(DDBlocks.ARID_DEEPSLATE.get()) ||
                state.is(Blocks.MUD) ||
                state.is(Blocks.PACKED_MUD) ||
                super.mayPlaceOn(state, world, pos);
    }

}
