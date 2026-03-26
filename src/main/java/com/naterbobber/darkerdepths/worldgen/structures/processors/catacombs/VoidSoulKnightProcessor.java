package com.naterbobber.darkerdepths.worldgen.structures.processors.catacombs;

import com.mojang.serialization.MapCodec;
import com.naterbobber.darkerdepths.init.DDBlocks;
import com.naterbobber.darkerdepths.init.DDStructureProcessorTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import org.jetbrains.annotations.Nullable;

public class VoidSoulKnightProcessor extends StructureProcessor {
    public static final MapCodec<VoidSoulKnightProcessor> CODEC = MapCodec.unit(VoidSoulKnightProcessor::new);

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
        BlockState placeholderState = relativeBlockInfo.state();

        if (placeholderState.is(Blocks.BROWN_GLAZED_TERRACOTTA)) {

            Direction direction = getFacingDirection(placeholderState, settings);
            BlockState mobPlacementState = DDBlocks.MOB_PLACER.get().defaultBlockState();

            CompoundTag nbt = new CompoundTag();
            nbt.putString("EntityType", "darkerdepths:void_soul_knight");
            nbt.putFloat("Rotation", direction.toYRot());
            nbt.putBoolean("HasSpawned", false);

            CompoundTag entityData = new CompoundTag();
            if (!entityData.isEmpty()) {
                nbt.put("EntityData", entityData);
            }

            return new StructureTemplate.StructureBlockInfo(relativeBlockInfo.pos(), mobPlacementState, nbt);

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
        return DDStructureProcessorTypes.VOID_SOUL_KNIGHT.get();
    }
}