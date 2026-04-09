package com.naterbobber.darkerdepths.worldgen.carvers;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.chunk.CarvingMask;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.Aquifer;
import net.minecraft.world.level.levelgen.carver.CarvingContext;
import net.minecraft.world.level.levelgen.carver.WorldCarver;

import java.util.function.Function;

public class ChalkRiverCarver extends WorldCarver<ChalkRiverConfiguration> {

    public ChalkRiverCarver(Codec<ChalkRiverConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean isStartChunk(ChalkRiverConfiguration config, RandomSource random) {
        return true;
    }

    @Override
    public boolean carve(CarvingContext context, ChalkRiverConfiguration config, ChunkAccess chunk,
                         Function<BlockPos, Holder<Biome>> biomeAccessor, RandomSource random,
                         Aquifer aquifer, ChunkPos chunkPos, CarvingMask mask) {
        return false;
    }
}