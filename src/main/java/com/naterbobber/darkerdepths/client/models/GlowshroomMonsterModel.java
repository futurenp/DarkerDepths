package com.naterbobber.darkerdepths.client.models;

import com.naterbobber.darkerdepths.common.entities.GlowshroomMonsterEntity;
import com.naterbobber.darkerdepths.common.init.DDEntityTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.Mth;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.constant.DataTickets;

public class GlowshroomMonsterModel extends DDDefaultEntityModel<GlowshroomMonsterEntity> {
    public GlowshroomMonsterModel() {
        super(DDEntityTypes.GLOWSHROOM_MONSTER);
        this.renderTypeFactory = RenderType::entityCutout;
    }

    @Override
    public void setCustomAnimations(GlowshroomMonsterEntity animatable, long instanceId, AnimationState<GlowshroomMonsterEntity> animationState) {
        var headLook = this.getAnimationProcessor().getBone("head_look");

        if (headLook == null) {
            return;
        }

        var entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
        var restPose = headLook.getInitialSnapshot();
        var rotation = new Quaternionf()
                .rotationZYX(restPose.getRotZ(), restPose.getRotY(), restPose.getRotX())
                .rotateY(entityData.netHeadYaw() * Mth.DEG_TO_RAD)
                .rotateX(entityData.headPitch() * Mth.DEG_TO_RAD)
                .getEulerAnglesZYX(new Vector3f());

        headLook.setRotX(rotation.x);
        headLook.setRotY(rotation.y);
        headLook.setRotZ(rotation.z);
    }
}
