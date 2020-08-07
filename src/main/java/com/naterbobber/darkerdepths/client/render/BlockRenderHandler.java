package com.naterbobber.darkerdepths.client.render;

import com.naterbobber.darkerdepths.init.BlockInit;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BlockRenderHandler {

    public static void blockRenders() {
        RenderTypeLookup.setRenderLayer(BlockInit.petrified_door, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(BlockInit.petrified_trapdoor, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(BlockInit.rope, RenderType.getCutout());
    }

}