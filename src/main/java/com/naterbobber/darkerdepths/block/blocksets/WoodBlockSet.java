package com.naterbobber.darkerdepths.block.blocksets;

import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class WoodBlockSet {
    private final DeferredBlock<? extends Block> log;
    private final DeferredBlock<? extends Block> wood;
    private final DeferredBlock<? extends Block> strippedLog;
    private final DeferredBlock<? extends Block> strippedWood;
    private final DeferredBlock<? extends Block> planks;
    private final DeferredBlock<? extends Block> boards;
    private final DeferredBlock<? extends Block> verticalPlanks;
    private final DeferredBlock<? extends Block> stairs;
    private final DeferredBlock<? extends Block> slab;
    private final DeferredBlock<? extends Block> verticalSlab;
    private final DeferredBlock<? extends Block> trimmedPlanks;
    private final DeferredBlock<? extends Block> fence;
    private final DeferredBlock<? extends Block> fenceGate;
    private final DeferredBlock<? extends Block> door;
    private final DeferredBlock<? extends Block> trapdoor;
    private final DeferredBlock<? extends Block> bookshelf;
    private final DeferredBlock<? extends Block> pressurePlate;
    private final DeferredBlock<? extends Block> button;
    private final DeferredBlock<? extends Block> sign;
    private final DeferredBlock<? extends Block> wallSign;
    private final DeferredBlock<? extends Block> hangingSign;
    private final DeferredBlock<? extends Block> wallHangingSign;
    private final DeferredBlock<? extends Block> post;
    private final DeferredBlock<? extends Block> strippedPost;
    private final DeferredItem<? extends Item> boat;
    private final DeferredItem<? extends Item> chestBoat;
    private final TagKey<Item> logTag;
    private final List<DeferredBlock<? extends Block>> allBlocks;

    protected WoodBlockSet(Builder builder) {
        this.log = builder.log;
        this.wood = builder.wood;
        this.strippedLog = builder.strippedLog;
        this.strippedWood = builder.strippedWood;
        this.planks = builder.planks;
        this.boards = builder.boards;
        this.verticalPlanks = builder.verticalPlanks;
        this.stairs = builder.stairs;
        this.slab = builder.slab;
        this.verticalSlab = builder.verticalSlab;
        this.trimmedPlanks = builder.trimmedPlanks;
        this.fence = builder.fence;
        this.fenceGate = builder.fenceGate;
        this.door = builder.door;
        this.trapdoor = builder.trapdoor;
        this.bookshelf = builder.bookshelf;
        this.pressurePlate = builder.pressurePlate;
        this.button = builder.button;
        this.sign = builder.sign;
        this.wallSign = builder.wallSign;
        this.hangingSign = builder.hangingSign;
        this.wallHangingSign = builder.wallHangingSign;
        this.post = builder.post;
        this.strippedPost = builder.strippedPost;
        this.boat = builder.boat;
        this.chestBoat = builder.chestBoat;
        this.logTag = builder.logTag;

        var blocksStream = Stream.of(
                this.log, this.wood, this.strippedLog, this.strippedWood, this.planks, this.boards, this.verticalPlanks,
                this.stairs, this.slab, this.verticalSlab, this.trimmedPlanks, this.fence, this.fenceGate, this.door,
                this.trapdoor, this.bookshelf, this.pressurePlate, this.button, this.sign, this.wallSign,
                this.hangingSign, this.wallHangingSign, this.post, this.strippedPost
        );
        this.allBlocks = blocksStream.filter(Objects::nonNull).toList();
    }

    public DeferredBlock<? extends Block> getLog() { return log; }
    public DeferredBlock<? extends Block> getWood() { return wood; }
    public DeferredBlock<? extends Block> getStrippedLog() { return strippedLog; }
    public DeferredBlock<? extends Block> getStrippedWood() { return strippedWood; }
    public DeferredBlock<? extends Block> getPlanks() { return planks; }
    public DeferredBlock<? extends Block> getBoards() { return boards; }
    public DeferredBlock<? extends Block> getVerticalPlanks() { return verticalPlanks; }
    public DeferredBlock<? extends Block> getStairs() { return stairs; }
    public DeferredBlock<? extends Block> getSlab() { return slab; }
    public DeferredBlock<? extends Block> getVerticalSlab() { return verticalSlab; }
    public DeferredBlock<? extends Block> getTrimmedPlanks() { return trimmedPlanks; }
    public DeferredBlock<? extends Block> getFence() { return fence; }
    public DeferredBlock<? extends Block> getFenceGate() { return fenceGate; }
    public DeferredBlock<? extends Block> getDoor() { return door; }
    public DeferredBlock<? extends Block> getTrapdoor() { return trapdoor; }
    public DeferredBlock<? extends Block> getBookshelf() { return bookshelf; }
    public DeferredBlock<? extends Block> getPressurePlate() { return pressurePlate; }
    public DeferredBlock<? extends Block> getButton() { return button; }
    public DeferredBlock<? extends Block> getSign() { return sign; }
    public DeferredBlock<? extends Block> getWallSign() { return wallSign; }
    public DeferredBlock<? extends Block> getHangingSign() { return hangingSign; }
    public DeferredBlock<? extends Block> getWallHangingSign() { return wallHangingSign; }
    public DeferredBlock<? extends Block> getPost() { return post; }
    public DeferredBlock<? extends Block> getStrippedPost() { return strippedPost; }
    public DeferredItem<? extends Item> getBoat() { return boat; }
    public DeferredItem<? extends Item> getChestBoat() { return chestBoat; }
    public TagKey<Item> getLogTag() { return logTag; }

    public List<DeferredBlock<? extends Block>> getBlocks() {
        return allBlocks;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private DeferredBlock<? extends Block> log;
        private DeferredBlock<? extends Block> wood;
        private DeferredBlock<? extends Block> strippedLog;
        private DeferredBlock<? extends Block> strippedWood;
        private DeferredBlock<? extends Block> planks;
        private DeferredBlock<? extends Block> boards;
        private DeferredBlock<? extends Block> verticalPlanks;
        private DeferredBlock<? extends Block> stairs;
        private DeferredBlock<? extends Block> slab;
        private DeferredBlock<? extends Block> verticalSlab;
        private DeferredBlock<? extends Block> trimmedPlanks;
        private DeferredBlock<? extends Block> fence;
        private DeferredBlock<? extends Block> fenceGate;
        private DeferredBlock<? extends Block> door;
        private DeferredBlock<? extends Block> trapdoor;
        private DeferredBlock<? extends Block> bookshelf;
        private DeferredBlock<? extends Block> pressurePlate;
        private DeferredBlock<? extends Block> button;
        private DeferredBlock<? extends Block> sign;
        private DeferredBlock<? extends Block> wallSign;
        private DeferredBlock<? extends Block> hangingSign;
        private DeferredBlock<? extends Block> wallHangingSign;
        private DeferredBlock<? extends Block> post;
        private DeferredBlock<? extends Block> strippedPost;
        private DeferredItem<? extends Item> boat;
        private DeferredItem<? extends Item> chestBoat;
        private TagKey<Item> logTag;

        public Builder log(DeferredBlock<? extends Block> log) {
            this.log = log;
            return this;
        }

        public Builder wood(DeferredBlock<? extends Block> wood) {
            this.wood = wood;
            return this;
        }

        public Builder strippedLog(DeferredBlock<? extends Block> strippedLog) {
            this.strippedLog = strippedLog;
            return this;
        }

        public Builder strippedWood(DeferredBlock<? extends Block> strippedWood) {
            this.strippedWood = strippedWood;
            return this;
        }

        public Builder planks(DeferredBlock<? extends Block> planks) {
            this.planks = planks;
            return this;
        }

        public Builder boards(DeferredBlock<? extends Block> boards) {
            this.boards = boards;
            return this;
        }

        public Builder verticalPlanks(DeferredBlock<? extends Block> verticalPlanks) {
            this.verticalPlanks = verticalPlanks;
            return this;
        }

        public Builder stairs(DeferredBlock<? extends Block> stairs) {
            this.stairs = stairs;
            return this;
        }

        public Builder slab(DeferredBlock<? extends Block> slab) {
            this.slab = slab;
            return this;
        }

        public Builder verticalSlab(DeferredBlock<? extends Block> verticalSlab) {
            this.verticalSlab = verticalSlab;
            return this;
        }

        public Builder trimmedPlanks(DeferredBlock<? extends Block> trimmedPlanks) {
            this.trimmedPlanks = trimmedPlanks;
            return this;
        }

        public Builder fence(DeferredBlock<? extends Block> fence) {
            this.fence = fence;
            return this;
        }

        public Builder fenceGate(DeferredBlock<? extends Block> fenceGate) {
            this.fenceGate = fenceGate;
            return this;
        }

        public Builder door(DeferredBlock<? extends Block> door) {
            this.door = door;
            return this;
        }

        public Builder trapdoor(DeferredBlock<? extends Block> trapdoor) {
            this.trapdoor = trapdoor;
            return this;
        }

        public Builder bookshelf(DeferredBlock<? extends Block> bookshelf) {
            this.bookshelf = bookshelf;
            return this;
        }

        public Builder pressurePlate(DeferredBlock<? extends Block> pressurePlate) {
            this.pressurePlate = pressurePlate;
            return this;
        }

        public Builder button(DeferredBlock<? extends Block> button) {
            this.button = button;
            return this;
        }

        public Builder sign(DeferredBlock<? extends Block> sign) {
            this.sign = sign;
            return this;
        }

        public Builder wallSign(DeferredBlock<? extends Block> wallSign) {
            this.wallSign = wallSign;
            return this;
        }

        public Builder hangingSign(DeferredBlock<? extends Block> hangingSign) {
            this.hangingSign = hangingSign;
            return this;
        }

        public Builder wallHangingSign(DeferredBlock<? extends Block> wallHangingSign) {
            this.wallHangingSign = wallHangingSign;
            return this;
        }

        public Builder post(DeferredBlock<? extends Block> post) {
            this.post = post;
            return this;
        }

        public Builder strippedPost(DeferredBlock<? extends Block> strippedPost) {
            this.strippedPost = strippedPost;
            return this;
        }

        public Builder boat(DeferredItem<? extends Item> boat) {
            this.boat = boat;
            return this;
        }

        public Builder chestBoat(DeferredItem<? extends Item> chestBoat) {
            this.chestBoat = chestBoat;
            return this;
        }

        public Builder logTag(TagKey<Item> logTag) {
            this.logTag = logTag;
            return this;
        }

        public WoodBlockSet build() {
            return new WoodBlockSet(this);
        }
    }
}