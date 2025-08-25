package com.naterbobber.darkerdepths.world.gen.processors.catacombs;

import com.mojang.serialization.Codec;
import com.naterbobber.darkerdepths.init.DDStructureProcessorTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BarrelBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import org.jetbrains.annotations.Nullable;

public class CatacombsBarrelProcessor extends StructureProcessor {
    public static final Codec<CatacombsBarrelProcessor> CODEC = Codec.unit(CatacombsBarrelProcessor::new);

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

        if (placeholderState.is(Blocks.PISTON)) {
            lootTableId = ResourceLocation.fromNamespaceAndPath("darkerdepths", "chests/catacombs/standard");
        } else if (placeholderState.is(Blocks.STICKY_PISTON)) {
            lootTableId = ResourceLocation.fromNamespaceAndPath("darkerdepths", "chests/catacombs/treasure");
        }

        if (lootTableId != null) {
            Direction facing = placeholderState.getValue(BlockStateProperties.FACING);

            BlockState newBarrelState = Blocks.BARREL.defaultBlockState()
                    .setValue(BarrelBlock.FACING, facing);

            CompoundTag nbt = new CompoundTag();
            nbt.putString("LootTable", lootTableId.toString());

            return new StructureTemplate.StructureBlockInfo(relativeBlockInfo.pos(), newBarrelState, nbt);
        }

        return relativeBlockInfo;
    }

    @Override
    protected StructureProcessorType<?> getType() {
        return DDStructureProcessorTypes.CATACOMBS_BARREL.get();
    }
}