package com.naterbobber.darkerdepths.client.models;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.entities.GlowshroomMonsterEntity;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

@OnlyIn(Dist.CLIENT)
public class GlowshroomMonsterModel extends DefaultedEntityGeoModel<GlowshroomMonsterEntity> {
    public GlowshroomMonsterModel() {
        super(ResourceLocation.withDefaultNamespace(DarkerDepths.MOD_ID), true);
    }

    @Override
    public ResourceLocation getModelResource(GlowshroomMonsterEntity glowshroomMonsterEntity) {
        return DarkerDepths.id("geo/entity/glowshroom_monster.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(GlowshroomMonsterEntity glowshroomMonsterEntity) {
        return DarkerDepths.id("textures/entity/glowshroom_monster/glowshroom_monster.png");
    }

    @Override
    public ResourceLocation getAnimationResource(GlowshroomMonsterEntity glowshroomMonsterEntity) {
        return DarkerDepths.id("animations/entity/glowshroom_monster.animation.json");
    }
}