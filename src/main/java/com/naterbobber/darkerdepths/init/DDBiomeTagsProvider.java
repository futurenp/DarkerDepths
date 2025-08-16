package com.naterbobber.darkerdepths.init;

import com.naterbobber.darkerdepths.DarkerDepths;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

public class DDBiomeTagsProvider {
    public static final TagKey<Biome> HAS_ROPE_MINE_FOREST =
            createTag("has_structure/rope_mine_forest");

    public static final TagKey<Biome> HAS_ROPE_MINE_DESERT =
            createTag("has_structure/rope_mine_desert");
    public static final TagKey<Biome> CATACOMBS = createTag("has_structure/catacombs");

    private static TagKey<Biome> createTag(String name) {
        return TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(DarkerDepths.MODID, name));
    }

}