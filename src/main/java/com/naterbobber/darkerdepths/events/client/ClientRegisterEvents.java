package com.naterbobber.darkerdepths.events.client;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.client.particle.DrippingParticle;
import com.naterbobber.darkerdepths.client.particle.VoidSoulFlameParticle;
import com.naterbobber.darkerdepths.client.particle.VoidSoulFlameSmokeParticle;
import com.naterbobber.darkerdepths.client.particle.VoidSoulParticle;
import com.naterbobber.darkerdepths.client.render.renderers.*;
import com.naterbobber.darkerdepths.init.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.ChestBoatModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.renderer.blockentity.HangingSignRenderer;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = DarkerDepths.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientRegisterEvents {


    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(DDBlockEntityTypes.DD_SIGN.get(), SignRenderer::new);
        event.registerBlockEntityRenderer(DDBlockEntityTypes.DD_HANGING_SIGN.get(), HangingSignRenderer::new);
        event.registerEntityRenderer(DDEntityTypes.PETRIFIED_BOAT.get(), context -> new PetrifiedBoatRenderer(context, false));
        event.registerEntityRenderer(DDEntityTypes.PETRIFIED_CHEST_BOAT.get(), context -> new PetrifiedBoatRenderer(context, true));
        event.registerEntityRenderer(DDEntityTypes.GLOWSHROOM_MONSTER.get(), GlowshroomMonsterRenderer::new);
        event.registerEntityRenderer(DDEntityTypes.BODY_SNATCHER.get(), BodySnatcherRenderer::new);
        event.registerEntityRenderer(DDEntityTypes.VOID_SOUL_KNIGHT.get(), VoidSoulKnightRenderer::new);
        event.registerEntityRenderer(DDEntityTypes.VOID_SOUL.get(), VoidSoulRenderer::new);
        event.registerBlockEntityRenderer(DDBlockEntityTypes.TOMB.get(), TombBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(DDBlockEntityTypes.PARANOIA_ALTAR.get(), ParanoiaAltarBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(DDBlockEntityTypes.VOID_SOUL_JAR.get(), VoidSoulJarBlockEntityRenderer::new);
    }

    @SubscribeEvent
    public static void registerEntityModelLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(new ModelLayerLocation(DarkerDepths.id("boat/petrified"), "main"), BoatModel::createBodyModel);
        event.registerLayerDefinition(new ModelLayerLocation(DarkerDepths.id("chest_boat/petrified"), "main"), ChestBoatModel::createBodyModel);
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void registerParticles(RegisterParticleProvidersEvent event) {
        ParticleEngine engine = Minecraft.getInstance().particleEngine;
        engine.register(DDParticleTypes.DRIPPING_AMBER.get(), DrippingParticle.DrippingAmberFactory::new);
        engine.register(DDParticleTypes.FALLING_AMBER.get(), DrippingParticle.FallingAmberFactory::new);
        engine.register(DDParticleTypes.LANDING_AMBER.get(), DrippingParticle.LandingAmberFactory::new);
        engine.register(DDParticleTypes.VOID_SOUL.get(), VoidSoulParticle.VoidSoulFactory::new);
        engine.register(DDParticleTypes.VOID_SOUL_FLAME.get(), VoidSoulFlameParticle.VoidSoulFlameFactory::new);
        engine.register(DDParticleTypes.VOID_SOUL_FLAME_SMOKE.get(), VoidSoulFlameSmokeParticle.VoidSoulFlameSmokeFactory::new);
    }


}
