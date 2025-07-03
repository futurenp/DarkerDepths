package com.naterbobber.darkerdepths.init;

import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class DDBlockStateProperties {
    public static final IntegerProperty CRACKED = IntegerProperty.create("cracked", 0, 3);
    public static final IntegerProperty CRYSTAL_LEVEL = IntegerProperty.create("crystal_level", 0, 2);
}
