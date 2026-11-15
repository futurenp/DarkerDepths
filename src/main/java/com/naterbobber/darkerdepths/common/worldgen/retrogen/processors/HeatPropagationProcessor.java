package com.naterbobber.darkerdepths.common.worldgen.retrogen.processors;

import com.naterbobber.darkerdepths.common.block.DDBlockStateProperties;
import com.naterbobber.darkerdepths.common.block.generic.IHeatableBlock;
import com.naterbobber.darkerdepths.common.util.DDTags;
import com.naterbobber.darkerdepths.common.worldgen.retrogen.IChunkPostProcessor;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.LevelChunkSection;

import java.util.HashMap;
import java.util.Map;

public class HeatPropagationProcessor implements IChunkPostProcessor {
    private record Job(ChunkPos pos, HeatBakeTask task) {
    }

    private final Map<ServerLevel, Job> jobs = new HashMap<>();

    @Override
    public String getNbtTag() {
        return "darkerdepths_needs_heat_bake";
    }

    @Override
    public boolean requiresProcessing(ChunkAccess chunk) {
        for (var section : chunk.getSections()) {
            if (!section.hasOnlyAir() && section.getStates().maybeHas(HeatPropagationProcessor::isHeatable)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean processChunk(ServerLevel level, LevelChunk chunk, LevelChunk[][] localChunks, long deadline) {
        var job = jobs.get(level);

        if (job == null || !job.pos().equals(chunk.getPos())) {
            job = new Job(chunk.getPos(), new HeatBakeTask(chunk.getPos().x, chunk.getPos().z,
                    chunk.getMinBuildHeight(), chunk.getHeight()));
            jobs.put(level, job);
        }

        var pos = new BlockPos.MutableBlockPos();
        var access = new HeatBakeTask.Access() {
            private final LevelChunkSection[] sourceSections = new LevelChunkSection[chunk.getSectionsCount() * 9];
            private final byte[] sourcePresence = new byte[sourceSections.length];
            private LevelChunkSection sourceSection;

            private int sourceSectionIndex(int x, int sectionY, int z) {
                return (sectionY - chunk.getMinSection()) * 9 + x * 3 + z;
            }

            private boolean hasSources(int x, int sectionY, int z) {
                int index = sourceSectionIndex(x, sectionY, z);

                if (sourcePresence[index] == 0) {
                    var neighbor = localChunks[x][z];
                    var section = neighbor.getSection(sectionY - neighbor.getMinSection());

                    sourceSections[index] = section;
                    sourcePresence[index] = (byte) (!section.hasOnlyAir()
                            && section.getStates().maybeHas(HeatPropagationProcessor::isSource) ? 2 : 1);
                }

                return sourcePresence[index] == 2;
            }

            private LevelChunk chunkAt(int x, int z) {
                return localChunks[(x >> 4) - chunk.getPos().x + 1][(z >> 4) - chunk.getPos().z + 1];
            }

            private BlockState stateAt(int x, int y, int z) {
                var neighbor = chunkAt(x, z);
                return neighbor.getSection(neighbor.getSectionIndex(y)).getBlockState(x & 15, y & 15, z & 15);
            }

            @Override
            public boolean hasSourcesInSection(int sectionY) {
                for (int x = 0; x < 3; x++) {
                    for (int z = 0; z < 3; z++) {
                        if (hasSources(x, sectionY, z)) {
                            return true;
                        }
                    }
                }

                return false;
            }

            @Override
            public boolean selectSourceSection(int x, int y, int z) {
                int chunkX = (x >> 4) - chunk.getPos().x + 1;
                int chunkZ = (z >> 4) - chunk.getPos().z + 1;
                int sectionY = y >> 4;

                boolean present = hasSources(chunkX, sectionY, chunkZ);
                sourceSection = sourceSections[sourceSectionIndex(chunkX, sectionY, chunkZ)];

                return present;
            }

            @Override
            public int sourceHeat(int x, int y, int z) {
                var state = sourceSection.getBlockState(x & 15, y & 15, z & 15);
                return isSource(state) ? IHeatableBlock.determineHeat(state) : 0;
            }

            @Override
            public boolean isHeatable(int x, int y, int z) {
                return HeatPropagationProcessor.isHeatable(stateAt(x, y, z));
            }

            @Override
            public void applyHeat(int x, int y, int z, int heat) {
                var state = stateAt(x, y, z);

                if (heat > state.getValue(DDBlockStateProperties.HEAT_LEVEL)) {
                    pos.set(x, y, z);
                    setBlockFast(level, chunk, pos, state.setValue(DDBlockStateProperties.HEAT_LEVEL, heat),
                            Block.UPDATE_CLIENTS | Block.UPDATE_KNOWN_SHAPE);
                }
            }
        };

        if (job.task().advance(access, deadline, System::nanoTime)) {
            jobs.remove(level);
            return true;
        }

        return false;
    }

    private static boolean isHeatable(BlockState state) {
        return state.is(DDTags.Blocks.HEATABLE) && state.hasProperty(DDBlockStateProperties.HEAT_LEVEL);
    }

    private static boolean isSource(BlockState state) {
        return state.is(DDTags.Blocks.HEAT_PROVIDER) || state.getFluidState().is(DDTags.Fluids.HEAT_PROVIDER);
    }

    @Override
    public void clearSuspendedState(ServerLevel level, ChunkPos pos) {
        var job = jobs.get(level);

        if (job != null && Math.abs((long) job.pos().x - pos.x) <= 1 && Math.abs((long) job.pos().z - pos.z) <= 1) {
            jobs.remove(level);
        }
    }

    @Override
    public void clearSuspendedState() {
        jobs.clear();
    }
}
