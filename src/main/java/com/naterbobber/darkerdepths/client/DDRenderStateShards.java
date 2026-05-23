package com.naterbobber.darkerdepths.client;

import net.minecraft.client.renderer.RenderStateShard;

public class DDRenderStateShards {
    public static final RenderStateShard.ShaderStateShard GLOW_THROUGH_FOG =
            new RenderStateShard.ShaderStateShard(DDShaders::getRenderTypeGlowThroughFog);
}
