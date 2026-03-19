package com.naterbobber.darkerdepths.events;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.init.DDBlocks;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.furnace.FurnaceFuelBurnTimeEvent;

@EventBusSubscriber(modid = DarkerDepths.MOD_ID)
public class FuelEvents {

    @SubscribeEvent
    public static void onFuelBurnTime(FurnaceFuelBurnTimeEvent event) {
        if (event.getItemStack().is(DDBlocks.SCORCHED_REMAINS_BLOCK.asItem())) {
            event.setBurnTime(11200);
        } else if (event.getItemStack().is(DDBlocks.SCORCHED_REMAINS.asItem())) {
            event.setBurnTime(2000);
        }
    }
}