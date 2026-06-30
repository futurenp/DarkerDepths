package com.naterbobber.darkerdepths.client.models;

import com.naterbobber.darkerdepths.entities.GlowshroomMonsterEntity;
import com.naterbobber.darkerdepths.init.DDEntityTypes;
import net.minecraft.client.renderer.RenderType;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.model.data.EntityModelData;

public class GlowshroomMonsterModel extends DDDefaultEntityModel<GlowshroomMonsterEntity> {
    public GlowshroomMonsterModel() {
        super(DDEntityTypes.GLOWSHROOM_MONSTER);
        this.renderTypeFactory = RenderType::entityCutout;
    }

    @Override
    public void setCustomAnimations(GlowshroomMonsterEntity animatable, long instanceId, AnimationState<GlowshroomMonsterEntity> animationState) {
        if (this.getBone("head").isEmpty() || this.headBone == null) return;

        GeoBone head = this.getAnimationProcessor().getBone(this.headBone);

        EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
        head.setRotX(this.getBone("head").get().getRotX() + (entityData.headPitch() * 0.017453292F));
        head.setRotY(this.getBone("head").get().getRotY() + (entityData.netHeadYaw() * 0.017453292F));
    }
}
