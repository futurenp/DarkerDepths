package com.naterbobber.darkerdepths.client.events;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.block.DDBlockStateProperties;
import com.naterbobber.darkerdepths.client.ClientDeathAnchorAnimationOverlay;
import com.naterbobber.darkerdepths.client.fog.FogManager;
import com.naterbobber.darkerdepths.client.fog.modifiers.BiomeFogModifier;
import com.naterbobber.darkerdepths.client.fog.modifiers.EffectFogModifier;
import com.naterbobber.darkerdepths.client.fog.modifiers.ScorcherFlashModifier;
import com.naterbobber.darkerdepths.client.render.EmissiveBakedModel;
import com.naterbobber.darkerdepths.init.DDBlocks;
import com.naterbobber.darkerdepths.init.DDDataComponents;
import com.naterbobber.darkerdepths.init.DDItems;
import com.naterbobber.darkerdepths.init.DDWoodType;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockModelShaper;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.registries.DeferredBlock;

@OnlyIn(Dist.CLIENT)
@EventBusSubscriber(modid = DarkerDepths.MOD_ID, value = Dist.CLIENT)
public class ClientSetupEvents {

    @SubscribeEvent
    public static void onClientSetup(final FMLClientSetupEvent event) {
        IEventBus eventBus = NeoForge.EVENT_BUS;

        eventBus.register(new ClientEvents());
        eventBus.register(new ClientDeathAnchorAnimationOverlay());
        FogManager.register(new BiomeFogModifier());
        FogManager.register(new EffectFogModifier());
        FogManager.register(new ScorcherFlashModifier());

        event.enqueueWork(() -> {
            DDWoodType.setupWoodTypes();
            registerStilettoProperties();
        });
    }

    private static void registerStilettoProperties() {
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
    }

    @SubscribeEvent
    public static void onModifyBakingResult(ModelEvent.ModifyBakingResult event) {

        // Solid Blocks -> Translucent Glow
        for (BlockState state : DDBlocks.DARKSLATE.get().getStateDefinition().getPossibleStates()) {
            if (state.hasProperty(DDBlockStateProperties.HEAT_LEVEL) && state.getValue(DDBlockStateProperties.HEAT_LEVEL) >= 2) {
                ModelResourceLocation targetModel = BlockModelShaper.stateToModelLocation(state);
                var originalModel = event.getModels().get(targetModel);
                if (originalModel != null) {
                    event.getModels().put(targetModel, new EmissiveBakedModel(originalModel, RenderType.solid(), RenderType.translucent()));
                }
            }
        }

        for (BlockState state : DDBlocks.GEYSER.get().getStateDefinition().getPossibleStates()) {
            if (state.hasProperty(DDBlockStateProperties.BURSTING) && state.getValue(DDBlockStateProperties.BURSTING)) {
                ModelResourceLocation targetModel = BlockModelShaper.stateToModelLocation(state);
                var originalModel = event.getModels().get(targetModel);
                if (originalModel != null) {
                    event.getModels().put(targetModel, new EmissiveBakedModel(originalModel, RenderType.solid(), RenderType.translucent()));
                }
            }
        }

        applyToAllStates(event, RenderType.solid(), RenderType.translucent(),
                DDBlocks.GLOWSHROOM_PLANKS,
                DDBlocks.GLOWSHROOM_BLOCK
        );

        applyToAllStates(event, RenderType.cutout(), RenderType.cutout(),
                DDBlocks.GLIMMERING_VINE_PLANT,
                DDBlocks.GLIMMERING_VINES,
                DDBlocks.GLOWSHROOM
        );
    }

    @SafeVarargs
    private static void applyToAllStates(ModelEvent.ModifyBakingResult event, RenderType baseRender, RenderType glowRender, DeferredBlock<? extends Block>... blockHolders) {
        for (DeferredBlock<? extends Block> holder : blockHolders) {
            for (BlockState state : holder.get().getStateDefinition().getPossibleStates()) {
                ModelResourceLocation targetModel = BlockModelShaper.stateToModelLocation(state);
                var originalModel = event.getModels().get(targetModel);
                if (originalModel != null) {
                    event.getModels().put(targetModel, new EmissiveBakedModel(originalModel, baseRender, glowRender));
                }
            }
        }
    }
}
