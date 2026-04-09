package com.naterbobber.darkerdepths.init;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.worldgen.structures.processors.catacombs.CatacombsArchaeologyProcessor;
import com.naterbobber.darkerdepths.worldgen.structures.processors.catacombs.CatacombsBarrelProcessor;
import com.naterbobber.darkerdepths.worldgen.structures.processors.catacombs.TombProcessor;
import com.naterbobber.darkerdepths.worldgen.structures.processors.catacombs.VoidSoulKnightProcessor;
import com.naterbobber.darkerdepths.worldgen.structures.processors.rope_mines.RopeMinesArchaeologyProcessor;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = DarkerDepths.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DDStructureProcessorTypes {

    public static final DeferredRegister<StructureProcessorType<?>> STRUCTURE_PROCESSORS = DeferredRegister.create(Registries.STRUCTURE_PROCESSOR, DarkerDepths.MOD_ID);

    public static final RegistryObject<StructureProcessorType<TombProcessor>> TOMB = STRUCTURE_PROCESSORS.register("tomb", () -> () -> TombProcessor.CODEC);
    public static final RegistryObject<StructureProcessorType<CatacombsBarrelProcessor>> CATACOMBS_BARREL = STRUCTURE_PROCESSORS.register("catacombs_barrel", () -> () -> CatacombsBarrelProcessor.CODEC);
    public static final RegistryObject<StructureProcessorType<CatacombsArchaeologyProcessor>> CATACOMBS_ARCHAEOLOGY = STRUCTURE_PROCESSORS.register("catacombs_archaeology", () -> () -> CatacombsArchaeologyProcessor.CODEC);
    public static final RegistryObject<StructureProcessorType<VoidSoulKnightProcessor>> VOID_SOUL_KNIGHT = STRUCTURE_PROCESSORS.register("void_soul_knight", () -> () -> VoidSoulKnightProcessor.CODEC);
    public static final RegistryObject<StructureProcessorType<RopeMinesArchaeologyProcessor>> ROPE_MINES_ARCHAEOLOGY = STRUCTURE_PROCESSORS.register("rope_mines_archaeology", () -> () -> RopeMinesArchaeologyProcessor.CODEC);

}
