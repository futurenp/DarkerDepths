package com.naterbobber.darkerdepths.client.models;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.entities.BodySnatcherEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

@OnlyIn(Dist.CLIENT)
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

    @Override
    public void setCustomAnimations(BodySnatcherEntity animatable, long instanceId, AnimationState<BodySnatcherEntity> animationState) {
        if (this.getBone("head").isEmpty() || this.headBone == null) return;

        GeoBone head = this.getAnimationProcessor().getBone(this.headBone);

        EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
        head.setRotX(this.getBone("head").get().getRotX() + (entityData.headPitch() * 0.017453292F));
        head.setRotY(this.getBone("head").get().getRotY() + (entityData.netHeadYaw() * 0.017453292F));

        if(this.getBone("body").isPresent()) {
            GeoBone body = this.getBone("body").get();
            head.setPosY(body.getPosY() + 0.7f);
            head.setPosX(body.getPosX());
            head.setPosZ(body.getPosZ());
        }
    }
}