package com.naterbobber.darkerdepths.init;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.entities.PetrifiedBoatEntity;
import com.naterbobber.darkerdepths.item.*;
import net.minecraft.core.Direction;
import net.minecraft.world.item.*;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class DDItems {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(DarkerDepths.MOD_ID);

    public static final DeferredHolder<Item, GlowshroomCapItem> GLOWSHROOM_CAP = ITEMS.register("glowshroom_cap",
            () -> new GlowshroomCapItem(new Item.Properties().stacksTo(1)));
    public static final DeferredHolder<Item, Item> GLOW_GRIME = createSimpleItem("glow_grime");
    public static final DeferredHolder<Item, RopeItem> ROPE = ITEMS.register("rope",
            () -> new RopeItem(DDBlocks.ROPE.get(), new Item.Properties()));
    //    public static final DeferredHolder<Item, QuickRopeItem> QUICKROPE = ITEMS.register("quickrope",
//     () -> new QuickRopeItem(new Item.Properties().maxDamage(16)));
//    public static final DeferredHolder<Item, BlockItem> MAGMA_PAD = ITEMS.register("magma_pad",
//            () -> new BlockItem(DDBlocks.MAGMA_PAD.get(), new Item.Properties())); // Note: PlaceOnWaterBlockItem is removed
    public static final DeferredHolder<Item, Item> AMBER = createSimpleItem("amber");
    public static final DeferredHolder<Item, Item> VOID_SOUL_REQUIEM = createSimpleItem("void_soul_requiem");
    public static final DeferredHolder<Item, Item> FORSAKEN_BRONZE_SCRAP = createSimpleItem("forsaken_bronze_scrap");
    public static final DeferredHolder<Item, Item> FORSAKEN_BRONZE_INGOT = createSimpleItem("forsaken_bronze_ingot");
    public static final DeferredHolder<Item, StilettoItem> STILETTO = ITEMS.register("stiletto",
            () -> new StilettoItem(2, -1.6F, new Item.Properties()));
    public static final DeferredHolder<Item, StandingAndWallBlockItem> VOID_SOUL_TORCH = ITEMS.register("void_soul_torch",
            () -> new StandingAndWallBlockItem(DDBlocks.VOID_SOUL_TORCH.get(), DDBlocks.WALL_VOID_SOUL_TORCH.get(), new Item.Properties(), Direction.DOWN));
    public static final DeferredHolder<Item, SpawnEggItem> GLOWSHROOM_MONSTER_SPAWN_EGG = ITEMS.register("glowshroom_monster_spawn_egg",
            () -> new SpawnEggItem(DDEntityTypes.GLOWSHROOM_MONSTER.get(), 8290688, 8513702, new Item.Properties()));
    public static final DeferredHolder<Item, SpawnEggItem> BODY_SNATCHER_SPAWN_EGG = ITEMS.register("body_snatcher_spawn_egg",
            () -> new SpawnEggItem(DDEntityTypes.BODY_SNATCHER.get(), 5058592, 16757097, new Item.Properties()));
    public static final DeferredHolder<Item, SpawnEggItem> VOID_SOUL_KNIGHT_SPAWN_EGG = ITEMS.register("void_soul_knight_spawn_egg",
            () -> new SpawnEggItem(DDEntityTypes.VOID_SOUL_KNIGHT.get(), 3542019, 16757097, new Item.Properties()));
    public static final DeferredHolder<Item, SpawnEggItem> VOID_SOUL_SPAWN_EGG = ITEMS.register("void_soul_spawn_egg",
            () -> new SpawnEggItem(DDEntityTypes.VOID_SOUL.get(), 0, 16749117, new Item.Properties()));
    public static final DeferredHolder<Item, DDBoatItem> PETRIFIED_BOAT = ITEMS.register("petrified_boat",
            () -> new DDBoatItem(false, PetrifiedBoatEntity.BoatType.PETRIFIED, new Item.Properties().stacksTo(1)));
    public static final DeferredHolder<Item, DDBoatItem> PETRIFIED_CHEST_BOAT = ITEMS.register("petrified_chest_boat",
            () -> new DDBoatItem(true, PetrifiedBoatEntity.BoatType.PETRIFIED, new Item.Properties().stacksTo(1)));
    public static final DeferredHolder<Item, SignItem> PETRIFIED_SIGN = ITEMS.register("petrified_sign",
            () -> new SignItem(new Item.Properties().stacksTo(16), DDBlocks.PETRIFIED_SIGN.get(), DDBlocks.PETRIFIED_WALL_SIGN.get()));
    public static final DeferredHolder<Item, HangingSignItem> PETRIFIED_HANGING_SIGN = ITEMS.register("petrified_hanging_sign",
            () -> new HangingSignItem(DDBlocks.PETRIFIED_HANGING_SIGN.get(), DDBlocks.PETRIFIED_WALL_HANGING_SIGN.get(), new Item.Properties().stacksTo(16)));

    public static final DeferredHolder<Item, ParanoiaAltarItem> PARANOIA_ALTAR = ITEMS.register("paranoia_altar",
            () -> new ParanoiaAltarItem(DDBlocks.PARANOIA_ALTAR.get(), new Item.Properties()));
    public static final DeferredHolder<Item, VoidSoulJarItem> VOID_SOUL_JAR = ITEMS.register("void_soul_jar",
            () -> new VoidSoulJarItem(DDBlocks.VOID_SOUL_JAR.get(), new Item.Properties()));

    private static DeferredHolder<Item, Item> createSimpleItem(String name) {
        return ITEMS.register(name, () -> new Item(new Item.Properties()));
    }
}
