package com.naterbobber.darkerdepths.blocks;

import com.naterbobber.darkerdepths.init.DDBlocks;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;
import org.jetbrains.annotations.Nullable;

public class DDLogBlock extends RotatedPillarBlock {
    private final Block strippedBlock;
    public DDLogBlock(Properties properties, Block strippedBlock) {
        super(properties);
        this.strippedBlock = strippedBlock;
    }

    @Override
    public @Nullable BlockState getToolModifiedState(BlockState state, UseOnContext context, ItemAbility itemAbility, boolean simulate) {
        if(itemAbility == ItemAbilities.AXE_STRIP) {
            return strippedBlock.defaultBlockState().setValue(RotatedPillarBlock.AXIS, state.getValue(RotatedPillarBlock.AXIS));
        }
        return super.getToolModifiedState(state, context, itemAbility, simulate);
    }
}
