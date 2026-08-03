package com.naterbobber.darkerdepths.client.render;

import com.naterbobber.darkerdepths.block.DDBlockStateProperties;
import com.naterbobber.darkerdepths.init.DDBlocks;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockModelShaper;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.fml.ModLoadingException;
import net.neoforged.fml.ModLoadingIssue;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.registries.DeferredBlock;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

@OnlyIn(Dist.CLIENT)
public class BakedModelManager {

    public static void modify(ModelEvent.ModifyBakingResult event) {
        var models = new ArrayList<BlockBaker>();

        models.add(BlockBaker.builder(event, DDBlocks.SHELF_GLOWSHROOM, DDBlocks.DEAD_SHELF_GLOWSHROOM)
                .dynamicSettings(blockState -> {
                    if(blockState.getValue(DDBlockStateProperties.LARGE)) {
                        return DirectionalOffsetBakedModel.Settings.of(DirectionalOffsetBakedModel.OffsetType.Y)
                                .maxShiftY(0.15F);
                    }
                    return DirectionalOffsetBakedModel.Settings.of(DirectionalOffsetBakedModel.OffsetType.XYZ);
                })
                .build());

        models.add(BlockBaker.builder(event, DDBlocks.GEYSER)
                .modelSettings(EmissiveBakedModel.settings().baseBrightness(LightTexture.pack(6, 0)))
                .predicate(state -> state.hasProperty(DDBlockStateProperties.BURSTING) && state.getValue(DDBlockStateProperties.BURSTING))
                .build());

        models.add(BlockBaker.builder(event,
                        DDBlocks.DARKSLATE,
                        DDBlocks.DARKSLATE_SLAB,
                        DDBlocks.DARKSLATE_VERTICAL_SLAB,
                        DDBlocks.DARKSLATE_STAIRS,
                        DDBlocks.DARKSLATE_WALL,
                        DDBlocks.POLISHED_DARKSLATE,
                        DDBlocks.POLISHED_DARKSLATE_STAIRS,
                        DDBlocks.POLISHED_DARKSLATE_SLAB,
                        DDBlocks.POLISHED_DARKSLATE_VERTICAL_SLAB,
                        DDBlocks.DARKSLATE_BRICKS,
                        DDBlocks.DARKSLATE_BRICKS_STAIRS,
                        DDBlocks.DARKSLATE_BRICKS_SLAB,
                        DDBlocks.DARKSLATE_BRICKS_VERTICAL_SLAB,
                        DDBlocks.DARKSLATE_BRICKS_WALL,
                        DDBlocks.CHISELED_DARKSLATE_BRICKS,
                        DDBlocks.CRACKED_DARKSLATE_BRICKS,
                        DDBlocks.DARKSLATE_PILLAR
                )
                .predicate(state -> state.hasProperty(DDBlockStateProperties.HEAT_LEVEL) && state.getValue(DDBlockStateProperties.HEAT_LEVEL) >= 2)
                .dynamicSettings(state -> {
                    var brightness = state.getValue(DDBlockStateProperties.HEAT_LEVEL) * 2 - 1;
                    return EmissiveBakedModel.settings().baseBrightness(LightTexture.pack(brightness, 0));
                })
                .build());

        models.add(BlockBaker.builder(event, DDBlocks.STRING_LIGHTS)
                .modelSettings(EmissiveBakedModel.settings()
                        .baseBrightness(LightTexture.pack(15, 0))
                        .baseRenderType(RenderType.CUTOUT)
                        .removeShadeBase())
                .build());

        var glowshroomBrightness = LightTexture.pack(12, 0);
        var glowshroomBaseBrightness = LightTexture.pack(5, 0);

        models.add(BlockBaker.builder(event,
                        DDBlocks.GLOWSHROOM_FENCE,
                        DDBlocks.GLOWSHROOM_SLAB,
                        DDBlocks.GLOWSHROOM_STAIRS,
                        DDBlocks.GLOWSHROOM_FENCE_GATE,
                        DDBlocks.GLOWSHROOM_PLANKS,
                        DDBlocks.GLOWSHROOM_BUTTON,
                        DDBlocks.GLOWSHROOM_PRESSURE_PLATE,
                        DDBlocks.STRIPPED_GLOWSHROOM_STEM,
                        DDBlocks.STRIPPED_GLOWSHROOM_HYPHAE,
                        DDBlocks.GLOWSHROOM_POST,
                        DDBlocks.GLOWSHROOM_BOOKSHELF,
                        DDBlocks.TRIMMED_PETRIFIED_PLANKS,
                        DDBlocks.GLOWSHROOM_VERTICAL_SLAB,
                        DDBlocks.VERTICAL_GLOWSHROOM_PLANKS,
                        DDBlocks.GLOWSHROOM_TRAPDOOR,
                        DDBlocks.GLOWSHROOM_DOOR
                )
                .modelSettings(EmissiveBakedModel.settings())
                .build());

        models.add(BlockBaker.builder(event,
                        DDBlocks.GLOWSHROOM,
                        DDBlocks.GULBS
                )
                .modelSettings(EmissiveBakedModel.settings()
                        .baseRenderType(RenderType.CUTOUT)
                        .glowRenderType(RenderType.CUTOUT)
                        .glowBrightness(glowshroomBrightness)
                        .baseBrightness(glowshroomBaseBrightness)
                        .removeShadeBase())
                .build());

        models.add(BlockBaker.builder(event,
                        DDBlocks.GLIMMERING_VINE_PLANT,
                        DDBlocks.GLIMMERING_VINES
                )
                .modelSettings(EmissiveBakedModel.settings()
                        .removeShadeBase()
                        .baseRenderType(RenderType.CUTOUT)
                        .baseBrightness(glowshroomBrightness))
                .build());

        models.add(BlockBaker.builder(event,
                        DDBlocks.SHELF_GLOWSHROOM
                )
                .modelSettings(EmissiveBakedModel.settings()
                        .baseBrightness(glowshroomBrightness)
                        .baseRenderType(RenderType.CUTOUT)
                        .removeShadeBase())
                .build());

        models.add(BlockBaker.builder(event,
                        DDBlocks.GLOWSHROOM_PILEUS
                )
                .modelSettings(EmissiveBakedModel.settings()
                        .baseBrightness(glowshroomBrightness))
                .build());

        models.add(BlockBaker.builder(event,
                        DDBlocks.GLOWSHROOM_STEM,
                        DDBlocks.GLOWSHROOM_HYPHAE,
                        DDBlocks.STRIPPED_GLOWSHROOM_POST
                )
                .modelSettings(EmissiveBakedModel.settings()
                        .baseBrightness(LightTexture.pack(2, 0))
                        .glowBrightness(glowshroomBrightness))
                .build());

        models.add(BlockBaker.builder(event, DDBlocks.POTTED_GLOWSHROOM)
                .modelSettings(EmissiveBakedModel.settings()
                        .baseRenderType(RenderType.CUTOUT)
                        .glowRenderType(RenderType.CUTOUT)
                        .glowBrightness(glowshroomBrightness)
                        .manualModelGlow())
                .build());

        models.add(BlockBaker.builder(event,
                        DDBlocks.GLOWSHROOM_LANTERN,
                        DDBlocks.GLOWSHROOM_LAMP
                )
                .modelSettings(EmissiveBakedModel.settings()
                        .baseRenderType(RenderType.CUTOUT)
                        .glowRenderType(RenderType.CUTOUT)
                        .glowBrightness(LightTexture.FULL_BRIGHT))
                .build());

        models.add(BlockBaker.builder(event, DDBlocks.GLOWSHROOM_HEART)
                .modelSettings(EmissiveBakedModel.settings()
                        .glowBrightness(LightTexture.FULL_BRIGHT)
                        .baseBrightness(glowshroomBaseBrightness))
                .build());

        models.add(BlockBaker.builder(event,
                        DDBlocks.MOSSY_GRIMESTONE,
                        DDBlocks.MOSSY_GRIMESTONE_BRICKS,
                        DDBlocks.MOSSY_GRIMESTONE_BRICKS_SLAB,
                        DDBlocks.MOSSY_GRIMESTONE_BRICKS_STAIRS,
                        DDBlocks.MOSSY_GRIMESTONE_BRICKS_VERTICAL_SLAB,
                        DDBlocks.MOSSY_GRIMESTONE_BRICKS_WALL
                )
                .modelSettings(EmissiveBakedModel.settings()
                        .glowBrightness(LightTexture.pack(3, 0))
                        .shadeGlow())
                .build());

        models.add(BlockBaker.builder(event,
                        DDBlocks.MOSSY_SPROUTS
                )
                .modelSettings(EmissiveBakedModel.settings()
                        .baseRenderType(RenderType.CUTOUT)
                        .baseBrightness(LightTexture.pack(2, 0))
                        .removeShadeBase()
                        .manualModelGlow())
                .build());

        models.add(BlockBaker.builder(event,
                        DDBlocks.GLOWSPURS
                )
                .modelSettings(EmissiveBakedModel.settings()
                        .baseRenderType(RenderType.CUTOUT)
                        .baseBrightness(LightTexture.pack(5, 0))
                        .removeShadeBase()
                        .manualModelGlow())
                .build());

        models.add(BlockBaker.builder(event, DDBlocks.AMBER_BLOCK)
                .modelSettings(EmissiveBakedModel.settings())
                .build());

        models.forEach(BlockBaker::apply);
        clearHolders();
    }

