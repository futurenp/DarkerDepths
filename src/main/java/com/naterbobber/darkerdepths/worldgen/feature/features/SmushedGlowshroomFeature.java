package com.naterbobber.darkerdepths.worldgen.feature.features;

import com.mojang.serialization.Codec;
import com.naterbobber.darkerdepths.block.DDBlockStateProperties;
import com.naterbobber.darkerdepths.block.custom.GlowshroomBlock;
import com.naterbobber.darkerdepths.init.DDBlocks;
import com.naterbobber.darkerdepths.util.DDTags;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import java.util.stream.IntStream;

public class SmushedGlowshroomFeature extends Feature<NoneFeatureConfiguration> {

    public SmushedGlowshroomFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel world = context.level();
        BlockPos pos = context.origin();
        RandomSource rand = context.random();
        BlockState belowState = world.getBlockState(pos.below());

        if (!belowState.is(DDTags.Blocks.HUGE_GLOWSHROOM_GROWABLE)) {
            return false;
        }

        int height = Mth.nextInt(rand, 1, 4);

        boolean canPlace = true;
        for (int i = 0; i <= height; i++) {
            BlockPos checkPos = pos.above(i);
            BlockState state = world.getBlockState(checkPos);

            boolean replaceable = (state.isAir()
                    || state.is(DDBlocks.GLOWSHROOM.get())
                    || state.canBeReplaced())
                    && !state.is(Blocks.LAVA);

            if (!replaceable) {
                canPlace = false;
                break;
            }
        }

        if (canPlace) {
            generateGlowshroom(world, pos, height);
            return true;
        } else {
            return false;
        }
    }

    private void generateGlowshroom(WorldGenLevel world, BlockPos pos, int height) {
        for (int i = 0; i < height; i++) {
            BlockPos stalkPos = pos.above(i);
            if (world.getBlockState(stalkPos).canBeReplaced()) {
                this.setBlock(world, stalkPos, DDBlocks.GLOWSHROOM_STEM.get().defaultBlockState());
            }
        }

        for (int i = 0; i <= height; i++) {
            BlockPos stalkPos = pos.above(i);
            if (world.getBlockState(stalkPos).canBeReplaced()) {

                BlockState state;
                if(i == height && world.getRandom().nextFloat() < 0.5F) {
                    state = DDBlocks.GLOWSHROOM_HEART.get().defaultBlockState();
                } else {
                    state = DDBlocks.GLOWSHROOM_STEM.get().defaultBlockState();
                }

                setBlock(world, stalkPos, state);
            }
        }

        BlockPos capBase = pos.above(height + 1);

        int[][][] glowshroomBlockMap = {
                {
                        {0, 1, 1, 1, 0},
                        {1, 0, 0, 0, 1},
                        {1, 0, 0, 0, 1},
                        {1, 0, 0, 0, 1},
                        {0, 1, 1, 1, 0}
                },
                {
                        {0, 2, 1, 2, 0},
                        {2, 1, 1, 1, 2},
                        {1, 1, 1, 1, 1},
                        {2, 1, 1, 1, 2},
                        {0, 2, 1, 2, 0}
                }
        };

        for (int y = -1; y < 1; y++) {
            for (int x = -2; x <= 2; x++) {
                for (int z = -2; z <= 2; z++) {
                    int placement = glowshroomBlockMap[y + 1][x + 2][z + 2];
                    doPlace(world, capBase, x, y, z, placement);
                }
            }
        }

        decorate(world, capBase);
    }

    private void doPlace(WorldGenLevel world, BlockPos capBase, int x, int y, int z, int placement) {
        var placePos = capBase.offset(x, y, z);
        if(!world.getBlockState(placePos).is(BlockTags.REPLACEABLE) || placement == 0) {
            return;
        }

        if(placement == 1) {
            setBlock(world, placePos, DDBlocks.GLOWSHROOM_BLOCK.get().defaultBlockState());
        } else if(placement == 2 && world.getRandom().nextFloat() > 0.9F) {
            setBlock(world, placePos, DDBlocks.GLOWSHROOM_BLOCK.get().defaultBlockState());
        }
    }

    private void decorate(WorldGenLevel world, BlockPos capBase){
        int x, y, z;
        int maxGlowshrooms = 2;
        int currentGlowshrooms = 0;
        int maxGlimmeringVines = 3;
        int currentGlimmeringVines = 0;

        var random = world.getRandom();
        for(int i = 0; i < 15; i++) {
            x = random.nextInt(-2, 2);
            y = random.nextInt(-1, 2);
            z = random.nextInt(-2, 2);

            var pos = capBase.offset(x, y == -1 ? -2 : y, z);

            BlockState state;

            if(world.getBlockState(pos).is(BlockTags.AIR)) {
                if(world.getBlockState(pos.above()).is(DDBlocks.GLOWSHROOM_BLOCK.get()) && currentGlowshrooms < maxGlowshrooms) {
                    currentGlowshrooms++;
                    state = DDBlocks.GLIMMERING_VINES.get().defaultBlockState();
                } else if (world.getBlockState(pos.below()).is(DDBlocks.GLOWSHROOM_BLOCK.get()) &&  currentGlimmeringVines < maxGlimmeringVines) {
                    state = DDBlocks.GLOWSHROOM.get().defaultBlockState().setValue(DDBlockStateProperties.GLOWSHROOM_CLUSTERS, random.nextInt(1, 4));
                    currentGlimmeringVines++;
                } else return;

                setBlock(world, pos, state);
            }
        }

    }
}
