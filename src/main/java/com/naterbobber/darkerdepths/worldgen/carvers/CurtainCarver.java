package com.naterbobber.darkerdepths.worldgen.carvers;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.SectionPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.CarvingMask;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.Aquifer;
import net.minecraft.world.level.levelgen.carver.CarverConfiguration;
import net.minecraft.world.level.levelgen.carver.CarvingContext;
import net.minecraft.world.level.levelgen.carver.WorldCarver;
import org.apache.commons.lang3.mutable.MutableBoolean;

import java.util.function.Function;

public class CurtainCarver extends WorldCarver<BlockCarverConfiguration> {
    public CurtainCarver(Codec<BlockCarverConfiguration> codec) {
        super(codec);
    }

    @Override
    protected boolean carveEllipsoid(CarvingContext context, BlockCarverConfiguration config, ChunkAccess chunk, Function<BlockPos, Holder<Biome>> biomeAccessor, Aquifer aquifer, double x, double y, double z, double horizontalRadius, double verticalRadius, CarvingMask carvingMask, CarveSkipChecker skipChecker) {
        ChunkPos chunkpos = chunk.getPos();
        double d0 = (double)chunkpos.getMiddleBlockX();
        double d1 = (double)chunkpos.getMiddleBlockZ();
        double d2 = 16.0D + horizontalRadius * 2.0D;

        if (!(Math.abs(x - d0) > d2) && !(Math.abs(z - d1) > d2)) {
            int i = chunkpos.getMinBlockX();
            int j = chunkpos.getMinBlockZ();

            int k = Math.max(Mth.floor(x - horizontalRadius) - i - 1, 0);
            int l = Math.min(Mth.floor(x + horizontalRadius) - i, 15);
            int l1 = Math.max(Mth.floor(z - horizontalRadius) - j - 1, 0);
            int i2 = Math.min(Mth.floor(z + horizontalRadius) - j, 15);

            int i1 = context.getMinGenY() + 1;
            int k1 = context.getMinGenY() + context.getGenDepth() - 1;

            boolean flag = false;
            BlockPos.MutableBlockPos currentPos = new BlockPos.MutableBlockPos();
            BlockPos.MutableBlockPos checkPos = new BlockPos.MutableBlockPos();

            for(int j2 = k; j2 <= l; ++j2) {
                int k2 = chunkpos.getBlockX(j2);
                double d3 = ((double)k2 + 0.5D - x) / horizontalRadius;

                for(int l2 = l1; l2 <= i2; ++l2) {
                    int i3 = chunkpos.getBlockZ(l2);
                    double d4 = ((double)i3 + 0.5D - z) / horizontalRadius;

                    if (d3 * d3 + d4 * d4 < 1.0D) {
                        MutableBoolean reachedSurface = new MutableBoolean(false);
                        MutableBoolean draping = new MutableBoolean(false);

                        // Prime the draping state for floors/edges
                        checkPos.set(k2, k1 + 1, i3);
                        BlockState topState = chunk.getBlockState(checkPos);
                        if (topState.isAir() || !topState.getFluidState().isEmpty()) {
                            draping.setTrue();
                        }

                        // Scan top-to-bottom
                        for(int j3 = k1; j3 > i1; --j3) {
                            if (!carvingMask.get(j2, j3, l2) || isDebugEnabled(config)) {
                                currentPos.set(k2, j3, i3);
                                flag |= this.carveCurtainBlock(context, config, chunk, biomeAccessor, carvingMask, currentPos, checkPos, aquifer, reachedSurface, draping);
                            }
                        }
                    }
                }
            }
            return flag;
        } else {
            return false;
        }
    }

