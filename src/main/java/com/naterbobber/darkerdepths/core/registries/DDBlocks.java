package com.naterbobber.darkerdepths.core.registries;

import com.naterbobber.darkerdepths.common.blocks.AbstractGemStoneBlock;
import com.naterbobber.darkerdepths.common.blocks.AloeBlock;
import com.naterbobber.darkerdepths.common.blocks.AridrockBlock;
import com.naterbobber.darkerdepths.common.blocks.AshBlock;
import com.naterbobber.darkerdepths.common.blocks.AshFullBlock;
import com.naterbobber.darkerdepths.common.blocks.ClusterBlock;
import com.naterbobber.darkerdepths.common.blocks.CrystalMelon;
import com.naterbobber.darkerdepths.common.blocks.DDOreBlock;
import com.naterbobber.darkerdepths.common.blocks.DDStandingSignBlock;
import com.naterbobber.darkerdepths.common.blocks.DDWallSignBlock;
import com.naterbobber.darkerdepths.common.blocks.DetritusBlock;
import com.naterbobber.darkerdepths.common.blocks.GeyserBlock;
import com.naterbobber.darkerdepths.common.blocks.GlowSpireBlock;
import com.naterbobber.darkerdepths.common.blocks.GlowSpirePlantBlock;
import com.naterbobber.darkerdepths.common.blocks.Glowshroom;
import com.naterbobber.darkerdepths.common.blocks.GlowspursBlock;
import com.naterbobber.darkerdepths.common.blocks.GrimestoneBlock;
import com.naterbobber.darkerdepths.common.blocks.HangingDoublePlantBlock;
import com.naterbobber.darkerdepths.common.blocks.LushAridrockBlock;
import com.naterbobber.darkerdepths.common.blocks.MossyGrimestoneBlock;
import com.naterbobber.darkerdepths.common.blocks.PorousBlock;
import com.naterbobber.darkerdepths.common.blocks.RepellentBlock;
import com.naterbobber.darkerdepths.common.blocks.RootsBlock;
import com.naterbobber.darkerdepths.common.blocks.RopeBlock;
import com.naterbobber.darkerdepths.common.blocks.SpeleothemBlock;
import com.naterbobber.darkerdepths.common.blocks.SproutsBlock;
import com.naterbobber.darkerdepths.common.blocks.VerticalSlabBlock;
import com.naterbobber.darkerdepths.common.blocks.WoodPostBlock;
import com.naterbobber.darkerdepths.common.blocks.material.DDMaterial;
import com.naterbobber.darkerdepths.core.DarkerDepths;
import com.naterbobber.darkerdepths.core.api.Registries;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.DeadBushBlock;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.FenceBlock;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.block.FlowerPotBlock;
import net.minecraft.block.PressurePlateBlock;
import net.minecraft.block.RedstoneOreBlock;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.TrapDoorBlock;
import net.minecraft.block.WallBlock;
import net.minecraft.block.WoodButtonBlock;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;

//<>

