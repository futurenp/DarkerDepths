package com.naterbobber.darkerdepths.client.render.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.client.models.GlowshroomMonsterModel;
import com.naterbobber.darkerdepths.client.render.DDRenderTypes;
import com.naterbobber.darkerdepths.client.render.renderers.layers.DDCustomRenderTypeLayer;
import com.naterbobber.darkerdepths.entities.GlowshroomMonsterEntity;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
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
    private int currentTick = -1;

    public GlowshroomMonsterRenderer(EntityRendererProvider.Context context) {
        super(context, new GlowshroomMonsterModel());
        addRenderLayer(new DDCustomRenderTypeLayer<>(this, DDRenderTypes.EMISSIVE_TRANSPARENT(GLOWSHROOM_TEXTURE)));
        addRenderLayer(new DDCustomRenderTypeLayer<>(this, DDRenderTypes.CONFIGURABLE_EMISSIVE_TRANSPARENT(MOSS_TEXTURE), 10));
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
        if(brightness < 3) return 3;
        return brightness;
    }
}
