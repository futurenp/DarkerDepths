package com.naterbobber.darkerdepths.block.blockstates;

import net.minecraft.tags.TagKey;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.Tags;

public enum BedState implements StringRepresentable {
    NONE("none", null),
    WHITE("white", Tags.Items.DYED_WHITE),
    LIGHT_GRAY("light_gray", Tags.Items.DYED_LIGHT_GRAY),
    GRAY("gray", Tags.Items.DYED_GRAY),
    BLACK("black", Tags.Items.DYED_BLACK),
    BROWN("brown", Tags.Items.DYED_BROWN),
    RED("red", Tags.Items.DYED_RED),
    ORANGE("orange", Tags.Items.DYED_ORANGE),
    YELLOW("yellow", Tags.Items.DYED_YELLOW),
    LIME("lime", Tags.Items.DYED_LIME),
    GREEN("green", Tags.Items.DYED_GREEN),
    CYAN("cyan", Tags.Items.DYED_CYAN),
    LIGHT_BLUE("light_blue", Tags.Items.DYED_LIGHT_BLUE),
    BLUE("blue", Tags.Items.DYED_BLUE),
    PURPLE("purple", Tags.Items.DYED_PURPLE),
    MAGENTA("magenta", Tags.Items.DYED_MAGENTA),
    PINK("pink", Tags.Items.DYED_PINK);

    private final String name;
    private final TagKey<Item> dyeTag;

    BedState(String name, TagKey<Item> dyeTag) {
        this.name = name;
        this.dyeTag = dyeTag;
    }

    public TagKey<Item> getDyeTag() {
        return this.dyeTag;
    }

    public static BedState getFromItem(ItemStack stack) {
        if (stack.isEmpty()) {
            return NONE;
        }

        for (var state : values()) {
            if (state.dyeTag != null && stack.is(state.dyeTag)) {
                return state;
            }
        }
        return NONE;
    }

    @Override
    public String getSerializedName() {
        return this.name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}