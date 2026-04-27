package com.naterbobber.darkerdepths.worldgen.feature.features;

import com.mojang.serialization.Codec;
import com.naterbobber.darkerdepths.block.custom.GlowshroomBlock;
import com.naterbobber.darkerdepths.init.DDBlocks;
import com.naterbobber.darkerdepths.util.DDTags;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
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
            if(height <= 3) {
                smallGlowshroom(world, pos, height);
            } else {
                hugeGlowshroom(world, pos, height);
            }
            return true;
        } else {
            return false;
        }
    }

    private void smallGlowshroom(WorldGenLevel world, BlockPos pos, int height) {
        for (int i = 0; i < height; i++) {
            BlockPos stalkPos = pos.above(i);
            if (world.getBlockState(stalkPos).canBeReplaced()) {
                this.setBlock(world, stalkPos, DDBlocks.GLOWSHROOM_STEM.get().defaultBlockState());
            }
        }

        BlockPos capBase = pos.above(height - 1);

        //0 = nothing, 1 = glowshroom block, 2 = glimmering vines chance, 3 = glimmering vines / glowshroom block chance, 4 = glowshroom cluster chance
        int[][][] smallGlowshroomMap = {
                {
                        {0, 2, 0},
                        {2, 0, 2},
                        {0, 2, 0}
                },
                {
                        {3, 1, 3},
                        {1, 1, 1},
                        {3, 1, 3}
                },
                {
                        {1, 1, 1},
                        {1, 1, 1},
                        {1, 1, 1}
                },
                {
                        {1, 1, 1},
                        {1, 1, 1},
                        {1, 1, 1}
                },
                {
                        {4, 4, 4},
                        {4, 4, 4},
                        {4, 4, 4}
                }
        };

        for (int y = 0; y < 5; y++) {
            for (int x = -1; x <= 1; x++) {
                for (int z = -1; z <= 1; z++) {

                    int blockIndex = smallGlowshroomMap[y][x + 1][z + 1];
                    BlockPos capPos = capBase.offset(x, y, z);
                    if(world.getBlockState(capPos).canBeReplaced()) {
                        switch (blockIndex) {
                            case 1:
                                this.setBlock(world, capPos, DDBlocks.GLOWSHROOM_BLOCK.get().defaultBlockState());
                                break;
                            case 2:
                                if(!world.getBlockState(capPos).is(Blocks.WATER)){
                                    int glimmeringVinesRandom = (int) (Math.random() * 8);
                                    if (glimmeringVinesRandom == 0) {
                                        this.setBlock(world, capPos, DDBlocks.GLIMMERING_VINES.get().defaultBlockState());
                                    }
                                }
                                break;
                            case 3:
                                int glowVineRandom = (int) (Math.random() * 4);
                                if(world.getBlockState(capPos).is(Blocks.WATER)) {
                                    glowVineRandom = 1;
                                }
                                if (glowVineRandom == 0) {
                                    this.setBlock(world, capPos, DDBlocks.GLIMMERING_VINES.get().defaultBlockState());
                                } else {
                                    this.setBlock(world, capPos, DDBlocks.GLOWSHROOM_BLOCK.get().defaultBlockState());
                                }
                                break;
                            case 4:
                                int glowshroomRandom = (int) (Math.random() * 9);
                                int blockStateRandom = (int) (Math.random() * 3 + 1);
                                if (glowshroomRandom == 0) {
                                    if(world.getBlockState(capPos).is(Blocks.WATER)){
                                        this.setBlock(world, capPos, DDBlocks.GLOWSHROOM.get().defaultBlockState().setValue(GlowshroomBlock.GLOWSHROOM_CLUSTERS, blockStateRandom).setValue(GlowshroomBlock.WATERLOGGED, true));
                                    } else {
                                        this.setBlock(world, capPos, DDBlocks.GLOWSHROOM.get().defaultBlockState().setValue(GlowshroomBlock.GLOWSHROOM_CLUSTERS, blockStateRandom));
                                    }
                                }
                                break;
                            default:
                                break;
                        }
                    }
                }
            }
        }
    }

    private void hugeGlowshroom(WorldGenLevel world, BlockPos pos, int height) {
        for (int i = 0; i < height; i++) {
            BlockPos stalkPos = pos.above(i);
            if (world.getBlockState(stalkPos).canBeReplaced()) {
                this.setBlock(world, stalkPos, DDBlocks.GLOWSHROOM_STEM.get().defaultBlockState());
            }
        }

        BlockPos capBase = pos.above(height - 3);

        //0 = nothing, 1 = glowshroom block, 2 = glimmering vines chance, 3 = glimmering vines / glowshroom block chance, 4 = glowshroom cluster chance, 5 = shroomlight
        int[][][] smallGlowshroomMap = {
                {
                        {0, 2, 2, 2, 0},
                        {2, 0, 0, 0, 2},
                        {2, 0, 0, 0, 2},
                        {2, 0, 0, 0, 2},
                        {0, 2, 2, 2, 0}
                },
                {
                        {3, 1, 1, 1, 3},
                        {1, 0, 0, 0, 1},
                        {1, 0, 0, 0, 1},
                        {1, 0, 0, 0, 1},
                        {3, 1, 1, 1, 3}
                },
                {
                        {1, 1, 1, 1, 1},
                        {1, 0, 0, 0, 1},
                        {1, 0, 0, 0, 1},
                        {1, 0, 0, 0, 1},
                        {1, 1, 1, 1, 1}
                },
                {
                        {1, 1, 1, 1, 1},
                        {1, 0, 0, 0, 1},
                        {1, 0, 5, 0, 1},
                        {1, 0, 0, 0, 1},
                        {1, 1, 1, 1, 1}
                },
                {
                        {4, 1, 1, 1, 4},
                        {1, 1, 1, 1, 1},
                        {1, 1, 1, 1, 1},
                        {1, 1, 1, 1, 1},
                        {4, 1, 1, 1, 4}
                },
                {
                        {0, 4, 4, 4, 0},
                        {4, 4, 4, 4, 4},
                        {4, 4, 4, 4, 4},
                        {4, 4, 4, 4, 4},
                        {0, 4, 4, 4, 0}
                }
        };

        for (int y = 0; y < 6; y++) {
            for (int x = -2; x <= 2; x++) {
                for (int z = -2; z <= 2; z++) {

                    int blockIndex = smallGlowshroomMap[y][x + 2][z + 2];
                    BlockPos capPos = capBase.offset(x, y, z);
                    if(world.getBlockState(capPos).canBeReplaced()){
                        switch (blockIndex) {
                            case 1:
                                this.setBlock(world, capPos, DDBlocks.GLOWSHROOM_BLOCK.get().defaultBlockState());
                                break;
                            case 2:
                                if(!world.getBlockState(capPos).is(Blocks.WATER)) {
                                    int glimmeringVinesRandom = (int) (Math.random() * 8);
                                    if (glimmeringVinesRandom == 0) {
                                        this.setBlock(world, capPos, DDBlocks.GLIMMERING_VINES.get().defaultBlockState());
                                    }
                                }
                                break;
                            case 3:
                                int glowVineRandom = (int) (Math.random() * 4);
                                if(world.getBlockState(capPos).is(Blocks.WATER)) {
                                    glowVineRandom = 1;
                                }
                                if (glowVineRandom == 0) {
                                    this.setBlock(world, capPos, DDBlocks.GLIMMERING_VINES.get().defaultBlockState());
                                } else {
                                    this.setBlock(world, capPos, DDBlocks.GLOWSHROOM_BLOCK.get().defaultBlockState());
                                }
                                break;
                            case 4:
                                int glowshroomRandom = (int) (Math.random() * 18);
                                int blockStateRandom = (int) (Math.random() * 3 + 1);
                                if (glowshroomRandom == 0) {
                                    if(world.getBlockState(capPos).is(Blocks.WATER)){
                                        this.setBlock(world, capPos, DDBlocks.GLOWSHROOM.get().defaultBlockState().setValue(GlowshroomBlock.GLOWSHROOM_CLUSTERS, blockStateRandom).setValue(GlowshroomBlock.WATERLOGGED, true));
                                    } else {
                                        this.setBlock(world, capPos, DDBlocks.GLOWSHROOM.get().defaultBlockState().setValue(GlowshroomBlock.GLOWSHROOM_CLUSTERS, blockStateRandom));
                                    }
                                }
                                break;
                            case 5:
                                //this.setBlock(world, capPos, Blocks.SHROOMLIGHT.defaultBlockState());
                                this.setBlock(world, capPos, DDBlocks.GLOWSHROOM_HEART.get().defaultBlockState());
                                break;
                            default:
                                break;
                        }
                    }
                }
            }
        }
    }
}
