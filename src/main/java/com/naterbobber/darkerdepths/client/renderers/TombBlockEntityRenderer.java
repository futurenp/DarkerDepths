package com.naterbobber.darkerdepths.client.renderers;

import com.naterbobber.darkerdepths.blocks.blockentities.TombBlockEntity;
import com.naterbobber.darkerdepths.client.models.TombModel;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class TombBlockEntityRenderer extends GeoBlockRenderer<TombBlockEntity> {
    public TombBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        super(new TombModel());
    }

    @Override
    public boolean shouldRenderOffScreen(TombBlockEntity model) {
        return true;
    }
}