package com.naterbobber.darkerdepths.client.models;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.entities.BodySnatcherEntity;
import com.naterbobber.darkerdepths.entities.GlowshroomMonsterEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

@OnlyIn(Dist.CLIENT)
public class GlowshroomMonsterModel extends DefaultedEntityGeoModel<GlowshroomMonsterEntity> {
    public GlowshroomMonsterModel() {
        super(ResourceLocation.withDefaultNamespace(DarkerDepths.MODID), false);
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

//    @Override
//    public void setCustomAnimations(GlowshroomMonsterEntity animatable, long instanceId, AnimationState<GlowshroomMonsterEntity> animationState) {
//        if (this.getBone("head").isEmpty() || this.headBone == null) return;
//
//        CoreGeoBone head = this.getAnimationProcessor().getBone(this.headBone);
//
//        EntityModelData entityData = (EntityModelData) animationState.getData(DataTickets.ENTITY_MODEL_DATA);
//        head.setRotX(this.getBone("head").get().getRotX() + (entityData.headPitch() * 0.017453292F));
//        head.setRotY(this.getBone("head").get().getRotY() + (entityData.netHeadYaw() * 0.017453292F));
//
//        if(this.getBone("body").isPresent()) {
//            GeoBone body = this.getBone("body").get();
//            head.setPosY(body.getPosY() + 0.7f);
//            head.setPosX(body.getPosX());
//            head.setPosZ(body.getPosZ());
//        }
//    }
}