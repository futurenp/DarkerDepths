package com.naterbobber.darkerdepths.init;

import com.mojang.datafixers.util.Pair;
import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.data.tags.DDBiomeTagsProvider;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.Pools;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.heightproviders.ConstantHeight;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.StructureSpawnOverride;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadType;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.structures.JigsawStructure;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool.Projection.RIGID;

public class DDStructures {
    public static final DeferredRegister<StructureType<?>> STRUCTURE_TYPES =
            DeferredRegister.create(Registries.STRUCTURE_TYPE, DarkerDepths.MODID);

    public static final RegistryObject<StructureType<JigsawStructure>> DD_JIGSAW_TYPE =
            STRUCTURE_TYPES.register("dd_jigsaw", () -> () -> JigsawStructure.CODEC);

    public static final ResourceKey<Structure> ROPE_MINE_FOREST = createStructureKey("rope_mine_forest");
    public static final ResourceKey<StructureSet> ROPE_MINE_FOREST_SET = createStructureSetKey("rope_mine_forest");
    public static final ResourceKey<StructureTemplatePool> ROPE_MINE_FOREST_POOL = createPoolKey("rope_mine_forest");

    public static final ResourceKey<Structure> ROPE_MINE_DESERT = createStructureKey("rope_mine_desert");
    public static final ResourceKey<StructureSet> ROPE_MINE_DESERT_SET = createStructureSetKey("rope_mine_desert");
    public static final ResourceKey<StructureTemplatePool> ROPE_MINE_DESERT_POOL = createPoolKey("rope_mine_desert");

    public static final ResourceKey<Structure> CATACOMBS = createStructureKey("catacombs");
    public static final ResourceKey<StructureSet> CATACOMBS_SET = createStructureSetKey("catacombs_set");
    public static final ResourceKey<StructureTemplatePool> CATACOMBS_STARTS_POOL = createPoolKey("catacombs/starts");
    public static final ResourceKey<StructureTemplatePool> CATACOMBS_HALLS_POOL = createPoolKey("catacombs/halls");
    public static final ResourceKey<StructureTemplatePool> CATACOMBS_CENTER_EXTENSIONS_POOL = createPoolKey("catacombs/center_extensions");
    public static final ResourceKey<StructureTemplatePool> CATACOMBS_HALLS_EXTENSIONS = createPoolKey("catacombs/extensions");

    public static void bootstrap(BootstapContext<Structure> context) {
        HolderGetter<Biome> biomeGetter = context.lookup(Registries.BIOME);
        HolderGetter<StructureTemplatePool> poolGetter = context.lookup(Registries.TEMPLATE_POOL);

        Structure.StructureSettings forestSettings = new Structure.StructureSettings(
                biomeGetter.getOrThrow(DDBiomeTagsProvider.HAS_ROPE_MINE_FOREST),
                Map.of(),
                GenerationStep.Decoration.SURFACE_STRUCTURES,
                TerrainAdjustment.NONE
        );

        context.register(ROPE_MINE_FOREST, new JigsawStructure(
                forestSettings,
                poolGetter.getOrThrow(ROPE_MINE_FOREST_POOL),
                Optional.empty(),
                1,
                ConstantHeight.of(VerticalAnchor.absolute(-10)),
                true,
                Optional.of(Heightmap.Types.WORLD_SURFACE_WG),
                80
        ));

        Structure.StructureSettings desertSettings = new Structure.StructureSettings(
                biomeGetter.getOrThrow(DDBiomeTagsProvider.HAS_ROPE_MINE_DESERT),
                Map.of(),
                GenerationStep.Decoration.SURFACE_STRUCTURES,
                TerrainAdjustment.NONE
        );

        context.register(ROPE_MINE_DESERT, new JigsawStructure(
                desertSettings,
                poolGetter.getOrThrow(ROPE_MINE_DESERT_POOL),
                Optional.empty(),
                1,
                ConstantHeight.of(VerticalAnchor.absolute(-18)),
                true,
                Optional.of(Heightmap.Types.WORLD_SURFACE_WG),
                80
        ));

        Structure.StructureSettings catacombs = new Structure.StructureSettings(
                biomeGetter.getOrThrow(DDBiomeTagsProvider.CATACOMBS),
                Arrays.stream(MobCategory.values()).collect(Collectors.toMap(v -> {
                    return v;
                }, v1 -> {
                    return new StructureSpawnOverride(StructureSpawnOverride.BoundingBoxType.STRUCTURE, WeightedRandomList.create());
                })),
                GenerationStep.Decoration.UNDERGROUND_STRUCTURES,
                TerrainAdjustment.BURY
        );

        context.register(CATACOMBS, new JigsawStructure(
                catacombs,
                poolGetter.getOrThrow(CATACOMBS_STARTS_POOL),
                Optional.empty(),
                7,
                ConstantHeight.of(VerticalAnchor.absolute(-10)),
                false,
                Optional.empty(),
                116
        ));
    }

