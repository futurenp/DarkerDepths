package com.naterbobber.darkerdepths.events;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.client.DynamicLightHandler;
import com.naterbobber.darkerdepths.client.models.GlowshroomCapModel;
import com.naterbobber.darkerdepths.client.models.GlowshroomMonsterModel;
import com.naterbobber.darkerdepths.client.particle.DrippingParticle;
import com.naterbobber.darkerdepths.client.renderers.GlowshroomMonsterRenderer;
import com.naterbobber.darkerdepths.client.renderers.PetrifiedBoatRenderer;
import com.naterbobber.darkerdepths.init.DDBlockEntityTypes;
import com.naterbobber.darkerdepths.init.DDBlocks;
import com.naterbobber.darkerdepths.init.DDEntityTypes;
import com.naterbobber.darkerdepths.init.DDModelLayers;
import com.naterbobber.darkerdepths.init.DDParticleTypes;
import com.naterbobber.darkerdepths.init.DDWoodType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
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
    }

    @SubscribeEvent
    public static void registerEntityModelLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(new ModelLayerLocation(new ResourceLocation(DarkerDepths.MODID, "boat/petrified"), "main"), () -> BoatModel.createBodyModel(false));
        event.registerLayerDefinition(new ModelLayerLocation(new ResourceLocation(DarkerDepths.MODID, "chest_boat/petrified"), "main"), () -> BoatModel.createBodyModel(true));
        event.registerLayerDefinition(DDModelLayers.GLOWSHROOM_MONSTER, GlowshroomMonsterModel::createBodyLayer);
        event.registerLayerDefinition(DDModelLayers.GLOWSHROOM_CAP, GlowshroomCapModel::createBodyLayer);
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void registerParticles(ParticleFactoryRegisterEvent event) {
        ParticleEngine engine = Minecraft.getInstance().particleEngine;
        engine.register(DDParticleTypes.DRIPPING_RESIN.get(), DrippingParticle.DrippingResinFactory::new);
        engine.register(DDParticleTypes.FALLING_RESIN.get(), DrippingParticle.FallingResinFactory::new);
        engine.register(DDParticleTypes.LANDING_RESIN.get(), DrippingParticle.LandingResinFactory::new);
    }

    @SubscribeEvent
    public static void onClientSetup(final FMLClientSetupEvent event) {
        ItemBlockRenderTypes.setRenderLayer(DDBlocks.DRY_SPROUTS.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(DDBlocks.ROPE.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(DDBlocks.ROOTS.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(DDBlocks.LONG_ROOTS.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(DDBlocks.LUSH_SPROUTS.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(DDBlocks.MOSSY_SPROUTS.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(DDBlocks.GLOWSPURS.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(DDBlocks.GLOWSPIRE.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(DDBlocks.GLOWSPIRE_PLANT.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(DDBlocks.PETRIFIED_DOOR.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(DDBlocks.PETRIFIED_TRAPDOOR.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(DDBlocks.PETRIFIED_POST.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(DDBlocks.STRIPPED_PETRIFIED_POST.get(), RenderType.cutout());

        MinecraftForge.EVENT_BUS.addListener((LivingEvent.LivingUpdateEvent livingEvent) -> {
            DynamicLightHandler.tick(livingEvent.getEntityLiving());
        });
        event.enqueueWork(DDWoodType::setupWoodTypes);
    }

}
