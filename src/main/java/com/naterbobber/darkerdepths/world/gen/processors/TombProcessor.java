package com.naterbobber.darkerdepths.world.gen.processors;

import com.mojang.serialization.Codec;
import com.naterbobber.darkerdepths.init.DDStructureProcessorTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import org.jetbrains.annotations.Nullable;

// TODO: MAKE THE TOMB SPAWN PROPERLY
public class TombProcessor extends StructureProcessor {
    public static final Codec<TombProcessor> CODEC = Codec.unit(TombProcessor::new);

    @Nullable
    @Override
    public StructureTemplate.StructureBlockInfo processBlock(LevelReader level, BlockPos pOffset, BlockPos pPos, StructureTemplate.StructureBlockInfo pBlockInfo, StructureTemplate.StructureBlockInfo pRelativeBlockInfo, StructurePlaceSettings settings) {
//        BlockState blockState = pRelativeBlockInfo.state();
//        if (blockState.is(Blocks.MAGENTA_GLAZED_TERRACOTTA)) {
//            Direction direction = blockState.getValue(BlockStateProperties.HORIZONTAL_FACING);
//            System.out.println("Direction is " + direction);
//            System.out.println("Rotation is " + settings.getRotation());
//            for (TombBlock.Part part : TombBlock.Part.values()) {
//                if (part == TombBlock.Part.FRONT_CENTER) continue;
//                BlockPos partPos = TombBlock.getPartPos(pRelativeBlockInfo.pos(), part, direction);
//                BlockState partState = DDBlocks.TOMB.get().defaultBlockState()
//                        .setValue(BlockStateProperties.HORIZONTAL_FACING, direction)
//                        .setValue(TombBlock.PART, part);
//                level.getChunk(partPos).setBlockState(partPos, partState, true);
//            }
//            return new StructureTemplate.StructureBlockInfo(pRelativeBlockInfo.pos(), DDBlocks.TOMB.get().defaultBlockState().setValue(TombBlock.PART, TombBlock.Part.FRONT_CENTER).setValue(TombBlock.FACING, direction), pRelativeBlockInfo.nbt());
//        }
        return super.processBlock(level, pOffset, pPos, pBlockInfo, pRelativeBlockInfo, settings);
    }

    @Override
    protected StructureProcessorType<?> getType() {
        return DDStructureProcessorTypes.TOMB.get();
    }
}
