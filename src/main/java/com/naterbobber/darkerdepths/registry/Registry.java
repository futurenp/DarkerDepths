package com.naterbobber.darkerdepths.registry;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.DarkerDepthsItemGroup;
import com.naterbobber.darkerdepths.blocks.*;
import com.naterbobber.darkerdepths.entities.PetrifiedBoatEntity;
import com.naterbobber.darkerdepths.init.BlockInit;
import com.naterbobber.darkerdepths.init.ItemInit;
import com.naterbobber.darkerdepths.items.DDBoatItem;
import com.naterbobber.darkerdepths.items.RopeItem;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.item.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class Registry {

    public static final ItemGroup DARKER_DEPTHS = new DarkerDepthsItemGroup("DarkerDepths");

    // ITEM REGISTRATION
    @SubscribeEvent
    public static void onItemsRegistry(final RegistryEvent.Register<Item> itemRegistryEvent)
    {
        itemRegistryEvent.getRegistry().registerAll
                (
                        //petrified wood
                        ItemInit.petrified_planks = new BlockItem(BlockInit.petrified_planks, new Item.Properties().group(DARKER_DEPTHS)).setRegistryName(BlockInit.petrified_planks.getRegistryName()),
                        ItemInit.petrified_log = new BlockItem(BlockInit.petrified_log, new Item.Properties().group(DARKER_DEPTHS)).setRegistryName(BlockInit.petrified_log.getRegistryName()),
                        ItemInit.stripped_petrified_log = new BlockItem(BlockInit.stripped_petrified_log, new Item.Properties().group(DARKER_DEPTHS)).setRegistryName(BlockInit.stripped_petrified_log.getRegistryName()),
                        ItemInit.stripped_petrified_wood = new BlockItem(BlockInit.stripped_petrified_wood, new Item.Properties().group(DARKER_DEPTHS)).setRegistryName(BlockInit.stripped_petrified_wood.getRegistryName()),
                        ItemInit.petrified_wood = new BlockItem(BlockInit.petrified_wood, new Item.Properties().group(DARKER_DEPTHS)).setRegistryName(BlockInit.petrified_wood.getRegistryName()),
                        ItemInit.petrified_slab = new BlockItem(BlockInit.petrified_slab, new Item.Properties().group(DARKER_DEPTHS)).setRegistryName(BlockInit.petrified_slab.getRegistryName()),
                        ItemInit.petrified_pressure_plate = new BlockItem(BlockInit.petrified_pressure_plate, new Item.Properties().group(DARKER_DEPTHS)).setRegistryName(BlockInit.petrified_pressure_plate.getRegistryName()),
                        ItemInit.petrified_fence = new BlockItem(BlockInit.petrified_fence, new Item.Properties().group(DARKER_DEPTHS)).setRegistryName(BlockInit.petrified_fence.getRegistryName()),
                        ItemInit.petrified_trapdoor = new BlockItem(BlockInit.petrified_trapdoor, new Item.Properties().group(DARKER_DEPTHS)).setRegistryName(BlockInit.petrified_trapdoor.getRegistryName()),
                        ItemInit.petrified_fence_gate = new BlockItem(BlockInit.petrified_fence_gate, new Item.Properties().group(DARKER_DEPTHS)).setRegistryName(BlockInit.petrified_fence_gate.getRegistryName()),
                        ItemInit.petrified_button = new BlockItem(BlockInit.petrified_button, new Item.Properties().group(DARKER_DEPTHS)).setRegistryName(BlockInit.petrified_button.getRegistryName()),
                        ItemInit.petrified_stairs = new BlockItem(BlockInit.petrified_stairs, new Item.Properties().group(DARKER_DEPTHS)).setRegistryName(BlockInit.petrified_stairs.getRegistryName()),
                        ItemInit.petrified_door = new BlockItem(BlockInit.petrified_door, new Item.Properties().group(DARKER_DEPTHS)).setRegistryName(BlockInit.petrified_door.getRegistryName()),
                        ItemInit.petrified_boat = new DDBoatItem(PetrifiedBoatEntity.Type.Petrified, (new Item.Properties().maxStackSize(1).group(DARKER_DEPTHS))).setRegistryName(location("petrified_boat")),
                        //limestone
                        ItemInit.limestone = new BlockItem(BlockInit.limestone, new Item.Properties().group(DARKER_DEPTHS)).setRegistryName(BlockInit.limestone.getRegistryName()),
                        ItemInit.polished_limestone = new BlockItem(BlockInit.polished_limestone, new Item.Properties().group(DARKER_DEPTHS)).setRegistryName(BlockInit.polished_limestone.getRegistryName()),
                        ItemInit.limestone_wall = new BlockItem(BlockInit.limestone_wall, new Item.Properties().group(DARKER_DEPTHS)).setRegistryName(BlockInit.limestone_wall.getRegistryName()),
                        ItemInit.polished_limestone_stairs = new BlockItem(BlockInit.polished_limestone_stairs, new Item.Properties().group(DARKER_DEPTHS)).setRegistryName(BlockInit.polished_limestone_stairs.getRegistryName()),
                        ItemInit.limestone_stairs = new BlockItem(BlockInit.limestone_stairs, new Item.Properties().group(DARKER_DEPTHS)).setRegistryName(BlockInit.limestone_stairs.getRegistryName()),
                        ItemInit.polished_limestone_slab = new BlockItem(BlockInit.polished_limestone_slab, new Item.Properties().group(DARKER_DEPTHS)).setRegistryName(BlockInit.polished_limestone_slab.getRegistryName()),
                        ItemInit.limestone_slab = new BlockItem(BlockInit.limestone_slab, new Item.Properties().group(DARKER_DEPTHS)).setRegistryName(BlockInit.limestone_slab.getRegistryName()),
                        //shale
                        ItemInit.shale = new BlockItem(BlockInit.shale, new Item.Properties().group(DARKER_DEPTHS)).setRegistryName(BlockInit.shale.getRegistryName()),
                        ItemInit.polished_shale = new BlockItem(BlockInit.polished_shale, new Item.Properties().group(DARKER_DEPTHS)).setRegistryName(BlockInit.polished_shale.getRegistryName()),
                        ItemInit.shale_wall = new BlockItem(BlockInit.shale_wall, new Item.Properties().group(DARKER_DEPTHS)).setRegistryName(BlockInit.shale_wall.getRegistryName()),
                        ItemInit.polished_shale_stairs = new BlockItem(BlockInit.polished_shale_stairs, new Item.Properties().group(DARKER_DEPTHS)).setRegistryName(BlockInit.polished_shale_stairs.getRegistryName()),
                        ItemInit.shale_stairs = new BlockItem(BlockInit.shale_stairs, new Item.Properties().group(DARKER_DEPTHS)).setRegistryName(BlockInit.shale_stairs.getRegistryName()),
                        ItemInit.polished_shale_slab = new BlockItem(BlockInit.polished_shale_slab, new Item.Properties().group(DARKER_DEPTHS)).setRegistryName(BlockInit.polished_shale_slab.getRegistryName()),
                        ItemInit.shale_slab = new BlockItem(BlockInit.shale_slab, new Item.Properties().group(DARKER_DEPTHS)).setRegistryName(BlockInit.shale_slab.getRegistryName()),
                        //Crystal Blocks
                        ItemInit.amethyst_crystal_block = new BlockItem(BlockInit.amethyst_crystal_block, new Item.Properties().group(DARKER_DEPTHS)).setRegistryName(BlockInit.amethyst_crystal_block.getRegistryName()),
                        ItemInit.celestine_crystal_block = new BlockItem(BlockInit.celestine_crystal_block, new Item.Properties().group(DARKER_DEPTHS)).setRegistryName(BlockInit.celestine_crystal_block.getRegistryName()),
                        ItemInit.redstone_crystal_block = new BlockItem(BlockInit.redstone_crystal_block, new Item.Properties().group(DARKER_DEPTHS)).setRegistryName(BlockInit.redstone_crystal_block.getRegistryName()),
                        //Crystals
                        ItemInit.amethyst_crystal = new BlockItem(BlockInit.amethyst_crystal, new Item.Properties().group(DARKER_DEPTHS)).setRegistryName(BlockInit.amethyst_crystal.getRegistryName()),
                        ItemInit.celestine_crystal = new BlockItem(BlockInit.celestine_crystal, new Item.Properties().group(DARKER_DEPTHS)).setRegistryName(BlockInit.celestine_crystal.getRegistryName()),
                        ItemInit.redstone_crystal = new BlockItem(BlockInit.redstone_crystal, new Item.Properties().group(DARKER_DEPTHS)).setRegistryName(BlockInit.redstone_crystal.getRegistryName()),
                        ItemInit.crystal_melon = new BlockItem(BlockInit.crystal_melon, new Item.Properties().group(DARKER_DEPTHS)).setRegistryName(BlockInit.crystal_melon.getRegistryName()),
                        //Silver
                        ItemInit.silver_ore = new BlockItem(BlockInit.silver_ore, new Item.Properties().group(DARKER_DEPTHS)).setRegistryName(BlockInit.silver_ore.getRegistryName()),
                        ItemInit.silver_ingot = new Item(new Item.Properties().group(DARKER_DEPTHS)).setRegistryName(location("silver_ingot")),
                        //misc blocks
                        ItemInit.stone_brick_pillar = new BlockItem(BlockInit.stone_brick_pillar, new Item.Properties().group(DARKER_DEPTHS)).setRegistryName(BlockInit.stone_brick_pillar.getRegistryName()),
                        ItemInit.elytrine_ore = new BlockItem(BlockInit.elytrine_ore, new Item.Properties().group(DARKER_DEPTHS)).setRegistryName(BlockInit.elytrine_ore.getRegistryName()),
                        ItemInit.glowshroom = new BlockItem(BlockInit.glowshroom, new Item.Properties().group(DARKER_DEPTHS)).setRegistryName(BlockInit.glowshroom.getRegistryName()),
                        ItemInit.glowshroom_cap = new com.naterbobber.darkerdepths.items.GlowshroomCap(BlockInit.glowshroom_cap, new Item.Properties().group(DARKER_DEPTHS)).setRegistryName(BlockInit.glowshroom_cap.getRegistryName()),
                        ItemInit.speleothem = new BlockItem(BlockInit.speleothem, new Item.Properties().group(DARKER_DEPTHS)).setRegistryName(BlockInit.speleothem.getRegistryName()),
                        ItemInit.rope = new RopeItem(BlockInit.rope, new Item.Properties().group(DARKER_DEPTHS)).setRegistryName(BlockInit.rope.getRegistryName()),
                        ItemInit.spawner_fragment = new Item(new Item.Properties().group(DARKER_DEPTHS)).setRegistryName(location("spawner_fragment")),
                        //misc items
                        ItemInit.elytrine_crystal = new Item(new Item.Properties().group(DARKER_DEPTHS)).setRegistryName(location("elytrine_crystal"))
                        );
        DarkerDepths.LOGGER.info("Items registered.");
    }

    // BLOCK REGISTRATION
    @SubscribeEvent
    public static void onBlocksRegistry(final RegistryEvent.Register<Block> BlockRegistryEvent)
    {
        BlockRegistryEvent.getRegistry().registerAll
                (
                        //petrified wood
                        BlockInit.petrified_planks = new Block(Block.Properties.create(Material.ROCK).hardnessAndResistance(2.5f, 3.0f).harvestTool(ToolType.AXE).harvestLevel(0).sound(SoundType.WOOD)).setRegistryName(location("petrified_planks")),
                        BlockInit.petrified_log = new RotatedPillarBlock(RotatedPillarBlock.Properties.create(Material.ROCK).hardnessAndResistance(2.5f, 3.0f).harvestTool(ToolType.AXE).harvestLevel(0).sound(SoundType.WOOD)).setRegistryName(location("petrified_log")),
                        BlockInit.stripped_petrified_log = new RotatedPillarBlock(RotatedPillarBlock.Properties.create(Material.ROCK).hardnessAndResistance(2.5f, 3.0f).harvestTool(ToolType.AXE).harvestLevel(0).sound(SoundType.WOOD)).setRegistryName(location("stripped_petrified_log")),
                        BlockInit.stripped_petrified_wood = new RotatedPillarBlock(RotatedPillarBlock.Properties.create(Material.ROCK).hardnessAndResistance(2.5f, 3.0f).harvestTool(ToolType.AXE).harvestLevel(0).sound(SoundType.WOOD)).setRegistryName(location("stripped_petrified_wood")),
                        BlockInit.petrified_wood = new RotatedPillarBlock(RotatedPillarBlock.Properties.create(Material.ROCK).hardnessAndResistance(2.5f, 3.0f).harvestTool(ToolType.AXE).harvestLevel(0).sound(SoundType.WOOD)).setRegistryName(location("petrified_wood")),
                        BlockInit.petrified_slab = new SlabBlock(SlabBlock.Properties.from(BlockInit.petrified_planks)).setRegistryName(location("petrified_slab")),
                        BlockInit.petrified_pressure_plate = new CustomPressurePlateBlock(CustomPressurePlateBlock.Sensitivity.EVERYTHING, CustomPressurePlateBlock.Properties.create(Material.ROCK).hardnessAndResistance(0.5f).harvestTool(ToolType.AXE).sound(SoundType.WOOD)).setRegistryName(location("petrified_pressure_plate")),
                        BlockInit.petrified_fence = new FenceBlock(FenceBlock.Properties.create(Material.ROCK).hardnessAndResistance(2.5f, 3.0f).harvestTool(ToolType.AXE).sound(SoundType.WOOD)).setRegistryName(location("petrified_fence")),
                        BlockInit.petrified_trapdoor = new CustomTrapDoorBlock(CustomTrapDoorBlock.Properties.create(Material.ROCK).hardnessAndResistance(2.5f, 3.0f).harvestTool(ToolType.AXE).notSolid().sound(SoundType.WOOD)).setRegistryName(location("petrified_trapdoor")),
                        BlockInit.petrified_fence_gate = new FenceGateBlock(FenceGateBlock.Properties.from(BlockInit.petrified_fence)).setRegistryName(location("petrified_fence_gate")),
                        BlockInit.petrified_button = new CustomWoodButtonBlock(CustomWoodButtonBlock.Properties.create(Material.ROCK).hardnessAndResistance(0.5f).harvestTool(ToolType.AXE).sound(SoundType.WOOD)).setRegistryName(location("petrified_button")),
                        BlockInit.petrified_stairs = new StairsBlock(() -> BlockInit.petrified_planks.getDefaultState(), StairsBlock.Properties.from(BlockInit.petrified_planks)).setRegistryName(location("petrified_stairs")),
                        BlockInit.petrified_door = new CustomDoorBlock(CustomDoorBlock.Properties.create(Material.ROCK).hardnessAndResistance(2.5f, 3.0f).harvestTool(ToolType.AXE).notSolid().sound(SoundType.WOOD)).setRegistryName(location("petrified_door")),
                        //limestone
                        BlockInit.limestone = new Block(Block.Properties.create(Material.ROCK).hardnessAndResistance(1.5f, 6.0f).harvestTool(ToolType.PICKAXE).harvestLevel(0).sound(SoundType.STONE)).setRegistryName(location("limestone")),
                        BlockInit.polished_limestone = new Block(Block.Properties.create(Material.ROCK).hardnessAndResistance(1.5f, 6.0f).harvestTool(ToolType.PICKAXE).harvestLevel(0).sound(SoundType.STONE)).setRegistryName(location("polished_limestone")),
                        BlockInit.limestone_wall = new WallBlock(WallBlock.Properties.create(Material.ROCK).hardnessAndResistance(1.5f, 6.0f).harvestTool(ToolType.PICKAXE).sound(SoundType.STONE)).setRegistryName(location("limestone_wall")),
                        BlockInit.polished_limestone_stairs = new StairsBlock(() -> BlockInit.polished_limestone.getDefaultState(), StairsBlock.Properties.from(BlockInit.polished_limestone)).setRegistryName(location("polished_limestone_stairs")),
                        BlockInit.limestone_stairs = new StairsBlock(() -> BlockInit.limestone.getDefaultState(), StairsBlock.Properties.from(BlockInit.limestone)).setRegistryName(location("limestone_stairs")),
                        BlockInit.polished_limestone_slab = new SlabBlock(SlabBlock.Properties.from(BlockInit.polished_limestone)).setRegistryName(location("polished_limestone_slab")),
                        BlockInit.limestone_slab = new SlabBlock(SlabBlock.Properties.from(BlockInit.limestone)).setRegistryName(location("limestone_slab")),
                        //shale
                        BlockInit.shale = new Block(Block.Properties.create(Material.ROCK).hardnessAndResistance(1.5f, 6.0f).harvestTool(ToolType.PICKAXE).harvestLevel(0).sound(SoundType.STONE)).setRegistryName(location("shale")),
                        BlockInit.polished_shale = new Block(Block.Properties.create(Material.ROCK).hardnessAndResistance(1.5f, 6.0f).harvestTool(ToolType.PICKAXE).harvestLevel(0).sound(SoundType.STONE)).setRegistryName(location("polished_shale")),
                        BlockInit.shale_wall = new WallBlock(WallBlock.Properties.create(Material.ROCK).hardnessAndResistance(1.5f, 6.0f).harvestTool(ToolType.PICKAXE).sound(SoundType.STONE)).setRegistryName(location("shale_wall")),
                        BlockInit.polished_shale_stairs = new StairsBlock(() -> BlockInit.polished_shale.getDefaultState(), StairsBlock.Properties.from(BlockInit.polished_shale)).setRegistryName(location("polished_shale_stairs")),
                        BlockInit.shale_stairs = new StairsBlock(() -> BlockInit.shale.getDefaultState(), StairsBlock.Properties.from(BlockInit.shale)).setRegistryName(location("shale_stairs")),
                        BlockInit.polished_shale_slab = new SlabBlock(SlabBlock.Properties.from(BlockInit.polished_shale)).setRegistryName(location("polished_shale_slab")),
                        BlockInit.shale_slab = new SlabBlock(SlabBlock.Properties.from(BlockInit.shale)).setRegistryName(location("shale_slab")),
                        //Crystals
                        BlockInit.crystal_melon = new CrystalMelon().setRegistryName(location("crystal_melon")),
                        BlockInit.amethyst_crystal = new AmethystCrystal().setRegistryName(location("amethyst_crystal")),
                        BlockInit.amethyst_crystal_block = new Block(Block.Properties.create(Material.ROCK).hardnessAndResistance(1.0f, 1.0f).harvestTool(ToolType.PICKAXE).harvestLevel(2).sound(SoundType.GLASS).setLightLevel(value -> 10)).setRegistryName(location("amethyst_crystal_block")),
                        BlockInit.celestine_crystal = new CelestineCrystal().setRegistryName(location("celestine_crystal")),
                        BlockInit.celestine_crystal_block = new Block(Block.Properties.create(Material.ROCK).hardnessAndResistance(1.0f, 1.0f).harvestTool(ToolType.PICKAXE).harvestLevel(2).sound(SoundType.GLASS).setLightLevel(value -> 10)).setRegistryName(location("celestine_crystal_block")),
                        BlockInit.redstone_crystal = new RedstoneCrystal().setRegistryName(location("redstone_crystal")),
                        BlockInit.redstone_crystal_block = new Block(Block.Properties.create(Material.ROCK).hardnessAndResistance(1.0f, 1.0f).harvestTool(ToolType.PICKAXE).harvestLevel(2).sound(SoundType.GLASS).setLightLevel(value -> 10)).setRegistryName(location("redstone_crystal_block")),
                        //Silver
                        BlockInit.silver_ore = new Block(Block.Properties.create(Material.ROCK).hardnessAndResistance(2.5f, 6.0f).harvestTool(ToolType.PICKAXE).harvestLevel(2).sound(SoundType.STONE)).setRegistryName(location("silver_ore")),
                        //misc blocks
                        BlockInit.stone_brick_pillar = new RotatedPillarBlock(RotatedPillarBlock.Properties.create(Material.ROCK).hardnessAndResistance(1.5f, 6.0f).harvestTool(ToolType.PICKAXE).harvestLevel(0).sound(SoundType.STONE)).setRegistryName(location("stone_brick_pillar")),
                        BlockInit.elytrine_ore = new Block(Block.Properties.create(Material.ROCK).hardnessAndResistance(2.5f, 6.0f).harvestTool(ToolType.PICKAXE).harvestLevel(3).sound(SoundType.STONE)).setRegistryName(location("elytrine_ore")),
                        BlockInit.glowshroom = new Glowshroom().setRegistryName(location("glowshroom")),
                        BlockInit.glowshroom_cap = new GlowshroomCap().setRegistryName(location("glowshroom_cap")),
                        BlockInit.speleothem = new Speleothem().setRegistryName(location("speleothem")),
                        BlockInit.rope = new RopeBlock(RopeBlock.Properties.create(Material.MISCELLANEOUS).zeroHardnessAndResistance().sound(SoundType.CLOTH)).setRegistryName(location("rope"))
                );
        DarkerDepths.LOGGER.info("Blocks registered.");
    }

    public static ResourceLocation location(String name)
    {
        return new ResourceLocation(DarkerDepths.MODID, name);
    }
}
