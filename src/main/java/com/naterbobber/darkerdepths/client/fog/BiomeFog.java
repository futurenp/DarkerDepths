package com.naterbobber.darkerdepths.client.fog;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import software.bernie.geckolib.core.object.Color;


import java.util.function.Supplier;

public class BiomeFog {
    ResourceKey<Biome> biomeKey;
    Color color;
    float weight;
    Supplier<Integer> minDist;
    Supplier<Integer> maxDist;

    public BiomeFog(ResourceKey<Biome> biome, Color color, Supplier<Integer> minDist, Supplier<Integer> maxDist) {
        this.biomeKey = biome;
        this.color = color;
        this.minDist = minDist;
        this.maxDist = maxDist;
    }

    Color getWeightedColors() {
        return Color.ofRGB(color.getRedFloat() * weight, color.getGreenFloat() * weight, color.getBlueFloat() * weight);
    }

    float getWeightedMin() {
        return weight * minDist.get();
    }

    float getWeightedMax() {
        return weight * maxDist.get();
    }

    public ResourceKey<Biome> getBiomeKey() {
        return biomeKey;
    }

    public void setBiomeKey(ResourceKey<Biome> biomeKey) {
        this.biomeKey = biomeKey;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public int getMinDist() {
        return minDist.get();
    }

    public void setMinDist(Supplier<Integer> minDist) {
        this.minDist = minDist;
    }

    public int getMaxDist() {
        return maxDist.get();
    }

    public void setMaxDist(Supplier<Integer> maxDist) {
        this.maxDist = maxDist;
    }

    public int getIntColor() {
        return color.getColor();
    }

}