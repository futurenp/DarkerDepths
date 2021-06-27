package com.naterbobber.darkerdepths.core.registries;

import com.naterbobber.darkerdepths.common.entities.PetrifiedBoatEntity;
import com.naterbobber.darkerdepths.common.items.*;
import com.naterbobber.darkerdepths.core.DDRegistries;
import com.naterbobber.darkerdepths.core.DarkerDepths;

import net.minecraft.entity.EntityType;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SignItem;
import net.minecraft.item.SoupItem;
import net.minecraft.item.SpawnEggItem;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;

//<>

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class DDItems {
	public static final DDRegistries HELPER = DarkerDepths.REGISTRIES;

	public static final RegistryObject<Item> ROPE 				          = HELPER.registerItem("rope", () -> new RopeItem(DDBlocks.ROPE.get(), new Item.Properties().group(DarkerDepths.DARKER_DEPTHS)));
	public static final RegistryObject<Item> GLOWSHROOM_CAP		          = HELPER.registerItem("glowshroom_cap", () -> new GlowshroomCap(new Item.Properties().maxStackSize(1).group(DarkerDepths.DARKER_DEPTHS)));
	public static final RegistryObject<Item> PETRIFIED_SIGN		          = HELPER.registerItem("petrified_sign", () -> new SignItem(new Item.Properties().maxStackSize(16).group(DarkerDepths.DARKER_DEPTHS), DDBlocks.PETRIFIED_SIGN.get(), DDBlocks.PETRIFIED_WALL_SIGN.get()));

	//MISCELLANEOUS
	public static final RegistryObject<Item> RESIN						  = HELPER.registerItem("resin", () -> new Item(new Item.Properties().group(DarkerDepths.DARKER_DEPTHS)));
	public static final RegistryObject<Item> SILVER_INGOT 				  = HELPER.registerItem("silver_ingot", () -> new Item(new Item.Properties().group(DarkerDepths.DARKER_DEPTHS)));
	public static final RegistryObject<Item> SPAWNER_FRAGMENT 			  = HELPER.registerItem("spawner_fragment", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> GLOWSHROOM_MONSTER_SPAWN_EGG = HELPER.registerItem("glowshroom_monster_spawn_egg", () -> new DDSpawnEggItem(DDEntityTypes.GLOWSHROOM_MONSTER, 4948564, 4443806, new Item.Properties().group(DarkerDepths.DARKER_DEPTHS)));
	public static final RegistryObject<Item> MAGMA_MINION_SPAWN_EGG		  = HELPER.registerItem("magma_minion_spawn_egg", () -> new DDSpawnEggItem(DDEntityTypes.MAGMA_MINION, 9261824, 15453980, new Item.Properties().group(DarkerDepths.DARKER_DEPTHS)));

	//FOOD
	public static final RegistryObject<Item> GLOWSHROOM_STEW			  = HELPER.registerItem("glowshroom_stew", () -> new SoupItem(new Item.Properties().maxStackSize(1).food(new Food.Builder().hunger(5).saturation(0.6F).build())));

	//TRANSPORT
	public static final RegistryObject<Item> PETRIFIED_BOAT 			  = HELPER.registerItem("petrified_boat", () -> new DDBoatItem(PetrifiedBoatEntity.Type.Petrified, new Item.Properties().maxStackSize(1).group(DarkerDepths.DARKER_DEPTHS)));
}