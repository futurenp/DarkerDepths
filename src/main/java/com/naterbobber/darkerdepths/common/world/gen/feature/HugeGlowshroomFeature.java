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
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.ProbabilityConfig;

import java.util.Random;

public class HugeGlowshroomFeature extends Feature<HugeGlowshroomConfig> {

    public HugeGlowshroomFeature(Codec<HugeGlowshroomConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(ISeedReader world, ChunkGenerator generator, Random rand, BlockPos pos, HugeGlowshroomConfig config) {
        if (world.getBlockState(pos.down()) == DDBlocks.GLOWSPURS.get().getDefaultState() || world.getBlockState(pos.down()) == DDBlocks.GLOWSHROOM.get().getDefaultState() || world.isAirBlock(pos.down()) || world.getBlockState(pos.down()) == DDBlocks.GLOWSHROOM_BLOCK.get().getDefaultState() || !world.isAirBlock(pos.up())) {
            return false;
        } else {
            int height = MathHelper.nextInt(rand, 2, 5);
            int chanceHeight = 3;
            if (height > chanceHeight) {
                for (int j = 0; j < height; j++) {
                for (int i = height - 2; i <= height; i++) {
                    for (int x = -2; x <= 2; x++) {
                        for (int z = -2; z <= 2; z++) {
                            BlockPos.Mutable mutable = new BlockPos.Mutable();
                            mutable.setPos(pos).move(Direction.UP, j);
                            this.setBlockState(world, mutable, config.stem);
                            boolean bl = x == -2 || x == 2;
                            boolean bl1 = z == -2 || z == 2;
                            boolean bl2 = x == -1 || x == 0 || x == 1;
                            boolean bl3 = z == -1 || z == 0 || z == 1;
                            if (!bl2 || !bl3) {
                                mutable.setAndOffset(pos, x, i, z);
                                this.setBlockState(world, mutable, config.cap);
                            }
                            if (!bl || !bl1) {
                                mutable.setAndOffset(pos, x, i + 1, z);
                                this.setBlockState(world, mutable, config.cap);
                                }
                            }
                        }
                    }
                }
            }
             else {
                 for (int i = 0; i <= height; i++) {
                     for (int x = -1; x <= 1; x++) {
                         for (int z = -1; z <= 1; z++) {
                             BlockPos.Mutable mutable = new BlockPos.Mutable();
                             mutable.setPos(pos).move(Direction.UP, i);
                             this.setBlockState(world, mutable, config.stem);
                             mutable.setAndOffset(pos, x, height, z).move(Direction.UP, i);
                             this.setBlockState(world, mutable, config.cap);
                             }
                         }
                    }
                }
            }
            return true;
        }
}
