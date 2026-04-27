package com.naterbobber.darkerdepths.compat;

import net.minecraft.resources.ResourceLocation;
import net.neoforged.fml.ModList;

public class CompatID {
    private final String compatID;
    private String[] alternates = null;

    public CompatID(String compatID) {
        this.compatID = compatID;
    }

    public CompatID(String... compatIDs) {
        if(compatIDs.length == 0) {
            throw new IllegalArgumentException("CompatIDs cannot be empty");
        }
        this.compatID = compatIDs[0];
        this.alternates = compatIDs;
    }

    public static CompatID create(String compatID) {
        return new CompatID(compatID);
    }

    public static CompatID create(String... compatIDs) {
        return new CompatID(compatIDs);
    }

    public String toString() {
        return compatID;
    }

    public ResourceLocation id(String name){
        return ResourceLocation.fromNamespaceAndPath(compatID, name);
    }

    public boolean isLoaded() {
        if(alternates != null && alternates.length > 1){
            for(String alt : alternates){
                if(ModList.get().isLoaded(alt)){
                    return true;
                }
            }
            return false;
        } else {
            return ModList.get().isLoaded(compatID);
        }
    }
}
