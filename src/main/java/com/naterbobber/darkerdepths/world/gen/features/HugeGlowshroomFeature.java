package com.naterbobber.darkerdepths.world.gen.features;

import com.mojang.serialization.Codec;
import com.naterbobber.darkerdepths.init.DDBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class HugeGlowshroomFeature extends Feature<NoneFeatureConfiguration> {

    public HugeGlowshroomFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel world = context.level();
        BlockPos pos = context.origin();
        RandomSource rand = context.random();
        BlockState belowState = world.getBlockState(pos.below());
        boolean flag = belowState.is(BlockTags.BASE_STONE_OVERWORLD) || belowState.is(DDBlocks.MOSSY_GRIMESTONE.get()) || belowState.is(DDBlocks.GRIMESTONE.get());
        boolean flag2 = world.isStateAtPosition(pos, BlockBehaviour.BlockStateBase::isAir) || world.getBlockState(pos).getMaterial().isReplaceable();
        if (flag2 && flag) {
            int height = Mth.nextInt(rand, 2, 4);
            int chanceHeight = 3;
            if (height > chanceHeight) {
                hugeGlowshroom(world, pos, height);
            } else {
                smallGlowshroom(world, pos, height);
            }
            return true;
        } else {
            return false;
        }
    }

    private void smallGlowshroom(WorldGenLevel world, BlockPos pos, int height) {
        for (int i = 0; i < height; i++) {
            for (int x = -1; x <= 1; x++) {
                for (int z = -1; z <= 1; z++) {
                    BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
                    mutable.set(pos).move(Direction.UP, i);
                    if (world.getBlockState(pos).getBlock() == DDBlocks.GLOWSHROOM.get()) world.removeBlock(pos, true);
                    if (world.getBlockState(mutable).getMaterial().isReplaceable()) {
                        this.setBlock(world, mutable, DDBlocks.GLOWSHROOM_STEM.get().defaultBlockState());
                    }
                    mutable.setWithOffset(pos, x, height, z).move(Direction.UP, i);
                    if (world.getBlockState(mutable).getMaterial().isReplaceable()) {
                        this.setBlock(world, mutable, DDBlocks.GLOWSHROOM_BLOCK.get().defaultBlockState());
                    }
                }
            }
        }
    }

    private void hugeGlowshroom(WorldGenLevel world, BlockPos pos, int height) {
        for (int j = 0; j <= height; j++) {
            for (int i = height - 2; i <= height; i++) {
                for (int x = -2; x <= 2; x++) {
                    for (int z = -2; z <= 2; z++) {
                        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
                        mutable.set(pos).move(Direction.UP, j);
                        if (world.getBlockState(mutable).getMaterial().isReplaceable()) {
                            this.setBlock(world, mutable, DDBlocks.GLOWSHROOM_STEM.get().defaultBlockState());
                        }
                        if (world.getBlockState(pos).getBlock() == DDBlocks.GLOWSHROOM.get()) world.removeBlock(pos, true);
                        boolean bl = x == -2 || x == 2;
                        boolean bl1 = z == -2 || z == 2;
                        boolean bl2 = x == -1 || x == 0 || x == 1;
                        boolean bl3 = z == -1 || z == 0 || z == 1;
                        if (!bl2 || !bl3) {
                            mutable.setWithOffset(pos, x, i, z);
                            if (world.getBlockState(mutable).getMaterial().isReplaceable()) {
                                this.setBlock(world, mutable, DDBlocks.GLOWSHROOM_BLOCK.get().defaultBlockState());
                            }
                        }
                        if (!bl || !bl1) {
                            mutable.setWithOffset(pos, x, height + 1, z);
                            if (world.getBlockState(mutable).getMaterial().isReplaceable()) {
                                this.setBlock(world, mutable, DDBlocks.GLOWSHROOM_BLOCK.get().defaultBlockState());
                            }
                        }
                    }
                }
            }
        }
    }

    public boolean checkBelowState(LevelAccessor world, BlockPos pos) {
        return world.getBlockState(pos.below()).is(DDBlocks.GRIMESTONE.get()) || world.getBlockState(pos.below()).is(DDBlocks.MOSSY_GRIMESTONE.get());
    }
}
