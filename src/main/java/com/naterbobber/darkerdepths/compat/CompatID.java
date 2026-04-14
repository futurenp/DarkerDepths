package com.naterbobber.darkerdepths.compat;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.ModList;

public class CompatID {
    private final String compatID;

    public CompatID(String compatID) {
        this.compatID = compatID;
    }

    public static CompatID createCompatID(String compatID) {
        return new CompatID(compatID);
    }

    public String toString() {
        return compatID;
    }

    public ResourceLocation id(String name){
        return new ResourceLocation(compatID, name);
    }

    public boolean isLoaded() {
        return ModList.get().isLoaded(compatID);
    }

}