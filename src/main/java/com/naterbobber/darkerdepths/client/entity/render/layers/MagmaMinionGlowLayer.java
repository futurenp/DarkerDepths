package com.naterbobber.darkerdepths.client.entity.render.layers;

import com.naterbobber.darkerdepths.client.entity.model.MagmaMinionModel;
import com.naterbobber.darkerdepths.common.entities.MagmaMinionEntity;
import com.naterbobber.darkerdepths.core.DarkerDepths;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.AbstractEyesLayer;
import net.minecraft.util.ResourceLocation;

public class MagmaMinionGlowLayer<T extends MagmaMinionEntity, M extends MagmaMinionModel<T>> extends AbstractEyesLayer<T, M> {
    private static final RenderType TEXTURE = RenderType.getEyes(new ResourceLocation(DarkerDepths.MODID, "textures/entity/magma_minion_layer.png"));

    public MagmaMinionGlowLayer(IEntityRenderer<T, M> renderer) {
        super(renderer);
    }

    @Override
    public RenderType getRenderType() {
        return TEXTURE;
    }

}
