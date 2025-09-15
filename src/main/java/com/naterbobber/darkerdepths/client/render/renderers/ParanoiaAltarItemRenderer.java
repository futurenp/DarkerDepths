package com.naterbobber.darkerdepths.client.render.renderers;

import com.naterbobber.darkerdepths.client.models.ParanoiaAltarModel;
import com.naterbobber.darkerdepths.item.ParanoiaAltarItem;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib.renderer.GeoItemRenderer;

@OnlyIn(Dist.CLIENT)
public class ParanoiaAltarItemRenderer extends GeoItemRenderer<ParanoiaAltarItem> {
    public ParanoiaAltarItemRenderer() {
        super(new ParanoiaAltarModel<>());
    }
}