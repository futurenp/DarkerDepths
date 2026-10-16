package com.naterbobber.darkerdepths.common.init;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.common.worldgen.structures.DDMineshaftPieces;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class DDStructurePieceTypes {
    public static final DeferredRegister<StructurePieceType> STRUCTURE_PIECE_TYPES = DeferredRegister.create(Registries.STRUCTURE_PIECE, DarkerDepths.MOD_ID);

    public static final DeferredHolder<StructurePieceType, StructurePieceType.ContextlessType> PETRIFIED_MINESHAFT_CORRIDOR =
            STRUCTURE_PIECE_TYPES.register("petrified_mineshaft_corridor", () -> DDMineshaftPieces.MineShaftCorridor::new);
    public static final DeferredHolder<StructurePieceType, StructurePieceType.ContextlessType> PETRIFIED_MINESHAFT_CROSSING =
            STRUCTURE_PIECE_TYPES.register("petrified_mineshaft_crossing", () -> DDMineshaftPieces.MineShaftCrossing::new);
    public static final DeferredHolder<StructurePieceType, StructurePieceType.ContextlessType> PETRIFIED_MINESHAFT_ROOM =
            STRUCTURE_PIECE_TYPES.register("petrified_mineshaft_room", () -> DDMineshaftPieces.MineShaftRoom::new);
    public static final DeferredHolder<StructurePieceType, StructurePieceType.ContextlessType> PETRIFIED_MINESHAFT_STAIRS =
            STRUCTURE_PIECE_TYPES.register("petrified_mineshaft_stairs", () -> DDMineshaftPieces.MineShaftStairs::new);
}
