package com.naterbobber.darkerdepths.common.world.gen.feature;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mojang.serialization.Codec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.math.shapes.BitSetVoxelShapePart;
import net.minecraft.util.math.shapes.VoxelShapePart;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldWriter;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.IWorldGenerationBaseReader;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.foliageplacer.FoliagePlacer;

import java.util.Comparator;
import java.util.List;
import java.util.OptionalInt;
import java.util.Random;
import java.util.Set;

//<>

public class DDTreeFeature extends Feature<BaseTreeFeatureConfig> {
    public DDTreeFeature(Codec<BaseTreeFeatureConfig> codec) {
        super(codec);
    }

    public static boolean canTreeReplace(IWorldGenerationBaseReader readerIn, BlockPos pos) {
        return isReplaceableAt(readerIn, pos) || readerIn.hasBlockState(pos, state -> state.isIn(BlockTags.LOGS));
    }

    private static boolean isVine(IWorldGenerationBaseReader readerIn, BlockPos pos) {
        return readerIn.hasBlockState(pos, state -> state.isIn(Blocks.VINE));
    }

    private static boolean isWaterAt(IWorldGenerationBaseReader readerIn, BlockPos pos) {
        return readerIn.hasBlockState(pos, state -> state.isIn(Blocks.WATER));
    }

    public static boolean isAirOrLeavesAt(IWorldGenerationBaseReader readerIn, BlockPos pos) {
        return readerIn.hasBlockState(pos, state -> state.isAir() || state.isIn(BlockTags.LEAVES));
    }

    private static boolean isDirtOrFarmlandAt(IWorldGenerationBaseReader readerIn, BlockPos pos) {
        return readerIn.hasBlockState(pos, state -> {
            Block block = state.getBlock();
            return isDirt(block) || block == Blocks.FARMLAND;
        });
    }

    private static boolean isTallPlantAt(IWorldGenerationBaseReader readerIn, BlockPos pos) {
        return readerIn.hasBlockState(pos, state -> {
            Material material = state.getMaterial();
            return material == Material.TALL_PLANTS;
        });
    }

    public static void setBlockStateWithoutUpdatingNeighbors(IWorldWriter readerIn, BlockPos pos, BlockState state) {
        readerIn.setBlockState(pos, state, 19);
    }

    public static boolean isReplaceableAt(IWorldGenerationBaseReader readerIn, BlockPos pos) {
        return isAirOrLeavesAt(readerIn, pos) || isTallPlantAt(readerIn, pos) || isWaterAt(readerIn, pos);
    }

