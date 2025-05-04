package com.naterbobber.darkerdepths.init;

import com.naterbobber.darkerdepths.DarkerDepths;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.material.Fluid;

public class DDFluidTags {

    public static final TagKey<Fluid> AMBER = create("amber");

    private static TagKey<Fluid> create(String name) {
        return TagKey.create(Registries.FLUID, DarkerDepths.id(name));
    }

}
