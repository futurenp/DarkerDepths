package com.naterbobber.darkerdepths.block.custom;

import com.mojang.serialization.MapCodec;
import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.block.DDBlockStateProperties;
import com.naterbobber.darkerdepths.init.DDBlocks;
import com.naterbobber.darkerdepths.init.DDParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CherryLeavesBlock;
import net.minecraft.world.level.block.GrowingPlantBodyBlock;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.VoxelShape;

public class GlimmeringVinePlantBlock extends GrowingPlantBodyBlock {
    public static final VoxelShape SHAPE = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 16.0D, 15.0D);
    private static final BooleanProperty BASE = DDBlockStateProperties.BASE;

    public GlimmeringVinePlantBlock(Properties properties) {
        super(properties, Direction.DOWN, SHAPE, false);
        registerDefaultState(this.stateDefinition.any().setValue(BASE, false));
    }

    @Override
    public boolean isLadder(BlockState state, LevelReader world, BlockPos pos, LivingEntity entity) {
        return true;
    }

    @Override
    protected GrowingPlantHeadBlock getHeadBlock() {
        return DDBlocks.GLIMMERING_VINES.get();
    }

    @Override
    protected MapCodec<? extends GrowingPlantBodyBlock> codec() {
        return null;
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if(random.nextFloat() > 0.15F) {
            return;
        }
        ParticleUtils.spawnParticleInBlock(level, pos, 1, DDParticleTypes.GLOW_GLIMMER.get());
    }

    @Override
    protected BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos) {
        var aboveState = level.getBlockState(currentPos.above());
        var belowState = level.getBlockState(currentPos.below());
        var newState = state;
        if(!aboveState.is(DDBlocks.GLIMMERING_VINE_PLANT) && !belowState.is(DDBlocks.GLIMMERING_VINES)) {
            if(!state.getValue(BASE)) {
                newState = state.setValue(BASE, true);
            }
        } else {
            if(state.getValue(BASE)) {
                newState = state.setValue(BASE, false);
            }
        }

        return super.updateShape(newState, facing, facingState, level, currentPos, facingPos);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(BASE);
    }
}
