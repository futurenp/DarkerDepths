package com.naterbobber.darkerdepths.util;

import com.mojang.datafixers.util.Pair;
import com.naterbobber.darkerdepths.DarkerDepths;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;

import java.util.function.Consumer;

public class BiomeReagentHandler {

    public static final Climate.Parameter FULL_RANGE = Climate.Parameter.span(-1.0F, 1.0F);
    public static final Climate.Parameter DEFAULT_CAVE_BIOME_RANGE = Climate.Parameter.span(0.2F, 0.9F);

    public static ResourceKey<Biome> MOLTEN_CAVERN;
    public static ResourceKey<Biome> SANDY_CATACOMBS;
    public static ResourceKey<Biome> GLOWSHROOM_FOREST;

    public static void init(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> consumer) {
        if (MOLTEN_CAVERN == null)
            MOLTEN_CAVERN = ResourceKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(DarkerDepths.MODID, "molten_cavern"));

        if (SANDY_CATACOMBS == null)
            SANDY_CATACOMBS = ResourceKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(DarkerDepths.MODID, "sandy_catacombs"));

        if (GLOWSHROOM_FOREST == null)
            GLOWSHROOM_FOREST = ResourceKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(DarkerDepths.MODID, "glowshroom_forest"));


        consumer.accept(Pair.of(Climate.parameters(Climate.Parameter.span(0.8F, 1.0F), Climate.Parameter.span(0.4F, 1.0F), FULL_RANGE, FULL_RANGE, DEFAULT_CAVE_BIOME_RANGE, FULL_RANGE, 0.0F), MOLTEN_CAVERN));
        consumer.accept(Pair.of(Climate.parameters(Climate.Parameter.span(0.0F, 1.0F), Climate.Parameter.span(0.1F, 1.0F), Climate.Parameter.span(0.3F, 1.0F), Climate.Parameter.span(0.5F, 1.0F), DEFAULT_CAVE_BIOME_RANGE, FULL_RANGE, 0.0F), SANDY_CATACOMBS));
        consumer.accept(Pair.of(Climate.parameters(Climate.Parameter.span(-1.0F, -0.5F), Climate.Parameter.span(0.5F, 1.0F), FULL_RANGE, Climate.Parameter.span(0.2F, 1.0F), DEFAULT_CAVE_BIOME_RANGE, Climate.Parameter.span(-0.5F, 0.1F), 0.0F), GLOWSHROOM_FOREST));

    }

    /**
     * GLOWSHROOM FOREST
     * TEMPERATURE: -1--0.5
     * HUMIDITY: 0.5-1.0
     * CONTINENTALNESS: FULL RANGE
     * EROSION: 0.2-1.0
     * WEIRDNESS: -0.5-0.1
     * DEPTH: 0.2-0.9
     */

    /**
     * SANDY CATACOMBS PARAMETERS
     * TEMPERATURE: 0.0-1.0
     * HUMIDITY: 0.1-1.0
     * CONTINENTALNESS: 0.3-1.0
     * EROSION: 0.5-1.0
     * WEIRDNESS: FULL RANGE
     * DEPTH: 0.2-0.9
     */

    /**
     * MOLTEN CAVERN PARAMTERS
     * TEMPERATURE: 0.8-1.0
     * HUMIDITY: 0.4-1.0
     * CONTINENTALNESS: FULL RANGE
     * EROSION: FULL RANGE
     * DEPTH: 0.2-0.9
     * WEIRDNESS: FULL RANGE
     * OFFSET: 0.0F
     */

    /**
     * DRIPSTONE CAVES PARAMETERS
     * TEMPERATURE: FULL RANGE
     * HUMIDITY: FULL RANGE
     * CONTINENTALNESS: 0.8-1.0
     * EROSION: FULL RANGE
     * DEPTH: 0.2-0.9
     * WEIRDNESS: FULL RANGE
     * OFFSET: 0.0F
     */

    /**
     * LUSH CAVES PARAMETERS
     * TEMPERATURE: FULL RANGE
     * HUMIDITY: 0.7-1.0
     * CONTINENTALNESS: FULL RANGE
     * EROSION: FULL RANGE
     * DEPTH: 0.2-0.9
     * WEIRDNESS: FULL RANGE
     * OFFSET: 0.0F
     */

    /**
     *
     * @param consumer
     * @param tempereature
     * @param humidity
     * @param continentalness
     * @param erosion
     * @param depth
     * @param weirdness
     * @param offset
     * @param keyBiome
     */
    private static void addUndergroundBiome(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> consumer, Climate.Parameter tempereature, Climate.Parameter humidity, Climate.Parameter continentalness, Climate.Parameter erosion, Climate.Parameter depth, Climate.Parameter weirdness, float offset, ResourceKey<Biome> keyBiome) {
        consumer.accept(Pair.of(Climate.parameters(tempereature, humidity, continentalness, erosion, depth, weirdness, offset), keyBiome));
    }

}
