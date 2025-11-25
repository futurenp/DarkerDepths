package com.naterbobber.darkerdepths.init;

import com.google.common.collect.Maps;
import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.block.DDBlockSetTypes;
import com.naterbobber.darkerdepths.block.custom.*;
import com.naterbobber.darkerdepths.block.generic.*;
import com.naterbobber.darkerdepths.item.BlockItemWithHoverText;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class DDBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(DarkerDepths.MOD_ID);
    public static final Map<DeferredItem<? extends Item>, List<String>> COMPAT = Maps.newLinkedHashMap();

    public static final BlockBehaviour.Properties PETRIFIED_LOG_PROPERTIES =
            blockProperties(2.4f, 3.0f, SoundType.STEM, true);
    public static final BlockBehaviour.Properties PETRIFIED_PLANKS_PROPERTIES =
            blockProperties(2.5f, 3.0f, SoundType.WOOD, true);
    public static final BlockBehaviour.Properties PETRIFIED_BUTTON_PROPERTIES =
            blockProperties(0.5f, SoundType.WOOD, true);
    public static final BlockBehaviour.Properties PETRIFIED_SIGN_PROPERTIES =
            blockProperties(1.0f, SoundType.WOOD, true).noCollission();
    public static final BlockBehaviour.Properties DARKSLATE_PROPERTIES =
            blockProperties(3.5f, 6.5f, SoundType.DEEPSLATE, true);
    public static final BlockBehaviour.Properties DARKSLATE_BRICKS_PROPERTIES =
            blockProperties(4.0f, 7.5f, SoundType.DEEPSLATE_BRICKS, true);
    public static final BlockBehaviour.Properties ARIDROCK_PROPERTIES =
            blockProperties(1.5f, 6.0f, SoundType.STONE, true).mapColor(MapColor.SAND);
    public static final BlockBehaviour.Properties ARIDROCK_BRICKS_PROPERTIES =
            blockProperties(2.0f, 6.0f, SoundType.STONE, true).mapColor(MapColor.SAND);
    public static final BlockBehaviour.Properties DUSKROCK_PROPERTIES =
            blockProperties(1.25f, 4.0f, SoundType.TUFF, true);
    public static final BlockBehaviour.Properties DUSKROCK_BRICKS_PROPERTIES =
            blockProperties(1.5f, 5.0f, SoundType.STONE, true);
    public static final BlockBehaviour.Properties LIVING_CRYSTAL_PROPERTIES =
            blockProperties(4.0f, 7.5f, SoundType.DEEPSLATE, true).randomTicks();
    public static final BlockBehaviour.Properties FORSAKEN_BRONZE_PROPERTIES =
            blockProperties(8.0f, 15.0f, SoundType.NETHERITE_BLOCK, true);


    public static final DeferredBlock<RotatedPillarBlock> STRIPPED_PETRIFIED_LOG = registerBlock("stripped_petrified_log",
            () -> new RotatedPillarBlock(PETRIFIED_LOG_PROPERTIES));
    public static final DeferredBlock<RotatedPillarBlock> STRIPPED_PETRIFIED_WOOD = registerBlock("stripped_petrified_wood",
            () -> new RotatedPillarBlock(PETRIFIED_LOG_PROPERTIES));
    public static final DeferredBlock<RotatedPillarBlock> PETRIFIED_LOG = registerBlock("petrified_log",
            () -> new DDLogBlock(PETRIFIED_LOG_PROPERTIES, STRIPPED_PETRIFIED_LOG.get()));
    public static final DeferredBlock<RotatedPillarBlock> PETRIFIED_WOOD = registerBlock("petrified_wood",
            () -> new DDLogBlock(PETRIFIED_LOG_PROPERTIES, STRIPPED_PETRIFIED_WOOD.get()));
    public static final DeferredBlock<Block> PETRIFIED_PLANKS = registerBlock("petrified_planks",
            () -> new Block(PETRIFIED_PLANKS_PROPERTIES));
    public static final DeferredBlock<Block> VERTICAL_PETRIFIED_PLANKS = registerCompatBlock(List.of("quark"), "vertical_petrified_planks",
            () -> new Block(PETRIFIED_PLANKS_PROPERTIES));
    public static final DeferredBlock<RelationalStairBlock> PETRIFIED_STAIRS = registerBlock("petrified_stairs",
            () -> new RelationalStairBlock(PETRIFIED_PLANKS.get()));
    public static final DeferredBlock<RelationalSlabBlock> PETRIFIED_SLAB = registerBlock("petrified_slab",
            () -> new RelationalSlabBlock(PETRIFIED_PLANKS.get()));
    public static final DeferredBlock<VerticalSlabBlock> PETRIFIED_VERTICAL_SLAB = registerCompatBlock(List.of("quark"), "petrified_vertical_slab",
            () -> new VerticalSlabBlock(PETRIFIED_PLANKS_PROPERTIES));
    public static final DeferredBlock<RelationalFenceBlock> PETRIFIED_FENCE = registerBlock("petrified_fence",
            () -> new RelationalFenceBlock(PETRIFIED_PLANKS.get()));
    public static final DeferredBlock<RelationalFenceGateBlock> PETRIFIED_FENCE_GATE = registerBlock("petrified_fence_gate",
            () -> new RelationalFenceGateBlock(PETRIFIED_PLANKS.get(), DDWoodType.PETRIFIED));
    public static final DeferredBlock<DoorBlock> PETRIFIED_DOOR = registerBlock("petrified_door",
            () -> new DoorBlock(DDBlockSetTypes.PETRIFIED, PETRIFIED_PLANKS_PROPERTIES));
    public static final DeferredBlock<TrapDoorBlock> PETRIFIED_TRAPDOOR = registerBlock("petrified_trapdoor",
            () -> new TrapDoorBlock(DDBlockSetTypes.PETRIFIED, PETRIFIED_PLANKS_PROPERTIES));
    public static final DeferredBlock<RelationalPressurePlateBlock> PETRIFIED_PRESSURE_PLATE = registerBlock("petrified_pressure_plate",
            () -> new RelationalPressurePlateBlock(PETRIFIED_PLANKS.get(), DDBlockSetTypes.PETRIFIED));
    public static final DeferredBlock<RelationalButtonBlock> PETRIFIED_BUTTON = registerBlock("petrified_button",
            () -> new RelationalButtonBlock(PETRIFIED_PLANKS.get(), DDBlockSetTypes.PETRIFIED, 30));
    public static final DeferredBlock<DDStandingSignBlock> PETRIFIED_SIGN = registerNoTabBlock("petrified_sign",
            () -> new DDStandingSignBlock(DDWoodType.PETRIFIED, PETRIFIED_SIGN_PROPERTIES));
    public static final DeferredBlock<DDWallSignBlock> PETRIFIED_WALL_SIGN = registerNoTabBlock("petrified_wall_sign",
            () -> new DDWallSignBlock(PETRIFIED_SIGN_PROPERTIES, DDWoodType.PETRIFIED));
    public static final DeferredBlock<DDCeilingHangingSignBlock> PETRIFIED_HANGING_SIGN = registerNoTabBlock("petrified_hanging_sign",
            () -> new DDCeilingHangingSignBlock(DDWoodType.PETRIFIED, PETRIFIED_SIGN_PROPERTIES));
    public static final DeferredBlock<DDWallHangingSignBlock> PETRIFIED_WALL_HANGING_SIGN = registerNoTabBlock("petrified_wall_hanging_sign",
            () -> new DDWallHangingSignBlock(DDWoodType.PETRIFIED, PETRIFIED_SIGN_PROPERTIES));
    public static final DeferredBlock<WoodPostBlock> PETRIFIED_POST = registerCompatBlock(List.of("quark"), "petrified_post",
            () -> new WoodPostBlock(PETRIFIED_LOG_PROPERTIES.noOcclusion()));
    public static final DeferredBlock<WoodPostBlock> STRIPPED_PETRIFIED_POST = registerCompatBlock(List.of("quark"), "stripped_petrified_post",
            () -> new WoodPostBlock(PETRIFIED_LOG_PROPERTIES.noOcclusion()));
