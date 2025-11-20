package com.naterbobber.darkerdepths.block;

import com.naterbobber.darkerdepths.init.DDBlocks;
import com.naterbobber.darkerdepths.init.DDParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AmethystClusterBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;

public class PorousBlock extends RotatedPillarBlock {

    public PorousBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel worldIn, BlockPos pos, RandomSource random) {
        if (random.nextInt(17) == 0) {
            Direction direction = Direction.getRandom(random);
            BlockPos blockPos = pos.relative(direction);
            BlockState blockState = worldIn.getBlockState(blockPos);
            if (canGrowIn(blockState) && direction != Direction.UP) {
                worldIn.setBlockAndUpdate(blockPos, DDBlocks.AMBER_CLUSTER.get().defaultBlockState().setValue(AmethystClusterBlock.FACING, direction).setValue(AmethystClusterBlock.WATERLOGGED, blockState.getFluidState().getType() == Fluids.WATER));
            }
        }
    }

    public static boolean canGrowIn(BlockState state) {
        return state.isAir() || state.is(Blocks.WATER) && state.getFluidState().getAmount() == 8;
    }

    @Override
    public void animateTick(BlockState stateIn, Level worldIn, BlockPos pos, RandomSource rand) {
        if (rand.nextInt(10) == 0) {
            Direction direction = Direction.getRandom(rand);
            if (direction != Direction.UP) {
                BlockPos blockPos = pos.relative(direction);
                BlockState blockState = worldIn.getBlockState(blockPos);
                if (!stateIn.canOcclude() || !blockState.isFaceSturdy(worldIn, blockPos, direction.getOpposite())) {
                    double x = direction.getStepX() == 0 ? rand.nextDouble() : 0.5D + direction.getStepX() * 0.6D;
                    double y = direction.getStepY() == 0 ? rand.nextDouble() : 0.5D + direction.getStepY() * 0.6D;
                    double z = direction.getStepZ() == 0 ? rand.nextDouble() : 0.5D + direction.getStepZ() * 0.6D;
                    worldIn.addParticle(DDParticleTypes.DRIPPING_AMBER.get(), pos.getX() + x, pos.getY() + y, pos.getZ() + z, 0.0D, 0.0D, 0.0D);
                }
            }
        }
    }

}