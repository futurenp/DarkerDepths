
package com.naterbobber.darkerdepths.worldgen.terrablender;

import com.mojang.datafixers.util.Pair;
import com.naterbobber.darkerdepths.util.DDResourceKeys;
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

public class CaveBiomeRegion extends Region
{
    public CaveBiomeRegion(ResourceLocation name, int weight)
    {
        super(name, RegionType.OVERWORLD, weight);
    }

    @Override
    public void addBiomes(Registry<Biome> registry, Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> mapper)
    {
        VanillaParameterOverlayBuilder builder = new VanillaParameterOverlayBuilder();

        this.addBiome(mapper,
                Temperature.span(Temperature.NEUTRAL, Temperature.HOT),
                Humidity.span(Humidity.NEUTRAL, Humidity.HUMID),
                Continentalness.span(Continentalness.OCEAN, Continentalness.NEAR_INLAND),
                Erosion.FULL_RANGE.parameter(),
                Weirdness.MID_SLICE_NORMAL_ASCENDING.parameter(),
                Climate.Parameter.span(0.3F, 0.9F),
                0.0f,
                DDResourceKeys.Biomes.GLOWSHROOM_FOREST);

        this.addBiome(mapper, 
                Temperature.FULL_RANGE.parameter(),
                Humidity.span(Humidity.DRY, Humidity.WET),
                Continentalness.span(Continentalness.MID_INLAND, Continentalness.FAR_INLAND),
                Erosion.span(Erosion.EROSION_0, Erosion.EROSION_2),
                Weirdness.MID_SLICE_NORMAL_ASCENDING.parameter(),
                Climate.Parameter.span(0.3F, 1.0F),
                0.0f,
                DDResourceKeys.Biomes.MOLTEN_CAVERN);

        this.addBiome(mapper,
                Temperature.span(Temperature.WARM, Temperature.HOT),
                Humidity.span(Humidity.DRY, Humidity.ARID),
                Continentalness.span(Continentalness.NEAR_INLAND, Continentalness.MID_INLAND),
                Erosion.FULL_RANGE.parameter(),
                Weirdness.MID_SLICE_NORMAL_ASCENDING.parameter(),
                Climate.Parameter.span(0.3F, 0.9F),
                0.0f,
                DDResourceKeys.Biomes.SANDY_CATACOMBS);

        builder.build().forEach(mapper);
    }
}