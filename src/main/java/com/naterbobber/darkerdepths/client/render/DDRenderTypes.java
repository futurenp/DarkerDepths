package com.naterbobber.darkerdepths.client.render;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.client.DDRenderStateShards;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.event.RegisterNamedRenderTypesEvent;

public class DDRenderTypes extends RenderType {

    public DDRenderTypes(String pName, VertexFormat pFormat, VertexFormat.Mode pMode, int pBufferSize, boolean pAffectsCrumbling, boolean pSortOnUpload, Runnable pSetupState, Runnable pClearState) {
        super(pName, pFormat, pMode, pBufferSize, pAffectsCrumbling, pSortOnUpload, pSetupState, pClearState);
    }

    public static void registerRenderTypes(RegisterNamedRenderTypesEvent event) {
    }

    public static void register(RegisterNamedRenderTypesEvent event, RenderType renderType){
        event.register(DarkerDepths.id(renderType.name), renderType, renderType);
    }

    public static RenderType INVERTED_CUBE (ResourceLocation textureLocation) {
        CompositeState state = CompositeState.builder()
                .setShaderState(RENDERTYPE_ENTITY_CUTOUT_SHADER)
                .setTextureState(new TextureStateShard(textureLocation, false, false))
                .createCompositeState(false);

        return create("inverted_cube", state);
    }

    public static RenderType EMISSIVE_SOLID (ResourceLocation textureLocation) {
        CompositeState state = CompositeState.builder()
                .setShaderState(RENDERTYPE_EYES_SHADER)
                .setTextureState(new TextureStateShard(textureLocation, false, false))
                .createCompositeState(false);

        return create("emissive_solid", state);
    }

    public static RenderType EMISSIVE_TRANSPARENT (ResourceLocation textureLocation) {
        CompositeState state = CompositeState.builder()
                .setShaderState(RENDERTYPE_EYES_SHADER)
                .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
                .setTextureState(new TextureStateShard(textureLocation, false, false))
                .createCompositeState(false);

        return create("emissive_transparent", state);
    }

    public static RenderType EMISSIVE_TRANSPARENT_FOG_OVERRIDE(ResourceLocation textureLocation) {
        CompositeState state = CompositeState.builder()
                .setShaderState(DDRenderStateShards.GLOW_THROUGH_FOG)
                .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
                .setCullState(NO_CULL)
                .setLayeringState(RenderStateShard.VIEW_OFFSET_Z_LAYERING)
                .setWriteMaskState(COLOR_WRITE)
                .setTextureState(new TextureStateShard(textureLocation, false, false))
                .createCompositeState(false);

        return create("emissive_transparent_fog_override", state);
    }

    public static RenderType CONFIGURABLE_EMISSIVE_TRANSPARENT (ResourceLocation textureLocation) {
        CompositeState state = CompositeState.builder()
                .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
                .setShaderState(RENDERTYPE_ENTITY_TRANSLUCENT_CULL_SHADER)
                .setLightmapState(LIGHTMAP)
                .setTextureState(new TextureStateShard(textureLocation, false, false))
                .createCompositeState(false);

        return create("emissive_transparent", state);
    }

    private static RenderType create(String name, CompositeState state) {
        return create(name,
                DefaultVertexFormat.NEW_ENTITY,
                VertexFormat.Mode.QUADS,
                256,
                false,
                true,
                state);
    }
}
