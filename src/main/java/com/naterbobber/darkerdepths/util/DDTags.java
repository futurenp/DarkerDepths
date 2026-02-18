package com.naterbobber.darkerdepths.util;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.compat.CompatID;
import com.naterbobber.darkerdepths.compat.DDCompat;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;

public class DDTags {
    private static <T> TagKey<T> createTag(String tag) {
        TagKey<T> type = new TagKey<T>();
        var registry = type.registry();
        return TagKey.create(registry, DarkerDepths.id(tag));
    }

    private static <T> TagKey<T> createCompatTag(ResourceKey<Registry<T>> registry, String tag, CompatID compatID) {
        return TagKey.create(registry, ResourceLocation.fromNamespaceAndPath(compatID.toString(), tag));
    }

    public static class Items {
        private static TagKey<Item> tag(String tag) {
            return createTag(Registries.ITEM, tag);
        }
        private static final ResourceKey<Registry<Item>> RK = Registries.ITEM;
        public static final TagKey<Item> PETRIFIED_LOGS = tag("petrified_logs");
        public static final TagKey<Item> STILETTO_ENCHANTABLE = tag("stiletto_enchantable");

    }

    public static class Blocks {

        public static final TagKey<Block> GEYSER_BOOSTERS = tag("geyser_boosters");
        public static final TagKey<Block> GEYSER_BYPASSES = tag("geyser_bypasses");
        public static final TagKey<Block> HUSKS_SPAWNABLE_ON = tag("husks_spawnable_on");
        public static final TagKey<Block> TRIMMED_PLANKS = tag("trimmed_planks", DDCompat.NO_MANS_LAND);
        public static final TagKey<Block> WOODEN_BOOKSHELVES = tag("wooden_bookshelves", DDCompat.BLUEPRINT);
        public static final TagKey<Block> WOODEN_BOARDS = tag("wooden_boards", DDCompat.WOODWORKS);
        public static final TagKey<Block> VERTICAL_PLANKS = tag("vertical_planks", DDCompat.QUARK);
        public static final TagKey<Block> VERTICAL_SLAB = tag("vertical_slab", DDCompat.QUARK);
    }

    public static class Biomes {
        private static TagKey<Biome> tag(String tag) {
            return createTag(Registries.BIOME, tag);
        }
        public static final TagKey<Biome> HAS_ROPE_MINE_FOREST = tag("has_structure/rope_mine_forest");
        public static final TagKey<Biome> HAS_ROPE_MINE_DESERT = tag("has_structure/rope_mine_desert");
        public static final TagKey<Biome> CATACOMBS = tag("has_structure/catacombs");
    }
}
