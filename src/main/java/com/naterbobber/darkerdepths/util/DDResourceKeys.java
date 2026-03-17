package com.naterbobber.darkerdepths.util;

import com.google.common.collect.Lists;
import com.naterbobber.darkerdepths.DarkerDepths;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import net.minecraft.world.level.storage.loot.LootTable;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.List;

public class DDResourceKeys {
    public static class Biomes {
        public static final List<ResourceKey<Biome>> BIOMES = Lists.newArrayList();
        public static final ResourceKey<Biome> MOLTEN_CAVERN = createKey("molten_cavern");
        public static final ResourceKey<Biome> SANDY_CATACOMBS = createKey("sandy_catacombs");
        public static final ResourceKey<Biome> GLOWSHROOM_FOREST = createKey("glowshroom_forest");

        public static ResourceKey<Biome> createKey(String name) {
            ResourceKey<Biome> key = ResourceKey.create(Registries.BIOME, DarkerDepths.id(name));
            BIOMES.add(key);
            return key;
        }
    }
    
    public static class BiomeModifiers {
        public static final ResourceKey<BiomeModifier> ADD_MOLTEN_CAVERNS_VEGETAL_FEATURES = createKey("add_molten_caverns_vegetal_features");
        public static final ResourceKey<BiomeModifier> ADD_MOLTEN_CAVERNS_RAW_GENERATION = createKey("add_molten_caverns_raw_generation");
        public static final ResourceKey<BiomeModifier> ADD_MOLTEN_CAVERNS_LOCAL_MODIFICATIONS = createKey("add_molten_caverns_local_modifications");
        public static final ResourceKey<BiomeModifier> ADD_MOLTEN_CAVERNS_ORES = createKey("add_molten_caverns_ores");
        public static final ResourceKey<BiomeModifier> ADD_SANDY_CATACOMBS_LOCAL_MODIFICATIONS = createKey("add_sandy_catacombs_local_modifications");
        public static final ResourceKey<BiomeModifier> ADD_SANDY_CATACOMBS_VEGETAL_FEATURES = createKey("add_sandy_catacombs_vegetal_features");
        public static final ResourceKey<BiomeModifier> ADD_SANDY_CATACOMBS_SPAWNS = createKey("add_sandy_catacombs_spawns");
        public static final ResourceKey<BiomeModifier> ADD_SANDY_CATACOMBS_RAW_GENERATION = createKey("add_sandy_catacombs_raw_generation");
        public static final ResourceKey<BiomeModifier> ADD_SANDY_CATACOMBS_UNDERGROUND_DECORATION = createKey("add_sandy_catacombs_underground_decoration");
        public static final ResourceKey<BiomeModifier> ADD_GLOWSHROOM_FOREST_VEGETAL_FEATURES = createKey("add_glowshroom_forest_vegetal_features");
        public static final ResourceKey<BiomeModifier> ADD_GLOWSHROOM_FOREST_SPAWNS = createKey("add_glowshroom_forest_spawns");

