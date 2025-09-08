package com.naterbobber.darkerdepths.blocks;

import com.naterbobber.darkerdepths.init.DDBlockStateProperties;
import com.naterbobber.darkerdepths.init.DDBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;

public class DeadLivingCrystalBlock extends Block {
    private static final IntegerProperty CRYSTAL_GROWTH_LEVEL = DDBlockStateProperties.CRYSTAL_GROWTH_LEVEL;

    public DeadLivingCrystalBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(CRYSTAL_GROWTH_LEVEL, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(CRYSTAL_GROWTH_LEVEL);
    }

        @Override
    public ItemInteractionResult useItemOn(ItemStack stack, BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (stack.is(Items.DIAMOND) && blockState.getValue(CRYSTAL_GROWTH_LEVEL) == 0) {
            if (!player.getAbilities().instabuild) {
                stack.shrink(1);
            }
            level.setBlock(blockPos, blockState.setValue(CRYSTAL_GROWTH_LEVEL, 1), 2);
            level.playSound(null, blockPos, SoundEvents.TURTLE_EGG_CRACK, SoundSource.BLOCKS, 1.0F, 1.0F);
            level.scheduleTick(blockPos, this, 1);
            return ItemInteractionResult.SUCCESS;
        }
        return super.useItemOn(stack, blockState, level, blockPos, player, hand, hitResult);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource randomSource) {
        int cracked = state.getValue(CRYSTAL_GROWTH_LEVEL);
        if (cracked < 3 && cracked != 0) {
            level.setBlock(pos, state.setValue(CRYSTAL_GROWTH_LEVEL, cracked + 1), 2);
            level.playSound(null, pos, SoundEvents.TURTLE_EGG_CRACK, SoundSource.BLOCKS, 1.0F, 1.0F);
            level.scheduleTick(pos, this, 20);
        } else if (cracked == 3) {
            level.setBlockAndUpdate(pos, DDBlocks.LIVING_CRYSTAL.get().defaultBlockState());
            level.playSound(null, pos, SoundEvents.DEEPSLATE_PLACE, SoundSource.BLOCKS, 1.0F, 1.0F);
        }
    }
}
