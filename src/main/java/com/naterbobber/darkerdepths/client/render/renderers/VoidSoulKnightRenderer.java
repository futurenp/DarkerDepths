package com.naterbobber.darkerdepths.client.render.renderers;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.client.models.DDDefaultEntityModel;
import com.naterbobber.darkerdepths.client.render.DDRenderTypes;
import com.naterbobber.darkerdepths.client.render.renderers.layers.DDRenderLayer;
import com.naterbobber.darkerdepths.entities.VoidSoulKnightEntity;
import com.naterbobber.darkerdepths.init.DDEntityTypes;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

@OnlyIn(Dist.CLIENT)
public class VoidSoulKnightRenderer extends GeoEntityRenderer<VoidSoulKnightEntity> {
    private static final ResourceLocation TEXTURE = DarkerDepths.id("textures/entity/void_soul_knight/void_soul_knight_glowmask.png");

    public VoidSoulKnightRenderer(EntityRendererProvider.Context context) {
        super(context, DDDefaultEntityModel.of(DDEntityTypes.VOID_SOUL_KNIGHT));
        this.addRenderLayer(DDRenderLayer.withType(this, DDRenderTypes.emissiveTransparentFogOverride(TEXTURE)));
    }
}