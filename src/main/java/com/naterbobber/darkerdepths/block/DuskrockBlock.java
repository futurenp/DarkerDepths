package com.naterbobber.darkerdepths.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DeadBushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.util.TriState;

public class DuskrockBlock extends Block {

    public DuskrockBlock(Properties properties) {
        super(properties);
    }


    @Override
    public TriState canSustainPlant(BlockState state, BlockGetter level, BlockPos soilPosition, Direction facing, BlockState plant) {
        Block plantBlock = plant.getBlock();
        if (plantBlock instanceof DeadBushBlock || plantBlock instanceof SproutsBlock) {
            return TriState.TRUE;
        }
        return super.canSustainPlant(state, level, soilPosition, facing, plant);
    }

}
