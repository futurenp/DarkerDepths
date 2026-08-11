package com.naterbobber.darkerdepths.client.models;

import com.naterbobber.darkerdepths.DarkerDepths;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.model.GeoModel;

public class DDDefaultBlockItemModel<T extends GeoAnimatable> extends GeoModel<T> {
    protected final ResourceLocation textureLocation;
    protected final ResourceLocation modelLocation;
    protected final ResourceLocation animationLocation;

    public DDDefaultBlockItemModel(String location) {
        this.textureLocation = DarkerDepths.id(String.format("textures/entity/%s/%s.png", location, location));
        this.modelLocation = DarkerDepths.id(String.format("geo/block/%s.geo.json", location));
        this.animationLocation = DarkerDepths.id(String.format("animations/block/%s.animation.json", location));
    }

    @Override
    public ResourceLocation getTextureResource(T animatable) {
        return textureLocation;
    }

    @Override
    public ResourceLocation getModelResource(T animatable) {
        return modelLocation;
    }

    @Override
    public ResourceLocation getAnimationResource(T animatable) {
        return animationLocation;
    }
}
