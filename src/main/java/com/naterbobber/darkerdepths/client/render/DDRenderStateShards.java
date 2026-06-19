package com.naterbobber.darkerdepths.client.render;

import com.naterbobber.darkerdepths.config.DDConfig;
import net.minecraft.client.renderer.RenderStateShard;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DDRenderStateShards {
    private static final RenderStateShard.ShaderStateShard GLOW_THROUGH_FOG =
            new RenderStateShard.ShaderStateShard(DDShaders::getRenderTypeGlowThroughFog);

    public static RenderStateShard.ShaderStateShard getGlowThroughFog() {
        if(!DDConfig.CONFIG.DARKER_DEPTHS_SHADERS.get()) {
            return RenderStateShard.ShaderStateShard.RENDERTYPE_EYES_SHADER;
        }

        return GLOW_THROUGH_FOG;
    }
}
