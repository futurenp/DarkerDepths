package com.naterbobber.darkerdepths.common.world.gen.placement;

import com.mojang.serialization.Codec;
import com.naterbobber.darkerdepths.core.util.CaveSurface;
import com.naterbobber.darkerdepths.core.util.Column;
import com.naterbobber.darkerdepths.core.util.helpers.BlockPosHelper;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.WorldDecoratingHelper;
import net.minecraft.world.gen.placement.Placement;

import java.util.Optional;
import java.util.OptionalInt;
import java.util.Random;
import java.util.stream.Stream;

//<>

public class CaveSurfacePlacement extends Placement<CaveDecoratorConfig> {
    public CaveSurfacePlacement(Codec<CaveDecoratorConfig> codec) {
        super(codec);
    }

    @Override
    public Stream<BlockPos> getPositions(WorldDecoratingHelper worldIn, Random rand, CaveDecoratorConfig configIn, BlockPos pos) {
        Optional<Column> column = Column.scan(worldIn.field_242889_a, pos, configIn.floorToCeilingSearchRange, BlockState::isAir, (state) -> state.getMaterial().isSolid());
        if (!column.isPresent()) {
            return Stream.of();
        } else {
            OptionalInt surfaceType = configIn.surface == CaveSurface.CEILING ? column.get().getCeiling() :  column.get().getFloor();
            return !surfaceType.isPresent() ? Stream.of() : Stream.of(BlockPosHelper.atY(pos, surfaceType.getAsInt() - configIn.surface.getY()));
        }
    }
}