    /**
     * Called when placing the tree feature.
     */
    private boolean place(IWorldGenerationReader readerIn, Random random, BlockPos pos, Set<BlockPos> trunkReplacer, Set<BlockPos> foliageReplacer, MutableBoundingBox boundingBoxIn, BaseTreeFeatureConfig configIn) {
        int height = configIn.trunkPlacer.func_236917_a_(random);
        int randomHeight = configIn.foliagePlacer.func_230374_a_(random, height, configIn);
        int radius = height - randomHeight;
        int randomRadius = configIn.foliagePlacer.func_230376_a_(random, radius);
        if (pos.getY() >= 1 && pos.getY() + height + 1 <= 256) {
            OptionalInt minClippedHeight = configIn.minimumSize.func_236710_c_();
            int topPosition = this.getTopPosition(readerIn, height, pos, configIn);
            if (topPosition >= height || minClippedHeight.isPresent() && topPosition >= minClippedHeight.getAsInt()) {
                List<FoliagePlacer.Foliage> foliages = configIn.trunkPlacer.func_230382_a_(readerIn, random, topPosition, pos, trunkReplacer, boundingBoxIn, configIn);
                foliages.forEach(foliage -> {
                    configIn.foliagePlacer.func_236752_a_(readerIn, random, configIn, topPosition, foliage, randomHeight, randomRadius, foliageReplacer, boundingBoxIn);
                });
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    private int getTopPosition(IWorldGenerationBaseReader readerIn, int height, BlockPos pos, BaseTreeFeatureConfig config) {
        BlockPos.Mutable mutable = new BlockPos.Mutable();

        for(int y = 0; y <= height + 1; ++y) {
            int radius = config.minimumSize.func_230369_a_(height, y);

            for(int x = -radius; x <= radius; ++x) {
                for(int z = -radius; z <= radius; ++z) {
                    mutable.setAndOffset(pos, x, y, z);
                    if (!canTreeReplace(readerIn, mutable) || !config.ignoreVines && isVine(readerIn, mutable)) {
                        return y - 2;
                    }
                }
            }
        }

        return height;
    }

    protected void setBlockState(IWorldWriter world, BlockPos pos, BlockState state) {
        setBlockStateWithoutUpdatingNeighbors(world, pos, state);
    }

    @Override
    public final boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, BaseTreeFeatureConfig config) {
        Set<BlockPos> trunkPositions = Sets.newHashSet();
        Set<BlockPos> foliagePositions = Sets.newHashSet();
        Set<BlockPos> decorationPositions = Sets.newHashSet();
        MutableBoundingBox box = MutableBoundingBox.getNewBoundingBox();
        boolean flag = this.place(reader, rand, pos, trunkPositions, foliagePositions, box, config);
        if (box.minX <= box.maxX && flag && !trunkPositions.isEmpty()) {
            if (!config.decorators.isEmpty()) {
                List<BlockPos> list = Lists.newArrayList(trunkPositions);
                List<BlockPos> list1 = Lists.newArrayList(foliagePositions);
                list.sort(Comparator.comparingInt(Vector3i::getY));
                list1.sort(Comparator.comparingInt(Vector3i::getY));
                config.decorators.forEach((decorator) -> {
                    decorator.func_225576_a_(reader, rand, list, list1, decorationPositions, box);
                });
            }

            VoxelShapePart voxelshapepart = this.placeLogsAndLeaves(reader, box, trunkPositions, decorationPositions);
            Template.func_222857_a(reader, 3, voxelshapepart, box.minX, box.minY, box.minZ);
            return true;
        } else {
            return false;
        }
    }

    private VoxelShapePart placeLogsAndLeaves(IWorld worldIn, MutableBoundingBox box, Set<BlockPos> trunkPositions, Set<BlockPos> decorationPositions) {
        List<Set<BlockPos>> positions = Lists.newArrayList();
        VoxelShapePart shapePart = new BitSetVoxelShapePart(box.getXSize(), box.getYSize(), box.getZSize());

        for(int i = 0; i < 6; ++i) {
            positions.add(Sets.newHashSet());
        }

        BlockPos.Mutable mutable = new BlockPos.Mutable();

        for(BlockPos pos : Lists.newArrayList(decorationPositions)) {
            if (box.isVecInside(pos)) {
                shapePart.setFilled(pos.getX() - box.minX, pos.getY() - box.minY, pos.getZ() - box.minZ, true, true);
            }
        }

        for(BlockPos pos : Lists.newArrayList(trunkPositions)) {
            if (box.isVecInside(pos)) {
                shapePart.setFilled(pos.getX() - box.minX, pos.getY() - box.minY, pos.getZ() - box.minZ, true, true);
            }

            for(Direction direction : Direction.values()) {
                mutable.setAndMove(pos, direction);
                if (!trunkPositions.contains(mutable)) {
                    BlockState blockstate = worldIn.getBlockState(mutable);
                    if (blockstate.hasProperty(BlockStateProperties.DISTANCE_1_7)) {
                        positions.get(0).add(mutable.toImmutable());
                        setBlockStateWithoutUpdatingNeighbors(worldIn, mutable, blockstate.with(BlockStateProperties.DISTANCE_1_7, 1));
                        if (box.isVecInside(mutable)) {
                            shapePart.setFilled(mutable.getX() - box.minX, mutable.getY() - box.minY, mutable.getZ() - box.minZ, true, true);
                        }
                    }
                }
            }
        }

        for(int i = 1; i < 6; ++i) {
            Set<BlockPos> set = positions.get(i - 1);
            Set<BlockPos> set1 = positions.get(i);

            for(BlockPos blockpos2 : set) {
                if (box.isVecInside(blockpos2)) {
                    shapePart.setFilled(blockpos2.getX() - box.minX, blockpos2.getY() - box.minY, blockpos2.getZ() - box.minZ, true, true);
                }

                for(Direction direction1 : Direction.values()) {
                    mutable.setAndMove(blockpos2, direction1);
                    if (!set.contains(mutable) && !set1.contains(mutable)) {
                        BlockState blockstate1 = worldIn.getBlockState(mutable);
                        if (blockstate1.hasProperty(BlockStateProperties.DISTANCE_1_7)) {
                            int k = blockstate1.get(BlockStateProperties.DISTANCE_1_7);
                            if (k > i + 1) {
                                BlockState blockstate2 = blockstate1.with(BlockStateProperties.DISTANCE_1_7, i + 1);
                                setBlockStateWithoutUpdatingNeighbors(worldIn, mutable, blockstate2);
                                if (box.isVecInside(mutable)) {
                                    shapePart.setFilled(mutable.getX() - box.minX, mutable.getY() - box.minY, mutable.getZ() - box.minZ, true, true);
                                }

                                set1.add(mutable.toImmutable());
                            }
                        }
                    }
                }
            }
        }

        return shapePart;
    }
}