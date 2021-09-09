package com.naterbobber.darkerdepths.core.registries;

import com.naterbobber.darkerdepths.common.entities.PetrifiedBoatEntity;
import com.naterbobber.darkerdepths.common.items.DDBoatItem;
import com.naterbobber.darkerdepths.common.items.DDSpawnEggItem;
import com.naterbobber.darkerdepths.common.items.GlowshroomCapItem;
import com.naterbobber.darkerdepths.common.items.RopeItem;
import com.naterbobber.darkerdepths.core.api.Registries;
import com.naterbobber.darkerdepths.core.DarkerDepths;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SignItem;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;

//<>

@Mod.EventBusSubscriber(modid = DarkerDepths.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DDItems {
	public static final Registries HELPER = DarkerDepths.REGISTRIES;

	public static final RegistryObject<Item> ROPE 				          = HELPER.registerItem("rope", () -> new RopeItem(DDBlocks.ROPE.get(), new Item.Properties().group(DarkerDepths.DARKER_DEPTHS)));
	public static final RegistryObject<Item> GLOWSHROOM_CAP		          = HELPER.registerItem("glowshroom_cap", () -> new GlowshroomCapItem(new Item.Properties().maxStackSize(1).group(DarkerDepths.DARKER_DEPTHS)));
	public static final RegistryObject<Item> PETRIFIED_SIGN		          = HELPER.registerItem("petrified_sign", () -> new SignItem(new Item.Properties().maxStackSize(16).group(DarkerDepths.DARKER_DEPTHS), DDBlocks.PETRIFIED_SIGN.get(), DDBlocks.PETRIFIED_WALL_SIGN.get()));

	//MISCELLANEOUS
	public static final RegistryObject<Item> RESIN						  = HELPER.registerItem("resin", () -> new Item(new Item.Properties().group(DarkerDepths.DARKER_DEPTHS)));
	public static final RegistryObject<Item> RAW_SILVER					  = HELPER.registerItem("raw_silver", () -> new Item(new Item.Properties().group(DarkerDepths.DARKER_DEPTHS)));
	public static final RegistryObject<Item> SILVER_INGOT 				  = HELPER.registerItem("silver_ingot", () -> new Item(new Item.Properties().group(DarkerDepths.DARKER_DEPTHS)));

//	public static final RegistryObject<Item> SPAWNER_FRAGMENT 			  = HELPER.registerItem("spawner_fragment", () -> new Item(new Item.Properties().group(ItemGroup.MISC)));

	public static final RegistryObject<Item> GLOWSHROOM_MONSTER_SPAWN_EGG = HELPER.registerItem("glowshroom_monster_spawn_egg", () -> new DDSpawnEggItem(DDEntityTypes.GLOWSHROOM_MONSTER, 8290688, 8513702, new Item.Properties().group(DarkerDepths.DARKER_DEPTHS)));
	public static final RegistryObject<Item> MAGMA_MINION_SPAWN_EGG		  = HELPER.registerItem("magma_minion_spawn_egg", () -> new DDSpawnEggItem(DDEntityTypes.MAGMA_MINION, 6448235, 16760916, new Item.Properties().group(DarkerDepths.DARKER_DEPTHS)));

	//TRANSPORT
	public static final RegistryObject<Item> PETRIFIED_BOAT 			  = HELPER.registerItem("petrified_boat", () -> new DDBoatItem(PetrifiedBoatEntity.Type.Petrified, new Item.Properties().maxStackSize(1).group(DarkerDepths.DARKER_DEPTHS)));
}