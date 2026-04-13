package com.naterbobber.darkerdepths.init;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.worldgen.structures.DDMineshaftStructure;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = DarkerDepths.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DDStructureTypes {
    public static final DeferredRegister<StructureType<?>> STRUCTURE_TYPES = DeferredRegister.create(Registries.STRUCTURE_TYPE, DarkerDepths.MOD_ID);

    public static final RegistryObject<StructureType<DDMineshaftStructure>> PETRIFIED_MINESHAFT = register("petrified_mineshaft", DDMineshaftStructure.CODEC);

    public static <P extends Structure> RegistryObject<StructureType<P>> register(String name, Codec<P> codec) {
        return STRUCTURE_TYPES.register(name, () -> () -> codec);
    }
}