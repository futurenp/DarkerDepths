package com.naterbobber.darkerdepths.client.renderers;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.client.models.BodySnatcherModel;
import com.naterbobber.darkerdepths.client.renderers.layers.BodySnatcherLayer;
import com.naterbobber.darkerdepths.entities.BodySnatcherEntity;
import com.naterbobber.darkerdepths.init.DDModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BodySnatcherRenderer extends MobRenderer<BodySnatcherEntity, BodySnatcherModel<BodySnatcherEntity>> {
    public static final ResourceLocation TEXTURE = DarkerDepths.id("textures/entity/body_snatcher/body_snatcher.png");

    public BodySnatcherRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new BodySnatcherModel<>(ctx.bakeLayer(DDModelLayers.BODY_SNATCHER)), 0.4F);
        this.addLayer(new BodySnatcherLayer(this));
    }

    @Override
    public ResourceLocation getTextureLocation(BodySnatcherEntity p_114482_) {
        return TEXTURE;
    }
}
