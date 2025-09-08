package com.naterbobber.darkerdepths.init;

import com.google.common.collect.Maps;
import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.blocks.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Map;
import java.util.function.Supplier;

public class DDBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(DarkerDepths.MOD_ID);
    public static final Map<DeferredHolder<Item, ? extends Item>, String> COMPAT = Maps.newLinkedHashMap();

    // --- PETRIFIED WOOD SET ---
    public static final DeferredHolder<Block, RotatedPillarBlock> PETRIFIED_LOG = registerBlock("petrified_log",
            () -> new RotatedPillarBlock(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(2.4f, 3.0f).sound(SoundType.STEM)));
    public static final DeferredHolder<Block, RotatedPillarBlock> PETRIFIED_WOOD = registerBlock("petrified_wood",
            () -> new RotatedPillarBlock(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(2.4f, 3.0f).sound(SoundType.STEM)));
    public static final DeferredHolder<Block, RotatedPillarBlock> STRIPPED_PETRIFIED_LOG = registerBlock("stripped_petrified_log",
            () -> new RotatedPillarBlock(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(2.4f, 3.0f).sound(SoundType.STEM)));
    public static final DeferredHolder<Block, RotatedPillarBlock> STRIPPED_PETRIFIED_WOOD = registerBlock("stripped_petrified_wood",
            () -> new RotatedPillarBlock(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(2.4f, 3.0f).sound(SoundType.STEM)));
    public static final DeferredHolder<Block, Block> PETRIFIED_PLANKS = registerBlock("petrified_planks",
            () -> new Block(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(2.5f, 3.0f).sound(SoundType.WOOD)));
    public static final DeferredHolder<Block, Block> VERTICAL_PETRIFIED_PLANKS = registerCompatBlock("quark", "vertical_petrified_planks",
            () -> new Block(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(2.5f, 3.0f).sound(SoundType.WOOD)));
    public static final DeferredHolder<Block, StairBlock> PETRIFIED_STAIRS = registerBlock("petrified_stairs",
            () -> new StairBlock(PETRIFIED_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(2.5f, 3.0f).sound(SoundType.WOOD)));
    public static final DeferredHolder<Block, SlabBlock> PETRIFIED_SLAB = registerBlock("petrified_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(2.5f, 3.0f).sound(SoundType.WOOD)));
    public static final DeferredHolder<Block, VerticalSlabBlock> PETRIFIED_VERTICAL_SLAB = registerCompatBlock("quark", "petrified_vertical_slab",
            () -> new VerticalSlabBlock(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(2.5f, 3.0f).sound(SoundType.WOOD)));
    public static final DeferredHolder<Block, FenceBlock> PETRIFIED_FENCE = registerBlock("petrified_fence",
            () -> new FenceBlock(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(2.5f, 3.0f).sound(SoundType.WOOD)));
    public static final DeferredHolder<Block, FenceGateBlock> PETRIFIED_FENCE_GATE = registerBlock("petrified_fence_gate",
            () -> new FenceGateBlock(DDWoodType.PETRIFIED, BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(2.5f, 3.0f).sound(SoundType.WOOD)));
    public static final DeferredHolder<Block, DoorBlock> PETRIFIED_DOOR = registerBlock("petrified_door",
            () -> new DoorBlock(DDBlockSetTypes.PETRIFIED, BlockBehaviour.Properties.of().requiresCorrectToolForDrops().noOcclusion().strength(2.5f, 3.0f).sound(SoundType.WOOD)));
    public static final DeferredHolder<Block, TrapDoorBlock> PETRIFIED_TRAPDOOR = registerBlock("petrified_trapdoor",
            () -> new TrapDoorBlock(DDBlockSetTypes.PETRIFIED, BlockBehaviour.Properties.of().requiresCorrectToolForDrops().noOcclusion().strength(2.5f, 3.0f).sound(SoundType.WOOD)));
    public static final DeferredHolder<Block, PressurePlateBlock> PETRIFIED_PRESSURE_PLATE = registerBlock("petrified_pressure_plate",
            () -> new PressurePlateBlock(DDBlockSetTypes.PETRIFIED, BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(0.5f).sound(SoundType.WOOD)));
    public static final DeferredHolder<Block, ButtonBlock> PETRIFIED_BUTTON = registerBlock("petrified_button",
            () -> new ButtonBlock(DDBlockSetTypes.PETRIFIED, 30, BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(0.5f).sound(SoundType.WOOD)));
    public static final DeferredHolder<Block, DDStandingSignBlock> PETRIFIED_SIGN = registerNoTabBlock("petrified_sign",
            () -> new DDStandingSignBlock(DDWoodType.PETRIFIED, BlockBehaviour.Properties.of().noCollission().strength(1.0F).requiresCorrectToolForDrops().sound(SoundType.WOOD)));
    public static final DeferredHolder<Block, DDWallSignBlock> PETRIFIED_WALL_SIGN = registerNoTabBlock("petrified_wall_sign",
            () -> new DDWallSignBlock(BlockBehaviour.Properties.of().noCollission().strength(1.0F).requiresCorrectToolForDrops().sound(SoundType.WOOD), DDWoodType.PETRIFIED));
    public static final DeferredHolder<Block, WoodPostBlock> PETRIFIED_POST = registerCompatBlock("quark", "petrified_post",
            () -> new WoodPostBlock(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().noOcclusion().strength(2.5f, 3.0f).sound(SoundType.STEM)));
    public static final DeferredHolder<Block, WoodPostBlock> STRIPPED_PETRIFIED_POST = registerCompatBlock("quark", "stripped_petrified_post",
            () -> new WoodPostBlock(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().noOcclusion().strength(2.5f, 3.0f).sound(SoundType.WOOD)));
    public static final DeferredHolder<Block, PorousBlock> POROUS_PETRIFIED_LOG = registerBlock("porous_petrified_log",
            () -> new PorousBlock(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(2.4f, 3.0f).sound(SoundType.STEM).randomTicks().lightLevel(value -> 6)));

    // --- MISC ---
    public static final DeferredHolder<Block, MagmaPadBlock> MAGMA_PAD = registerNoTabBlock("magma_pad",
            () -> new MagmaPadBlock(BlockBehaviour.Properties.of().strength(0.1F).lightLevel(state -> 3).sound(DDSoundEvents.GRIMESTONE).noOcclusion().pushReaction(PushReaction.DESTROY)));

    // --- DARKSLATE SET ---
    public static final DeferredHolder<Block, RotatedPillarBlock> DARKSLATE = registerBlock("darkslate",
            () -> new RotatedPillarBlock(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(1.5f, 6.0f).sound(SoundType.DEEPSLATE)));
    public static final DeferredHolder<Block, StairBlock> DARKSLATE_STAIRS = registerBlock("darkslate_stairs",
            () -> new StairBlock(DARKSLATE.get().defaultBlockState(), BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(1.5f, 6.0f).sound(SoundType.DEEPSLATE)));
    public static final DeferredHolder<Block, SlabBlock> DARKSLATE_SLAB = registerBlock("darkslate_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(1.5f, 6.0f).sound(SoundType.DEEPSLATE)));
    public static final DeferredHolder<Block, VerticalSlabBlock> DARKSLATE_VERTICAL_SLAB = registerCompatBlock("quark", "darkslate_vertical_slab",
            () -> new VerticalSlabBlock(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(1.5f, 6.0f).sound(SoundType.DEEPSLATE)));
    public static final DeferredHolder<Block, WallBlock> DARKSLATE_WALL = registerBlock("darkslate_wall",
            () -> new WallBlock(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(1.5f, 6.0f).sound(SoundType.DEEPSLATE)));
    public static final DeferredHolder<Block, Block> POLISHED_DARKSLATE = registerBlock("polished_darkslate",
            () -> new Block(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(1.5f, 6.0f).sound(SoundType.DEEPSLATE)));
    public static final DeferredHolder<Block, StairBlock> POLISHED_DARKSLATE_STAIRS = registerBlock("polished_darkslate_stairs",
            () -> new StairBlock(POLISHED_DARKSLATE.get().defaultBlockState(), BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(1.5f, 6.0f).sound(SoundType.DEEPSLATE)));
    public static final DeferredHolder<Block, SlabBlock> POLISHED_DARKSLATE_SLAB = registerBlock("polished_darkslate_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(1.5f, 6.0f).sound(SoundType.DEEPSLATE)));
    public static final DeferredHolder<Block, VerticalSlabBlock> POLISHED_DARKSLATE_VERTICAL_SLAB = registerCompatBlock("quark", "polished_darkslate_vertical_slab",
            () -> new VerticalSlabBlock(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(1.5f, 6.0f).sound(SoundType.DEEPSLATE)));
    public static final DeferredHolder<Block, Block> DARKSLATE_BRICKS = registerBlock("darkslate_bricks",
            () -> new Block(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(1.5f, 6.0f).sound(SoundType.DEEPSLATE_BRICKS)));
    public static final DeferredHolder<Block, StairBlock> DARKSLATE_BRICKS_STAIRS = registerBlock("darkslate_bricks_stairs",
            () -> new StairBlock(DARKSLATE_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(1.5f, 6.0f).sound(SoundType.DEEPSLATE_BRICKS)));
    public static final DeferredHolder<Block, SlabBlock> DARKSLATE_BRICKS_SLAB = registerBlock("darkslate_bricks_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(1.5f, 6.0f).sound(SoundType.DEEPSLATE_BRICKS)));
    public static final DeferredHolder<Block, VerticalSlabBlock> DARKSLATE_BRICKS_VERTICAL_SLAB = registerCompatBlock("quark", "darkslate_bricks_vertical_slab",
            () -> new VerticalSlabBlock(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(1.5f, 6.0f).sound(SoundType.DEEPSLATE_BRICKS)));
    public static final DeferredHolder<Block, WallBlock> DARKSLATE_BRICKS_WALL = registerBlock("darkslate_bricks_wall",
            () -> new WallBlock(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(1.5f, 6.0f).sound(SoundType.DEEPSLATE_BRICKS)));
    public static final DeferredHolder<Block, Block> CHISELED_DARKSLATE_BRICKS = registerBlock("chiseled_darkslate_bricks",
            () -> new Block(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(1.5f, 6.0f).sound(SoundType.DEEPSLATE_BRICKS)));
    public static final DeferredHolder<Block, Block> CRACKED_DARKSLATE_BRICKS = registerBlock("cracked_darkslate_bricks",
            () -> new Block(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(1.5f, 6.0f).sound(SoundType.DEEPSLATE_BRICKS)));

    // --- ASH SET ---
    public static final DeferredHolder<Block, AshFullBlock> ASH_BLOCK = registerBlock("ash_block",
            () -> new AshFullBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).requiresCorrectToolForDrops().strength(0.2f).sound(SoundType.SNOW).randomTicks()));
    public static final DeferredHolder<Block, GeyserBlock> GEYSER = registerBlock("geyser",
            () -> new GeyserBlock(BlockBehaviour.Properties.of().sound(SoundType.DEEPSLATE).strength(1.5F, 6.0F).requiresCorrectToolForDrops().randomTicks()));
    public static final DeferredHolder<Block, AshBlock> ASH = registerBlock("ash",
            () -> new AshBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).randomTicks().strength(0.1f).requiresCorrectToolForDrops().sound(SoundType.SNOW)));

    // --- ARIDROCK SET ---
    public static final DeferredHolder<Block, RotatedPillarBlock> ARIDROCK = registerBlock("aridrock",
            () -> new RotatedPillarBlock(BlockBehaviour.Properties.of().mapColor(MapColor.SAND).requiresCorrectToolForDrops().strength(1.5f, 6.0f).sound(SoundType.STONE)));
    public static final DeferredHolder<Block, StairBlock> ARIDROCK_STAIRS = registerBlock("aridrock_stairs",
            () -> new StairBlock(ARIDROCK.get().defaultBlockState(), BlockBehaviour.Properties.of().mapColor(MapColor.SAND).requiresCorrectToolForDrops().strength(1.5f, 6.0f).sound(SoundType.STONE)));
    public static final DeferredHolder<Block, SlabBlock> ARIDROCK_SLAB = registerBlock("aridrock_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.of().mapColor(MapColor.SAND).requiresCorrectToolForDrops().strength(1.5f, 6.0f).sound(SoundType.STONE)));
    public static final DeferredHolder<Block, VerticalSlabBlock> ARIDROCK_VERTICAL_SLAB = registerCompatBlock("quark", "aridrock_vertical_slab",
            () -> new VerticalSlabBlock(BlockBehaviour.Properties.of().mapColor(MapColor.SAND).requiresCorrectToolForDrops().strength(1.5f, 6.0f).sound(SoundType.STONE)));
    public static final DeferredHolder<Block, WallBlock> ARIDROCK_WALL = registerBlock("aridrock_wall",
            () -> new WallBlock(BlockBehaviour.Properties.of().mapColor(MapColor.SAND).requiresCorrectToolForDrops().strength(1.5f, 6.0f).sound(SoundType.STONE)));
    public static final DeferredHolder<Block, Block> POLISHED_ARIDROCK = registerBlock("polished_aridrock",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.SAND).requiresCorrectToolForDrops().strength(1.5f, 6.0f).sound(SoundType.STONE)));
    public static final DeferredHolder<Block, StairBlock> POLISHED_ARIDROCK_STAIRS = registerBlock("polished_aridrock_stairs",
            () -> new StairBlock(POLISHED_ARIDROCK.get().defaultBlockState(), BlockBehaviour.Properties.of().mapColor(MapColor.SAND).requiresCorrectToolForDrops().strength(1.5f, 6.0f).sound(SoundType.STONE)));
    public static final DeferredHolder<Block, SlabBlock> POLISHED_ARIDROCK_SLAB = registerBlock("polished_aridrock_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.of().mapColor(MapColor.SAND).requiresCorrectToolForDrops().strength(1.5f, 6.0f).sound(SoundType.STONE)));
    public static final DeferredHolder<Block, VerticalSlabBlock> POLISHED_ARIDROCK_VERTICAL_SLAB = registerCompatBlock("quark", "polished_aridrock_vertical_slab",
            () -> new VerticalSlabBlock(BlockBehaviour.Properties.of().mapColor(MapColor.SAND).requiresCorrectToolForDrops().strength(1.5f, 6.0f).sound(SoundType.STONE)));
    public static final DeferredHolder<Block, Block> ARIDROCK_BRICKS = registerBlock("aridrock_bricks",
            () -> new Block(BlockBehaviour.Properties.of().sound(SoundType.STONE).strength(1.5F, 6.0F).requiresCorrectToolForDrops()));
    public static final DeferredHolder<Block, StairBlock> ARIDROCK_BRICKS_STAIRS = registerBlock("aridrock_bricks_stairs",
            () -> new StairBlock(ARIDROCK_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.of().sound(SoundType.STONE).strength(1.5F, 6.0F).requiresCorrectToolForDrops()));
    public static final DeferredHolder<Block, SlabBlock> ARIDROCK_BRICKS_SLAB = registerBlock("aridrock_bricks_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.of().sound(SoundType.STONE).strength(1.5F, 6.0F).requiresCorrectToolForDrops()));
    public static final DeferredHolder<Block, VerticalSlabBlock> ARIDROCK_BRICKS_VERTICAL_SLAB = registerCompatBlock("quark", "aridrock_bricks_vertical_slab",
            () -> new VerticalSlabBlock(BlockBehaviour.Properties.of().sound(SoundType.STONE).strength(1.5F, 6.0F).requiresCorrectToolForDrops()));
    public static final DeferredHolder<Block, WallBlock> ARIDROCK_BRICKS_WALL = registerBlock("aridrock_bricks_wall",
            () -> new WallBlock(BlockBehaviour.Properties.of().mapColor(MapColor.SAND).requiresCorrectToolForDrops().strength(1.5f, 6.0f).sound(SoundType.STONE)));
    public static final DeferredHolder<Block, Block> CHISELED_ARIDROCK_BRICKS = registerBlock("chiseled_aridrock_bricks",
            () -> new Block(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(1.5f, 6.0f).sound(SoundType.STONE)));
    public static final DeferredHolder<Block, Block> CRACKED_ARIDROCK_BRICKS = registerBlock("cracked_aridrock_bricks",
            () -> new Block(BlockBehaviour.Properties.of().sound(SoundType.STONE).strength(1.5F, 6.0F).requiresCorrectToolForDrops()));
    public static final DeferredHolder<Block, ConnectedPillarBlock> ARIDROCK_PILLAR = registerBlock("aridrock_pillar",
            () -> new ConnectedPillarBlock(BlockBehaviour.Properties.of().sound(SoundType.STONE).strength(1.5F, 6.0F).requiresCorrectToolForDrops()));
    public static final DeferredHolder<Block, Block> SKULL_WALL = registerBlock("skull_wall",
            () -> new Block(BlockBehaviour.Properties.of().sound(SoundType.STONE).strength(1.5F, 6.0F).requiresCorrectToolForDrops()));

    // --- DUSKROCK SET ---
    public static final DeferredHolder<Block, LimestoneBlock> DUSKROCK = registerBlock("duskrock",
            () -> new LimestoneBlock(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(1.5f, 6.0f).sound(SoundType.TUFF)));
    public static final DeferredHolder<Block, StairBlock> DUSKROCK_STAIRS = registerBlock("duskrock_stairs",
            () -> new StairBlock(DUSKROCK.get().defaultBlockState(), BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(1.5f, 6.0f).sound(SoundType.TUFF)));
    public static final DeferredHolder<Block, SlabBlock> DUSKROCK_SLAB = registerBlock("duskrock_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(1.5f, 6.0f).sound(SoundType.TUFF)));
    public static final DeferredHolder<Block, VerticalSlabBlock> DUSKROCK_VERTICAL_SLAB = registerCompatBlock("quark", "duskrock_vertical_slab",
            () -> new VerticalSlabBlock(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(1.5f, 6.0f).sound(SoundType.TUFF)));
    public static final DeferredHolder<Block, WallBlock> DUSKROCK_WALL = registerBlock("duskrock_wall",
            () -> new WallBlock(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(1.5f, 6.0f).sound(SoundType.TUFF)));
    public static final DeferredHolder<Block, Block> POLISHED_DUSKROCK = registerBlock("polished_duskrock",
            () -> new Block(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(1.5f, 6.0f).sound(SoundType.STONE)));
    public static final DeferredHolder<Block, StairBlock> POLISHED_DUSKROCK_STAIRS = registerBlock("polished_duskrock_stairs",
            () -> new StairBlock(POLISHED_DUSKROCK.get().defaultBlockState(), BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(1.5f, 6.0f).sound(SoundType.STONE)));
    public static final DeferredHolder<Block, SlabBlock> POLISHED_DUSKROCK_SLAB = registerBlock("polished_duskrock_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(1.5f, 6.0f).sound(SoundType.STONE)));
    public static final DeferredHolder<Block, VerticalSlabBlock> POLISHED_DUSKROCK_VERTICAL_SLAB = registerCompatBlock("quark", "polished_duskrock_vertical_slab",
            () -> new VerticalSlabBlock(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(1.5f, 6.0f).sound(SoundType.STONE)));
    public static final DeferredHolder<Block, Block> DUSKROCK_BRICKS = registerBlock("duskrock_bricks",
            () -> new Block(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(1.5f, 6.0f).sound(SoundType.STONE)));
    public static final DeferredHolder<Block, StairBlock> DUSKROCK_BRICKS_STAIRS = registerBlock("duskrock_bricks_stairs",
            () -> new StairBlock(DUSKROCK_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(1.5f, 6.0f).sound(SoundType.STONE)));
    public static final DeferredHolder<Block, SlabBlock> DUSKROCK_BRICKS_SLAB = registerBlock("duskrock_bricks_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(1.5f, 6.0f).sound(SoundType.STONE)));
    public static final DeferredHolder<Block, VerticalSlabBlock> DUSKROCK_BRICKS_VERTICAL_SLAB = registerCompatBlock("quark", "duskrock_bricks_vertical_slab",
            () -> new VerticalSlabBlock(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(1.5f, 6.0f).sound(SoundType.STONE)));
    public static final DeferredHolder<Block, WallBlock> DUSKROCK_BRICKS_WALL = registerBlock("duskrock_bricks_wall",
            () -> new WallBlock(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(1.5F, 6.0F).requiresCorrectToolForDrops().sound(SoundType.STONE)));
    public static final DeferredHolder<Block, Block> CHISELED_DUSKROCK_BRICKS = registerBlock("chiseled_duskrock_bricks",
            () -> new Block(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(1.5f, 6.0f).sound(SoundType.STONE)));
    public static final DeferredHolder<Block, Block> CRACKED_DUSKROCK_BRICKS = registerBlock("cracked_duskrock_bricks",
            () -> new Block(BlockBehaviour.Properties.of().sound(SoundType.STONE).strength(1.5F, 6.0F).requiresCorrectToolForDrops()));

    // --- DEEPSLATE VARIANTS ---
    public static final DeferredHolder<Block, LayeredDeepslateBlock> ARID_DEEPSLATE = registerBlock("arid_deepslate",
            () -> new LayeredDeepslateBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE).randomTicks()));

    // --- SOUL RELATED ---
    public static final DeferredHolder<Block, VoidSoulTorchBlock> VOID_SOUL_TORCH = registerNoTabBlock("void_soul_torch",
            () -> new VoidSoulTorchBlock(BlockBehaviour.Properties.of().strength(0.0F, 1.0F).noCollission().sound(SoundType.WOOD).lightLevel(state -> 10), DDParticleTypes.VOID_SOUL_FLAME::get));
    public static final DeferredHolder<Block, WallVoidSoulTorchBlock> WALL_VOID_SOUL_TORCH = registerNoTabBlock("wall_void_soul_torch",
            () -> new WallVoidSoulTorchBlock(BlockBehaviour.Properties.of().strength(0.0F, 1.0F).noCollission().sound(SoundType.WOOD).dropsLike(VOID_SOUL_TORCH.get()).lightLevel(state -> 12), DDParticleTypes.VOID_SOUL_FLAME::get));

    // --- PLANTS ---
    public static final DeferredHolder<Block, PetrifiedRootBlock> PETRIFIED_ROOTS = registerBlock("petrified_roots",
            () -> new PetrifiedRootBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).offsetType(BlockBehaviour.OffsetType.XZ).noCollission().destroyTime(0.0F).sound(SoundType.HANGING_ROOTS)));
    public static final DeferredHolder<Block, PetrifiedRootPlantBlock> PETRIFIED_ROOTS_PLANT = registerNoTabBlock("petrified_roots_plant",
            () -> new PetrifiedRootPlantBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).offsetType(BlockBehaviour.OffsetType.XZ).noCollission().destroyTime(0.0F).sound(SoundType.HANGING_ROOTS)));
    public static final DeferredHolder<Block, DrySproutsBlock> DRY_SPROUTS = registerBlock("dry_sprouts",
            () -> new DrySproutsBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).noCollission().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ)));

    // --- GRIMESTONE SET ---
    public static final DeferredHolder<Block, GrimestoneBlock> GRIMESTONE = registerBlock("grimestone",
            () -> new GrimestoneBlock(BlockBehaviour.Properties.of().strength(1.5F, 6.0F).sound(SoundType.DEEPSLATE).requiresCorrectToolForDrops()));
    public static final DeferredHolder<Block, StairBlock> GRIMESTONE_STAIRS = registerBlock("grimestone_stairs",
            () -> new StairBlock(GRIMESTONE.get().defaultBlockState(), BlockBehaviour.Properties.of().strength(1.5F, 6.0F).sound(SoundType.DEEPSLATE).requiresCorrectToolForDrops()));
    public static final DeferredHolder<Block, SlabBlock> GRIMESTONE_SLAB = registerBlock("grimestone_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.of().strength(1.5F, 6.0F).sound(SoundType.DEEPSLATE).requiresCorrectToolForDrops()));
    public static final DeferredHolder<Block, VerticalSlabBlock> GRIMESTONE_VERTICAL_SLAB = registerCompatBlock("quark", "grimestone_vertical_slab",
            () -> new VerticalSlabBlock(BlockBehaviour.Properties.of().strength(1.5F, 6.0F).sound(SoundType.DEEPSLATE).requiresCorrectToolForDrops()));
    public static final DeferredHolder<Block, WallBlock> GRIMESTONE_WALL = registerBlock("grimestone_wall",
            () -> new WallBlock(BlockBehaviour.Properties.of().strength(1.5F, 6.0F).sound(SoundType.DEEPSLATE).requiresCorrectToolForDrops()));
    public static final DeferredHolder<Block, MossyGrimestoneBlock> MOSSY_GRIMESTONE = registerBlock("mossy_grimestone",
            () -> new MossyGrimestoneBlock(BlockBehaviour.Properties.of().strength(1.5F, 6.0F).sound(SoundType.DEEPSLATE).requiresCorrectToolForDrops()));
    public static final DeferredHolder<Block, Block> POLISHED_GRIMESTONE = registerBlock("polished_grimestone",
            () -> new Block(BlockBehaviour.Properties.of().strength(1.5F, 6.0F).sound(SoundType.DEEPSLATE).requiresCorrectToolForDrops()));
    public static final DeferredHolder<Block, StairBlock> POLISHED_GRIMESTONE_STAIRS = registerBlock("polished_grimestone_stairs",
            () -> new StairBlock(POLISHED_GRIMESTONE.get().defaultBlockState(), BlockBehaviour.Properties.of().strength(1.5F, 6.0F).sound(SoundType.DEEPSLATE).requiresCorrectToolForDrops()));
    public static final DeferredHolder<Block, SlabBlock> POLISHED_GRIMESTONE_SLAB = registerBlock("polished_grimestone_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.of().strength(1.5F, 6.0F).sound(SoundType.DEEPSLATE).requiresCorrectToolForDrops()));
    public static final DeferredHolder<Block, VerticalSlabBlock> POLISHED_GRIMESTONE_VERTICAL_SLAB = registerCompatBlock("quark", "polished_grimestone_vertical_slab",
            () -> new VerticalSlabBlock(BlockBehaviour.Properties.of().strength(1.5F, 6.0F).sound(SoundType.DEEPSLATE).requiresCorrectToolForDrops()));
    public static final DeferredHolder<Block, Block> GRIMESTONE_BRICKS = registerBlock("grimestone_bricks",
            () -> new Block(BlockBehaviour.Properties.of().sound(SoundType.DEEPSLATE_BRICKS).strength(1.5F, 6.0F).requiresCorrectToolForDrops()));
    public static final DeferredHolder<Block, StairBlock> GRIMESTONE_BRICKS_STAIRS = registerBlock("grimestone_bricks_stairs",
            () -> new StairBlock(GRIMESTONE_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.of().sound(SoundType.DEEPSLATE_BRICKS).strength(1.5F, 6.0F).requiresCorrectToolForDrops()));
    public static final DeferredHolder<Block, SlabBlock> GRIMESTONE_BRICKS_SLAB = registerBlock("grimestone_bricks_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.of().sound(SoundType.DEEPSLATE_BRICKS).strength(1.5F, 6.0F).requiresCorrectToolForDrops()));
    public static final DeferredHolder<Block, VerticalSlabBlock> GRIMESTONE_BRICKS_VERTICAL_SLAB = registerCompatBlock("quark", "grimestone_bricks_vertical_slab",
            () -> new VerticalSlabBlock(BlockBehaviour.Properties.of().sound(SoundType.DEEPSLATE_BRICKS).strength(1.5F, 6.0F).requiresCorrectToolForDrops()));
    public static final DeferredHolder<Block, WallBlock> GRIMESTONE_BRICKS_WALL = registerBlock("grimestone_bricks_wall",
            () -> new WallBlock(BlockBehaviour.Properties.of().sound(SoundType.DEEPSLATE_BRICKS).strength(1.5F, 6.0F).requiresCorrectToolForDrops()));
    public static final DeferredHolder<Block, Block> CHISELED_GRIMESTONE_BRICKS = registerBlock("chiseled_grimestone_bricks",
            () -> new Block(BlockBehaviour.Properties.of().sound(SoundType.DEEPSLATE_BRICKS).strength(1.5F, 6.0F).requiresCorrectToolForDrops()));
    public static final DeferredHolder<Block, Block> CRACKED_GRIMESTONE_BRICKS = registerBlock("cracked_grimestone_bricks",
            () -> new Block(BlockBehaviour.Properties.of().sound(SoundType.DEEPSLATE_BRICKS).strength(1.5F, 6.0F).requiresCorrectToolForDrops()));

    // --- CRYSTAL/MELON RELATED ---
    public static final DeferredHolder<Block, StoneMelonBlock> STONE_MELON = registerBlock("stone_melon",
            () -> new StoneMelonBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_GRAY).requiresCorrectToolForDrops().strength(1.0f).sound(SoundType.DEEPSLATE)));
    public static final DeferredHolder<Block, DeadLivingCrystalBlock> DEAD_LIVING_CRYSTAL = registerBlock("dead_living_crystal",
            () -> new DeadLivingCrystalBlock(BlockBehaviour.Properties.of().sound(SoundType.DEEPSLATE).strength(1.5F, 6.0F).requiresCorrectToolForDrops().randomTicks()));
    public static final DeferredHolder<Block, LivingCrystalBlock> LIVING_CRYSTAL = registerBlock("living_crystal",
            () -> new LivingCrystalBlock(BlockBehaviour.Properties.of().sound(SoundType.DEEPSLATE).strength(1.5F, 6.0F).randomTicks().requiresCorrectToolForDrops()));
    public static final DeferredHolder<Block, CrystalMelonBlock> CRYSTAL_MELON = registerBlock("crystal_melon",
            () -> new CrystalMelonBlock(BlockBehaviour.Properties.of().strength(1.5F, 1F).sound(SoundType.AMETHYST).lightLevel(value -> 10).requiresCorrectToolForDrops()));

    // --- GLOWSHROOM SET ---
    public static final DeferredHolder<Block, GlowshroomBlock> GLOWSHROOM = registerBlock("glowshroom",
            () -> new GlowshroomBlock(BlockBehaviour.Properties.of().strength(0.0F, 1.0F).sound(SoundType.SLIME_BLOCK).lightLevel((state) -> 3 + (2 * state.getValue(GlowshroomBlock.CLUSTERS_1_3))).noCollission()));
    public static final DeferredHolder<Block, GlowspursBlock> GLOWSPURS = registerBlock("glowspurs",
            () -> new GlowspursBlock(BlockBehaviour.Properties.of().destroyTime(0.0F).lightLevel(value -> 5).sound(SoundType.SLIME_BLOCK).noCollission()));
    public static final DeferredHolder<Block, SproutsBlock> MOSSY_SPROUTS = registerBlock("mossy_sprouts",
            () -> new SproutsBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).noCollission().instabreak().sound(SoundType.WET_GRASS).offsetType(BlockBehaviour.OffsetType.XZ)));
    public static final DeferredHolder<Block, FlowerPotBlock> POTTED_GLOWSHROOM = registerNoTabBlock("potted_glowshroom",
            () -> new FlowerPotBlock(GLOWSHROOM.get(), BlockBehaviour.Properties.of().destroyTime(0.0F).noOcclusion()));
    public static final DeferredHolder<Block, GlimmeringVinesBlock> GLIMMERING_VINES = registerBlock("glimmering_vines",
            () -> new GlimmeringVinesBlock(BlockBehaviour.Properties.of().noCollission().lightLevel(value -> 8).sound(SoundType.SPORE_BLOSSOM)));
    public static final DeferredHolder<Block, GlimmeringVinePlantBlock> GLIMMERING_VINE_PLANT = registerNoTabBlock("glimmering_vine_plant",
            () -> new GlimmeringVinePlantBlock(BlockBehaviour.Properties.of().noCollission().lightLevel(value -> 8).sound(SoundType.SPORE_BLOSSOM)));
    public static final DeferredHolder<Block, Block> GLOWSHROOM_BLOCK = registerBlock("glowshroom_block",
            () -> new Block(BlockBehaviour.Properties.of().strength(0.5F).sound(SoundType.SLIME_BLOCK)));
    public static final DeferredHolder<Block, RotatedPillarBlock> GLOWSHROOM_STEM = registerBlock("glowshroom_stem",
            () -> new RotatedPillarBlock(BlockBehaviour.Properties.of().strength(1.2f, 2.0f).sound(SoundType.STEM)));
    public static final DeferredHolder<Block, Block> GLOWSHROOM_HEART = registerBlock("glowshroom_heart",
            () -> new Block(BlockBehaviour.Properties.of().strength(2.0f, 3.0f).sound(SoundType.SHROOMLIGHT).lightLevel(value -> 15)));
    public static final DeferredHolder<Block, GlowshroomLanternBlock> GLOWSHROOM_LANTERN = registerBlock("glowshroom_lantern",
            () -> new GlowshroomLanternBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.LANTERN)));
    public static final DeferredHolder<Block, GlowshroomLampBlock> GLOWSHROOM_LAMP = registerBlock("glowshroom_lamp",
            () -> new GlowshroomLampBlock(BlockBehaviour.Properties.of().strength(0.3f, 0.3f).lightLevel(state -> state.getValue(BlockStateProperties.LIT) ? 15 : 0).sound(SoundType.GLASS)));

    // --- UTILITY ---
    public static final DeferredHolder<Block, RopeBlock> ROPE = registerNoTabBlock("rope",
            () -> new RopeBlock(BlockBehaviour.Properties.of().strength(0.1F).sound(SoundType.WOOL)));
    public static final DeferredHolder<Block, Block> AMBER_BLOCK = registerBlock("amber_block",
            () -> new Block(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(1.0f).sound(SoundType.AMETHYST).lightLevel(value -> 7)));
    public static final DeferredHolder<Block, AmethystClusterBlock> AMBER_CLUSTER = registerBlock("amber_cluster",
            () -> new AmethystClusterBlock(7, 3, BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(1.0f).sound(SoundType.SMALL_AMETHYST_BUD).lightLevel(value -> 7)));
    public static final DeferredHolder<Block, VoidSoulJarBlock> VOID_SOUL_JAR = registerNoTabBlock("void_soul_jar",
            () -> new VoidSoulJarBlock(BlockBehaviour.Properties.of().strength(0.8f).sound(SoundType.GLASS).lightLevel(value -> 5)));
    public static final DeferredHolder<Block, DeathAnchorBlock> DEATH_ANCHOR = registerBlock("death_anchor",
            () -> new DeathAnchorBlock(BlockBehaviour.Properties.of().strength(5.0F, 12.0F).sound(SoundType.NETHERITE_BLOCK).requiresCorrectToolForDrops()));
    public static final DeferredHolder<Block, TombBlock> TOMB = registerBlock("tomb",
            () -> new TombBlock(BlockBehaviour.Properties.of().sound(SoundType.DEEPSLATE).strength(4F, 10.0F).requiresCorrectToolForDrops().noOcclusion()));
    public static final DeferredHolder<Block, ParanoiaAltarBlock> PARANOIA_ALTAR = registerNoTabBlock("paranoia_altar",
            () -> new ParanoiaAltarBlock(BlockBehaviour.Properties.of().sound(SoundType.DEEPSLATE).strength(4F, 10.0F).lightLevel(level -> 9).requiresCorrectToolForDrops().noOcclusion()));
    public static final DeferredHolder<Block, Block> FORSAKEN_BRONZE_BLOCK = registerBlock("forsaken_bronze_block",
            () -> new Block(BlockBehaviour.Properties.of().strength(5.0F, 12.0F).sound(SoundType.NETHERITE_BLOCK).requiresCorrectToolForDrops()));
    public static final DeferredHolder<Block, MobPlacerBlock> MOB_PLACER = registerNoTabBlock("mob_placer",
            () -> new MobPlacerBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BEDROCK)));


    public static <B extends Block> DeferredHolder<Block, B> registerBlock(String name, Supplier<B> blockSupplier) {
        DeferredHolder<Block, B> block = BLOCKS.register(name, blockSupplier);
        DDItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
        return block;
    }

    public static <B extends Block> DeferredHolder<Block, B> registerNoTabBlock(String name, Supplier<B> blockSupplier) {
        return BLOCKS.register(name, blockSupplier);
    }

    public static <B extends Block> DeferredHolder<Block, B> registerCompatBlock(String modId, String key, Supplier<B> blockSupplier) {
        DeferredHolder<Block, B> block = BLOCKS.register(key, blockSupplier);
        DeferredHolder<Item, BlockItem> item = DDItems.ITEMS.register(key, () -> new BlockItem(block.get(), new Item.Properties()));
        COMPAT.put(item, modId);
        return block;
    }
}

