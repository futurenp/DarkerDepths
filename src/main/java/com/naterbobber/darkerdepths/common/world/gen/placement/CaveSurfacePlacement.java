package com.naterbobber.darkerdepths.common.world.gen.placement;

import com.mojang.serialization.Codec;
import com.naterbobber.darkerdepths.core.util.VerticalSurfaceType;
import com.naterbobber.darkerdepths.core.util.CaveSurface;
import com.naterbobber.darkerdepths.core.util.helpers.BlockPosHelper;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.feature.WorldDecoratingHelper;
import net.minecraft.world.gen.placement.Placement;

import java.util.Optional;
import java.util.OptionalInt;
import java.util.Random;
import java.util.stream.Stream;

//<>

public class CaveSurfacePlacement extends Placement<CaveSurfaceDecoratorConfig> {
    public CaveSurfacePlacement(Codec<CaveSurfaceDecoratorConfig> codec) {
        super(codec);
    }

    @Override
    public Stream<BlockPos> getPositions(WorldDecoratingHelper worldIn, Random rand, CaveSurfaceDecoratorConfig configIn, BlockPos pos) {
        Optional<CaveSurface> caveSurface = CaveSurface.create(worldIn.field_242889_a, pos, configIn.searchRange, BlockState::isAir, (state) -> {
            return state.getMaterial().isSolid();
        });
        if (!caveSurface.isPresent()) {
            return Stream.of();
        } else {
            OptionalInt surface = configIn.surface == VerticalSurfaceType.CEILING ? caveSurface.get().getCeilingHeight() : caveSurface.get().getFloorHeight();
            return !surface.isPresent() ? Stream.of() : Stream.of(BlockPosHelper.atY(pos, surface.getAsInt() - configIn.surface.getOffset()));
        }
    }
}