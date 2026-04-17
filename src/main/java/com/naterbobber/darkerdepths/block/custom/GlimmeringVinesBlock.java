package com.naterbobber.darkerdepths.block.custom;

import com.mojang.serialization.MapCodec;
import com.naterbobber.darkerdepths.init.DDBlocks;
import com.naterbobber.darkerdepths.init.DDParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.shapes.VoxelShape;

public class GlimmeringVinesBlock extends GrowingPlantHeadBlock {
    protected static final VoxelShape SHAPE = Block.box(4.0D, 9.0D, 4.0D, 12.0D, 16.0D, 12.0D);

    public GlimmeringVinesBlock(Properties properties) {
        super(properties, Direction.DOWN, SHAPE, false, 0.1D);
        this.registerDefaultState((this.stateDefinition.any()).setValue(AGE, 20));
    }

    @Override
    public boolean isLadder(BlockState state, LevelReader world, BlockPos pos, LivingEntity entity) {
        return true;
    }

    @Override
    protected MapCodec<? extends GrowingPlantHeadBlock> codec() {
        return null;
    }

    @Override
    protected int getBlocksToGrowWhenBonemealed(RandomSource rand) {
        return rand.nextInt(1, 4);
    }

    @Override
    protected boolean canGrowInto(BlockState state) {
        return state.isAir();
    }

    @Override
    protected Block getBodyBlock() {
        return DDBlocks.GLIMMERING_VINE_PLANT.get();
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if(random.nextFloat() > 0.15F) {
            return;
        }
        ParticleUtils.spawnParticleInBlock(level, pos, 1, DDParticleTypes.GLOW_GLIMMER.get());
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        super.randomTick(state, level, pos, random);
    }

    @Override
    protected BlockState getGrowIntoState(BlockState state, RandomSource random) {
        return state.setValue(AGE, state.getValue(AGE) + 1);
    }

    public BlockState getStateForPlacement(LevelAccessor level) {
        return this.defaultBlockState().setValue(AGE, level.getRandom().nextInt(7) + 18);
    }
}
