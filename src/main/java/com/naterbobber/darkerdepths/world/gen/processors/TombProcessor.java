package com.naterbobber.darkerdepths.world.gen.processors;

import com.mojang.serialization.Codec;
import com.naterbobber.darkerdepths.blocks.TombBlock;
import com.naterbobber.darkerdepths.init.DDBlocks;
import com.naterbobber.darkerdepths.init.DDStructureProcessorTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class TombProcessor extends StructureProcessor {
    public static final Codec<TombProcessor> CODEC = Codec.unit(TombProcessor::new);
    private final List<StructureTemplate.StructureBlockInfo> tombParts = new ArrayList<>();

    @Nullable
    @Override
    public StructureTemplate.StructureBlockInfo processBlock(
            LevelReader level,
            BlockPos templatePos,
            BlockPos worldPos,
            StructureTemplate.StructureBlockInfo blockInfo,
            StructureTemplate.StructureBlockInfo relativeBlockInfo,
            StructurePlaceSettings settings
    ) {
        BlockState blockState = relativeBlockInfo.state();

        if (blockState.is(Blocks.MAGENTA_GLAZED_TERRACOTTA)) {
            Direction facing = getFacingDirection(blockState, settings);
            TombBlock.generateMultiblockForProcessor(worldPos, facing, level, tombParts, relativeBlockInfo.pos());

            BlockState tombState = DDBlocks.TOMB.get().defaultBlockState()
                    .setValue(TombBlock.PART, TombBlock.Part.FRONT_CENTER)
                    .setValue(TombBlock.FACING, facing)
                    .setValue(TombBlock.WATERLOGGED,
                            level.getFluidState(worldPos).getType() == Fluids.WATER);

            return new StructureTemplate.StructureBlockInfo(
                    relativeBlockInfo.pos(),
                    tombState,
                    relativeBlockInfo.nbt()
            );
        }

        for (StructureTemplate.StructureBlockInfo tombPart : tombParts) {
            if (tombPart.pos().equals(relativeBlockInfo.pos())) {
                return tombPart;
            }
        }

        return relativeBlockInfo;
    }

    private Direction getFacingDirection(BlockState markerBlock, StructurePlaceSettings settings) {
        if (markerBlock.hasProperty(BlockStateProperties.HORIZONTAL_FACING)) {
            Direction markerFacing = markerBlock.getValue(BlockStateProperties.HORIZONTAL_FACING);
            return settings.getRotation().rotate(markerFacing);
        }

        return settings.getRotation().rotate(Direction.NORTH);
    }

    @Override
    protected StructureProcessorType<?> getType() {
        return DDStructureProcessorTypes.TOMB.get();
    }
}
