package com.naterbobber.darkerdepths.common.world.gen.biome.provider;

import java.util.List;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.naterbobber.darkerdepths.common.world.gen.layer.CaveLayerUtil;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.layer.Layer;
import net.minecraft.world.gen.layer.LayerUtil;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

//<>

/**
 * @author BlackGear27
 */
public class DDBiomeProvider extends BiomeProvider {
	public static final Codec<DDBiomeProvider> CODEC = RecordCodecBuilder.create((instance) -> {
		return instance.group(Codec.LONG.fieldOf("seed").stable().forGetter((builder) -> {
			return builder.seed;
		})).apply(instance, instance.stable(DDBiomeProvider::new));
	});
	private final Layer genBiomes;
	private final Layer caveBiomes;
	private static final List<Biome> POSSIBLE_BIOMES = ImmutableList.of(Biomes.OCEAN, Biomes.PLAINS, Biomes.DESERT, Biomes.MOUNTAINS, Biomes.FOREST, Biomes.TAIGA, Biomes.SWAMP, Biomes.RIVER, Biomes.FROZEN_OCEAN, Biomes.FROZEN_RIVER, Biomes.SNOWY_TUNDRA, Biomes.SNOWY_MOUNTAINS, Biomes.MUSHROOM_FIELDS, Biomes.MUSHROOM_FIELD_SHORE, Biomes.BEACH, Biomes.DESERT_HILLS, Biomes.WOODED_HILLS, Biomes.TAIGA_HILLS, Biomes.MOUNTAIN_EDGE, Biomes.JUNGLE, Biomes.JUNGLE_HILLS, Biomes.JUNGLE_EDGE, Biomes.DEEP_OCEAN, Biomes.STONE_SHORE, Biomes.SNOWY_BEACH, Biomes.BIRCH_FOREST, Biomes.BIRCH_FOREST_HILLS, Biomes.DARK_FOREST, Biomes.SNOWY_TAIGA, Biomes.SNOWY_TAIGA_HILLS, Biomes.GIANT_TREE_TAIGA, Biomes.GIANT_TREE_TAIGA_HILLS, Biomes.WOODED_MOUNTAINS, Biomes.SAVANNA, Biomes.SAVANNA_PLATEAU, Biomes.BADLANDS, Biomes.WOODED_BADLANDS_PLATEAU, Biomes.BADLANDS_PLATEAU, Biomes.WARM_OCEAN, Biomes.LUKEWARM_OCEAN, Biomes.COLD_OCEAN, Biomes.DEEP_WARM_OCEAN, Biomes.DEEP_LUKEWARM_OCEAN, Biomes.DEEP_COLD_OCEAN, Biomes.DEEP_FROZEN_OCEAN, Biomes.SUNFLOWER_PLAINS, Biomes.DESERT_LAKES, Biomes.GRAVELLY_MOUNTAINS, Biomes.FLOWER_FOREST, Biomes.TAIGA_MOUNTAINS, Biomes.SWAMP_HILLS, Biomes.ICE_SPIKES, Biomes.MODIFIED_JUNGLE, Biomes.MODIFIED_JUNGLE_EDGE, Biomes.TALL_BIRCH_FOREST, Biomes.TALL_BIRCH_HILLS, Biomes.DARK_FOREST_HILLS, Biomes.SNOWY_TAIGA_MOUNTAINS, Biomes.GIANT_SPRUCE_TAIGA, Biomes.GIANT_SPRUCE_TAIGA_HILLS, Biomes.MODIFIED_GRAVELLY_MOUNTAINS, Biomes.SHATTERED_SAVANNA, Biomes.SHATTERED_SAVANNA_PLATEAU, Biomes.ERODED_BADLANDS, Biomes.MODIFIED_WOODED_BADLANDS_PLATEAU, Biomes.MODIFIED_BADLANDS_PLATEAU);
	private final long seed;
	private final boolean legacyBiomeInitLayer;
	private final boolean largeBiomes;

	public DDBiomeProvider(long seed) {
		super(POSSIBLE_BIOMES);
		this.seed = seed;
		this.legacyBiomeInitLayer = false;
		this.largeBiomes = false;
		this.genBiomes = LayerUtil.func_237215_a_(seed, this.legacyBiomeInitLayer, this.largeBiomes ? 6 : 4, 4);
		this.caveBiomes = CaveLayerUtil.createLayers(seed);
	}

	protected Codec<? extends BiomeProvider> func_230319_a_() {
		return CODEC;
	}

	@OnlyIn(Dist.CLIENT)
	public BiomeProvider func_230320_a_(long seed) {
		return new DDBiomeProvider(seed);
	}

	public Biome getNoiseBiome(int x, int y, int z) {
		if (y <= 11) {
			return this.caveBiomes.func_215738_a(x, z);
		}
		return this.genBiomes.func_215738_a(x, z);
	}
}