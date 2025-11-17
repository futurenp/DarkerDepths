package com.naterbobber.darkerdepths.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class DDConfigValues {
    public final ModConfigSpec.IntValue SUPERCHARGE_DURATION;
    public final ModConfigSpec.IntValue SUPERCHARGE_DIG_SPEED;
    public final ModConfigSpec.IntValue SUPERCHARGE_ATTACK_SPEED;
    public final ModConfigSpec.IntValue SUPERCHARGE_ATTACK_DAMAGE;
    public final ModConfigSpec.BooleanValue SUPERCHARGE_UNBREAKABLE;
    public final ModConfigSpec.BooleanValue PARANOIA_ALTAR_EFFECTS_CREATIVE;
    public final ModConfigSpec.IntValue PARANOIA_ALTAR_RADIUS_HORIZONTAL;
    public final ModConfigSpec.IntValue PARANOIA_ALTAR_RADIUS_VERTICAL;

    public DDConfigValues(ModConfigSpec.Builder builder) {
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