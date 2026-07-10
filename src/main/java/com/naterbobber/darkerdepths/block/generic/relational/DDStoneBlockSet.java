package com.naterbobber.darkerdepths.block.generic.relational;

import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredBlock;

public class DDStoneBlockSet {
    private final DeferredBlock<? extends Block> base;
    private final DeferredBlock<? extends Block> baseStairs;
    private final DeferredBlock<? extends Block> baseSlab;
    private final DeferredBlock<? extends Block> baseVerticalSlab;
    private final DeferredBlock<? extends Block> baseWall;
    private final DeferredBlock<? extends Block> polished;
    private final DeferredBlock<? extends Block> polishedStairs;
    private final DeferredBlock<? extends Block> polishedSlab;
    private final DeferredBlock<? extends Block> polishedVerticalSlab;
    private final DeferredBlock<? extends Block> bricks;
    private final DeferredBlock<? extends Block> bricksStairs;
    private final DeferredBlock<? extends Block> bricksSlab;
    private final DeferredBlock<? extends Block> bricksVerticalSlab;
    private final DeferredBlock<? extends Block> bricksWall;
    private final DeferredBlock<? extends Block> crackedBricks;
    private final DeferredBlock<? extends Block> chiseled;
    private final DeferredBlock<? extends Block> mossyBricks;
    private final DeferredBlock<? extends Block> mossyBricksStairs;
    private final DeferredBlock<? extends Block> mossyBricksSlab;
    private final DeferredBlock<? extends Block> mossyBricksVerticalSlab;
    private final DeferredBlock<? extends Block> mossyBricksWall;
    private final DeferredBlock<? extends Block> pillar;

    protected DDStoneBlockSet(Builder builder) {
        this.base = builder.base;
        this.baseStairs = builder.baseStairs;
        this.baseSlab = builder.baseSlab;
        this.baseVerticalSlab = builder.baseVerticalSlab;
        this.baseWall = builder.baseWall;
        this.polished = builder.polished;
        this.polishedStairs = builder.polishedStairs;
        this.polishedSlab = builder.polishedSlab;
        this.polishedVerticalSlab = builder.polishedVerticalSlab;
        this.bricks = builder.bricks;
        this.bricksStairs = builder.bricksStairs;
        this.bricksSlab = builder.bricksSlab;
        this.bricksVerticalSlab = builder.bricksVerticalSlab;
        this.bricksWall = builder.bricksWall;
        this.crackedBricks = builder.crackedBricks;
        this.chiseled = builder.chiseled;
        this.mossyBricks = builder.mossyBricks;
        this.mossyBricksStairs = builder.mossyBricksStairs;
        this.mossyBricksSlab = builder.mossyBricksSlab;
        this.mossyBricksVerticalSlab = builder.mossyBricksVerticalSlab;
        this.mossyBricksWall = builder.mossyBricksWall;
        this.pillar = builder.pillar;
    }

    public DeferredBlock<? extends Block> getBase() { return base; }
    public DeferredBlock<? extends Block> getBaseStairs() { return baseStairs; }
    public DeferredBlock<? extends Block> getBaseSlab() { return baseSlab; }
    public DeferredBlock<? extends Block> getBaseVerticalSlab() { return baseVerticalSlab; }
    public DeferredBlock<? extends Block> getBaseWall() { return baseWall; }
    public DeferredBlock<? extends Block> getPolished() { return polished; }
    public DeferredBlock<? extends Block> getPolishedStairs() { return polishedStairs; }
    public DeferredBlock<? extends Block> getPolishedSlab() { return polishedSlab; }
    public DeferredBlock<? extends Block> getPolishedVerticalSlab() { return polishedVerticalSlab; }
    public DeferredBlock<? extends Block> getBricks() { return bricks; }
    public DeferredBlock<? extends Block> getBricksStairs() { return bricksStairs; }
    public DeferredBlock<? extends Block> getBricksSlab() { return bricksSlab; }
    public DeferredBlock<? extends Block> getBricksVerticalSlab() { return bricksVerticalSlab; }
    public DeferredBlock<? extends Block> getBricksWall() { return bricksWall; }
    public DeferredBlock<? extends Block> getCrackedBricks() { return crackedBricks; }
    public DeferredBlock<? extends Block> getChiseled() { return chiseled; }
    public DeferredBlock<? extends Block> getMossyBricks() { return mossyBricks; }
    public DeferredBlock<? extends Block> getMossyBricksStairs() { return mossyBricksStairs; }
    public DeferredBlock<? extends Block> getMossyBricksSlab() { return mossyBricksSlab; }
    public DeferredBlock<? extends Block> getMossyBricksVerticalSlab() { return mossyBricksVerticalSlab; }
    public DeferredBlock<? extends Block> getMossyBricksWall() { return mossyBricksWall; }
    public DeferredBlock<? extends Block> getPillar() { return pillar; }

