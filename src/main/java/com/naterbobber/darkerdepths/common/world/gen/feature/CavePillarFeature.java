package com.naterbobber.darkerdepths.common.world.gen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.structure.StructureManager;

import java.util.Random;

//<>

public class CavePillarFeature extends Feature<CavePillarConfig> {
    public CavePillarFeature(Codec<CavePillarConfig> codec) {
        super(codec);
    }

    @Override
    public boolean func_230362_a_(ISeedReader worldIn, StructureManager manager, ChunkGenerator generator, Random rand, BlockPos pos, CavePillarConfig configIn) {
        if (configIn.pointingDirection == Direction.UP && isEmptyOrWaterOrLava(worldIn, pos) && !isEmptyOrWaterOrLava(worldIn, pos.down())) {
            generatePillar(worldIn, rand, pos, configIn);
            return true;
        } else if (configIn.pointingDirection == Direction.DOWN && isEmptyOrWaterOrLava(worldIn, pos) && !isEmptyOrWaterOrLava(worldIn, pos.up())) {
            generatePillar(worldIn, rand, pos, configIn);
            return true;
        } else {
            return false;
        }
    }

    private void generatePillar(IWorld worldIn, Random rand, BlockPos pos, CavePillarConfig configIn) {
        BlockPos.Mutable newPos = pos.toMutable();
        BlockPos.Mutable oldPos = pos.toMutable();
        boolean north = true;
        boolean south = true;
        boolean west = true;
        boolean east = true;
        boolean northeast = true;
        boolean northwest = true;
        boolean southeast = true;
        boolean southwest = true;

        while (isEmptyOrWaterOrLava(worldIn, newPos)) {
            if (World.isOutsideBuildHeight(newPos)) {
                return;
            }

            worldIn.setBlockState(newPos, configIn.pillarState, 2);
            north = north && this.placeDirectionalPillarBase(worldIn, rand, (oldPos.func_239622_a_(newPos, Direction.NORTH)), configIn);
            south = south && this.placeDirectionalPillarBase(worldIn, rand, (oldPos.func_239622_a_(newPos, Direction.SOUTH)), configIn);
            west = west && this.placeDirectionalPillarBase(worldIn, rand, (oldPos.func_239622_a_(newPos, Direction.WEST)), configIn);
            east = east && this.placeDirectionalPillarBase(worldIn, rand, (oldPos.func_239622_a_(newPos, Direction.EAST)), configIn);
            northeast = northeast && this.placeDiagonalPillarBase(worldIn, rand, (oldPos.setPos(newPos).move(Direction.NORTH).move(Direction.EAST)), configIn);
            northwest = northwest && this.placeDiagonalPillarBase(worldIn, rand, (oldPos.setPos(newPos).move(Direction.NORTH).move(Direction.WEST)), configIn);
            southeast = southeast && this.placeDiagonalPillarBase(worldIn, rand, (oldPos.setPos(newPos).move(Direction.SOUTH).move(Direction.EAST)), configIn);
            southwest = southwest && this.placeDiagonalPillarBase(worldIn, rand, (oldPos.setPos(newPos).move(Direction.SOUTH).move(Direction.WEST)), configIn);
            newPos.move(configIn.pointingDirection);
        }

    }

    private boolean placeDirectionalPillarBase(IWorld worldIn, Random rand, BlockPos pos, CavePillarConfig configIn) {
        if (rand.nextInt(10) != 0) {
            worldIn.setBlockState(pos, configIn.pillarState, 2);
            return true;
        } else {
            return false;
        }
    }

    private boolean placeDiagonalPillarBase(IWorld worldIn, Random rand, BlockPos pos, CavePillarConfig configIn) {
        if (rand.nextInt(5) != 0) {
            worldIn.setBlockState(pos, configIn.pillarState, 2);
            return true;
        } else {
            return false;
        }
    }

    public static boolean isEmptyOrWaterOrLava(IWorld worldIn, BlockPos pos) {
        return worldIn.isAirBlock(pos) || worldIn.getBlockState(pos).isIn(Blocks.WATER) || worldIn.getBlockState(pos).isIn(Blocks.LAVA);
    }
}