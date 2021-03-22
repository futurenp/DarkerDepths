package com.naterbobber.darkerdepths.common.world.gen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.StructureManager;

import java.util.Random;

//<>

public class StonePillarFeature extends Feature<NoFeatureConfig> {
    public StonePillarFeature(Codec<NoFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean func_230362_a_(ISeedReader worldIn, StructureManager manager, ChunkGenerator generator, Random rand, BlockPos pos, NoFeatureConfig configIn) {
        if (worldIn.isAirBlock(pos) && !worldIn.isAirBlock(pos.up())) {
            BlockPos.Mutable oldPos = pos.toMutable();
            BlockPos.Mutable newPos = pos.toMutable();
            boolean north = true;
            boolean south = true;
            boolean west = true;
            boolean east = true;

            while (isEmptyOrWaterOrLava(worldIn, oldPos)) {
                if (World.isOutsideBuildHeight(oldPos)) {
                    return true;
                }

                worldIn.setBlockState(oldPos, Blocks.DIAMOND_BLOCK.getDefaultState(), 2);
                north = north && this.stopOrPlaceStone(worldIn, rand, (newPos.func_239622_a_(oldPos, Direction.NORTH)));
                south = south && this.stopOrPlaceStone(worldIn, rand, (newPos.func_239622_a_(oldPos, Direction.SOUTH)));
                west = west && this.stopOrPlaceStone(worldIn, rand, (newPos.func_239622_a_(oldPos, Direction.WEST)));
                east = east && this.stopOrPlaceStone(worldIn, rand, (newPos.func_239622_a_(oldPos, Direction.EAST)));
                oldPos.move(Direction.DOWN);

            }

            oldPos.move(Direction.UP);
            this.tryPlacingStone(worldIn, rand, newPos.func_239622_a_(oldPos, Direction.NORTH));
            this.tryPlacingStone(worldIn, rand, newPos.func_239622_a_(oldPos, Direction.SOUTH));
            this.tryPlacingStone(worldIn, rand, newPos.func_239622_a_(oldPos, Direction.WEST));
            this.tryPlacingStone(worldIn, rand, newPos.func_239622_a_(oldPos, Direction.EAST));
            oldPos.move(Direction.UP);
            BlockPos.Mutable mutable = new BlockPos.Mutable();

            for (int x = -3; x < 4; x++) {
                for (int z = -3; z < 4; z++) {
                    int radius = MathHelper.abs(x) * MathHelper.abs(z);
                    if (rand.nextInt(10) < 10 - radius) {
                        mutable.setPos(oldPos.add(x, 0, z));
                        int index = 3;

                        while (isEmptyOrWaterOrLava(worldIn, newPos.func_239622_a_(mutable, Direction.DOWN))) {
                            mutable.move(Direction.DOWN);
                            --index;
                            if (index <= 0) {
                                break;
                            }
                        }
                    }
                }
            }

            return true;
        } else {
            return false;
        }
    }

    //base
    private void tryPlacingStone(IWorld worldIn, Random rand, BlockPos pos) {
        if (rand.nextInt(10) != 0) {
            BlockPos.Mutable pos1 = pos.toMutable();
            worldIn.setBlockState(pos1, Blocks.DIAMOND_BLOCK.getDefaultState(), 2);
            worldIn.setBlockState(pos1.west(), Blocks.EMERALD_BLOCK.getDefaultState(), 2);
            worldIn.setBlockState(pos1.east(), Blocks.EMERALD_BLOCK.getDefaultState(), 2);
            worldIn.setBlockState(pos1.north(), Blocks.EMERALD_BLOCK.getDefaultState(), 2);
            worldIn.setBlockState(pos1.south(), Blocks.EMERALD_BLOCK.getDefaultState(), 2);
            worldIn.setBlockState(pos1.up(), Blocks.EMERALD_BLOCK.getDefaultState(), 2);
            worldIn.setBlockState(pos1.up(2), Blocks.EMERALD_BLOCK.getDefaultState(), 2);
            worldIn.setBlockState(pos1.up(3), Blocks.EMERALD_BLOCK.getDefaultState(), 2);
            worldIn.setBlockState(pos1.up(4), Blocks.EMERALD_BLOCK.getDefaultState(), 2);
        }
    }

    //top
    private boolean stopOrPlaceStone(IWorld worldIn, Random rand, BlockPos pos) {
        if (rand.nextInt(5) != 0) {
            BlockPos.Mutable pos1 = pos.toMutable();
            worldIn.setBlockState(pos1, Blocks.DIAMOND_BLOCK.getDefaultState(), 2);
            worldIn.setBlockState(pos1.west(), Blocks.IRON_BLOCK.getDefaultState(), 2);
            worldIn.setBlockState(pos1.east(), Blocks.IRON_BLOCK.getDefaultState(), 2);
            worldIn.setBlockState(pos1.north(), Blocks.IRON_BLOCK.getDefaultState(), 2);
            worldIn.setBlockState(pos1.south(), Blocks.IRON_BLOCK.getDefaultState(), 2);
            return true;
        } else {
            return false;
        }
    }

    public static boolean isEmptyOrWaterOrLava(IWorld worldIn, BlockPos pos) {
        return worldIn.isAirBlock(pos) || worldIn.getBlockState(pos).isIn(Blocks.WATER) || worldIn.getBlockState(pos).isIn(Blocks.LAVA);
    }
}