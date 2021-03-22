package com.naterbobber.darkerdepths.client.render;

import com.naterbobber.darkerdepths.core.registries.DDBlocks;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BlockRenderHandler {
    public static void blockRenders() {
        RenderTypeLookup.setRenderLayer(DDBlocks.PETRIFIED_DOOR.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(DDBlocks.PETRIFIED_TRAPDOOR.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(DDBlocks.ROPE.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(DDBlocks.ROOTS.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(DDBlocks.LONG_ROOTS.get(), RenderType.getCutout());
    }
}