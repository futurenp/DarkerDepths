package com.naterbobber.darkerdepths.worldgen.feature;

import com.google.common.collect.ImmutableList;
import com.naterbobber.darkerdepths.block.custom.AshBlock;
import com.naterbobber.darkerdepths.block.custom.GlowshroomBlock;
import com.naterbobber.darkerdepths.block.custom.GlowspursBlock;
import com.naterbobber.darkerdepths.init.DDBlocks;
import com.naterbobber.darkerdepths.init.DDFeatures;
import com.naterbobber.darkerdepths.util.DDTags;
import com.naterbobber.darkerdepths.worldgen.feature.config.*;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.RuleBasedBlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.placement.CaveSurface;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.minecraft.world.level.material.Fluids;

import java.util.List;
import java.util.Optional;

import static com.naterbobber.darkerdepths.util.DDResourceKeys.ConfiguredFeatures.*;

public class DDConfiguredFeatures {

    public static final ImmutableList<BlockState> OVERWORLD_REPLACEABLES    = ImmutableList.of(
            Blocks.STONE.defaultBlockState(),
            Blocks.ANDESITE.defaultBlockState(),
            Blocks.GRANITE.defaultBlockState(),
            Blocks.DIORITE.defaultBlockState(),
            Blocks.DIRT.defaultBlockState(),
            DDBlocks.DARKSLATE.get().defaultBlockState(),
            DDBlocks.ARIDROCK.get().defaultBlockState(),
            DDBlocks.ARID_DEEPSLATE.get().defaultBlockState(),
//            DDBlocks.DUSKROCK.get().defaultBlockState(),
            Blocks.GRAVEL.defaultBlockState(),
            Blocks.DEEPSLATE.defaultBlockState(),
            Blocks.TUFF.defaultBlockState()
    );


    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        HolderGetter<ConfiguredFeature<?, ?>> lookup = context.lookup(Registries.CONFIGURED_FEATURE);
        FeatureUtils.register(context, GLOWSHROOM_PATCH, DDFeatures.RANDOM_GLOWSHROOM_PATCHES.get(), FeatureConfiguration.NONE);
        FeatureUtils.register(context, HUGE_GLOWSHROOM, DDFeatures.HUGE_GLOWSHROOM.get(), FeatureConfiguration.NONE);
        FeatureUtils.register(context, HUGE_GLOWSHROOM_PLANTED, DDFeatures.HUGE_GLOWSHROOM.get(), FeatureConfiguration.NONE);
        FeatureUtils.register(context, AMBERS_PLACEMENT, DDFeatures.GEMSTONE.get(), FeatureConfiguration.NONE);
        FeatureUtils.register(context, ARID_BOULDER, DDFeatures.ARID_BOULDER.get(), FeatureConfiguration.NONE);
        FeatureUtils.register(context, CATACOMBS_LAVA_LINING, DDFeatures.CATACOMBS_LAVA_LINING.get(), FeatureConfiguration.NONE);
        FeatureUtils.register(context, DUSKROCK_STRIPE, DDFeatures.DUSKROCK_STRIPE.get(), FeatureConfiguration.NONE);
        FeatureUtils.register(context, MOLTEN_SPRING, Feature.SPRING, new SpringConfiguration(
                Fluids.LAVA.defaultFluidState(),
                false, 4,
                1,
                HolderSet.direct(Block::builtInRegistryHolder, Blocks.STONE, Blocks.GRANITE, Blocks.DIORITE, Blocks.ANDESITE, Blocks.DEEPSLATE, Blocks.TUFF, Blocks.CALCITE, Blocks.DIRT, DDBlocks.DARKSLATE.get())
        ));

        FeatureUtils.register(context, ASH_PLACEMENTS, Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(
                new WeightedStateProvider(
                        SimpleWeightedRandomList.<BlockState>builder()
                                .add(DDBlocks.ASH.get().defaultBlockState().setValue(AshBlock.LAYERS, 1), 25)
                                .add(DDBlocks.ASH.get().defaultBlockState().setValue(AshBlock.LAYERS, 2), 15)
                                .build()
                )
        ));

        FeatureUtils.register(context, GEYSER_PLACEMENT, DDFeatures.GEYSER_UNDER_FLUID.get(), new SimpleBlockConfiguration(
                BlockStateProvider.simple(DDBlocks.GEYSER.get().defaultBlockState())
        ));

