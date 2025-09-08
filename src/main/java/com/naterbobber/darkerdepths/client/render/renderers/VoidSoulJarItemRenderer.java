package com.naterbobber.darkerdepths.client.render.renderers;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.client.models.VoidSoulJarModel;
import com.naterbobber.darkerdepths.client.render.DDRenderTypes;
import com.naterbobber.darkerdepths.client.render.renderers.layers.DDCustomRenderTypeLayer;
import com.naterbobber.darkerdepths.item.VoidSoulJarItem;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class VoidSoulJarItemRenderer extends GeoItemRenderer<VoidSoulJarItem> {
    private static final ResourceLocation GLOWING_TEXTURE = ResourceLocation.fromNamespaceAndPath(DarkerDepths.MOD_ID, "textures/entity/void_soul_jar/void_soul_jar_glowmask.png");
    private static final ResourceLocation INVERTED_TEXTURE = ResourceLocation.fromNamespaceAndPath(DarkerDepths.MOD_ID, "textures/entity/void_soul_jar/void_soul_jar_inverted.png");


    public VoidSoulJarItemRenderer() {
        super(new VoidSoulJarModel<>());
        addRenderLayer(new DDCustomRenderTypeLayer<>(this, RenderType.eyes(GLOWING_TEXTURE)));
        addRenderLayer(new DDCustomRenderTypeLayer<>(this, DDRenderTypes.INVERTED_CUBE(INVERTED_TEXTURE)));
    }
}