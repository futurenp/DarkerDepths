package com.naterbobber.darkerdepths.common.world.gen.biome.provider;

import java.util.ArrayList;

import com.naterbobber.darkerdepths.core.registries.DDBiomes;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistry;

//<>

public class CaveBiomeReceiver {
	public static ForgeRegistry<Biome> BIOME = ((ForgeRegistry<Biome>)ForgeRegistries.BIOMES);
	
	public static final int DEFAULT_CAVE 	= BIOME.getID(DDBiomes.DEFAULT_CAVE.get());
	public static final int MOLTEN_CAVERN 	= BIOME.getID(DDBiomes.MOLTEN_CAVERN.get());
	public static final int SANDY_CATACOMBS	= BIOME.getID(DDBiomes.SANDY_CATACOMBS.get());
	
	public static ArrayList<Integer> BIOME_ID = new ArrayList<>();
	
	static {
		BIOME_ID.add(DEFAULT_CAVE);
		BIOME_ID.add(MOLTEN_CAVERN);
		BIOME_ID.add(DEFAULT_CAVE);
		BIOME_ID.add(SANDY_CATACOMBS);
		BIOME_ID.add(DEFAULT_CAVE);
	}
	
	public static int getRandomBiomes(INoiseRandom context) {
		return BIOME_ID.get(context.random(BIOME_ID.size()));
	}
}