package com.naterbobber.darkerdepths.common.blocks;

import com.naterbobber.darkerdepths.core.registries.DDEntityTypes;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public class SpurBlock extends Block {
    private static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    private static final VoxelShape SHAPE = Block.makeCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 4.0D, 14.0D);

    public SpurBlock(AbstractBlock.Properties properties) {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(POWERED, false));
    }

    @Override
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        return facing == Direction.DOWN && !this.isValidPosition(stateIn, worldIn, currentPos) ? Blocks.AIR.getDefaultState() : super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        super.fillStateContainer(builder);
        builder.add(POWERED);
    }

    @Override
    public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
        if (entityIn instanceof LivingEntity && entityIn.getType() != DDEntityTypes.GLOWSHROOM_MONSTER.get()) {
            entityIn.setMotionMultiplier(state, new Vector3d(0.8F, 0.75D, 0.8F));
            if (!worldIn.isRemote && !state.get(POWERED)) {
                this.updateState(pos, state, worldIn);
            }
        }
    }

    private void updateState(BlockPos pos, BlockState state, World worldIn) {
        worldIn.setBlockState(pos, state.with(POWERED, true), 2);
        worldIn.getPendingBlockTicks().scheduleTick(pos, this, 40);
    }

    @Override
    public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
        boolean bl = state.get(POWERED);
        if (bl) {
            worldIn.setBlockState(new BlockPos(pos), state.with(POWERED, false), 2);
            worldIn.playSound(null, pos, SoundEvents.BLOCK_SLIME_BLOCK_PLACE, SoundCategory.BLOCKS, 0.3F, 1.2F);
        }
    }

    @Override
    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
        return hasEnoughSolidSide(worldIn, pos.down(), Direction.UP);
    }
}
