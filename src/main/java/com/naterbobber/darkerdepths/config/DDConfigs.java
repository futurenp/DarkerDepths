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

    static {
        BUILDER.push("Darker Depths Config");

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