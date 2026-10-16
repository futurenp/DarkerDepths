package com.naterbobber.darkerdepths.common.worldgen.feature.features;

import com.mojang.serialization.Codec;
import com.naterbobber.darkerdepths.common.block.unique.PointedAridrockBlock;
import com.naterbobber.darkerdepths.common.init.DDBlocks;
import com.naterbobber.darkerdepths.common.worldgen.feature.config.PointedAridrockCeilingConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.DripstoneThickness;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class PointedAridrockCeilingFeature extends Feature<PointedAridrockCeilingConfig> {
    public PointedAridrockCeilingFeature(Codec<PointedAridrockCeilingConfig> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<PointedAridrockCeilingConfig> context) {
        var level = context.level();
        var origin = context.origin();
        var config = context.config();
        var random = context.random();
        var center = origin.above();
        if (level.isOutsideBuildHeight(origin) || level.isOutsideBuildHeight(center)
                || !level.isEmptyBlock(origin) || !canReplaceCeiling(level, center, config)) {
            return false;
        }

        int radius = config.radius().sample(random);
        int depth = config.depth().sample(random);
        var supports = new ArrayList<BlockPos>();
        boolean placed = false;
        for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {
                int distanceSquared = x * x + z * z;
                if (distanceSquared > radius * radius
                        || distanceSquared > (radius - 1) * (radius - 1) && random.nextFloat() < 0.25F) {
                    continue;
                }
                var support = findCeiling(level, origin.offset(x, 0, z), config.verticalRange());
                if (support == null || !canReplaceCeiling(level, support, config)) {
                    continue;
                }
                for (int y = 0; y < depth; y++) {
                    var pos = support.above(y);
                    if (level.isOutsideBuildHeight(pos) || !level.ensureCanWrite(pos)) {
                        break;
                    }
                    var state = level.getBlockState(pos);
                    if (state.is(DDBlocks.ARIDROCK.get())) {
                        continue;
                    }
                    if (!config.replaceableBlocks().contains(state.getBlock().builtInRegistryHolder())) {
                        break;
                    }
                    placed |= level.setBlock(pos, DDBlocks.ARIDROCK.get().defaultBlockState(), Block.UPDATE_CLIENTS);
                }
                if (level.getBlockState(support).is(DDBlocks.ARIDROCK.get())) {
                    supports.add(support);
                }
            }
        }

        for (var support : supports) {
            if (random.nextFloat() < config.pointedChance()) {
                placed |= placePointed(level, support.below(), config, random);
            }
        }
        return placed;
    }

    private static boolean canReplaceCeiling(WorldGenLevel level, BlockPos pos, PointedAridrockCeilingConfig config) {
        if (!level.ensureCanWrite(pos)) {
            return false;
        }
        var state = level.getBlockState(pos);
        return (state.is(DDBlocks.ARIDROCK.get())
                || config.replaceableBlocks().contains(state.getBlock().builtInRegistryHolder()))
                && state.isFaceSturdy(level, pos, Direction.DOWN);
    }

    @Nullable
    private static BlockPos findCeiling(WorldGenLevel level, BlockPos origin, int range) {
        boolean startsInAir = level.isEmptyBlock(origin);
        for (int i = 1; i <= range; i++) {
            var pos = startsInAir ? origin.above(i) : origin.below(i);
            if (level.isOutsideBuildHeight(pos)) {
                return null;
            }
            if (startsInAir && !level.isEmptyBlock(pos)) {
                return pos;
            }
            if (!startsInAir && level.isEmptyBlock(pos)) {
                return pos.above();
            }
        }
        return null;
    }

    private static boolean placePointed(WorldGenLevel level, BlockPos origin, PointedAridrockCeilingConfig config, RandomSource random) {
        int requestedHeight = config.height().sample(random);
        int height = 0;
        while (height < requestedHeight) {
            var pos = origin.below(height);
            if (level.isOutsideBuildHeight(pos) || !level.ensureCanWrite(pos) || !level.isEmptyBlock(pos)) {
                break;
            }
            height++;
        }
        if (height == 0) {
            return false;
        }
        var state = DDBlocks.POINTED_ARIDROCK.get().defaultBlockState()
                .setValue(PointedAridrockBlock.TIP_DIRECTION, Direction.DOWN)
                .setValue(PointedAridrockBlock.WATERLOGGED, false);
        for (int i = 0; i < height; i++) {
            var thickness = i == height - 1 ? DripstoneThickness.TIP
                    : i == height - 2 ? DripstoneThickness.FRUSTUM
                    : i == 0 ? DripstoneThickness.BASE : DripstoneThickness.MIDDLE;
            level.setBlock(origin.below(i), state.setValue(PointedAridrockBlock.THICKNESS, thickness), Block.UPDATE_CLIENTS);
        }
        return true;
    }
}
