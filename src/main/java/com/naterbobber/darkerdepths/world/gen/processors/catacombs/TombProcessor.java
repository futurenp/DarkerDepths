package com.naterbobber.darkerdepths.world.gen.processors.catacombs;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.naterbobber.darkerdepths.blocks.TombBlock;
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
        BlockState blockState = relativeBlockInfo.state();

        if (blockState.is(Blocks.MAGENTA_GLAZED_TERRACOTTA)) {
            Direction facing = getFacingDirection(blockState, settings);
            Set<StructureTemplate.StructureBlockInfo> currentTombPartsSet = TombBlock.generateMultiblockForProcessor(worldPos, facing, level, relativeBlockInfo.pos());

            Map<BlockPos, StructureTemplate.StructureBlockInfo> currentTombParts = new HashMap<>();
            currentTombPartsSet.forEach(part -> currentTombParts.put(part.pos(), part));

            tombPartsMap.putAll(currentTombParts);

            BlockState tombState = DDBlocks.TOMB.get().defaultBlockState()
                    .setValue(TombBlock.PART, TombBlock.Part.FRONT_CENTER)
                    .setValue(TombBlock.FACING, facing)
                    .setValue(TombBlock.INHABITED, true)
                    .setValue(TombBlock.WATERLOGGED, level.getFluidState(worldPos).getType() == Fluids.WATER);

            CompoundTag nbt = new CompoundTag();
            ListTag itemsNbt = new ListTag();

            ItemStack forsakenBronzeScrap = new ItemStack(DDItems.FORSAKEN_BRONZE_SCRAP.get());
            CompoundTag itemTag = (CompoundTag) forsakenBronzeScrap.save(level.registryAccess());
            itemTag.putByte("Slot", (byte) 0);
            itemsNbt.add(itemTag);

            nbt.put("Items", itemsNbt);

            return new StructureTemplate.StructureBlockInfo(
                    relativeBlockInfo.pos(),
                    tombState,
                    nbt
            );
        }

        StructureTemplate.StructureBlockInfo tombPart = tombPartsMap.get(relativeBlockInfo.pos());
        if (tombPart != null) {
            return tombPart;
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