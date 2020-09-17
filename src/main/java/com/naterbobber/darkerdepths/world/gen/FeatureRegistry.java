package com.naterbobber.darkerdepths.world.gen;

import com.google.common.collect.ImmutableSet;
import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.init.BlockInit;
import net.minecraft.block.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placement.ChanceConfig;
import net.minecraft.world.gen.placement.NoiseDependant;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.registries.ForgeRegistries;

//<>

@Mod.EventBusSubscriber(modid = DarkerDepths.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)

public class FeatureRegistry {

    @SubscribeEvent
    public static void glowshroomSpawn(FMLLoadCompleteEvent event) {
        BlockClusterFeatureConfig GLOWSHROOM_CONFIG = new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(BlockInit.glowshroom.getBlock().getDefaultState()), new GlowshroomBlockPlacer()).tries(200).whitelist(ImmutableSet.of(Blocks.STONE.getBlock(), Blocks.DIORITE.getBlock(), Blocks.ANDESITE.getBlock(), Blocks.DIRT.getBlock(), BlockInit.limestone.getBlock(), Blocks.GRAVEL.getBlock())).func_227317_b_().build();
        for (Biome biome : ForgeRegistries.BIOMES) {
        	if (biome.getCategory() != Biome.Category.NETHER) {        		
        		biome.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Feature.RANDOM_PATCH.withConfiguration(GLOWSHROOM_CONFIG).withPlacement(Placement.CHANCE_HEIGHTMAP_DOUBLE.configure(new ChanceConfig(1))));
        	}
        }
    }

    @SubscribeEvent
    public static void speleothemSpawn(FMLLoadCompleteEvent event) {
        BlockClusterFeatureConfig SPELEOTHEM_CONFIG = new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(BlockInit.speleothem.getBlock().getDefaultState()), new SpeleothemBlockPlacer()).tries(40).whitelist(ImmutableSet.of(Blocks.STONE.getBlock(), Blocks.DIORITE.getBlock(), Blocks.ANDESITE.getBlock(), Blocks.DIRT.getBlock(), BlockInit.shale.getBlock(), BlockInit.limestone.getBlock(), Blocks.GRAVEL.getBlock())).func_227317_b_().build();
        for (Biome biome : ForgeRegistries.BIOMES) {
        	if (biome.getCategory() != Biome.Category.NETHER) {        		
        		biome.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Feature.RANDOM_PATCH.withConfiguration(SPELEOTHEM_CONFIG).withPlacement(Placement.NOISE_HEIGHTMAP_32.configure(new NoiseDependant(-0.01, 15, 25))));
        	}
        }
    }

    @SubscribeEvent
    public static void glowshroomCapSpawn(FMLLoadCompleteEvent event) {
        BlockClusterFeatureConfig GLOWSHROOM_CAP_CONFIG = new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(BlockInit.glowshroom_cap.getBlock().getDefaultState()), new SpeleothemBlockPlacer()).tries(3).whitelist(ImmutableSet.of(BlockInit.shale.getBlock())).func_227317_b_().build();
        for (Biome biome : ForgeRegistries.BIOMES) {
        	if (biome.getCategory() != Biome.Category.NETHER) {        		
        		biome.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Feature.RANDOM_PATCH.withConfiguration(GLOWSHROOM_CAP_CONFIG).withPlacement(Placement.NOISE_HEIGHTMAP_32.configure(new NoiseDependant(-1, 7, 10))));}
        	}
        }
}
