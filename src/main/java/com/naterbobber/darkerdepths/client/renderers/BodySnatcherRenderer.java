package com.naterbobber.darkerdepths.client.renderers;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.client.models.BodySnatcherModel;
import com.naterbobber.darkerdepths.entities.BodySnatcher;
import com.naterbobber.darkerdepths.init.DDModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BodySnatcherRenderer extends MobRenderer<BodySnatcher, BodySnatcherModel<BodySnatcher>> {
    public static final ResourceLocation TEXTURE = DarkerDepths.id("textures/entity/body_snatcher/body_snatcher.png");

    public BodySnatcherRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new BodySnatcherModel<>(ctx.bakeLayer(DDModelLayers.BODY_SNATCHER)), 0.4F);
    }

    @Override
    public ResourceLocation getTextureLocation(BodySnatcher p_114482_) {
        return TEXTURE;
    }
}
