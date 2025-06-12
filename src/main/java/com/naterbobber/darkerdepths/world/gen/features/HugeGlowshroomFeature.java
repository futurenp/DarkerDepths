package com.naterbobber.darkerdepths.world.gen.features;

import com.mojang.serialization.Codec;
import com.naterbobber.darkerdepths.init.DDBlocks;
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
import com.naterbobber.darkerdepths.blocks.GlowshroomBlock;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.WaterFluid;

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
        boolean flag = (belowState.is(BlockTags.BASE_STONE_OVERWORLD) || belowState.is(DDBlocks.MOSSY_GRIMESTONE.get()) || belowState.is(DDBlocks.GRIMESTONE.get())) && !belowState.is(Blocks.LAVA);
        boolean flag2 = (world.isStateAtPosition(pos, BlockBehaviour.BlockStateBase::isAir) || world.getBlockState(pos).canBeReplaced()) && !world.getBlockState(pos).is(Blocks.LAVA);
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
                                        this.setBlock(world, capPos, DDBlocks.GLOWSHROOM.get().defaultBlockState().setValue(GlowshroomBlock.CLUSTERS_1_3, blockStateRandom).setValue(GlowshroomBlock.WATERLOGGED, true));
                                    } else {
                                        this.setBlock(world, capPos, DDBlocks.GLOWSHROOM.get().defaultBlockState().setValue(GlowshroomBlock.CLUSTERS_1_3, blockStateRandom));
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
                        {0, 2, 2, 2, 0}                },
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
                                        this.setBlock(world, capPos, DDBlocks.GLOWSHROOM.get().defaultBlockState().setValue(GlowshroomBlock.CLUSTERS_1_3, blockStateRandom).setValue(GlowshroomBlock.WATERLOGGED, true));
                                    } else {
                                        this.setBlock(world, capPos, DDBlocks.GLOWSHROOM.get().defaultBlockState().setValue(GlowshroomBlock.CLUSTERS_1_3, blockStateRandom));
                                    }
                                }
                                break;
                            case 5:
                                this.setBlock(world, capPos, Blocks.SHROOMLIGHT.defaultBlockState());
                                break;
                            default:
                                break;
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
