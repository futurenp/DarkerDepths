package com.naterbobber.darkerdepths.core.registries;

import com.naterbobber.darkerdepths.common.blocks.*;
import com.naterbobber.darkerdepths.core.DarkerDepths;

import com.naterbobber.darkerdepths.core.RegistryHelper;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;

//<>

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class DDBlocks {
	public static final RegistryHelper HELPER = DarkerDepths.REGISTRY_HELPER;

	//BUILDING BLOCKS
	public static final RegistryObject<Block> PETRIFIED_PLANKS 			= HELPER.createBlock("petrified_planks", () -> new Block(Block.Properties.create(Material.ROCK).hardnessAndResistance(2.5f, 3.0f).harvestTool(ToolType.PICKAXE).harvestLevel(0).sound(SoundType.WOOD)), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> PETRIFIED_LOG 			= HELPER.createBlock("petrified_log", () -> new RotatedPillarBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(2.4f, 3.0f).harvestTool(ToolType.PICKAXE).harvestLevel(0).sound(SoundType.WOOD)), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> STRIPPED_PETRIFIED_LOG 	= HELPER.createBlock("stripped_petrified_log", () -> new RotatedPillarBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(2.4f, 3.0f).harvestTool(ToolType.PICKAXE).harvestLevel(0).sound(SoundType.WOOD)), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> STRIPPED_PETRIFIED_WOOD 	= HELPER.createBlock("stripped_petrified_wood", () -> new RotatedPillarBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(2.4f, 3.0f).harvestTool(ToolType.PICKAXE).harvestLevel(0).sound(SoundType.WOOD)), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> PETRIFIED_WOOD 			= HELPER.createBlock("petrified_wood", () -> new RotatedPillarBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(2.4f, 3.0f).harvestTool(ToolType.PICKAXE).harvestLevel(0).sound(SoundType.WOOD)), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> PETRIFIED_SLAB 			= HELPER.createBlock("petrified_slab", () -> new SlabBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(2.5f, 3.0f).harvestTool(ToolType.PICKAXE).harvestLevel(0).sound(SoundType.WOOD)), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> PETRIFIED_STAIRS 			= HELPER.createBlock("petrified_stairs", () -> new StairsBlock(() -> PETRIFIED_PLANKS.get().getDefaultState(), Block.Properties.create(Material.ROCK).hardnessAndResistance(2.5f, 3.0f).harvestTool(ToolType.PICKAXE).harvestLevel(0).sound(SoundType.WOOD)), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> LIMESTONE		 			= HELPER.createBlock("limestone", () -> new Block(Block.Properties.create(Material.ROCK).hardnessAndResistance(1.5f, 6.0f).harvestTool(ToolType.PICKAXE).sound(SoundType.STONE)), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> POLISHED_LIMESTONE		= HELPER.createBlock("polished_limestone", () -> new Block(Block.Properties.create(Material.ROCK).hardnessAndResistance(1.5f, 6.0f).harvestTool(ToolType.PICKAXE).sound(SoundType.STONE)), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> LIMESTONE_SLAB 			= HELPER.createBlock("limestone_slab", () -> new SlabBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(1.5f, 6.0f).harvestTool(ToolType.PICKAXE).sound(SoundType.STONE)), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> POLISHED_LIMESTONE_SLAB 	= HELPER.createBlock("polished_limestone_slab", () -> new SlabBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(1.5f, 6.0f).harvestTool(ToolType.PICKAXE).sound(SoundType.STONE)), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> LIMESTONE_STAIRS 			= HELPER.createBlock("limestone_stairs", () -> new StairsBlock(() -> LIMESTONE.get().getDefaultState(), Block.Properties.create(Material.ROCK).hardnessAndResistance(1.5f, 6.0f).harvestTool(ToolType.PICKAXE).sound(SoundType.WOOD)), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> POLISHED_LIMESTONE_STAIRS = HELPER.createBlock("polished_limestone_stairs", () -> new StairsBlock(() -> POLISHED_LIMESTONE.get().getDefaultState(), Block.Properties.create(Material.ROCK).hardnessAndResistance(1.5f, 6.0f).harvestTool(ToolType.PICKAXE).sound(SoundType.WOOD)), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> SHALE			 			= HELPER.createBlock("shale", () -> new Block(Block.Properties.create(Material.ROCK).hardnessAndResistance(1.5f, 6.0f).harvestTool(ToolType.PICKAXE).sound(SoundType.STONE)), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> POLISHED_SHALE			= HELPER.createBlock("polished_shale", () -> new Block(Block.Properties.create(Material.ROCK).hardnessAndResistance(1.5f, 6.0f).harvestTool(ToolType.PICKAXE).sound(SoundType.STONE)), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> SHALE_SLAB 				= HELPER.createBlock("shale_slab", () -> new SlabBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(1.5f, 6.0f).harvestTool(ToolType.PICKAXE).sound(SoundType.STONE)), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> POLISHED_SHALE_SLAB 		= HELPER.createBlock("polished_shale_slab", () -> new SlabBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(1.5f, 6.0f).harvestTool(ToolType.PICKAXE).sound(SoundType.STONE)), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> SHALE_STAIRS 				= HELPER.createBlock("shale_stairs", () -> new StairsBlock(() -> SHALE.get().getDefaultState(), Block.Properties.create(Material.ROCK).hardnessAndResistance(1.5f, 6.0f).harvestTool(ToolType.PICKAXE).sound(SoundType.WOOD)), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> POLISHED_SHALE_STAIRS 	= HELPER.createBlock("polished_shale_stairs", () -> new StairsBlock(() -> POLISHED_SHALE.get().getDefaultState(), Block.Properties.create(Material.ROCK).hardnessAndResistance(1.5f, 6.0f).harvestTool(ToolType.PICKAXE).sound(SoundType.WOOD)), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> SILVER_ORE 				= HELPER.createBlock("silver_ore", () -> new Block(Block.Properties.create(Material.ROCK).hardnessAndResistance(2.5f, 6.0f).harvestTool(ToolType.PICKAXE).harvestLevel(2).sound(SoundType.STONE)), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> ELYTRINE_ORE 				= HELPER.createBlock("elytrine_ore", () -> new Block(Block.Properties.create(Material.ROCK).hardnessAndResistance(2.5f, 6.0f).harvestTool(ToolType.PICKAXE).harvestLevel(3).sound(SoundType.STONE)), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> STONE_BRICK_PILLAR		= HELPER.createBlock("stone_brick_pillar", () -> new RotatedPillarBlock(RotatedPillarBlock.Properties.create(Material.ROCK).hardnessAndResistance(1.5f, 6.0f).harvestTool(ToolType.PICKAXE).harvestLevel(0).sound(SoundType.STONE)), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> ASH_BLOCK 				= HELPER.createBlock("ash_block", () -> new AshFullBlock(Block.Properties.create(Material.SAND, MaterialColor.BLACK).hardnessAndResistance(0.2f).sound(SoundType.SNOW).tickRandomly()), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> ASH						= HELPER.createBlock("ash", () -> new AshBlock(AbstractBlock.Properties.create(Material.SAND, MaterialColor.BLACK).hardnessAndResistance(0.1f).sound(SoundType.SNOW)), DarkerDepths.DARKER_DEPTHS);

	//DECORATION
	public static final RegistryObject<Block> PETRIFIED_FENCE 			= HELPER.createBlock("petrified_fence", () -> new FenceBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(2.5f, 3.0f).harvestTool(ToolType.PICKAXE).harvestLevel(0).sound(SoundType.WOOD)), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> LIMESTONE_WALL 			= HELPER.createBlock("limestone_wall", () -> new WallBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(1.5f, 6.0f).harvestTool(ToolType.PICKAXE).sound(SoundType.STONE)), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> SHALE_WALL 				= HELPER.createBlock("shale_wall", () -> new WallBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(1.5f, 6.0f).harvestTool(ToolType.PICKAXE).sound(SoundType.STONE)), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> CRYSTAL_MELON 			= HELPER.createBlock("crystal_melon", () -> new CrystalMelon(), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> AMETHYST_CRYSTAL 			= HELPER.createBlock("amethyst_crystal", () -> new AmethystCrystal(), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> AMETHYST_CRYSTAL_BLOCK	= HELPER.createBlock("amethyst_crystal_block", () -> new Block(Block.Properties.create(Material.ROCK).hardnessAndResistance(1.0f, 1.0f).harvestTool(ToolType.PICKAXE).harvestLevel(2).sound(SoundType.GLASS).setLightLevel(value -> 10)), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> CELESTINE_CRYSTAL 		= HELPER.createBlock("celestine_crystal", () -> new CelestineCrystal(), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> CELESTINE_CRYSTAL_BLOCK	= HELPER.createBlock("celestine_crystal_block", () -> new Block(Block.Properties.create(Material.ROCK).hardnessAndResistance(1.0f, 1.0f).harvestTool(ToolType.PICKAXE).harvestLevel(2).sound(SoundType.GLASS).setLightLevel(value -> 10)), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> REDSTONE_CRYSTAL 			= HELPER.createBlock("redstone_crystal", () -> new RedstoneCrystal(), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> REDSTONE_CRYSTAL_BLOCK	= HELPER.createBlock("redstone_crystal_block", () -> new Block(Block.Properties.create(Material.ROCK).hardnessAndResistance(1.0f, 1.0f).harvestTool(ToolType.PICKAXE).harvestLevel(2).sound(SoundType.GLASS).setLightLevel(value -> 10)), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> GLOWSHROOM				= HELPER.createBlock("glowshroom", () -> new Glowshroom(), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> GLOWSHROOM_CAP			= HELPER.createBlock("glowshroom_cap", () -> new GlowshroomCap(), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> SPELEOTHEM				= HELPER.createBlock("speleothem", () -> new Speleothem(), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> ROPE						= HELPER.createBlockNoItem("rope", () -> new RopeBlock(RopeBlock.Properties.create(Material.MISCELLANEOUS).zeroHardnessAndResistance().sound(SoundType.CLOTH)));
	
	//REDSTONE
	public static final RegistryObject<Block> PETRIFIED_PRESSURE_PLATE 	= HELPER.createBlock("petrified_pressure_plate", () -> new CustomPressurePlateBlock(CustomPressurePlateBlock.Sensitivity.EVERYTHING ,Block.Properties.create(Material.ROCK).hardnessAndResistance(0.5f).harvestTool(ToolType.PICKAXE).harvestLevel(0).sound(SoundType.WOOD)), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> PETRIFIED_FENCE_GATE 		= HELPER.createBlock("petrified_fence_gate", () -> new FenceGateBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(2.5f, 3.0f).harvestTool(ToolType.PICKAXE).harvestLevel(0).sound(SoundType.WOOD)), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> PETRIFIED_TRAPDOOR 		= HELPER.createBlock("petrified_trapdoor", () -> new CustomTrapDoorBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(2.5f, 3.0f).harvestTool(ToolType.PICKAXE).harvestLevel(0).sound(SoundType.WOOD)), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> PETRIFIED_BUTTON 			= HELPER.createBlock("petrified_button", () -> new CustomWoodButtonBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(0.5f).harvestTool(ToolType.PICKAXE).harvestLevel(0).sound(SoundType.WOOD)), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> PETRIFIED_DOOR 			= HELPER.createBlock("petrified_door", () -> new CustomDoorBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(2.5f, 3.0f).harvestTool(ToolType.PICKAXE).harvestLevel(0).sound(SoundType.WOOD)), DarkerDepths.DARKER_DEPTHS);
}