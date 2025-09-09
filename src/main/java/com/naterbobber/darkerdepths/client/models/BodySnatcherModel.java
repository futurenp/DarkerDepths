package com.naterbobber.darkerdepths.client.models;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.entities.BodySnatcherEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class BodySnatcherModel extends DefaultedEntityGeoModel<BodySnatcherEntity> {
    public BodySnatcherModel() {
        super(ResourceLocation.withDefaultNamespace(DarkerDepths.MODID), true);
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
        if (!animatable.isIdle()) {
            if (this.headBone != null && this.turnsHead) {
                CoreGeoBone head = this.getAnimationProcessor().getBone(this.headBone);
                if (head != null) {
                    EntityModelData entityData = (EntityModelData) animationState.getData(DataTickets.ENTITY_MODEL_DATA);
                    head.setRotX(entityData.headPitch() * 0.017453292F);
                    head.setRotY(entityData.netHeadYaw() * 0.017453292F);
                }

            }
        }
    }
}