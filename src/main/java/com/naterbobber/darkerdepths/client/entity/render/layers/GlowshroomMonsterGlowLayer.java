package com.naterbobber.darkerdepths.client.entity.render.layers;

import com.naterbobber.darkerdepths.client.entity.model.GlowshroomMonsterModel;
import com.naterbobber.darkerdepths.common.entities.GlowshroomMonsterEntity;
import com.naterbobber.darkerdepths.core.DarkerDepths;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.AbstractEyesLayer;
import net.minecraft.util.ResourceLocation;

public class GlowshroomMonsterGlowLayer<T extends GlowshroomMonsterEntity, M extends GlowshroomMonsterModel<T>> extends AbstractEyesLayer<T, M> {
    private static final RenderType TEXTURE = RenderType.getEyes(new ResourceLocation(DarkerDepths.MODID,"textures/entity/glowshroom_monster_layer.png"));

    public GlowshroomMonsterGlowLayer(IEntityRenderer<T, M> renderer) {
        super(renderer);
    }

    @Override
    public RenderType getRenderType() {
        return TEXTURE;
    }
}