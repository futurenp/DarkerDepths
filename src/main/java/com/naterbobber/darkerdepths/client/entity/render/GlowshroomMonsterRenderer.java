package com.naterbobber.darkerdepths.client.entity.render;

import com.naterbobber.darkerdepths.client.entity.model.GlowshroomMonsterModel;
import com.naterbobber.darkerdepths.client.entity.render.layers.GlowshroomMonsterGlowLayer;
import com.naterbobber.darkerdepths.common.entities.GlowshroomMonsterEntity;
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
public class GlowshroomMonsterRenderer<T extends GlowshroomMonsterEntity> extends MobRenderer<T, GlowshroomMonsterModel<T>> {
    private static final ResourceLocation GLOWSHROOM_MONSTER_TEXTURES = new ResourceLocation(DarkerDepths.MODID,"textures/entity/glowshroom_monster.png");

    public GlowshroomMonsterRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new GlowshroomMonsterModel<>(), 0.8F);
    }

    protected float getDeathMaxRotation(T entityLivingBaseIn) {
        return 90.0F;
    }

    public ResourceLocation getEntityTexture(T entity) {
        return GLOWSHROOM_MONSTER_TEXTURES;
    }
}