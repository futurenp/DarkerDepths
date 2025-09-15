package com.naterbobber.darkerdepths.client.render.renderers;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.client.models.VoidSoulModel;
import com.naterbobber.darkerdepths.client.render.DDRenderTypes;
import com.naterbobber.darkerdepths.client.render.renderers.layers.DDCustomRenderTypeLayer;
import com.naterbobber.darkerdepths.entities.VoidSoulEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

import javax.annotation.Nullable;

@OnlyIn(Dist.CLIENT)
public class VoidSoulRenderer extends GeoEntityRenderer<VoidSoulEntity> {
    private static final ResourceLocation INNER_TEXTURE = ResourceLocation.fromNamespaceAndPath(DarkerDepths.MODID, "textures/entity/void_soul/void_soul_glowmask.png");

    public VoidSoulRenderer(EntityRendererProvider.Context context) {
        super(context, new VoidSoulModel());
        addRenderLayer(new DDCustomRenderTypeLayer<>(this, RenderType.eyes(INNER_TEXTURE)));
    }

    @Override
    public RenderType getRenderType(VoidSoulEntity animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return DDRenderTypes.INVERTED_CUBE(texture);
    }
}

