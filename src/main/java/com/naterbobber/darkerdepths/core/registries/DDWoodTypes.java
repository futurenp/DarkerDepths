package com.naterbobber.darkerdepths.core.registries;

import com.naterbobber.darkerdepths.core.DarkerDepths;
import net.minecraft.block.WoodType;
import net.minecraft.client.renderer.Atlases;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.HashSet;
import java.util.Set;

//<>

public class DDWoodTypes {
    private static final Set<WoodType> WOOD_TYPES = new HashSet<>();

    public static final WoodType PETRIFIED = registerWoodType(new WoodType(DarkerDepths.MODID + ":" + "petrified"));

    @OnlyIn(Dist.CLIENT)
    public static void initializeAtlas() {
        for (WoodType woodType : WOOD_TYPES) {
            Atlases.addWoodType(woodType);
        }
    }

    public static WoodType registerWoodType(WoodType woodType) {
        WOOD_TYPES.add(woodType);
        return WoodType.register(woodType);
    }
}