    public static Builder builder() {
        var builder = new Builder();
        return builder;
    }

    public static class Builder {
        private DeferredBlock<? extends Block> base;
        private DeferredBlock<? extends Block> baseStairs;
        private DeferredBlock<? extends Block> baseSlab;
        private DeferredBlock<? extends Block> baseVerticalSlab;
        private DeferredBlock<? extends Block> baseWall;
        private DeferredBlock<? extends Block> polished;
        private DeferredBlock<? extends Block> polishedStairs;
        private DeferredBlock<? extends Block> polishedSlab;
        private DeferredBlock<? extends Block> polishedVerticalSlab;
        private DeferredBlock<? extends Block> bricks;
        private DeferredBlock<? extends Block> bricksStairs;
        private DeferredBlock<? extends Block> bricksSlab;
        private DeferredBlock<? extends Block> bricksVerticalSlab;
        private DeferredBlock<? extends Block> bricksWall;
        private DeferredBlock<? extends Block> crackedBricks;
        private DeferredBlock<? extends Block> chiseled;
        private DeferredBlock<? extends Block> mossyBricks;
        private DeferredBlock<? extends Block> mossyBricksStairs;
        private DeferredBlock<? extends Block> mossyBricksSlab;
        private DeferredBlock<? extends Block> mossyBricksVerticalSlab;
        private DeferredBlock<? extends Block> mossyBricksWall;
        private DeferredBlock<? extends Block> pillar;

        public Builder base(DeferredBlock<? extends Block> base) {
            this.base = base;
            return this;
        }

        public Builder baseStairs(DeferredBlock<? extends Block> baseStairs) {
            this.baseStairs = baseStairs;
            return this;
        }

        public Builder baseSlab(DeferredBlock<? extends Block> baseSlab) {
            this.baseSlab = baseSlab;
            return this;
        }

        public Builder baseVerticalSlab(DeferredBlock<? extends Block> baseVerticalSlab) {
            this.baseVerticalSlab = baseVerticalSlab;
            return this;
        }

        public Builder baseWall(DeferredBlock<? extends Block> baseWall) {
            this.baseWall = baseWall;
            return this;
        }

        public Builder polished(DeferredBlock<? extends Block> polished) {
            this.polished = polished;
            return this;
        }

        public Builder polishedStairs(DeferredBlock<? extends Block> polishedStairs) {
            this.polishedStairs = polishedStairs;
            return this;
        }

        public Builder polishedSlab(DeferredBlock<? extends Block> polishedSlab) {
            this.polishedSlab = polishedSlab;
            return this;
        }

        public Builder polishedVerticalSlab(DeferredBlock<? extends Block> polishedVerticalSlab) {
            this.polishedVerticalSlab = polishedVerticalSlab;
            return this;
        }

        public Builder bricks(DeferredBlock<? extends Block> bricks) {
            this.bricks = bricks;
            return this;
        }

        public Builder bricksStairs(DeferredBlock<? extends Block> bricksStairs) {
            this.bricksStairs = bricksStairs;
            return this;
        }

        public Builder bricksSlab(DeferredBlock<? extends Block> bricksSlab) {
            this.bricksSlab = bricksSlab;
            return this;
        }

        public Builder bricksVerticalSlab(DeferredBlock<? extends Block> bricksVerticalSlab) {
            this.bricksVerticalSlab = bricksVerticalSlab;
            return this;
        }

        public Builder bricksWall(DeferredBlock<? extends Block> bricksWall) {
            this.bricksWall = bricksWall;
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

        public Builder mossyBricks(DeferredBlock<? extends Block> mossyBricks) {
            this.mossyBricks = mossyBricks;
            return this;
        }

        public Builder mossyBricksStairs(DeferredBlock<? extends Block> mossyBricksStairs) {
            this.mossyBricksStairs = mossyBricksStairs;
            return this;
        }

        public Builder mossyBricksSlab(DeferredBlock<? extends Block> mossyBricksSlab) {
            this.mossyBricksSlab = mossyBricksSlab;
            return this;
        }

        public Builder mossyBricksVerticalSlab(DeferredBlock<? extends Block> mossyBricksVerticalSlab) {
            this.mossyBricksVerticalSlab = mossyBricksVerticalSlab;
            return this;
        }

        public Builder mossyBricksWall(DeferredBlock<? extends Block> mossyBricksWall) {
            this.mossyBricksWall = mossyBricksWall;
            return this;
        }

        public Builder pillar(DeferredBlock<? extends Block> pillar) {
            this.pillar = pillar;
            return this;
        }

        public DDStoneBlockSet build() {
            return new DDStoneBlockSet(this);
        }
    }
}