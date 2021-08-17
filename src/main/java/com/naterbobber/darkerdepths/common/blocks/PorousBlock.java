package com.naterbobber.darkerdepths.common.blocks;

import com.naterbobber.darkerdepths.core.registries.DDBlocks;
import com.naterbobber.darkerdepths.core.registries.DDParticleTypes;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.block.material.PushReaction;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

//<>

public class PorousBlock extends RotatedPillarBlock {
    public PorousBlock(Properties properties) {
        super(properties);
    }

    @Override
    public PushReaction getPushReaction(BlockState state) {
        return PushReaction.DESTROY;
    }

    @Override
    public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
        if (random.nextInt(17) == 0) {
            Direction direction = Direction.getRandomDirection(random);
            BlockPos blockPos = pos.offset(direction);
            BlockState blockState = worldIn.getBlockState(blockPos);
            if (canGrowIn(blockState) && direction != Direction.UP) {
                worldIn.setBlockState(blockPos, DDBlocks.AMBER.get().getDefaultState().with(AbstractGemStoneBlock.FACING, direction).with(AbstractGemStoneBlock.WATERLOGGED, blockState.getFluidState().getFluid() == Fluids.WATER));
            }
        }
    }

    public static boolean canGrowIn(BlockState state) {
        return state.isAir() || state.isIn(Blocks.WATER) && state.getFluidState().getLevel() == 8;
    }

    @Override
    public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        if (rand.nextInt(10) == 0) {
            Direction direction = Direction.getRandomDirection(rand);
            if (direction != Direction.UP) {
                BlockPos blockPos = pos.offset(direction);
                BlockState blockState = worldIn.getBlockState(blockPos);
                if (!stateIn.isSolid() || !blockState.isSolidSide(worldIn, blockPos, direction.getOpposite())) {
                    double x = direction.getXOffset() == 0 ? rand.nextDouble() : 0.5D + direction.getXOffset() * 0.6D;
                    double y = direction.getYOffset() == 0 ? rand.nextDouble() : 0.5D + direction.getYOffset() * 0.6D;
                    double z = direction.getZOffset() == 0 ? rand.nextDouble() : 0.5D + direction.getZOffset() * 0.6D;
                    worldIn.addParticle(DDParticleTypes.DRIPPING_RESIN.get(), pos.getX() + x, pos.getY() + y, pos.getZ() + z, 0.0D, 0.0D, 0.0D);
                }
            }
        }
    }
}