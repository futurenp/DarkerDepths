package com.naterbobber.darkerdepths.common.blocks;

import net.minecraft.block.DeadBushBlock;

//<>

public class DetritusBlock extends DeadBushBlock {
    public DetritusBlock(Properties builder) {
        super(builder);
    }

    @Override
    public OffsetType getOffsetType() {
        return OffsetType.XZ;
    }
}