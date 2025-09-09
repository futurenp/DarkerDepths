package com.naterbobber.darkerdepths.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class DDConfigs {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.ConfigValue<Integer> SUPERCHARGE_MINUTES;
    public static final ForgeConfigSpec.ConfigValue<Integer> SUPERCHARGE_DIG_SPEED;
    public static final ForgeConfigSpec.ConfigValue<Integer> SUPERCHARGE_ATTACK_SPEED;
    public static final ForgeConfigSpec.ConfigValue<Integer> SUPERCHARGE_ATTACK_DAMAGE;
    public static final ForgeConfigSpec.ConfigValue<Boolean> SUPERCHARGE_UNBREAKABLE;
    public static final DDBiomeConfig SANDY_CATACOMBS_CLIMATE;
    public static final DDBiomeConfig GLOWSHROOM_FOREST_CLIMATE;
    public static final DDBiomeConfig MOLTEN_CAVERN_CLIMATE;


    static {
        BUILDER.push("Biomes");

        var sandyCatacombsDefaults = new DDBiomeConfig.Defaults(
                0.5, 2.0,
                -1.0, -0.4,
                0.1, 0.3,
                -0.8, 2.0,
                -1.0, 1.0,
                0.3, 2.0,
                0.0
        );
        SANDY_CATACOMBS_CLIMATE = DDBiomeConfig.create(BUILDER, "sandy_catacombs", sandyCatacombsDefaults);

        var glowshroomForestDefaults = new DDBiomeConfig.Defaults(
                0.5, 1.4,
                0.5, 2.0,
                0.35, 0.5,
                0.275, 1.0,
                -1.0, 1.0,
                0.3, 0.9,
                0.0
        );
        GLOWSHROOM_FOREST_CLIMATE = DDBiomeConfig.create(BUILDER, "glowshroom_forest", glowshroomForestDefaults);

        var moltenCavernDefaults = new DDBiomeConfig.Defaults(
                -2.0, 2.0,
                -2.0, 2.0,
                0.85, 1.05,
                -2.0, 0.0,
                -1.0, 1.0,
                0.5, 2.0,
                0.0
        );
        MOLTEN_CAVERN_CLIMATE = DDBiomeConfig.create(BUILDER, "molten_cavern", moltenCavernDefaults);

        BUILDER.pop();

        BUILDER.push("Supercharges");

        SUPERCHARGE_MINUTES = BUILDER.comment("Duration of the supercharge buff in minutes")
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

        SPEC = BUILDER.build();
    }
}