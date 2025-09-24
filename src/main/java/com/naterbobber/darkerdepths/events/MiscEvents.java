package com.naterbobber.darkerdepths.events;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.crafting.DDBoatDispenseItemBehavior;
import com.naterbobber.darkerdepths.init.DDItems;
import net.minecraft.world.level.block.DispenserBlock;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.TagsUpdatedEvent;

@EventBusSubscriber(modid = DarkerDepths.MOD_ID)
public class MiscEvents {

    @SubscribeEvent
    public void onTagsUpdated(TagsUpdatedEvent event) {
        DispenserBlock.registerBehavior(DDItems.PETRIFIED_BOAT.get(), new DDBoatDispenseItemBehavior());
        DispenserBlock.registerBehavior(DDItems.PETRIFIED_CHEST_BOAT.get(), new DDBoatDispenseItemBehavior(true));
    }
}