    public static void clearHolders() {
        BakedModelManager.BlockBaker.clearHolders();
    }

    private static class BlockBaker {
        private final List<DeferredBlock<? extends Block>> blockHolders;
        private final ModelEvent.ModifyBakingResult event;
        private final Predicate<BlockState> predicate;
        private final Function<BlockState, BakedModelSettings> settingsProvider;

        private static final Map<DeferredBlock<? extends Block>, List<Class<? extends BakedModelSettings>>> registeredModels = new HashMap<>();

        public BlockBaker (BlockBaker.Builder builder) {
            blockHolders = builder.blockHolders;
            event = builder.event;
            settingsProvider = builder.settingsProvider;
            predicate = builder.predicate;
        }

        public void apply() {
            checkDuplicates();

            for (var holder : blockHolders) {
                for (var state : holder.get().getStateDefinition().getPossibleStates()) {
                    if(!predicate.test(state)) {
                        continue;
                    }

                    var targetModel = BlockModelShaper.stateToModelLocation(state);
                    var originalModel = event.getModels().get(targetModel);

                    if (originalModel != null) {
                        var settings = settingsProvider.apply(state);
                        var model = settings.model(originalModel);

                        event.getModels().put(targetModel, model);
                    }
                }
            }
        }

        private static void checkDuplicates() {
            var duplicateNames = registeredModels.entrySet().stream()
                    .filter(entry -> {
                        var list = entry.getValue();
                        var set = new HashSet<>(list);
                        return set.size() != list.size();
                    })
                    .map(entry -> entry.getKey().getId().toString())
                    .distinct()
                    .toList();

            if(!duplicateNames.isEmpty()) {
                throw new ModLoadingException(ModLoadingIssue.error(
                        "Duplicate entries found in model baker!\n\t\t" + String.join("\n\t\t", duplicateNames), duplicateNames));
            }
        }

