package com.naterbobber.darkerdepths.client.render;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.naterbobber.darkerdepths.config.DDConfig;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureManager;

public class DDParticleRenderTypes {
    private static final ParticleRenderType PARTICLE_GLOW_THROUGH_FOG = new ParticleRenderType() {
        @Override
        public BufferBuilder begin(Tesselator tesselator, TextureManager textureManager) {
            RenderSystem.depthMask(true);
            RenderSystem.setShader(DDShaders::getParticleRenderTypeGlowThroughFog);
            RenderSystem.setShaderTexture(0, TextureAtlas.LOCATION_PARTICLES);
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            return tesselator.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.PARTICLE);
        }

        @Override
        public String toString() {
            return "PARTICLE_GLOW_THROUGH_FOG";
        }
    };

    private static final ParticleRenderType PARTICLE_GLOW_TRANSPARENT = new ParticleRenderType() {
        @Override
        public BufferBuilder begin(Tesselator tesselator, TextureManager textureManager) {
            RenderSystem.depthMask(true);
            RenderSystem.setShaderTexture(0, TextureAtlas.LOCATION_PARTICLES);
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            return tesselator.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.PARTICLE);
        }

        @Override
        public String toString() {
            return "PARTICLE_GLOW_THROUGH_FOG";
        }
    };

    public static ParticleRenderType getParticleGlowThroughFog() {
        if(!DDConfig.CONFIG.DARKER_DEPTHS_SHADERS.get()) {
            return PARTICLE_GLOW_TRANSPARENT;
        }

        return PARTICLE_GLOW_THROUGH_FOG;
    }
}
