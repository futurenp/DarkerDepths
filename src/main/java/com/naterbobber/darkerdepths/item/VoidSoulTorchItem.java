package com.naterbobber.darkerdepths.item;

import net.minecraft.core.Direction;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.StandingAndWallBlockItem;
import net.minecraft.world.level.block.Block;

public class VoidSoulTorchItem extends StandingAndWallBlockItem {

    public VoidSoulTorchItem(Block standingBlock, Block wallBlock, Item.Properties properties) {
        super(standingBlock, wallBlock, properties, Direction.DOWN);
    }
}