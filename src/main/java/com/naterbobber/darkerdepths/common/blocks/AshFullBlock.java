package com.naterbobber.darkerdepths.common.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.FallingBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.particles.BlockParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

import com.naterbobber.darkerdepths.core.registries.DDBlocks;

import javax.annotation.Nullable;

//<>

public class AshFullBlock extends FallingBlock {

    public AshFullBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {}

	@Override
    public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
        /*
        BlockPos.Mutable mutable = pos.toMutable();

        for (int i = 0; i < 10; i++) {
            mutable.move(Direction.DOWN);
            BlockState blockState = worldIn.getBlockState(mutable);
            if (!blockState.isAir()) {
                worldIn.setBlockState(mutable, DDBlocks.ASH.get().getDefaultState());
            }
        }*/
    /*
    	BlockPos fillerpos = pos.down();
    	BlockPos basepos = pos.down(2);
    	BlockState fillerstate = worldIn.getBlockState(fillerpos);
    	BlockState basestate = worldIn.getBlockState(basepos);
    	boolean isBaseSolid = basestate.getMaterial().isSolid();
    	if (isBaseSolid) {
    		for (int i = 0; i < 4; i++) {
    			if (fillerstate.isIn(DDBlocks.ASH.get()) && DDBlocks.ASH.get().getDefaultState().isValidPosition(worldIn, pos.down())) {
    				int layer = fillerstate.get(AshBlock.LAYERS);
    				worldIn.setBlockState(pos.down(), DDBlocks.ASH.get().getDefaultState().with(AshBlock.LAYERS, Integer.valueOf(Math.min(8, layer + 1))));
    			} else if (fillerstate.isAir()) {
    				worldIn.setBlockState(pos.down(), DDBlocks.ASH.get().getDefaultState());
    			}
    		}
    	}*/
    }

    @Nullable
    private static BlockPos findValidPositionForAshBelowFullBlock(World worldIn, BlockPos pos) {
        BlockPos.Mutable mutable = pos.toMutable();

        for (int i = 0; i < 5; i++) {
            mutable.move(Direction.DOWN);
            BlockState validState = worldIn.getBlockState(mutable.up());
            BlockState baseState = worldIn.getBlockState(mutable);
            if (validState.isAir() && baseState.getMaterial().isSolid()) {
                if (baseState.isIn(DDBlocks.ASH.get())) {
                    int layer = baseState.get(AshBlock.LAYERS);
                    worldIn.setBlockState(pos, DDBlocks.ASH.get().getDefaultState().getBlockState());
                }
                return mutable;
            }
        }
        return null;
    }

    @Override
    public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
        if (!(entityIn instanceof LivingEntity) || entityIn.world.getBlockState(entityIn.getOnPosition()).getBlock().equals(DDBlocks.ASH_BLOCK.get())) {
            if (!entityIn.isSpectator() && (entityIn.prevPosX != entityIn.getPosX() || entityIn.prevPosZ != entityIn.getPosZ()) && worldIn.rand.nextBoolean()) {
                spawnAshParticles(worldIn, new Vector3d(entityIn.getPosX(), pos.getY(), entityIn.getPosZ()));
            }
        }
    }

    private void spawnAshParticles(World worldIn, Vector3d vector3d) {
        if (worldIn.isRemote) {
            Random rand = worldIn.getRandom();
            double getY = vector3d.y + 1.0d;

            for (int i = 0; i < rand.nextInt(3); ++i) {
                worldIn.addParticle(ParticleTypes.CLOUD, vector3d.x, getY, vector3d.z, ((-1.0f + rand.nextFloat() * 2.0f) / 12.0f), 0.05000000074505806d, ((-1.0f + rand.nextFloat() * 2.0f) / 12.0f));
            }
        }
    }

    @Override
    public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        if (rand.nextInt(16) == 0) {
            BlockPos blockPos = pos.down();
            boolean isSolidAsh = DDBlocks.ASH.get().getDefaultState().get(AshBlock.LAYERS) == 8;
            if (worldIn.isAirBlock(blockPos) || (worldIn.getBlockState(blockPos).isIn(DDBlocks.ASH.get()) && !isSolidAsh)) {
                double x = (double)pos.getX() + rand.nextDouble();
                double y = (double)pos.getY() - 0.05d;
                double z = (double)pos.getZ() + rand.nextDouble();
                worldIn.addParticle(new BlockParticleData(ParticleTypes.FALLING_DUST, stateIn), x, y, z,0.0d, 0.0d, 0.0d);
            }
        }
    }

    @Override
    public int getDustColor(BlockState state, IBlockReader reader, BlockPos pos) {
        return 5132116;
    }
}