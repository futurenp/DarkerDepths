package com.naterbobber.darkerdepths.worldgen.carvers;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.CarvingMask;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.Aquifer;
import net.minecraft.world.level.levelgen.carver.CarvingContext;
import net.minecraft.world.level.levelgen.carver.WorldCarver;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

public class SurfaceCrawlerCarver extends WorldCarver<BlockCarverConfiguration> {
    public SurfaceCrawlerCarver(Codec<BlockCarverConfiguration> codec) {
        super(codec);
    }

    // We override the root carve method to replace the standard "tube" math completely
    @Override
    public boolean carve(CarvingContext context, BlockCarverConfiguration config, ChunkAccess chunk, Function<BlockPos, Holder<Biome>> biomeAccessor, RandomSource random, Aquifer aquifer, ChunkPos chunkPos, CarvingMask carvingMask) {
        // Amount of distinct lines to generate per chunk (adjust as needed)
        int veinsToGenerate = random.nextInt(3) + 1;
        boolean carvedAny = false;

        for (int v = 0; v < veinsToGenerate; v++) {
            // 1. Find a starting point on an exposed cave wall
            BlockPos startPos = findExposedStart(context, config, chunk, chunkPos, random);
            if (startPos == null) continue;

            // 2. Start the surface crawl
            int maxLineLength = 30 + random.nextInt(50); // Line length: 30 to 80 blocks
            BlockPos.MutableBlockPos currentPos = startPos.mutable();
            Set<BlockPos> visited = new HashSet<>();

            // Give the line initial random momentum so it travels outward
            BlockPos currentDirection = new BlockPos(random.nextInt(3) - 1, random.nextInt(3) - 1, random.nextInt(3) - 1);

            for (int i = 0; i < maxLineLength; i++) {
                // Paint the current block
                BlockState placeState = config.placeableBlock.defaultBlockState();
                if (placeState != null) {
                    chunk.setBlockState(currentPos, placeState, false);
                    carvedAny = true;
                }
                visited.add(currentPos.immutable());

                // 3. Find the next surface block to crawl to
                BlockPos nextPos = getNextSurfaceNeighbor(context, config, chunk, currentPos, currentDirection, visited, random);

                if (nextPos == null) {
                    break; // Hit a dead end or chunk border, stop the line early
                }

                // 4. Update direction momentum to keep the line moving somewhat straight
                currentDirection = nextPos.subtract(currentPos);
                currentPos.set(nextPos);
            }
        }

        return carvedAny;
    }

    private BlockPos findExposedStart(CarvingContext context, BlockCarverConfiguration config, ChunkAccess chunk, ChunkPos chunkPos, RandomSource random) {
        int minX = chunkPos.getMinBlockX();
        int minZ = chunkPos.getMinBlockZ();
        int minY = context.getMinGenY() + 1;
        int maxY = context.getMinGenY() + context.getGenDepth() - 1;

        BlockPos.MutableBlockPos checkPos = new BlockPos.MutableBlockPos();

        // Try a few random spots in the chunk until we hit a valid cave wall
        for (int i = 0; i < 50; i++) {
            int x = minX + random.nextInt(16);
            int y = minY + random.nextInt(maxY - minY);
            int z = minZ + random.nextInt(16);
            checkPos.set(x, y, z);

            if (isSolidAndExposed(context, config, chunk, checkPos)) {
                return checkPos.immutable();
            }
        }
        return null;
    }

    private BlockPos getNextSurfaceNeighbor(CarvingContext context, BlockCarverConfiguration config, ChunkAccess chunk, BlockPos current, BlockPos direction, Set<BlockPos> visited, RandomSource random) {
        List<BlockPos> validNeighbors = new ArrayList<>();
        BlockPos.MutableBlockPos checkPos = new BlockPos.MutableBlockPos();
        ChunkPos chunkPos = chunk.getPos();

        // Check a 3x3x3 grid around the current block to allow climbing and dropping
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                for (int z = -1; z <= 1; z++) {
                    if (x == 0 && y == 0 && z == 0) continue; // Skip self

                    checkPos.setWithOffset(current, x, y, z);

                    // Crucial: Prevent leaving chunk boundaries to avoid generation crashes
                    if (checkPos.getX() < chunkPos.getMinBlockX() || checkPos.getX() > chunkPos.getMaxBlockX() ||
                            checkPos.getZ() < chunkPos.getMinBlockZ() || checkPos.getZ() > chunkPos.getMaxBlockZ()) {
                        continue;
                    }

                    if (!visited.contains(checkPos) && isSolidAndExposed(context, config, chunk, checkPos)) {
                        validNeighbors.add(checkPos.immutable());
                    }
                }
            }
        }

        if (validNeighbors.isEmpty()) return null;

        // Sort neighbors to favor the current momentum/direction so it draws a "line" and not a "blob"
        validNeighbors.sort((p1, p2) -> {
            // Calculate how much moving to p1 vs p2 aligns with our current momentum vector
            double align1 = p1.subtract(current).distManhattan(direction);
            double align2 = p2.subtract(current).distManhattan(direction);

            // Add a touch of randomness so the line winds naturally along the wall
            return Double.compare(align1 + random.nextDouble(), align2 + random.nextDouble());
        });

        return validNeighbors.get(0); // Return the best trajectory match
    }

    private boolean isSolidAndExposed(CarvingContext context, BlockCarverConfiguration config, ChunkAccess chunk, BlockPos pos) {
        // Must be inside vertical generation bounds
        if (pos.getY() < context.getMinGenY() || pos.getY() >= context.getMinGenY() + context.getGenDepth()) {
            return false;
        }

        BlockState state = chunk.getBlockState(pos);

        // Block must be valid to replace per your configuration (stone, deepslate, etc.)
        if (!this.canReplaceBlock(config, state)) {
            return false;
        }

        // Must border at least one air or fluid block to be considered "exposed"
        BlockPos.MutableBlockPos neighborPos = new BlockPos.MutableBlockPos();
        for (Direction dir : Direction.values()) {
            neighborPos.setWithOffset(pos, dir);

            if (neighborPos.getY() >= context.getMinGenY() && neighborPos.getY() < context.getMinGenY() + context.getGenDepth()) {
                BlockState neighborState = chunk.getBlockState(neighborPos);
                if (neighborState.isAir() || !neighborState.getFluidState().isEmpty()) {
                    return true;
                }
            }
        }

        return false;
    }

    // Since we completely replaced the pathfinding, we can safely ignore the vanilla math methods
    @Override
    protected boolean carveEllipsoid(CarvingContext context, BlockCarverConfiguration config, ChunkAccess chunk, Function<BlockPos, Holder<Biome>> biomeAccessor, Aquifer aquifer, double x, double y, double z, double horizontalRadius, double verticalRadius, CarvingMask carvingMask, CarveSkipChecker skipChecker) {
        return false;
    }

    @Override
    public boolean isStartChunk(BlockCarverConfiguration config, RandomSource random) {
        return random.nextFloat() <= config.probability;
    }
}