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
import com.naterbobber.darkerdepths.init.DDItems;
import com.naterbobber.darkerdepths.init.DDWoodType;
import com.naterbobber.darkerdepths.item.StilettoItem;
import net.minecraft.client.renderer.block.BlockModelShaper;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = DarkerDepths.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientSetupEvents {

    @SubscribeEvent
    public static void onClientSetup(final FMLClientSetupEvent event) {
        IEventBus eventBus = MinecraftForge.EVENT_BUS;

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

    private static void registerStilettoProperties(){
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
    }

    @SubscribeEvent
    public static void onModifyBakingResult(ModelEvent.ModifyBakingResult event) {
        for (BlockState state : DDBlocks.DARKSLATE.get().getStateDefinition().getPossibleStates()) {
            if (state.hasProperty(DDBlockStateProperties.HEAT_LEVEL) && state.getValue(DDBlockStateProperties.HEAT_LEVEL) >= 2) {
                ModelResourceLocation targetModel = BlockModelShaper.stateToModelLocation(state);
                var originalModel = event.getModels().get(targetModel);
                if (originalModel != null) {
                    event.getModels().put(targetModel, new EmissiveBakedModel(originalModel));
                }
            }
        }

        for (BlockState state : DDBlocks.GEYSER.get().getStateDefinition().getPossibleStates()) {
            if (state.hasProperty(DDBlockStateProperties.BURSTING) && state.getValue(DDBlockStateProperties.BURSTING)) {
                ModelResourceLocation targetModel = BlockModelShaper.stateToModelLocation(state);
                var originalModel = event.getModels().get(targetModel);
                if (originalModel != null) {
                    event.getModels().put(targetModel, new EmissiveBakedModel(originalModel));
                }
            }
        }
    }
}
