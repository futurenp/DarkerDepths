package com.naterbobber.darkerdepths.init;

import com.google.common.collect.Maps;
import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.blocks.*;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Map;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = DarkerDepths.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DDBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, DarkerDepths.MODID);
    public static final Map<RegistryObject<? extends Item>, String> COMPAT = Maps.newLinkedHashMap();

    public static final RegistryObject<Block> PETRIFIED_LOG = registerBlock("petrified_log",
            () -> new RotatedPillarBlock(Block.Properties.of().requiresCorrectToolForDrops().strength(2.4f, 3.0f).sound(SoundType.STEM)));
    public static final RegistryObject<Block> PETRIFIED_WOOD = registerBlock("petrified_wood",
            () -> new RotatedPillarBlock(Block.Properties.of().requiresCorrectToolForDrops().strength(2.4f, 3.0f).sound(SoundType.STEM)));
    public static final RegistryObject<Block> STRIPPED_PETRIFIED_LOG = registerBlock("stripped_petrified_log",
            () -> new RotatedPillarBlock(Block.Properties.of().requiresCorrectToolForDrops().strength(2.4f, 3.0f).sound(SoundType.STEM)));
    public static final RegistryObject<Block> STRIPPED_PETRIFIED_WOOD = registerBlock("stripped_petrified_wood",
            () -> new RotatedPillarBlock(Block.Properties.of().requiresCorrectToolForDrops().strength(2.4f, 3.0f).sound(SoundType.STEM)));
    public static final RegistryObject<Block> PETRIFIED_PLANKS = registerBlock("petrified_planks",
            () -> new Block(Block.Properties.of().requiresCorrectToolForDrops().strength(2.5f, 3.0f).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> VERTICAL_PETRIFIED_PLANKS = registerCompatBlock("quark", "vertical_petrified_planks",
            () -> new Block(Block.Properties.copy(PETRIFIED_PLANKS.get())));
    public static final RegistryObject<Block> PETRIFIED_STAIRS = registerBlock("petrified_stairs",
            () -> new StairBlock(() -> PETRIFIED_PLANKS.get().defaultBlockState(), Block.Properties.of().requiresCorrectToolForDrops().strength(2.5f, 3.0f).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> PETRIFIED_SLAB = registerBlock("petrified_slab",
            () -> new SlabBlock(Block.Properties.of().requiresCorrectToolForDrops().strength(2.5f, 3.0f).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> PETRIFIED_VERTICAL_SLAB = registerCompatBlock("quark", "petrified_vertical_slab",
            () -> new VerticalSlabBlock(BlockBehaviour.Properties.copy(PETRIFIED_SLAB.get())));
    public static final RegistryObject<Block> PETRIFIED_FENCE = registerBlock("petrified_fence",
            () -> new FenceBlock(Block.Properties.of().requiresCorrectToolForDrops().strength(2.5f, 3.0f).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> PETRIFIED_FENCE_GATE = registerBlock("petrified_fence_gate",
            () -> new FenceGateBlock(Block.Properties.of().requiresCorrectToolForDrops().strength(2.5f, 3.0f).sound(SoundType.WOOD), DDWoodType.PETRIFIED));
    public static final RegistryObject<Block> PETRIFIED_DOOR = registerBlock("petrified_door",
            () -> new DoorBlock(Block.Properties.of().requiresCorrectToolForDrops().noOcclusion().strength(2.5f, 3.0f).sound(SoundType.WOOD), DDBlockSetTypes.PETRIFIED));
    public static final RegistryObject<Block> PETRIFIED_TRAPDOOR = registerBlock("petrified_trapdoor",
            () -> new TrapDoorBlock(Block.Properties.of().requiresCorrectToolForDrops().noOcclusion().strength(2.5f, 3.0f).sound(SoundType.WOOD), DDBlockSetTypes.PETRIFIED));
    public static final RegistryObject<Block> PETRIFIED_PRESSURE_PLATE = registerBlock("petrified_pressure_plate",
            () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING ,Block.Properties.of().requiresCorrectToolForDrops().strength(0.5f).sound(SoundType.WOOD), DDBlockSetTypes.PETRIFIED));
    public static final RegistryObject<Block> PETRIFIED_BUTTON = registerBlock("petrified_button",
            () -> new ButtonBlock(Block.Properties.of().requiresCorrectToolForDrops().strength(0.5f).sound(SoundType.WOOD), DDBlockSetTypes.PETRIFIED, 30, true));
    public static final RegistryObject<Block> PETRIFIED_SIGN = registerNoTabBlock("petrified_sign",
            () -> new DDStandingSignBlock(BlockBehaviour.Properties.of().noCollission().strength(1.0F).requiresCorrectToolForDrops().sound(SoundType.WOOD), DDWoodType.PETRIFIED));
    public static final RegistryObject<Block> PETRIFIED_WALL_SIGN = registerNoTabBlock("petrified_wall_sign",
            () -> new DDWallSignBlock(BlockBehaviour.Properties.of().noCollission().strength(1.0F).requiresCorrectToolForDrops().sound(SoundType.WOOD), DDWoodType.PETRIFIED));
    public static final RegistryObject<Block> PETRIFIED_POST = registerCompatBlock("quark", "petrified_post",
            () -> new WoodPostBlock(Block.Properties.of().requiresCorrectToolForDrops().noOcclusion().strength(2.5f, 3.0f).sound(SoundType.STEM)));
    public static final RegistryObject<Block> STRIPPED_PETRIFIED_POST = registerCompatBlock("quark", "stripped_petrified_post",
            () -> new WoodPostBlock(Block.Properties.of().requiresCorrectToolForDrops().noOcclusion().strength(2.5f, 3.0f).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> POROUS_PETRIFIED_LOG = registerBlock("porous_petrified_log",
            () -> new PorousBlock(BlockBehaviour.Properties.copy(PETRIFIED_LOG.get()).randomTicks().lightLevel(value -> 6)));
    public static final RegistryObject<Block> MAGMA_PAD = registerNoTabBlock("magma_pad",
            () -> new MagmaPadBlock(BlockBehaviour.Properties.of().strength(0.1F).lightLevel(state -> 3).sound(DDSoundEvents.GRIMESTONE).noOcclusion().pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<Block> DARKSLATE = registerBlock("darkslate",
            () -> new RotatedPillarBlock(Block.Properties.of().requiresCorrectToolForDrops().strength(1.5f, 6.0f).sound(SoundType.DEEPSLATE)));
    public static final RegistryObject<Block> DARKSLATE_STAIRS = registerBlock("darkslate_stairs",
            () -> new StairBlock(() -> DARKSLATE.get().defaultBlockState(), Block.Properties.of().requiresCorrectToolForDrops().strength(1.5f, 6.0f).sound(SoundType.STONE)));
    public static final RegistryObject<Block> DARKSLATE_SLAB = registerBlock("darkslate_slab",
            () -> new SlabBlock(Block.Properties.of().requiresCorrectToolForDrops().strength(1.5f, 6.0f).sound(SoundType.STONE)));
    public static final RegistryObject<Block> DARKSLATE_VERTICAL_SLAB = registerCompatBlock("quark", "darkslate_vertical_slab",
            () -> new VerticalSlabBlock(BlockBehaviour.Properties.copy(DARKSLATE_SLAB.get())));
    public static final RegistryObject<Block> DARKSLATE_WALL = registerBlock("darkslate_wall",
            () -> new WallBlock(Block.Properties.of().requiresCorrectToolForDrops().strength(1.5f, 6.0f).sound(SoundType.STONE)));
    public static final RegistryObject<Block> POLISHED_DARKSLATE = registerBlock("polished_darkslate",
            () -> new Block(Block.Properties.of().requiresCorrectToolForDrops().strength(1.5f, 6.0f).sound(SoundType.STONE)));
    public static final RegistryObject<Block> POLISHED_DARKSLATE_STAIRS = registerBlock("polished_darkslate_stairs",
            () -> new StairBlock(() -> POLISHED_DARKSLATE.get().defaultBlockState(), Block.Properties.of().requiresCorrectToolForDrops().strength(1.5f, 6.0f).sound(SoundType.STONE)));
    public static final RegistryObject<Block> POLISHED_DARKSLATE_SLAB = registerBlock("polished_darkslate_slab",
            () -> new SlabBlock(Block.Properties.of().requiresCorrectToolForDrops().strength(1.5f, 6.0f).sound(SoundType.STONE)));
    public static final RegistryObject<Block> POLISHED_DARKSLATE_VERTICAL_SLAB = registerCompatBlock("quark", "polished_darkslate_vertical_slab",
            () -> new VerticalSlabBlock(BlockBehaviour.Properties.copy(POLISHED_DARKSLATE_SLAB.get())));
    public static final RegistryObject<Block> DARKSLATE_BRICKS = registerBlock("darkslate_bricks",
            () -> new Block(Block.Properties.of().requiresCorrectToolForDrops().strength(1.5f, 6.0f).sound(SoundType.STONE)));
    public static final RegistryObject<Block> DARKSLATE_BRICKS_STAIRS = registerBlock("darkslate_bricks_stairs",
            () -> new StairBlock(DARKSLATE_BRICKS.get().defaultBlockState(), Block.Properties.of().requiresCorrectToolForDrops().strength(1.5f, 6.0f).sound(SoundType.STONE)));
    public static final RegistryObject<Block> DARKSLATE_BRICKS_SLAB = registerBlock("darkslate_bricks_slab",
            () -> new SlabBlock(Block.Properties.of().requiresCorrectToolForDrops().strength(1.5f, 6.0f).sound(SoundType.STONE)));
    public static final RegistryObject<Block> DARKSLATE_BRICKS_VERTICAL_SLAB = registerCompatBlock("quark", "darkslate_bricks_vertical_slab",
            () -> new VerticalSlabBlock(Block.Properties.of().requiresCorrectToolForDrops().strength(1.5f, 6.0f).sound(SoundType.STONE)));
    public static final RegistryObject<Block> CHISELED_DARKSLATE_BRICKS = registerBlock("chiseled_darkslate_bricks",
            () -> new Block(Block.Properties.of().requiresCorrectToolForDrops().strength(1.5f, 6.0f).sound(SoundType.STONE)));
    public static final RegistryObject<Block> CRACKED_DARKSLATE_BRICKS = registerBlock("cracked_darkslate_bricks",
            () -> new Block(Block.Properties.of().requiresCorrectToolForDrops().strength(1.5f, 6.0f).sound(SoundType.STONE)));
    public static final RegistryObject<Block> DARKSLATE_BRICKS_WALL = registerBlock("darkslate_bricks_wall",
            () -> new WallBlock(Block.Properties.of().requiresCorrectToolForDrops().strength(1.5f, 6.0f).sound(SoundType.STONE)));
    public static final RegistryObject<Block> ASH_BLOCK = registerBlock("ash_block",
            () -> new AshFullBlock(Block.Properties.of().mapColor(MapColor.COLOR_BLACK).requiresCorrectToolForDrops().strength(0.2f).sound(SoundType.SNOW).randomTicks()));
    public static final RegistryObject<Block> GEYSER = registerBlock("geyser",
            () -> new GeyserBlock(BlockBehaviour.Properties.of().sound(SoundType.DEEPSLATE).strength(1.5F, 6.0F).requiresCorrectToolForDrops().randomTicks()));
    public static final RegistryObject<Block> ASH = registerBlock("ash",
            () -> new AshBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).randomTicks().strength(0.1f).requiresCorrectToolForDrops().sound(SoundType.SNOW)));
    public static final RegistryObject<Block> ARIDROCK = registerBlock("aridrock",
            () -> new RotatedPillarBlock(Block.Properties.of().mapColor(MapColor.SAND).requiresCorrectToolForDrops().strength(1.5f, 6.0f).sound(SoundType.STONE)));
    public static final RegistryObject<Block> ARIDROCK_STAIRS = registerBlock("aridrock_stairs",
            () -> new StairBlock(() -> ARIDROCK.get().defaultBlockState(), Block.Properties.of().mapColor(MapColor.SAND).requiresCorrectToolForDrops().strength(1.5f, 6.0f).sound(SoundType.STONE)));
    public static final RegistryObject<Block> ARIDROCK_SLAB = registerBlock("aridrock_slab",
            () -> new SlabBlock(Block.Properties.of().mapColor(MapColor.SAND).requiresCorrectToolForDrops().strength(1.5f, 6.0f).sound(SoundType.STONE)));
    public static final RegistryObject<Block> ARIDROCK_VERTICAL_SLAB = registerCompatBlock("quark", "aridrock_vertical_slab",
            () -> new VerticalSlabBlock(Block.Properties.copy(ARIDROCK_SLAB.get())));
    public static final RegistryObject<Block> ARIDROCK_WALL = registerBlock("aridrock_wall",
            () -> new WallBlock(Block.Properties.of().mapColor(MapColor.SAND).requiresCorrectToolForDrops().strength(1.5f, 6.0f).sound(SoundType.STONE)));
    public static final RegistryObject<Block> POLISHED_ARIDROCK = registerBlock("polished_aridrock",
            () -> new Block(Block.Properties.of().mapColor(MapColor.SAND).requiresCorrectToolForDrops().strength(1.5f, 6.0f).sound(SoundType.STONE)));
    public static final RegistryObject<Block> POLISHED_ARIDROCK_STAIRS = registerBlock("polished_aridrock_stairs",
            () -> new StairBlock(() -> POLISHED_ARIDROCK.get().defaultBlockState(), Block.Properties.of().mapColor(MapColor.SAND).requiresCorrectToolForDrops().strength(1.5f, 6.0f).sound(SoundType.STONE)));
    public static final RegistryObject<Block> POLISHED_ARIDROCK_SLAB = registerBlock("polished_aridrock_slab",
            () -> new SlabBlock(Block.Properties.of().mapColor(MapColor.SAND).requiresCorrectToolForDrops().strength(1.5f, 6.0f).sound(SoundType.STONE)));
    public static final RegistryObject<Block> POLISHED_ARIDROCK_VERTICAL_SLAB = registerCompatBlock("quark", "polished_aridrock_vertical_slab",
            () -> new VerticalSlabBlock(Block.Properties.copy(POLISHED_ARIDROCK_SLAB.get())));
    public static final RegistryObject<Block> ARIDROCK_BRICKS = registerBlock("aridrock_bricks",
            () -> new Block(BlockBehaviour.Properties.of().sound(SoundType.STONE).strength(1.5F, 6.0F).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> ARIDROCK_BRICKS_STAIRS = registerBlock("aridrock_bricks_stairs",
            () -> new StairBlock(ARIDROCK_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.of().sound(SoundType.STONE).strength(1.5F, 6.0F).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> ARIDROCK_BRICKS_SLAB = registerBlock("aridrock_bricks_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.of().sound(SoundType.STONE).strength(1.5F, 6.0F).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> ARIDROCK_BRICKS_VERTICAL_SLAB = registerCompatBlock("quark", "aridrock_bricks_vertical_slab",
            () -> new VerticalSlabBlock(BlockBehaviour.Properties.copy(ARIDROCK_BRICKS_SLAB.get())));
    public static final RegistryObject<Block> ARIDROCK_PILLAR = registerBlock("aridrock_pillar",
            () -> new ConnectedPillarBlock(BlockBehaviour.Properties.of().sound(SoundType.STONE).strength(1.5F, 6.0F).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> CHISELED_ARIDROCK_BRICKS = registerBlock("chiseled_aridrock_bricks",
            () -> new Block(Block.Properties.of().requiresCorrectToolForDrops().strength(1.5f, 6.0f).sound(SoundType.STONE)));
    public static final RegistryObject<Block> CRACKED_ARIDROCK_BRICKS = registerBlock("cracked_aridrock_bricks",
            () -> new Block(BlockBehaviour.Properties.of().sound(SoundType.STONE).strength(1.5F, 6.0F).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> ARIDROCK_BRICKS_WALL = registerBlock("aridrock_bricks_wall",
            () -> new WallBlock(Block.Properties.of().mapColor(MapColor.SAND).requiresCorrectToolForDrops().strength(1.5f, 6.0f).sound(SoundType.STONE)));
    public static final RegistryObject<Block> SKULL_WALL = registerBlock("skull_wall",
            () -> new Block(BlockBehaviour.Properties.of().sound(SoundType.STONE).strength(1.5F, 6.0F).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> DUSKROCK = registerBlock("duskrock",
            () -> new LimestoneBlock(Block.Properties.of().requiresCorrectToolForDrops().strength(1.5f, 6.0f).sound(SoundType.TUFF)));
    public static final RegistryObject<Block> DUSKROCK_STAIRS = registerBlock("duskrock_stairs",
            () -> new StairBlock(() -> DUSKROCK.get().defaultBlockState(), Block.Properties.of().requiresCorrectToolForDrops().strength(1.5f, 6.0f).sound(SoundType.STONE)));
    public static final RegistryObject<Block> DUSKROCK_SLAB = registerBlock("duskrock_slab",
            () -> new SlabBlock(Block.Properties.of().requiresCorrectToolForDrops().strength(1.5f, 6.0f).sound(SoundType.STONE)));
    public static final RegistryObject<Block> DUSKROCK_VERTICAL_SLAB = registerCompatBlock("quark", "duskrock_vertical_slab",
            () -> new VerticalSlabBlock(BlockBehaviour.Properties.copy(DUSKROCK_SLAB.get())));
    public static final RegistryObject<Block> DUSKROCK_WALL = registerBlock("duskrock_wall",
            () -> new WallBlock(Block.Properties.of().requiresCorrectToolForDrops().strength(1.5f, 6.0f).sound(SoundType.STONE)));
    public static final RegistryObject<Block> POLISHED_DUSKROCK = registerBlock("polished_duskrock",
            () -> new Block(Block.Properties.of().requiresCorrectToolForDrops().strength(1.5f, 6.0f).sound(SoundType.STONE)));
    public static final RegistryObject<Block> POLISHED_DUSKROCK_STAIRS = registerBlock("polished_duskrock_stairs",
            () -> new StairBlock(() -> POLISHED_DUSKROCK.get().defaultBlockState(), Block.Properties.of().requiresCorrectToolForDrops().strength(1.5f, 6.0f).sound(SoundType.STONE)));
    public static final RegistryObject<Block> POLISHED_DUSKROCK_SLAB = registerBlock("polished_duskrock_slab",
            () -> new SlabBlock(Block.Properties.of().requiresCorrectToolForDrops().strength(1.5f, 6.0f).sound(SoundType.STONE)));
    public static final RegistryObject<Block> POLISHED_DUSKROCK_VERTICAL_SLAB = registerCompatBlock("quark", "polished_duskrock_vertical_slab",
            () -> new VerticalSlabBlock(BlockBehaviour.Properties.copy(POLISHED_DUSKROCK_SLAB.get())));
    public static final RegistryObject<Block> DUSKROCK_BRICKS_WALL = registerBlock("duskrock_bricks_wall",
            () -> new WallBlock(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(1.5F, 6.0F).requiresCorrectToolForDrops().sound(SoundType.STONE)));
    public static final RegistryObject<Block> DUSKROCK_BRICKS = registerBlock("duskrock_bricks",
            () -> new Block(Block.Properties.of().requiresCorrectToolForDrops().strength(1.5f, 6.0f).sound(SoundType.STONE)));
    public static final RegistryObject<Block> DUSKROCK_BRICKS_STAIRS = registerBlock("duskrock_bricks_stairs",
            () -> new StairBlock(() -> POLISHED_DUSKROCK.get().defaultBlockState(), Block.Properties.of().requiresCorrectToolForDrops().strength(1.5f, 6.0f).sound(SoundType.STONE)));
    public static final RegistryObject<Block> DUSKROCK_BRICKS_SLAB = registerBlock("duskrock_bricks_slab",
            () -> new SlabBlock(Block.Properties.of().requiresCorrectToolForDrops().strength(1.5f, 6.0f).sound(SoundType.STONE)));
    public static final RegistryObject<Block> DUSKROCK_BRICKS_VERTICAL_SLAB = registerCompatBlock("quark", "duskrock_bricks_vertical_slab",
            () -> new VerticalSlabBlock(BlockBehaviour.Properties.copy(ARIDROCK_BRICKS_SLAB.get())));
    public static final RegistryObject<Block> CHISELED_DUSKROCK_BRICKS = registerBlock("chiseled_duskrock_bricks",
            () -> new Block(Block.Properties.of().requiresCorrectToolForDrops().strength(1.5f, 6.0f).sound(SoundType.STONE)));
    public static final RegistryObject<Block> CRACKED_DUSKROCK_BRICKS = registerBlock("cracked_duskrock_bricks",
            () -> new Block(BlockBehaviour.Properties.of().sound(SoundType.STONE).strength(1.5F, 6.0F).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> ARID_DEEPSLATE = registerBlock("arid_deepslate",
            () -> new LayeredDeepslateBlock(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE).randomTicks()));
    public static final RegistryObject<Block> VOID_SOUL_TORCH = registerNoTabBlock("void_soul_torch",
            () -> new VoidSoulTorchBlock(BlockBehaviour.Properties.of().strength(0.0F, 1.0F).noCollission().sound(SoundType.WOOD).lightLevel(state -> 10), DDParticleTypes.VOID_SOUL_FLAME::get));
    public static final RegistryObject<Block> WALL_VOID_SOUL_TORCH = registerNoTabBlock("wall_void_soul_torch",
            () -> new WallVoidSoulTorchBlock(BlockBehaviour.Properties.of().strength(0.0F, 1.0F).noCollission().sound(SoundType.WOOD).lootFrom(VOID_SOUL_TORCH).lightLevel(state -> 12), DDParticleTypes.VOID_SOUL_FLAME::get));
    public static final RegistryObject<Block> PETRIFIED_ROOTS = registerBlock("petrified_roots",
            () -> new PetrifiedRootBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).offsetType(BlockBehaviour.OffsetType.XZ).noCollission().instabreak().sound(SoundType.HANGING_ROOTS)));
    public static final RegistryObject<Block> PETRIFIED_ROOTS_PLANT = registerNoTabBlock("petrified_roots_plant",
            () -> new PetrifiedRootPlantBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).offsetType(BlockBehaviour.OffsetType.XZ).noCollission().instabreak().sound(SoundType.HANGING_ROOTS)));
    public static final RegistryObject<Block> DRY_SPROUTS = registerBlock("dry_sprouts",
            () -> new DrySproutsBlock(BlockBehaviour.Properties.copy(Blocks.DEAD_BUSH).offsetType(BlockBehaviour.OffsetType.XZ)));
    public static final RegistryObject<Block> GRIMESTONE = registerBlock("grimestone",
            () -> new GrimestoneBlock(BlockBehaviour.Properties.of().strength(1.5F, 6.0F).sound(SoundType.DEEPSLATE).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> GRIMESTONE_STAIRS = registerBlock("grimestone_stairs",
            () -> new StairBlock(GRIMESTONE.get().defaultBlockState(), BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(1.5F, 6.0F).sound(DDSoundEvents.GRIMESTONE)));
    public static final RegistryObject<Block> GRIMESTONE_SLAB = registerBlock("grimestone_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.of().strength(1.5F, 6.0F).sound(SoundType.DEEPSLATE).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> GRIMESTONE_VERTICAL_SLAB = registerCompatBlock("quark", "grimestone_vertical_slab",
            () -> new VerticalSlabBlock(BlockBehaviour.Properties.copy(GRIMESTONE_SLAB.get())));
    public static final RegistryObject<Block> GRIMESTONE_WALL = registerBlock("grimestone_wall",
            () -> new WallBlock(BlockBehaviour.Properties.of().sound(SoundType.DEEPSLATE).strength(1.5F, 6.0F).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> MOSSY_GRIMESTONE = registerBlock("mossy_grimestone",
            () -> new MossyGrimestoneBlock(BlockBehaviour.Properties.of().strength(1.5F, 6.0F).requiresCorrectToolForDrops().randomTicks().sound(SoundType.DEEPSLATE)));
    public static final RegistryObject<Block> POLISHED_GRIMESTONE = registerBlock("polished_grimestone",
            () -> new Block(BlockBehaviour.Properties.of().strength(1.5F, 6.0F)));
    public static final RegistryObject<Block> POLISHED_GRIMESTONE_STAIRS = registerBlock("polished_grimestone_stairs",
            () -> new StairBlock(POLISHED_GRIMESTONE.get().defaultBlockState(), BlockBehaviour.Properties.of().strength(1.5F, 6.0F)));
    public static final RegistryObject<Block> POLISHED_GRIMESTONE_SLAB = registerBlock("polished_grimestone_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.of().strength(1.5F, 6.0F)));
    public static final RegistryObject<Block> POLISHED_GRIMESTONE_VERTICAL_SLAB = registerCompatBlock("quark", "polished_grimestone_vertical_slab",
            () -> new VerticalSlabBlock(Block.Properties.copy(POLISHED_GRIMESTONE_SLAB.get())));
    public static final RegistryObject<Block> CHISELED_GRIMESTONE_BRICKS = registerBlock("chiseled_grimestone_bricks",
            () -> new Block(BlockBehaviour.Properties.of().sound(SoundType.STONE).strength(1.5F, 6.0F).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> GRIMESTONE_BRICKS = registerBlock("grimestone_bricks",
            () -> new Block(BlockBehaviour.Properties.of().sound(SoundType.STONE).strength(1.5F, 6.0F).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> GRIMESTONE_BRICKS_STAIRS = registerBlock("grimestone_bricks_stairs",
            () -> new StairBlock(GRIMESTONE_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.of().sound(SoundType.STONE).strength(1.5F, 6.0F).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> GRIMESTONE_BRICKS_SLAB = registerBlock("grimestone_bricks_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.of().sound(SoundType.STONE).strength(1.5F, 6.0F).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> GRIMESTONE_BRICKS_VERTICAL_SLAB = registerCompatBlock("quark", "grimestone_bricks_vertical_slab",
            () -> new VerticalSlabBlock(BlockBehaviour.Properties.copy(GRIMESTONE_BRICKS_SLAB.get())));
    public static final RegistryObject<Block> CRACKED_GRIMESTONE_BRICKS = registerBlock("cracked_grimestone_bricks",
            () -> new Block(BlockBehaviour.Properties.of().sound(SoundType.STONE).strength(1.5F, 6.0F).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> GRIMESTONE_BRICKS_WALL = registerBlock("grimestone_bricks_wall",
            () -> new WallBlock(BlockBehaviour.Properties.of().sound(SoundType.STONE).strength(1.5F, 6.0F).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> STONE_MELON = registerBlock("stone_melon",
            () -> new StoneMelonBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_GRAY).requiresCorrectToolForDrops().strength(1.0f).sound(SoundType.DEEPSLATE)));
    public static final RegistryObject<Block> DEAD_LIVING_CRYSTAL = registerBlock("dead_living_crystal",
            () -> new DeadLivingCrystalBlock(BlockBehaviour.Properties.of().sound(SoundType.DEEPSLATE).strength(1.5F, 6.0F).requiresCorrectToolForDrops().randomTicks()));
    public static final RegistryObject<Block> LIVING_CRYSTAL = registerBlock("living_crystal",
            () -> new LivingCrystalBlock(BlockBehaviour.Properties.of().sound(SoundType.DEEPSLATE).strength(1.5F, 6.0F).randomTicks().requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> CRYSTAL_MELON = registerBlock("crystal_melon",
            () -> new CrystalMelonBlock(BlockBehaviour.Properties.of().strength(1.5F, 1F).sound(SoundType.AMETHYST).lightLevel(value -> 10).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> GLOWSHROOM = registerBlock("glowshroom",
            () -> new GlowshroomBlock(BlockBehaviour.Properties.of().strength(0.0F, 1.0F).sound(SoundType.SLIME_BLOCK).lightLevel((state) -> 3 + (2 * state.getValue(GlowshroomBlock.CLUSTERS_1_3))).noCollission()));
    public static final RegistryObject<Block> GLOWSPURS = registerBlock("glowspurs",
            () -> new GlowspursBlock(BlockBehaviour.Properties.of().instabreak().lightLevel(value -> 5).sound(SoundType.SLIME_BLOCK).noCollission()));
    public static final RegistryObject<Block> MOSSY_SPROUTS = registerBlock("mossy_sprouts",
            () -> new SproutsBlock(BlockBehaviour.Properties.copy(Blocks.GRASS).sound(SoundType.WET_GRASS).offsetType(BlockBehaviour.OffsetType.XZ)));
    public static final RegistryObject<Block> POTTED_GLOWSHROOM = registerNoTabBlock("potted_glowshroom",
            () -> new FlowerPotBlock(GLOWSHROOM.get(), BlockBehaviour.Properties.of().instabreak().noOcclusion()));
    public static final RegistryObject<Block> GLIMMERING_VINES = registerBlock("glimmering_vines",
            () -> new GlimmeringVinesBlock(BlockBehaviour.Properties.of().noCollission().lightLevel(value -> 8).sound(SoundType.SPORE_BLOSSOM)));
    public static final RegistryObject<Block> GLIMMERING_VINE_PLANT = registerNoTabBlock("glimmering_vine_plant",
            () -> new GlimmeringVinePlantBlock(BlockBehaviour.Properties.copy(GLIMMERING_VINES.get()).sound(SoundType.SPORE_BLOSSOM)));
    public static final RegistryObject<Block> GLOWSHROOM_BLOCK = registerBlock("glowshroom_block",
            () -> new Block(BlockBehaviour.Properties.of().strength(0.5F).sound(SoundType.SLIME_BLOCK)));
    public static final RegistryObject<Block> GLOWSHROOM_STEM = registerBlock("glowshroom_stem",
            () -> new RotatedPillarBlock(BlockBehaviour.Properties.of().strength(1.2f, 2.0f).sound(SoundType.STEM)));
    public static final RegistryObject<Block> GLOWSHROOM_HEART = registerBlock("glowshroom_heart",
            () -> new Block(BlockBehaviour.Properties.of().strength(2.0f, 3.0f).sound(SoundType.SHROOMLIGHT).lightLevel(value -> 15)));
    public static final RegistryObject<Block> GLOWSHROOM_LANTERN = registerBlock("glowshroom_lantern",
            () -> new GlowshroomLanternBlock(BlockBehaviour.Properties.copy(Blocks.LANTERN)));
    public static final RegistryObject<Block> GLOWSHROOM_LAMP = registerBlock("glowshroom_lamp",
            () -> new GlowshroomLampBlock(BlockBehaviour.Properties.of().strength(0.3f, 0.3f).lightLevel(state -> state.getValue(BlockStateProperties.LIT) ? 15 : 0).sound(SoundType.GLASS)));
    public static final RegistryObject<Block> ROPE = registerNoTabBlock("rope",
            () -> new RopeBlock(BlockBehaviour.Properties.of().strength(0.1F).sound(SoundType.WOOL)));
    public static final RegistryObject<Block> AMBER_BLOCK = registerBlock("amber_block",
            () -> new Block(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(1.0f).sound(SoundType.AMETHYST).lightLevel(value -> 7)));
    public static final RegistryObject<Block> AMBER_CLUSTER = registerBlock("amber_cluster",
            () -> new AmethystClusterBlock(6, 3, BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(1.0f).sound(SoundType.SMALL_AMETHYST_BUD).lightLevel(value -> 7)));
    public static final RegistryObject<Block> VOID_SOUL_JAR = registerNoTabBlock("void_soul_jar",
            () -> new VoidSoulJarBlock(BlockBehaviour.Properties.of().strength(0.8f).sound(SoundType.GLASS).lightLevel(value -> 5)));
    public static final RegistryObject<Block> DEATH_ANCHOR = registerBlock("death_anchor",
            () -> new DeathAnchorBlock(BlockBehaviour.Properties.of().strength(5.0F, 12.0F).sound(SoundType.NETHERITE_BLOCK).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> TOMB = registerBlock("tomb",
            () -> new TombBlock(BlockBehaviour.Properties.of().sound(SoundType.DEEPSLATE).strength(4F, 10.0F).requiresCorrectToolForDrops().noOcclusion()));
    public static final RegistryObject<Block> PARANOIA_ALTAR = registerNoTabBlock("paranoia_altar",
            () -> new ParanoiaAltarBlock(BlockBehaviour.Properties.of().sound(SoundType.DEEPSLATE).strength(4F, 10.0F).lightLevel(level -> 9).requiresCorrectToolForDrops().noOcclusion()));
    public static final RegistryObject<Block> FORSAKEN_BRONZE_BLOCK = registerBlock("forsaken_bronze_block",
            () -> new Block(BlockBehaviour.Properties.of().strength(5.0F, 12.0F).sound(SoundType.NETHERITE_BLOCK).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> MOB_PLACER = registerNoTabBlock("mob_placer",
            () -> new MobPlacerBlock(Block.Properties.copy(Blocks.BEDROCK)));


    public static <B extends Block> RegistryObject<B> registerBlock(String name, Supplier<? extends B> blocks) {
        RegistryObject<B> block = BLOCKS.register(name, blocks);
        DDItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
        return block;
    }

    public static <B extends Block> RegistryObject<B> registerNoTabBlock(String name, Supplier<? extends B> blocks) {
        return BLOCKS.register(name, blocks);
    }

    public static <B extends Block> RegistryObject<B> registerCompatBlock(String modId, String key, Supplier<? extends B> block) {
        RegistryObject<B> blocks = BLOCKS.register(key, block);
        RegistryObject<BlockItem> item = DDItems.ITEMS.register(key, () -> new BlockItem(blocks.get(), new Item.Properties()));
        COMPAT.put(item, modId);
        return blocks;
    }

}