        private static void addHolder(DeferredBlock<? extends Block> holder, Class<? extends BakedModelSettings> settingsClass) {
            registeredModels.computeIfAbsent(holder, k -> new ArrayList<>()).add(settingsClass);
        }

        private static void clearHolders() {
            registeredModels.clear();
        }

        @SafeVarargs
        public static BlockBaker.Builder builder(ModelEvent.ModifyBakingResult event, DeferredBlock<? extends Block>... blockHolders) {
            return new BlockBaker.Builder(event, blockHolders);
        }

        public static BlockBaker.Builder builder(ModelEvent.ModifyBakingResult event, List<DeferredBlock<? extends Block>> blockHoldersList) {
            return new BlockBaker.Builder(event, blockHoldersList.toArray(new DeferredBlock[blockHoldersList.size()]));
        }

        static class Builder {
            private final ModelEvent.ModifyBakingResult event;
            private final List<DeferredBlock<? extends Block>> blockHolders;
            private Predicate<BlockState> predicate = state -> true;

            private Function<BlockState, BakedModelSettings> settingsProvider =
                    state -> EmissiveBakedModel.settings();

            @SafeVarargs
            public Builder(ModelEvent.ModifyBakingResult event, DeferredBlock<? extends Block>... blockHolders) {
                this.event = event;
                this.blockHolders = Arrays.asList(blockHolders);
            }

            public Builder modelSettings(EmissiveBakedModel.Settings modelSettings) {
                this.settingsProvider = state -> modelSettings;
                return this;
            }

            public Builder dynamicSettings(Function<BlockState, BakedModelSettings> settingsProvider) {
                this.settingsProvider = settingsProvider;
                return this;
            }

            public Builder predicate(Predicate<BlockState> predicate) {
                this.predicate = predicate;
                return this;
            }

            public BlockBaker build() {
                for (var holder : blockHolders) {
                    var settings = settingsProvider.apply(holder.get().defaultBlockState());
                    BlockBaker.addHolder(holder, settings.getClass());
                }
                return new BlockBaker(this);
            }
        }
    }
}