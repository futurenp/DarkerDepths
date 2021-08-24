package com.naterbobber.darkerdepths.core;

import com.naterbobber.darkerdepths.core.api.DarkerDepthsCompat;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;

//<>

@Mod.EventBusSubscriber(modid = DarkerDepths.MODID)
public class DarkerDepthsConfig {
    public static final ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec common;

    public static final ForgeConfigSpec.ConfigValue<Boolean> dropSilverRawOre;
    public static final ForgeConfigSpec.ConfigValue<Boolean> generateDarkerDepthsSpeleothems;

    static {
        builder.push("gameplay");
            dropSilverRawOre = builder.define("silver drops raw ore", DarkerDepthsCompat.CAVES_AND_CLIFFS);
        builder.pop();

        builder.push("worldgen");
            generateDarkerDepthsSpeleothems = builder.define("generates darker depths's speleothems naturally", DarkerDepthsCompat.QUARK);
        builder.pop();

        common = builder.build();
    }
}