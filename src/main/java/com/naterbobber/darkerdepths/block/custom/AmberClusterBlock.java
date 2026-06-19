package com.naterbobber.darkerdepths.block.custom;

import net.minecraft.world.level.block.AmethystClusterBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathComputationType;

public class AmberClusterBlock extends AmethystClusterBlock {
    public AmberClusterBlock(float height, float aabbOffset, Properties properties) {
        super(height, aabbOffset, properties);
    }

    @Override
    protected boolean isPathfindable(BlockState state, PathComputationType pathComputationType) {
        return false;
    }
}
