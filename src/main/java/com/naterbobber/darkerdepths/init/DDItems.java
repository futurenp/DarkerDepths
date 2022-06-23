package com.naterbobber.darkerdepths.init;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.entities.PetrifiedBoatEntity;
import com.naterbobber.darkerdepths.item.DDBoatItem;
import com.naterbobber.darkerdepths.item.GlowshroomCapItem;
import com.naterbobber.darkerdepths.item.RopeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SignItem;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = DarkerDepths.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DDItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, DarkerDepths.MODID);

    public static final RegistryObject<Item> ROPE = ITEMS.register("rope", () -> new RopeItem(DDBlocks.ROPE.get(), new Item.Properties().tab(DarkerDepths.DARKER_DEPTHS)));
    public static final RegistryObject<Item> GLOWSHROOM_CAP = ITEMS.register("glowshroom_cap", () -> new GlowshroomCapItem(new Item.Properties().stacksTo(1).tab(DarkerDepths.DARKER_DEPTHS)));
    public static final RegistryObject<Item> PETRIFIED_BOAT = ITEMS.register("petrified_boat", () -> new DDBoatItem(false, PetrifiedBoatEntity.BoatType.PETRIFIED, new Item.Properties().stacksTo(1).tab(DarkerDepths.DARKER_DEPTHS)));
    public static final RegistryObject<Item> PETRIFIED_CHEST_BOAT = ITEMS.register("petrified_chest_boat", () -> new DDBoatItem(true, PetrifiedBoatEntity.BoatType.PETRIFIED, new Item.Properties().stacksTo(1).tab(DarkerDepths.DARKER_DEPTHS)));
    public static final RegistryObject<Item> GLOWSHROOM_MONSTER_SPAWN_EGG = ITEMS.register("glowshroom_monster_spawn_egg", () -> new ForgeSpawnEggItem(DDEntityTypes.GLOWSHROOM_MONSTER, 8290688, 8513702, new Item.Properties().tab(DarkerDepths.DARKER_DEPTHS)));
    public static final RegistryObject<Item> PETRIFIED_SIGN = ITEMS.register("petrified_sign", () -> new SignItem(new Item.Properties().stacksTo(16).tab(DarkerDepths.DARKER_DEPTHS), DDBlocks.PETRIFIED_SIGN.get(), DDBlocks.PETRIFIED_WALL_SIGN.get()));
    public static final RegistryObject<Item> RESIN = createSimpleItem("resin");
    public static final RegistryObject<Item> RAW_SILVER = createItem("raw_silver");
    public static final RegistryObject<Item> SILVER_INGOT = createItem("silver_ingot");

    private static RegistryObject<Item> createItem(String name) {
        return ITEMS.register(name, () -> new Item(new Item.Properties()));
    }

    private static RegistryObject<Item> createSimpleItem(String name) {
        return ITEMS.register(name, () -> new Item(new Item.Properties().tab(DarkerDepths.DARKER_DEPTHS)));
    }

}
