package com.naterbobber.darkerdepths.init;

import com.mojang.serialization.MapCodec;
import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.worldgen.structures.DDMineshaftStructure;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class DDStructureTypes {
    public static final DeferredRegister<StructureType<?>> STRUCTURE_TYPES = DeferredRegister.create(Registries.STRUCTURE_TYPE, DarkerDepths.MOD_ID);

    public static final DeferredHolder<StructureType<?>, StructureType<DDMineshaftStructure>> PETRIFIED_MINESHAFT = register("petrified_mineshaft", DDMineshaftStructure.CODEC);

    public static <P extends Structure> DeferredHolder<StructureType<?>, StructureType<P>> register(String name, MapCodec<P> codec) {
        return STRUCTURE_TYPES.register(name, () -> () -> codec);
    }
}
