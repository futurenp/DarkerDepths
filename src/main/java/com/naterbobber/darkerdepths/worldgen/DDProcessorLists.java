package com.naterbobber.darkerdepths.worldgen;

import com.google.common.collect.ImmutableList;
import com.naterbobber.darkerdepths.util.DDResourceKeys;
import com.naterbobber.darkerdepths.worldgen.processors.catacombs.CatacombsArchaeologyProcessor;
import com.naterbobber.darkerdepths.worldgen.processors.catacombs.CatacombsBarrelProcessor;
import com.naterbobber.darkerdepths.worldgen.processors.catacombs.TombProcessor;
import com.naterbobber.darkerdepths.worldgen.processors.catacombs.VoidSoulKnightProcessor;
import com.naterbobber.darkerdepths.worldgen.processors.rope_mines.RopeMinesArchaeologyProcessor;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;

public class DDProcessorLists {
    public static void bootstrap(BootstrapContext<StructureProcessorList> context) {
        context.register(DDResourceKeys.StructureProcessorLists.CATACOMBS_PROCESSOR, new StructureProcessorList(ImmutableList.of(
                new TombProcessor(),
                new CatacombsBarrelProcessor(),
                new CatacombsArchaeologyProcessor(),
                new VoidSoulKnightProcessor()
        )));

        context.register(DDResourceKeys.StructureProcessorLists.ROPE_MINES_PROCESSOR, new StructureProcessorList(ImmutableList.of(
                new RopeMinesArchaeologyProcessor()
        )));
    }
}



