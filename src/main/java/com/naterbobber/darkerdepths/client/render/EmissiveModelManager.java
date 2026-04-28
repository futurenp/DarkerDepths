package com.naterbobber.darkerdepths.client.render;

import com.naterbobber.darkerdepths.block.DDBlockStateProperties;
import com.naterbobber.darkerdepths.init.DDBlocks;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockModelShaper;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModLoadingException;
import net.neoforged.fml.ModLoadingIssue;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.registries.DeferredBlock;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

public class EmissiveModelManager {

    @SubscribeEvent
    public static void modify(ModelEvent.ModifyBakingResult event) {
        var models = new ArrayList<BlockBaker>();

        models.add(new BlockBaker.Builder(event, DDBlocks.GEYSER)
                .modelSettings(new EmissiveBakedModel.ModelSettings().baseBrightness(LightTexture.pack(6, 0)))
                .predicate(state -> state.hasProperty(DDBlockStateProperties.BURSTING) && state.getValue(DDBlockStateProperties.BURSTING))
                .build());

        models.add(new BlockBaker.Builder(event, DDBlocks.DARKSLATE)
                .predicate(state -> state.hasProperty(DDBlockStateProperties.HEAT_LEVEL) && state.getValue(DDBlockStateProperties.HEAT_LEVEL) >= 2)
                .dynamicSettings(state -> {
                    var brightness = state.getValue(DDBlockStateProperties.HEAT_LEVEL) * 2 - 1;
                    return new EmissiveBakedModel.ModelSettings().baseBrightness(LightTexture.pack(brightness, 0));
                })
                .build());

        int glowshroomBrightness = LightTexture.pack(12, 0);
        int glowshroomBaseBrightness = LightTexture.pack(5, 0);

        models.add(new BlockBaker.Builder(event,
                DDBlocks.GLOWSHROOM_FENCE,
                DDBlocks.GLOWSHROOM_SLAB,
                DDBlocks.GLOWSHROOM_STAIRS,
                DDBlocks.GLOWSHROOM_FENCE_GATE,
                DDBlocks.GLOWSHROOM_PLANKS,
                DDBlocks.GLOWSHROOM_BLOCK,
                DDBlocks.GLOWSHROOM_BUTTON,
                DDBlocks.GLOWSHROOM_PRESSURE_PLATE,
                DDBlocks.STRIPPED_GLOWSHROOM_STEM,
                DDBlocks.STRIPPED_GLOWSHROOM_HYPHAE,
                DDBlocks.GLOWSHROOM_POST,
                DDBlocks.GLOWSHROOM_BOOKSHELF,
                DDBlocks.TRIMMED_PETRIFIED_PLANKS,
                DDBlocks.GLOWSHROOM_VERTICAL_SLAB,
                DDBlocks.VERTICAL_GLOWSHROOM_PLANKS
        )
                .modelSettings(new EmissiveBakedModel.ModelSettings())
                .build());

        models.add(new BlockBaker.Builder(event, DDBlocks.CRYSTAL_MELON)
                .modelSettings(new EmissiveBakedModel.ModelSettings()
                        .baseBrightness(LightTexture.pack(10, 0))
                        .removeShadeBase())
                .build());

        models.add(new BlockBaker.Builder(event,
                DDBlocks.GLIMMERING_VINE_PLANT,
                DDBlocks.GLIMMERING_VINES,
                DDBlocks.GLOWSHROOM
        )
                .modelSettings(new EmissiveBakedModel.ModelSettings()
                        .baseRenderType(RenderType.CUTOUT)
                        .glowRenderType(RenderType.CUTOUT)
                        .glowBrightness(glowshroomBrightness)
                        .baseBrightness(glowshroomBaseBrightness)
                        .manualModelGlow()
                        .removeShadeBase())
                .build());

        models.add(new BlockBaker.Builder(event,
                DDBlocks.GLOWSHROOM_STEM,
                DDBlocks.GLOWSHROOM_HYPHAE,
                DDBlocks.STRIPPED_GLOWSHROOM_POST
        )
                .modelSettings(new EmissiveBakedModel.ModelSettings()
                        .baseBrightness(LightTexture.pack(2, 0))
                        .glowBrightness(glowshroomBrightness))
                .build());

        models.add(new BlockBaker.Builder(event, DDBlocks.POTTED_GLOWSHROOM)
                .modelSettings(new EmissiveBakedModel.ModelSettings()
                        .baseRenderType(RenderType.CUTOUT)
                        .glowRenderType(RenderType.CUTOUT)
                        .glowBrightness(glowshroomBrightness)
                        .manualModelGlow())
                .build());

        models.add(new BlockBaker.Builder(event,
                DDBlocks.GLOWSHROOM_LANTERN,
                DDBlocks.GLOWSHROOM_LAMP
        )
                .modelSettings(new EmissiveBakedModel.ModelSettings()
                        .baseRenderType(RenderType.CUTOUT)
                        .glowRenderType(RenderType.CUTOUT)
                        .glowBrightness(LightTexture.FULL_BRIGHT)
                        .manualModelGlow())
                .build());

        models.add(new BlockBaker.Builder(event, DDBlocks.GLOWSHROOM_HEART)
                .modelSettings(new EmissiveBakedModel.ModelSettings()
                        .glowBrightness(LightTexture.FULL_BRIGHT)
                        .baseBrightness(glowshroomBaseBrightness))
                .build());

        models.add(new BlockBaker.Builder(event, DDBlocks.MOSSY_GRIMESTONE)
                .modelSettings(new EmissiveBakedModel.ModelSettings()
                        .glowBrightness(LightTexture.pack(2, 0)))
                .build());

        models.add(new BlockBaker.Builder(event, DDBlocks.MOSSY_SPROUTS)
                .modelSettings(new EmissiveBakedModel.ModelSettings()
                        .baseRenderType(RenderType.CUTOUT)
                        .baseBrightness(LightTexture.pack(2, 0))
                        .removeShadeBase()
                        .manualModelGlow())
                .build());

        models.add(new BlockBaker.Builder(event, DDBlocks.AMBER_BLOCK)
                .modelSettings(new EmissiveBakedModel.ModelSettings())
                .build());

        models.forEach(BlockBaker::apply);
        clearHolders();
    }