    protected boolean carveCurtainBlock(CarvingContext context, BlockCarverConfiguration config, ChunkAccess chunk, Function<BlockPos, Holder<Biome>> biomeGetter, CarvingMask carvingMask, BlockPos.MutableBlockPos pos, BlockPos.MutableBlockPos checkPos, Aquifer aquifer, MutableBoolean reachedSurface, MutableBoolean draping) {
        BlockState blockstate = chunk.getBlockState(pos);

        if (blockstate.isAir() || !blockstate.getFluidState().isEmpty()) {
            draping.setTrue();
            return false;
        }

        if (blockstate.is(Blocks.GRASS_BLOCK) || blockstate.is(Blocks.MYCELIUM)) {
            reachedSurface.setTrue();
        }

        if (!this.canReplaceBlock(config, blockstate) && !isDebugEnabled(config)) {
            draping.setFalse();
            return false;
        }

        boolean isHorizontallyExposed = false;
        for (Direction dir : Direction.Plane.HORIZONTAL) {
            int adjX = pos.getX() + dir.getStepX();
            int adjZ = pos.getZ() + dir.getStepZ();

            if (adjX >= chunk.getPos().getMinBlockX() && adjX <= chunk.getPos().getMaxBlockX() &&
                    adjZ >= chunk.getPos().getMinBlockZ() && adjZ <= chunk.getPos().getMaxBlockZ()) {

                checkPos.set(adjX, pos.getY(), adjZ);
                BlockState adjState = chunk.getBlockState(checkPos);

                if (adjState.isAir() || !adjState.getFluidState().isEmpty()) {
                    isHorizontallyExposed = true;
                    break;
                }
            }
        }

        if (draping.isTrue() && !isHorizontallyExposed) {
            draping.setFalse();
        }

        // --- CEILING EXPOSURE EXCEPTION ---
        // Look ahead (downwards) up to 2 blocks. If there's air, we are at a ceiling.
        boolean isBottomExposed = false;
        for (int yOffset = 1; yOffset <= 2; yOffset++) {
            if (pos.getY() - yOffset >= context.getMinGenY()) {
                checkPos.setWithOffset(pos, 0, -yOffset, 0);
                BlockState belowState = chunk.getBlockState(checkPos);
                if (belowState.isAir() || !belowState.getFluidState().isEmpty()) {
                    isBottomExposed = true;
                    break;
                }
            }
        }

        // If we are NOT draping from the top, AND we are NOT within 2 blocks of a ceiling, apply the sheer-wall rule
        if (draping.isFalse() && !isBottomExposed) {
            if (isNextToDeepAirColumn(chunk, pos, checkPos, context)) {
                return false;
            }
        }

        BlockState blockstate1 = config.placeableBlock.defaultBlockState();
        if (blockstate1 == null) return false;

        chunk.setBlockState(pos, blockstate1, false);

        if (aquifer.shouldScheduleFluidUpdate() && !blockstate1.getFluidState().isEmpty()) {
            chunk.markPosForPostprocessing(pos);
        }

        if (reachedSurface.isTrue()) {
            checkPos.setWithOffset(pos, Direction.DOWN);
            if (chunk.getBlockState(checkPos).is(Blocks.DIRT)) {
                context.topMaterial(biomeGetter, chunk, checkPos, !blockstate1.getFluidState().isEmpty()).ifPresent((p_284918_) -> {
                    chunk.setBlockState(checkPos, p_284918_, false);
                    if (!p_284918_.getFluidState().isEmpty()) {
                        chunk.markPosForPostprocessing(checkPos);
                    }
                });
            }
        }

        return true;
    }

