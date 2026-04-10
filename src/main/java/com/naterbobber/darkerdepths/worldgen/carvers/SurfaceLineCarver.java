package com.naterbobber.darkerdepths.worldgen.carvers;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.CarvingMask;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.Aquifer;
import net.minecraft.world.level.levelgen.carver.CarvingContext;
import net.minecraft.world.level.levelgen.carver.WorldCarver;

import java.util.function.Function;

public class SurfaceLineCarver extends WorldCarver<BlockCarverConfiguration> {
    public SurfaceLineCarver(Codec<BlockCarverConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean carve(CarvingContext context, BlockCarverConfiguration config, ChunkAccess chunk, Function<BlockPos, Holder<Biome>> biomeAccessor, RandomSource random, Aquifer aquifer, ChunkPos chunkPos, CarvingMask carvingMask) {
        int veins = random.nextInt(3) + 1;

        for(int k = 0; k < veins; ++k) {
            double startX = chunkPos.getBlockX(random.nextInt(16));
            double startY = config.y.sample(random, context);
            double startZ = chunkPos.getBlockZ(random.nextInt(16));

            float initialYaw = random.nextFloat() * ((float)Math.PI * 2F);
            float initialPitch = (random.nextFloat() - 0.5F) / 4.0F;

            // Define the specific wall this line will hug (Left or Right relative to travel direction)
            float laserAngleOffset = (random.nextFloat() < 0.5f ? 1.0f : -1.0f) * ((float)Math.PI / 2F);

            // Give it a slight upward or downward slant so lines aren't perfectly horizontal
            float laserPitch = (random.nextFloat() - 0.5f) * 0.5f;

            this.createLaserTunnel(context, config, chunk, startX, startY, startZ, initialYaw, initialPitch, laserAngleOffset, laserPitch, random.nextLong());
        }

        return true;
    }

    protected void createLaserTunnel(CarvingContext context, BlockCarverConfiguration config, ChunkAccess chunk, double x, double y, double z, float yaw, float pitch, float laserAngleOffset, float laserPitch, long seed) {
        RandomSource random = RandomSource.create(seed);

        // 4x Resolution for perfectly continuous, unbroken lines
        int highResCount = 400;
        float stepScale = 0.25F;

        float f = 0.0F;
        float f1 = 0.0F;

        int currentThickness = 1;
        int thicknessDuration = 0;

        BlockPos.MutableBlockPos lastPlaced = new BlockPos.MutableBlockPos();
        boolean hasLast = false;

        for (int j = 0; j < highResCount; ++j) {
            // Manage thickness segments (holds thickness for 4-8 blocks of distance)
            if (thicknessDuration <= 0) {
                currentThickness = random.nextFloat() < 0.35F ? 2 : 1;
                thicknessDuration = 16 + random.nextInt(16);
            }
            thicknessDuration--;

            // Move the theoretical center forward
            float f2 = Mth.cos(pitch);
            x += (double)(Mth.cos(yaw) * f2) * stepScale;
            y += (double)Mth.sin(pitch) * stepScale;
            z += (double)(Mth.sin(yaw) * f2) * stepScale;

            // Only update travel angles at normal frequency to keep cave shapes natural
            if (j % 4 == 0) {
                pitch *= 0.92F;
                pitch += f1 * 0.1F;
                yaw += f * 0.1F;
                f1 *= 0.9F;
                f *= 0.75F;
                f1 += (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 1.0F;
                f += (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 2.0F;
            }

            // Calculate the Laser Vector (pointing at the wall)
            float currentLaserYaw = yaw + laserAngleOffset;
            double lX = Mth.cos(currentLaserYaw);
            double lY = laserPitch;
            double lZ = Mth.sin(currentLaserYaw);

            // Normalize laser
            double len = Math.sqrt(lX*lX + lY*lY + lZ*lZ);
            lX /= len; lY /= len; lZ /= len;

            // Fire laser to paint the wall
            boolean hit = fireLaser(context, config, chunk, x, y, z, lX, lY, lZ, currentThickness, lastPlaced, hasLast);
            if (hit) {
                hasLast = true;
            } else if (random.nextFloat() < 0.05f) {
                // If the laser spends too long in empty air, reset the connection to avoid cross-room lines
                hasLast = false;
            }
        }
    }

    private boolean fireLaser(CarvingContext context, BlockCarverConfiguration config, ChunkAccess chunk, double oX, double oY, double oZ, double dirX, double dirY, double dirZ, int thickness, BlockPos.MutableBlockPos lastPlaced, boolean hasLast) {
        ChunkPos chunkpos = chunk.getPos();
        int minX = chunkpos.getMinBlockX();
        int maxX = chunkpos.getMaxBlockX();
        int minZ = chunkpos.getMinBlockZ();
        int maxZ = chunkpos.getMaxBlockZ();

        double maxDistance = 10.0D;
        double step = 0.5D;

        BlockPos.MutableBlockPos testPos = new BlockPos.MutableBlockPos();

        for (double d = 0; d <= maxDistance; d += step) {
            int px = Mth.floor(oX + dirX * d);
            int py = Mth.floor(oY + dirY * d);
            int pz = Mth.floor(oZ + dirZ * d);

            testPos.set(px, py, pz);

            if (py < context.getMinGenY() || py >= context.getMinGenY() + context.getGenDepth()) {
                continue;
            }

            // Ensure the ray is inside the currently generating chunk
            if (px >= minX && px <= maxX && pz >= minZ && pz <= maxZ) {
                BlockState state = chunk.getBlockState(testPos);

                // Ray continues through air and already-placed magma
                if (state.isAir() || !state.getFluidState().isEmpty() || state.is(config.placeableBlock)) {
                    continue;
                }

                // Ray hit a rock! Is it exposed to the surface?
                if (isSolidAndExposed(context, config, chunk, testPos)) {
                    BlockState placeState = config.placeableBlock.defaultBlockState();
                    if (placeState == null) return false;

                    chunk.setBlockState(testPos, placeState, false);

                    // Standard horizontal thickness
                    if (thickness > 1) {
                        BlockPos.MutableBlockPos below = new BlockPos.MutableBlockPos().setWithOffset(testPos, Direction.DOWN);
                        if (below.getY() >= context.getMinGenY() && isSolidAndExposed(context, config, chunk, below)) {
                            chunk.setBlockState(below, placeState, false);
                        }
                    }

                    // Steep Slope / Vertical Gap filler
                    if (hasLast) {
                        int dy = Math.abs(lastPlaced.getY() - py);
                        int dx = Math.abs(lastPlaced.getX() - px);
                        int dz = Math.abs(lastPlaced.getZ() - pz);

                        // If it dropped vertically, but didn't teleport across the room
                        if (dy > 1 && dx <= 2 && dz <= 2) {
                            int minY = Math.min(lastPlaced.getY(), py);
                            int maxY = Math.max(lastPlaced.getY(), py);

                            for (int fillY = minY + 1; fillY < maxY; fillY++) {
                                BlockPos fillPos = new BlockPos(px, fillY, pz);
                                if (isSolidAndExposed(context, config, chunk, fillPos)) {
                                    chunk.setBlockState(fillPos, placeState, false);

                                    // Make vertical lines slightly thicker to match the 1-2 block width
                                    if (thickness > 1) {
                                        for(Direction dir : Direction.Plane.HORIZONTAL) {
                                            BlockPos beside = fillPos.relative(dir);
                                            if (beside.getX() >= minX && beside.getX() <= maxX && beside.getZ() >= minZ && beside.getZ() <= maxZ) {
                                                if(isSolidAndExposed(context, config, chunk, beside)) {
                                                    chunk.setBlockState(beside, placeState, false);
                                                    break; // Widen by just 1 block
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                    lastPlaced.set(testPos);
                    return true; // Laser hit the wall, stop raycast
                } else {
                    // Hit solid rock that is NOT exposed (it's buried deep). Stop the laser.
                    return false;
                }
            }
        }
        return false;
    }

    private boolean isSolidAndExposed(CarvingContext context, BlockCarverConfiguration config, ChunkAccess chunk, BlockPos pos) {
        if (pos.getY() < context.getMinGenY() || pos.getY() >= context.getMinGenY() + context.getGenDepth()) return false;

        BlockState state = chunk.getBlockState(pos);

        if (state.is(config.placeableBlock)) return false;
        if (!this.canReplaceBlock(config, state)) return false;

        BlockPos.MutableBlockPos nPos = new BlockPos.MutableBlockPos();
        for (Direction dir : Direction.values()) {
            nPos.setWithOffset(pos, dir);
            if (nPos.getY() >= context.getMinGenY() && nPos.getY() < context.getMinGenY() + context.getGenDepth()) {
                BlockState neighborState = chunk.getBlockState(nPos);
                if (neighborState.isAir() || !neighborState.getFluidState().isEmpty()) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    protected boolean carveEllipsoid(CarvingContext context, BlockCarverConfiguration config, ChunkAccess chunk, Function<BlockPos, Holder<Biome>> biomeAccessor, Aquifer aquifer, double x, double y, double z, double horizontalRadius, double verticalRadius, CarvingMask carvingMask, CarveSkipChecker skipChecker) {
        return false; // Bypassed
    }

    @Override
    public boolean isStartChunk(BlockCarverConfiguration config, RandomSource random) {
        return random.nextFloat() <= config.probability;
    }
}