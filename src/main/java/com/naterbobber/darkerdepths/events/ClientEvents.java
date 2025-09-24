package com.naterbobber.darkerdepths.events;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.client.models.GlowshroomCapModel;
import com.naterbobber.darkerdepths.client.particle.DrippingParticle;
import com.naterbobber.darkerdepths.client.particle.VoidSoulFlameParticle;
import com.naterbobber.darkerdepths.client.particle.VoidSoulFlameSmokeParticle;
import com.naterbobber.darkerdepths.client.particle.VoidSoulParticle;
import com.naterbobber.darkerdepths.client.render.renderers.BodySnatcherRenderer;
import com.naterbobber.darkerdepths.client.render.renderers.GlowshroomMonsterRenderer;
import com.naterbobber.darkerdepths.client.render.renderers.ParanoiaAltarBlockEntityRenderer;
import com.naterbobber.darkerdepths.client.render.renderers.PetrifiedBoatRenderer;
import com.naterbobber.darkerdepths.client.render.renderers.TombBlockEntityRenderer;
import com.naterbobber.darkerdepths.client.render.renderers.VoidSoulJarBlockEntityRenderer;
import com.naterbobber.darkerdepths.client.render.renderers.VoidSoulKnightRenderer;
import com.naterbobber.darkerdepths.client.render.renderers.VoidSoulRenderer;
import com.naterbobber.darkerdepths.init.DDBlockEntityTypes;
import com.naterbobber.darkerdepths.init.DDDataComponents;
import com.naterbobber.darkerdepths.init.DDEntityTypes;
import com.naterbobber.darkerdepths.init.DDItems;
import com.naterbobber.darkerdepths.init.DDModelLayers;
import com.naterbobber.darkerdepths.init.DDParticleTypes;
import com.naterbobber.darkerdepths.init.DDWoodType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.ChestBoatModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.renderer.blockentity.HangingSignRenderer;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.client.renderer.item.ItemProperties;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.common.NeoForge;

@OnlyIn(Dist.CLIENT)
@EventBusSubscriber(modid = DarkerDepths.MOD_ID)
public class ClientEvents {


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
        event.registerLayerDefinition(DDModelLayers.GLOWSHROOM_CAP, GlowshroomCapModel::createBodyLayer);
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

    @SubscribeEvent
    public static void onClientSetup(final FMLClientSetupEvent event) {
        IEventBus eventBus = NeoForge.EVENT_BUS;

        eventBus.register(new ClientForgeEvents());
        eventBus.register(new ClientDeathAnchorAnimationOverlay());

        event.enqueueWork(() -> {
            DDWoodType.setupWoodTypes();

            ItemProperties.register(DDItems.STILETTO.get(), DarkerDepths.id("charge"), (itemStack, clientLevel, livingEntity, i) -> {
                int time = itemStack.getOrDefault(DDDataComponents.STILETTO_TIME, 0);
                int readyTime = itemStack.getOrDefault(DDDataComponents.STILETTO_READY_TIME, 0);

                if (time > 0) {
                    return 1.0F;
                }
                if (readyTime > 0) {
                    return 0.5F;
                }

                return 0.0F;
            });
        });
    }
}
