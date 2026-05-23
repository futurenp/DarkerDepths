package com.naterbobber.darkerdepths.client.render.renderers;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.client.models.VoidSoulModel;
import com.naterbobber.darkerdepths.client.render.DDRenderTypes;
import com.naterbobber.darkerdepths.client.render.renderers.layers.DDRenderTypeLayer;
import com.naterbobber.darkerdepths.entities.VoidSoulEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.util.ClientUtil;

import javax.annotation.Nullable;

@OnlyIn(Dist.CLIENT)
public class VoidSoulRenderer extends GeoEntityRenderer<VoidSoulEntity> {
    private static final ResourceLocation INNER_TEXTURE = DarkerDepths.id("textures/entity/void_soul/void_soul_glowmask.png");

    public VoidSoulRenderer(EntityRendererProvider.Context context) {
        super(context, new VoidSoulModel());
        addRenderLayer(new DDRenderTypeLayer<>(this, DDRenderTypes.EMISSIVE_TRANSPARENT_FOG_OVERRIDE(INNER_TEXTURE)));
    }
}

