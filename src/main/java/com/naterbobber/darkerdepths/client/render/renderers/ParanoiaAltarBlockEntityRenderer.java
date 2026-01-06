package com.naterbobber.darkerdepths.client.render.renderers;

import com.naterbobber.darkerdepths.block.blockentities.ParanoiaAltarBlockEntity;
import com.naterbobber.darkerdepths.client.models.ParanoiaAltarModel;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

@OnlyIn(Dist.CLIENT)
public class ParanoiaAltarBlockEntityRenderer extends GeoBlockRenderer<ParanoiaAltarBlockEntity> {
    public ParanoiaAltarBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        super(new ParanoiaAltarModel());
    }
}