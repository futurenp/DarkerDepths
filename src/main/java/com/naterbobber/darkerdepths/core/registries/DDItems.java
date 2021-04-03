package com.naterbobber.darkerdepths.core.registries;

import com.blackgear.bgcore.core.CoreRegistries;
import com.naterbobber.darkerdepths.common.entities.PetrifiedBoatEntity;
import com.naterbobber.darkerdepths.common.items.*;
import com.naterbobber.darkerdepths.core.DarkerDepths;

import com.naterbobber.darkerdepths.core.init.EntityTypesInit;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.SoupItem;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;

//<>

@Mod.EventBusSubscriber(modid = DarkerDepths.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DDItems {
	public static final CoreRegistries HELPER = DarkerDepths.REGISTRY_HELPER;
	
	//decoration
	public static final RegistryObject<Item> GLOWSHROOM_CAP 	= HELPER.registerItem("glowshroom_cap", () -> new GlowshroomCap(DDBlocks.GLOWSHROOM_CAP.get(), new Item.Properties().group(DarkerDepths.DARKER_DEPTHS)));
	
	//tools
	public static final RegistryObject<Item> ROPE 				= HELPER.registerItem("rope", () -> new RopeItem(DDBlocks.ROPE.get(), new Item.Properties().group(DarkerDepths.DARKER_DEPTHS)));
	
	//miscellaneous
	public static final RegistryObject<Item> SILVER_INGOT 		= HELPER.registerItem("silver_ingot", () -> new Item(new Item.Properties().group(DarkerDepths.DARKER_DEPTHS)));
//	public static final RegistryObject<Item> SPAWNER_FRAGMENT 	= HELPER.registerItem("spawner_fragment", () -> new Item(new Item.Properties().group(DarkerDepths.DARKER_DEPTHS)));
	public static final RegistryObject<Item> GLOWSHROOM_MONSTER_SPAWN_EGG = HELPER.registerItem("glowshroom_monster_spawn_egg", () -> new DDSpawnEggItem(EntityTypesInit.GLOWSHROOM_MONSTER, 4948564, 4443806, new Item.Properties().group(DarkerDepths.DARKER_DEPTHS)));
	public static final RegistryObject<Item> MAGMA_MINION_SPAWN_EGG		  = HELPER.registerItem("magma_minion_spawn_egg", () -> new DDSpawnEggItem(EntityTypesInit.MAGMA_MINION, 9261824, 15453980, new Item.Properties().group(DarkerDepths.DARKER_DEPTHS)));

	//food
	public static final RegistryObject<Item> GLOWSHROOM_STEW	= HELPER.registerItem("glowshroom_stew", () -> new SoupItem(new Item.Properties().maxStackSize(1).food(new Food.Builder().hunger(6).saturation(0.6F).build()).group(DarkerDepths.DARKER_DEPTHS)));

	//transport
	public static final RegistryObject<Item> PETRIFIED_BOAT 	= HELPER.registerItem("petrified_boat", () -> new DDBoatItem(PetrifiedBoatEntity.Type.Petrified, new Item.Properties().maxStackSize(1).group(DarkerDepths.DARKER_DEPTHS)));
}