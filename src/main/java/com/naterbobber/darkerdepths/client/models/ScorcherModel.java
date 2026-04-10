package com.naterbobber.darkerdepths.client.models;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.entities.ScorcherEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

@OnlyIn(Dist.CLIENT)
public class ScorcherModel extends DefaultedEntityGeoModel<ScorcherEntity> {
    public ScorcherModel() {
        super(new ResourceLocation(DarkerDepths.MOD_ID), true);
    }

    @Override
    public ResourceLocation getModelResource(ScorcherEntity scorcherEntity) {
        return DarkerDepths.id("geo/entity/scorcher.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(ScorcherEntity scorcherEntity) {
        return DarkerDepths.id("textures/entity/scorcher/scorcher.png");
    }

    @Override
    public ResourceLocation getAnimationResource(ScorcherEntity scorcherEntity) {
        return DarkerDepths.id("animations/entity/scorcher.animation.json");
    }
}