//    public static final DeferredBlock<Block> TRIMMED_PETRIFIED_PLANKS = registerCompatBlock(List.of("nomansland"), "trimmed_petrified_planks", CompatBlocks::createTrimmedPlanks);
    public static final DeferredBlock<PorousBlock> POROUS_PETRIFIED_LOG = registerBlock("porous_petrified_log",
            () -> new PorousBlock(PETRIFIED_LOG_PROPERTIES.randomTicks().lightLevel(value -> 6)));
    public static final DeferredBlock<RotatedPillarBlock> ARIDROCK = registerBlock("aridrock",
            () -> new RotatedPillarBlock(ARIDROCK_PROPERTIES));
    public static final DeferredBlock<RelationalStairBlock> ARIDROCK_STAIRS = registerBlock("aridrock_stairs",
            () -> new RelationalStairBlock(ARIDROCK.get()));
    public static final DeferredBlock<RelationalSlabBlock> ARIDROCK_SLAB = registerBlock("aridrock_slab",
            () -> new RelationalSlabBlock(ARIDROCK.get()));
    public static final DeferredBlock<VerticalSlabBlock> ARIDROCK_VERTICAL_SLAB = registerCompatBlock(List.of("quark"), "aridrock_vertical_slab",
            () -> new VerticalSlabBlock(ARIDROCK_PROPERTIES));
    public static final DeferredBlock<RelationalWallBlock> ARIDROCK_WALL = registerBlock("aridrock_wall",
            () -> new RelationalWallBlock(ARIDROCK.get()));
    public static final DeferredBlock<Block> POLISHED_ARIDROCK = registerBlock("polished_aridrock",
            () -> new Block(ARIDROCK_PROPERTIES));
    public static final DeferredBlock<RelationalStairBlock> POLISHED_ARIDROCK_STAIRS = registerBlock("polished_aridrock_stairs",
            () -> new RelationalStairBlock(POLISHED_ARIDROCK.get()));
    public static final DeferredBlock<RelationalSlabBlock> POLISHED_ARIDROCK_SLAB = registerBlock("polished_aridrock_slab",
            () -> new RelationalSlabBlock(POLISHED_ARIDROCK.get()));
    public static final DeferredBlock<VerticalSlabBlock> POLISHED_ARIDROCK_VERTICAL_SLAB = registerCompatBlock(List.of("quark"), "polished_aridrock_vertical_slab",
            () -> new VerticalSlabBlock(ARIDROCK_PROPERTIES));
    public static final DeferredBlock<Block> ARIDROCK_BRICKS = registerBlock("aridrock_bricks",
            () -> new Block(ARIDROCK_BRICKS_PROPERTIES));
    public static final DeferredBlock<RelationalStairBlock> ARIDROCK_BRICKS_STAIRS = registerBlock("aridrock_bricks_stairs",
            () -> new RelationalStairBlock(ARIDROCK_BRICKS.get()));
    public static final DeferredBlock<RelationalSlabBlock> ARIDROCK_BRICKS_SLAB = registerBlock("aridrock_bricks_slab",
            () -> new RelationalSlabBlock(ARIDROCK_BRICKS.get()));
    public static final DeferredBlock<VerticalSlabBlock> ARIDROCK_BRICKS_VERTICAL_SLAB = registerCompatBlock(List.of("quark"), "aridrock_bricks_vertical_slab",
            () -> new VerticalSlabBlock(ARIDROCK_BRICKS_PROPERTIES));
    public static final DeferredBlock<RelationalWallBlock> ARIDROCK_BRICKS_WALL = registerBlock("aridrock_bricks_wall",
            () -> new RelationalWallBlock(ARIDROCK_BRICKS.get()));
    public static final DeferredBlock<Block> CHISELED_ARIDROCK_BRICKS = registerBlock("chiseled_aridrock_bricks",
            () -> new Block(ARIDROCK_BRICKS_PROPERTIES));
    public static final DeferredBlock<Block> CRACKED_ARIDROCK_BRICKS = registerBlock("cracked_aridrock_bricks",
            () -> new Block(ARIDROCK_BRICKS_PROPERTIES));
    public static final DeferredBlock<ConnectedPillarBlock> ARIDROCK_PILLAR = registerBlock("aridrock_pillar",
            () -> new ConnectedPillarBlock(ARIDROCK_BRICKS_PROPERTIES));
    public static final DeferredBlock<Block> SKULL_WALL = registerBlock("skull_wall",
            () -> new Block(ARIDROCK_BRICKS_PROPERTIES));
    public static final DeferredBlock<DuskrockBlock> DUSKROCK = registerBlock("duskrock",
            () -> new DuskrockBlock(DUSKROCK_PROPERTIES));
    public static final DeferredBlock<RelationalStairBlock> DUSKROCK_STAIRS = registerBlock("duskrock_stairs",
            () -> new RelationalStairBlock(DUSKROCK.get()));
    public static final DeferredBlock<RelationalSlabBlock> DUSKROCK_SLAB = registerBlock("duskrock_slab",
            () -> new RelationalSlabBlock(DUSKROCK.get()));
    public static final DeferredBlock<VerticalSlabBlock> DUSKROCK_VERTICAL_SLAB = registerCompatBlock(List.of("quark"), "duskrock_vertical_slab",
            () -> new VerticalSlabBlock(DUSKROCK_PROPERTIES));
    public static final DeferredBlock<RelationalWallBlock> DUSKROCK_WALL = registerBlock("duskrock_wall",
            () -> new RelationalWallBlock(DUSKROCK.get()));
    public static final DeferredBlock<Block> POLISHED_DUSKROCK = registerBlock("polished_duskrock",
            () -> new Block(DUSKROCK_BRICKS_PROPERTIES));
    public static final DeferredBlock<RelationalStairBlock> POLISHED_DUSKROCK_STAIRS = registerBlock("polished_duskrock_stairs",
            () -> new RelationalStairBlock(POLISHED_DUSKROCK.get()));
    public static final DeferredBlock<RelationalSlabBlock> POLISHED_DUSKROCK_SLAB = registerBlock("polished_duskrock_slab",
            () -> new RelationalSlabBlock(POLISHED_DUSKROCK.get()));
    public static final DeferredBlock<VerticalSlabBlock> POLISHED_DUSKROCK_VERTICAL_SLAB = registerCompatBlock(List.of("quark"), "polished_duskrock_vertical_slab",
            () -> new VerticalSlabBlock(DUSKROCK_BRICKS_PROPERTIES));
    public static final DeferredBlock<Block> DUSKROCK_BRICKS = registerBlock("duskrock_bricks",
            () -> new Block(DUSKROCK_BRICKS_PROPERTIES));
    public static final DeferredBlock<RelationalStairBlock> DUSKROCK_BRICKS_STAIRS = registerBlock("duskrock_bricks_stairs",
            () -> new RelationalStairBlock(DUSKROCK_BRICKS.get()));
    public static final DeferredBlock<RelationalSlabBlock> DUSKROCK_BRICKS_SLAB = registerBlock("duskrock_bricks_slab",
            () -> new RelationalSlabBlock(DUSKROCK_BRICKS.get()));
    public static final DeferredBlock<VerticalSlabBlock> DUSKROCK_BRICKS_VERTICAL_SLAB = registerCompatBlock(List.of("quark"), "duskrock_bricks_vertical_slab",
            () -> new VerticalSlabBlock(DUSKROCK_BRICKS_PROPERTIES));
    public static final DeferredBlock<RelationalWallBlock> DUSKROCK_BRICKS_WALL = registerBlock("duskrock_bricks_wall",
            () -> new RelationalWallBlock(DUSKROCK_BRICKS.get()));
    public static final DeferredBlock<Block> CHISELED_DUSKROCK_BRICKS = registerBlock("chiseled_duskrock_bricks",
            () -> new Block(DUSKROCK_BRICKS_PROPERTIES));
    public static final DeferredBlock<Block> CRACKED_DUSKROCK_BRICKS = registerBlock("cracked_duskrock_bricks",
            () -> new Block(DUSKROCK_BRICKS_PROPERTIES));
    public static final DeferredBlock<VoidSoulJarBlock> VOID_SOUL_JAR = registerNoTabBlock("void_soul_jar",
            () -> new VoidSoulJarBlock(BlockBehaviour.Properties.of().strength(0.8f).sound(SoundType.GLASS).lightLevel(value -> 5)));
    public static final DeferredBlock<Block> FORSAKEN_BRONZE_BLOCK = registerBlock("forsaken_bronze_block",
            () -> new Block(FORSAKEN_BRONZE_PROPERTIES));
    public static final DeferredBlock<DeathAnchorBlock> DEATH_ANCHOR = registerTooltipBlock("death_anchor",
            () -> new DeathAnchorBlock(FORSAKEN_BRONZE_PROPERTIES),
            List.of(Component.translatable("tooltip.darkerdepths.death_anchor.shift_desc_1").withStyle(ChatFormatting.GOLD),
                    Component.translatable("tooltip.darkerdepths.death_anchor.shift_desc_2").withStyle(ChatFormatting.GOLD)));
    public static final DeferredBlock<TombBlock> TOMB = registerBlock("tomb",
            () -> new TombBlock(blockProperties(4.0f, 10.0f, SoundType.DEEPSLATE, true).noOcclusion()));
    public static final DeferredBlock<ParanoiaAltarBlock> PARANOIA_ALTAR = registerNoTabBlock("paranoia_altar",
            () -> new ParanoiaAltarBlock(blockProperties(2.5f, 3.0f, SoundType.DEEPSLATE, true).lightLevel(level -> 9).noOcclusion()));
    public static final DeferredBlock<LayeredDeepslateBlock> ARID_DEEPSLATE = registerBlock("arid_deepslate",
            () -> new LayeredDeepslateBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE).randomTicks()));
    public static final DeferredBlock<VoidSoulTorchBlock> VOID_SOUL_TORCH = registerNoTabBlock("void_soul_torch",
            () -> new VoidSoulTorchBlock(DDParticleTypes.VOID_SOUL_FLAME::get, BlockBehaviour.Properties.of().strength(0.0F, 1.0F).noCollission().sound(SoundType.WOOD).lightLevel(state -> 10)));
    public static final DeferredBlock<WallVoidSoulTorchBlock> WALL_VOID_SOUL_TORCH = registerNoTabBlock("wall_void_soul_torch",
            () -> new WallVoidSoulTorchBlock(DDParticleTypes.VOID_SOUL_FLAME::get, BlockBehaviour.Properties.of().strength(0.0F, 1.0F).noCollission().sound(SoundType.WOOD).lootFrom(VOID_SOUL_TORCH).lightLevel(state -> 12)));
    public static final DeferredBlock<PetrifiedRootBlock> PETRIFIED_ROOTS = registerBlock("petrified_roots",
            () -> new PetrifiedRootBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).offsetType(BlockBehaviour.OffsetType.XZ).noCollission().instabreak().sound(SoundType.HANGING_ROOTS)));
    public static final DeferredBlock<PetrifiedRootPlantBlock> PETRIFIED_ROOTS_PLANT = registerNoTabBlock("petrified_roots_plant",
            () -> new PetrifiedRootPlantBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).offsetType(BlockBehaviour.OffsetType.XZ).noCollission().instabreak().sound(SoundType.HANGING_ROOTS)));
    public static final DeferredBlock<DrySproutsBlock> DRY_SPROUTS = registerBlock("dry_sprouts",
            () -> new DrySproutsBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DEAD_BUSH).offsetType(BlockBehaviour.OffsetType.XZ)));
    public static final DeferredBlock<Block> AMBER_BLOCK = registerBlock("amber_block",
            () -> new Block(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(1.0f).sound(SoundType.AMETHYST).lightLevel(value -> 7)));
    public static final DeferredBlock<AmethystClusterBlock> AMBER_CLUSTER = registerBlock("amber_cluster",
            () -> new AmethystClusterBlock(6, 3, BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(1.0f).sound(SoundType.SMALL_AMETHYST_BUD).lightLevel(value -> 7)));
    public static final DeferredBlock<RotatedPillarBlock> DARKSLATE = registerBlock("darkslate",
            () -> new RotatedPillarBlock(DARKSLATE_PROPERTIES));
    public static final DeferredBlock<RelationalStairBlock> DARKSLATE_STAIRS = registerBlock("darkslate_stairs",
            () -> new RelationalStairBlock(DARKSLATE.get()));
    public static final DeferredBlock<RelationalSlabBlock> DARKSLATE_SLAB = registerBlock("darkslate_slab",
            () -> new RelationalSlabBlock(DARKSLATE.get()));
    public static final DeferredBlock<VerticalSlabBlock> DARKSLATE_VERTICAL_SLAB = registerCompatBlock(List.of("quark"), "darkslate_vertical_slab",
            () -> new VerticalSlabBlock(DARKSLATE_PROPERTIES));
    public static final DeferredBlock<RelationalWallBlock> DARKSLATE_WALL = registerBlock("darkslate_wall",
            () -> new RelationalWallBlock(DARKSLATE.get()));
    public static final DeferredBlock<Block> POLISHED_DARKSLATE = registerBlock("polished_darkslate",
            () -> new Block(DARKSLATE_PROPERTIES));
    public static final DeferredBlock<RelationalStairBlock> POLISHED_DARKSLATE_STAIRS = registerBlock("polished_darkslate_stairs",
            () -> new RelationalStairBlock(POLISHED_DARKSLATE.get()));
    public static final DeferredBlock<RelationalSlabBlock> POLISHED_DARKSLATE_SLAB = registerBlock("polished_darkslate_slab",
            () -> new RelationalSlabBlock(POLISHED_DARKSLATE.get()));
    public static final DeferredBlock<VerticalSlabBlock> POLISHED_DARKSLATE_VERTICAL_SLAB = registerCompatBlock(List.of("quark"), "polished_darkslate_vertical_slab",
            () -> new VerticalSlabBlock(DARKSLATE_PROPERTIES));
    public static final DeferredBlock<Block> DARKSLATE_BRICKS = registerBlock("darkslate_bricks",
            () -> new Block(DARKSLATE_BRICKS_PROPERTIES));
    public static final DeferredBlock<RelationalStairBlock> DARKSLATE_BRICKS_STAIRS = registerBlock("darkslate_bricks_stairs",
            () -> new RelationalStairBlock(DARKSLATE_BRICKS.get()));
    public static final DeferredBlock<RelationalSlabBlock> DARKSLATE_BRICKS_SLAB = registerBlock("darkslate_bricks_slab",
            () -> new RelationalSlabBlock(DARKSLATE_BRICKS.get()));
    public static final DeferredBlock<VerticalSlabBlock> DARKSLATE_BRICKS_VERTICAL_SLAB = registerCompatBlock(List.of("quark"), "darkslate_bricks_vertical_slab",
            () -> new VerticalSlabBlock(DARKSLATE_BRICKS_PROPERTIES));
    public static final DeferredBlock<RelationalWallBlock> DARKSLATE_BRICKS_WALL = registerBlock("darkslate_bricks_wall",
            () -> new RelationalWallBlock(DARKSLATE_BRICKS.get()));
    public static final DeferredBlock<Block> CHISELED_DARKSLATE_BRICKS = registerBlock("chiseled_darkslate_bricks",
            () -> new Block(DARKSLATE_BRICKS_PROPERTIES));
    public static final DeferredBlock<Block> CRACKED_DARKSLATE_BRICKS = registerBlock("cracked_darkslate_bricks",
            () -> new Block(DARKSLATE_BRICKS_PROPERTIES));
    public static final DeferredBlock<GeyserBlock> GEYSER = registerBlock("geyser",
            () -> new GeyserBlock(DARKSLATE_PROPERTIES.randomTicks()));
    public static final DeferredBlock<StoneMelonBlock> STONE_MELON = registerBlock("stone_melon",
            () -> new StoneMelonBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_GRAY).requiresCorrectToolForDrops().strength(1.0f).sound(SoundType.DEEPSLATE)));
    public static final DeferredBlock<DeadLivingCrystalBlock> DEAD_LIVING_CRYSTAL = registerBlock("dead_living_crystal",
            () -> new DeadLivingCrystalBlock(LIVING_CRYSTAL_PROPERTIES));
    public static final DeferredBlock<LivingCrystalBlock> LIVING_CRYSTAL = registerBlock("living_crystal",
            () -> new LivingCrystalBlock(LIVING_CRYSTAL_PROPERTIES));
    public static final DeferredBlock<CrystalMelonBlock> CRYSTAL_MELON = registerTooltipBlock("crystal_melon",
            () -> new CrystalMelonBlock(blockProperties(1.5f, 1.0f, SoundType.AMETHYST, true).lightLevel(value -> 10)),
            List.of(Component.translatable("tooltip.darkerdepths.crystal_melon.shift_desc_1").withStyle(ChatFormatting.AQUA),
                    Component.translatable("tooltip.darkerdepths.crystal_melon.shift_desc_2").withStyle(ChatFormatting.AQUA)
            )
    );
    public static final DeferredBlock<MagmaPadBlock> MAGMA_PAD = registerNoTabBlock("magma_pad",
            () -> new MagmaPadBlock(BlockBehaviour.Properties.of().strength(0.1F).lightLevel(state -> 3).sound(DDSoundTypes.GRIMESTONE.get()).noOcclusion().pushReaction(PushReaction.DESTROY)));
    public static final DeferredBlock<AshFullBlock> ASH_BLOCK = registerBlock("ash_block",
            () -> new AshFullBlock(blockProperties(0.2f, SoundType.SNOW, false).mapColor(MapColor.COLOR_BLACK).randomTicks()));
    public static final DeferredBlock<AshBlock> ASH = registerBlock("ash",
            () -> new AshBlock(blockProperties(0.1f, SoundType.SNOW, true).mapColor(MapColor.COLOR_BLACK).randomTicks()));
    public static final DeferredBlock<GrimestoneBlock> GRIMESTONE = registerBlock("grimestone",
            () -> new GrimestoneBlock(DARKSLATE_PROPERTIES));
    public static final DeferredBlock<RelationalStairBlock> GRIMESTONE_STAIRS = registerBlock("grimestone_stairs",
            () -> new RelationalStairBlock(GRIMESTONE.get()));
    public static final DeferredBlock<RelationalSlabBlock> GRIMESTONE_SLAB = registerBlock("grimestone_slab",
            () -> new RelationalSlabBlock(GRIMESTONE.get()));
    public static final DeferredBlock<VerticalSlabBlock> GRIMESTONE_VERTICAL_SLAB = registerCompatBlock(List.of("quark"), "grimestone_vertical_slab",
            () -> new VerticalSlabBlock(DARKSLATE_PROPERTIES));
    public static final DeferredBlock<RelationalWallBlock> GRIMESTONE_WALL = registerBlock("grimestone_wall",
            () -> new RelationalWallBlock(GRIMESTONE.get()));
    public static final DeferredBlock<MossyGrimestoneBlock> MOSSY_GRIMESTONE = registerBlock("mossy_grimestone",
            () -> new MossyGrimestoneBlock(DARKSLATE_PROPERTIES));
    public static final DeferredBlock<Block> POLISHED_GRIMESTONE = registerBlock("polished_grimestone",
            () -> new Block(DARKSLATE_PROPERTIES));
    public static final DeferredBlock<RelationalStairBlock> POLISHED_GRIMESTONE_STAIRS = registerBlock("polished_grimestone_stairs",
            () -> new RelationalStairBlock(POLISHED_GRIMESTONE.get()));
    public static final DeferredBlock<RelationalSlabBlock> POLISHED_GRIMESTONE_SLAB = registerBlock("polished_grimestone_slab",
            () -> new RelationalSlabBlock(POLISHED_GRIMESTONE.get()));
    public static final DeferredBlock<VerticalSlabBlock> POLISHED_GRIMESTONE_VERTICAL_SLAB = registerCompatBlock(List.of("quark"), "polished_grimestone_vertical_slab",
            () -> new VerticalSlabBlock(DARKSLATE_PROPERTIES));
    public static final DeferredBlock<Block> GRIMESTONE_BRICKS = registerBlock("grimestone_bricks",
            () -> new Block(DARKSLATE_BRICKS_PROPERTIES));
    public static final DeferredBlock<RelationalStairBlock> GRIMESTONE_BRICKS_STAIRS = registerBlock("grimestone_bricks_stairs",
            () -> new RelationalStairBlock(GRIMESTONE_BRICKS.get()));
    public static final DeferredBlock<RelationalSlabBlock> GRIMESTONE_BRICKS_SLAB = registerBlock("grimestone_bricks_slab",
            () -> new RelationalSlabBlock(GRIMESTONE_BRICKS.get()));
    public static final DeferredBlock<VerticalSlabBlock> GRIMESTONE_BRICKS_VERTICAL_SLAB = registerCompatBlock(List.of("quark"), "grimestone_bricks_vertical_slab",
            () -> new VerticalSlabBlock(DARKSLATE_BRICKS_PROPERTIES));
    public static final DeferredBlock<RelationalWallBlock> GRIMESTONE_BRICKS_WALL = registerBlock("grimestone_bricks_wall",
            () -> new RelationalWallBlock(GRIMESTONE_BRICKS.get()));
    public static final DeferredBlock<Block> CHISELED_GRIMESTONE_BRICKS = registerBlock("chiseled_grimestone_bricks",
            () -> new Block(DARKSLATE_BRICKS_PROPERTIES));
    public static final DeferredBlock<Block> CRACKED_GRIMESTONE_BRICKS = registerBlock("cracked_grimestone_bricks",
            () -> new Block(DARKSLATE_BRICKS_PROPERTIES));
    public static final DeferredBlock<Block> GLOWSHROOM_BLOCK = registerBlock("glowshroom_block",
            () -> new Block(BlockBehaviour.Properties.of().strength(0.5F).sound(SoundType.SLIME_BLOCK)));
    public static final DeferredBlock<RotatedPillarBlock> GLOWSHROOM_STEM = registerBlock("glowshroom_stem",
            () -> new RotatedPillarBlock(BlockBehaviour.Properties.of().strength(1.2f, 2.0f).sound(SoundType.STEM)));
    public static final DeferredBlock<Block> GLOWSHROOM_HEART = registerBlock("glowshroom_heart",
            () -> new Block(BlockBehaviour.Properties.of().strength(2.0f, 3.0f).sound(SoundType.SHROOMLIGHT).lightLevel(value -> 15)));
    public static final DeferredBlock<GlowshroomBlock> GLOWSHROOM = registerBlock("glowshroom",
            () -> new GlowshroomBlock(BlockBehaviour.Properties.of().strength(0.0F, 1.0F).sound(SoundType.SLIME_BLOCK).lightLevel((state) -> 3 + (2 * state.getValue(GlowshroomBlock.GLOWSHROOM_CLUSTERS))).noCollission()));
    public static final DeferredBlock<FlowerPotBlock> POTTED_GLOWSHROOM = registerNoTabBlock("potted_glowshroom",
            () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, GLOWSHROOM, BlockBehaviour.Properties.of().instabreak().noOcclusion()));
    public static final DeferredBlock<GlowspursBlock> GLOWSPURS = registerBlock("glowspurs",
            () -> new GlowspursBlock(BlockBehaviour.Properties.of().instabreak().lightLevel(value -> 5).sound(SoundType.SLIME_BLOCK).noCollission()));
    public static final DeferredBlock<SproutsBlock> MOSSY_SPROUTS = registerBlock("mossy_sprouts",
            () -> new SproutsBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS).sound(SoundType.WET_GRASS).offsetType(BlockBehaviour.OffsetType.XZ)));
    public static final DeferredBlock<GlimmeringVinesBlock> GLIMMERING_VINES = registerBlock("glimmering_vines",
            () -> new GlimmeringVinesBlock(BlockBehaviour.Properties.of().noCollission().lightLevel(value -> 8).sound(SoundType.SPORE_BLOSSOM)));
    public static final DeferredBlock<GlimmeringVinePlantBlock> GLIMMERING_VINE_PLANT = registerNoTabBlock("glimmering_vine_plant",
            () -> new GlimmeringVinePlantBlock(BlockBehaviour.Properties.ofFullCopy(GLIMMERING_VINES.get()).sound(SoundType.SPORE_BLOSSOM)));
    public static final DeferredBlock<GlowshroomLampBlock> GLOWSHROOM_LAMP = registerBlock("glowshroom_lamp",
            () -> new GlowshroomLampBlock(BlockBehaviour.Properties.of().strength(0.3f, 0.3f).lightLevel(state -> state.getValue(BlockStateProperties.LIT) ? 15 : 0).sound(SoundType.GLASS)));
    public static final DeferredBlock<GlowshroomLanternBlock> GLOWSHROOM_LANTERN = registerBlock("glowshroom_lantern",
            () -> new GlowshroomLanternBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.LANTERN)));
    public static final DeferredBlock<RopeBlock> ROPE = registerNoTabBlock("rope",
            () -> new RopeBlock(blockProperties(0.1f, SoundType.WOOL, false)));
    public static final DeferredBlock<MobPlacerBlock> MOB_PLACER = registerNoTabBlock("mob_placer",
            () -> new MobPlacerBlock(Block.Properties.ofFullCopy(Blocks.BEDROCK)));


    public static <B extends Block> DeferredBlock<B> registerBlock(String name, Supplier<? extends B> blocks) {
        DeferredBlock<B> block = BLOCKS.register(name, blocks);
        DDItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
        return block;
    }

    public static <B extends Block> DeferredBlock<B> registerNoTabBlock(String name, Supplier<? extends B> blocks) {
        DeferredBlock<B> block = BLOCKS.register(name, blocks);
        return block;
    }

    public static <B extends Block> DeferredBlock<B> registerTooltipBlock(String name, Supplier<? extends B> blocks, List<Component> tooltips) {
        DeferredBlock<B> block = BLOCKS.register(name, blocks);
        DDItems.ITEMS.register(name, () -> new BlockItemWithHoverText(block.get(), new Item.Properties(), tooltips));
        return block;
    }

    public static <B extends Block> DeferredBlock<B> registerCompatBlock(List<String> modIds, String key, Supplier<B> blockSupplier) {
        DeferredBlock<B> block = BLOCKS.register(key, blockSupplier);
        DeferredItem<BlockItem> item = DDItems.ITEMS.register(key, () -> new BlockItem(block.get(), new Item.Properties()));
        COMPAT.put(item, modIds);
        return block;
    }

    public static BlockBehaviour.Properties blockProperties(float destroyTime, float explosionResistance, SoundType sound, boolean requiresTool) {
        if (requiresTool) {
            return BlockBehaviour.Properties.of().strength(destroyTime, explosionResistance).sound(sound).requiresCorrectToolForDrops();
        } else {
            return BlockBehaviour.Properties.of().strength(destroyTime, explosionResistance).sound(sound);
        }
    }

    public static BlockBehaviour.Properties blockProperties(float destroyTime, SoundType sound, boolean requiresTool) {
        if (requiresTool) {
            return BlockBehaviour.Properties.of().strength(destroyTime).sound(sound).requiresCorrectToolForDrops();
        } else {
            return BlockBehaviour.Properties.of().strength(destroyTime).sound(sound);
        }
    }
}