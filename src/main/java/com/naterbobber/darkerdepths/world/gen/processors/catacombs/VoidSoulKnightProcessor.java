package com.naterbobber.darkerdepths.world.gen.processors.catacombs;

import com.mojang.serialization.Codec;
import com.naterbobber.darkerdepths.entities.VoidSoulKnightEntity;
import com.naterbobber.darkerdepths.init.DDBlocks;
import com.naterbobber.darkerdepths.init.DDEntityTypes;
import com.naterbobber.darkerdepths.init.DDStructureProcessorTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
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
    public static final Codec<VoidSoulKnightProcessor> CODEC = Codec.unit(VoidSoulKnightProcessor::new);

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
            BlockState blockState = Blocks.AIR.defaultBlockState();

            Direction direction = blockInfo.state().getValue(BlockStateProperties.HORIZONTAL_FACING);

            // Create the entity
            //custom marker block work better instead?
            if (level instanceof ServerLevel serverLevel) {
                VoidSoulKnightEntity knight = new VoidSoulKnightEntity(DDEntityTypes.VOID_SOUL_KNIGHT.get(), serverLevel);

                // Set position and rotation
                knight.setPos(worldPos.getX() + 0.5, worldPos.getY(), worldPos.getZ() + 0.5);
                knight.setYRot(direction.toYRot());
                knight.yRotO = direction.toYRot();

                return new StructureTemplate.StructureBlockInfo(relativeBlockInfo.pos(), blockState, null);
            }
        }
        return relativeBlockInfo;
    }

    @Override
    protected StructureProcessorType<?> getType() {
        return DDStructureProcessorTypes.VOID_SOUL_KNIGHT.get();
    }
}