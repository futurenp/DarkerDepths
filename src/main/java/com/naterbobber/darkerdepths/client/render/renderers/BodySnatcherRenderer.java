package com.naterbobber.darkerdepths.client.render.renderers;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.client.models.BodySnatcherModel;
import com.naterbobber.darkerdepths.client.render.renderers.layers.DDRenderTypeLayer;
import com.naterbobber.darkerdepths.entities.BodySnatcherEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

@OnlyIn(Dist.CLIENT)
public class BodySnatcherRenderer extends GeoEntityRenderer<BodySnatcherEntity> {
    private static final ResourceLocation TEXTURE = DarkerDepths.id("textures/entity/body_snatcher/body_snatcher_glowmask.png");

    public BodySnatcherRenderer(EntityRendererProvider.Context context) {
        super(context, new BodySnatcherModel());
        addRenderLayer(new DDRenderTypeLayer<>(this, RenderType.eyes(TEXTURE)));
    }
}