package com.naterbobber.darkerdepths.events;

import com.mojang.blaze3d.shaders.FogShape;
import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.client.DynamicLightHandler;
import com.naterbobber.darkerdepths.client.models.BodySnatcherModel;
import com.naterbobber.darkerdepths.client.models.GlowshroomCapModel;
import com.naterbobber.darkerdepths.client.models.GlowshroomMonsterModel;
import com.naterbobber.darkerdepths.client.models.TombModel;
import com.naterbobber.darkerdepths.client.particle.DrippingParticle;
import com.naterbobber.darkerdepths.client.particle.VoidSoulFlameParticle;
import com.naterbobber.darkerdepths.client.renderers.BodySnatcherRenderer;
import com.naterbobber.darkerdepths.client.renderers.GlowshroomMonsterRenderer;
import com.naterbobber.darkerdepths.client.renderers.PetrifiedBoatRenderer;
import com.naterbobber.darkerdepths.client.renderers.TombBlockEntityRenderer;
import com.naterbobber.darkerdepths.init.*;
import com.naterbobber.darkerdepths.item.StilettoItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.ChestBoatModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.particle.FlameParticle;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.client.event.ViewportEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = DarkerDepths.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEvents {


    public static final ModelLayerLocation TOMB_LAYER = new ModelLayerLocation(new ResourceLocation(DarkerDepths.MODID, "tomb"), "main");

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(DDBlockEntityTypes.DD_SIGN.get(), SignRenderer::new);
        event.registerEntityRenderer(DDEntityTypes.PETRIFIED_BOAT.get(), context -> new PetrifiedBoatRenderer(context, false));
        event.registerEntityRenderer(DDEntityTypes.PETRIFIED_CHEST_BOAT.get(), context -> new PetrifiedBoatRenderer(context, true));
        event.registerEntityRenderer(DDEntityTypes.GLOWSHROOM_MONSTER.get(), GlowshroomMonsterRenderer::new);
        event.registerEntityRenderer(DDEntityTypes.BODY_SNATCHER.get(), BodySnatcherRenderer::new);
        event.registerBlockEntityRenderer(DDBlockEntityTypes.TOMB.get(), TombBlockEntityRenderer::new);

    }

    @SubscribeEvent
    public static void registerEntityModelLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(new ModelLayerLocation(DarkerDepths.id("boat/petrified"), "main"), BoatModel::createBodyModel);
        event.registerLayerDefinition(new ModelLayerLocation(DarkerDepths.id("chest_boat/petrified"), "main"), ChestBoatModel::createBodyModel);
        event.registerLayerDefinition(DDModelLayers.GLOWSHROOM_MONSTER, GlowshroomMonsterModel::createBodyLayer);
        event.registerLayerDefinition(DDModelLayers.GLOWSHROOM_CAP, GlowshroomCapModel::createBodyLayer);
        event.registerLayerDefinition(DDModelLayers.BODY_SNATCHER, BodySnatcherModel::createBodyLayer);
        event.registerLayerDefinition(TombBlockEntityRenderer.createModelLayerLocation(), TombModel::createBodyLayer);
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void registerParticles(RegisterParticleProvidersEvent event) {
        ParticleEngine engine = Minecraft.getInstance().particleEngine;
        engine.register(DDParticleTypes.DRIPPING_RESIN.get(), DrippingParticle.DrippingResinFactory::new);
        engine.register(DDParticleTypes.FALLING_RESIN.get(), DrippingParticle.FallingResinFactory::new);
        engine.register(DDParticleTypes.LANDING_RESIN.get(), DrippingParticle.LandingResinFactory::new);
        engine.register(DDParticleTypes.VOID_SOUL_FLAME.get(), VoidSoulFlameParticle.VoidSoulFlameFactory::new);
    }

    @SubscribeEvent
    public static void onClientSetup(final FMLClientSetupEvent event) {
        ItemBlockRenderTypes.setRenderLayer(DDBlocks.DRY_SPROUTS.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(DDBlocks.ROPE.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(DDBlocks.MOSSY_SPROUTS.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(DDBlocks.GLOWSPURS.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(DDBlocks.GLIMMERING_VINES.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(DDBlocks.GLIMMERING_VINE_PLANT.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(DDBlocks.PETRIFIED_DOOR.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(DDBlocks.PETRIFIED_TRAPDOOR.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(DDBlocks.PETRIFIED_POST.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(DDBlocks.STRIPPED_PETRIFIED_POST.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(DDBlocks.AMBER_CLUSTER.get(), RenderType.cutout());



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
