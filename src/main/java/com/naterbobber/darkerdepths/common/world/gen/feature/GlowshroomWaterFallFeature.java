package com.naterbobber.darkerdepths.common.world.gen.feature;

import com.mojang.serialization.Codec;
import com.naterbobber.darkerdepths.core.registries.DDBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;

public class GlowshroomWaterFallFeature extends Feature<HugeGlowshroomConfig> {

    public GlowshroomWaterFallFeature(Codec<HugeGlowshroomConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(ISeedReader world, ChunkGenerator generator, Random rand, BlockPos pos, HugeGlowshroomConfig config) {
        if (world.getBlockState(pos).getBlock() == DDBlocks.GRIMESTONE.get() && checkState(world, pos)) {
            int height = 1;
            int size = 2;
            BlockPos.Mutable mutable = new BlockPos.Mutable();
            this.generatePool(world, pos, mutable, rand, size, height, config);
            return true;
        } else {
            return false;
        }
    }

    private boolean canGenerate(IWorld world, BlockPos pos) {
        return world.hasBlockState(pos, GlowshroomWaterFallFeature::canGenerate);
    }

    private static boolean canGenerate(BlockState state) {
        return state.isAir();
    }

    private void generatePool(IWorld world, BlockPos pos, BlockPos.Mutable mutable, Random rand, int size, int height, HugeGlowshroomConfig config) {
        int size2 = 3;
        for (int xx = -size2; xx <= size2; xx++) {
            for (int zz= -size2; zz <= size2; zz++) {
                BlockPos.Mutable mutable1 = new BlockPos.Mutable();
                mutable1.setAndOffset(pos, xx, 1, zz);
                boolean xl = xx == -size2;
                boolean xl1 = xx == size2;
                boolean xl2 = zz == -size2;
                boolean xl3 = zz == size2;
                boolean xl4 = xl || xl1;
                boolean xl5 = xl2 || xl3;
                if (!xl4 || !xl5) {
                    this.setBlockState(world, mutable1, config.cap);
                    if (rand.nextInt(5) == 0 && rand.nextBoolean()) {
                        boolean xl6 = xx == 0;
                        boolean xl7 = zz == 0;
                        if (xl6 || xl7 && rand.nextInt(5) == 0) {
                            world.setBlockState(mutable1, Blocks.WATER.getDefaultState().getBlockState(), 2);
                            world.getPendingFluidTicks().scheduleTick(mutable1, Fluids.WATER.getFluid(), 0);
                        }
                    }
                    for (int x = -size; x <= size; x++) {
                        for (int z = -size; z <= size; z++) {
                            mutable.setAndOffset(pos, x, 0, z);
                            boolean bl = x == -size;
                            boolean bl1 = x == size;
                            boolean bl2 = z == -size;
                            boolean bl3 = z == size;
                            boolean bl4 = bl || bl1;
                            boolean bl5 = bl2 || bl3;
                            if (!bl4 || !bl5) {
                                this.setBlockState(world, mutable, config.cap);
                                this.setBlockState(world, mutable.up(), Blocks.WATER.getDefaultState());
                            }
                        }
                    }
                }
            }
        }
    }

    private boolean checkState(IWorld world, BlockPos pos) {
        return world.isAirBlock(pos.north(3)) || world.isAirBlock(pos.south(3)) || world.isAirBlock(pos.east(3)) || world.isAirBlock(pos.west(3));
    }

//    private void generateWaterPool(IWorld world, BlockPos.Mutable mutable, Random rand, BlockPos pos, int size, int height) {
//        for (int x = -size; x <= size; x++) {
//            for (int z = -size; z <= size; z++) {
//                for (int i = 0; i <= height; i++) {
//                    mutable.setAndOffset(pos, x, i, z);
//                    this.setBlockState(world, mutable, DDBlocks.GLOWSHROOM_BLOCK.get().getDefaultState());
//                    mutable.setAndOffset(pos, x, i + 1, z);
//                    boolean bl = x == -size;
//                    boolean bl1 = x == size;
//                    boolean bl2 = z == -size;
//                    boolean bl3 = z == size;
//                    boolean bl4 = bl || bl1;
//                    boolean bl5 = bl2 || bl3;
//                    if (!bl4 || !bl5) {
//                        this.setBlockState(world, mutable, DDBlocks.GLOWSHROOM_BLOCK.get().getDefaultState());
//                    }
//                    int size2 = 3;
//                    for (int xx = -size2; xx <= size2; xx++) {
//                        for (int zz = -size2; zz <= size2; zz++) {
//                            mutable.setAndOffset(pos, xx, i + 1, zz);
//                            this.setBlockState(world, mutable, Blocks.WATER.getDefaultState());
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
}



