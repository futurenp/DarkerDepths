package com.naterbobber.darkerdepths.events;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.crafting.DDBoatDispenseItemBehavior;
import com.naterbobber.darkerdepths.init.DDItems;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraftforge.event.TagsUpdatedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DarkerDepths.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class MiscEvents {

    @SubscribeEvent
    public void onTagsUpdated(TagsUpdatedEvent event) {
        DispenserBlock.registerBehavior(DDItems.PETRIFIED_BOAT.get(), new DDBoatDispenseItemBehavior());
    }

}
