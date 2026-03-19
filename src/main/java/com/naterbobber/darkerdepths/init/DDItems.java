package com.naterbobber.darkerdepths.init;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.entities.PetrifiedBoatEntity;
import com.naterbobber.darkerdepths.item.*;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.Tool;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;

public class DDItems {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(DarkerDepths.MOD_ID);
//    private static final Boat.Type PETRIFIED_BOAT_TYPE = Boat.Type.byName("PETRIFIED");

    public static final DeferredItem<GlowshroomCapItem> GLOWSHROOM_CAP = ITEMS.register("glowshroom_cap",
            () -> new GlowshroomCapItem(new Item.Properties().stacksTo(1).durability(ArmorItem.Type.HELMET.getDurability(156))));
    public static final DeferredItem<Item> GLOW_GRIME = createSimpleItem("glow_grime");
    public static final DeferredItem<RopeItem> ROPE = ITEMS.register("rope",
            () -> new RopeItem(DDBlocks.ROPE.get(), new Item.Properties()));
    //    public static final DeferredItem<QuickRopeItem> QUICKROPE = ITEMS.register("quickrope",
//     () -> new QuickRopeItem(new Item.Properties().maxDamage(16)));
    public static final DeferredItem<PlaceOnWaterBlockItem> MAGMA_PAD = ITEMS.register("magma_pad",
            () -> new PlaceOnWaterBlockItem(DDBlocks.MAGMA_PAD.get(), new Item.Properties()));
    public static final DeferredItem<Item> AMBER = createSimpleItem("amber");
    public static final DeferredItem<Item> VOID_SOUL_REQUIEM = createSimpleItem("void_soul_requiem");
    public static final DeferredItem<Item> FORSAKEN_BRONZE_SCRAP = createSimpleItem("forsaken_bronze_scrap");
    public static final DeferredItem<Item> FORSAKEN_BRONZE_INGOT = createSimpleItem("forsaken_bronze_ingot");
//    public static final DeferredItem<StilettoItem> STILETTO = ITEMS.register("stiletto",
//            () -> new StilettoItem(2, -1.6F, new Item.Properties()));
    public static final DeferredItem<StilettoItem> STILETTO = ITEMS.register("stiletto",
            () -> new StilettoItem(new Item.Properties()
                    .component(DataComponents.TOOL, new Tool(
                            List.of(
                                    Tool.Rule.minesAndDrops(List.of(Blocks.COBWEB), 15.0f),
                                    Tool.Rule.overrideSpeed(BlockTags.SWORD_EFFICIENT, 1.5f)
                            ),
                            1.0f,
                            2
                            )
                    )
                    .component(DataComponents.ATTRIBUTE_MODIFIERS, SwordItem.createAttributes(
                            StilettoItem.STILETTO,
                            2,
                            -1.6f
                    ))
            )
    );

    public static final DeferredItem<StandingAndWallBlockItem> VOID_SOUL_TORCH = ITEMS.register("void_soul_torch",
            () -> new StandingAndWallBlockItem(DDBlocks.VOID_SOUL_TORCH.get(), DDBlocks.WALL_VOID_SOUL_TORCH.get(), new Item.Properties(), Direction.DOWN));
    public static final DeferredItem<SpawnEggItem> GLOWSHROOM_MONSTER_SPAWN_EGG = ITEMS.register("glowshroom_monster_spawn_egg",
            () -> new SpawnEggItem(DDEntityTypes.GLOWSHROOM_MONSTER.get(), 8290688, 8513702, new Item.Properties()));
    public static final DeferredItem<SpawnEggItem> BODY_SNATCHER_SPAWN_EGG = ITEMS.register("body_snatcher_spawn_egg",
            () -> new SpawnEggItem(DDEntityTypes.BODY_SNATCHER.get(), 5058592, 16757097, new Item.Properties()));
    public static final DeferredItem<SpawnEggItem> VOID_SOUL_KNIGHT_SPAWN_EGG = ITEMS.register("void_soul_knight_spawn_egg",
            () -> new SpawnEggItem(DDEntityTypes.VOID_SOUL_KNIGHT.get(), 3542019, 16757097, new Item.Properties()));
    public static final DeferredItem<SpawnEggItem> VOID_SOUL_SPAWN_EGG = ITEMS.register("void_soul_spawn_egg",
            () -> new SpawnEggItem(DDEntityTypes.VOID_SOUL.get(), 0, 16749117, new Item.Properties()));
    public static final DeferredItem<SpawnEggItem> SCORCHER_SPAWN_EGG = ITEMS.register("scorcher_spawn_egg",
            () -> new SpawnEggItem(DDEntityTypes.SCORCHER.get(), 16228419, 16777215, new Item.Properties()));
    public static final DeferredItem<DDBoatItem> PETRIFIED_BOAT = ITEMS.register("petrified_boat",
            () -> new DDBoatItem(false, PetrifiedBoatEntity.BoatType.PETRIFIED, new Item.Properties().stacksTo(1)));
    public static final DeferredItem<DDBoatItem> PETRIFIED_CHEST_BOAT = ITEMS.register("petrified_chest_boat",
            () -> new DDBoatItem(true, PetrifiedBoatEntity.BoatType.PETRIFIED, new Item.Properties().stacksTo(1)));
    public static final DeferredItem<SignItem> PETRIFIED_SIGN = ITEMS.register("petrified_sign",
            () -> new SignItem(new Item.Properties().stacksTo(16), DDBlocks.PETRIFIED_SIGN.get(), DDBlocks.PETRIFIED_WALL_SIGN.get()));
    public static final DeferredItem<HangingSignItem> PETRIFIED_HANGING_SIGN = ITEMS.register("petrified_hanging_sign",
            () -> new HangingSignItem(DDBlocks.PETRIFIED_HANGING_SIGN.get(), DDBlocks.PETRIFIED_WALL_HANGING_SIGN.get(), new Item.Properties().stacksTo(16)));

    public static final DeferredItem<ParanoiaAltarItem> PARANOIA_ALTAR = ITEMS.register("paranoia_altar",
            () -> new ParanoiaAltarItem(DDBlocks.PARANOIA_ALTAR.get(), new Item.Properties()));
    public static final DeferredItem<VoidSoulJarItem> VOID_SOUL_JAR = ITEMS.register("void_soul_jar",
            () -> new VoidSoulJarItem(DDBlocks.VOID_SOUL_JAR.get(), new Item.Properties()));

    private static DeferredItem<Item> createSimpleItem(String name) {
        return ITEMS.register(name, () -> new Item(new Item.Properties()));
    }
}
