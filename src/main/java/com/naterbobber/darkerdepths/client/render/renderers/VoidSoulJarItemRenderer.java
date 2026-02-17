package com.naterbobber.darkerdepths.client.render.renderers;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.client.models.VoidSoulJarModel;
import com.naterbobber.darkerdepths.client.render.DDRenderTypes;
import com.naterbobber.darkerdepths.client.render.renderers.layers.DDCustomRenderTypeLayer;
import com.naterbobber.darkerdepths.item.VoidSoulJarItem;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib.renderer.GeoItemRenderer;

@OnlyIn(Dist.CLIENT)
public class VoidSoulJarItemRenderer extends GeoItemRenderer<VoidSoulJarItem> {
    private static final ResourceLocation GLOWING_TEXTURE = DarkerDepths.id("textures/entity/void_soul_jar/void_soul_jar_glowmask.png");
    private static final ResourceLocation INVERTED_TEXTURE = DarkerDepths.id("textures/entity/void_soul_jar/void_soul_jar_inverted.png");


    public VoidSoulJarItemRenderer() {
        super(new VoidSoulJarModel<>());
        addRenderLayer(new DDCustomRenderTypeLayer<>(this, RenderType.eyes(GLOWING_TEXTURE)));
        addRenderLayer(new DDCustomRenderTypeLayer<>(this, DDRenderTypes.INVERTED_CUBE(INVERTED_TEXTURE)));
    }
}