package com.naterbobber.darkerdepths.client.events;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.client.render.DDShaders;
import com.naterbobber.darkerdepths.client.events.listeners.DDClientReloadListener;
import com.naterbobber.darkerdepths.client.particle.*;
import com.naterbobber.darkerdepths.client.particle.void_soul.*;
import com.naterbobber.darkerdepths.client.particle.geyser.*;
import com.naterbobber.darkerdepths.client.render.DDRenderTypes;
import com.naterbobber.darkerdepths.client.render.renderers.*;
import com.naterbobber.darkerdepths.init.DDBlockEntityTypes;
import com.naterbobber.darkerdepths.init.DDEntityTypes;
import com.naterbobber.darkerdepths.init.DDParticleTypes;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.ChestBoatModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.blockentity.HangingSignRenderer;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.*;

@OnlyIn(Dist.CLIENT)
@EventBusSubscriber(modid = DarkerDepths.MOD_ID, value = Dist.CLIENT)
public class ClientRegisterEvents {


    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(DDBlockEntityTypes.DD_SIGN.get(), SignRenderer::new);
        event.registerBlockEntityRenderer(DDBlockEntityTypes.DD_HANGING_SIGN.get(), HangingSignRenderer::new);
        event.registerEntityRenderer(DDEntityTypes.PETRIFIED_BOAT.get(), context -> new PetrifiedBoatRenderer(context, false));
        event.registerEntityRenderer(DDEntityTypes.PETRIFIED_CHEST_BOAT.get(), context -> new PetrifiedBoatRenderer(context, true));
        event.registerEntityRenderer(DDEntityTypes.GLOWSHROOM_BOAT.get(), context -> new GlowshroomBoatRenderer(context, false));
        event.registerEntityRenderer(DDEntityTypes.GLOWSHROOM_CHEST_BOAT.get(), context -> new GlowshroomBoatRenderer(context, true));
        event.registerEntityRenderer(DDEntityTypes.GLOWSHROOM_MONSTER.get(), GlowshroomMonsterRenderer::new);
        event.registerEntityRenderer(DDEntityTypes.BODY_SNATCHER.get(), BodySnatcherRenderer::new);
        event.registerEntityRenderer(DDEntityTypes.VOID_SOUL_KNIGHT.get(), VoidSoulKnightRenderer::new);
        event.registerEntityRenderer(DDEntityTypes.VOID_SOUL.get(), VoidSoulRenderer::new);
        event.registerEntityRenderer(DDEntityTypes.SCORCHER.get(), ScorcherRenderer::new);
        event.registerBlockEntityRenderer(DDBlockEntityTypes.TOMB.get(), TombBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(DDBlockEntityTypes.PARANOIA_ALTAR.get(), ParanoiaAltarBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(DDBlockEntityTypes.VOID_SOUL_JAR.get(), VoidSoulJarBlockEntityRenderer::new);
    }

    @SubscribeEvent
    public static void registerEntityModelLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(new ModelLayerLocation(DarkerDepths.id("boat/petrified"), "main"), BoatModel::createBodyModel);
        event.registerLayerDefinition(new ModelLayerLocation(DarkerDepths.id("chest_boat/petrified"), "main"), ChestBoatModel::createBodyModel);
        event.registerLayerDefinition(new ModelLayerLocation(DarkerDepths.id("boat/glowshroom"), "main"), BoatModel::createBodyModel);
        event.registerLayerDefinition(new ModelLayerLocation(DarkerDepths.id("chest_boat/glowshroom"), "main"), ChestBoatModel::createBodyModel);

    }

    @SubscribeEvent
    public static void registerParticles(RegisterParticleProvidersEvent event) {
        // Amber
        event.registerSpriteSet(DDParticleTypes.DRIPPING_AMBER.get(), DrippingParticle.DrippingAmberProvider::new);
        event.registerSpriteSet(DDParticleTypes.FALLING_AMBER.get(), DrippingParticle.FallingAmberProvider::new);
        event.registerSpriteSet(DDParticleTypes.LANDING_AMBER.get(), DrippingParticle.LandingAmberProvider::new);

        // Void Soul
        event.registerSpriteSet(DDParticleTypes.VOID_SOUL.get(), VoidSoulParticle.Provider::new);
        event.registerSpriteSet(DDParticleTypes.VOID_SOUL_FLAME.get(), VoidSoulFlameParticle.Provider::new);
        event.registerSpriteSet(DDParticleTypes.VOID_SOUL_FLAME_SMOKE.get(), VoidSoulFlameSmokeParticle.Provider::new);
        event.registerSpriteSet(DDParticleTypes.VOID_SOUL_SMOKE.get(), VoidSoulDeathParticle.Provider::new);

        // Geyser
        event.registerSpriteSet(DDParticleTypes.GEYSER_PASSIVE_SMOKE.get(), GeyserPassiveSmokeParticle.Provider::new);
        event.registerSpriteSet(DDParticleTypes.GEYSER_BURST_SMOKE.get(), GeyserBurstSmokeParticle.Provider::new);
        event.registerSpriteSet(DDParticleTypes.GEYSER_BURST_SMOKE_LAVA.get(), GeyserBurstSmokeParticle.LavaProvider::new);
        event.registerSpriteSet(DDParticleTypes.GEYSER_BURST_MIST.get(), GeyserBurstMistParticle.Provider::new);
        event.registerSpriteSet(DDParticleTypes.GEYSER_BURST_FLAME.get(), GeyserBurstFlameParticle.Provider::new);
        event.registerSpriteSet(DDParticleTypes.SMALL_GEYSER_BURST_FLAME.get(), GeyserBurstFlameParticle.SmallProvider::new);
        event.registerSpriteSet(DDParticleTypes.GEYSER_BURST_FLAME_BOOSTED.get(), GeyserBurstFlameParticle.BoostedProvider::new);

        // Misc
        event.registerSpriteSet(DDParticleTypes.MOLTEN_ASH.get(),
                (spriteSet) -> new ColoredAshParticle.Provider(spriteSet, 1F, 0.4F, 0.25F, ColoredAshParticle.BrightnessBehavior.FADE));
        event.registerSpriteSet(DDParticleTypes.GLOW_GLIMMER.get(),
                (spriteSet) -> new ColoredAshParticle.Provider(spriteSet, 0.25F, 1F, 0.55F, ColoredAshParticle.BrightnessBehavior.FULL_BRIGHT));

        event.registerSpriteSet(DDParticleTypes.SCORCHER_SEARCHLIGHT.get(), ScorcherSearchlightParticle.Provider::new);
    }

    @SubscribeEvent
    public static void registerReloadListener(RegisterClientReloadListenersEvent event) {
        event.registerReloadListener(new DDClientReloadListener());
    }

    @SubscribeEvent
    public static void registerShaders(RegisterShadersEvent event) {
        DDShaders.registerShaders(event);
    }

    @SubscribeEvent
    public static void registerRenderTypes(RegisterNamedRenderTypesEvent event) {
        DDRenderTypes.registerRenderTypes(event);
    }
}
