package com.naterbobber.darkerdepths.init;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.worldgen.structures.DDMineshaftPieces;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = DarkerDepths.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DDStructurePieceTypes {
    public static final DeferredRegister<StructurePieceType> PIECE_TYPES =
            DeferredRegister.create(Registries.STRUCTURE_PIECE, DarkerDepths.MOD_ID);

    public static final RegistryObject<StructurePieceType> DD_ROOM = PIECE_TYPES.register("dd_mineshaft_room",
            () -> (StructurePieceType.ContextlessType) DDMineshaftPieces.MineShaftRoom::new);

    public static final RegistryObject<StructurePieceType> DD_CORRIDOR = PIECE_TYPES.register("dd_mineshaft_corridor",
            () -> (StructurePieceType.ContextlessType) DDMineshaftPieces.MineShaftCorridor::new);

    public static final RegistryObject<StructurePieceType> DD_CROSSING = PIECE_TYPES.register("dd_mineshaft_crossing",
            () -> (StructurePieceType.ContextlessType) DDMineshaftPieces.MineShaftCrossing::new);

    public static final RegistryObject<StructurePieceType> DD_STAIRS = PIECE_TYPES.register("dd_mineshaft_stairs",
            () -> (StructurePieceType.ContextlessType) DDMineshaftPieces.MineShaftStairs::new);
}