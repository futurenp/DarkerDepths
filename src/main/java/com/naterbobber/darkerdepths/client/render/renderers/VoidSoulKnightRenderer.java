package com.naterbobber.darkerdepths.client.render.renderers;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.client.models.VoidSoulKnightModel;
import com.naterbobber.darkerdepths.client.render.renderers.layers.DDCustomRenderTypeLayer;
import com.naterbobber.darkerdepths.entities.VoidSoulKnightEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

@OnlyIn(Dist.CLIENT)
public class VoidSoulKnightRenderer extends GeoEntityRenderer<VoidSoulKnightEntity> {
    private static final ResourceLocation TEXTURE = DarkerDepths.id("textures/entity/void_soul_knight/void_soul_knight_glowmask.png");

    public VoidSoulKnightRenderer(EntityRendererProvider.Context context) {
        super(context, new VoidSoulKnightModel());
        addRenderLayer(new DDCustomRenderTypeLayer<>(this, RenderType.eyes(TEXTURE)));
    }
}