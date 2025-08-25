package com.naterbobber.darkerdepths.client.renderers;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.client.models.VoidSoulKnightModel;
import com.naterbobber.darkerdepths.client.renderers.layers.VoidSoulKnightLayer;
import com.naterbobber.darkerdepths.entities.VoidSoulKnightEntity;
import com.naterbobber.darkerdepths.init.DDModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class VoidSoulKnightRenderer extends MobRenderer<VoidSoulKnightEntity, VoidSoulKnightModel<VoidSoulKnightEntity>> {
    public static final ResourceLocation TEXTURE = DarkerDepths.id("textures/entity/void_soul_knight/void_soul_knight.png");

    public VoidSoulKnightRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new VoidSoulKnightModel<>(ctx.bakeLayer(DDModelLayers.VOID_SOUL_KNIGHT)), 0.4F);
        this.addLayer(new VoidSoulKnightLayer(this));
    }

    @Override
    public ResourceLocation getTextureLocation(VoidSoulKnightEntity p_114482_) {
        return TEXTURE;
    }
}
