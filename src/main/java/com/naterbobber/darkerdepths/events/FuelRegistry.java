package com.naterbobber.darkerdepths.events;

import com.naterbobber.darkerdepths.DarkerDepths;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DarkerDepths.MOD_ID)
public class FuelRegistry {
    private static final Object2IntMap<Item> FUELS = new Object2IntOpenHashMap<>();

    public static void register(Item item, int burnTime) {
        FUELS.put(item, burnTime);
    }

    @SubscribeEvent
    public static void onFuelBurnTimeEvent(FurnaceFuelBurnTimeEvent event) {
        var stack = event.getItemStack();
        if(stack.isEmpty()) return;
        if(!FUELS.containsKey(stack.getItem())) return;
        var burnTime = FUELS.getInt(stack.getItem());
        if(burnTime <= 0) return;
        event.setBurnTime(burnTime);
    }
}
