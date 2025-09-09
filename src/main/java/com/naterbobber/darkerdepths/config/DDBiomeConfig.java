package com.naterbobber.darkerdepths.config;

import net.minecraftforge.common.ForgeConfigSpec;

public record DDBiomeConfig(
        ForgeConfigSpec.DoubleValue tempMin, ForgeConfigSpec.DoubleValue tempMax,
        ForgeConfigSpec.DoubleValue humidityMin, ForgeConfigSpec.DoubleValue humidityMax,
        ForgeConfigSpec.DoubleValue continentalnessMin, ForgeConfigSpec.DoubleValue continentalnessMax,
        ForgeConfigSpec.DoubleValue erosionMin, ForgeConfigSpec.DoubleValue erosionMax,
        ForgeConfigSpec.DoubleValue weirdnessMin, ForgeConfigSpec.DoubleValue weirdnessMax,
        ForgeConfigSpec.DoubleValue depthMin, ForgeConfigSpec.DoubleValue depthMax,
        ForgeConfigSpec.DoubleValue offset) {

    public static DDBiomeConfig create(ForgeConfigSpec.Builder builder, String biomeName, Defaults defaults) {
        builder.comment("Climate parameters for the " + biomeName + " biome.").push(biomeName);

        var tempMin = builder.defineInRange("temperature_min", defaults.tempMin, -2.0, 2.0);
        var tempMax = builder.defineInRange("temperature_max", defaults.tempMax, -2.0, 2.0);

        var humidityMin = builder.defineInRange("humidity_min", defaults.humidityMin, -2.0, 2.0);
        var humidityMax = builder.defineInRange("humidity_max", defaults.humidityMax, -2.0, 2.0);

        var continentalnessMin = builder.defineInRange("continentalness_min", defaults.continentalnessMin, -2.0, 2.0);
        var continentalnessMax = builder.defineInRange("continentalness_max", defaults.continentalnessMax, -2.0, 2.0);

        var erosionMin = builder.defineInRange("erosion_min", defaults.erosionMin, -2.0, 2.0);
        var erosionMax = builder.defineInRange("erosion_max", defaults.erosionMax, -2.0, 2.0);

        var depthMin = builder.defineInRange("depth_min", defaults.depthMin, -2.0, 2.0);
        var depthMax = builder.defineInRange("depth_max", defaults.depthMax, -2.0, 2.0);

        var weirdnessMin = builder.defineInRange("weirdness_min", defaults.weirdnessMin, -2.0, 2.0);
        var weirdnessMax = builder.defineInRange("weirdness_max", defaults.weirdnessMax, -2.0, 2.0);

        var offset = builder.defineInRange("offset", defaults.offset, -2.0, 2.0);

        builder.pop();

        return new DDBiomeConfig(tempMin, tempMax, humidityMin, humidityMax, continentalnessMin, continentalnessMax,
                erosionMin, erosionMax, weirdnessMin, weirdnessMax, depthMin, depthMax, offset);
    }

    public record Defaults(double tempMin, double tempMax, double humidityMin, double humidityMax, double continentalnessMin, double continentalnessMax,
                           double erosionMin, double erosionMax, double weirdnessMin, double weirdnessMax, double depthMin, double depthMax, double offset) {}
}