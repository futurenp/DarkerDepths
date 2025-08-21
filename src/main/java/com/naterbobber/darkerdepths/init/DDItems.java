package com.naterbobber.darkerdepths.init;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.entities.PetrifiedBoatEntity;
import com.naterbobber.darkerdepths.item.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.PlaceOnWaterBlockItem;
import net.minecraft.world.item.SignItem;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = DarkerDepths.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DDItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, DarkerDepths.MODID);
    public static final RegistryObject<Item> GLOWSHROOM_CAP = ITEMS.register("glowshroom_cap",
            () -> new GlowshroomCapItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> GLOW_GRIME = createSimpleItem("glow_grime");
    public static final RegistryObject<Item> ROPE = ITEMS.register("rope",
            () -> new RopeItem(DDBlocks.ROPE.get(), new Item.Properties()));
    //public static final RegistryObject<Item> QUICKROPE = ITEMS.register("quickrope",
    // () -> new QuickRopeItem(new Item.Properties().durability(16)));
    public static final RegistryObject<Item> MAGMA_PAD = ITEMS.register("magma_pad",
            () -> new PlaceOnWaterBlockItem(DDBlocks.MAGMA_PAD.get(), new Item.Properties()));
    public static final RegistryObject<Item> AMBER = createSimpleItem("amber");
    public static final RegistryObject<Item> VOID_SOUL_REQUIEM = createSimpleItem("void_soul_requiem");
    public static final RegistryObject<Item> FORSAKEN_BRONZE_SCRAP = createSimpleItem("forsaken_bronze_scrap");
    public static final RegistryObject<Item> STILETTO = ITEMS.register("stiletto",
            () -> new StilettoItem(2, -1.6F, new Item.Properties()));
    public static final RegistryObject<Item> VOID_SOUL_TORCH = ITEMS.register("void_soul_torch",
            () -> new VoidSoulTorchItem(DDBlocks.VOID_SOUL_TORCH.get(), DDBlocks.WALL_VOID_SOUL_TORCH.get(), new Item.Properties()));
    public static final RegistryObject<Item> GLOWSHROOM_MONSTER_SPAWN_EGG = ITEMS.register("glowshroom_monster_spawn_egg",
            () -> new ForgeSpawnEggItem(DDEntityTypes.GLOWSHROOM_MONSTER, 8290688, 8513702, new Item.Properties()));
    public static final RegistryObject<Item> BODY_SNATCHER_SPAWN_EGG = ITEMS.register("body_snatcher_spawn_egg",
            () -> new ForgeSpawnEggItem(DDEntityTypes.BODY_SNATCHER, 2496017, 16775339, new Item.Properties()));
    public static final RegistryObject<Item> VOID_SOUL_KNIGHT_SPAWN_EGG = ITEMS.register("void_soul_knight_spawn_egg",
            () -> new ForgeSpawnEggItem(DDEntityTypes.VOID_SOUL_KNIGHT, 12425849, 851969, new Item.Properties()));
    public static final RegistryObject<Item> PETRIFIED_BOAT = ITEMS.register("petrified_boat",
            () -> new DDBoatItem(false, PetrifiedBoatEntity.BoatType.PETRIFIED, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> PETRIFIED_CHEST_BOAT = ITEMS.register("petrified_chest_boat",
            () -> new DDBoatItem(true, PetrifiedBoatEntity.BoatType.PETRIFIED, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> PETRIFIED_SIGN = ITEMS.register("petrified_sign",
            () -> new SignItem(new Item.Properties().stacksTo(16), DDBlocks.PETRIFIED_SIGN.get(), DDBlocks.PETRIFIED_WALL_SIGN.get()));
    public static final RegistryObject<Item> PARANOIA_ALTAR = ITEMS.register("paranoia_altar",
            () -> new ParanoiaAltarItem(DDBlocks.PARANOIA_ALTAR.get(), new Item.Properties()));

    private static RegistryObject<Item> createSimpleItem(String name) {
        return ITEMS.register(name, () -> new Item(new Item.Properties()));
    }

}
