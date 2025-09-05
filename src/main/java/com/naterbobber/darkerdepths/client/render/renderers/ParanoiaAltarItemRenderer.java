package com.naterbobber.darkerdepths.client.render.renderers;

import com.naterbobber.darkerdepths.client.models.ParanoiaAltarModel;
import com.naterbobber.darkerdepths.item.ParanoiaAltarItem;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class ParanoiaAltarItemRenderer extends GeoItemRenderer<ParanoiaAltarItem> {
    public ParanoiaAltarItemRenderer() {
        super(new ParanoiaAltarModel<>());
    }
}