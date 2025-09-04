package com.naterbobber.darkerdepths.client.render.renderers;

import com.naterbobber.darkerdepths.blocks.blockentities.ParanoiaAltarBlockEntity;
import com.naterbobber.darkerdepths.client.models.ParanoiaAltarModel;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class ParanoiaAltarBlockEntityRenderer extends GeoBlockRenderer<ParanoiaAltarBlockEntity> {
    public ParanoiaAltarBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        super(new ParanoiaAltarModel());
    }
}