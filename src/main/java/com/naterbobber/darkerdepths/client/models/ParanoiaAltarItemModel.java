package com.naterbobber.darkerdepths.client.models;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.item.ParanoiaAltarItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class ParanoiaAltarItemModel extends GeoModel<ParanoiaAltarItem> {

    @Override
    public ResourceLocation getModelResource(ParanoiaAltarItem paranoiaAltarItem) {
        return DarkerDepths.id("geo/block/paranoia_altar.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(ParanoiaAltarItem paranoiaAltarItem) {
        return DarkerDepths.id("textures/entity/paranoia_altar/paranoia_altar.png");
    }

    @Override
    public ResourceLocation getAnimationResource(ParanoiaAltarItem paranoiaAltarItem) {
        return DarkerDepths.id("animations/block/paranoia_altar.animation.json");
    }
}