    public static void bootstrapStructureSet(BootstapContext<StructureSet> context) {
        HolderGetter<Structure> structureGetter = context.lookup(Registries.STRUCTURE);

        context.register(ROPE_MINE_FOREST_SET,
                new StructureSet(
                        structureGetter.getOrThrow(ROPE_MINE_FOREST),
                        new RandomSpreadStructurePlacement(24, 8, RandomSpreadType.LINEAR, 1234567890)
                )
        );

        context.register(ROPE_MINE_DESERT_SET,
                new StructureSet(
                        structureGetter.getOrThrow(ROPE_MINE_DESERT),
                        new RandomSpreadStructurePlacement(28, 10, RandomSpreadType.LINEAR, 1234567891)
                )
        );

        context.register(CATACOMBS_SET,
                new StructureSet(
                        structureGetter.getOrThrow(CATACOMBS),
                        new RandomSpreadStructurePlacement(24, 8, RandomSpreadType.LINEAR, 20083232)
                )
        );
    }

    public static void bootstrapTemplatePool(BootstapContext<StructureTemplatePool> context) {
        Holder<StructureTemplatePool> emptyPoolHolder = context.lookup(Registries.TEMPLATE_POOL).getOrThrow(Pools.EMPTY);
        HolderGetter<StructureProcessorList> processorList = context.lookup(Registries.PROCESSOR_LIST);

        context.register(ROPE_MINE_FOREST_POOL, new StructureTemplatePool(
                emptyPoolHolder, List.of(Pair.of(StructurePoolElement.single("darkerdepths:rope_mine_forest").apply(RIGID), 1))
        ));

        context.register(ROPE_MINE_DESERT_POOL, new StructureTemplatePool(
                emptyPoolHolder, List.of(Pair.of(StructurePoolElement.single("darkerdepths:rope_mine_desert").apply(RIGID), 1))
        ));

        context.register(CATACOMBS_STARTS_POOL, new StructureTemplatePool(
                emptyPoolHolder,
                List.of(
                        Pair.of(StructurePoolElement.single("darkerdepths:catacombs/center_1").apply(RIGID), 1),
                        Pair.of(StructurePoolElement.single("darkerdepths:catacombs/center_2").apply(RIGID), 1)
                )
        ));

        context.register(CATACOMBS_CENTER_EXTENSIONS_POOL, new StructureTemplatePool(
                emptyPoolHolder,
                List.of(
                        Pair.of(StructurePoolElement.single("darkerdepths:catacombs/cross_section_1").apply(RIGID), 5),
                        Pair.of(StructurePoolElement.single("darkerdepths:catacombs/cross_section_2").apply(RIGID), 5),
                        Pair.of(StructurePoolElement.single("darkerdepths:catacombs/cross_section_3").apply(RIGID), 5),
                        Pair.of(StructurePoolElement.single("darkerdepths:catacombs/cross_section_4").apply(RIGID), 5),
                        Pair.of(StructurePoolElement.single("darkerdepths:catacombs/cross_section_5").apply(RIGID), 5),
                        Pair.of(StructurePoolElement.single("darkerdepths:catacombs/cross_section_6").apply(RIGID), 5),
                        Pair.of(StructurePoolElement.single("darkerdepths:catacombs/hallway_1").apply(RIGID), 20),
                        Pair.of(StructurePoolElement.single("darkerdepths:catacombs/hallway_2").apply(RIGID), 20),
                        Pair.of(StructurePoolElement.single("darkerdepths:catacombs/hallway_3").apply(RIGID), 20),
                        Pair.of(StructurePoolElement.single("darkerdepths:catacombs/hallway_4").apply(RIGID), 20),
                        Pair.of(StructurePoolElement.single("darkerdepths:catacombs/hallway_large_1").apply(RIGID), 20),
                        Pair.of(StructurePoolElement.single("darkerdepths:catacombs/hallway_large_2").apply(RIGID), 20)
                )
        ));

        context.register(CATACOMBS_HALLS_POOL, new StructureTemplatePool(
                emptyPoolHolder,
                List.of(
                        Pair.of(StructurePoolElement.single("darkerdepths:catacombs/cross_section_1").apply(RIGID), 30),
                        Pair.of(StructurePoolElement.single("darkerdepths:catacombs/cross_section_2").apply(RIGID), 30),
                        Pair.of(StructurePoolElement.single("darkerdepths:catacombs/cross_section_3").apply(RIGID), 30),
                        Pair.of(StructurePoolElement.single("darkerdepths:catacombs/cross_section_4").apply(RIGID), 30),
                        Pair.of(StructurePoolElement.single("darkerdepths:catacombs/cross_section_5").apply(RIGID), 30),
                        Pair.of(StructurePoolElement.single("darkerdepths:catacombs/cross_section_6").apply(RIGID), 30),
                        Pair.of(StructurePoolElement.single("darkerdepths:catacombs/hallway_1").apply(RIGID), 10),
                        Pair.of(StructurePoolElement.single("darkerdepths:catacombs/hallway_2").apply(RIGID), 10),
                        Pair.of(StructurePoolElement.single("darkerdepths:catacombs/hallway_3").apply(RIGID), 10),
                        Pair.of(StructurePoolElement.single("darkerdepths:catacombs/hallway_4").apply(RIGID), 10),
                        Pair.of(StructurePoolElement.single("darkerdepths:catacombs/hallway_large_1").apply(RIGID), 10),
                        Pair.of(StructurePoolElement.single("darkerdepths:catacombs/hallway_large_2").apply(RIGID), 10)
                )
        ));

        context.register(CATACOMBS_HALLS_EXTENSIONS, new StructureTemplatePool(
                emptyPoolHolder,
                List.of(
                        Pair.of(StructurePoolElement.single("darkerdepths:catacombs/hallway_1").apply(RIGID), 50),
                        Pair.of(StructurePoolElement.single("darkerdepths:catacombs/hallway_2").apply(RIGID), 50),
                        Pair.of(StructurePoolElement.single("darkerdepths:catacombs/hallway_3").apply(RIGID), 50),
                        Pair.of(StructurePoolElement.single("darkerdepths:catacombs/hallway_4").apply(RIGID), 50),
                        Pair.of(StructurePoolElement.single("darkerdepths:catacombs/hallway_large_1").apply(RIGID), 50),
                        Pair.of(StructurePoolElement.single("darkerdepths:catacombs/hallway_large_2").apply(RIGID), 50),
                        Pair.of(StructurePoolElement.single("darkerdepths:catacombs/room_1").apply(RIGID), 10),
                        Pair.of(StructurePoolElement.single("darkerdepths:catacombs/room_2").apply(RIGID), 10),
                        Pair.of(StructurePoolElement.single("darkerdepths:catacombs/room_3").apply(RIGID), 5),
                        Pair.of(StructurePoolElement.single("darkerdepths:catacombs/room_4").apply(RIGID), 10),
                        Pair.of(StructurePoolElement.single("darkerdepths:catacombs/room_5").apply(RIGID), 10),
                        Pair.of(StructurePoolElement.single("darkerdepths:catacombs/room_6").apply(RIGID), 10),
                        Pair.of(StructurePoolElement.single("darkerdepths:catacombs/room_7").apply(RIGID), 10),
                        Pair.of(StructurePoolElement.single("darkerdepths:catacombs/room_8").apply(RIGID), 10),
                        Pair.of(StructurePoolElement.single("darkerdepths:catacombs/treasure_room_1", processorList.getOrThrow(DDProcessorLists.TOMBS)).apply(RIGID), 30),
                        Pair.of(StructurePoolElement.single("darkerdepths:catacombs/treasure_room_2", processorList.getOrThrow(DDProcessorLists.TOMBS)).apply(RIGID), 30),
                        Pair.of(StructurePoolElement.single("darkerdepths:catacombs/treasure_room_3", processorList.getOrThrow(DDProcessorLists.TOMBS)).apply(RIGID), 20)
                )
        ));
    }

    private static ResourceKey<Structure> createStructureKey(String name) {
        return ResourceKey.create(Registries.STRUCTURE, ResourceLocation.fromNamespaceAndPath(DarkerDepths.MODID, name));
    }

    private static ResourceKey<StructureSet> createStructureSetKey(String name) {
        return ResourceKey.create(Registries.STRUCTURE_SET, ResourceLocation.fromNamespaceAndPath(DarkerDepths.MODID, name));
    }

    private static ResourceKey<StructureTemplatePool> createPoolKey(String name) {
        return ResourceKey.create(Registries.TEMPLATE_POOL, ResourceLocation.fromNamespaceAndPath(DarkerDepths.MODID, name));
    }

    public static ResourceKey<BiomeModifier> createKey(String name) {
        return ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, ResourceLocation.fromNamespaceAndPath(DarkerDepths.MODID, name));
    }
}