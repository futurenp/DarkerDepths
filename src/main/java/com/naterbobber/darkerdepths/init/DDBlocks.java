package com.naterbobber.darkerdepths.init;

import com.google.common.collect.Maps;
import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.block.DDBlockSetTypes;
import com.naterbobber.darkerdepths.block.custom.*;
import com.naterbobber.darkerdepths.block.generic.*;
import com.naterbobber.darkerdepths.item.BlockItemWithHoverText;
import net.minecraft.ChatFormatting;
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
import net.minecraft.network.chat.Component;

import java.util.Map;
import java.util.List;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = DarkerDepths.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DDBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, DarkerDepths.MODID);
    public static final Map<RegistryObject<? extends Item>, String> COMPAT = Maps.newLinkedHashMap();


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


    public static final RegistryObject<Block> PETRIFIED_LOG = registerBlock("petrified_log",
            () -> new RotatedPillarBlock(PETRIFIED_LOG_PROPERTIES));
    public static final RegistryObject<Block> PETRIFIED_WOOD = registerBlock("petrified_wood",
            () -> new RotatedPillarBlock(PETRIFIED_LOG_PROPERTIES));
    public static final RegistryObject<Block> STRIPPED_PETRIFIED_LOG = registerBlock("stripped_petrified_log",
            () -> new RotatedPillarBlock(PETRIFIED_LOG_PROPERTIES));
    public static final RegistryObject<Block> STRIPPED_PETRIFIED_WOOD = registerBlock("stripped_petrified_wood",
            () -> new RotatedPillarBlock(PETRIFIED_LOG_PROPERTIES));
    public static final RegistryObject<Block> PETRIFIED_PLANKS = registerBlock("petrified_planks",
            () -> new Block(PETRIFIED_PLANKS_PROPERTIES));
    public static final RegistryObject<Block> VERTICAL_PETRIFIED_PLANKS = registerCompatBlock("quark", "vertical_petrified_planks",
            () -> new Block(PETRIFIED_PLANKS_PROPERTIES));
    public static final RegistryObject<Block> PETRIFIED_STAIRS = registerBlock("petrified_stairs",
            () -> new StairBlock(() -> PETRIFIED_PLANKS.get().defaultBlockState(), PETRIFIED_PLANKS_PROPERTIES));
    public static final RegistryObject<Block> PETRIFIED_SLAB = registerBlock("petrified_slab",
            () -> new SlabBlock(PETRIFIED_PLANKS_PROPERTIES));
    public static final RegistryObject<Block> PETRIFIED_VERTICAL_SLAB = registerCompatBlock("quark", "petrified_vertical_slab",
            () -> new VerticalSlabBlock(PETRIFIED_PLANKS_PROPERTIES));
    public static final RegistryObject<Block> PETRIFIED_FENCE = registerBlock("petrified_fence",
            () -> new FenceBlock(PETRIFIED_PLANKS_PROPERTIES));
    public static final RegistryObject<Block> PETRIFIED_FENCE_GATE = registerBlock("petrified_fence_gate",
            () -> new FenceGateBlock(PETRIFIED_PLANKS_PROPERTIES, DDWoodType.PETRIFIED));
    public static final RegistryObject<Block> PETRIFIED_DOOR = registerBlock("petrified_door",
            () -> new DoorBlock(PETRIFIED_PLANKS_PROPERTIES, DDBlockSetTypes.PETRIFIED));
    public static final RegistryObject<Block> PETRIFIED_TRAPDOOR = registerBlock("petrified_trapdoor",
            () -> new TrapDoorBlock(PETRIFIED_PLANKS_PROPERTIES, DDBlockSetTypes.PETRIFIED));
    public static final RegistryObject<Block> PETRIFIED_PRESSURE_PLATE = registerBlock("petrified_pressure_plate",
            () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING , PETRIFIED_BUTTON_PROPERTIES, DDBlockSetTypes.PETRIFIED));
    public static final RegistryObject<Block> PETRIFIED_BUTTON = registerBlock("petrified_button",
            () -> new ButtonBlock(PETRIFIED_BUTTON_PROPERTIES, DDBlockSetTypes.PETRIFIED, 30, true));
    public static final RegistryObject<Block> PETRIFIED_SIGN = registerNoTabBlock("petrified_sign",
            () -> new DDStandingSignBlock(PETRIFIED_SIGN_PROPERTIES, DDWoodType.PETRIFIED));
    public static final RegistryObject<Block> PETRIFIED_WALL_SIGN = registerNoTabBlock("petrified_wall_sign",
            () -> new DDWallSignBlock(PETRIFIED_SIGN_PROPERTIES, DDWoodType.PETRIFIED));
    public static final RegistryObject<Block> PETRIFIED_HANGING_SIGN = registerNoTabBlock("petrified_hanging_sign",
            () -> new DDCeilingHangingSignBlock(PETRIFIED_SIGN_PROPERTIES, DDWoodType.PETRIFIED));
    public static final RegistryObject<Block> PETRIFIED_WALL_HANGING_SIGN = registerNoTabBlock("petrified_wall_hanging_sign",
            () -> new DDWallHangingSignBlock(PETRIFIED_SIGN_PROPERTIES, DDWoodType.PETRIFIED));
    public static final RegistryObject<Block> PETRIFIED_POST = registerCompatBlock("quark", "petrified_post",
            () -> new WoodPostBlock(PETRIFIED_LOG_PROPERTIES.noOcclusion()));
    public static final RegistryObject<Block> STRIPPED_PETRIFIED_POST = registerCompatBlock("quark", "stripped_petrified_post",
            () -> new WoodPostBlock(PETRIFIED_LOG_PROPERTIES.noOcclusion()));
    public static final RegistryObject<Block> POROUS_PETRIFIED_LOG = registerBlock("porous_petrified_log",
            () -> new PorousBlock(PETRIFIED_LOG_PROPERTIES.randomTicks().lightLevel(value -> 6)));
    public static final RegistryObject<Block> ARIDROCK = registerBlock("aridrock",
            () -> new RotatedPillarBlock(ARIDROCK_PROPERTIES));
    public static final RegistryObject<Block> ARIDROCK_STAIRS = registerBlock("aridrock_stairs",
            () -> new StairBlock(() -> ARIDROCK.get().defaultBlockState(), ARIDROCK_PROPERTIES));
    public static final RegistryObject<Block> ARIDROCK_SLAB = registerBlock("aridrock_slab",
            () -> new SlabBlock(ARIDROCK_PROPERTIES));
    public static final RegistryObject<Block> ARIDROCK_VERTICAL_SLAB = registerCompatBlock("quark", "aridrock_vertical_slab",
            () -> new VerticalSlabBlock(ARIDROCK_PROPERTIES));
    public static final RegistryObject<Block> ARIDROCK_WALL = registerBlock("aridrock_wall",
            () -> new WallBlock(ARIDROCK_PROPERTIES));
    public static final RegistryObject<Block> POLISHED_ARIDROCK = registerBlock("polished_aridrock",
            () -> new Block(ARIDROCK_PROPERTIES));
    public static final RegistryObject<Block> POLISHED_ARIDROCK_STAIRS = registerBlock("polished_aridrock_stairs",
            () -> new StairBlock(() -> POLISHED_ARIDROCK.get().defaultBlockState(), ARIDROCK_PROPERTIES));
    public static final RegistryObject<Block> POLISHED_ARIDROCK_SLAB = registerBlock("polished_aridrock_slab",
            () -> new SlabBlock(ARIDROCK_PROPERTIES));
    public static final RegistryObject<Block> POLISHED_ARIDROCK_VERTICAL_SLAB = registerCompatBlock("quark", "polished_aridrock_vertical_slab",
            () -> new VerticalSlabBlock(ARIDROCK_PROPERTIES));
    public static final RegistryObject<Block> ARIDROCK_BRICKS = registerBlock("aridrock_bricks",
            () -> new Block(ARIDROCK_BRICKS_PROPERTIES));
    public static final RegistryObject<Block> ARIDROCK_BRICKS_STAIRS = registerBlock("aridrock_bricks_stairs",
            () -> new StairBlock(ARIDROCK_BRICKS.get().defaultBlockState(), ARIDROCK_BRICKS_PROPERTIES));
    public static final RegistryObject<Block> ARIDROCK_BRICKS_SLAB = registerBlock("aridrock_bricks_slab",
            () -> new SlabBlock(ARIDROCK_BRICKS_PROPERTIES));
    public static final RegistryObject<Block> ARIDROCK_BRICKS_VERTICAL_SLAB = registerCompatBlock("quark", "aridrock_bricks_vertical_slab",
            () -> new VerticalSlabBlock(ARIDROCK_BRICKS_PROPERTIES));
    public static final RegistryObject<Block> ARIDROCK_BRICKS_WALL = registerBlock("aridrock_bricks_wall",
            () -> new WallBlock(ARIDROCK_BRICKS_PROPERTIES));
    public static final RegistryObject<Block> CHISELED_ARIDROCK_BRICKS = registerBlock("chiseled_aridrock_bricks",
            () -> new Block(ARIDROCK_BRICKS_PROPERTIES));
    public static final RegistryObject<Block> CRACKED_ARIDROCK_BRICKS = registerBlock("cracked_aridrock_bricks",
            () -> new Block(ARIDROCK_BRICKS_PROPERTIES));
    public static final RegistryObject<Block> ARIDROCK_PILLAR = registerBlock("aridrock_pillar",
            () -> new ConnectedPillarBlock(ARIDROCK_BRICKS_PROPERTIES));
    public static final RegistryObject<Block> SKULL_WALL = registerBlock("skull_wall",
            () -> new Block(ARIDROCK_BRICKS_PROPERTIES));
    public static final RegistryObject<Block> DUSKROCK = registerBlock("duskrock",
            () -> new DuskrockBlock(DUSKROCK_PROPERTIES));
    public static final RegistryObject<Block> DUSKROCK_STAIRS = registerBlock("duskrock_stairs",
            () -> new StairBlock(() -> DUSKROCK.get().defaultBlockState(), DUSKROCK_PROPERTIES));
    public static final RegistryObject<Block> DUSKROCK_SLAB = registerBlock("duskrock_slab",
            () -> new SlabBlock(DUSKROCK_PROPERTIES));
    public static final RegistryObject<Block> DUSKROCK_VERTICAL_SLAB = registerCompatBlock("quark", "duskrock_vertical_slab",
            () -> new VerticalSlabBlock(DUSKROCK_PROPERTIES));
    public static final RegistryObject<Block> DUSKROCK_WALL = registerBlock("duskrock_wall",
            () -> new WallBlock(DUSKROCK_PROPERTIES));
    public static final RegistryObject<Block> POLISHED_DUSKROCK = registerBlock("polished_duskrock",
            () -> new Block(DUSKROCK_BRICKS_PROPERTIES));
    public static final RegistryObject<Block> POLISHED_DUSKROCK_STAIRS = registerBlock("polished_duskrock_stairs",
            () -> new StairBlock(() -> POLISHED_DUSKROCK.get().defaultBlockState(), DUSKROCK_BRICKS_PROPERTIES));
    public static final RegistryObject<Block> POLISHED_DUSKROCK_SLAB = registerBlock("polished_duskrock_slab",
            () -> new SlabBlock(DUSKROCK_BRICKS_PROPERTIES));
    public static final RegistryObject<Block> POLISHED_DUSKROCK_VERTICAL_SLAB = registerCompatBlock("quark", "polished_duskrock_vertical_slab",
            () -> new VerticalSlabBlock(DUSKROCK_BRICKS_PROPERTIES));
    public static final RegistryObject<Block> DUSKROCK_BRICKS = registerBlock("duskrock_bricks",
            () -> new Block(DUSKROCK_BRICKS_PROPERTIES));
    public static final RegistryObject<Block> DUSKROCK_BRICKS_STAIRS = registerBlock("duskrock_bricks_stairs",
            () -> new StairBlock(() -> POLISHED_DUSKROCK.get().defaultBlockState(), DUSKROCK_BRICKS_PROPERTIES));
    public static final RegistryObject<Block> DUSKROCK_BRICKS_SLAB = registerBlock("duskrock_bricks_slab",
            () -> new SlabBlock(DUSKROCK_BRICKS_PROPERTIES));
    public static final RegistryObject<Block> DUSKROCK_BRICKS_VERTICAL_SLAB = registerCompatBlock("quark", "duskrock_bricks_vertical_slab",
            () -> new VerticalSlabBlock(DUSKROCK_BRICKS_PROPERTIES));
    public static final RegistryObject<Block> DUSKROCK_BRICKS_WALL = registerBlock("duskrock_bricks_wall",
            () -> new WallBlock(DUSKROCK_BRICKS_PROPERTIES));
    public static final RegistryObject<Block> CHISELED_DUSKROCK_BRICKS = registerBlock("chiseled_duskrock_bricks",
            () -> new Block(DUSKROCK_BRICKS_PROPERTIES));
    public static final RegistryObject<Block> CRACKED_DUSKROCK_BRICKS = registerBlock("cracked_duskrock_bricks",
            () -> new Block(DUSKROCK_BRICKS_PROPERTIES));
    public static final RegistryObject<Block> VOID_SOUL_JAR = registerNoTabBlock("void_soul_jar",
            () -> new VoidSoulJarBlock(BlockBehaviour.Properties.of().strength(0.8f).sound(SoundType.GLASS).lightLevel(value -> 5)));
    public static final RegistryObject<Block> FORSAKEN_BRONZE_BLOCK = registerBlock("forsaken_bronze_block",
            () -> new Block(FORSAKEN_BRONZE_PROPERTIES));
    public static final RegistryObject<Block> DEATH_ANCHOR = registerTooltipBlock("death_anchor",
            () -> new DeathAnchorBlock(FORSAKEN_BRONZE_PROPERTIES),
            List.of(Component.translatable("tooltip.darkerdepths.death_anchor.shift_desc_1").withStyle(ChatFormatting.GOLD),
                    Component.translatable("tooltip.darkerdepths.death_anchor.shift_desc_2").withStyle(ChatFormatting.GOLD)));
    public static final RegistryObject<Block> TOMB = registerBlock("tomb",
            () -> new TombBlock(blockProperties(4.0f, 10.0f, SoundType.DEEPSLATE, true).noOcclusion()));
    public static final RegistryObject<Block> PARANOIA_ALTAR = registerNoTabBlock("paranoia_altar",
            () -> new ParanoiaAltarBlock(blockProperties(2.5f, 3.0f, SoundType.DEEPSLATE, true).lightLevel(level -> 9).noOcclusion()));
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
    public static final RegistryObject<Block> AMBER_BLOCK = registerBlock("amber_block",
            () -> new Block(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(1.0f).sound(SoundType.AMETHYST).lightLevel(value -> 7)));
    public static final RegistryObject<Block> AMBER_CLUSTER = registerBlock("amber_cluster",
            () -> new AmethystClusterBlock(6, 3, BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(1.0f).sound(SoundType.SMALL_AMETHYST_BUD).lightLevel(value -> 7)));
    public static final RegistryObject<Block> DARKSLATE = registerBlock("darkslate",
            () -> new RotatedPillarBlock(DARKSLATE_PROPERTIES));
    public static final RegistryObject<Block> DARKSLATE_STAIRS = registerBlock("darkslate_stairs",
            () -> new StairBlock(() -> DARKSLATE.get().defaultBlockState(), DARKSLATE_PROPERTIES));
    public static final RegistryObject<Block> DARKSLATE_SLAB = registerBlock("darkslate_slab",
            () -> new SlabBlock(DARKSLATE_PROPERTIES));
    public static final RegistryObject<Block> DARKSLATE_VERTICAL_SLAB = registerCompatBlock("quark", "darkslate_vertical_slab",
            () -> new VerticalSlabBlock(DARKSLATE_PROPERTIES));
    public static final RegistryObject<Block> DARKSLATE_WALL = registerBlock("darkslate_wall",
            () -> new WallBlock(DARKSLATE_PROPERTIES));
    public static final RegistryObject<Block> POLISHED_DARKSLATE = registerBlock("polished_darkslate",
            () -> new Block(DARKSLATE_PROPERTIES));
    public static final RegistryObject<Block> POLISHED_DARKSLATE_STAIRS = registerBlock("polished_darkslate_stairs",
            () -> new StairBlock(() -> POLISHED_DARKSLATE.get().defaultBlockState(), DARKSLATE_PROPERTIES));
    public static final RegistryObject<Block> POLISHED_DARKSLATE_SLAB = registerBlock("polished_darkslate_slab",
            () -> new SlabBlock(DARKSLATE_PROPERTIES));
    public static final RegistryObject<Block> POLISHED_DARKSLATE_VERTICAL_SLAB = registerCompatBlock("quark", "polished_darkslate_vertical_slab",
            () -> new VerticalSlabBlock(DARKSLATE_PROPERTIES));
    public static final RegistryObject<Block> DARKSLATE_BRICKS = registerBlock("darkslate_bricks",
            () -> new Block(DARKSLATE_BRICKS_PROPERTIES));
    public static final RegistryObject<Block> DARKSLATE_BRICKS_STAIRS = registerBlock("darkslate_bricks_stairs",
            () -> new StairBlock(DARKSLATE_BRICKS.get().defaultBlockState(), DARKSLATE_BRICKS_PROPERTIES));
    public static final RegistryObject<Block> DARKSLATE_BRICKS_SLAB = registerBlock("darkslate_bricks_slab",
            () -> new SlabBlock(DARKSLATE_BRICKS_PROPERTIES));
    public static final RegistryObject<Block> DARKSLATE_BRICKS_VERTICAL_SLAB = registerCompatBlock("quark", "darkslate_bricks_vertical_slab",
            () -> new VerticalSlabBlock(DARKSLATE_BRICKS_PROPERTIES));
    public static final RegistryObject<Block> DARKSLATE_BRICKS_WALL = registerBlock("darkslate_bricks_wall",
            () -> new WallBlock(DARKSLATE_BRICKS_PROPERTIES));
    public static final RegistryObject<Block> CHISELED_DARKSLATE_BRICKS = registerBlock("chiseled_darkslate_bricks",
            () -> new Block(DARKSLATE_BRICKS_PROPERTIES));
    public static final RegistryObject<Block> CRACKED_DARKSLATE_BRICKS = registerBlock("cracked_darkslate_bricks",
            () -> new Block(DARKSLATE_BRICKS_PROPERTIES));
    public static final RegistryObject<Block> GEYSER = registerBlock("geyser",
            () -> new GeyserBlock(DARKSLATE_PROPERTIES.randomTicks()));
    public static final RegistryObject<Block> STONE_MELON = registerBlock("stone_melon",
            () -> new StoneMelonBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_GRAY).requiresCorrectToolForDrops().strength(1.0f).sound(SoundType.DEEPSLATE)));
    public static final RegistryObject<Block> DEAD_LIVING_CRYSTAL = registerBlock("dead_living_crystal",
            () -> new DeadLivingCrystalBlock(LIVING_CRYSTAL_PROPERTIES));
    public static final RegistryObject<Block> LIVING_CRYSTAL = registerBlock("living_crystal",
            () -> new LivingCrystalBlock(LIVING_CRYSTAL_PROPERTIES));
    public static final RegistryObject<Block> CRYSTAL_MELON = registerTooltipBlock("crystal_melon",
            () -> new CrystalMelonBlock(blockProperties(1.5f, 1.0f, SoundType.AMETHYST, true).lightLevel(value -> 10)),
            List.of(Component.translatable("tooltip.darkerdepths.crystal_melon.shift_desc_1").withStyle(ChatFormatting.AQUA),
                    Component.translatable("tooltip.darkerdepths.crystal_melon.shift_desc_2").withStyle(ChatFormatting.AQUA)
            )
    );
    public static final RegistryObject<Block> MAGMA_PAD = registerNoTabBlock("magma_pad",
            () -> new MagmaPadBlock(BlockBehaviour.Properties.of().strength(0.1F).lightLevel(state -> 3).sound(DDSoundEvents.GRIMESTONE).noOcclusion().pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<Block> ASH_BLOCK = registerBlock("ash_block",
            () -> new AshFullBlock(blockProperties(0.2f, SoundType.SNOW, false).mapColor(MapColor.COLOR_BLACK).randomTicks()));
    public static final RegistryObject<Block> ASH = registerBlock("ash",
            () -> new AshBlock(blockProperties(0.1f, SoundType.SNOW, true).mapColor(MapColor.COLOR_BLACK).randomTicks()));
    public static final RegistryObject<Block> GRIMESTONE = registerBlock("grimestone",
            () -> new GrimestoneBlock(DARKSLATE_PROPERTIES));
    public static final RegistryObject<Block> GRIMESTONE_STAIRS = registerBlock("grimestone_stairs",
            () -> new StairBlock(GRIMESTONE.get().defaultBlockState(), DARKSLATE_PROPERTIES));
    public static final RegistryObject<Block> GRIMESTONE_SLAB = registerBlock("grimestone_slab",
            () -> new SlabBlock(DARKSLATE_PROPERTIES));
    public static final RegistryObject<Block> GRIMESTONE_VERTICAL_SLAB = registerCompatBlock("quark", "grimestone_vertical_slab",
            () -> new VerticalSlabBlock(DARKSLATE_PROPERTIES));
    public static final RegistryObject<Block> GRIMESTONE_WALL = registerBlock("grimestone_wall",
            () -> new WallBlock(DARKSLATE_PROPERTIES));
    public static final RegistryObject<Block> MOSSY_GRIMESTONE = registerBlock("mossy_grimestone",
            () -> new MossyGrimestoneBlock(DARKSLATE_PROPERTIES));
    public static final RegistryObject<Block> POLISHED_GRIMESTONE = registerBlock("polished_grimestone",
            () -> new Block(DARKSLATE_PROPERTIES));
    public static final RegistryObject<Block> POLISHED_GRIMESTONE_STAIRS = registerBlock("polished_grimestone_stairs",
            () -> new StairBlock(POLISHED_GRIMESTONE.get().defaultBlockState(), DARKSLATE_PROPERTIES));
    public static final RegistryObject<Block> POLISHED_GRIMESTONE_SLAB = registerBlock("polished_grimestone_slab",
            () -> new SlabBlock(DARKSLATE_PROPERTIES));
    public static final RegistryObject<Block> POLISHED_GRIMESTONE_VERTICAL_SLAB = registerCompatBlock("quark", "polished_grimestone_vertical_slab",
            () -> new VerticalSlabBlock(DARKSLATE_PROPERTIES));
    public static final RegistryObject<Block> GRIMESTONE_BRICKS = registerBlock("grimestone_bricks",
            () -> new Block(DARKSLATE_BRICKS_PROPERTIES));
    public static final RegistryObject<Block> GRIMESTONE_BRICKS_STAIRS = registerBlock("grimestone_bricks_stairs",
            () -> new StairBlock(GRIMESTONE_BRICKS.get().defaultBlockState(), DARKSLATE_BRICKS_PROPERTIES));
    public static final RegistryObject<Block> GRIMESTONE_BRICKS_SLAB = registerBlock("grimestone_bricks_slab",
            () -> new SlabBlock(DARKSLATE_BRICKS_PROPERTIES));
    public static final RegistryObject<Block> GRIMESTONE_BRICKS_VERTICAL_SLAB = registerCompatBlock("quark", "grimestone_bricks_vertical_slab",
            () -> new VerticalSlabBlock(DARKSLATE_BRICKS_PROPERTIES));
    public static final RegistryObject<Block> GRIMESTONE_BRICKS_WALL = registerBlock("grimestone_bricks_wall",
            () -> new WallBlock(DARKSLATE_BRICKS_PROPERTIES));
    public static final RegistryObject<Block> CHISELED_GRIMESTONE_BRICKS = registerBlock("chiseled_grimestone_bricks",
            () -> new Block(DARKSLATE_BRICKS_PROPERTIES));
    public static final RegistryObject<Block> CRACKED_GRIMESTONE_BRICKS = registerBlock("cracked_grimestone_bricks",
            () -> new Block(DARKSLATE_BRICKS_PROPERTIES));
    public static final RegistryObject<Block> GLOWSHROOM_BLOCK = registerBlock("glowshroom_block",
            () -> new Block(BlockBehaviour.Properties.of().strength(0.5F).sound(SoundType.SLIME_BLOCK)));
    public static final RegistryObject<Block> GLOWSHROOM_STEM = registerBlock("glowshroom_stem",
            () -> new RotatedPillarBlock(BlockBehaviour.Properties.of().strength(1.2f, 2.0f).sound(SoundType.STEM)));
    public static final RegistryObject<Block> GLOWSHROOM_HEART = registerBlock("glowshroom_heart",
            () -> new Block(BlockBehaviour.Properties.of().strength(2.0f, 3.0f).sound(SoundType.SHROOMLIGHT).lightLevel(value -> 15)));
    public static final RegistryObject<Block> GLOWSHROOM = registerBlock("glowshroom",
            () -> new GlowshroomBlock(BlockBehaviour.Properties.of().strength(0.0F, 1.0F).sound(SoundType.SLIME_BLOCK).lightLevel((state) -> 3 + (2 * state.getValue(GlowshroomBlock.GLOWSHROOM_CLUSTERS))).noCollission()));
    public static final RegistryObject<Block> POTTED_GLOWSHROOM = registerNoTabBlock("potted_glowshroom",
            () -> new FlowerPotBlock(GLOWSHROOM.get(), BlockBehaviour.Properties.of().instabreak().noOcclusion()));
    public static final RegistryObject<Block> GLOWSPURS = registerBlock("glowspurs",
            () -> new GlowspursBlock(BlockBehaviour.Properties.of().instabreak().lightLevel(value -> 5).sound(SoundType.SLIME_BLOCK).noCollission()));
    public static final RegistryObject<Block> MOSSY_SPROUTS = registerBlock("mossy_sprouts",
            () -> new SproutsBlock(BlockBehaviour.Properties.copy(Blocks.GRASS).sound(SoundType.WET_GRASS).offsetType(BlockBehaviour.OffsetType.XZ)));
    public static final RegistryObject<Block> GLIMMERING_VINES = registerBlock("glimmering_vines",
            () -> new GlimmeringVinesBlock(BlockBehaviour.Properties.of().noCollission().lightLevel(value -> 8).sound(SoundType.SPORE_BLOSSOM)));
    public static final RegistryObject<Block> GLIMMERING_VINE_PLANT = registerNoTabBlock("glimmering_vine_plant",
            () -> new GlimmeringVinePlantBlock(BlockBehaviour.Properties.copy(GLIMMERING_VINES.get()).sound(SoundType.SPORE_BLOSSOM)));
    public static final RegistryObject<Block> GLOWSHROOM_LAMP = registerBlock("glowshroom_lamp",
            () -> new GlowshroomLampBlock(BlockBehaviour.Properties.of().strength(0.3f, 0.3f).lightLevel(state -> state.getValue(BlockStateProperties.LIT) ? 15 : 0).sound(SoundType.GLASS)));
    public static final RegistryObject<Block> GLOWSHROOM_LANTERN = registerBlock("glowshroom_lantern",
            () -> new GlowshroomLanternBlock(BlockBehaviour.Properties.copy(Blocks.LANTERN)));
    public static final RegistryObject<Block> ROPE = registerNoTabBlock("rope",
            () -> new RopeBlock(blockProperties(0.1f, SoundType.WOOL, false)));
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

    public static <B extends Block> RegistryObject<B> registerTooltipBlock(String name, Supplier<? extends B> blocks, List<Component> tooltips) {
        RegistryObject<B> block = BLOCKS.register(name, blocks);
        DDItems.ITEMS.register(name, () -> new BlockItemWithHoverText(block.get(), new Item.Properties(), tooltips));
        return block;
    }

    public static <B extends Block> RegistryObject<B> registerCompatBlock(String modId, String key, Supplier<? extends B> block) {
        RegistryObject<B> blocks = BLOCKS.register(key, block);
        RegistryObject<BlockItem> item = DDItems.ITEMS.register(key, () -> new BlockItem(blocks.get(), new Item.Properties()));
        COMPAT.put(item, modId);
        return blocks;
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