        private static ResourceKey<BiomeModifier> createKey(String name) {
            return ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, DarkerDepths.id(name));
        }
    }

    public static class ConfiguredFeatures {
        public static final ResourceKey<ConfiguredFeature<?, ?>> GLOWSHROOM_PATCH = createKey("glowshroom_patch");
        public static final ResourceKey<ConfiguredFeature<?, ?>> HUGE_GLOWSHROOM = createKey("huge_glowshroom");
        public static final ResourceKey<ConfiguredFeature<?, ?>> HUGE_GLOWSHROOM_PLANTED = createKey("huge_glowshroom_planted");
        public static final ResourceKey<ConfiguredFeature<?, ?>> AMBERS_PLACEMENT = createKey("amber_placement");
        public static final ResourceKey<ConfiguredFeature<?, ?>> MOLTEN_SPRING = createKey("molten_spring");
        public static final ResourceKey<ConfiguredFeature<?, ?>> LARGE_MOLTEN_PILLAR = createKey("large_molten_pillar");
        public static final ResourceKey<ConfiguredFeature<?, ?>> SCORCHER_PLACER = createKey("scorcher_placer");
        public static final ResourceKey<ConfiguredFeature<?, ?>> ASH_PLACEMENTS = createKey("ash_placement");
        public static final ResourceKey<ConfiguredFeature<?, ?>> GEYSER_PLACEMENT = createKey("geyser_placement");
        public static final ResourceKey<ConfiguredFeature<?, ?>> MOLTEN_POOL = createKey("molten_pool");
        public static final ResourceKey<ConfiguredFeature<?, ?>> GRIME_VEGETATION = createKey("grime_vegetation");
        public static final ResourceKey<ConfiguredFeature<?, ?>> ARID_VEGETATION = createKey("arid_vegetation");
        public static final ResourceKey<ConfiguredFeature<?, ?>> DARKSLATE_VEGETATION = createKey("darkslate_vegetation");
        public static final ResourceKey<ConfiguredFeature<?, ?>> DARKSLATE_PLACEMENT = createKey("darkslate_placement");
        public static final ResourceKey<ConfiguredFeature<?, ?>> CATACOMBS_SAND_PLACEMENT = createKey("catacombs_sand_placement");
        public static final ResourceKey<ConfiguredFeature<?, ?>> CATACOMBS_LAYERED_PLACEMENT = createKey("catacombs_layer_placement");
        public static final ResourceKey<ConfiguredFeature<?, ?>> SHORT_PETRIFIED_BRANCH = createKey("short_petrified_branch");
        public static final ResourceKey<ConfiguredFeature<?, ?>> LONG_PETRIFIED_BRANCH = createKey("long_petrified_branch");
        public static final ResourceKey<ConfiguredFeature<?, ?>> PETRIFIED_BRANCH = createKey("petrified_branch");
        public static final ResourceKey<ConfiguredFeature<?, ?>> MAGMA_ORE = createKey("magma_ore");
        public static final ResourceKey<ConfiguredFeature<?, ?>> MAGMA_DISK = createKey("magma_disk");
        public static final ResourceKey<ConfiguredFeature<?, ?>> DARKSLATE_SURFACE = createKey("darkslate_surface");
        public static final ResourceKey<ConfiguredFeature<?, ?>> ARID_SURFACE = createKey("arid_surface");
        public static final ResourceKey<ConfiguredFeature<?, ?>> GRIME_SURFACE = createKey("grime_surface");
        public static final ResourceKey<ConfiguredFeature<?, ?>> ARID_BOULDER = createKey("arid_boulder");
        public static final ResourceKey<ConfiguredFeature<?, ?>> CATACOMBS_LAVA_LINING = createKey("catacombs_lava_lining");
        public static final ResourceKey<ConfiguredFeature<?, ?>> DUSKROCK_STRIPE = createKey("duskrock_stripe");
        public static final ResourceKey<ConfiguredFeature<?, ?>> GLIMMERING_VINES = createKey("glimmering_vines");
        public static final ResourceKey<ConfiguredFeature<?, ?>> CRYSTAL_HUSK_ORE = createKey("crystal_husk_ore");
        public static final ResourceKey<ConfiguredFeature<?, ?>> DUSKROCK_ORE = createKey("duskrock_ore");
        public static ResourceKey<ConfiguredFeature<?, ?>> createKey(String name) {
            return ResourceKey.create(Registries.CONFIGURED_FEATURE, DarkerDepths.id(name));
        }
    }

    public static class PlacedFeatures {
        public static final ResourceKey<PlacedFeature> HUGE_GLOWSHROOM = createKey("huge_glowshroom");
        public static final ResourceKey<PlacedFeature> GLOWSHROOM_PATCH = createKey("glowshroom_patch");
        public static final ResourceKey<PlacedFeature> AMBER_PLACEMENT = createKey("amber");
        public static final ResourceKey<PlacedFeature> MOLTEN_SPRING = createKey("molten_spring");
        public static final ResourceKey<PlacedFeature> MOLTEN_POOL = createKey("molten_pool");
        public static final ResourceKey<PlacedFeature> DARKSLATE_PLACEMENT = createKey("darkslate_placement");
        public static final ResourceKey<PlacedFeature> DARKSLATE_VEGETATION = createKey("darkslate_vegetation");
        public static final ResourceKey<PlacedFeature> LARGE_MOLTEN_PILLAR = createKey("large_molten_pillar");
        public static final ResourceKey<PlacedFeature> SCORCHER_PLACER = createKey("scorcher_placer");
        public static final ResourceKey<PlacedFeature> CATACOMBS_SAND_PLACEMENT = createKey("catacombs_sand_placement");
        public static final ResourceKey<PlacedFeature> CATACOMBS_LAYERED_PLACEMENT = createKey("catacombs_layered_placement");
        public static final ResourceKey<PlacedFeature> PETRIFIED_BRANCH = createKey("petrified_branch");
        public static final ResourceKey<PlacedFeature> MAGMA_ORE = createKey("magma_ore");
        public static final ResourceKey<PlacedFeature> MAGMA_DISK = createKey("magma_disk");
        public static final ResourceKey<PlacedFeature> GRIME_SURFACE = createKey("grime_surface");
        public static final ResourceKey<PlacedFeature> ARID_SURFACE = createKey("arid_surface");
        public static final ResourceKey<PlacedFeature> DARKSLATE_SURFACE = createKey("darkslate_surface");
        public static final ResourceKey<PlacedFeature> ARID_BOULDER = createKey("arid_boulder");
        public static final ResourceKey<PlacedFeature> CATACOMBS_LAVA_LINING = createKey("catacombs_lava_lining");
        public static final ResourceKey<PlacedFeature> DUSKROCK_STRIPE = createKey("duskrock_stripe");
        public static final ResourceKey<PlacedFeature> GLIMMERING_VINES = createKey("glimmering_vines");
        public static final ResourceKey<PlacedFeature> CRYSTAL_HUSK_ORE = createKey("crystal_husk_ore");
        public static final ResourceKey<PlacedFeature> DUSKROCK_ORE = createKey("duskrock_ore");

        public static ResourceKey<PlacedFeature> createKey(String name) {
            return ResourceKey.create(Registries.PLACED_FEATURE, DarkerDepths.id(name));
        }
    }
    
    public static class Structures {
        public static final ResourceKey<Structure> ROPE_MINE_FOREST = createKey("rope_mine_forest");
        public static final ResourceKey<Structure> ROPE_MINE_DESERT = createKey("rope_mine_desert");
        public static final ResourceKey<Structure> CATACOMBS = createKey("catacombs");
        public static final ResourceKey<Structure> PETRIFIED_MINESHAFT = createKey("petrified_mineshaft");

        private static ResourceKey<net.minecraft.world.level.levelgen.structure.Structure> createKey(String name) {
            return ResourceKey.create(Registries.STRUCTURE, DarkerDepths.id(name));
        }
    }

    public static class StructureSets {
        public static final ResourceKey<StructureSet> ROPE_MINE_FOREST_SET = createKey("rope_mine_forest");
        public static final ResourceKey<StructureSet> ROPE_MINE_DESERT_SET = createKey("rope_mine_desert");
        public static final ResourceKey<StructureSet> PETRIFIED_MINESHAFT_SET = createKey("petrified_mineshaft");
        public static final ResourceKey<StructureSet> CATACOMBS_SET = createKey("catacombs_set");

        private static ResourceKey<StructureSet> createKey(String name) {
            return ResourceKey.create(Registries.STRUCTURE_SET, DarkerDepths.id(name));
        }
    }

    public static class StructureTemplatePools {
        public static final ResourceKey<StructureTemplatePool> ROPE_MINE_FOREST_POOL = createKey("rope_mine_forest");
        public static final ResourceKey<StructureTemplatePool> ROPE_MINE_DESERT_POOL = createKey("rope_mine_desert");
        public static final ResourceKey<StructureTemplatePool> CATACOMBS_STARTS_POOL = createKey("catacombs/starts");
        public static final ResourceKey<StructureTemplatePool> CATACOMBS_HALLS_POOL = createKey("catacombs/halls");
        public static final ResourceKey<StructureTemplatePool> CATACOMBS_CENTER_EXTENSIONS_POOL = createKey("catacombs/center_extensions");
        public static final ResourceKey<StructureTemplatePool> CATACOMBS_HALLS_EXTENSIONS = createKey("catacombs/extensions");

        private static ResourceKey<StructureTemplatePool> createKey(String name) {
            return ResourceKey.create(Registries.TEMPLATE_POOL, DarkerDepths.id(name));
        }
    }

    public static class StructureProcessorLists {
        public static final ResourceKey<StructureProcessorList> CATACOMBS_PROCESSOR = createKey("catacombs_processor");
        public static final ResourceKey<StructureProcessorList> ROPE_MINES_PROCESSOR = createKey("rope_mines_processor");

        private static ResourceKey<StructureProcessorList> createKey(String pName) {
            return ResourceKey.create(Registries.PROCESSOR_LIST, DarkerDepths.id(pName));
        }
    }

    public static class Enchantments {
        public static final List<ResourceKey<Enchantment>> ENCHANTMENTS = Lists.newArrayList();
        public static final ResourceKey<Enchantment> SWIFT_STRIKE = createKey("swift_strike");
        public static final ResourceKey<Enchantment> QUICK_DASH = createKey("quick_dash");

        private static ResourceKey<Enchantment> createKey(String name) {
            ResourceKey<Enchantment> key = ResourceKey.create(Registries.ENCHANTMENT, DarkerDepths.id(name));
            ENCHANTMENTS.add(key);
            return key;
        }
    }

    public static class DamageTypes {
        public static final ResourceKey<DamageType> SOUL_BINDING_DAMAGE = createKey("soul_binding_damage");
        private static ResourceKey<DamageType> createKey(String name) {
            return ResourceKey.create(Registries.DAMAGE_TYPE, DarkerDepths.id(name));
        }
    }

    public static class LootTables {
        public static final ResourceKey<LootTable> CATACOMBS_CHEST_STANDARD = createKey("chests/catacombs/standard");
        public static final ResourceKey<LootTable> CATACOMBS_CHEST_TREASURE = createKey("chests/catacombs/treasure");
        public static final ResourceKey<LootTable> CATACOMBS_ARCHAEOLOGY_STANDARD = createKey("archaeology/catacombs/standard");

        private static ResourceKey<LootTable> createKey(String name) {
            return ResourceKey.create(Registries.LOOT_TABLE, DarkerDepths.id(name));
        }
    }
}
