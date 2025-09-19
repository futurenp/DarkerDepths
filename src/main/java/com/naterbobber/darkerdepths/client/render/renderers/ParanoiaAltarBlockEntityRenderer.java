package com.naterbobber.darkerdepths.client.render.renderers;

import com.naterbobber.darkerdepths.blocks.blockentities.ParanoiaAltarBlockEntity;
import com.naterbobber.darkerdepths.client.models.ParanoiaAltarModel;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

@OnlyIn(Dist.CLIENT)
public class ParanoiaAltarBlockEntityRenderer extends GeoBlockRenderer<ParanoiaAltarBlockEntity> {
    public ParanoiaAltarBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        super(new ParanoiaAltarModel());
    }
}