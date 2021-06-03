package com.naterbobber.darkerdepths.common.world.gen.feature;

import com.mojang.serialization.Codec;
import com.naterbobber.darkerdepths.core.registries.DDBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;

import java.util.Random;

public class HugeTwistedGlowshroomFeature extends Feature<HugeGlowshroomConfig> {

    public HugeTwistedGlowshroomFeature(Codec<HugeGlowshroomConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(ISeedReader world, ChunkGenerator generator, Random rand, BlockPos pos, HugeGlowshroomConfig config) {
        BlockState checkState = world.getBlockState(pos.down());
        if (world.isAirBlock(pos.down()) || checkState == DDBlocks.GLOWSPURS.get().getDefaultState() || checkState == DDBlocks.GLOWSHROOM.get().getDefaultState()) {
            return false;
        } else {
            Direction[] randDir = {Direction.SOUTH, Direction.WEST, Direction.NORTH, Direction.WEST};
            Direction dir = Util.getRandomObject(randDir, rand);
            int size = 1;
            int k = MathHelper.nextInt(rand, 5, 8);
            int h = MathHelper.nextInt(rand, 3, 4);
            for (int i = 0; i <= k; i++) {
                for (int x = -size; x <= size; x++) {
                    for (int z = -size; z <= size; z++) {
                        for (int u = 0; u <= 5; u++) {
                            BlockPos.Mutable mutable = new BlockPos.Mutable();
                            mutable.setPos(pos).move(Direction.UP, i);
                            this.setBlockState(world, mutable, config.stem);
                            mutable.setPos(pos).setAndOffset(pos, x, k, z).move(Direction.UP, u);
                            this.setBlockState(world, mutable, config.cap);
                            if (k >= 5 && rand.nextBoolean()) {
                                int r = 3;
                                for (int j = 0; j <= r; j++) {
                                    for (int t = 0; t <= 1; t++) {
                                        mutable.setPos(pos).move(Direction.UP, h).move(dir, j);
                                        this.setBlockState(world, mutable, config.stem);
                                        mutable.setAndOffset(pos, x, h + 1, z).move(dir, r).move(Direction.UP, t);
                                        this.setBlockState(world, mutable, config.cap);
                                        BlockPos.Mutable mutable1 = new BlockPos.Mutable();
                                        mutable1.setPos(mutable).move(Direction.UP, 1);
                                    }
                                }
                            }
                        }
                    }
                }
            }
            return true;
        }
    }
}
