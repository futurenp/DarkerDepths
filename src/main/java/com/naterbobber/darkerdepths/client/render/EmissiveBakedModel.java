package com.naterbobber.darkerdepths.client.render;

import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.ChunkRenderTypeSet;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.client.model.BakedModelWrapper;
import net.neoforged.neoforge.client.model.data.ModelData;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class EmissiveBakedModel extends BakedModelWrapper<BakedModel> {

    private final RenderType baseRenderType;
    private final RenderType glowRenderType;
    private final int baseBrightness;
    private final int glowBrightness;

    public EmissiveBakedModel(BakedModel originalModel, RenderType baseRenderType, RenderType glowRenderType, int baseBrightness, int glowBrightness) {
        super(originalModel);
        this.baseRenderType = baseRenderType;
        this.glowRenderType = glowRenderType;
        this.baseBrightness = baseBrightness;
        this.glowBrightness = glowBrightness;
    }

    @Override
    public @NotNull ChunkRenderTypeSet getRenderTypes(@NotNull BlockState state, @NotNull RandomSource rand, @NotNull ModelData data) {
        return ChunkRenderTypeSet.of(this.baseRenderType, this.glowRenderType);
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, RandomSource rand, ModelData data, @Nullable RenderType renderType) {
        List<BakedQuad> originalQuads = super.getQuads(state, side, rand, data, null);
        List<BakedQuad> newQuads = new ArrayList<>();

        for (BakedQuad quad : originalQuads) {
            boolean isGlow = quad.getSprite().contents().name().getPath().endsWith("_glow");

            if (renderType == null) {
                if (isGlow) {
                    newQuads.add(changeBrightness(quad, glowBrightness));
                } else {
                    newQuads.add(baseBrightness == 0 ? quad : changeBrightness(quad, baseBrightness));
                }
            }
            else if (!isGlow && renderType == this.baseRenderType) {
                newQuads.add(baseBrightness == 0 ? quad : changeBrightness(quad, baseBrightness));
            }
            else if (isGlow && renderType == this.glowRenderType) {
                newQuads.add(changeBrightness(quad, glowBrightness));
            }
        }
        return newQuads;
    }

    private BakedQuad changeBrightness(BakedQuad quad, int brightness) {
        int[] vertexData = quad.getVertices().clone();
        for (int i = 0; i < 4; i++) {
            int vertexStartIndex = i * 8;
            vertexData[vertexStartIndex + 6] = brightness;
        }
        return new BakedQuad(vertexData, quad.getTintIndex(), quad.getDirection(), quad.getSprite(), false);
    }

    public static class Builder {
        BakedModel originalModel;
        RenderType baseRenderType = RenderType.SOLID;
        RenderType glowRenderType = RenderType.TRANSLUCENT;
        int baseBrightness = 0;
        int glowBrightness = LightTexture.FULL_BLOCK;

        public Builder(BakedModel originalModel){
            this.originalModel = originalModel;
        }

        public Builder(){}

        public Builder originalModel(BakedModel originalModel){
            this.originalModel = originalModel;
            return this;
        }

        public Builder baseRenderType(RenderType baseRenderType) {
            this.baseRenderType = baseRenderType;
            return this;
        }

        public Builder glowRenderType(RenderType glowRenderType) {
            this.glowRenderType = glowRenderType;
            return this;
        }

        public Builder baseBrightness(int baseBrightness) {
            this.baseBrightness = baseBrightness;
            return this;
        }

        public Builder glowBrightness(int glowBrightness) {
            this.glowBrightness = glowBrightness;
            return this;
        }

        public EmissiveBakedModel build(){
            return new EmissiveBakedModel(originalModel, baseRenderType, glowRenderType, baseBrightness, glowBrightness);
        }
    }
}