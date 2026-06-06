package com.naterbobber.darkerdepths.client.render;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.naterbobber.darkerdepths.DarkerDepths;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.event.RegisterNamedRenderTypesEvent;

import static net.minecraft.client.renderer.RenderType.*;

@OnlyIn(Dist.CLIENT)
final public class DDRenderTypes {
    private DDRenderTypes() {}

    public static void registerRenderTypes(RegisterNamedRenderTypesEvent event) {
    }

    public static void register(RegisterNamedRenderTypesEvent event, RenderType renderType){
        event.register(DarkerDepths.id(renderType.name), renderType, renderType);
    }

    public static RenderType invertedCube(ResourceLocation textureLocation) {
        var stateBuilder = stateBuilderWithTexture(textureLocation)
                .setShaderState(RENDERTYPE_ENTITY_CUTOUT_SHADER);

        return createRenderType("inverted_cube", stateBuilder);
    }

    public static RenderType emissiveSolid(ResourceLocation textureLocation) {
        var stateBuilder = stateBuilderWithTexture(textureLocation)
                .setShaderState(RENDERTYPE_EYES_SHADER);

        return createRenderType("emissive_solid", stateBuilder);
    }

    public static RenderType emissiveTransparent(ResourceLocation textureLocation) {
        var stateBuilder = stateBuilderWithTexture(textureLocation)
                .setShaderState(RENDERTYPE_EYES_SHADER)
                .setTransparencyState(TRANSLUCENT_TRANSPARENCY);

        return createRenderType("emissive_transparent", stateBuilder);
    }

    public static RenderType emissiveTransparentFogOverride(ResourceLocation textureLocation) {
        var stateBuilder = stateBuilderWithTexture(textureLocation)
                .setShaderState(DDRenderStateShards.GLOW_THROUGH_FOG)
                .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
                .setCullState(NO_CULL)
                .setLayeringState(RenderStateShard.VIEW_OFFSET_Z_LAYERING)
                .setWriteMaskState(COLOR_WRITE);

        return createRenderType("emissive_transparent_fog_override", stateBuilder);
    }

    public static RenderType configurableEmissiveTransparent(ResourceLocation textureLocation) {
        var stateBuilder = stateBuilderWithTexture(textureLocation)
                .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
                .setShaderState(RENDERTYPE_ENTITY_TRANSLUCENT_CULL_SHADER)
                .setLightmapState(LIGHTMAP);

        return createRenderType("emissive_transparent", stateBuilder);
    }

    private static RenderType createRenderType(String name, CompositeState.CompositeStateBuilder stateBuilder) {
        return RenderType.create(name,
                DefaultVertexFormat.NEW_ENTITY,
                VertexFormat.Mode.QUADS,
                256,
                false,
                true,
                stateBuilder.createCompositeState(false)
        );
    }

    private static CompositeState.CompositeStateBuilder stateBuilderWithTexture(ResourceLocation textureLocation) {
        return CompositeState.builder().setTextureState(new TextureStateShard(textureLocation, false, false));
    }
}
