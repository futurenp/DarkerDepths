package com.naterbobber.darkerdepths.client.render.renderers;

import com.naterbobber.darkerdepths.client.models.DDDefaultBlockItemModel;
import com.naterbobber.darkerdepths.common.init.DDBlockEntityTypes;
import com.naterbobber.darkerdepths.common.item.ParanoiaAltarItem;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import software.bernie.geckolib.renderer.GeoItemRenderer;

@OnlyIn(Dist.CLIENT)
public class ParanoiaAltarItemRenderer extends GeoItemRenderer<ParanoiaAltarItem> {
    public ParanoiaAltarItemRenderer() {
        super(new DDDefaultBlockItemModel<>(DDBlockEntityTypes.PARANOIA_ALTAR.getId().getPath()));

    }
}