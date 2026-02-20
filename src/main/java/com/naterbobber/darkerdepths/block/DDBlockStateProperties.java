package com.naterbobber.darkerdepths.block;

import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class DDBlockStateProperties {
    public static final IntegerProperty CRYSTAL_GROWTH_LEVEL = IntegerProperty.create("crystal_growth_level", 0, 3);
    public static final IntegerProperty MELON_GROWTH_LEVEL = IntegerProperty.create("melon_growth_level", 0, 2);
    public static final IntegerProperty HEAT_LEVEL = IntegerProperty.create("heat_level", 0, 4);
}
