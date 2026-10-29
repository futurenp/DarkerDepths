package com.naterbobber.darkerdepths.common.block.unique;

import com.naterbobber.darkerdepths.common.init.DDBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;
import org.jetbrains.annotations.Nullable;

public class DryDirtBlock extends Block implements BonemealableBlock {
    public DryDirtBlock(Properties properties) {
        super(properties);
    }

    @Override
    public @Nullable BlockState getToolModifiedState(BlockState state, UseOnContext context, ItemAbility ability, boolean simulate) {
        if (ability == ItemAbilities.HOE_TILL && HoeItem.onlyIfAirAbove(context)) {
            return Blocks.DIRT.defaultBlockState();
        }

        return super.getToolModifiedState(state, context, ability, simulate);
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
        var above = pos.above();
        var sprouts = DDBlocks.DRY_SPROUTS.get().defaultBlockState();
        return level.isEmptyBlock(above) && sprouts.canSurvive(level, above);
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public BlockPos getParticlePos(BlockPos pos) {
        return pos.above();
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        var above = pos.above();
        var sprouts = DDBlocks.DRY_SPROUTS.get().defaultBlockState();
        if (level.isEmptyBlock(above) && sprouts.canSurvive(level, above)) {
            level.setBlock(above, sprouts, Block.UPDATE_ALL);
        }
        for (var nearby : BlockPos.betweenClosed(pos.offset(-1, -1, -1), pos.offset(1, 1, 1))) {
            if (nearby.equals(pos) || !level.getBlockState(nearby).is(this) || random.nextInt(4) != 0) {
                continue;
            }

            var target = nearby.above();
            if (level.isEmptyBlock(target) && sprouts.canSurvive(level, target)) {
                if (level.setBlock(target, sprouts, Block.UPDATE_ALL)) {
                    level.levelEvent(1505, nearby, 2);
                }
            }
        }
    }
}