        FeatureUtils.register(context, MOLTEN_POOL, DDFeatures.LAVA_VEGETATION_PATCH_FEATURE.get(), new VegetationPatchConfiguration(
                BlockTags.BASE_STONE_OVERWORLD,
                BlockStateProvider.simple(DDBlocks.DARKSLATE.get().defaultBlockState()),
                PlacementUtils.inlinePlaced(lookup.getOrThrow(GEYSER_PLACEMENT)),
                CaveSurface.FLOOR,
                ConstantInt.of(1), 0.0F, 5, 0.4F, UniformInt.of(4, 7), 0.3F)
        );

        FeatureUtils.register(context, GRIME_VEGETATION, Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(
                new WeightedStateProvider(
                        SimpleWeightedRandomList.<BlockState>builder()
                                .add(DDBlocks.GLOWSHROOM.get().defaultBlockState().setValue(GlowshroomBlock.GLOWSHROOM_CLUSTERS, 1), 4)
                                .add(DDBlocks.GLOWSHROOM.get().defaultBlockState().setValue(GlowshroomBlock.GLOWSHROOM_CLUSTERS, 2), 2)
                                .add(DDBlocks.GLOWSHROOM.get().defaultBlockState().setValue(GlowshroomBlock.GLOWSHROOM_CLUSTERS, 3), 1)
                                .add(DDBlocks.GLOWSPURS.get().defaultBlockState().setValue(GlowspursBlock.FACING, Direction.NORTH), 1)
                                .add(DDBlocks.GLOWSPURS.get().defaultBlockState().setValue(GlowspursBlock.FACING, Direction.EAST), 1)
                                .add(DDBlocks.GLOWSPURS.get().defaultBlockState().setValue(GlowspursBlock.FACING, Direction.SOUTH), 1)
                                .add(DDBlocks.GLOWSPURS.get().defaultBlockState().setValue(GlowspursBlock.FACING, Direction.WEST), 1)
                                .add(DDBlocks.MOSSY_SPROUTS.get().defaultBlockState(), 120)
                                .add(Blocks.AIR.defaultBlockState(), 1000)
                )
        ));

