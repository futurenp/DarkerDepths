package com.naterbobber.darkerdepths.client.render.renderers;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.client.models.VoidSoulJarModel;
import com.naterbobber.darkerdepths.client.render.DDRenderTypes;
import com.naterbobber.darkerdepths.client.render.renderers.layers.DDRenderLayer;
import com.naterbobber.darkerdepths.item.VoidSoulJarItem;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import software.bernie.geckolib.renderer.GeoItemRenderer;

@OnlyIn(Dist.CLIENT)
public class VoidSoulJarItemRenderer extends GeoItemRenderer<VoidSoulJarItem> {
    private static final ResourceLocation GLOWING_TEXTURE = DarkerDepths.id("textures/entity/void_soul_jar/void_soul_jar_glowmask.png");
    private static final ResourceLocation INVERTED_TEXTURE = DarkerDepths.id("textures/entity/void_soul_jar/void_soul_jar_inverted.png");


    public VoidSoulJarItemRenderer() {
        super(new VoidSoulJarModel<>());
        addRenderLayer(DDRenderLayer.withType(this, DDRenderTypes.emissiveTransparentFogOverride(GLOWING_TEXTURE)));
        addRenderLayer(DDRenderLayer.withType(this, DDRenderTypes.invertedCube(INVERTED_TEXTURE)));
    }
}