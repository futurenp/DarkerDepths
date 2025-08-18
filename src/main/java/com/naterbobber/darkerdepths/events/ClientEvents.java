package com.naterbobber.darkerdepths.events;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.client.DynamicLightHandler;
import com.naterbobber.darkerdepths.client.models.BodySnatcherModel;
import com.naterbobber.darkerdepths.client.models.GlowshroomCapModel;
import com.naterbobber.darkerdepths.client.models.GlowshroomMonsterModel;
import com.naterbobber.darkerdepths.client.particle.DrippingParticle;
import com.naterbobber.darkerdepths.client.particle.VoidSoulFlameParticle;
import com.naterbobber.darkerdepths.client.particle.VoidSoulFlameSmokeParticle;
import com.naterbobber.darkerdepths.client.particle.VoidSoulParticle;
import com.naterbobber.darkerdepths.client.renderers.*;
import com.naterbobber.darkerdepths.init.*;
import com.naterbobber.darkerdepths.item.StilettoItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.ChestBoatModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = DarkerDepths.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEvents {


    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(DDBlockEntityTypes.DD_SIGN.get(), SignRenderer::new);
        event.registerEntityRenderer(DDEntityTypes.PETRIFIED_BOAT.get(), context -> new PetrifiedBoatRenderer(context, false));
        event.registerEntityRenderer(DDEntityTypes.PETRIFIED_CHEST_BOAT.get(), context -> new PetrifiedBoatRenderer(context, true));
        event.registerEntityRenderer(DDEntityTypes.GLOWSHROOM_MONSTER.get(), GlowshroomMonsterRenderer::new);
        event.registerEntityRenderer(DDEntityTypes.BODY_SNATCHER.get(), BodySnatcherRenderer::new);
        event.registerBlockEntityRenderer(DDBlockEntityTypes.TOMB.get(), TombBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(DDBlockEntityTypes.PARANOIA_ALTAR.get(), ParanoiaAltarBlockEntityRenderer::new);
    }

    @SubscribeEvent
    public static void registerEntityModelLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(new ModelLayerLocation(DarkerDepths.id("boat/petrified"), "main"), BoatModel::createBodyModel);
        event.registerLayerDefinition(new ModelLayerLocation(DarkerDepths.id("chest_boat/petrified"), "main"), ChestBoatModel::createBodyModel);
        event.registerLayerDefinition(DDModelLayers.GLOWSHROOM_MONSTER, GlowshroomMonsterModel::createBodyLayer);
        event.registerLayerDefinition(DDModelLayers.GLOWSHROOM_CAP, GlowshroomCapModel::createBodyLayer);
        event.registerLayerDefinition(DDModelLayers.BODY_SNATCHER, BodySnatcherModel::createBodyLayer);
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
        MinecraftForge.EVENT_BUS.addListener((LivingEvent.LivingTickEvent livingEvent) -> {
            DynamicLightHandler.tick(livingEvent.getEntity());
        });

        MinecraftForge.EVENT_BUS.register(new ClientForgeEvents());

        event.enqueueWork(() -> {
            DDWoodType.setupWoodTypes();

            ItemProperties.register(DDItems.STILETTO.get(), DarkerDepths.id("charge"), (itemStack, clientLevel, livingEntity, i) -> {
                CompoundTag tag = itemStack.getTag();
                if (tag != null) {
                    if (tag.getInt(StilettoItem.TIME_FRAME) > 0) {
                        return 1.0F;
                    }
                    if (tag.getInt(StilettoItem.READY_TICKS) > 0) {
                        return 0.5F;
                    }
                }

                return 0.0F;
            });
        });
    }
}
