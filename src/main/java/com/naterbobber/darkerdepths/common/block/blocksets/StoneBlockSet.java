package com.naterbobber.darkerdepths.common.block.blocksets;

import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredBlock;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class StoneBlockSet {
    private final Group standardGroup;
    private final Group polishedGroup;
    private final Group bricksGroup;
    private final Group mossyGroup;

    private final DeferredBlock<? extends Block> crackedBricks;
    private final DeferredBlock<? extends Block> chiseled;
    private final DeferredBlock<? extends Block> pillar;

    private final List<DeferredBlock<? extends Block>> allBlocks;
    private final List<Group> allGroups;

    protected StoneBlockSet(Builder builder) {
        this.standardGroup = builder.standardGroup;
        this.polishedGroup = builder.polishedGroup;
        this.bricksGroup = builder.bricksGroup;
        this.mossyGroup = builder.mossyGroup;

        this.crackedBricks = builder.crackedBricks;
        this.chiseled = builder.chiseled;
        this.pillar = builder.pillar;

        var blocksStream = Stream.of(
                this.crackedBricks, this.chiseled, this.pillar
        );

        if (this.standardGroup != null) blocksStream = Stream.concat(blocksStream, this.standardGroup.getMembers().stream());
        if (this.polishedGroup != null) blocksStream = Stream.concat(blocksStream, this.polishedGroup.getMembers().stream());
        if (this.bricksGroup != null) blocksStream = Stream.concat(blocksStream, this.bricksGroup.getMembers().stream());
        if (this.mossyGroup != null) blocksStream = Stream.concat(blocksStream, this.mossyGroup.getMembers().stream());

        this.allBlocks = blocksStream.filter(Objects::nonNull).toList();

        this.allGroups = Stream.of(this.standardGroup, this.polishedGroup, this.bricksGroup, this.mossyGroup)
                .filter(Objects::nonNull)
                .filter(group -> group.base() != null)
                .toList();    }

    public Group getStandardGroup() { return standardGroup; }
    public Group getPolishedGroup() { return polishedGroup; }
    public Group getBricksGroup() { return bricksGroup; }
    public Group getMossyGroup() { return mossyGroup; }
    
    public DeferredBlock<? extends Block> getCrackedBricks() { return crackedBricks; }
    public DeferredBlock<? extends Block> getChiseled() { return chiseled; }
    public DeferredBlock<? extends Block> getPillar() { return pillar; }

    public List<DeferredBlock<? extends Block>> getAllBlocks() {
        return allBlocks;
    }

    public List<Group> getAllGroups() {
        return allGroups;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Group standardGroup;
        private Group polishedGroup;
        private Group bricksGroup;
        private Group mossyGroup;
        private DeferredBlock<? extends Block> crackedBricks;
        private DeferredBlock<? extends Block> chiseled;
        private DeferredBlock<? extends Block> pillar;

        public Builder standard(Group group) {
            this.standardGroup = group;
            return this;
        }

        public Builder polished(Group group) {
            this.polishedGroup = group;
            return this;
        }

        public Builder bricks(Group group) {
            this.bricksGroup = group;
            return this;
        }

        public Builder mossy(Group group) {
            this.mossyGroup = group;
            return this;
        }

        public Builder crackedBricks(DeferredBlock<? extends Block> crackedBricks) {
            this.crackedBricks = crackedBricks;
            return this;
        }

        public Builder chiseled(DeferredBlock<? extends Block> chiseled) {
            this.chiseled = chiseled;
            return this;
        }

        public Builder pillar(DeferredBlock<? extends Block> pillar) {
            this.pillar = pillar;
            return this;
        }

        public StoneBlockSet build() {
            return new StoneBlockSet(this);
        }
    }

    public record Group(
            DeferredBlock<? extends Block> base,
            DeferredBlock<? extends Block> stairs,
            DeferredBlock<? extends Block> slab,
            DeferredBlock<? extends Block> verticalSlab,
            DeferredBlock<? extends Block> wall) 
    {
        public Group(DeferredBlock<? extends Block> base, DeferredBlock<? extends Block> stairs, DeferredBlock<? extends Block> slab, DeferredBlock<? extends Block> verticalSlab, @Nullable DeferredBlock<? extends Block> wall) {
            this.base = base;
            this.stairs = stairs;
            this.slab = slab;
            this.verticalSlab = verticalSlab;
            this.wall = wall;
        }

        public List<DeferredBlock<? extends Block>> getMembers() {
            return Arrays.asList(base, stairs, slab, verticalSlab, wall);
        }
    }
}