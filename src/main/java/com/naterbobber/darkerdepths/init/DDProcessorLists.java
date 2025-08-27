package com.naterbobber.darkerdepths.init;

import com.google.common.collect.ImmutableList;
import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.world.gen.processors.catacombs.CatacombsArchaeologyProcessor;
import com.naterbobber.darkerdepths.world.gen.processors.catacombs.CatacombsBarrelProcessor;
import com.naterbobber.darkerdepths.world.gen.processors.catacombs.TombProcessor;
import com.naterbobber.darkerdepths.world.gen.processors.catacombs.VoidSoulKnightProcessor;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;

public class DDProcessorLists {

    public static final ResourceKey<StructureProcessorList> CATACOMBS_PROCESSOR = createKey("catacombs_processor");

    public static void bootstrap(BootstapContext<StructureProcessorList> context) {
        context.register(CATACOMBS_PROCESSOR, new StructureProcessorList(ImmutableList.of(
                new TombProcessor(),
                new CatacombsBarrelProcessor(),
                new CatacombsArchaeologyProcessor(),
                new VoidSoulKnightProcessor()
        )));
    }

    private static ResourceKey<StructureProcessorList> createKey(String pName) {
        return ResourceKey.create(Registries.PROCESSOR_LIST, DarkerDepths.id(pName));
    }

}



