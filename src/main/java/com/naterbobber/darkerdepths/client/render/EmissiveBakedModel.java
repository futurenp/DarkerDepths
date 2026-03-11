package com.naterbobber.darkerdepths.client.render;

import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.ChunkRenderTypeSet;
import net.neoforged.neoforge.client.model.BakedModelWrapper;
import net.neoforged.neoforge.client.model.data.ModelData;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class EmissiveBakedModel extends BakedModelWrapper<BakedModel> {

    public EmissiveBakedModel(BakedModel originalModel) {
        super(originalModel);
    }

    @Override
    public @NotNull ChunkRenderTypeSet getRenderTypes(@NotNull BlockState state, @NotNull RandomSource rand, @NotNull ModelData data) {
        return ChunkRenderTypeSet.of(RenderType.solid(), RenderType.translucent());
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, RandomSource rand, ModelData data, @Nullable RenderType renderType) {
        List<BakedQuad> originalQuads = super.getQuads(state, side, rand, data, null);
        List<BakedQuad> newQuads = new ArrayList<>();

        for (BakedQuad quad : originalQuads) {
            boolean isGlow = quad.getSprite().contents().name().getPath().contains("glow");

            if (renderType == null) {
                newQuads.add(isGlow ? makeFullyBright(quad) : quad);
            }
            else if (renderType == RenderType.solid() && !isGlow) {
                newQuads.add(quad);
            }
            else if (renderType == RenderType.translucent() && isGlow) {
                newQuads.add(makeFullyBright(quad));
            }
        }
        return newQuads;
    }

    private BakedQuad makeFullyBright(BakedQuad quad) {
        int[] vertexData = quad.getVertices().clone();
        for (int i = 0; i < 4; i++) {
            int vertexStartIndex = i * 8;
            vertexData[vertexStartIndex + 6] = LightTexture.FULL_BRIGHT;
        }
        return new BakedQuad(vertexData, quad.getTintIndex(), quad.getDirection(), quad.getSprite(), false);
    }
}