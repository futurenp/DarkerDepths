package com.naterbobber.darkerdepths.client.render.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.client.models.DDDefaultEntityModel;
import com.naterbobber.darkerdepths.client.models.GlowshroomMonsterModel;
import com.naterbobber.darkerdepths.client.render.DDRenderTypes;
import com.naterbobber.darkerdepths.client.render.renderers.layers.DDRenderLayer;
import com.naterbobber.darkerdepths.entities.GlowshroomMonsterEntity;
import com.naterbobber.darkerdepths.init.DDEntityTypes;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LightLayer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.joml.Vector3d;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

@OnlyIn(Dist.CLIENT)
public class GlowshroomMonsterRenderer extends GeoEntityRenderer<GlowshroomMonsterEntity> {
    private static final ResourceLocation GLOWSHROOM_TEXTURE = DarkerDepths.id("textures/entity/glowshroom_monster/glowshroom_monster_glowshroom_glowmask.png");
    private static final ResourceLocation MOSS_TEXTURE = DarkerDepths.id("textures/entity/glowshroom_monster/glowshroom_monster_moss_glowmask.png");

    public GlowshroomMonsterRenderer(EntityRendererProvider.Context context) {
        super(context, new GlowshroomMonsterModel());
        addRenderLayer(DDRenderLayer.withType(this, DDRenderTypes.emissiveTransparent(GLOWSHROOM_TEXTURE)));
        addRenderLayer(DDRenderLayer.withType(this, DDRenderTypes.configurableEmissiveTransparent(MOSS_TEXTURE)).setMinBrightness(5));
    }

    @Override
    public void renderFinal(PoseStack poseStack, GlowshroomMonsterEntity animatable, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay, int color) {
        int attackTick = this.animatable.getAttackTick();
        if(attackTick == 24) {
            this.model.getBone("particle").ifPresent((particle) -> {
                RandomSource rand = animatable.getRandom();
                Vector3d particlePos = particle.getWorldPosition();

                double randomOffset = 1;

                if(rand.nextDouble() > .8) {
                    animatable.getCommandSenderWorld().addParticle(
                            ParticleTypes.EXPLOSION,
                            particlePos.x() + rand.nextDouble()/randomOffset - 0.5/randomOffset,
                            particlePos.y() + rand.nextDouble()/randomOffset - 0.5/randomOffset + 0.5,
                            particlePos.z() + rand.nextDouble()/randomOffset - 0.5/randomOffset,
                            rand.nextDouble() - 0.5,
                            -rand.nextDouble(),
                            rand.nextDouble() - 0.5
                    );
                }
            });
        }
    }

    @Override
    protected int getBlockLightLevel(GlowshroomMonsterEntity entity, BlockPos pos) {
        if(entity.isOnFire()) return 15;
        int brightness = entity.level().getBrightness(LightLayer.BLOCK, pos);
        return Math.max(brightness, 2);
    }

    @Override
    public float getMotionAnimThreshold(GlowshroomMonsterEntity animatable) {
        return 0.005f;
    }

}