    private boolean isNextToDeepAirColumn(ChunkAccess chunk, BlockPos pos, BlockPos.MutableBlockPos checkPos, CarvingContext context) {
        int minX = chunk.getPos().getMinBlockX();
        int maxX = chunk.getPos().getMaxBlockX();
        int minZ = chunk.getPos().getMinBlockZ();
        int maxZ = chunk.getPos().getMaxBlockZ();

        for (Direction dir : Direction.Plane.HORIZONTAL) {
            int adjX = pos.getX() + dir.getStepX();
            int adjZ = pos.getZ() + dir.getStepZ();

            if (adjX < minX || adjX > maxX || adjZ < minZ || adjZ > maxZ) {
                continue;
            }

            checkPos.set(adjX, pos.getY(), adjZ);
            BlockState adjState = chunk.getBlockState(checkPos);

            if (adjState.isAir() || !adjState.getFluidState().isEmpty()) {
                int airColumn = 1;

                // Scan Upwards
                for (int y = 1; y <= 3; y++) {
                    checkPos.set(adjX, pos.getY() + y, adjZ);
                    if (checkPos.getY() >= context.getMinGenY() + context.getGenDepth()) break;

                    BlockState upState = chunk.getBlockState(checkPos);
                    if (upState.isAir() || !upState.getFluidState().isEmpty()) {
                        airColumn++;
                    } else {
                        break;
                    }
                }

                // Scan Downwards
                for (int y = 1; y <= 3; y++) {
                    if (airColumn > 3) break;

                    checkPos.set(adjX, pos.getY() - y, adjZ);
                    if (checkPos.getY() < context.getMinGenY()) break;

                    BlockState downState = chunk.getBlockState(checkPos);
                    if (downState.isAir() || !downState.getFluidState().isEmpty()) {
                        airColumn++;
                    } else {
                        break;
                    }
                }

                if (airColumn > 3) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    protected boolean carveBlock(CarvingContext context, BlockCarverConfiguration config, ChunkAccess chunk, Function<BlockPos, Holder<Biome>> biomeGetter, CarvingMask carvingMask, BlockPos.MutableBlockPos pos, BlockPos.MutableBlockPos checkPos, Aquifer aquifer, MutableBoolean reachedSurface) {
        return false;
    }

    private static boolean isDebugEnabled(CarverConfiguration config) {
        return config.debugSettings.isDebugMode();
    }

    public boolean isStartChunk(BlockCarverConfiguration config, RandomSource random) {
        return random.nextFloat() <= config.probability;
    }

    public boolean carve(CarvingContext context, BlockCarverConfiguration config, ChunkAccess chunk, Function<BlockPos, Holder<Biome>> biomeAccessor, RandomSource random, Aquifer aquifer, ChunkPos chunkPos, CarvingMask carvingMask) {
        int i = SectionPos.sectionToBlockCoord(this.getRange() * 2 - 1);
        int j = random.nextInt(random.nextInt(random.nextInt(this.getCaveBound()) + 1) + 1);

        for(int k = 0; k < j; ++k) {
            double d0 = (double)chunkPos.getBlockX(random.nextInt(16));
            double d1 = (double)config.y.sample(random, context);
            double d2 = (double)chunkPos.getBlockZ(random.nextInt(16));
            double d3 = (double)config.horizontalRadiusMultiplier.sample(random);
            double d4 = (double)config.verticalRadiusMultiplier.sample(random);

            WorldCarver.CarveSkipChecker skipChecker = (p_159202_, p_159203_, p_159204_, p_159205_, p_159206_) -> false;

            for(int k1 = 0; k1 < 1; ++k1) {
                float f = random.nextFloat() * ((float)Math.PI * 2F);
                float f3 = (random.nextFloat() - 0.5F) / 4.0F;
                float f2 = this.getThickness(random);
                int i1 = i - random.nextInt(i / 4);
                this.createTunnel(context, config, chunk, biomeAccessor, random.nextLong(), aquifer, d0, d1, d2, d3, d4, f2, f, f3, 0, i1, this.getYScale(), carvingMask, skipChecker);
            }
        }
        return true;
    }

    protected int getCaveBound() {
        return 15;
    }

    protected float getThickness(RandomSource random) {
        float f = random.nextFloat() * 1.5F + 1.5F;
        if (random.nextInt(10) == 0) {
            f *= random.nextFloat() * random.nextFloat() * 1.5F + 1.0F;
        }
        return f;
    }

    @Override
    public int getRange() {
        return 6;
    }

    protected double getYScale() {
        return 1.0D;
    }

    protected void createTunnel(CarvingContext context, BlockCarverConfiguration config, ChunkAccess chunk, Function<BlockPos, Holder<Biome>> biomeAccessor, long seed, Aquifer aquifer, double x, double y, double z, double horizontalRadiusMultiplier, double verticalRadiusMultiplier, float thickness, float yaw, float pitch, int branchIndex, int branchCount, double horizontalVerticalRatio, CarvingMask carvingMask, WorldCarver.CarveSkipChecker skipChecker) {
        RandomSource randomsource = RandomSource.create(seed);
        int i = randomsource.nextInt(branchCount / 2) + branchCount / 4;
        boolean flag = randomsource.nextInt(6) == 0;
        float f = 0.0F;
        float f1 = 0.0F;

        for(int j = branchIndex; j < branchCount; ++j) {
            double d0 = 1.5D + (double)(Mth.sin((float)Math.PI * (float)j / (float)branchCount) * thickness);
            double d1 = d0 * horizontalVerticalRatio;
            float f2 = Mth.cos(pitch);
            x += (double)(Mth.cos(yaw) * f2);
            y += (double)Mth.sin(pitch);
            z += (double)(Mth.sin(yaw) * f2);
            pitch *= flag ? 0.92F : 0.7F;
            pitch += f1 * 0.1F;
            yaw += f * 0.1F;
            f1 *= 0.9F;
            f *= 0.75F;
            f1 += (randomsource.nextFloat() - randomsource.nextFloat()) * randomsource.nextFloat() * 1.0F;
            f += (randomsource.nextFloat() - randomsource.nextFloat()) * randomsource.nextFloat() * 2.0F;

            if (j == i && thickness > 1.0F) {
                this.createTunnel(context, config, chunk, biomeAccessor, randomsource.nextLong(), aquifer, x, y, z, horizontalRadiusMultiplier, verticalRadiusMultiplier, randomsource.nextFloat() * 0.5F + 0.5F, yaw - ((float)Math.PI / 2F), pitch / 3.0F, j, branchCount, 1.0D, carvingMask, skipChecker);
                this.createTunnel(context, config, chunk, biomeAccessor, randomsource.nextLong(), aquifer, x, y, z, horizontalRadiusMultiplier, verticalRadiusMultiplier, randomsource.nextFloat() * 0.5F + 0.5F, yaw + ((float)Math.PI / 2F), pitch / 3.0F, j, branchCount, 1.0D, carvingMask, skipChecker);
                return;
            }

            if (randomsource.nextInt(4) != 0) {
                if (!canReach(chunk.getPos(), x, z, j, branchCount, thickness)) {
                    return;
                }
                this.carveEllipsoid(context, config, chunk, biomeAccessor, aquifer, x, y, z, d0 * horizontalRadiusMultiplier, d1 * verticalRadiusMultiplier, carvingMask, skipChecker);
            }
        }
    }
}