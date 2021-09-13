package com.naterbobber.darkerdepths.client.render;

import com.naterbobber.darkerdepths.client.entity.render.GlowshroomMonsterRenderer;
import com.naterbobber.darkerdepths.client.entity.render.MagmaMinionRenderer;
import com.naterbobber.darkerdepths.client.entity.render.PetrifiedBoatRenderer;
import com.naterbobber.darkerdepths.common.entities.PetrifiedBoatEntity;
import com.naterbobber.darkerdepths.common.tileentities.RepellentTileEntityRenderer;
import com.naterbobber.darkerdepths.core.DarkerDepths;
import com.naterbobber.darkerdepths.core.registries.DDBlocks;
import com.naterbobber.darkerdepths.core.registries.DDEntityTypes;
import com.naterbobber.darkerdepths.core.registries.DDTileEntities;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.tileentity.SignTileEntityRenderer;
import net.minecraft.entity.EntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = DarkerDepths.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class RendererManager {

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        RenderTypeLookup.setRenderLayer(DDBlocks.ALOE.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(DDBlocks.CELESTINE_CRYSTAL.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(DDBlocks.DRY_SPROUTS.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(DDBlocks.DETRITUS.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(DDBlocks.ROPE.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(DDBlocks.ROOTS.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(DDBlocks.LONG_ROOTS.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(DDBlocks.LUSH_SPROUTS.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(DDBlocks.MOSSY_SPROUTS.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(DDBlocks.GLOWSPURS.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(DDBlocks.POTTED_DETRITUS.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(DDBlocks.GLOWSPIRE.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(DDBlocks.GLOWSPIRE_PLANT.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(DDBlocks.POTTED_ALOE.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(DDBlocks.PETRIFIED_DOOR.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(DDBlocks.PETRIFIED_TRAPDOOR.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(DDBlocks.PETRIFIED_POST.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(DDBlocks.STRIPPED_PETRIFIED_POST.get(), RenderType.getCutout());

        RenderTypeLookup.setRenderLayer(DDBlocks.REPELLENT_BLOCK.get(), RenderType.getTranslucent());

        ClientRegistry.bindTileEntityRenderer(DDTileEntities.REPELLENT.get(), RepellentTileEntityRenderer::new);

        ClientRegistry.bindTileEntityRenderer(DDTileEntities.PETRIFIED_SIGN.get(), SignTileEntityRenderer::new);

        RenderingRegistry.registerEntityRenderingHandler(DDEntityTypes.GLOWSHROOM_MONSTER.get(), GlowshroomMonsterRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(DDEntityTypes.MAGMA_MINION.get(), MagmaMinionRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler((EntityType<? extends PetrifiedBoatEntity>) DDEntityTypes.BOAT.get(), PetrifiedBoatRenderer::new);

    }
}