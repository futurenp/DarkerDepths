package com.naterbobber.darkerdepths.common.world.gen.feature;

import com.mojang.serialization.Codec;
import com.naterbobber.darkerdepths.common.blocks.AbstractGemStoneBlock;
import com.naterbobber.darkerdepths.common.blocks.HangingDoublePlantBlock;
import com.naterbobber.darkerdepths.core.registries.DDBlocks;
import com.naterbobber.darkerdepths.core.registries.tags.DDBlockTags;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.blockplacer.DoublePlantBlockPlacer;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;

public class PetrifiedLogBranchFeature extends Feature<NoFeatureConfig> {

    public PetrifiedLogBranchFeature(Codec<NoFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(ISeedReader world, ChunkGenerator generator, Random rand, BlockPos pos, NoFeatureConfig config) {
        if (!world.getBlockState(pos.up()).isIn(DDBlockTags.SANDY_GROUND_REPLACEABLE)) {
            return false;
        } else {
            BlockPos.Mutable mutable = new BlockPos.Mutable();
            for (int i = 0; i < MathHelper.nextInt(rand, 6, 8); i++) {
                for (int x = -1; x < 1; x++) {
                    for (int z = -1; z < 1; z++) {
                        mutable.setAndOffset(pos, rand.nextInt(2) - rand.nextInt(2), 0, rand.nextInt(2) - rand.nextInt(2)).move(Direction.DOWN, i);
                        spawnLogs(world, mutable);
                    }
                }
            }
            return true;
        }
    }

    private void spawnLogs(IWorld world, BlockPos pos) {
        Random rand = new Random();
        this.setBlockState(world, pos, DDBlocks.PETRIFIED_LOG.get().getDefaultState());
        if (rand.nextInt(25) == 0) {
            if (world.isAirBlock(pos)) {
                this.setBlockState(world, pos, DDBlocks.POROUS_PETRIFIED_LOG.get().getDefaultState());
                if (rand.nextInt(5) == 0) {
                    for (Direction direction : Direction.values()) {
                        if (world.isAirBlock(pos.offset(direction)) && rand.nextBoolean()) {
                            this.setBlockState(world, pos.offset(direction), DDBlocks.AMBER.get().getDefaultState().with(AbstractGemStoneBlock.FACING, direction));
                        }
                    }
                }
            }
        }
        if (world.isAirBlock(pos.down())) {
            this.setBlockState(world, pos.down(), DDBlocks.LONG_ROOTS.get().getDefaultState());
            this.setBlockState(world, pos.down(2), DDBlocks.LONG_ROOTS.get().getDefaultState().with(HangingDoublePlantBlock.HALF, DoubleBlockHalf.LOWER));
        }
    }
}

