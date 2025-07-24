package com.naterbobber.darkerdepths.init;

import com.mojang.datafixers.util.Pair;
import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.data.DDStructureTagsProvider;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.Pools;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.heightproviders.ConstantHeight;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadType;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.structures.JigsawStructure;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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


    public static void bootstrap(BootstapContext<Structure> context) {
        HolderGetter<Biome> biomeGetter = context.lookup(Registries.BIOME);
        HolderGetter<StructureTemplatePool> poolGetter = context.lookup(Registries.TEMPLATE_POOL);

        Structure.StructureSettings forestSettings = new Structure.StructureSettings(
                biomeGetter.getOrThrow(DDStructureTagsProvider.HAS_ROPE_MINE_FOREST),
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
                biomeGetter.getOrThrow(DDStructureTagsProvider.HAS_ROPE_MINE_DESERT),
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
                        new RandomSpreadStructurePlacement(28, 10, RandomSpreadType.LINEAR, 1234567891) // Different values to avoid overlap
                )
        );
    }

    public static void bootstrapTemplatePool(BootstapContext<StructureTemplatePool> context) {
        Holder<StructureTemplatePool> emptyPoolHolder = context.lookup(Registries.TEMPLATE_POOL).getOrThrow(Pools.EMPTY);

        context.register(ROPE_MINE_FOREST_POOL, new StructureTemplatePool(
                emptyPoolHolder, List.of(Pair.of(StructurePoolElement.single("darkerdepths:rope_mine_forest").apply(StructureTemplatePool.Projection.RIGID), 1))
        ));

        context.register(ROPE_MINE_DESERT_POOL, new StructureTemplatePool(
                emptyPoolHolder, List.of(Pair.of(StructurePoolElement.single("darkerdepths:rope_mine_desert").apply(StructureTemplatePool.Projection.RIGID), 1))
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