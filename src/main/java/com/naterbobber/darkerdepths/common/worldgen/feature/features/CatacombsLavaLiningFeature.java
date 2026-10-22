package com.naterbobber.darkerdepths.common.worldgen.feature.features;

import com.mojang.serialization.Codec;
import com.naterbobber.darkerdepths.common.init.DDBlocks;
import com.naterbobber.darkerdepths.common.util.DDResourceKeys;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class CatacombsLavaLiningFeature extends Feature<NoneFeatureConfiguration> {
    private static final Direction[] DIRECTIONS = Direction.values();

    public CatacombsLavaLiningFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        var world = context.level();
        var chunkPos = new ChunkPos(context.origin());
        var random = context.random();
        var currentPos = new BlockPos.MutableBlockPos();
        var adjacentPos = new BlockPos.MutableBlockPos();
        var abovePos = new BlockPos.MutableBlockPos();
        int startX = chunkPos.getMinBlockX();
        int startZ = chunkPos.getMinBlockZ();
        int minY = Math.max(world.getMinBuildHeight(), -71);
        int maxY = Math.min(world.getMaxBuildHeight() - 1, 7);
        boolean placed = false;

        for (int cx = -1; cx <= 1; cx++) {
            for (int cz = -1; cz <= 1; cz++) {
                var chunk = world.getChunk(chunkPos.x + cx, chunkPos.z + cz);
                int minX = Math.max(startX - 1, chunk.getPos().getMinBlockX());
                int maxX = Math.min(startX + 16, chunk.getPos().getMaxBlockX());
                int minZ = Math.max(startZ - 1, chunk.getPos().getMinBlockZ());
                int maxZ = Math.min(startZ + 16, chunk.getPos().getMaxBlockZ());

                for (int sy = minY >> 4; sy <= maxY >> 4; sy++) {
                    var section = chunk.getSection(sy - chunk.getMinSection());
                    if (section.hasOnlyAir() || !section.getStates().maybeHas(state -> state.is(Blocks.LAVA))) continue;
                    for (int y = Math.max(minY, sy * 16); y <= Math.min(maxY, sy * 16 + 15); y++) {
                        for (int x = minX; x <= maxX; x++) {
                            for (int z = minZ; z <= maxZ; z++) {
                                if (!section.getBlockState(x & 15, y & 15, z & 15).is(Blocks.LAVA)) continue;
                                currentPos.set(x, y, z);
                                for (var direction : DIRECTIONS) {
                                    adjacentPos.setWithOffset(currentPos, direction);
                                    if ((adjacentPos.getX() >> 4) != chunkPos.x || (adjacentPos.getZ() >> 4) != chunkPos.z
                                            || world.isOutsideBuildHeight(adjacentPos)) continue;
                                    var state = world.getBlockState(adjacentPos);
                                    if (!canReplace(state) && !state.is(DDBlocks.DUSKROCK.get())) continue;
                                    if (!world.getBiome(adjacentPos).is(DDResourceKeys.Biomes.SANDY_CATACOMBS)) continue;
                                    world.setBlock(adjacentPos, Blocks.SMOOTH_BASALT.defaultBlockState(), Block.UPDATE_CLIENTS);
                                    placed = true;
                                    abovePos.setWithOffset(adjacentPos, Direction.UP);
                                    if (world.getBlockState(abovePos).is(DDBlocks.DRY_SPROUTS.get())) {
                                        world.setBlock(abovePos, Blocks.AIR.defaultBlockState(), Block.UPDATE_CLIENTS);
                                    }
                                    placeCluster(world, adjacentPos, random, chunkPos);
                                }
                            }
                        }
                    }
                }
            }
        }
        return placed;
    }

    private static boolean canReplace(BlockState state) {
        return state.is(BlockTags.BASE_STONE_OVERWORLD) || state.is(DDBlocks.ARIDROCK.get())
                || state.is(DDBlocks.ARID_DEEPSLATE.get()) || state.is(Blocks.PACKED_MUD);
    }

    private void placeCluster(WorldGenLevel world, BlockPos centerPos, RandomSource random, ChunkPos chunkPos) {
        int radius = 1 + random.nextInt(2);
        var pos = new BlockPos.MutableBlockPos();
        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    if (x * x + y * y + z * z > radius * radius) continue;
                    pos.setWithOffset(centerPos, x, y, z);
                    if ((pos.getX() >> 4) != chunkPos.x || (pos.getZ() >> 4) != chunkPos.z) continue;
                    var state = world.getBlockState(pos);
                    if (!state.is(DDBlocks.DARKSLATE.get()) && !state.is(Blocks.LAVA) && canReplace(state)) {
                        world.setBlock(pos, DDBlocks.DUSKROCK.get().defaultBlockState(), Block.UPDATE_CLIENTS);
                    }
                }
            }
        }
    }
}
