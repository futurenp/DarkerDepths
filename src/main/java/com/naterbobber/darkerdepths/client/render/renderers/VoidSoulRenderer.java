package com.naterbobber.darkerdepths.client.render.renderers;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.client.models.VoidSoulModel;
import com.naterbobber.darkerdepths.client.render.DDRenderTypes;
import com.naterbobber.darkerdepths.client.render.renderers.layers.DDRenderLayer;
import com.naterbobber.darkerdepths.entities.VoidSoulEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

@OnlyIn(Dist.CLIENT)
public class VoidSoulRenderer extends GeoEntityRenderer<VoidSoulEntity> {
    private static final ResourceLocation INNER_TEXTURE = DarkerDepths.id("textures/entity/void_soul/void_soul_glowmask.png");

    public VoidSoulRenderer(EntityRendererProvider.Context context) {
        super(context, new VoidSoulModel());
        addRenderLayer(new DDRenderLayer<>(this, DDRenderTypes.EMISSIVE_TRANSPARENT_FOG_OVERRIDE(INNER_TEXTURE)));
    }
}

