package com.naterbobber.darkerdepths.worldgen.structures;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import java.util.function.IntFunction;

import com.naterbobber.darkerdepths.init.DDBlocks;
import com.naterbobber.darkerdepths.init.DDStructureTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;
import net.minecraft.util.ByIdMap.OutOfBoundsStrategy;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;
import net.neoforged.neoforge.registries.DeferredBlock;

public class DDMineshaftStructure extends Structure {
    public static final MapCodec<DDMineshaftStructure> CODEC = RecordCodecBuilder.mapCodec((p_227971_) -> p_227971_.group(settingsCodec(p_227971_), DDMineshaftStructure.Type.CODEC.fieldOf("mineshaft_type").forGetter((p_227969_) -> p_227969_.type)).apply(p_227971_, DDMineshaftStructure::new));
    private final Type type;

    public DDMineshaftStructure(Structure.StructureSettings settings, Type type) {
        super(settings);
        this.type = type;
    }

    public Optional<Structure.GenerationStub> findGenerationPoint(Structure.GenerationContext context) {
        context.random().nextDouble();
        ChunkPos chunkpos = context.chunkPos();
        BlockPos blockpos = new BlockPos(chunkpos.getMiddleBlockX(), 50, chunkpos.getMinBlockZ());
        StructurePiecesBuilder structurepiecesbuilder = new StructurePiecesBuilder();
        int i = this.generatePiecesAndAdjust(structurepiecesbuilder, context);
        return Optional.of(new Structure.GenerationStub(blockpos.offset(0, i, 0), Either.right(structurepiecesbuilder)));
    }

    @Override
    public StructureType<DDMineshaftStructure> type() {
        return DDStructureTypes.PETRIFIED_MINESHAFT.get();
    }

    private int generatePiecesAndAdjust(StructurePiecesBuilder builder, Structure.GenerationContext context) {
        ChunkPos chunkpos = context.chunkPos();
        WorldgenRandom worldgenrandom = context.random();
        ChunkGenerator chunkgenerator = context.chunkGenerator();
        DDMineshaftPieces.MineShaftRoom mineshaftpieces$mineshaftroom = new DDMineshaftPieces.MineShaftRoom(0, worldgenrandom, chunkpos.getBlockX(2), chunkpos.getBlockZ(2), this.type);
        builder.addPiece(mineshaftpieces$mineshaftroom);
        mineshaftpieces$mineshaftroom.addChildren(mineshaftpieces$mineshaftroom, builder, worldgenrandom);
        int i = chunkgenerator.getSeaLevel();
        return builder.moveBelowSeaLevel(i, chunkgenerator.getMinY(), worldgenrandom, 10);

    }

    public enum Type implements StringRepresentable {
        PETRIFIED("petrified", DDBlocks.PETRIFIED_LOG, DDBlocks.PETRIFIED_PLANKS, DDBlocks.PETRIFIED_FENCE);

        public static final Codec<Type> CODEC = StringRepresentable.fromEnum(Type::values);
        private static final IntFunction<Type> BY_ID = ByIdMap.continuous(Enum::ordinal, values(), OutOfBoundsStrategy.ZERO);
        private final String name;
        private final DeferredBlock<? extends Block> woodState;
        private final DeferredBlock<? extends Block> planksHolder;
        private final DeferredBlock<? extends Block> fenceHolder;

        Type(String name, DeferredBlock<? extends Block> woodBlock, DeferredBlock<? extends Block> planksBlock, DeferredBlock<? extends Block> fenceBlock) {
            this.name = name;
            this.woodState = woodBlock;
            this.planksHolder = planksBlock;
            this.fenceHolder = fenceBlock;
        }

        public String getName() {
            return this.name;
        }

        public static Type byId(int id) {
            return BY_ID.apply(id);
        }

        public BlockState getWoodState() {
            return this.woodState.get().defaultBlockState();
        }

        public BlockState getPlanksState() {
            return this.planksHolder.get().defaultBlockState();
        }

        public BlockState getFenceState() {
            return this.fenceHolder.get().defaultBlockState();
        }

        public String getSerializedName() {
            return this.name;
        }
    }
}
