package com.naterbobber.darkerdepths.util;

import com.naterbobber.darkerdepths.DarkerDepths;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;

public class DDTags {
    private static <T> TagKey<T> createTag(ResourceKey<Registry<T>> registry, String id) {
        return TagKey.create(registry, DarkerDepths.id(id));
    }
    public static class Items {
        private static final ResourceKey<Registry<Item>> RK = Registries.ITEM;
        public static final TagKey<Item> PETRIFIED_LOGS = createTag(RK, "petrified_logs");
        public static final TagKey<Item> STILETTO_ENCHANTABLE = createTag(RK, "stiletto_enchantable");

    }

    public static class Blocks {
        private static final ResourceKey<Registry<Block>> RK = Registries.BLOCK;
        public static final TagKey<Block> GEYSER_BOOSTERS = createTag(RK, "geyser_boosters");
        public static final TagKey<Block> GEYSER_BYPASSES = createTag(RK, "geyser_bypasses");
        public static final TagKey<Block> HUSKS_SPAWNABLE_ON = createTag(RK, "husks_spawnable_on");
    }

    public static class Biomes {
        private static final ResourceKey<Registry<Biome>> RK = Registries.BIOME;
        public static final TagKey<Biome> HAS_ROPE_MINE_FOREST = createTag(RK, "has_structure/rope_mine_forest");
        public static final TagKey<Biome> HAS_ROPE_MINE_DESERT = createTag(RK, "has_structure/rope_mine_desert");
        public static final TagKey<Biome> CATACOMBS = createTag(RK, "has_structure/catacombs");
    }
}
