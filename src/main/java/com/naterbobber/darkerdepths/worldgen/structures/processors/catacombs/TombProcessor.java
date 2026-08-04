package com.naterbobber.darkerdepths.worldgen.structures.processors.catacombs;

import com.mojang.serialization.MapCodec;
import com.naterbobber.darkerdepths.block.DDBlockStateProperties;
import com.naterbobber.darkerdepths.block.blockstates.TombUtils;
import com.naterbobber.darkerdepths.init.DDBlocks;
import com.naterbobber.darkerdepths.init.DDItems;
import com.naterbobber.darkerdepths.init.DDStructureProcessorTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class TombProcessor extends StructureProcessor {
    public static final MapCodec<TombProcessor> CODEC = MapCodec.unit(TombProcessor::new);
    private final Map<BlockPos, StructureTemplate.StructureBlockInfo> tombPartsMap = new HashMap<>();

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
        var blockState = relativeBlockInfo.state();

        if (blockState.is(Blocks.MAGENTA_GLAZED_TERRACOTTA)) {
            Direction facing = getFacingDirection(blockState, settings);

            var duskrockTomb = DDBlocks.TOMBS.get("tomb").get();

            var currentTombPartsSet = duskrockTomb.generateMultiblockForProcessor(worldPos, facing, level, relativeBlockInfo.pos());

            Map<BlockPos, StructureTemplate.StructureBlockInfo> currentTombParts = new HashMap<>();
            currentTombPartsSet.forEach(part -> currentTombParts.put(part.pos(), part));

            tombPartsMap.putAll(currentTombParts);

            var tombState = duskrockTomb.defaultBlockState()
                    .setValue(DDBlockStateProperties.TOMB_PART, TombUtils.Part.FRONT_CENTER)
                    .setValue(HorizontalDirectionalBlock.FACING, facing)
                    .setValue(DDBlockStateProperties.INHABITED, true)
                    .setValue(BlockStateProperties.WATERLOGGED, level.getFluidState(worldPos).getType() == Fluids.WATER);

            var nbt = new CompoundTag();
            var itemsNbt = new ListTag();

            var forsakenBronzeScrap = new ItemStack(DDItems.FORSAKEN_BRONZE_SCRAP.get());
            var itemTag = (CompoundTag) forsakenBronzeScrap.save(level.registryAccess());
            itemTag.putByte("Slot", (byte) 0);
            itemsNbt.add(itemTag);

            nbt.put("Items", itemsNbt);

            return new StructureTemplate.StructureBlockInfo(
                    relativeBlockInfo.pos(),
                    tombState,
                    nbt
            );
        }

        var tombPart = tombPartsMap.get(relativeBlockInfo.pos());
        if (tombPart != null) {
            return tombPart;
        }

        return relativeBlockInfo;
    }

    private Direction getFacingDirection(BlockState markerBlock, StructurePlaceSettings settings) {
        if (markerBlock.hasProperty(BlockStateProperties.HORIZONTAL_FACING)) {
            var markerFacing = markerBlock.getValue(BlockStateProperties.HORIZONTAL_FACING);
            return settings.getRotation().rotate(markerFacing);
        }

        return settings.getRotation().rotate(Direction.NORTH);
    }

    @Override
    protected StructureProcessorType<?> getType() {
        return DDStructureProcessorTypes.TOMB.get();
    }
}