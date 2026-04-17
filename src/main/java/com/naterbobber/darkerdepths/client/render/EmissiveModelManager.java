package com.naterbobber.darkerdepths.client.render;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.block.DDBlockStateProperties;
import com.naterbobber.darkerdepths.init.DDBlocks;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockModelShaper;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.registries.DeferredBlock;

public class EmissiveModelManager {

    @SubscribeEvent
    public static void modify(ModelEvent.ModifyBakingResult event) {
        for (BlockState state : DDBlocks.DARKSLATE.get().getStateDefinition().getPossibleStates()) {
            if (state.hasProperty(DDBlockStateProperties.HEAT_LEVEL) && state.getValue(DDBlockStateProperties.HEAT_LEVEL) >= 2) {
                ModelResourceLocation targetModel = BlockModelShaper.stateToModelLocation(state);
                var originalModel = event.getModels().get(targetModel);
                if (originalModel != null) {
                    var emissiveModel = new EmissiveBakedModel.Builder(originalModel).build();
                    event.getModels().put(targetModel, emissiveModel);
                }
            }
        }

        for (BlockState state : DDBlocks.GEYSER.get().getStateDefinition().getPossibleStates()) {
            if (state.hasProperty(DDBlockStateProperties.BURSTING) && state.getValue(DDBlockStateProperties.BURSTING)) {
                ModelResourceLocation targetModel = BlockModelShaper.stateToModelLocation(state);
                var originalModel = event.getModels().get(targetModel);
                if (originalModel != null) {
                    var emissiveModel = new EmissiveBakedModel.Builder(originalModel).build();
                    event.getModels().put(targetModel, emissiveModel);
                }
            }
        }

        int glowshroomBrightness = LightTexture.pack(12, 0);
        int glowshroomBaseBrightness = LightTexture.pack(5, 0);

        applyToAllStates(event, new EmissiveBakedModel.Builder(),
                DDBlocks.GLOWSHROOM_PLANKS,
                DDBlocks.GLOWSHROOM_BLOCK
        );

        applyToAllStates(event, new EmissiveBakedModel.Builder()
                        .baseRenderType(RenderType.CUTOUT)
                        .glowRenderType(RenderType.CUTOUT)
                        .glowBrightness(glowshroomBrightness)
                        .baseBrightness(glowshroomBaseBrightness),
                DDBlocks.GLIMMERING_VINE_PLANT,
                DDBlocks.GLIMMERING_VINES,
                DDBlocks.GLOWSHROOM
        );

        applyToAllStates(event, new EmissiveBakedModel.Builder()
                        .baseBrightness(LightTexture.pack(2, 0)),
                DDBlocks.GLOWSHROOM_STEM
        );

        applyToAllStates(event, new EmissiveBakedModel.Builder()
                        .baseRenderType(RenderType.CUTOUT)
                        .glowRenderType(RenderType.CUTOUT)
                        .glowBrightness(glowshroomBrightness),
                DDBlocks.POTTED_GLOWSHROOM
        );

        applyToAllStates(event, new EmissiveBakedModel.Builder()
                        .baseRenderType(RenderType.CUTOUT)
                        .glowRenderType(RenderType.CUTOUT)
                        .glowBrightness(LightTexture.FULL_BRIGHT),
                DDBlocks.GLOWSHROOM_LANTERN,
                DDBlocks.GLOWSHROOM_LAMP
        );

        applyToAllStates(event, new EmissiveBakedModel.Builder()
                        .glowBrightness(LightTexture.FULL_BRIGHT)
                        .baseBrightness(glowshroomBaseBrightness),
                DDBlocks.GLOWSHROOM_HEART
        );

        applyToAllStates(event, new EmissiveBakedModel.Builder()
                        .glowRenderType(RenderType.CUTOUT)
                        .glowBrightness(LightTexture.pack(2, 0)),
                DDBlocks.MOSSY_GRIMESTONE
        );

        applyToAllStates(event, new EmissiveBakedModel.Builder()
                        .baseRenderType(RenderType.CUTOUT)
                        .baseBrightness(LightTexture.pack(2, 0)),
                DDBlocks.MOSSY_SPROUTS
        );
    }

    @SafeVarargs
    private static void applyToAllStates(ModelEvent.ModifyBakingResult event, EmissiveBakedModel.Builder modelBuilder, DeferredBlock<? extends Block>... blockHolders) {
        for (DeferredBlock<? extends Block> holder : blockHolders) {
            for (BlockState state : holder.get().getStateDefinition().getPossibleStates()) {
                ModelResourceLocation targetModel = BlockModelShaper.stateToModelLocation(state);
                var originalModel = event.getModels().get(targetModel);
                if (originalModel != null) {
                    var emissiveModel = modelBuilder.originalModel(originalModel).build();
                    event.getModels().put(targetModel, emissiveModel);
                }
            }
        }
    }
}
