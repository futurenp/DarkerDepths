package com.naterbobber.darkerdepths.block;

import com.naterbobber.darkerdepths.block.blockstates.PillarState;
import com.naterbobber.darkerdepths.block.blockstates.VerticalSlabState;
import com.naterbobber.darkerdepths.block.custom.RopeBlock;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class DDBlockStateProperties {
    public static final IntegerProperty CRYSTAL_GROWTH_LEVEL = IntegerProperty.create("crystal_growth_level", 0, 3);
    public static final IntegerProperty MELON_GROWTH_LEVEL = IntegerProperty.create("melon_growth_level", 0, 2);
    public static final BooleanProperty INHABITED = BooleanProperty.create("inhabited");
    public static final EnumProperty<RopeBlock.RopePart> PART = EnumProperty.create("part", RopeBlock.RopePart.class);
    public static final IntegerProperty GLOWSHROOM_CLUSTERS = IntegerProperty.create("clusters", 1, 3);
    public static final EnumProperty<PillarState> PILLAR_STATE = EnumProperty.create("pillar_state", PillarState.class);
    public static final EnumProperty<VerticalSlabState> VERTICAL_SLAB_STATE = EnumProperty.create("type", VerticalSlabState.class);
    public static final IntegerProperty HEAT_LEVEL = IntegerProperty.create("heat_level", 0, 4);
    public static final BooleanProperty BURSTING = BooleanProperty.create("bursting");
    public static final BooleanProperty BOOSTED = BooleanProperty.create("boosted");
    public static final BooleanProperty PROVIDES_ASH = BooleanProperty.create("provides_ash");
    public static final BooleanProperty CHAIN_DOWN = BooleanProperty.create("chain_down");
    public static final BooleanProperty CHAIN_UP = BooleanProperty.create("chain_up");
    public static final BooleanProperty CHAIN_NORTH = BooleanProperty.create("chain_north");
    public static final BooleanProperty CHAIN_SOUTH = BooleanProperty.create("chain_south");
    public static final BooleanProperty CHAIN_EAST = BooleanProperty.create("chain_east");
    public static final BooleanProperty CHAIN_WEST = BooleanProperty.create("chain_west");
    public static final BooleanProperty[] CHAINED = new BooleanProperty[] {
            CHAIN_DOWN,
            CHAIN_UP,
            CHAIN_NORTH,
            CHAIN_SOUTH,
            CHAIN_EAST,
            CHAIN_WEST
    };
}
