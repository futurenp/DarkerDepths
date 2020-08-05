package com.naterbobber.darkerdepths.world.gen;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.init.BlockInit;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.placement.ConfiguredPlacement;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = DarkerDepths.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)

public class ModOreGen {

    @SubscribeEvent
    public static void generateOres(FMLLoadCompleteEvent event) {
        for (Biome biome : ForgeRegistries.BIOMES) {

            //nether gen
            if (biome.getCategory() == Biome.Category.NETHER) {
                basaltGen(biome, 220, 5, 3, 14, OreFeatureConfig.FillerBlockType.NETHERRACK, Blocks.BASALT.getDefaultState(), 15);
                blackstoneGen(biome, 270, 1, 1, 10, OreFeatureConfig.FillerBlockType.NETHERRACK, Blocks.BLACKSTONE.getDefaultState(), 25);


                //end gen
            } else if (biome.getCategory() == Biome.Category.THEEND) {


                //world gen
            } else if (biome.getCategory() == Biome.Category.OCEAN) {
                stoneGen(biome, 10, 5, 5, 55, OreFeatureConfig.FillerBlockType.NATURAL_STONE, BlockInit.limestone.getBlock().getDefaultState(), 70);
                magmaGen(biome, 25, 10, 7, 13, OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.MAGMA_BLOCK.getDefaultState(), 15);
                stoneGen(biome, 2, 5, 5, 16, OreFeatureConfig.FillerBlockType.NATURAL_STONE, BlockInit.elytrine_ore.getBlock().getDefaultState(), 4);

            } else if (biome.getCategory() == Biome.Category.SWAMP) {
                stoneGen(biome, 7, 5, 5, 55, OreFeatureConfig.FillerBlockType.NATURAL_STONE, BlockInit.shale.getBlock().getDefaultState(), 80);
                magmaGen(biome, 25, 10, 7, 13, OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.MAGMA_BLOCK.getDefaultState(), 15);
                stoneGen(biome, 2, 5, 5, 16, OreFeatureConfig.FillerBlockType.NATURAL_STONE, BlockInit.elytrine_ore.getBlock().getDefaultState(), 4);
            } else {
                stoneGen(biome, 2, 5, 5, 40, OreFeatureConfig.FillerBlockType.NATURAL_STONE, BlockInit.shale.getBlock().getDefaultState(), 60);
                magmaGen(biome, 25, 10, 7, 13, OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.MAGMA_BLOCK.getDefaultState(), 15);
                stoneGen(biome, 3, 5, 5, 55, OreFeatureConfig.FillerBlockType.NATURAL_STONE, BlockInit.limestone.getBlock().getDefaultState(), 50);
                stoneGen(biome, 2, 5, 5, 16, OreFeatureConfig.FillerBlockType.NATURAL_STONE, BlockInit.elytrine_ore.getBlock().getDefaultState(), 4);            }
        }
    }

    private static void stoneGen(Biome biome, int count, int bottomOffset, int topOffset, int max, OreFeatureConfig.FillerBlockType filler, BlockState defaultBlockstate, int size) {
        CountRangeConfig range = new CountRangeConfig(count, bottomOffset, topOffset, max);
        OreFeatureConfig feature = new OreFeatureConfig(filler, defaultBlockstate, size);
        ConfiguredPlacement config = Placement.COUNT_RANGE.configure(range);
        biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(feature).withPlacement(config));
    }

    private static void basaltGen(Biome biome, int count, int bottomOffset, int topOffset, int max, OreFeatureConfig.FillerBlockType filler, BlockState defaultBlockstate, int size) {
        CountRangeConfig range = new CountRangeConfig(count, bottomOffset, topOffset, max);
        OreFeatureConfig feature = new OreFeatureConfig(filler, defaultBlockstate, size);
        ConfiguredPlacement config = Placement.COUNT_RANGE.configure(range);
        biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(feature).withPlacement(config));
    }

    private static void blackstoneGen(Biome biome, int count, int bottomOffset, int topOffset, int max, OreFeatureConfig.FillerBlockType filler, BlockState defaultBlockstate, int size) {
        CountRangeConfig range = new CountRangeConfig(count, bottomOffset, topOffset, max);
        OreFeatureConfig feature = new OreFeatureConfig(filler, defaultBlockstate, size);
        ConfiguredPlacement config = Placement.COUNT_RANGE.configure(range);
        biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(feature).withPlacement(config));
    }

    private static void magmaGen(Biome biome, int count, int bottomOffset, int topOffset, int max, OreFeatureConfig.FillerBlockType filler, BlockState defaultBlockstate, int size) {
        CountRangeConfig range = new CountRangeConfig(count, bottomOffset, topOffset, max);
        OreFeatureConfig feature = new OreFeatureConfig(filler, defaultBlockstate, size);
        ConfiguredPlacement config = Placement.COUNT_RANGE.configure(range);
        biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(feature).withPlacement(config));
    }
}
