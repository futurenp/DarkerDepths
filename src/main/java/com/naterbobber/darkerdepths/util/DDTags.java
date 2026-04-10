package com.naterbobber.darkerdepths.util;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.compat.CompatID;
import com.naterbobber.darkerdepths.compat.DDCompat;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;

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
        public static final TagKey<Item> FORSAKEN_BRONZE_INGOT = TAGS.create("ingots/forsaken_bronze");
        public static final TagKey<Item> FORSAKEN_BRONZE_INGOT_FORGE = TAGS.create("ingots/forsaken_bronze", DDCompat.FORGE);
    }

    public static class Blocks {
        private static final TagFactory<Block> TAGS = make(Registries.BLOCK);

        public static final TagKey<Block> GEYSER_BOOSTERS = TAGS.create("geyser_boosters");
        public static final TagKey<Block> GEYSER_ASH_PROVIDERS = TAGS.create("geyser_ash_providers");
        public static final TagKey<Block> GEYSER_BYPASSES = TAGS.create("geyser_bypasses");
        public static final TagKey<Block> HUSKS_SPAWNABLE_ON = TAGS.create("husks_spawnable_on");
        public static final TagKey<Block> VERTICAL_PLANKS = TAGS.create("vertical_planks", DDCompat.QUARK);
        public static final TagKey<Block> WOODEN_BOOKSHELVES = TAGS.create("wooden_bookshelves", DDCompat.QUARK);
        public static final TagKey<Block> VERTICAL_SLAB = TAGS.create("vertical_slab", DDCompat.QUARK);
        public static final TagKey<Block> STRIPPED_LOGS = TAGS.create("stripped_logs");
        public static final TagKey<Block> STRIPPED_WOODS = TAGS.create("stripped_woods");
        public static final TagKey<Block> STRIPPED_LOGS_FORGE = TAGS.create("stripped_logs", DDCompat.FORGE);
        public static final TagKey<Block> STRIPPED_WOODS_FORGE = TAGS.create("stripped_woods", DDCompat.FORGE);
        public static final TagKey<Block> CATACOMBS_STRIPE_REPLACEABLE = TAGS.create("catacombs_stripe_replaceable");
        public static final TagKey<Block> MAGMA_STRIPE_REPLACEABLE = TAGS.create("magma_stripe_replaceable");
        public static final TagKey<Block> ARID_GROUND = TAGS.create("arid_ground");
        public static final TagKey<Block> HUGE_GLOWSHROOM_GROWABLE = TAGS.create("huge_glowshroom_growable");
        public static final TagKey<Block> HEATABLE = TAGS.create("heatable");
        public static final TagKey<Block> VERY_HIGH_HEAT = TAGS.create("very_high_heat");
        public static final TagKey<Block> HIGH_HEAT = TAGS.create("high_heat");
        public static final TagKey<Block> MEDIUM_HEAT = TAGS.create("medium_heat");
        public static final TagKey<Block> LOW_HEAT = TAGS.create("low_heat");
        public static final TagKey<Block> HEAT_PROVIDER = TAGS.create("heat_provider");
        public static final TagKey<Block> GRIME_GROUND = TAGS.create("grime_ground");
        public static final TagKey<Block> AIR = TAGS.create("air");
    }

    public static class Fluids {
        private static final TagFactory<Fluid> TAGS = make(Registries.FLUID);

        public static final TagKey<Fluid> VERY_HIGH_HEAT = TAGS.create("very_high_heat");
        public static final TagKey<Fluid> HIGH_HEAT = TAGS.create("high_heat");
        public static final TagKey<Fluid> MEDIUM_HEAT = TAGS.create("medium_heat");
        public static final TagKey<Fluid> LOW_HEAT = TAGS.create("low_heat");
        public static final TagKey<Fluid> HEAT_PROVIDER = TAGS.create("heat_provider");

    }

    public static class Biomes {
        private static final TagFactory<Biome> TAGS = make(Registries.BIOME);

        public static final TagKey<Biome> HAS_ROPE_MINE_FOREST = TAGS.create("has_structure/rope_mine_forest");
        public static final TagKey<Biome> HAS_ROPE_MINE_DESERT = TAGS.create("has_structure/rope_mine_desert");
        public static final TagKey<Biome> HAS_PETRIFIED_MINESHAFT = TAGS.create("has_structure/petrified_mineshaft");
        public static final TagKey<Biome> CATACOMBS = TAGS.create("has_structure/catacombs");
    }

    public static class EntityTypes {
        private static final TagFactory<EntityType<?>> TAGS = make(Registries.ENTITY_TYPE);

        public static final TagKey<EntityType<?>> GLOWSHROOM_MONSTER_TARGET = TAGS.create("glowshroom_monster_target");
        public static final TagKey<EntityType<?>> VOID_SOUL = TAGS.create("void_soul");
    }
}