package com.naterbobber.darkerdepths.world.gen.processors.rope_mines;

import com.mojang.serialization.Codec;
import com.naterbobber.darkerdepths.init.DDStructureProcessorTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import org.jetbrains.annotations.Nullable;

public class RopeMinesArchaeologyProcessor extends StructureProcessor {
    public static final Codec<RopeMinesArchaeologyProcessor> CODEC = Codec.unit(RopeMinesArchaeologyProcessor::new);

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
        ResourceLocation lootTableId = null;

        if (placeholderState.is(Blocks.SAND) && Math.random() > .95) {
            lootTableId = new ResourceLocation("darkerdepths", "archaeology/catacombs/standard");
        }

        if (lootTableId != null) {
            BlockState blockState = Blocks.SUSPICIOUS_SAND.defaultBlockState();
            CompoundTag nbt = new CompoundTag();
            nbt.putString("LootTable", lootTableId.toString());

            return new StructureTemplate.StructureBlockInfo(relativeBlockInfo.pos(), blockState, nbt);
        }

        return relativeBlockInfo;
    }

    @Override
    protected StructureProcessorType<?> getType() {
        return DDStructureProcessorTypes.ROPE_MINES_ARCHAEOLOGY.get();
    }
}