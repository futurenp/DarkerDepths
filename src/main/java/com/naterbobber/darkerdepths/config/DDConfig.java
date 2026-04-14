package com.naterbobber.darkerdepths.config;

import com.naterbobber.darkerdepths.config.builders.DDBiomeConfigBuilder;
import net.minecraftforge.common.ForgeConfigSpec;

public class DDConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.ConfigValue<Integer> SUPERCHARGE_DURATION;
    public static final ForgeConfigSpec.ConfigValue<Integer> SUPERCHARGE_DIG_SPEED;
    public static final ForgeConfigSpec.ConfigValue<Integer> SUPERCHARGE_ATTACK_SPEED;
    public static final ForgeConfigSpec.ConfigValue<Integer> SUPERCHARGE_ATTACK_DAMAGE;
    public static final ForgeConfigSpec.ConfigValue<Boolean> SUPERCHARGE_UNBREAKABLE;
    public static final ForgeConfigSpec.ConfigValue<Boolean> PARANOIA_ALTAR_EFFECTS_CREATIVE;
    public static final ForgeConfigSpec.ConfigValue<Integer> PARANOIA_ALTAR_RADIUS_HORIZONTAL;
    public static final ForgeConfigSpec.ConfigValue<Integer> PARANOIA_ALTAR_RADIUS_VERTICAL;

    public static final DDBiomeConfigBuilder SANDY_CATACOMBS_CLIMATE;
    public static final DDBiomeConfigBuilder GLOWSHROOM_FOREST_CLIMATE;
    public static final DDBiomeConfigBuilder MOLTEN_CAVERN_CLIMATE;

    public static final ForgeConfigSpec.ConfigValue<Boolean> ENABLE_BIOME_FOG;
    public static final ForgeConfigSpec.ConfigValue<Integer> MOLTEN_CAVERN_FOG_MIN;
    public static final ForgeConfigSpec.ConfigValue<Integer> MOLTEN_CAVERN_FOG_MAX;
    public static final ForgeConfigSpec.ConfigValue<Integer> SANDY_CATACOMBS_FOG_MIN;
    public static final ForgeConfigSpec.ConfigValue<Integer> SANDY_CATACOMBS_FOG_MAX;
    public static final ForgeConfigSpec.ConfigValue<Integer> GLOWSHROOM_FOREST_FOG_MIN;
    public static final ForgeConfigSpec.ConfigValue<Integer> GLOWSHROOM_FOREST_FOG_MAX;

    public static final ForgeConfigSpec.ConfigValue<Boolean> DD_BIOME_INJECTION;
    public static final ForgeConfigSpec.ConfigValue<Boolean> USE_DEFAULTS_TERRABLENDER;
    public static final ForgeConfigSpec.ConfigValue<Integer> OVERWORLD_BIOME_WEIGHT_TERRABLENDER;
    public static final ForgeConfigSpec.ConfigValue<Boolean> DISABLE_HEATABLE_BLOCK_BAKING;
    public static final ForgeConfigSpec.ConfigValue<Integer> HEAT_BAKE_BUDGET;


    static {
        BUILDER.push("Biomes");
        DD_BIOME_INJECTION = BUILDER.comment("Darker Depths biome & surfacerule injection").define("biome_injection", true);
        BUILDER.push("Terrablender");
        OVERWORLD_BIOME_WEIGHT_TERRABLENDER = BUILDER.comment("Darker Depths Terrablender region weight").defineInRange("weight", 4, 0, Integer.MAX_VALUE);
        USE_DEFAULTS_TERRABLENDER = BUILDER
                .comment("Biome parameters are set to much more lenient values for Terrablender due to regions. To disable the defaults and use the config parameters, set this to false.")
                .define("use_default_biome_params_terrablender", true);
        BUILDER.pop();
        var sandyCatacombsDefaults = new DDBiomeConfigBuilder.Defaults(
                0.5, 0.9,
                -0.8, -0.1,
                0.2, 0.3,
                -0.2, 0.7,
                -1.0, 1.0,
                0.3, 1.5,
                0.0
        );
        SANDY_CATACOMBS_CLIMATE = DDBiomeConfigBuilder.create(BUILDER, "sandy_catacombs", sandyCatacombsDefaults);

        var glowshroomForestDefaults = new DDBiomeConfigBuilder.Defaults(
                0.4, 1,
                0.6, 1.2,
                -0.5, 0.5,
                0.1, 0.5,
                -1.0, 1.0,
                0.3, 0.9,
                0.0
        );
        GLOWSHROOM_FOREST_CLIMATE = DDBiomeConfigBuilder.create(BUILDER, "glowshroom_forest", glowshroomForestDefaults);

        var moltenCavernDefaults = new DDBiomeConfigBuilder.Defaults(
                -1.0, 0.7,
                -0.3, 1.0,
                0.55, 0.7,
                -0.4, 0.4,
                -1.0, 1.0,
                0.7, 2.0,
                0.0
        );
        MOLTEN_CAVERN_CLIMATE = DDBiomeConfigBuilder.create(BUILDER, "molten_cavern", moltenCavernDefaults);
        BUILDER.pop();

        BUILDER.push("Biome Fog");
        ENABLE_BIOME_FOG = BUILDER.comment("Enable/disable Darker Depths biome fog adjustments:").define("biome_fog", true);
        BUILDER.push("Glowshroom Forest");
        GLOWSHROOM_FOREST_FOG_MIN = BUILDER.comment("Min fog distance:").defineInRange("min_fog", 0, -1000, 1000);
        GLOWSHROOM_FOREST_FOG_MAX = BUILDER.comment("Max fog distance:").defineInRange("max_fog", 96, -1000, 1000);
        BUILDER.pop();
        BUILDER.push("Molten Cavern");
        MOLTEN_CAVERN_FOG_MIN = BUILDER.comment("Min fog distance:").defineInRange("min_fog", 0, -1000, 1000);
        MOLTEN_CAVERN_FOG_MAX = BUILDER.comment("Max fog distance:").defineInRange("max_fog", 96, -1000, 1000);
        BUILDER.pop();
        BUILDER.push("Sandy Catacombs");
        SANDY_CATACOMBS_FOG_MIN = BUILDER.comment("Min fog distance:").defineInRange("min_fog", -10, -1000, 1000);
        SANDY_CATACOMBS_FOG_MAX = BUILDER.comment("Max fog distance:").defineInRange("max_fog", 128, -1000, 1000);
        BUILDER.pop(2);

        BUILDER.push("Supercharges");
        SUPERCHARGE_DURATION = BUILDER.comment("Duration of the supercharge buff in minutes")
                .defineInRange("supercharge_minutes", 5, 1, 3600);

        BUILDER.comment("Supercharge Multipliers - Increases in percentage:");

        SUPERCHARGE_DIG_SPEED = BUILDER.comment("Dig speed increase:")
                .defineInRange("supercharge_dig_speed", 20, 0, 1000);

        SUPERCHARGE_ATTACK_SPEED = BUILDER.comment("Attack speed increase:")
                .defineInRange("supercharge_attack_speed", 20, 0, 1000);

        SUPERCHARGE_ATTACK_DAMAGE = BUILDER.comment("Attack damage increase:")
                .defineInRange("supercharge_attack_damage", 20, 0, 1000);

        SUPERCHARGE_UNBREAKABLE = BUILDER.comment("Supercharge tool unbreakablity:").define("supercharge_unbreakable", true);
        BUILDER.pop();


        BUILDER.push("Paranoia Altar");
        PARANOIA_ALTAR_EFFECTS_CREATIVE = BUILDER.comment("Allows players in creative mode to be effected by the Paranoia Altar")
                .define("paranoia_altar_effects_creative", false);

        PARANOIA_ALTAR_RADIUS_HORIZONTAL = BUILDER.defineInRange("paranoia_altar_horizontal_range", 72, 0, 128);
        PARANOIA_ALTAR_RADIUS_VERTICAL = BUILDER.defineInRange("paranoia_altar_vertical_range", 8, 0, 128);
        BUILDER.pop();


        BUILDER.push("Performance");
        HEAT_BAKE_BUDGET = BUILDER.comment("Blocks like Darkslate are 'baked' into generation so that all of their stages are set correctly after world generation. \n" +
                        "This budget controls how many resources are provided to bake each tick. \n" +
                        "Setting this to a high value will result in more lag when generating, but will finish baking faster. \n" +
                        "Recommended to keep this value low (< 2500) as baking is typically not a priority.\n" +
                        "A value too low will result in worse performance overall as chunks will repeatedly have to be revisited as baking has not completed.")
                .defineInRange("heat_bake_budget",2500, 500, Integer.MAX_VALUE);

        DISABLE_HEATABLE_BLOCK_BAKING = BUILDER.comment("Disable baking heatable blocks (not recommended!)\n" +
                        "Blocks such as Darkslate will not generate with the correct stages and will not be updated to be correct heat values upon generation if true.\n" +
                        "Baking does require additional resources, so disabling should improve world gen performance.")
                .define("disable_heatable_block_baking", false);
        BUILDER.pop();

        SPEC = BUILDER.build();
    }
}