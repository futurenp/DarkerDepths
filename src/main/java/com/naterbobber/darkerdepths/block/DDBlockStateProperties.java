package com.naterbobber.darkerdepths.block;

import com.naterbobber.darkerdepths.block.custom.RopeBlock;
import com.naterbobber.darkerdepths.block.generic.ConnectedPillarBlock;
import com.naterbobber.darkerdepths.block.generic.VerticalSlabBlock;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class DDBlockStateProperties {
    public static final IntegerProperty CRYSTAL_GROWTH_LEVEL = IntegerProperty.create("crystal_growth_level", 0, 3);
    public static final IntegerProperty MELON_GROWTH_LEVEL = IntegerProperty.create("melon_growth_level", 0, 2);
    public static final BooleanProperty INHABITED = BooleanProperty.create("inhabited");
    public static final EnumProperty<RopeBlock.RopePart> PART = EnumProperty.create("part", RopeBlock.RopePart.class);
    public static final IntegerProperty GLOWSHROOM_CLUSTERS = IntegerProperty.create("clusters", 1, 3);
    public static final EnumProperty<ConnectedPillarBlock.PillarState> PILLAR_STATE = EnumProperty.create("pillar_state", ConnectedPillarBlock.PillarState.class);
    public static final EnumProperty<VerticalSlabBlock.VerticalSlabType> TYPE = EnumProperty.create("type", VerticalSlabBlock.VerticalSlabType.class);
    public static final IntegerProperty HEAT_LEVEL = IntegerProperty.create("heat_level", 0, 4);
}
