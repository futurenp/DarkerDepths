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
    public static final ForgeConfigSpec.ConfigValue<Boolean> PARANOIA_ALTAR_EFFECTS_CREATIVE;
    public static final ForgeConfigSpec.ConfigValue<Integer> PARANOIA_ALTAR_RADIUS_HORIZONTAL;
    public static final ForgeConfigSpec.ConfigValue<Integer> PARANOIA_ALTAR_RADIUS_VERTICAL;

    public static final DDBiomeConfig SANDY_CATACOMBS_CLIMATE;
    public static final DDBiomeConfig GLOWSHROOM_FOREST_CLIMATE;
    public static final DDBiomeConfig MOLTEN_CAVERN_CLIMATE;


    static {
        BUILDER.push("Biomes");
        var sandyCatacombsDefaults = new DDBiomeConfig.Defaults(
                0.5, 1.2,
                -1.0, -0.4,
                0.1, 0.3,
                -0.25, 1.0,
                -1.0, 1.0,
                0.3, 2.0,
                0.0
        );
        SANDY_CATACOMBS_CLIMATE = DDBiomeConfig.create(BUILDER, "sandy_catacombs", sandyCatacombsDefaults);

        var glowshroomForestDefaults = new DDBiomeConfig.Defaults(
                0.4, 1,
                0.6, 1.2,
                -0.5, 0.5,
                0.1, 0.5,
                -1.0, 1.0,
                0.3, 0.9,
                0.0
        );
        GLOWSHROOM_FOREST_CLIMATE = DDBiomeConfig.create(BUILDER, "glowshroom_forest", glowshroomForestDefaults);

        var moltenCavernDefaults = new DDBiomeConfig.Defaults(
                -0.75, 0.75,
                -1.0, 1.0,
                0.65, 0.8,
                -0.325, 0,
                -1.0, 1.0,
                0.8, 2.0,
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


        BUILDER.push("Paranoia Altar");
        PARANOIA_ALTAR_EFFECTS_CREATIVE = BUILDER.comment("Allows players in creative mode to be effected by the Paranoia Altar")
                .define("paranoia_altar_effects_creative", false);

        PARANOIA_ALTAR_RADIUS_HORIZONTAL = BUILDER.defineInRange("paranoia_altar_horizontal_range", 72, 0, 128);
        PARANOIA_ALTAR_RADIUS_VERTICAL = BUILDER.defineInRange("paranoia_altar_vertical_range", 8, 0, 128);
        BUILDER.pop();

        SPEC = BUILDER.build();
    }
}