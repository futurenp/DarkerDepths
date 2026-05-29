package com.naterbobber.darkerdepths.client.models;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.client.render.DDRenderTypes;
import com.naterbobber.darkerdepths.entities.ScorcherEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

@OnlyIn(Dist.CLIENT)
public class ScorcherModel extends DefaultedEntityGeoModel<ScorcherEntity> {
    public ScorcherModel() {
        super(ResourceLocation.withDefaultNamespace(DarkerDepths.MOD_ID), true);
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

    @Override
    public @Nullable RenderType getRenderType(ScorcherEntity animatable, ResourceLocation texture) {
        return DDRenderTypes.emissiveSolid(texture);
    }
}