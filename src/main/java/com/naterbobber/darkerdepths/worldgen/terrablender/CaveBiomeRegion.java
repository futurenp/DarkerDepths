
package com.naterbobber.darkerdepths.worldgen.terrablender;

import com.mojang.datafixers.util.Pair;
import com.naterbobber.darkerdepths.config.DDConfig;
import com.naterbobber.darkerdepths.util.DDResourceKeys;
import com.naterbobber.darkerdepths.worldgen.ClimateParamsUtil;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import terrablender.api.Region;
import terrablender.api.RegionType;
import terrablender.api.VanillaParameterOverlayBuilder;

import java.util.function.Consumer;

import static terrablender.api.ParameterUtils.*;

public class CaveBiomeRegion extends Region {
    private static final Climate.Parameter WEIRDNESS = Climate.Parameter.span(-1.0F, 1.0F);
    public CaveBiomeRegion(ResourceLocation name, int weight)
    {
        super(name, RegionType.OVERWORLD, weight);
    }

    @Override
    public void addBiomes(Registry<Biome> registry, Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> mapper)
    {
        addModifiedVanillaOverworldBiomes(mapper, b -> {});

        if(DDConfig.CONFIG.USE_DEFAULTS_TERRABLENDER.get()) {
            this.addBiome(mapper,
                    Temperature.span(Temperature.NEUTRAL, Temperature.HOT),
                    Humidity.span(Humidity.NEUTRAL, Humidity.HUMID),
                    Continentalness.span(Continentalness.OCEAN, Continentalness.NEAR_INLAND),
                    Erosion.FULL_RANGE.parameter(),
                    WEIRDNESS,
                    Depth.UNDERGROUND.parameter(),
                    0.0f,
                    DDResourceKeys.Biomes.GLOWSHROOM_FOREST);

            this.addBiome(mapper,
                    Temperature.FULL_RANGE.parameter(),
                    Humidity.span(Humidity.DRY, Humidity.WET),
                    Continentalness.span(Continentalness.MID_INLAND, Continentalness.FAR_INLAND),
                    Erosion.span(Erosion.EROSION_0, Erosion.EROSION_2),
                    WEIRDNESS,
                    Depth.span(Depth.UNDERGROUND, Depth.FLOOR),
                    0.0f,
                    DDResourceKeys.Biomes.MOLTEN_CAVERN);

            this.addBiome(mapper,
                    Temperature.span(Temperature.WARM, Temperature.HOT),
                    Humidity.span(Humidity.DRY, Humidity.ARID),
                    Continentalness.span(Continentalness.NEAR_INLAND, Continentalness.MID_INLAND),
                    Erosion.FULL_RANGE.parameter(),
                    WEIRDNESS,
                    Depth.UNDERGROUND.parameter(),
                    0.0f,
                    DDResourceKeys.Biomes.SANDY_CATACOMBS);
        }
        else {
            this.addBiome(mapper, ClimateParamsUtil.climateParamsFromConfig(DDConfig.CONFIG.GLOWSHROOM_FOREST_CLIMATE),
                    DDResourceKeys.Biomes.GLOWSHROOM_FOREST);

            this.addBiome(mapper, ClimateParamsUtil.climateParamsFromConfig(DDConfig.CONFIG.MOLTEN_CAVERN_CLIMATE),
                    DDResourceKeys.Biomes.MOLTEN_CAVERN);

            this.addBiome(mapper, ClimateParamsUtil.climateParamsFromConfig(DDConfig.CONFIG.SANDY_CATACOMBS_CLIMATE),
                    DDResourceKeys.Biomes.SANDY_CATACOMBS);
        }
    }
}