@Mod.EventBusSubscriber(modid = DarkerDepths.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DDBlocks {
	public static final Registries HELPER = DarkerDepths.REGISTRIES;

	//BUILDING BLOCKS

	//petrified
	public static final RegistryObject<Block> PETRIFIED_PLANKS 						= HELPER.registerBlock("petrified_planks", () -> new Block(Block.Properties.create(Material.ROCK).setRequiresTool().hardnessAndResistance(2.5f, 3.0f).harvestTool(ToolType.PICKAXE).harvestLevel(0).sound(SoundType.WOOD)), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> VERTICAL_PETRIFIED_PLANKS				= HELPER.registerCompatBlock("quark", "vertical_petrified_planks", () -> new Block(Block.Properties.from(PETRIFIED_PLANKS.get())), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> PETRIFIED_LOG 						= HELPER.registerBlock("petrified_log", () -> new RotatedPillarBlock(Block.Properties.create(Material.ROCK).setRequiresTool().hardnessAndResistance(2.4f, 3.0f).harvestTool(ToolType.PICKAXE).harvestLevel(0).sound(SoundType.WOOD)), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> STRIPPED_PETRIFIED_LOG 				= HELPER.registerBlock("stripped_petrified_log", () -> new RotatedPillarBlock(Block.Properties.create(Material.ROCK).setRequiresTool().hardnessAndResistance(2.4f, 3.0f).harvestTool(ToolType.PICKAXE).harvestLevel(0).sound(SoundType.WOOD)), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> STRIPPED_PETRIFIED_WOOD 				= HELPER.registerBlock("stripped_petrified_wood", () -> new RotatedPillarBlock(Block.Properties.create(Material.ROCK).setRequiresTool().hardnessAndResistance(2.4f, 3.0f).harvestTool(ToolType.PICKAXE).harvestLevel(0).sound(SoundType.WOOD)), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> PETRIFIED_WOOD 						= HELPER.registerBlock("petrified_wood", () -> new RotatedPillarBlock(Block.Properties.create(Material.ROCK).setRequiresTool().hardnessAndResistance(2.4f, 3.0f).harvestTool(ToolType.PICKAXE).harvestLevel(0).sound(SoundType.WOOD)), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> PETRIFIED_SLAB 						= HELPER.registerBlock("petrified_slab", () -> new SlabBlock(Block.Properties.create(Material.ROCK).setRequiresTool().hardnessAndResistance(2.5f, 3.0f).harvestTool(ToolType.PICKAXE).harvestLevel(0).sound(SoundType.WOOD)), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> PETRIFIED_VERTICAL_SLAB				= HELPER.registerCompatBlock("quark", "petrified_vertical_slab", () -> new VerticalSlabBlock(AbstractBlock.Properties.from(PETRIFIED_SLAB.get())), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> PETRIFIED_STAIRS 						= HELPER.registerBlock("petrified_stairs", () -> new StairsBlock(() -> PETRIFIED_PLANKS.get().getDefaultState(), Block.Properties.create(Material.ROCK).setRequiresTool().hardnessAndResistance(2.5f, 3.0f).harvestTool(ToolType.PICKAXE).harvestLevel(0).sound(SoundType.WOOD)), DarkerDepths.DARKER_DEPTHS);

	//pourus
	public static final RegistryObject<Block> POROUS_PETRIFIED_LOG					= HELPER.registerBlock("porous_petrified_log", () -> new PorousBlock(AbstractBlock.Properties.from(PETRIFIED_LOG.get()).tickRandomly().setLightLevel(value -> 6)), DarkerDepths.DARKER_DEPTHS);

	//moss/lush
	public static final RegistryObject<Block> LUSH_ARIDROCK 						= HELPER.registerBlock("lush_aridrock", () -> new LushAridrockBlock(Block.Properties.create(Material.ROCK, MaterialColor.LIME).setRequiresTool().hardnessAndResistance(1.5f, 6.0f).harvestTool(ToolType.PICKAXE).sound(SoundType.STONE).tickRandomly()), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> MOSSY_GRIMESTONE						= HELPER.registerBlock("mossy_grimestone", () -> new MossyGrimestoneBlock(AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(1.5F, 6.0F).setRequiresTool().harvestTool(ToolType.PICKAXE).tickRandomly().sound(DDSoundEvents.GRIMESTONE)), DarkerDepths.DARKER_DEPTHS);

	//cobbled
	public static final RegistryObject<Block> COBBLED_SANDSTONE 					= HELPER.registerBlock("cobbled_sandstone", () -> new Block(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.SAND).setRequiresTool().harvestTool(ToolType.PICKAXE).hardnessAndResistance(1.0F)), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> COBBLED_SANDSTONE_SLAB				= HELPER.registerBlock("cobbled_sandstone_slab", () -> new SlabBlock(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.SAND).setRequiresTool().harvestTool(ToolType.PICKAXE).hardnessAndResistance(1.0F)), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> COBBLED_SANDSTONE_VERTICAL_SLAB		= HELPER.registerCompatBlock("quark", "cobbled_sandstone_vertical_slab", () -> new VerticalSlabBlock(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.SAND).setRequiresTool().harvestTool(ToolType.PICKAXE).hardnessAndResistance(1.0F)), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> COBBLED_SANDSTONE_STAIRS				= HELPER.registerBlock("cobbled_sandstone_stairs", () -> new StairsBlock(COBBLED_SANDSTONE.get().getDefaultState(), AbstractBlock.Properties.create(Material.ROCK, MaterialColor.SAND).setRequiresTool().harvestTool(ToolType.PICKAXE).hardnessAndResistance(1.0F)), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> COBBLED_RED_SANDSTONE					= HELPER.registerBlock( "cobbled_red_sandstone", () -> new Block(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.ADOBE).setRequiresTool().harvestTool(ToolType.PICKAXE).hardnessAndResistance(1.0F)), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> COBBLED_RED_SANDSTONE_SLAB 			= HELPER.registerBlock("cobbled_red_sandstone_slab", () -> new SlabBlock(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.ADOBE).setRequiresTool().harvestTool(ToolType.PICKAXE).hardnessAndResistance(1.0F)), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> COBBLED_RED_SANDSTONE_VERTICAL_SLAB 	= HELPER.registerCompatBlock("quark", "cobbled_red_sandstone_vertical_slab", () -> new VerticalSlabBlock(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.ADOBE).setRequiresTool().harvestTool(ToolType.PICKAXE).hardnessAndResistance(1.0F)), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> COBBLED_RED_SANDSTONE_STAIRS 			= HELPER.registerBlock("cobbled_red_sandstone_stairs", () -> new StairsBlock(COBBLED_RED_SANDSTONE.get().getDefaultState(), AbstractBlock.Properties.create(Material.ROCK, MaterialColor.ADOBE).setRequiresTool().harvestTool(ToolType.PICKAXE).hardnessAndResistance(1.0F)), DarkerDepths.DARKER_DEPTHS);

	//shale
	public static final RegistryObject<Block> SHALE			 						= HELPER.registerBlock("shale", () -> new Block(Block.Properties.create(Material.ROCK).setRequiresTool().hardnessAndResistance(1.5f, 6.0f).harvestTool(ToolType.PICKAXE).sound(SoundType.STONE)), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> SHALE_SLAB 							= HELPER.registerBlock("shale_slab", () -> new SlabBlock(Block.Properties.create(Material.ROCK).setRequiresTool().hardnessAndResistance(1.5f, 6.0f).harvestTool(ToolType.PICKAXE).sound(SoundType.STONE)), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> SHALE_VERTICAL_SLAB					= HELPER.registerCompatBlock("quark", "shale_vertical_slab", () -> new VerticalSlabBlock(AbstractBlock.Properties.from(SHALE_SLAB.get())), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> SHALE_STAIRS 							= HELPER.registerBlock("shale_stairs", () -> new StairsBlock(() -> SHALE.get().getDefaultState(), Block.Properties.create(Material.ROCK).setRequiresTool().hardnessAndResistance(1.5f, 6.0f).harvestTool(ToolType.PICKAXE).sound(SoundType.STONE)), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> POLISHED_SHALE						= HELPER.registerBlock("polished_shale", () -> new Block(Block.Properties.create(Material.ROCK).setRequiresTool().hardnessAndResistance(1.5f, 6.0f).harvestTool(ToolType.PICKAXE).sound(SoundType.STONE)), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> POLISHED_SHALE_SLAB 					= HELPER.registerBlock("polished_shale_slab", () -> new SlabBlock(Block.Properties.create(Material.ROCK).setRequiresTool().hardnessAndResistance(1.5f, 6.0f).harvestTool(ToolType.PICKAXE).sound(SoundType.STONE)), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> POLISHED_SHALE_VERTICAL_SLAB			= HELPER.registerCompatBlock("quark", "polished_shale_vertical_slab", () -> new VerticalSlabBlock(AbstractBlock.Properties.from(POLISHED_SHALE_SLAB.get())), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> POLISHED_SHALE_STAIRS 				= HELPER.registerBlock("polished_shale_stairs", () -> new StairsBlock(() -> POLISHED_SHALE.get().getDefaultState(), Block.Properties.create(Material.ROCK).setRequiresTool().hardnessAndResistance(1.5f, 6.0f).harvestTool(ToolType.PICKAXE).sound(SoundType.STONE)), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> SHALE_BRICKS							= HELPER.registerBlock("shale_bricks", () -> new Block(Block.Properties.create(Material.ROCK).setRequiresTool().hardnessAndResistance(1.5f, 6.0f).harvestTool(ToolType.PICKAXE).sound(SoundType.STONE)), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> SHALE_BRICKS_SLAB						= HELPER.registerBlock("shale_bricks_slab", () -> new SlabBlock(Block.Properties.create(Material.ROCK).setRequiresTool().hardnessAndResistance(1.5f, 6.0f).harvestTool(ToolType.PICKAXE).sound(SoundType.STONE)), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> SHALE_BRICKS_VERTICAL_SLAB			= HELPER.registerCompatBlock("quark", "shale_bricks_vertical_slab", () -> new VerticalSlabBlock(Block.Properties.create(Material.ROCK).setRequiresTool().hardnessAndResistance(1.5f, 6.0f).harvestTool(ToolType.PICKAXE).sound(SoundType.STONE)), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> SHALE_BRICKS_STAIRS					= HELPER.registerBlock("shale_bricks_stairs", () -> new StairsBlock(SHALE_BRICKS.get().getDefaultState(), Block.Properties.create(Material.ROCK).setRequiresTool().hardnessAndResistance(1.5f, 6.0f).harvestTool(ToolType.PICKAXE).sound(SoundType.STONE)), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> CHISELED_SHALE_BRICKS					= HELPER.registerBlock("chiseled_shale_bricks", () -> new Block(Block.Properties.create(Material.ROCK).setRequiresTool().hardnessAndResistance(1.5f, 6.0f).harvestTool(ToolType.PICKAXE).sound(SoundType.STONE)), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> CRACKED_SHALE_BRICKS					= HELPER.registerBlock("cracked_shale_bricks", () -> new Block(Block.Properties.create(Material.ROCK).setRequiresTool().hardnessAndResistance(1.5f, 6.0f).harvestTool(ToolType.PICKAXE).sound(SoundType.STONE)), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> SHALE_SPELEOTHEM						= HELPER.registerCompatBlock("quark", "shale_speleothem", () -> new SpeleothemBlock(AbstractBlock.Properties.from(DDBlocks.SHALE.get()).hardnessAndResistance(1.5F).notSolid()), DarkerDepths.DARKER_DEPTHS);

	//aridrock
	public static final RegistryObject<Block> ARIDROCK 								= HELPER.registerBlock("aridrock", () -> new AridrockBlock(Block.Properties.create(Material.ROCK, MaterialColor.SAND).setRequiresTool().hardnessAndResistance(1.5f, 6.0f).harvestTool(ToolType.PICKAXE).sound(SoundType.STONE)), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> ARIDROCK_SLAB							= HELPER.registerBlock("aridrock_slab", () -> new SlabBlock(Block.Properties.create(Material.ROCK, MaterialColor.SAND).setRequiresTool().hardnessAndResistance(1.5f, 6.0f).harvestTool(ToolType.PICKAXE).sound(SoundType.STONE)), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> ARIDROCK_VERTICAL_SLAB				= HELPER.registerCompatBlock("quark", "aridrock_vertical_slab", () -> new VerticalSlabBlock(Block.Properties.from(ARIDROCK_SLAB.get())), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> ARIDROCK_STAIRS 						= HELPER.registerBlock("aridrock_stairs", () -> new StairsBlock(() -> ARIDROCK.get().getDefaultState(), Block.Properties.create(Material.ROCK, MaterialColor.SAND).setRequiresTool().hardnessAndResistance(1.5f, 6.0f).harvestTool(ToolType.PICKAXE).sound(SoundType.STONE)), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> POLISHED_ARIDROCK 					= HELPER.registerBlock("polished_aridrock", () -> new Block(Block.Properties.create(Material.ROCK, MaterialColor.SAND).setRequiresTool().hardnessAndResistance(1.5f, 6.0f).harvestTool(ToolType.PICKAXE).sound(SoundType.STONE)), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> POLISHED_ARIDROCK_SLAB				= HELPER.registerBlock("polished_aridrock_slab", () -> new SlabBlock(Block.Properties.create(Material.ROCK, MaterialColor.SAND).setRequiresTool().hardnessAndResistance(1.5f, 6.0f).harvestTool(ToolType.PICKAXE).sound(SoundType.STONE)), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> POLISHED_ARIDROCK_VERTICAL_SLAB		= HELPER.registerCompatBlock("quark", "polished_aridrock_vertical_slab", () -> new VerticalSlabBlock(Block.Properties.from(POLISHED_ARIDROCK_SLAB.get())), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> POLISHED_ARIDROCK_STAIRS  			= HELPER.registerBlock("polished_aridrock_stairs", () -> new StairsBlock(() -> POLISHED_ARIDROCK.get().getDefaultState(), Block.Properties.create(Material.ROCK, MaterialColor.SAND).setRequiresTool().hardnessAndResistance(1.5f, 6.0f).harvestTool(ToolType.PICKAXE).sound(SoundType.STONE)), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> ARIDROCK_BRICKS 						= HELPER.registerBlock("aridrock_bricks", () -> new Block(AbstractBlock.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(1.5F, 6.0F).harvestTool(ToolType.PICKAXE).setRequiresTool()), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> ARIDROCK_BRICKS_SLAB					= HELPER.registerBlock("aridrock_bricks_slab", () -> new SlabBlock(AbstractBlock.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(1.5F, 6.0F).harvestTool(ToolType.PICKAXE).setRequiresTool()), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> ARIDROCK_BRICKS_VERTICAL_SLAB 		= HELPER.registerCompatBlock("quark", "aridrock_bricks_vertical_slab", () -> new VerticalSlabBlock(AbstractBlock.Properties.from(ARIDROCK_BRICKS_SLAB.get())), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> ARIDROCK_BRICKS_STAIRS				= HELPER.registerBlock("aridrock_bricks_stairs", () -> new StairsBlock(ARIDROCK_BRICKS.get().getDefaultState(), AbstractBlock.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(1.5F, 6.0F).harvestTool(ToolType.PICKAXE).setRequiresTool()), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> CHISELED_ARIDROCK_BRICKS				= HELPER.registerBlock("chiseled_aridrock_bricks", () -> new Block(Block.Properties.create(Material.ROCK).setRequiresTool().hardnessAndResistance(1.5f, 6.0f).harvestTool(ToolType.PICKAXE).sound(SoundType.STONE)), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> CRACKED_ARIDROCK_BRICKS				= HELPER.registerBlock("cracked_aridrock_bricks", () -> new Block(AbstractBlock.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(1.5F, 6.0F).harvestTool(ToolType.PICKAXE).setRequiresTool()), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> ARIDROCK_SPELEOTHEM					= HELPER.registerCompatBlock("quark", "aridrock_speleothem", () -> new SpeleothemBlock(AbstractBlock.Properties.from(DDBlocks.ARIDROCK.get()).hardnessAndResistance(1.5F).notSolid()), DarkerDepths.DARKER_DEPTHS);

	//limestone
	public static final RegistryObject<Block> LIMESTONE		 						= HELPER.registerBlock("limestone", () -> new Block(Block.Properties.create(Material.ROCK).setRequiresTool().hardnessAndResistance(1.5f, 6.0f).harvestTool(ToolType.PICKAXE).sound(SoundType.STONE)) {
		@Override
		public boolean canSustainPlant(BlockState state, IBlockReader world, BlockPos pos, Direction facing, IPlantable plantable) {
			return plantable instanceof SproutsBlock || plantable instanceof DeadBushBlock;
		}
	}, DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> LIMESTONE_SLAB 						= HELPER.registerBlock("limestone_slab", () -> new SlabBlock(Block.Properties.create(Material.ROCK).setRequiresTool().hardnessAndResistance(1.5f, 6.0f).harvestTool(ToolType.PICKAXE).sound(SoundType.STONE)), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> LIMESTONE_VERTICAL_SLAB				= HELPER.registerCompatBlock("quark", "limestone_vertical_slab", () -> new VerticalSlabBlock(AbstractBlock.Properties.from(LIMESTONE_SLAB.get())), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> LIMESTONE_STAIRS 						= HELPER.registerBlock("limestone_stairs", () -> new StairsBlock(() -> LIMESTONE.get().getDefaultState(), Block.Properties.create(Material.ROCK).setRequiresTool().hardnessAndResistance(1.5f, 6.0f).harvestTool(ToolType.PICKAXE).sound(SoundType.STONE)), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> POLISHED_LIMESTONE					= HELPER.registerBlock("polished_limestone", () -> new Block(Block.Properties.create(Material.ROCK).setRequiresTool().hardnessAndResistance(1.5f, 6.0f).harvestTool(ToolType.PICKAXE).sound(SoundType.STONE)), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> POLISHED_LIMESTONE_SLAB 				= HELPER.registerBlock("polished_limestone_slab", () -> new SlabBlock(Block.Properties.create(Material.ROCK).setRequiresTool().hardnessAndResistance(1.5f, 6.0f).harvestTool(ToolType.PICKAXE).sound(SoundType.STONE)), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> POLISHED_LIMESTONE_VERTICAL_SLAB		= HELPER.registerCompatBlock("quark", "polished_limestone_vertical_slab", () -> new VerticalSlabBlock(AbstractBlock.Properties.from(POLISHED_LIMESTONE_SLAB.get())), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> POLISHED_LIMESTONE_STAIRS 			= HELPER.registerBlock("polished_limestone_stairs", () -> new StairsBlock(() -> POLISHED_LIMESTONE.get().getDefaultState(), Block.Properties.create(Material.ROCK).setRequiresTool().hardnessAndResistance(1.5f, 6.0f).harvestTool(ToolType.PICKAXE).sound(SoundType.STONE)), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> LIMESTONE_BRICKS 						= HELPER.registerBlock("limestone_bricks", () -> new Block(Block.Properties.create(Material.ROCK).setRequiresTool().hardnessAndResistance(1.5f, 6.0f).harvestTool(ToolType.PICKAXE).sound(SoundType.STONE)), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> LIMESTONE_BRICKS_SLAB					= HELPER.registerBlock("limestone_bricks_slab", () -> new SlabBlock(Block.Properties.create(Material.ROCK).setRequiresTool().hardnessAndResistance(1.5f, 6.0f).harvestTool(ToolType.PICKAXE).sound(SoundType.STONE)), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> LIMESTONE_BRICKS_VERTICAL_SLAB 		= HELPER.registerCompatBlock("quark", "limestone_bricks_vertical_slab", () -> new VerticalSlabBlock(AbstractBlock.Properties.from(ARIDROCK_BRICKS_SLAB.get())), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> LIMESTONE_BRICKS_STAIRS				= HELPER.registerBlock("limestone_bricks_stairs", () -> new StairsBlock(() -> POLISHED_LIMESTONE.get().getDefaultState(), Block.Properties.create(Material.ROCK).setRequiresTool().hardnessAndResistance(1.5f, 6.0f).harvestTool(ToolType.PICKAXE).sound(SoundType.STONE)), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> CHISELED_LIMESTONE_BRICKS				= HELPER.registerBlock("chiseled_limestone_bricks", () -> new Block(Block.Properties.create(Material.ROCK).setRequiresTool().hardnessAndResistance(1.5f, 6.0f).harvestTool(ToolType.PICKAXE).sound(SoundType.STONE)), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> CRACKED_LIMESTONE_BRICKS				= HELPER.registerBlock("cracked_limestone_bricks", () -> new Block(AbstractBlock.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(1.5F, 6.0F).harvestTool(ToolType.PICKAXE).setRequiresTool()), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> LIMESTONE_SPELEOTHEM					= HELPER.registerCompatBlock("quark", "limestone_speleothem", () -> new SpeleothemBlock(AbstractBlock.Properties.from(DDBlocks.LIMESTONE.get()).hardnessAndResistance(1.5F).notSolid()), DarkerDepths.DARKER_DEPTHS);

	//aridrock_ores
	public static final RegistryObject<Block> ARIDROCK_GOLD_ORE 					= HELPER.registerBlock("aridrock_gold_ore", () -> new Block(Block.Properties.create(Material.ROCK, MaterialColor.SAND).setRequiresTool().hardnessAndResistance(3.0F, 3.0F).harvestTool(ToolType.PICKAXE).harvestLevel(2).sound(SoundType.STONE)), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> ARIDROCK_IRON_ORE 					= HELPER.registerBlock("aridrock_iron_ore", () -> new Block(Block.Properties.create(Material.ROCK, MaterialColor.SAND).setRequiresTool().hardnessAndResistance(3.0F, 3.0F).harvestTool(ToolType.PICKAXE).harvestLevel(1).sound(SoundType.STONE)), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> ARIDROCK_COAL_ORE 					= HELPER.registerBlock("aridrock_coal_ore", () -> new DDOreBlock(Block.Properties.create(Material.ROCK, MaterialColor.SAND).setRequiresTool().hardnessAndResistance(3.0F, 3.0F).harvestTool(ToolType.PICKAXE).harvestLevel(0).sound(SoundType.STONE)), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> ARIDROCK_LAPIS_ORE 					= HELPER.registerBlock("aridrock_lapis_ore", () -> new DDOreBlock(Block.Properties.create(Material.ROCK, MaterialColor.SAND).setRequiresTool().hardnessAndResistance(3.0F, 3.0F).harvestTool(ToolType.PICKAXE).harvestLevel(1).sound(SoundType.STONE)), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> ARIDROCK_DIAMOND_ORE 					= HELPER.registerBlock("aridrock_diamond_ore", () -> new DDOreBlock(Block.Properties.create(Material.ROCK, MaterialColor.SAND).setRequiresTool().hardnessAndResistance(3.0F, 3.0F).harvestTool(ToolType.PICKAXE).harvestLevel(2).sound(SoundType.STONE)), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> ARIDROCK_REDSTONE_ORE 				= HELPER.registerBlock("aridrock_redstone_ore", () -> new RedstoneOreBlock(Block.Properties.create(Material.ROCK, MaterialColor.SAND).setRequiresTool().hardnessAndResistance(3.0F, 3.0F).setLightLevel(state -> 9).harvestTool(ToolType.PICKAXE).harvestLevel(2).sound(SoundType.STONE)), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> ARIDROCK_SILVER_ORE 					= HELPER.registerBlock("aridrock_silver_ore", () -> new Block(Block.Properties.create(Material.ROCK, MaterialColor.SAND).setRequiresTool().hardnessAndResistance(3.0F, 3.0F).harvestTool(ToolType.PICKAXE).harvestLevel(2).sound(SoundType.STONE)), DarkerDepths.DARKER_DEPTHS);

	//limestone_ores
	public static final RegistryObject<Block> LIMESTONE_GOLD_ORE 					= HELPER.registerBlock("limestone_gold_ore", () -> new Block(Block.Properties.create(Material.ROCK, MaterialColor.SAND).setRequiresTool().hardnessAndResistance(3.0F, 3.0F).harvestTool(ToolType.PICKAXE).harvestLevel(2).sound(SoundType.STONE)), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> LIMESTONE_IRON_ORE 					= HELPER.registerBlock("limestone_iron_ore", () -> new Block(Block.Properties.create(Material.ROCK, MaterialColor.SAND).setRequiresTool().hardnessAndResistance(3.0F, 3.0F).harvestTool(ToolType.PICKAXE).harvestLevel(1).sound(SoundType.STONE)), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> LIMESTONE_COAL_ORE 					= HELPER.registerBlock("limestone_coal_ore", () -> new DDOreBlock(Block.Properties.create(Material.ROCK, MaterialColor.SAND).setRequiresTool().hardnessAndResistance(3.0F, 3.0F).harvestTool(ToolType.PICKAXE).harvestLevel(0).sound(SoundType.STONE)), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> LIMESTONE_LAPIS_ORE 					= HELPER.registerBlock("limestone_lapis_ore", () -> new DDOreBlock(Block.Properties.create(Material.ROCK, MaterialColor.SAND).setRequiresTool().hardnessAndResistance(3.0F, 3.0F).harvestTool(ToolType.PICKAXE).harvestLevel(1).sound(SoundType.STONE)), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> LIMESTONE_DIAMOND_ORE 				= HELPER.registerBlock("limestone_diamond_ore", () -> new DDOreBlock(Block.Properties.create(Material.ROCK, MaterialColor.SAND).setRequiresTool().hardnessAndResistance(3.0F, 3.0F).harvestTool(ToolType.PICKAXE).harvestLevel(2).sound(SoundType.STONE)), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> LIMESTONE_REDSTONE_ORE 				= HELPER.registerBlock("limestone_redstone_ore", () -> new RedstoneOreBlock(Block.Properties.create(Material.ROCK, MaterialColor.SAND).setRequiresTool().hardnessAndResistance(3.0F, 3.0F).setLightLevel(state -> 9).harvestTool(ToolType.PICKAXE).harvestLevel(2).sound(SoundType.STONE)), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> LIMESTONE_SILVER_ORE 					= HELPER.registerBlock("limestone_silver_ore", () -> new Block(Block.Properties.create(Material.ROCK, MaterialColor.SAND).setRequiresTool().hardnessAndResistance(3.0F, 3.0F).harvestTool(ToolType.PICKAXE).harvestLevel(2).sound(SoundType.STONE)), DarkerDepths.DARKER_DEPTHS);

	//grimestone
	public static final RegistryObject<Block> GRIMESTONE							= HELPER.registerBlock("grimestone", () -> new GrimestoneBlock(AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(1.5F, 6.0F).sound(DDSoundEvents.GRIMESTONE).setRequiresTool().harvestTool(ToolType.PICKAXE)), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> GRIMESTONE_SLAB						= HELPER.registerBlock("grimestone_slab", () -> new SlabBlock(AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(1.5F, 6.0F).sound(SoundType.STONE).harvestTool(ToolType.PICKAXE).setRequiresTool()), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> GRIMESTONE_VERTICAL_SLAB				= HELPER.registerCompatBlock("quark", "grimestone_vertical_slab", () -> new VerticalSlabBlock(AbstractBlock.Properties.from(GRIMESTONE_SLAB.get())), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> GRIMESTONE_STAIRS						= HELPER.registerBlock("grimestone_stairs", () -> new StairsBlock(GRIMESTONE.get().getDefaultState(), AbstractBlock.Properties.create(Material.ROCK).setRequiresTool().hardnessAndResistance(1.5F, 6.0F).harvestTool(ToolType.PICKAXE).sound(SoundType.STONE)), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> POLISHED_GRIMESTONE					= HELPER.registerBlock("polished_grimestone", () -> new Block(AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(1.5F, 6.0F)), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> POLISHED_GRIMESTONE_SLAB				= HELPER.registerBlock("polished_grimestone_slab", () -> new SlabBlock(AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(1.5F, 6.0F)), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> POLISHED_GRIMESTONE_VERTICAL_SLAB		= HELPER.registerCompatBlock("quark", "polished_grimestone_vertical_slab", () -> new VerticalSlabBlock(Block.Properties.from(POLISHED_GRIMESTONE_SLAB.get())), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> POLISHED_GRIMESTONE_STAIRS			= HELPER.registerBlock("polished_grimestone_stairs", () -> new StairsBlock(POLISHED_GRIMESTONE.get().getDefaultState(), AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(1.5F, 6.0F)), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> CHISELED_GRIMESTONE_BRICKS 			= HELPER.registerBlock("chiseled_grimestone_bricks", () -> new Block(AbstractBlock.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(1.5F, 6.0F).harvestTool(ToolType.PICKAXE).setRequiresTool()), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> GRIMESTONE_BRICKS 					= HELPER.registerBlock("grimestone_bricks", () -> new Block(AbstractBlock.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(1.5F, 6.0F).harvestTool(ToolType.PICKAXE).setRequiresTool()), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> GRIMESTONE_BRICKS_SLAB				= HELPER.registerBlock("grimestone_bricks_slab", () -> new SlabBlock(AbstractBlock.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(1.5F, 6.0F).harvestTool(ToolType.PICKAXE).setRequiresTool()), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> GRIMESTONE_BRICKS_VERTICAL_SLAB 		= HELPER.registerCompatBlock("quark", "grimestone_bricks_vertical_slab", () -> new VerticalSlabBlock(AbstractBlock.Properties.from(GRIMESTONE_BRICKS_SLAB.get())), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> GRIMESTONE_BRICKS_STAIRS				= HELPER.registerBlock("grimestone_bricks_stairs", () -> new StairsBlock(GRIMESTONE_BRICKS.get().getDefaultState(), AbstractBlock.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(1.5F, 6.0F).harvestTool(ToolType.PICKAXE).setRequiresTool()), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> CRACKED_GRIMESTONE_BRICKS				= HELPER.registerBlock("cracked_grimestone_bricks", () -> new Block(AbstractBlock.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(1.5F, 6.0F).harvestTool(ToolType.PICKAXE).setRequiresTool()), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> GRIMESTONE_SPELEOTHEM					= HELPER.registerCompatBlock("quark", "grimestone_speleothem", () -> new SpeleothemBlock(AbstractBlock.Properties.from(DDBlocks.GRIMESTONE.get()).hardnessAndResistance(1.5F).notSolid()), DarkerDepths.DARKER_DEPTHS);

	public static final RegistryObject<Block> STONE_BRICK_PILLAR					= HELPER.registerBlock("stone_brick_pillar", () -> new RotatedPillarBlock(RotatedPillarBlock.Properties.create(Material.ROCK).setRequiresTool().hardnessAndResistance(1.5f, 6.0f).harvestTool(ToolType.PICKAXE).harvestLevel(0).sound(SoundType.STONE)), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> ASH_BLOCK 							= HELPER.registerBlock("ash_block", () -> new AshFullBlock(Block.Properties.create(Material.SNOW_BLOCK, MaterialColor.BLACK).harvestTool(ToolType.SHOVEL).setRequiresTool().hardnessAndResistance(0.2f).sound(SoundType.SNOW).tickRandomly()), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> SILVER_ORE 							= HELPER.registerBlock("silver_ore", () -> new Block(Block.Properties.create(Material.ROCK).setRequiresTool().hardnessAndResistance(3.0F, 3.0F).harvestTool(ToolType.PICKAXE).harvestLevel(2).sound(SoundType.STONE)), DarkerDepths.DARKER_DEPTHS);

	//DECORATION

	//plants
	public static final RegistryObject<Block> DETRITUS	 							= HELPER.registerBlock("detritus", () -> new DetritusBlock(AbstractBlock.Properties.from(Blocks.DEAD_BUSH)), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> ALOE 									= HELPER.registerCompatBlock("indev", "aloe", () -> new AloeBlock(AbstractBlock.Properties.from(Blocks.GRASS).tickRandomly()), DarkerDepths.DARKER_DEPTHS);

	//shrooms
	public static final RegistryObject<Block> GLOWSHROOM							= HELPER.registerBlock("glowshroom", () -> new Glowshroom(AbstractBlock.Properties.create(Material.PLANTS).hardnessAndResistance(0.0F, 1.0F).sound(SoundType.SLIME).setLightLevel((state) -> 2 + (3 * state.get(Glowshroom.CLUSTERS_1_3))).doesNotBlockMovement()), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> GLOWSPURS								= HELPER.registerBlock("glowspurs", () -> new GlowspursBlock(AbstractBlock.Properties.create(Material.ORGANIC).zeroHardnessAndResistance().sound(SoundType.SLIME).doesNotBlockMovement()), DarkerDepths.DARKER_DEPTHS);

	//sprouts/roots
	public static final RegistryObject<Block> ROOTS 								= HELPER.registerBlock("roots", () -> new RootsBlock(AbstractBlock.Properties.create(Material.TALL_PLANTS, MaterialColor.WOOD).doesNotBlockMovement().zeroHardnessAndResistance().sound(SoundType.PLANT)), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> LONG_ROOTS 							= HELPER.registerBlock("long_roots", () -> new HangingDoublePlantBlock(AbstractBlock.Properties.create(Material.TALL_PLANTS, MaterialColor.WOOD).doesNotBlockMovement().zeroHardnessAndResistance().sound(SoundType.PLANT)), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> DRY_SPROUTS   						= HELPER.registerBlock("dry_sprouts", () -> new SproutsBlock(AbstractBlock.Properties.from(Blocks.DEAD_BUSH)), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> LUSH_SPROUTS 							= HELPER.registerBlock("lush_sprouts", () -> new SproutsBlock(AbstractBlock.Properties.from(Blocks.GRASS)), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> MOSSY_SPROUTS 						= HELPER.registerBlock("mossy_sprouts", () -> new SproutsBlock(AbstractBlock.Properties.from(Blocks.GRASS)), DarkerDepths.DARKER_DEPTHS);

	//potted
	public static final RegistryObject<Block> POTTED_GLOWSHROOM						= HELPER.registerBlock("potted_glowshroom", () -> new FlowerPotBlock(GLOWSHROOM.get(), AbstractBlock.Properties.create(Material.MISCELLANEOUS).zeroHardnessAndResistance().notSolid()));
	public static final RegistryObject<Block> POTTED_DETRITUS						= HELPER.registerBlock("potted_detritus", () -> new FlowerPotBlock(DETRITUS.get(), AbstractBlock.Properties.create(Material.MISCELLANEOUS).zeroHardnessAndResistance().notSolid()));
	public static final RegistryObject<Block> POTTED_ALOE							= HELPER.registerBlock("potted_aloe", () -> new FlowerPotBlock(ALOE.get(), AbstractBlock.Properties.create(Material.MISCELLANEOUS).zeroHardnessAndResistance().notSolid()));

	//vines
	public static final RegistryObject<Block> GLOWSPIRE								= HELPER.registerBlock("glowspire", () -> new GlowSpireBlock(AbstractBlock.Properties.create(Material.PLANTS).doesNotBlockMovement().setLightLevel(value -> 8).sound(SoundType.PLANT)), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> GLOWSPIRE_PLANT						= HELPER.registerBlock("glowspire_plant", () -> new GlowSpirePlantBlock(AbstractBlock.Properties.from(GLOWSPIRE.get()).sound(SoundType.PLANT)));

	//layered
	public static final RegistryObject<Block> ASH									= HELPER.registerBlock("ash", () -> new AshBlock(AbstractBlock.Properties.create(Material.SNOW, MaterialColor.BLACK).harvestTool(ToolType.SHOVEL).tickRandomly().hardnessAndResistance(0.1f).setRequiresTool().sound(SoundType.SNOW)), DarkerDepths.DARKER_DEPTHS);

	//petrified
	public static final RegistryObject<Block> PETRIFIED_FENCE 						= HELPER.registerBlock("petrified_fence", () -> new FenceBlock(Block.Properties.create(Material.ROCK).setRequiresTool().hardnessAndResistance(2.5f, 3.0f).harvestTool(ToolType.PICKAXE).harvestLevel(0).sound(SoundType.WOOD)), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> PETRIFIED_POST						= HELPER.registerCompatBlock("quark", "petrified_post", () -> new WoodPostBlock(Block.Properties.create(Material.ROCK).setRequiresTool().notSolid().hardnessAndResistance(2.5f, 3.0f).harvestTool(ToolType.PICKAXE).harvestLevel(0).sound(SoundType.WOOD)), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> STRIPPED_PETRIFIED_POST				= HELPER.registerCompatBlock("quark", "stripped_petrified_post", () -> new WoodPostBlock(Block.Properties.create(Material.ROCK).setRequiresTool().notSolid().hardnessAndResistance(2.5f, 3.0f).harvestTool(ToolType.PICKAXE).harvestLevel(0).sound(SoundType.WOOD)), DarkerDepths.DARKER_DEPTHS);

	//shroom_blocks
	public static final RegistryObject<Block> GLOWSHROOM_BLOCK						= HELPER.registerBlock("glowshroom_block", () -> new Block(AbstractBlock.Properties.create(Material.ORGANIC).setLightLevel(value -> 8).hardnessAndResistance(0.2F).harvestTool(ToolType.HOE).sound(SoundType.SLIME)), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> GLOWSHROOM_STEM						= HELPER.registerBlock("glowshroom_stem", () -> new Block(AbstractBlock.Properties.create(Material.ORGANIC).hardnessAndResistance(0.2F).sound(SoundType.HYPHAE)), DarkerDepths.DARKER_DEPTHS);

	//rope
	public static final RegistryObject<Block> ROPE									= HELPER.registerBlock("rope", () -> new RopeBlock(RopeBlock.Properties.create(Material.MISCELLANEOUS).zeroHardnessAndResistance().sound(SoundType.CLOTH)));

	//shale
	public static final RegistryObject<Block> SHALE_WALL 							= HELPER.registerBlock("shale_wall", () -> new WallBlock(Block.Properties.create(Material.ROCK).setRequiresTool().hardnessAndResistance(1.5f, 6.0f).harvestTool(ToolType.PICKAXE).sound(SoundType.STONE)), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> SHALE_BRICKS_WALL						= HELPER.registerBlock("shale_bricks_wall", () -> new WallBlock(Block.Properties.create(Material.ROCK).setRequiresTool().hardnessAndResistance(1.5f, 6.0f).harvestTool(ToolType.PICKAXE).sound(SoundType.STONE)), DarkerDepths.DARKER_DEPTHS);

	//aridrock
	public static final RegistryObject<Block> ARIDROCK_WALL							= HELPER.registerBlock("aridrock_wall", () -> new WallBlock(Block.Properties.create(Material.ROCK, MaterialColor.SAND).setRequiresTool().hardnessAndResistance(1.5f, 6.0f).harvestTool(ToolType.PICKAXE).sound(SoundType.STONE)), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> ARIDROCK_BRICKS_WALL					= HELPER.registerBlock("aridrock_bricks_wall", () -> new WallBlock(Block.Properties.create(Material.ROCK, MaterialColor.SAND).setRequiresTool().hardnessAndResistance(1.5f, 6.0f).harvestTool(ToolType.PICKAXE).sound(SoundType.STONE)), DarkerDepths.DARKER_DEPTHS);

	//limestone
	public static final RegistryObject<Block> LIMESTONE_WALL 						= HELPER.registerBlock("limestone_wall", () -> new WallBlock(Block.Properties.create(Material.ROCK).setRequiresTool().hardnessAndResistance(1.5f, 6.0f).harvestTool(ToolType.PICKAXE).sound(SoundType.STONE)), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> LIMESTONE_BRICKS_WALL					= HELPER.registerBlock("limestone_bricks_wall", () -> new WallBlock(AbstractBlock.Properties.create(Material.ROCK).setRequiresTool().hardnessAndResistance(1.5F, 6.0F).harvestTool(ToolType.PICKAXE).setRequiresTool().sound(SoundType.STONE)), DarkerDepths.DARKER_DEPTHS);

	//grimestone
	public static final RegistryObject<Block> GRIMESTONE_WALL						= HELPER.registerBlock("grimestone_wall", () -> new WallBlock(AbstractBlock.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(1.5F, 6.0F).setRequiresTool().harvestTool(ToolType.PICKAXE)), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> GRIMESTONE_BRICKS_WALL				= HELPER.registerBlock("grimestone_bricks_wall", () -> new WallBlock(AbstractBlock.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(1.5F, 6.0F).setRequiresTool().harvestTool(ToolType.PICKAXE)), DarkerDepths.DARKER_DEPTHS);

	//crystal
	public static final RegistryObject<Block> AMBER 								= HELPER.registerBlock("amber", () -> new AbstractGemStoneBlock(4, 2, AbstractBlock.Properties.create(DDMaterial.AMBER).setRequiresTool().hardnessAndResistance(1.0f).harvestTool(ToolType.PICKAXE).harvestLevel(2).sound(SoundType.GLASS).setLightLevel(value -> 7)), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> AMBER_BLOCK 							= HELPER.registerBlock("amber_block", () -> new Block(AbstractBlock.Properties.create(DDMaterial.AMBER).setRequiresTool().hardnessAndResistance(1.0f).harvestTool(ToolType.PICKAXE).harvestLevel(2).sound(SoundType.GLASS).setLightLevel(value -> 7)), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> CRYSTAL_MELON 						= HELPER.registerBlock("crystal_melon", CrystalMelon::new, DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> CELESTINE_CRYSTAL 					= HELPER.registerBlock("celestine_crystal", () -> new ClusterBlock(14, 2, AbstractBlock.Properties.create(Material.ROCK).setRequiresTool().hardnessAndResistance(1.0f).harvestTool(ToolType.PICKAXE).setRequiresTool().sound(SoundType.GLASS).setLightLevel(value -> 10)), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> CELESTINE_CRYSTAL_BLOCK				= HELPER.registerBlock("celestine_crystal_block", () -> new Block(Block.Properties.create(Material.ROCK).hardnessAndResistance(1.0f, 1.0f).harvestTool(ToolType.PICKAXE).setRequiresTool().sound(SoundType.GLASS).setLightLevel(value -> 10)), DarkerDepths.DARKER_DEPTHS);

	//signs
	public static final RegistryObject<Block> PETRIFIED_SIGN						= HELPER.registerBlock("petrified_sign", () -> new DDStandingSignBlock(AbstractBlock.Properties.create(Material.ROCK).doesNotBlockMovement().hardnessAndResistance(1.0F).setRequiresTool().harvestTool(ToolType.PICKAXE).sound(SoundType.WOOD), DDWoodTypes.PETRIFIED));
	public static final RegistryObject<Block> PETRIFIED_WALL_SIGN					= HELPER.registerBlock("petrified_wall_sign", () -> new DDWallSignBlock(AbstractBlock.Properties.create(Material.ROCK).doesNotBlockMovement().hardnessAndResistance(1.0F).setRequiresTool().harvestTool(ToolType.PICKAXE).sound(SoundType.WOOD), DDWoodTypes.PETRIFIED));

	//cool_stuff
	public static final RegistryObject<Block> GEYSER								= HELPER.registerBlock("geyser", () -> new GeyserBlock(AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(1.5F, 6.0F).harvestTool(ToolType.PICKAXE).setRequiresTool().tickRandomly()), DarkerDepths.DARKER_DEPTHS);

	//REDSTONE

	//petrified
	public static final RegistryObject<Block> PETRIFIED_PRESSURE_PLATE 				= HELPER.registerBlock("petrified_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING ,Block.Properties.create(Material.ROCK).setRequiresTool().hardnessAndResistance(0.5f).harvestTool(ToolType.PICKAXE).harvestLevel(0).sound(SoundType.WOOD)), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> PETRIFIED_TRAPDOOR 					= HELPER.registerBlock("petrified_trapdoor", () -> new TrapDoorBlock(Block.Properties.create(Material.ROCK).setRequiresTool().notSolid().hardnessAndResistance(2.5f, 3.0f).harvestTool(ToolType.PICKAXE).harvestLevel(0).sound(SoundType.WOOD)), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> PETRIFIED_FENCE_GATE 					= HELPER.registerBlock("petrified_fence_gate", () -> new FenceGateBlock(Block.Properties.create(Material.ROCK).setRequiresTool().hardnessAndResistance(2.5f, 3.0f).harvestTool(ToolType.PICKAXE).harvestLevel(0).sound(SoundType.WOOD)), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> PETRIFIED_BUTTON 						= HELPER.registerBlock("petrified_button", () -> new WoodButtonBlock(Block.Properties.create(Material.ROCK).setRequiresTool().hardnessAndResistance(0.5f).harvestTool(ToolType.PICKAXE).harvestLevel(0).sound(SoundType.WOOD)), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> PETRIFIED_DOOR 						= HELPER.registerBlock("petrified_door", () -> new DoorBlock(Block.Properties.create(Material.ROCK).setRequiresTool().notSolid().hardnessAndResistance(2.5f, 3.0f).harvestTool(ToolType.PICKAXE).harvestLevel(0).sound(SoundType.WOOD)), DarkerDepths.DARKER_DEPTHS);

	//MISC
	public static final RegistryObject<Block> RAW_SILVER_BLOCK						= HELPER.registerBlock("raw_silver_block", () -> new Block(AbstractBlock.Properties.create(Material.ORGANIC).harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(5.0F, 6.0F)), DarkerDepths.DARKER_DEPTHS);
	public static final RegistryObject<Block> SILVER_BLOCK							= HELPER.registerBlock("silver_block", () -> new Block(AbstractBlock.Properties.create(Material.ORGANIC).harvestTool(ToolType.PICKAXE).harvestLevel(2).setRequiresTool().hardnessAndResistance(5.0F, 6.0F).sound(SoundType.METAL)), DarkerDepths.DARKER_DEPTHS);
//    public static final RegistryObject<Block> REPELLENT_BLOCK 						= HELPER.registerBlock("repellent_block", () -> new RepellentBlock(AbstractBlock.Properties.create(Material.MISCELLANEOUS).harvestTool(ToolType.PICKAXE).hardnessAndResistance(2.0F).setRequiresTool().sound(SoundType.NETHERITE)), DarkerDepths.DARKER_DEPTHS);
}