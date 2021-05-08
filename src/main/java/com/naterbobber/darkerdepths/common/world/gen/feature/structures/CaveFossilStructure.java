package com.naterbobber.darkerdepths.common.world.gen.feature.structures;

import com.mojang.serialization.Codec;
import com.naterbobber.darkerdepths.core.registries.DDBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.MarginedStructureStart;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.template.TemplateManager;

//<>

public class CaveFossilStructure extends Structure<NoFeatureConfig> {
    public CaveFossilStructure(Codec<NoFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public IStartFactory<NoFeatureConfig> getStartFactory() {
        return Start::new;
    }

    public static class Start extends MarginedStructureStart<NoFeatureConfig> {
        public Start(Structure<NoFeatureConfig> structure, int x, int z, MutableBoundingBox boundingBox, int height, long seed) {
            super(structure, x, z, boundingBox, height, seed);
        }

        @Override
        public void func_230364_a_(DynamicRegistries registries, ChunkGenerator generator, TemplateManager manager, int chunkX, int chunkZ, Biome biomeIn, NoFeatureConfig configIn) {
            ChunkPos chunkPos = new ChunkPos(chunkX, chunkZ);
            int x = chunkPos.getXStart() + this.rand.nextInt(16);
            int z = chunkPos.getZStart() + this.rand.nextInt(16);
            int seaLevel = generator.getSeaLevel();
            int y = seaLevel + this.rand.nextInt(generator.getGroundHeight() - 2 - seaLevel);
            IBlockReader sample = generator.func_230348_a_(x, z);

            for(BlockPos.Mutable pos = new BlockPos.Mutable(x, y, z); y < seaLevel; ++y) {
                BlockState airState = sample.getBlockState(pos);
                pos.move(Direction.DOWN);
                BlockState baseState = sample.getBlockState(pos);
                if (/*airState.isAir() &&*/ (baseState.matchesBlock(Blocks.STONE) || baseState.matchesBlock(DDBlocks.ARIDROCK.get()) || baseState.matchesBlock(DDBlocks.LIMESTONE.get())/* || baseState.isSolidSide(sample, pos, Direction.UP)*/)) {
                    break;
                }
            }

            if (y < seaLevel) {
                CaveFossilPieces.addPieces(manager, this.components, this.rand, new BlockPos(x, y, z));
                this.recalculateStructureSize();
            }
        }
    }
}