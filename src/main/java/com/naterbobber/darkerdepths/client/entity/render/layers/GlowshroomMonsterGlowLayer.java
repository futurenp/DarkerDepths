package com.naterbobber.darkerdepths.client.entity.render.layers;

import com.naterbobber.darkerdepths.client.entity.model.GlowshroomMonsterModel;
import com.naterbobber.darkerdepths.common.entities.GlowshroomMonsterEntity;
import com.naterbobber.darkerdepths.core.DarkerDepths;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.AbstractEyesLayer;
import net.minecraft.client.renderer.entity.model.SpiderModel;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GlowshroomMonsterGlowLayer<T extends GlowshroomMonsterEntity, M extends GlowshroomMonsterModel<T>> extends AbstractEyesLayer<T, M> {
    private static final RenderType RENDER_TYPE = RenderType.getEyes(new ResourceLocation(DarkerDepths.MODID,"textures/entity/glowshroom_monster_glow.png"));

    public GlowshroomMonsterGlowLayer(IEntityRenderer<T, M> rendererIn) {
        super(rendererIn);
    }

    public RenderType getRenderType() {
        return RENDER_TYPE;
    }
}