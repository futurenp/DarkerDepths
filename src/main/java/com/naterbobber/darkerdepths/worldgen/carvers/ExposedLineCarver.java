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
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.CarvingMask;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.Aquifer;
import net.minecraft.world.level.levelgen.carver.CarverConfiguration;
import net.minecraft.world.level.levelgen.carver.CarvingContext;
import net.minecraft.world.level.levelgen.carver.WorldCarver;
import org.apache.commons.lang3.mutable.MutableBoolean;

import java.util.function.Function;

public class ExposedLineCarver extends WorldCarver<BlockCarverConfiguration> {
    public ExposedLineCarver(Codec<BlockCarverConfiguration> codec) {
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

            for(int j2 = k; j2 <= l; ++j2) {
                int k2 = chunkpos.getBlockX(j2);
                double d3 = ((double)k2 + (double)0.5F - x) / horizontalRadius;

                for(int l2 = l1; l2 <= i2; ++l2) {
                    int i3 = chunkpos.getBlockZ(l2);
                    double d4 = ((double)i3 + (double)0.5F - z) / horizontalRadius;

                    if (!(d3 * d3 + d4 * d4 >= 1.0D)) {
                        MutableBoolean mutableboolean = new MutableBoolean(false);

                        for(int j3 = k1; j3 > i1; --j3) {
                            double d5 = ((double)j3 - (double)0.5F - y) / verticalRadius;
                            if (!skipChecker.shouldSkip(context, d3, d5, d4, j3) && (!carvingMask.get(j2, j3, l2) || isDebugEnabled(config))) {
                                blockpos$mutableblockpos.set(k2, j3, i3);
                                flag |= this.carveBlock(context, config, chunk, biomeAccessor, carvingMask, blockpos$mutableblockpos, blockpos$mutableblockpos1, aquifer, mutableboolean);
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
        BlockState blockstate = chunk.getBlockState(pos);

        // Don't replace blocks that are already air or fluid
        if (blockstate.isAir() || !blockstate.getFluidState().isEmpty()) {
            return false;
        }

        // Must be a replaceable block per the config
        if (!this.canReplaceBlock(config, blockstate) && !isDebugEnabled(config)) {
            return false;
        }

        // --- EXPOSURE LOGIC ---
        // Check all 6 adjacent sides to see if this block borders air or fluid
        boolean isExposed = false;
        for (Direction dir : Direction.values()) {
            checkPos.setWithOffset(pos, dir);

            // Prevent checking outside generation height bounds
            if (checkPos.getY() < context.getMinGenY() || checkPos.getY() >= context.getMinGenY() + context.getGenDepth()) {
                continue;
            }

            BlockState neighborState = chunk.getBlockState(checkPos);
            if (neighborState.isAir() || !neighborState.getFluidState().isEmpty()) {
                isExposed = true;
                break;
            }
        }

        // If completely buried inside solid rock, skip it
        if (!isExposed) {
            return false;
        }

        BlockState placeState = config.placeableBlock.defaultBlockState();
        if (placeState == null) {
            return false;
        }

        chunk.setBlockState(pos, placeState, false);
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

            WorldCarver.CarveSkipChecker skipChecker = (p_159202_, p_159203_, p_159204_, p_159205_, p_159206_) -> shouldSkip(p_159203_, p_159204_, p_159205_, d5);

            for(int k1 = 0; k1 < 1; ++k1) { // Reduced standard loops slightly to keep it line-focused
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

    // --- THICKNESS ADJUSTMENT ---
    // Scaled down to ensure the carver generates narrow lines/veins
    protected float getThickness(RandomSource random) {
        float f = random.nextFloat() * 1.5F + 0.5F;
        if (random.nextInt(10) == 0) {
            f *= random.nextFloat() * random.nextFloat() * 1.2F + 1.0F;
        }
        return f;
    }

    @Override
    public int getRange() {
        return 10;
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

    private static boolean shouldSkip(double relative, double relativeY, double relativeZ, double minrelativeY) {
        return relativeY <= minrelativeY || relative * relative + relativeY * relativeY + relativeZ * relativeZ >= 1.0D;
    }
}