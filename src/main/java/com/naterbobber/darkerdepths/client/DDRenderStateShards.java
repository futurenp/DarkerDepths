package com.naterbobber.darkerdepths.client;

import net.minecraft.client.renderer.RenderStateShard;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DDRenderStateShards {
    public static final RenderStateShard.ShaderStateShard GLOW_THROUGH_FOG =
            new RenderStateShard.ShaderStateShard(DDShaders::getRenderTypeGlowThroughFog);
}
