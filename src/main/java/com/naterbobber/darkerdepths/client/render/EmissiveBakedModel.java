package com.naterbobber.darkerdepths.client.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
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

@OnlyIn(Dist.CLIENT)
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

        var blockAtlas = Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS);
        var missingSprite = blockAtlas.apply(MissingTextureAtlasSprite.getLocation());

        for (BakedQuad quad : originalQuads) {
            boolean isManualGlowQuad = quad.getSprite().contents().name().getPath().endsWith("_glow");

            if (isManualGlowQuad) {
                if (renderType == null || renderType == settings.glowRenderType) {
                    newQuads.add(changeBrightness(quad, settings.glowBrightness, settings.shadeGlow, !settings.disableAmbientOcclusion));
                }
            } else {
                if (renderType == null || renderType == settings.baseRenderType) {
                    newQuads.add(settings.baseBrightness == 0 ? quad : changeBrightness(quad, settings.baseBrightness, !settings.removeShadeBase, !settings.disableAmbientOcclusion));
                }

                if (!settings.manualModelGlow && (renderType == null || renderType == settings.glowRenderType)) {
                    ResourceLocation baseLoc = quad.getSprite().contents().name();
                    ResourceLocation glowLoc = ResourceLocation.fromNamespaceAndPath(baseLoc.getNamespace(), baseLoc.getPath() + "_glow");

                    TextureAtlasSprite glowSprite = blockAtlas.apply(glowLoc);

                    if (glowSprite != null && glowSprite != missingSprite) {
                        newQuads.add(createGlowQuad(quad, glowSprite, settings.glowBrightness, settings.shadeGlow));
                    }
                }
            }
        }
        return newQuads;
    }

    private BakedQuad createGlowQuad(BakedQuad baseQuad, TextureAtlasSprite glowSprite, int brightness, boolean shade) {
        int[] originalData = baseQuad.getVertices();
        int[] newData = originalData.clone();
        TextureAtlasSprite baseSprite = baseQuad.getSprite();

        for (int i = 0; i < 4; i++) {
            int vertexIndex = i * 8;

            float oldU = Float.intBitsToFloat(originalData[vertexIndex + 4]);
            float oldV = Float.intBitsToFloat(originalData[vertexIndex + 5]);

            float relU = (oldU - baseSprite.getU0()) / (baseSprite.getU1() - baseSprite.getU0());
            float relV = (oldV - baseSprite.getV0()) / (baseSprite.getV1() - baseSprite.getV0());

            float newU = glowSprite.getU0() + relU * (glowSprite.getU1() - glowSprite.getU0());
            float newV = glowSprite.getV0() + relV * (glowSprite.getV1() - glowSprite.getV0());

            newData[vertexIndex + 4] = Float.floatToRawIntBits(newU);
            newData[vertexIndex + 5] = Float.floatToRawIntBits(newV);

            newData[vertexIndex + 6] = brightness;
        }

        return new BakedQuad(newData, baseQuad.getTintIndex(), baseQuad.getDirection(), glowSprite, shade);
    }

    private BakedQuad changeBrightness(BakedQuad quad, int brightness, boolean shade, boolean ambientOcclusion) {
        int[] vertexData = quad.getVertices().clone();
        for (int i = 0; i < 4; i++) {
            int vertexStartIndex = i * 8;
            vertexData[vertexStartIndex + 6] = brightness;
        }
        return new BakedQuad(vertexData, quad.getTintIndex(), quad.getDirection(), quad.getSprite(), shade, ambientOcclusion);
    }

    public static ModelSettings modelSettings() {
        return new ModelSettings();
    }

    public static class ModelSettings {
        RenderType baseRenderType = RenderType.SOLID;
        RenderType glowRenderType = RenderType.TRANSLUCENT;
        int baseBrightness = 0;
        int glowBrightness = LightTexture.FULL_BLOCK;
        boolean removeShadeBase = false;
        boolean shadeGlow = false;
        boolean manualModelGlow = false;
        boolean disableAmbientOcclusion = false;

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

        public ModelSettings manualModelGlow() {
            this.manualModelGlow = true;
            return this;
        }

        public ModelSettings disableAmbientOcclusion() {
            this.disableAmbientOcclusion = true;
            return this;
        }
    }
}