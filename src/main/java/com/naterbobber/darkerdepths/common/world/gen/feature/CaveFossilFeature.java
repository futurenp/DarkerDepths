package com.naterbobber.darkerdepths.common.world.gen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.block.Blocks;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.StructureManager;
import net.minecraft.world.gen.feature.template.*;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

//<>

public class CaveFossilFeature extends Feature<NoFeatureConfig> {
    private static final ResourceLocation STRUCTURE_SPINE_01 = new ResourceLocation("fossil/spine_1");
    private static final ResourceLocation STRUCTURE_SPINE_02 = new ResourceLocation("fossil/spine_2");
    private static final ResourceLocation STRUCTURE_SPINE_03 = new ResourceLocation("fossil/spine_3");
    private static final ResourceLocation STRUCTURE_SPINE_04 = new ResourceLocation("fossil/spine_4");
    private static final ResourceLocation STRUCTURE_SPINE_01_COAL = new ResourceLocation("fossil/spine_1_coal");
    private static final ResourceLocation STRUCTURE_SPINE_02_COAL = new ResourceLocation("fossil/spine_2_coal");
    private static final ResourceLocation STRUCTURE_SPINE_03_COAL = new ResourceLocation("fossil/spine_3_coal");
    private static final ResourceLocation STRUCTURE_SPINE_04_COAL = new ResourceLocation("fossil/spine_4_coal");
    private static final ResourceLocation STRUCTURE_SKULL_01 = new ResourceLocation("fossil/skull_1");
    private static final ResourceLocation STRUCTURE_SKULL_02 = new ResourceLocation("fossil/skull_2");
    private static final ResourceLocation STRUCTURE_SKULL_03 = new ResourceLocation("fossil/skull_3");
    private static final ResourceLocation STRUCTURE_SKULL_04 = new ResourceLocation("fossil/skull_4");
    private static final ResourceLocation STRUCTURE_SKULL_01_COAL = new ResourceLocation("fossil/skull_1_coal");
    private static final ResourceLocation STRUCTURE_SKULL_02_COAL = new ResourceLocation("fossil/skull_2_coal");
    private static final ResourceLocation STRUCTURE_SKULL_03_COAL = new ResourceLocation("fossil/skull_3_coal");
    private static final ResourceLocation STRUCTURE_SKULL_04_COAL = new ResourceLocation("fossil/skull_4_coal");
    private static final ResourceLocation[] FOSSILS = new ResourceLocation[]{STRUCTURE_SPINE_01, STRUCTURE_SPINE_02, STRUCTURE_SPINE_03, STRUCTURE_SPINE_04, STRUCTURE_SKULL_01, STRUCTURE_SKULL_02, STRUCTURE_SKULL_03, STRUCTURE_SKULL_04};
    private static final ResourceLocation[] FOSSILS_COAL = new ResourceLocation[]{STRUCTURE_SPINE_01_COAL, STRUCTURE_SPINE_02_COAL, STRUCTURE_SPINE_03_COAL, STRUCTURE_SPINE_04_COAL, STRUCTURE_SKULL_01_COAL, STRUCTURE_SKULL_02_COAL, STRUCTURE_SKULL_03_COAL, STRUCTURE_SKULL_04_COAL};

    public CaveFossilFeature(Codec<NoFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(ISeedReader worldIn, ChunkGenerator generator, Random rand, BlockPos pos, NoFeatureConfig config) {
        if (worldIn.isAirBlock(pos.down()) || worldIn.getBlockState(pos.down()).matchesBlock(Blocks.WATER) || worldIn.getBlockState(pos.down()).matchesBlock(Blocks.LAVA)) {
            return false;
        } else {
            Rotation rotation = Rotation.randomRotation(rand);
            int index = rand.nextInt(FOSSILS.length);
            TemplateManager templateManager = ((ServerWorld)worldIn.getWorld()).getServer().getTemplateManager();
            Template fossilsTemplate = templateManager.getTemplateDefaulted(FOSSILS[index]);
            Template coalFossilsTemplate = templateManager.getTemplateDefaulted(FOSSILS_COAL[index]);
            ChunkPos chunkPos = new ChunkPos(pos);
            MutableBoundingBox boundingBox = new MutableBoundingBox(chunkPos.getXStart(), 0, chunkPos.getZStart(), chunkPos.getXEnd(), 40, chunkPos.getZEnd());
            PlacementSettings placementSettings = (new PlacementSettings()).setRotation(rotation).setBoundingBox(boundingBox).setRandom(rand).addProcessor(BlockIgnoreStructureProcessor.AIR_AND_STRUCTURE_BLOCK);
            BlockPos blockPos = fossilsTemplate.transformedSize(rotation);
            int xChunk = rand.nextInt(16 - blockPos.getX());
            int zChunk = rand.nextInt(16 - blockPos.getZ());
            int y = 40;

            for(int x = 0; x < blockPos.getX(); ++x) {
                for(int z = 0; z < blockPos.getZ(); ++z) {
                    y = Math.min(y, worldIn.getHeight(Heightmap.Type.OCEAN_FLOOR_WG, pos.getX() + x + xChunk, pos.getZ() + z + zChunk));
                }
            }

            int height = Math.max(y - 15 - rand.nextInt(10), 10);
            BlockPos fossilsPosition = fossilsTemplate.getZeroPositionWithTransform(pos.add(xChunk, height, zChunk), Mirror.NONE, rotation);
            IntegrityProcessor integrityProcessor = new IntegrityProcessor(0.9F);
            placementSettings.clearProcessors().addProcessor(integrityProcessor);
            fossilsTemplate.func_237146_a_(worldIn, fossilsPosition, fossilsPosition, placementSettings, rand, 4);
            placementSettings.removeProcessor(integrityProcessor);
            IntegrityProcessor integrityProcessor1 = new IntegrityProcessor(0.1F);
            placementSettings.clearProcessors().addProcessor(integrityProcessor1);
            coalFossilsTemplate.func_237146_a_(worldIn, fossilsPosition, fossilsPosition, placementSettings, rand, 4);
            return true;
        }
    }
}