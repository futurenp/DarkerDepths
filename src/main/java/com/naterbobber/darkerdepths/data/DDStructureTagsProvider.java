package com.naterbobber.darkerdepths.data;

import com.naterbobber.darkerdepths.DarkerDepths;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

public class DDStructureTagsProvider {
    public static final TagKey<Biome> HAS_ROPE_MINE =
            createTag("has_structure/rope_mine");

    private static TagKey<Biome> createTag(String name) {
        return TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(DarkerDepths.MODID, name));
    }

}