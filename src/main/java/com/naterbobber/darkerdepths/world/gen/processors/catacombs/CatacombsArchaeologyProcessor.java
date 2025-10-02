package com.naterbobber.darkerdepths.world.gen.processors.catacombs;

import com.mojang.serialization.MapCodec;
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

public class CatacombsArchaeologyProcessor extends StructureProcessor {
    public static final MapCodec<CatacombsArchaeologyProcessor> CODEC = MapCodec.unit(CatacombsArchaeologyProcessor::new);

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

        if (placeholderState.is(Blocks.SAND) && Math.random() > .875) {
            lootTableId = ResourceLocation.fromNamespaceAndPath("darkerdepths", "archaeology/catacombs/standard");
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
        return DDStructureProcessorTypes.CATACOMBS_ARCHAEOLOGY.get();
    }
}