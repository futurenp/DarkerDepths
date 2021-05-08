package com.naterbobber.darkerdepths.common.world.gen.feature;

import com.mojang.serialization.Codec;
import com.naterbobber.darkerdepths.core.registries.DDBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorld;
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
    public boolean generate(ISeedReader worldIn, ChunkGenerator generator, Random rand, BlockPos pos, CavePillarConfig configIn) {
        boolean isValidDirection = configIn.pointingDirection == Direction.UP || configIn.pointingDirection == Direction.DOWN;
        if (isValidDirection) {
            if (isEmptyOrWaterOrLava(worldIn, pos)) {
                this.generateCrystalPillar(worldIn, rand, pos, configIn);
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    private void generateCrystalPillar(IWorld worldIn, Random rand, BlockPos pos, CavePillarConfig configIn) {
        this.generateMainCrystalPillar(worldIn, rand, pos, configIn);
        this.generateSideCrystalPillar(worldIn, rand, pos, Direction.NORTH, configIn);
        this.generateSideCrystalPillar(worldIn, rand, pos, Direction.SOUTH, configIn);
        this.generateSideCrystalPillar(worldIn, rand, pos, Direction.WEST, configIn);
        this.generateSideCrystalPillar(worldIn, rand, pos, Direction.EAST, configIn);
        this.generateCornerCrystalPillar(worldIn, rand, pos, Direction.NORTH, Direction.EAST, configIn);
        this.generateCornerCrystalPillar(worldIn, rand, pos, Direction.NORTH, Direction.WEST, configIn);
        this.generateCornerCrystalPillar(worldIn, rand, pos, Direction.SOUTH, Direction.EAST, configIn);
        this.generateCornerCrystalPillar(worldIn, rand, pos, Direction.SOUTH, Direction.WEST, configIn);
        this.generateCasing(worldIn, rand, pos, configIn);
    }

    private void generateCasing(IWorld worldIn, Random rand, BlockPos pos, CavePillarConfig configIn) {
        this.generateCrystalPillarCasing(worldIn, rand, pos, Direction.NORTH, Direction.EAST, 1, configIn);
        this.generateCrystalPillarCasing(worldIn, rand, pos, Direction.NORTH, Direction.EAST, 0, configIn);
        this.generateCrystalPillarCasing(worldIn, rand, pos, Direction.NORTH, Direction.EAST, -1, configIn);
        this.generateCrystalPillarCasing(worldIn, rand, pos, Direction.SOUTH, Direction.WEST, 1, configIn);
        this.generateCrystalPillarCasing(worldIn, rand, pos, Direction.SOUTH, Direction.WEST, 0, configIn);
        this.generateCrystalPillarCasing(worldIn, rand, pos, Direction.SOUTH, Direction.WEST, -1, configIn);
        this.generateCrystalPillarCasing(worldIn, rand, pos, Direction.EAST, Direction.SOUTH, 1, configIn);
        this.generateCrystalPillarCasing(worldIn, rand, pos, Direction.EAST, Direction.SOUTH, 0, configIn);
        this.generateCrystalPillarCasing(worldIn, rand, pos, Direction.EAST, Direction.SOUTH, -1, configIn);
        this.generateCrystalPillarCasing(worldIn, rand, pos, Direction.WEST, Direction.NORTH, 1, configIn);
        this.generateCrystalPillarCasing(worldIn, rand, pos, Direction.WEST, Direction.NORTH, 0, configIn);
        this.generateCrystalPillarCasing(worldIn, rand, pos, Direction.WEST, Direction.NORTH, -1, configIn);
    }

    private void generateMainCrystalPillar(IWorld worldIn, Random rand, BlockPos pos, CavePillarConfig configIn) {
        boolean isPointingUp = configIn.pointingDirection == Direction.UP;
        BlockPos.Mutable blockPos = new BlockPos.Mutable();

        for (int height = 0; height < MathHelper.nextInt(rand, 8, 13); height++) {
            blockPos.setPos(pos).move(configIn.pointingDirection, height);
            if ((isPointingUp && !isEmptyOrWaterOrLava(worldIn, blockPos.down())) || (!isPointingUp && !isEmptyOrWaterOrLava(worldIn, blockPos.up()))) {
                this.setBlockState(worldIn, blockPos, configIn.pillarState);
            }
        }
    }

    private void generateSideCrystalPillar(IWorld worldIn, Random rand, BlockPos pos, Direction side, CavePillarConfig configIn) {
        boolean isPointingUp = configIn.pointingDirection == Direction.UP;
        BlockPos.Mutable blockPos = new BlockPos.Mutable();

        for (int height = 0; height < MathHelper.nextInt(rand, 5, 10); height++) {
            blockPos.setPos(pos).move(configIn.pointingDirection, height).move(side);
            if ((isPointingUp && !isEmptyOrWaterOrLava(worldIn, blockPos.down())) || (!isPointingUp && !isEmptyOrWaterOrLava(worldIn, blockPos.up()))) {
                this.setBlockState(worldIn, blockPos, configIn.pillarState);
            }
        }
    }

    private void generateCornerCrystalPillar(IWorld worldIn, Random rand, BlockPos pos, Direction xSide, Direction zSide, CavePillarConfig configIn) {
        boolean isPointingUp = configIn.pointingDirection == Direction.UP;
        BlockPos.Mutable blockPos = new BlockPos.Mutable();

        for (int height = 0; height < MathHelper.nextInt(rand, 2, 4); height++) {
            blockPos.setPos(pos).move(configIn.pointingDirection, height).move(xSide).move(zSide);
            if ((isPointingUp && !isEmptyOrWaterOrLava(worldIn, blockPos.down())) || (!isPointingUp && !isEmptyOrWaterOrLava(worldIn, blockPos.up()))) {
                this.setBlockState(worldIn, blockPos, configIn.pillarState);
            }
        }
    }

    private void generateCrystalPillarCasing(IWorld worldIn, Random rand, BlockPos pos, Direction xSide, Direction zSide, int zOffset, CavePillarConfig configIn) {
        boolean isPointingUp = configIn.pointingDirection == Direction.UP;
        BlockPos.Mutable blockPos = new BlockPos.Mutable();

        for (int height = 0; height < MathHelper.nextInt(rand, 1, 3); height++) {
            blockPos.setPos(pos).move(configIn.pointingDirection, height).move(xSide, 2).move(zSide, zOffset);
            if ((isPointingUp && !isEmptyOrWaterOrLava(worldIn, blockPos.down())) || (!isPointingUp && !isEmptyOrWaterOrLava(worldIn, blockPos.up()))) {
                this.setBlockState(worldIn, blockPos, DDBlocks.SHALE.get().getDefaultState());
            }
        }
    }

    public static boolean isEmptyOrWaterOrLava(IWorld worldIn, BlockPos pos) {
        return worldIn.isAirBlock(pos) || worldIn.getBlockState(pos).matchesBlock(Blocks.WATER) || worldIn.getBlockState(pos).matchesBlock(Blocks.LAVA);
    }
}