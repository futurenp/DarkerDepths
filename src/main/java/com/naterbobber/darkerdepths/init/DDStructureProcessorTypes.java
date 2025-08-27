package com.naterbobber.darkerdepths.init;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.world.gen.processors.catacombs.CatacombsArchaeologyProcessor;
import com.naterbobber.darkerdepths.world.gen.processors.catacombs.CatacombsBarrelProcessor;
import com.naterbobber.darkerdepths.world.gen.processors.catacombs.TombProcessor;
import com.naterbobber.darkerdepths.world.gen.processors.catacombs.VoidSoulKnightProcessor;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = DarkerDepths.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DDStructureProcessorTypes {

    public static final DeferredRegister<StructureProcessorType<?>> STRUCTURE_PROCESSORS = DeferredRegister.create(Registries.STRUCTURE_PROCESSOR, DarkerDepths.MODID);

    public static final RegistryObject<StructureProcessorType<TombProcessor>> TOMB = STRUCTURE_PROCESSORS.register("tomb", () -> () -> TombProcessor.CODEC);
    public static final RegistryObject<StructureProcessorType<CatacombsBarrelProcessor>> CATACOMBS_BARREL = STRUCTURE_PROCESSORS.register("catacombs_barrel", () -> () -> CatacombsBarrelProcessor.CODEC);
    public static final RegistryObject<StructureProcessorType<CatacombsArchaeologyProcessor>> CATACOMBS_ARCHAEOLOGY = STRUCTURE_PROCESSORS.register("catacombs_archaeology", () -> () -> CatacombsArchaeologyProcessor.CODEC);
    public static final RegistryObject<StructureProcessorType<VoidSoulKnightProcessor>> VOID_SOUL_KNIGHT = STRUCTURE_PROCESSORS.register("void_soul_knight", () -> () -> VoidSoulKnightProcessor.CODEC);
}
