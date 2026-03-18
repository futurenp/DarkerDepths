package com.naterbobber.darkerdepths.compat;

import com.naterbobber.darkerdepths.block.generic.ConnectedPillarBlock;
import com.naterbobber.darkerdepths.block.generic.ConnectedRotatablePillarBlock;
import com.naterbobber.darkerdepths.init.DDBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class CompatBlocks {

    //NML
    public static Block createTrimmedPlanks() {
        return new ConnectedPillarBlock(BlockBehaviour.Properties.ofFullCopy(DDBlocks.PETRIFIED_PLANKS.get()));
    }
}
