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

    public static final RegistryObject<StructureType<JigsawStructure>> HOUSE_JIGSAW_TYPE =
            STRUCTURE_TYPES.register("rope_mine_jigsaw", () -> () -> JigsawStructure.CODEC);

    public static final ResourceKey<Structure> ROPE_MINE_JIGSAW = createStructureKey();
    public static final ResourceKey<StructureSet> ROPE_MINE_SET = createStructureSetKey();
    public static final ResourceKey<StructureTemplatePool> ROPE_MINE_POOL = createPoolKey();


    public static void bootstrap(BootstapContext<Structure> context) {
        HolderGetter<Biome> biomeGetter = context.lookup(Registries.BIOME);
        HolderGetter<StructureTemplatePool> poolGetter = context.lookup(Registries.TEMPLATE_POOL);

        Structure.StructureSettings settings = new Structure.StructureSettings(
                biomeGetter.getOrThrow(DDStructureTagsProvider.HAS_ROPE_MINE),
                Map.of(),
                GenerationStep.Decoration.SURFACE_STRUCTURES,
                TerrainAdjustment.NONE

        );

        context.register(ROPE_MINE_JIGSAW, new JigsawStructure(
                settings,
                poolGetter.getOrThrow(ROPE_MINE_POOL),
                Optional.empty(),
                1,
                ConstantHeight.of(VerticalAnchor.absolute(-10)),
                true,
                Optional.of(Heightmap.Types.WORLD_SURFACE_WG),
                80
        ));
    }

    public static void bootstrapStructureSet(BootstapContext<StructureSet> context) {
        HolderGetter<Structure> structureGetter = context.lookup(Registries.STRUCTURE);
        context.register(ROPE_MINE_SET,
                new StructureSet(
                        structureGetter.getOrThrow(ROPE_MINE_JIGSAW), // Just pass the structure holder directly
                        new RandomSpreadStructurePlacement(24, 8, RandomSpreadType.LINEAR, 1234567890)
                )
        );
    }

    public static void bootstrapTemplatePool(BootstapContext<StructureTemplatePool> context) {
        Holder<StructureTemplatePool> emptyPoolHolder = context.lookup(Registries.TEMPLATE_POOL).getOrThrow(Pools.EMPTY);

        context.register(ROPE_MINE_POOL, new StructureTemplatePool(
                emptyPoolHolder, List.of(Pair.of(StructurePoolElement.single("darkerdepths:rope_mine").apply(StructureTemplatePool.Projection.RIGID), 1))
        ));
    }

    private static ResourceKey<Structure> createStructureKey() {
        return ResourceKey.create(Registries.STRUCTURE, ResourceLocation.fromNamespaceAndPath(DarkerDepths.MODID, "rope_mine_jigsaw"));
    }

    private static ResourceKey<StructureSet> createStructureSetKey() {
        return ResourceKey.create(Registries.STRUCTURE_SET, ResourceLocation.fromNamespaceAndPath(DarkerDepths.MODID, "rope_mine"));
    }

    private static ResourceKey<StructureTemplatePool> createPoolKey() {
        return ResourceKey.create(Registries.TEMPLATE_POOL, ResourceLocation.fromNamespaceAndPath(DarkerDepths.MODID, "rope_mine"));
    }

    public static ResourceKey<BiomeModifier> createKey(String name) {
        return ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, ResourceLocation.fromNamespaceAndPath(DarkerDepths.MODID, name));
    }
}