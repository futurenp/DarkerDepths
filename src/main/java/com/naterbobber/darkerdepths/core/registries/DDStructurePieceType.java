package com.naterbobber.darkerdepths.core.registries;

import com.naterbobber.darkerdepths.common.world.gen.feature.structures.CaveFossilPieces;
import com.naterbobber.darkerdepths.core.DarkerDepths;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

//<>

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class DDStructurePieceType {
    public static IStructurePieceType CAVE_FOSSIL;

    @SubscribeEvent
    public static void registerStructurePieces(RegistryEvent.Register<Feature<?>> event) {
        CAVE_FOSSIL = Registry.register(Registry.STRUCTURE_PIECE, DarkerDepths.MODID + ":cave_fossil", CaveFossilPieces.CaveFossilPiece::new);
    }
}