package com.naterbobber.darkerdepths.core.registries;

import com.naterbobber.darkerdepths.common.entities.GlowshroomMonsterEntity;
import com.naterbobber.darkerdepths.common.entities.PetrifiedBoatEntity;
import com.naterbobber.darkerdepths.common.items.DDBoatItem;
import com.naterbobber.darkerdepths.common.items.DDSpawnEggItem;
import com.naterbobber.darkerdepths.common.items.GlowshroomCap;
import com.naterbobber.darkerdepths.common.items.RopeItem;
import com.naterbobber.darkerdepths.core.DarkerDepths;
import com.naterbobber.darkerdepths.core.RegistryHelper;

import com.naterbobber.darkerdepths.core.init.EntityTypesInit;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;

//<>

@Mod.EventBusSubscriber(modid = DarkerDepths.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DDItems {
	public static final RegistryHelper HELPER = DarkerDepths.REGISTRY_HELPER;
	
	//decoration
	public static final RegistryObject<Item> GLOWSHROOM_CAP 	= HELPER.createItem("glowshroom_cap", () -> new GlowshroomCap(DDBlocks.GLOWSHROOM_CAP.get(), new Item.Properties().group(DarkerDepths.DARKER_DEPTHS)));
	
	//tools
	public static final RegistryObject<Item> ROPE 				= HELPER.createItem("rope", () -> new RopeItem(DDBlocks.ROPE.get(), new Item.Properties().group(DarkerDepths.DARKER_DEPTHS)));
	
	//miscellaneous
	public static final RegistryObject<Item> SILVER_INGOT 		= HELPER.createItem("silver_ingot", () -> new Item(new Item.Properties().group(DarkerDepths.DARKER_DEPTHS)));
//	public static final RegistryObject<Item> SPAWNER_FRAGMENT 	= HELPER.createItem("spawner_fragment", () -> new Item(new Item.Properties().group(DarkerDepths.DARKER_DEPTHS)));
	public static final RegistryObject<Item> GLOWSHROOM_MONSTER_SPAWN_EGG = HELPER.createItem("glowshroom_monster_spawn_egg", () -> new DDSpawnEggItem(EntityTypesInit.GLOWSHROOM_MONSTER, 4948564, 4443806, new Item.Properties().group(DarkerDepths.DARKER_DEPTHS)));
	public static final RegistryObject<Item> MAGMA_MINION_SPAWN_EGG		  = HELPER.createItem("magma_minion_spawn_egg", () -> new DDSpawnEggItem(EntityTypesInit.MAGMA_MINION, 9261824, 15453980, new Item.Properties().group(DarkerDepths.DARKER_DEPTHS)));

	//transport
	public static final RegistryObject<Item> PETRIFIED_BOAT 	= HELPER.createItem("petrified_boat", () -> new DDBoatItem(PetrifiedBoatEntity.Type.Petrified, new Item.Properties().maxStackSize(1).group(DarkerDepths.DARKER_DEPTHS)));
}