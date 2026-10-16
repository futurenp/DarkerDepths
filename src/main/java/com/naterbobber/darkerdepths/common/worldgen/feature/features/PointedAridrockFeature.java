package com.naterbobber.darkerdepths.common.worldgen.feature.features;

import com.mojang.serialization.Codec;
import com.naterbobber.darkerdepths.common.block.unique.PointedAridrockBlock;
import com.naterbobber.darkerdepths.common.init.DDBlocks;
import com.naterbobber.darkerdepths.common.worldgen.feature.config.PointedAridrockConfig;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.DripstoneThickness;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

public class PointedAridrockFeature extends Feature<PointedAridrockConfig> {
    public PointedAridrockFeature(Codec<PointedAridrockConfig> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<PointedAridrockConfig> context) {
        var level = context.level();
        var origin = context.origin();
        var config = context.config();
        var floorPos = origin.below();
        if (level.isOutsideBuildHeight(origin) || level.isOutsideBuildHeight(floorPos)) {
            return false;
        }
        var floorState = level.getBlockState(floorPos);
        if (!config.validFloorBlocks().contains(floorState.getBlock().builtInRegistryHolder())
                || !floorState.isFaceSturdy(level, floorPos, Direction.UP)) {
            return false;
        }

        int requestedHeight = config.height().sample(context.random());
        int height = 0;
        while (height < requestedHeight) {
            var pos = origin.above(height);
            if (level.isOutsideBuildHeight(pos) || !level.isEmptyBlock(pos)) {
                break;
            }
            height++;
        }
        if (height == 0) {
            return false;
        }

        var state = DDBlocks.POINTED_ARIDROCK.get().defaultBlockState()
                .setValue(PointedAridrockBlock.TIP_DIRECTION, Direction.UP)
                .setValue(PointedAridrockBlock.WATERLOGGED, false);
        for (int i = 0; i < height; i++) {
            var thickness = i == height - 1 ? DripstoneThickness.TIP
                    : i == height - 2 ? DripstoneThickness.FRUSTUM
                    : i == 0 ? DripstoneThickness.BASE : DripstoneThickness.MIDDLE;
            level.setBlock(origin.above(i), state.setValue(PointedAridrockBlock.THICKNESS, thickness), Block.UPDATE_CLIENTS);
        }
        return true;
    }
}
