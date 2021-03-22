package com.naterbobber.darkerdepths.common.world.gen.feature.structures;

import com.naterbobber.darkerdepths.core.registries.DDStructurePieceType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.structure.StructureManager;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.structure.TemplateStructurePiece;
import net.minecraft.world.gen.feature.template.BlockIgnoreStructureProcessor;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.feature.template.TemplateManager;

import java.util.List;
import java.util.Random;

//<>

public class CaveFossilPieces {
    private static final ResourceLocation STRUCTURE_SPINE_01 = new ResourceLocation("fossil/spine_1");
    private static final ResourceLocation STRUCTURE_SPINE_02 = new ResourceLocation("fossil/spine_2");
    private static final ResourceLocation STRUCTURE_SPINE_03 = new ResourceLocation("fossil/spine_3");
    private static final ResourceLocation STRUCTURE_SPINE_04 = new ResourceLocation("fossil/spine_4");
    private static final ResourceLocation STRUCTURE_SKULL_01 = new ResourceLocation("fossil/skull_1");
    private static final ResourceLocation STRUCTURE_SKULL_02 = new ResourceLocation("fossil/skull_2");
    private static final ResourceLocation STRUCTURE_SKULL_03 = new ResourceLocation("fossil/skull_3");
    private static final ResourceLocation STRUCTURE_SKULL_04 = new ResourceLocation("fossil/skull_4");
    private static final ResourceLocation[] FOSSILS = new ResourceLocation[]{STRUCTURE_SPINE_01, STRUCTURE_SPINE_02, STRUCTURE_SPINE_03, STRUCTURE_SPINE_04, STRUCTURE_SKULL_01, STRUCTURE_SKULL_02, STRUCTURE_SKULL_03, STRUCTURE_SKULL_04};


    public static void addPieces(TemplateManager manager, List<StructurePiece> pieces, Random rand, BlockPos pos) {
        Rotation rotation = Rotation.randomRotation(rand);
        pieces.add(new CaveFossilPiece(manager, Util.getRandomObject(FOSSILS, rand), pos, rotation));
    }

    public static class CaveFossilPiece extends TemplateStructurePiece {
        private final ResourceLocation templateLocation;
        private final Rotation rotation;

        public CaveFossilPiece(TemplateManager manager, ResourceLocation piece, BlockPos pos, Rotation rotation) {
            super(DDStructurePieceType.CAVE_FOSSIL, 0);
            this.templateLocation = piece;
            this.templatePosition = pos;
            this.rotation = rotation;
            this.loadTemplate(manager);
        }

        public CaveFossilPiece(TemplateManager manager, CompoundNBT nbt) {
            super(DDStructurePieceType.CAVE_FOSSIL, nbt);
            this.templateLocation = new ResourceLocation(nbt.getString("Template"));
            this.rotation = Rotation.valueOf(nbt.getString("Rot"));
            this.loadTemplate(manager);
        }

        private void loadTemplate(TemplateManager manager) {
            Template template = manager.getTemplateDefaulted(this.templateLocation);
            PlacementSettings placementsettings = (new PlacementSettings()).setRotation(this.rotation).setMirror(Mirror.NONE).addProcessor(BlockIgnoreStructureProcessor.AIR_AND_STRUCTURE_BLOCK);
            this.setup(template, this.templatePosition, placementsettings);
        }

        @Override
        protected void readAdditional(CompoundNBT nbt) {
            super.readAdditional(nbt);
            nbt.putString("Template", this.templateLocation.toString());
            nbt.putString("Template", this.rotation.name());
        }

        @Override
        protected void handleDataMarker(String function, BlockPos pos, IWorld worldIn, Random rand, MutableBoundingBox sbb) {
        }

        @Override
        public boolean func_230383_a_(ISeedReader seedReader, StructureManager manager, ChunkGenerator generator, Random rand, MutableBoundingBox boundingBox, ChunkPos chunkPos, BlockPos pos) {
            boundingBox.expandTo(this.template.getMutableBoundingBox(this.placeSettings, this.templatePosition));
            return super.func_230383_a_(seedReader, manager, generator, rand, boundingBox, chunkPos, pos);
        }
    }
}