package com.naterbobber.darkerdepths.client.renderers;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.client.models.VoidSoulKnightModel;
import com.naterbobber.darkerdepths.client.renderers.layers.BodySnatcherLayer;
import com.naterbobber.darkerdepths.entities.VoidSoulKnightEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;

public class VoidSoulKnightRenderer extends GeoEntityRenderer<VoidSoulKnightEntity> {
    public VoidSoulKnightRenderer(EntityRendererProvider.Context context) {
        super(context, new VoidSoulKnightModel());
        this.addRenderLayer(new AutoGlowingGeoLayer<>(this));
    }
}