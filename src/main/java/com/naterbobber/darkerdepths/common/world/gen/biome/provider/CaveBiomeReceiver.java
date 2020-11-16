package com.naterbobber.darkerdepths.common.world.gen.biome.provider;

import java.util.ArrayList;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistry;

//<>

public class CaveBiomeReceiver {
	public static ForgeRegistry<Biome> BIOME = ((ForgeRegistry<Biome>)ForgeRegistries.BIOMES);
	
	public static final int VOID = BIOME.getID(Biomes.THE_VOID);
	public static final int NETHER = BIOME.getID(Biomes.NETHER_WASTES);
	
	public static ArrayList<Integer> BIOME_ID = new ArrayList<>();
	
	static {
		BIOME_ID.add(VOID);
		BIOME_ID.add(NETHER);
	}
	
	public static int getRandomBiomes(INoiseRandom context) {
		return BIOME_ID.get(context.random(BIOME_ID.size()));
	}
}