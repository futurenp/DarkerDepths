package com.naterbobber.darkerdepths.client.entity.render;

import com.naterbobber.darkerdepths.client.entity.model.GlowshroomMonsterModel;
import com.naterbobber.darkerdepths.client.entity.model.MagmaMinionModel;
import com.naterbobber.darkerdepths.client.entity.render.layers.GlowshroomMonsterGlowLayer;
import com.naterbobber.darkerdepths.client.entity.render.layers.MagmaMinionGlowLayer;
import com.naterbobber.darkerdepths.common.entities.GlowshroomMonsterEntity;
import com.naterbobber.darkerdepths.common.entities.MagmaMinionEntity;
import com.naterbobber.darkerdepths.core.DarkerDepths;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.SpiderEyesLayer;
import net.minecraft.client.renderer.entity.model.SpiderModel;
import net.minecraft.entity.monster.SpiderEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MagmaMinionRenderer<T extends MagmaMinionEntity> extends MobRenderer<T, MagmaMinionModel<T>> {
    private static final ResourceLocation MAGMA_MINION_TEXTURES = new ResourceLocation(DarkerDepths.MODID,"textures/entity/magma_minion.png");

    public MagmaMinionRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new MagmaMinionModel<>(), 0.5F);
        addLayer(new MagmaMinionGlowLayer<>(this));
    }

    protected float getDeathMaxRotation(T entityLivingBaseIn) {
        return 90.0F;
    }

    public ResourceLocation getEntityTexture(T entity) {
        return MAGMA_MINION_TEXTURES;
    }
}