        FeatureUtils.register(context, ARID_VEGETATION, DDFeatures.RANDOM_FLOOR_PLACEMENT.get(), new RandomFloorPlacementConfig(
                new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
                        .add(DDBlocks.DRY_SPROUTS.get().defaultBlockState(), 1)
                        .add(Blocks.AIR.defaultBlockState(), 2)
                        .build()
                ),
                HolderSet.direct(
                        DDBlocks.ARIDROCK.getDelegate(),
                        DDBlocks.DUSKROCK.getDelegate(),
                        Blocks.PACKED_MUD.builtInRegistryHolder()),
                15,
                4,
                3,
                7
                )
        );

        FeatureUtils.register(context, DARKSLATE_VEGETATION, DDFeatures.RANDOM_FLOOR_PLACEMENT.get(), new RandomFloorPlacementConfig(
                new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
                        .add(DDBlocks.SCORCHED_REMAINS.get().defaultBlockState(), 1)
                        .build()
                ),
                HolderSet.direct(DDBlocks.DARKSLATE.getDelegate()),
                10,
                4,
                2,
                1
                )
        );

        FeatureUtils.register(context, SCORCHED_REMAINS_PILE, DDFeatures.SMALL_BOULDER.get(), new SmallBoulderConfiguration(
                BlockStateProvider.simple(DDBlocks.SCORCHED_REMAINS_BLOCK.get()),
                Optional.of(BlockStateProvider.simple(DDBlocks.SCORCHED_REMAINS.get())),
                0.25F
        ));

        FeatureUtils.register(context, DARKSLATE_PLACEMENT, DDFeatures.REPLACE_LIST.get(), new ReplaceListConfig(
                OVERWORLD_REPLACEABLES,
                DDBlocks.DARKSLATE.get().defaultBlockState(),
                UniformInt.of(6, 9)

        ));

        FeatureUtils.register(context, CATACOMBS_SAND_PLACEMENT, DDFeatures.REPLACE_LIST.get(), new ReplaceListConfig(
                List.of(DDBlocks.ARIDROCK.get().defaultBlockState()),
                Blocks.SAND.defaultBlockState(),
                UniformInt.of(6, 12)

        ));

        FeatureUtils.register(context, CATACOMBS_LAYERED_PLACEMENT, DDFeatures.REPLACE_LIST_LAYERED.get(), new ReplaceListLayeredConfig(
                OVERWORLD_REPLACEABLES,
                UniformInt.of(8, 10),
                ImmutableList.of(
                        DDBlocks.ARIDROCK.get().defaultBlockState(),
                        Blocks.PACKED_MUD.defaultBlockState(),
                        DDBlocks.ARIDROCK.get().defaultBlockState(),
                        DDBlocks.ARIDROCK.get().defaultBlockState(),
                        Blocks.PACKED_MUD.defaultBlockState(),
                        Blocks.PACKED_MUD.defaultBlockState(),
                        DDBlocks.DUSKROCK.get().defaultBlockState(),
                        DDBlocks.DUSKROCK.get().defaultBlockState(),
                        Blocks.PACKED_MUD.defaultBlockState(),
                        Blocks.PACKED_MUD.defaultBlockState(),
                        DDBlocks.ARIDROCK.get().defaultBlockState()
                )

        ));

        FeatureUtils.register(context, SHORT_PETRIFIED_BRANCH, DDFeatures.PETRIFIED_BRANCH.get(), new PetrifiedBranchConfig(4, 8));

        FeatureUtils.register(context, LONG_PETRIFIED_BRANCH, DDFeatures.PETRIFIED_BRANCH.get(), new PetrifiedBranchConfig(8, 16));

        FeatureUtils.register(context, PETRIFIED_BRANCH, Feature.RANDOM_BOOLEAN_SELECTOR, new RandomBooleanFeatureConfiguration(
                PlacementUtils.inlinePlaced(lookup.getOrThrow(SHORT_PETRIFIED_BRANCH)),
                PlacementUtils.inlinePlaced(lookup.getOrThrow(LONG_PETRIFIED_BRANCH))
        ));

        FeatureUtils.register(context, CRYSTAL_HUSK_ORE, Feature.SCATTERED_ORE, new OreConfiguration(
                List.of(
                        OreConfiguration.target(
                                new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES),
                                DDBlocks.CRYSTAL_HUSK.get().defaultBlockState()
                        ),
                        OreConfiguration.target(
                                new BlockMatchTest(DDBlocks.DARKSLATE.get()),
                                DDBlocks.CRYSTAL_HUSK.get().defaultBlockState()
                        )
                ),
                5,
                0F)
        );

        FeatureUtils.register(context, MAGMA_ORE, Feature.ORE, new OreConfiguration(
                List.of(
                        OreConfiguration.target(
                                new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES),
                                Blocks.MAGMA_BLOCK.defaultBlockState()
                        ),
                        OreConfiguration.target(
                                new BlockMatchTest(DDBlocks.DARKSLATE.get()),
                                Blocks.MAGMA_BLOCK.defaultBlockState()
                        )
                ),
                30)
        );

        FeatureUtils.register(context, MAGMA_DISK, Feature.DISK,
                new DiskConfiguration(
                        RuleBasedBlockStateProvider.simple(Blocks.MAGMA_BLOCK),
                        BlockPredicate.matchesBlocks(DDBlocks.DARKSLATE.get()),
                        UniformInt.of(2, 4),
                        1
                )
        );

        FeatureUtils.register(context, LARGE_MOLTEN_PILLAR,
                DDFeatures.LARGE_MOLTEN_PILLAR.get(),
                new LargeDripstoneConfiguration(
                        30,
                        UniformInt.of(3, 19),
                        UniformFloat.of(0.4F, 2.0F),
                        0.33F,
                        UniformFloat.of(0.3F, 0.9F),
                        UniformFloat.of(0.4F, 1.0F),
                        UniformFloat.of(0.0F, 0.3F),
                        4,
                        0.6F)
        );

        FeatureUtils.register(context, SCORCHER_PLACER,
                DDFeatures.SCORCHER_PLACER.get(),
                new ScorcherFeatureConfig(4, 7, 1));


        FeatureUtils.register(context, DUSKROCK_ORE, Feature.ORE, new OreConfiguration(
                List.of(
                        OreConfiguration.target(
                                new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES),
                                DDBlocks.DUSKROCK.get().defaultBlockState()
                        ),
                        OreConfiguration.target(
                                new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES),
                                DDBlocks.DUSKROCK.get().defaultBlockState()
                        ),
                        OreConfiguration.target(
                                new BlockMatchTest(DDBlocks.ARIDROCK.get()),
                                DDBlocks.DUSKROCK.get().defaultBlockState()
                        ),
                        OreConfiguration.target(
                                new BlockMatchTest(DDBlocks.ARID_DEEPSLATE.get()),
                                DDBlocks.DUSKROCK.get().defaultBlockState()
                        )
                ),
                64)
        );

        FeatureUtils.register(context, DARKSLATE_SURFACE, Feature.VEGETATION_PATCH,
                new VegetationPatchConfiguration(
                        BlockTags.AIR,
                        BlockStateProvider.simple(DDBlocks.DARKSLATE.get().defaultBlockState()),
                        PlacementUtils.inlinePlaced(lookup.getOrThrow(DARKSLATE_VEGETATION)),
                        CaveSurface.FLOOR,
                        ConstantInt.of(1),
                        0.0F,
                        5,
                        0.0F,
                        UniformInt.of(4, 7),
                        0.3F
                )
        );


        FeatureUtils.register(context, ARID_SURFACE, DDFeatures.CORRESPONDENT_LAYER.get(), new CorrespondentLayersConfig(
                DDTags.Blocks.ARID_GROUND,
                List.of(
                        BlockStateProvider.simple(DDBlocks.ARIDROCK.get()),
                        BlockStateProvider.simple(DDBlocks.ARID_DEEPSLATE.get())
                ),
                PlacementUtils.inlinePlaced(lookup.getOrThrow(ARID_VEGETATION)),
                CaveSurface.FLOOR,
                ConstantInt.of(1), 0.0F, 5, 0.05F,
                UniformInt.of(4, 7), 0.3F, true)
        );

        FeatureUtils.register(context, GRIME_SURFACE, DDFeatures.CORRESPONDENT_LAYER.get(), new CorrespondentLayersConfig(
                BlockTags.LUSH_GROUND_REPLACEABLE,
                List.of(
                        BlockStateProvider.simple(DDBlocks.MOSSY_GRIMESTONE.get()),
                        BlockStateProvider.simple(DDBlocks.GRIMESTONE.get()),
                        BlockStateProvider.simple(Blocks.TUFF)
                ),
                PlacementUtils.inlinePlaced(lookup.getOrThrow(GRIME_VEGETATION)),
                CaveSurface.FLOOR,
                ConstantInt.of(1), 0.0F, 5, 0.8F,
                UniformInt.of(4, 7), 0.3F, false)
        );

        FeatureUtils.register(context, GLIMMERING_VINES, Feature.BLOCK_COLUMN, new BlockColumnConfiguration(
                List.of(BlockColumnConfiguration.layer(
                                new WeightedListInt(SimpleWeightedRandomList.<IntProvider>builder()
                                        .add(UniformInt.of(0, 10), 2)
                                        .add(UniformInt.of(0, 2), 3)
                                        .add(UniformInt.of(0, 5), 10)
                                        .build()),
                                BlockStateProvider.simple(DDBlocks.GLIMMERING_VINE_PLANT.get().defaultBlockState())
                        ),
                        BlockColumnConfiguration.layer(ConstantInt.of(1),
                                BlockStateProvider.simple(DDBlocks.GLIMMERING_VINES.get().defaultBlockState()))
                ),
                Direction.DOWN,
                BlockPredicate.ONLY_IN_AIR_PREDICATE,
                true)
        );

        FeatureUtils.register(context, PETRIFIED_ROOTS, Feature.BLOCK_COLUMN, new BlockColumnConfiguration(
                List.of(BlockColumnConfiguration.layer(
                                new WeightedListInt(SimpleWeightedRandomList.<IntProvider>builder()
                                        .add(UniformInt.of(0, 0), 4)
                                        .add(UniformInt.of(0, 1), 2)
                                        .add(UniformInt.of(0, 2), 1)
                                        .build()),
                                BlockStateProvider.simple(DDBlocks.PETRIFIED_ROOTS_PLANT.get().defaultBlockState())
                        ),
                        BlockColumnConfiguration.layer(ConstantInt.of(1),
                                BlockStateProvider.simple(DDBlocks.PETRIFIED_ROOTS.get().defaultBlockState()))
                ),
                Direction.DOWN,
                BlockPredicate.ONLY_IN_AIR_PREDICATE,
                true)
        );
    }
}
