package com.naterbobber.darkerdepths.core.registries;

import com.naterbobber.darkerdepths.common.entities.PetrifiedBoatEntity;
import com.naterbobber.darkerdepths.common.items.DDBoatItem;
import com.naterbobber.darkerdepths.common.items.RopeItem;
import com.naterbobber.darkerdepths.core.DarkerDepths;
import com.naterbobber.darkerdepths.core.RegistryHelper;

import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;

//<>

@Mod.EventBusSubscriber(modid = DarkerDepths.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DDItems {
	public static final RegistryHelper HELPER = DarkerDepths.REGISTRY_HELPER;
	
	//tools
	public static final RegistryObject<Item> ROPE 				= HELPER.createItem("rope", () -> new RopeItem(DDBlocks.ROPE.get(), new Item.Properties().group(DarkerDepths.DARKER_DEPTHS)));
	
	//miscellaneous
	public static final RegistryObject<Item> SILVER_INGOT 		= HELPER.createItem("silver_ingot", () -> new Item(new Item.Properties().group(DarkerDepths.DARKER_DEPTHS)));
	public static final RegistryObject<Item> SPAWNER_FRAGMENT 	= HELPER.createItem("spawner_fragment", () -> new Item(new Item.Properties().group(DarkerDepths.DARKER_DEPTHS)));
	public static final RegistryObject<Item> ELYTRINE_CRYSTAL 	= HELPER.createItem("elytrine_crystal", () -> new Item(new Item.Properties().group(DarkerDepths.DARKER_DEPTHS)));
	
	//transport
	public static final RegistryObject<Item> PETRIFIED_BOAT 	= HELPER.createItem("petrified_boat", () -> new DDBoatItem(PetrifiedBoatEntity.Type.Petrified, new Item.Properties().maxStackSize(1).group(DarkerDepths.DARKER_DEPTHS)));
}