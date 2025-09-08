package com.naterbobber.darkerdepths.client.render.renderers;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.blocks.blockentities.VoidSoulJarBlockEntity;
import com.naterbobber.darkerdepths.client.models.VoidSoulJarModel;
import com.naterbobber.darkerdepths.client.models.VoidSoulModel;
import com.naterbobber.darkerdepths.client.render.DDRenderTypes;
import com.naterbobber.darkerdepths.client.render.renderers.layers.*;
import com.naterbobber.darkerdepths.entities.VoidSoulEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

import javax.annotation.Nullable;

public class VoidSoulJarBlockEntityRenderer extends GeoBlockRenderer<VoidSoulJarBlockEntity> {
    private static final ResourceLocation GLOWING_TEXTURE = ResourceLocation.fromNamespaceAndPath(DarkerDepths.MOD_ID, "textures/entity/void_soul_jar/void_soul_jar_glowmask.png");
    private static final ResourceLocation INVERTED_TEXTURE = ResourceLocation.fromNamespaceAndPath(DarkerDepths.MOD_ID, "textures/entity/void_soul_jar/void_soul_jar_inverted.png");

    public VoidSoulJarBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        super(new VoidSoulJarModel<>());
        addRenderLayer(new DDCustomRenderTypeLayer<>(this, RenderType.eyes(GLOWING_TEXTURE)));
        addRenderLayer(new DDCustomRenderTypeLayer<>(this, DDRenderTypes.INVERTED_CUBE(INVERTED_TEXTURE)));
    }
}