package com.naterbobber.darkerdepths.client.render.renderers;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.block.blockentities.VoidSoulJarBlockEntity;
import com.naterbobber.darkerdepths.client.models.VoidSoulJarModel;
import com.naterbobber.darkerdepths.client.render.DDRenderTypes;
import com.naterbobber.darkerdepths.client.render.renderers.layers.DDCustomRenderTypeLayer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

@OnlyIn(Dist.CLIENT)
public class VoidSoulJarBlockEntityRenderer extends GeoBlockRenderer<VoidSoulJarBlockEntity> {
    private static final ResourceLocation GLOWING_TEXTURE = DarkerDepths.id("textures/entity/void_soul_jar/void_soul_jar_glowmask.png");
    private static final ResourceLocation INVERTED_TEXTURE = DarkerDepths.id("textures/entity/void_soul_jar/void_soul_jar_inverted.png");

    public VoidSoulJarBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        super(new VoidSoulJarModel<>());
        addRenderLayer(new DDCustomRenderTypeLayer<>(this, RenderType.eyes(GLOWING_TEXTURE)));
        addRenderLayer(new DDCustomRenderTypeLayer<>(this, DDRenderTypes.INVERTED_CUBE(INVERTED_TEXTURE)));
    }
}