package com.naterbobber.darkerdepths.client.models;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.entities.BodySnatcherEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

public class BodySnatcherModel extends DefaultedEntityGeoModel<BodySnatcherEntity> {
    public BodySnatcherModel() {
        super(ResourceLocation.withDefaultNamespace(DarkerDepths.MOD_ID), true);
    }

    @Override
    public ResourceLocation getModelResource(BodySnatcherEntity bodySnatcherEntity) {
        return DarkerDepths.id("geo/entity/body_snatcher.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(BodySnatcherEntity bodySnatcherEntity) {
        return DarkerDepths.id("textures/entity/body_snatcher/body_snatcher.png");
    }

    @Override
    public ResourceLocation getAnimationResource(BodySnatcherEntity bodySnatcherEntity) {
        return DarkerDepths.id("animations/entity/body_snatcher.animation.json");
    }
}