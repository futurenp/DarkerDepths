package com.naterbobber.darkerdepths.config.builders;

import net.neoforged.neoforge.common.ModConfigSpec;

public class DDConfigBuilder {
    public final ModConfigSpec.IntValue SUPERCHARGE_DURATION;
    public final ModConfigSpec.IntValue SUPERCHARGE_DIG_SPEED;
    public final ModConfigSpec.IntValue SUPERCHARGE_ATTACK_SPEED;
    public final ModConfigSpec.IntValue SUPERCHARGE_ATTACK_DAMAGE;
    public final ModConfigSpec.BooleanValue SUPERCHARGE_UNBREAKABLE;
    public final ModConfigSpec.BooleanValue PARANOIA_ALTAR_EFFECTS_CREATIVE;
    public final ModConfigSpec.IntValue PARANOIA_ALTAR_RADIUS_HORIZONTAL;
    public final ModConfigSpec.IntValue PARANOIA_ALTAR_RADIUS_VERTICAL;
    public final ModConfigSpec.BooleanValue ENABLE_BIOME_FOG;
    public final ModConfigSpec.IntValue MOLTEN_CAVERN_FOG_MIN;
    public final ModConfigSpec.IntValue MOLTEN_CAVERN_FOG_MAX;
    public final ModConfigSpec.IntValue SANDY_CATACOMBS_FOG_MIN;
    public final ModConfigSpec.IntValue SANDY_CATACOMBS_FOG_MAX;
    public final ModConfigSpec.IntValue GLOWSHROOM_FOREST_FOG_MIN;
    public final ModConfigSpec.IntValue GLOWSHROOM_FOREST_FOG_MAX;
    public final DDBiomeConfigBuilder SANDY_CATACOMBS_CLIMATE;
    public final DDBiomeConfigBuilder GLOWSHROOM_FOREST_CLIMATE;
    public final DDBiomeConfigBuilder MOLTEN_CAVERN_CLIMATE;
    public final ModConfigSpec.BooleanValue PRIORITIZE_TERRABLENDER;
    public final ModConfigSpec.BooleanValue USE_DEFAULTS_TERRABLENDER;
    public final ModConfigSpec.IntValue OVERWORLD_BIOME_WEIGHT_TERRABLENDER;

    public DDConfigBuilder(ModConfigSpec.Builder builder) {
        builder.push("Biome Provider");
        PRIORITIZE_TERRABLENDER = builder
                .comment("If Terrablender and Biolith are installed at the same time, Terrablender is used by default. Set this to false to use Biolith.")
                .comment("If only one is installed, Darker Depths will use whichever is available.")
                .define("prioritize_terrablender", true);
        builder.pop();

        builder.push("Biome Parameters");
        builder.push("Terrablender");
        OVERWORLD_BIOME_WEIGHT_TERRABLENDER = builder.comment("Darker Depths Terrablender region weight").defineInRange("weight", 4, 0, Integer.MAX_VALUE);
        USE_DEFAULTS_TERRABLENDER = builder
                .comment("Biome parameters are set to much more lenient values for Terrablender due to regions. To disable the defaults and use the config parameters, set this to false.")
                .define("use_default_biome_params_terrablender", true);
        builder.pop();
        builder.comment("These values will only be used when using biolith unless defaults for Terrablender are turned off.");
        var sandyCatacombsDefaults = new DDBiomeConfigBuilder.Defaults(
                0.5, 0.9,
                -0.8, -0.1,
                0.2, 0.3,
                -0.2, 0.7,
                -1.0, 1.0,
                0.3, 1.5,
                0.0
        );
        SANDY_CATACOMBS_CLIMATE = DDBiomeConfigBuilder.create(builder, "sandy_catacombs", sandyCatacombsDefaults);

        var glowshroomForestDefaults = new DDBiomeConfigBuilder.Defaults(
                0.4, 1,
                0.6, 1.2,
                -0.5, 0.5,
                0.1, 0.5,
                -1.0, 1.0,
                0.3, 0.9,
                0.0
        );
        GLOWSHROOM_FOREST_CLIMATE = DDBiomeConfigBuilder.create(builder, "glowshroom_forest", glowshroomForestDefaults);

        var moltenCavernDefaults = new DDBiomeConfigBuilder.Defaults(
                -1.0, 0.7,
                -0.3, 1.0,
                0.55, 0.7,
                -0.4, 0.4,
                -1.0, 1.0,
                0.7, 2.0,
                0.0
        );
        MOLTEN_CAVERN_CLIMATE = DDBiomeConfigBuilder.create(builder, "molten_cavern", moltenCavernDefaults);
        builder.pop();

        builder.push("Biome Fog");
        ENABLE_BIOME_FOG = builder.comment("Enable/disable Darker Depths biome fog adjustments:").define("biome_fog", true);
        builder.push("Glowshroom Forest");
        GLOWSHROOM_FOREST_FOG_MIN = builder.comment("Min fog distance:").defineInRange("min_fog", 0, -1000, 1000);
        GLOWSHROOM_FOREST_FOG_MAX = builder.comment("Max fog distance:").defineInRange("max_fog", 96, -1000, 1000);
        builder.pop();
        builder.push("Molten Cavern");
        MOLTEN_CAVERN_FOG_MIN = builder.comment("Min fog distance:").defineInRange("min_fog", 0, -1000, 1000);
        MOLTEN_CAVERN_FOG_MAX = builder.comment("Max fog distance:").defineInRange("max_fog", 96, -1000, 1000);
        builder.pop();
        builder.push("Sandy Catacombs");
        SANDY_CATACOMBS_FOG_MIN = builder.comment("Min fog distance:").defineInRange("min_fog", -10, -1000, 1000);
        SANDY_CATACOMBS_FOG_MAX = builder.comment("Max fog distance:").defineInRange("max_fog", 128, -1000, 1000);
        builder.pop(2);

        builder.push("Supercharges");
        SUPERCHARGE_DURATION = builder.comment("Duration of the supercharge buff in minutes")
                .defineInRange("supercharge_minutes", 5, 1, 3600);

        builder.comment("Supercharge Multipliers - Increases in percentage:");

        SUPERCHARGE_DIG_SPEED = builder.comment("Dig speed increase:")
                .defineInRange("supercharge_dig_speed", 20, 0, 1000);

        SUPERCHARGE_ATTACK_SPEED = builder.comment("Attack speed increase:")
                .defineInRange("supercharge_attack_speed", 20, 0, 1000);

        SUPERCHARGE_ATTACK_DAMAGE = builder.comment("Attack damage increase:")
                .defineInRange("supercharge_attack_damage", 20, 0, 1000);

        SUPERCHARGE_UNBREAKABLE = builder.comment("Supercharge tool unbreakablity:").define("supercharge_unbreakable", true);
        builder.pop();


        builder.push("Paranoia Altar");
        PARANOIA_ALTAR_EFFECTS_CREATIVE = builder.comment("Allows players in creative mode to be effected by the Paranoia Altar")
                .define("paranoia_altar_effects_creative", false);

        PARANOIA_ALTAR_RADIUS_HORIZONTAL = builder.defineInRange("paranoia_altar_horizontal_range", 72, 0, 128);
        PARANOIA_ALTAR_RADIUS_VERTICAL = builder.defineInRange("paranoia_altar_vertical_range", 8, 0, 128);
        builder.pop();
    }
}