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

public class BlockCarver extends WorldCarver<BlockCarverConfiguration> {
    public BlockCarver(Codec<BlockCarverConfiguration> codec) {
        super(codec);
    }

    @Override
    protected boolean carveEllipsoid(CarvingContext context, BlockCarverConfiguration config, ChunkAccess chunk, Function<BlockPos, Holder<Biome>> biomeAccessor, Aquifer aquifer, double x, double y, double z, double horizontalRadius, double verticalRadius, CarvingMask carvingMask, CarveSkipChecker skipChecker) {
        ChunkPos chunkpos = chunk.getPos();
        double d0 = (double)chunkpos.getMiddleBlockX();
        double d1 = (double)chunkpos.getMiddleBlockZ();
        double d2 = (double)16.0F + horizontalRadius * (double)2.0F;
        if (!(Math.abs(x - d0) > d2) && !(Math.abs(z - d1) > d2)) {
            int i = chunkpos.getMinBlockX();
            int j = chunkpos.getMinBlockZ();
            int k = Math.max(Mth.floor(x - horizontalRadius) - i - 1, 0);
            int l = Math.min(Mth.floor(x + horizontalRadius) - i, 15);
            int i1 = Math.max(Mth.floor(y - verticalRadius) - 1, context.getMinGenY() + 1);
            int j1 = chunk.isUpgrading() ? 0 : 7;
            int k1 = Math.min(Mth.floor(y + verticalRadius) + 1, context.getMinGenY() + context.getGenDepth() - 1 - j1);
            int l1 = Math.max(Mth.floor(z - horizontalRadius) - j - 1, 0);
            int i2 = Math.min(Mth.floor(z + horizontalRadius) - j, 15);
            boolean flag = false;
            BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
            BlockPos.MutableBlockPos blockpos$mutableblockpos1 = new BlockPos.MutableBlockPos();

            // Calculate the normalized distance of 1 block space to check neighbors
            double dx = 1.0 / Math.max(0.001, horizontalRadius);
            double dy = 1.0 / Math.max(0.001, verticalRadius);
            double dz = 1.0 / Math.max(0.001, horizontalRadius);

            for(int j2 = k; j2 <= l; ++j2) {
                int k2 = chunkpos.getBlockX(j2);
                double d3 = ((double)k2 + (double)0.5F - x) / horizontalRadius;

                for(int l2 = l1; l2 <= i2; ++l2) {
                    int i3 = chunkpos.getBlockZ(l2);
                    double d4 = ((double)i3 + (double)0.5F - z) / horizontalRadius;
                    if (!(d3 * d3 + d4 * d4 >= (double)1.0F)) {
                        MutableBoolean mutableboolean = new MutableBoolean(false);

                        for(int j3 = k1; j3 > i1; --j3) {
                            double d5 = ((double)j3 - (double)0.5F - y) / verticalRadius;
                            if (!skipChecker.shouldSkip(context, d3, d5, d4, j3) && (!carvingMask.get(j2, j3, l2) || isDebugEnabled(config))) {

                                boolean isBorder =
                                        skipChecker.shouldSkip(context, d3 + dx, d5, d4, j3) ||
                                                skipChecker.shouldSkip(context, d3 - dx, d5, d4, j3) ||
                                                skipChecker.shouldSkip(context, d3, d5 + dy, d4, j3 + 1) ||
                                                skipChecker.shouldSkip(context, d3, d5 - dy, d4, j3 - 1) ||
                                                skipChecker.shouldSkip(context, d3, d5, d4 + dz, j3) ||
                                                skipChecker.shouldSkip(context, d3, d5, d4 - dz, j3);

                                blockpos$mutableblockpos.set(k2, j3, i3);
                                flag |= this.carveCustomBlock(context, config, chunk, biomeAccessor, carvingMask, blockpos$mutableblockpos, blockpos$mutableblockpos1, aquifer, mutableboolean, isBorder);
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

    @Override
    protected boolean carveBlock(CarvingContext context, BlockCarverConfiguration config, ChunkAccess chunk, Function<BlockPos, Holder<Biome>> biomeGetter, CarvingMask carvingMask, BlockPos.MutableBlockPos pos, BlockPos.MutableBlockPos checkPos, Aquifer aquifer, MutableBoolean reachedSurface) {
        // Reroute the standard method to ours with isBorder = false as a fallback
        return this.carveCustomBlock(context, config, chunk, biomeGetter, carvingMask, pos, checkPos, aquifer, reachedSurface, false);
    }

    protected boolean carveCustomBlock(CarvingContext context, BlockCarverConfiguration config, ChunkAccess chunk, Function<BlockPos, Holder<Biome>> biomeGetter, CarvingMask carvingMask, BlockPos.MutableBlockPos pos, BlockPos.MutableBlockPos checkPos, Aquifer aquifer, MutableBoolean reachedSurface, boolean isBorder) {
        BlockState blockstate = chunk.getBlockState(pos);
        if (blockstate.is(Blocks.GRASS_BLOCK) || blockstate.is(Blocks.MYCELIUM)) {
            reachedSurface.setTrue();
        }

        if (!blockstate.getFluidState().isEmpty()) {
            return false;
        }

        boolean canReplace = this.canReplaceBlock(config, blockstate)
                || blockstate.is(config.placeableBlock)
                || blockstate.is(config.borderBlock);

        if (!canReplace && !isDebugEnabled(config)) {
            return false;
        }

        if (isBorder && blockstate.is(config.placeableBlock)) {
            return false;
        }

        BlockState blockstate1 = isBorder ? config.borderBlock.defaultBlockState() : config.placeableBlock.defaultBlockState();

        if (blockstate1 == null) {
            return false;
        }

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
            double d5 = (double)config.floorLevel.sample(random);
            WorldCarver.CarveSkipChecker worldcarver$carveskipchecker = (p_159202_, p_159203_, p_159204_, p_159205_, p_159206_) -> shouldSkip(p_159203_, p_159204_, p_159205_, d5);
            int l = 1;

            for(int k1 = 0; k1 < l; ++k1) {
                float f = random.nextFloat() * ((float)Math.PI * 2F);
                float f3 = (random.nextFloat() - 0.5F) / 4.0F;
                float f2 = this.getThickness(random);
                int i1 = i - random.nextInt(i / 4);

                this.createTunnel(context, config, chunk, biomeAccessor, random.nextLong(), aquifer, d0, d1, d2, d3, d4, f2, f, f3, 0, i1, this.getYScale(), carvingMask, worldcarver$carveskipchecker);
            }
        }

        return true;
    }

    protected int getCaveBound() {
        return 15;
    }

    protected float getThickness(RandomSource random) {
        float f = random.nextFloat() * 2.0F + random.nextFloat();
        if (random.nextInt(10) == 0) {
            f *= random.nextFloat() * random.nextFloat() * 1.5F + 1.0F;
        }

        return f;
    }

    @Override
    public int getRange() {
        return 10;
    }

    protected double getYScale() {
        return (double)1.0F;
    }

    protected void createTunnel(CarvingContext context, BlockCarverConfiguration config, ChunkAccess chunk, Function<BlockPos, Holder<Biome>> biomeAccessor, long seed, Aquifer aquifer, double x, double y, double z, double horizontalRadiusMultiplier, double verticalRadiusMultiplier, float thickness, float yaw, float pitch, int branchIndex, int branchCount, double horizontalVerticalRatio, CarvingMask carvingMask, WorldCarver.CarveSkipChecker skipChecker) {
        RandomSource randomsource = RandomSource.create(seed);
        int i = randomsource.nextInt(branchCount / 2) + branchCount / 4;
        boolean flag = randomsource.nextInt(6) == 0;
        float f = 0.0F;
        float f1 = 0.0F;

        for(int j = branchIndex; j < branchCount; ++j) {
            double d0 = (double)1.5F + (double)(Mth.sin((float)Math.PI * (float)j / (float)branchCount) * thickness);
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
                this.createTunnel(context, config, chunk, biomeAccessor, randomsource.nextLong(), aquifer, x, y, z, horizontalRadiusMultiplier, verticalRadiusMultiplier, randomsource.nextFloat() * 0.5F + 0.5F, yaw - ((float)Math.PI / 2F), pitch / 3.0F, j, branchCount, (double)1.0F, carvingMask, skipChecker);
                this.createTunnel(context, config, chunk, biomeAccessor, randomsource.nextLong(), aquifer, x, y, z, horizontalRadiusMultiplier, verticalRadiusMultiplier, randomsource.nextFloat() * 0.5F + 0.5F, yaw + ((float)Math.PI / 2F), pitch / 3.0F, j, branchCount, (double)1.0F, carvingMask, skipChecker);
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

    private static boolean shouldSkip(double relative, double relativeY, double relativeZ, double minrelativeY) {
        return relativeY <= minrelativeY || relative * relative + relativeY * relativeY + relativeZ * relativeZ >= (double) 1.0F;
    }
}