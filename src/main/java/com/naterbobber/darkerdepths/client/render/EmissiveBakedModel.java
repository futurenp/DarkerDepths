package com.naterbobber.darkerdepths.client.render;

import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.ChunkRenderTypeSet;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.client.model.BakedModelWrapper;
import net.neoforged.neoforge.client.model.data.ModelData;
import net.neoforged.neoforge.registries.DeferredBlock;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EmissiveBakedModel extends BakedModelWrapper<BakedModel> {

    private final ModelSettings settings;

    public EmissiveBakedModel(BakedModel originalModel, ModelSettings settings) {
        super(originalModel);
        this.settings = settings;
    }

    @Override
    public @NotNull ChunkRenderTypeSet getRenderTypes(@NotNull BlockState state, @NotNull RandomSource rand, @NotNull ModelData data) {
        return ChunkRenderTypeSet.of(settings.baseRenderType, settings.glowRenderType);
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, RandomSource rand, ModelData data, @Nullable RenderType renderType) {
        List<BakedQuad> originalQuads = super.getQuads(state, side, rand, data, null);
        List<BakedQuad> newQuads = new ArrayList<>();

        for (BakedQuad quad : originalQuads) {
            boolean isGlow = quad.getSprite().contents().name().getPath().endsWith("_glow");

            if (renderType == null) {
                if (isGlow) {
                    newQuads.add(changeBrightness(quad, settings.glowBrightness, settings.shadeGlow));
                } else {
                    newQuads.add(settings.baseBrightness == 0 ? quad : changeBrightness(quad, settings.baseBrightness, !settings.removeShadeBase));
                }
            }
            else if (!isGlow && renderType == settings.baseRenderType) {
                newQuads.add(settings.baseBrightness == 0 ? quad : changeBrightness(quad, settings.baseBrightness, !settings.removeShadeBase));
            }
            else if (isGlow && renderType == settings.glowRenderType) {
                newQuads.add(changeBrightness(quad, settings.glowBrightness, settings.shadeGlow));
            }
        }
        return newQuads;
    }

    private BakedQuad changeBrightness(BakedQuad quad, int brightness, boolean shade) {
        int[] vertexData = quad.getVertices().clone();
        for (int i = 0; i < 4; i++) {
            int vertexStartIndex = i * 8;
            vertexData[vertexStartIndex + 6] = brightness;
        }
        return new BakedQuad(vertexData, quad.getTintIndex(), quad.getDirection(), quad.getSprite(), shade);
    }

    public static class ModelSettings {
        RenderType baseRenderType = RenderType.SOLID;
        RenderType glowRenderType = RenderType.TRANSLUCENT;
        int baseBrightness = 0;
        int glowBrightness = LightTexture.FULL_BLOCK;
        boolean removeShadeBase = false;
        boolean shadeGlow = false;

        public ModelSettings(){}

        public ModelSettings baseRenderType(RenderType baseRenderType) {
            this.baseRenderType = baseRenderType;
            return this;
        }

        public ModelSettings glowRenderType(RenderType glowRenderType) {
            this.glowRenderType = glowRenderType;
            return this;
        }

        public ModelSettings baseBrightness(int baseBrightness) {
            this.baseBrightness = baseBrightness;
            return this;
        }

        public ModelSettings glowBrightness(int glowBrightness) {
            this.glowBrightness = glowBrightness;
            return this;
        }

        public ModelSettings removeShadeBase() {
            this.removeShadeBase = true;
            return this;
        }

        public ModelSettings shadeGlow() {
            this.shadeGlow = true;
            return this;
        }
    }
}