    public static void clearHolders() {
        EmissiveModelManager.BlockBaker.clearHolders();
    }

    private static class BlockBaker {
        private final List<DeferredBlock<? extends Block>> blockHolders;
        private final ModelEvent.ModifyBakingResult event;
        private final Predicate<BlockState> predicate;
        private final Function<BlockState, EmissiveBakedModel.ModelSettings> settingsProvider;
        private static final List<DeferredBlock<? extends Block>> allHolders = new ArrayList<>();

        public BlockBaker (BlockBaker.Builder builder) {
            blockHolders = builder.blockHolders;
            event = builder.event;
            settingsProvider = builder.settingsProvider;
            predicate = builder.predicate;
        }

        public void apply() {
            checkDuplicates();

            for (DeferredBlock<? extends Block> holder : blockHolders) {
                for (BlockState state : holder.get().getStateDefinition().getPossibleStates()) {
                    if(!predicate.test(state)) {
                        continue;
                    }

                    ModelResourceLocation targetModel = BlockModelShaper.stateToModelLocation(state);
                    var originalModel = event.getModels().get(targetModel);

                    if (originalModel != null) {
                        var emissiveModel = new EmissiveBakedModel(originalModel, settingsProvider.apply(state));
                        event.getModels().put(targetModel, emissiveModel);
                    }
                }
            }
        }

        private static void checkDuplicates() {
            var set = new HashSet<>(allHolders);
            if(set.size() != allHolders.size()) {
                List<String> duplicateNames = allHolders.stream()
                        .filter(holder -> Collections.frequency(allHolders, holder) > 1)
                        .map(holder -> holder.getId().toString())
                        .distinct()
                        .toList();

                throw new ModLoadingException(ModLoadingIssue.error(
            "Duplicate entries found in model baker!" + "\n\t\t" + String.join("\n\t\t", duplicateNames), duplicateNames));
            }
        }

        private static void addHolders(List<? extends DeferredBlock<? extends Block>> holders) {
            allHolders.addAll(holders);
        }

        private static void clearHolders() {
            allHolders.clear();
        }

        static class Builder {
            private final ModelEvent.ModifyBakingResult event;
            private final List<DeferredBlock<? extends Block>> blockHolders;
            private Predicate<BlockState> predicate = state -> true;

            private Function<BlockState, EmissiveBakedModel.ModelSettings> settingsProvider =
                    state -> new EmissiveBakedModel.ModelSettings();

            @SafeVarargs
            public Builder(ModelEvent.ModifyBakingResult event, DeferredBlock<? extends Block>... blockHolders) {
                this.event = event;
                this.blockHolders = Arrays.asList(blockHolders);
                BlockBaker.addHolders(this.blockHolders);
            }

            public Builder modelSettings(EmissiveBakedModel.ModelSettings modelSettings) {
                this.settingsProvider = state -> modelSettings;
                return this;
            }

            public Builder dynamicSettings(Function<BlockState, EmissiveBakedModel.ModelSettings> settingsProvider) {
                this.settingsProvider = settingsProvider;
                return this;
            }

            public Builder predicate(Predicate<BlockState> predicate) {
                this.predicate = predicate;
                return this;
            }

            public BlockBaker build() {
                return new BlockBaker(this);
            }
        }
    }
}
