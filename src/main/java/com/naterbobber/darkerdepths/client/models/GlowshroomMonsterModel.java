package com.naterbobber.darkerdepths.client.models;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.entities.BodySnatcherEntity;
import com.naterbobber.darkerdepths.entities.GlowshroomMonsterEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.joml.Vector3d;
import software.bernie.example.entity.BatEntity;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

@OnlyIn(Dist.CLIENT)
public class GlowshroomMonsterModel extends DefaultedEntityGeoModel<GlowshroomMonsterEntity> {
    public GlowshroomMonsterModel() {
        super(new ResourceLocation(DarkerDepths.MODID), true);
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
////        head.setRotX(this.getBone("head").get().getRotX() + (entityData.headPitch() * 0.017453292F));
////        head.setRotY(this.getBone("head").get().getRotY() + (entityData.netHeadYaw() * 0.017453292F));
//        head.setRotX(entityData.headPitch() * 0.017453292F - this.getBone("body").get().getRotX());
//        head.setRotY(entityData.netHeadYaw() * 0.017453292F - this.getBone("body").get().getRotY());
//        head.setRotZ(0);
//
//        if(this.getBone("body").isPresent()) {
//            GeoBone body = this.getBone("body").get();
//            head.setPosX(body.getPosX());
//            head.setPosZ(body.getPosZ());
//        }
//    }
}