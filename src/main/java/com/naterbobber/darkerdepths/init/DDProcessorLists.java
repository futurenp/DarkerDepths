package com.naterbobber.darkerdepths.init;

import com.google.common.collect.ImmutableList;
import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.world.gen.processors.TombProcessor;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;

public class DDProcessorLists {

    public static final ResourceKey<StructureProcessorList> TOMBS = createKey("tombs");

    public static void bootstrap(BootstapContext<StructureProcessorList> context) {
        context.register(TOMBS, new StructureProcessorList(ImmutableList.of(new TombProcessor())));
    }

    private static ResourceKey<StructureProcessorList> createKey(String pName) {
        return ResourceKey.create(Registries.PROCESSOR_LIST, DarkerDepths.id(pName));
    }

}
