package com.naterbobber.darkerdepths.common.world.gen.feature;

import com.mojang.serialization.Codec;
import com.naterbobber.darkerdepths.core.registries.DDBlocks;
import net.minecraft.block.AbstractTopPlantBlock;
import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;

public class GlowVineFeature extends Feature<NoFeatureConfig> {

    public GlowVineFeature(Codec<NoFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(ISeedReader world, ChunkGenerator generator, Random rand, BlockPos pos, NoFeatureConfig config) {
        if (!canGenerate(world, pos)) {
            return false;
        } else {
            generateVines(world, pos, rand);
            return true;
        }
    }

    private boolean canGenerate(IWorld world, BlockPos pos) {
        BlockState checkState = world.getBlockState(pos.up());
        return checkState == DDBlocks.GRIMESTONE.get().getDefaultState() || checkState == DDBlocks.GLOWSHROOM_BLOCK.get().getDefaultState();
    }

    public static void generateVines(IWorld world, BlockPos pos, Random rand) {
        int k = MathHelper.nextInt(rand, 5, 10);
        for (int i = 0; i <= k; i++) {
            BlockPos.Mutable mutable = new BlockPos.Mutable();
            mutable.setPos(pos).move(Direction.DOWN, i);
            if (i == k || !world.isAirBlock(mutable.down())) {
                world.setBlockState(mutable, DDBlocks.GLOWSPIRE.get().getDefaultState(), 2);
                break;
            }
            world.setBlockState(mutable, DDBlocks.GLOWSPIRE_PLANT.get().getDefaultState(), 2);
        }
    }
}
