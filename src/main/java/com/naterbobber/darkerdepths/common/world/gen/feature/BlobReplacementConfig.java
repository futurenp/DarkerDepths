package com.naterbobber.darkerdepths.common.world.gen.feature;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.world.gen.feature.IFeatureConfig;

import java.util.List;
import java.util.Set;

//<>

public class BlobReplacementConfig implements IFeatureConfig {
    public static final Codec<BlobReplacementConfig> CODEC = RecordCodecBuilder.create((instance) -> {
        return instance.group(BlockState.BLOCKSTATE_CODEC.listOf().fieldOf("target").forGetter((config) -> {
            return ImmutableList.copyOf(config.target);
        }), BlockState.BLOCKSTATE_CODEC.fieldOf("state").forGetter((config) -> {
            return config.blockState;
        }), Vector3i.field_239781_c_.fieldOf("minimum_reach").forGetter((config) -> {
            return config.minimumReach;
        }), Vector3i.field_239781_c_.fieldOf("maximum_reach").forGetter((config) -> {
            return config.maximumReach;
        })).apply(instance, BlobReplacementConfig::new);
    });
    public final Set<BlockState> target;
    public final BlockState blockState;
    public final Vector3i minimumReach;
    public final Vector3i maximumReach;

    public BlobReplacementConfig(List<BlockState> target, BlockState blockState, Vector3i minimumReach, Vector3i maximumReach) {
        this(ImmutableSet.copyOf(target), blockState, minimumReach, maximumReach);
    }

    public BlobReplacementConfig(Set<BlockState> target, BlockState blockState, Vector3i minimumReach, Vector3i maximumReach) {
        this.target = target;
        this.blockState = blockState;
        this.minimumReach = minimumReach;
        this.maximumReach = maximumReach;
    }

    public static class Builder {
        private Set<BlockState> target = ImmutableSet.of();
        private BlockState blockState   = Blocks.AIR.getDefaultState();
        private Vector3i minimumReach = Vector3i.NULL_VECTOR;
        private Vector3i maximumReach = Vector3i.NULL_VECTOR;

        public Builder setTarget(Set<BlockState> target) {
            this.target = target;
            return this;
        }

        public Builder setBlockState(BlockState blockState) {
            this.blockState = blockState;
            return this;
        }

        public Builder setMinReach(Vector3i minimumReach) {
            this.minimumReach = minimumReach;
            return this;
        }

        public Builder setMaxReach(Vector3i maximumReach) {
            this.maximumReach = maximumReach;
            return this;
        }

        public BlobReplacementConfig build() {
            if (this.minimumReach.getX() >= 0 && this.minimumReach.getY() >= 0 && this.minimumReach.getZ() >= 0) {
                if (this.minimumReach.getZ() <= this.maximumReach.getX() && this.minimumReach.getY() <= this.maximumReach.getY() && this.minimumReach.getZ() <= this.maximumReach.getZ()) {
                    return new BlobReplacementConfig(this.target, this.blockState, this.minimumReach, this.maximumReach);
                } else {
                    throw new IllegalArgumentException("Maximum reach must be greater than minimum reach for each axis");
                }
            } else {
                throw new IllegalArgumentException("Minimum reach cannot be less than zero");
            }
        }
    }
}