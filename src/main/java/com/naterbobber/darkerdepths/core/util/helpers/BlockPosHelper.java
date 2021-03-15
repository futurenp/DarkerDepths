package com.naterbobber.darkerdepths.core.util.helpers;

import net.minecraft.util.math.BlockPos;

//<>

public class BlockPosHelper {
    public static BlockPos atY(BlockPos pos, int yIn) {
        return new BlockPos.Mutable(pos.getX(), yIn, pos.getZ());
    }
}