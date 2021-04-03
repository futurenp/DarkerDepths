package com.naterbobber.darkerdepths.common.world.gen.feature;

import com.mojang.serialization.Codec;
import com.naterbobber.darkerdepths.common.blocks.FungusShelfBlock;
import com.naterbobber.darkerdepths.common.blocks.SporeBlock;
import com.naterbobber.darkerdepths.core.registries.DDBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.VineBlock;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.StructureManager;
import net.minecraftforge.common.Tags;

import java.util.Random;

public class ShelfFeature extends Feature<NoFeatureConfig> {

    public ShelfFeature(Codec<NoFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean func_230362_a_(ISeedReader world, StructureManager structure, ChunkGenerator chunk, Random rand, BlockPos pos, NoFeatureConfig config) {
        BlockState state = world.getBlockState(pos);
        if (!world.isAirBlock(pos)) {
            return false;
        } else {
                for (Direction direction : Direction.Plane.HORIZONTAL) {
                    BlockPos offset = pos.offset(direction);
                    if (world.getBlockState(offset).isIn(Blocks.STONE)) {
                        world.setBlockState(offset, DDBlocks.GLOWSHROOM_SHELF.get().getDefaultState().with(FungusShelfBlock.FACING, direction.getOpposite()), 2);
                        return true;
                    }
                }
            }
        return false;
        }
    }

