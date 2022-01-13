package com.naterbobber.darkerdepths.world.gen.features;

import com.mojang.serialization.Codec;
import com.naterbobber.darkerdepths.init.DDBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.VegetationPatchFeature;
import net.minecraft.world.level.levelgen.feature.configurations.VegetationPatchConfiguration;

import java.util.Random;
import java.util.function.Predicate;

public class CorrespondentLayerFeature extends VegetationPatchFeature {

    public CorrespondentLayerFeature(Codec<VegetationPatchConfiguration> codec) {
        super(codec);
    }

    @Override
    protected boolean placeGround(WorldGenLevel world, VegetationPatchConfiguration config, Predicate<BlockState> predicate, Random random, BlockPos.MutableBlockPos pos, int tries) {
        for(int i = 0; i < tries; ++i) {
            BlockState blockstate = config.groundState.getState(random, pos);
            BlockState blockstate1 = world.getBlockState(pos);
            if (!blockstate.is(blockstate1.getBlock())) {
                if (!predicate.test(blockstate1)) {
                    return i != 0;
                }

                world.setBlock(pos, blockstate, 2);
                world.setBlock(pos.below(), DDBlocks.ARID_DEEPSLATE.get().defaultBlockState(), 2);
                pos.move(config.surface.getDirection());
            }
        }

        return true;
    }
}
