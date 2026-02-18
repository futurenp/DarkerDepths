package com.naterbobber.darkerdepths.util;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.compat.CompatID;
import com.naterbobber.darkerdepths.compat.DDCompat;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;

public class DDTags {
    private record TagFactory<T>(ResourceKey<Registry<T>> registry) {
        public TagKey<T> create(String path) {
            return TagKey.create(registry, DarkerDepths.id(path));
        }

        public TagKey<T> create(String path, CompatID compatId) {
            return TagKey.create(registry, compatId.id(path));
        }
    }

    private static <T> TagFactory<T> make(ResourceKey<Registry<T>> registry) {
        return new TagFactory<>(registry);
    }

    public static class Items {
        private static final TagFactory<Item> TAGS = make(Registries.ITEM);

        public static final TagKey<Item> PETRIFIED_LOGS = TAGS.create("petrified_logs");
        public static final TagKey<Item> ROPES = TAGS.create("ropes", DDCompat.FORGE);
    }

    public static class Blocks {
        private static final TagFactory<Block> TAGS = make(Registries.BLOCK);

        public static final TagKey<Block> GEYSER_BOOSTERS = TAGS.create("geyser_boosters");
        public static final TagKey<Block> GEYSER_BYPASSES = TAGS.create("geyser_bypasses");
        public static final TagKey<Block> HUSKS_SPAWNABLE_ON = TAGS.create("husks_spawnable_on");
        public static final TagKey<Block> TRIMMED_PLANKS = TAGS.create("trimmed_planks", DDCompat.NO_MANS_LAND);
        public static final TagKey<Block> WOODEN_BOOKSHELVES = TAGS.create("wooden_bookshelves", DDCompat.BLUEPRINT);
        public static final TagKey<Block> WOODEN_BOARDS = TAGS.create("wooden_boards", DDCompat.WOODWORKS);
        public static final TagKey<Block> VERTICAL_PLANKS = TAGS.create("vertical_planks", DDCompat.QUARK);
        public static final TagKey<Block> VERTICAL_SLAB = TAGS.create("vertical_slab", DDCompat.QUARK);
    }

    public static class Biomes {
        private static final TagFactory<Biome> TAGS = make(Registries.BIOME);

        public static final TagKey<Biome> HAS_ROPE_MINE_FOREST = TAGS.create("has_structure/rope_mine_forest");
        public static final TagKey<Biome> HAS_ROPE_MINE_DESERT = TAGS.create("has_structure/rope_mine_desert");
        public static final TagKey<Biome> CATACOMBS = TAGS.create("has_structure/catacombs");
    }
}