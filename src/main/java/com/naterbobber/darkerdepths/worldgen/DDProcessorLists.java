package com.naterbobber.darkerdepths.worldgen;

import com.google.common.collect.ImmutableList;
import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.worldgen.processors.catacombs.CatacombsArchaeologyProcessor;
import com.naterbobber.darkerdepths.worldgen.processors.catacombs.CatacombsBarrelProcessor;
import com.naterbobber.darkerdepths.worldgen.processors.catacombs.TombProcessor;
import com.naterbobber.darkerdepths.worldgen.processors.catacombs.VoidSoulKnightProcessor;
import com.naterbobber.darkerdepths.worldgen.processors.rope_mines.RopeMinesArchaeologyProcessor;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;

public class DDProcessorLists {

    public static final ResourceKey<StructureProcessorList> CATACOMBS_PROCESSOR = createKey("catacombs_processor");
    public static final ResourceKey<StructureProcessorList> ROPE_MINES_PROCESSOR = createKey("rope_mines_processor");

    public static void bootstrap(BootstapContext<StructureProcessorList> context) {
        context.register(CATACOMBS_PROCESSOR, new StructureProcessorList(ImmutableList.of(
                new TombProcessor(),
                new CatacombsBarrelProcessor(),
                new CatacombsArchaeologyProcessor(),
                new VoidSoulKnightProcessor()
        )));

        context.register(ROPE_MINES_PROCESSOR, new StructureProcessorList(ImmutableList.of(
                new RopeMinesArchaeologyProcessor()
        )));
    }

    private static ResourceKey<StructureProcessorList> createKey(String pName) {
        return ResourceKey.create(Registries.PROCESSOR_LIST, DarkerDepths.id(pName));
    }

}



