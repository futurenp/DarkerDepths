package com.naterbobber.darkerdepths.init;

import com.mojang.serialization.MapCodec;
import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.worldgen.structures.processors.catacombs.CatacombsArchaeologyProcessor;
import com.naterbobber.darkerdepths.worldgen.structures.processors.catacombs.CatacombsBarrelProcessor;
import com.naterbobber.darkerdepths.worldgen.structures.processors.catacombs.TombProcessor;
import com.naterbobber.darkerdepths.worldgen.structures.processors.catacombs.VoidSoulKnightProcessor;
import com.naterbobber.darkerdepths.worldgen.structures.processors.rope_mines.RopeMinesArchaeologyProcessor;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

public class DDStructureProcessorTypes {

    public static final DeferredRegister<StructureProcessorType<?>> STRUCTURE_PROCESSORS = DeferredRegister.create(Registries.STRUCTURE_PROCESSOR, DarkerDepths.MOD_ID);

    public static final DeferredHolder<StructureProcessorType<?>, StructureProcessorType<TombProcessor>> TOMB = registerProcessor("tomb", TombProcessor.CODEC);
    public static final DeferredHolder<StructureProcessorType<?>, StructureProcessorType<CatacombsBarrelProcessor>> CATACOMBS_BARREL = registerProcessor("catacombs_barrel", CatacombsBarrelProcessor.CODEC);
    public static final DeferredHolder<StructureProcessorType<?>, StructureProcessorType<CatacombsArchaeologyProcessor>> CATACOMBS_ARCHAEOLOGY = registerProcessor("catacombs_archaeology", CatacombsArchaeologyProcessor.CODEC);
    public static final DeferredHolder<StructureProcessorType<?>, StructureProcessorType<VoidSoulKnightProcessor>> VOID_SOUL_KNIGHT = registerProcessor("void_soul_knight", VoidSoulKnightProcessor.CODEC);
    public static final DeferredHolder<StructureProcessorType<?>, StructureProcessorType<RopeMinesArchaeologyProcessor>> ROPE_MINES_ARCHAEOLOGY = registerProcessor("rope_mines_archaeology", RopeMinesArchaeologyProcessor.CODEC);

    public static <P extends StructureProcessor> DeferredHolder<StructureProcessorType<?>, StructureProcessorType<P>> registerProcessor(String name, MapCodec<P> codec) {
        return STRUCTURE_PROCESSORS.register(name, () -> () -> codec);
    }
}
