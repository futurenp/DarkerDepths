package com.naterbobber.darkerdepths.core;

import java.util.function.Supplier;

import javax.annotation.Nullable;

import com.google.common.collect.ImmutableList;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

//<>

public class RegistryHelper {
	private final String modId;
	private final DeferredRegister<Block> blockRegister;
	private final DeferredRegister<Item> itemRegister;
	private final DeferredRegister<Feature<?>> featureRegister;
	private final DeferredRegister<Structure<?>> structureRegister;
	private final DeferredRegister<Placement<?>> placementDeferredRegister;

	public RegistryHelper(String modId) {
		this.modId = modId;
		this.itemRegister = DeferredRegister.create(ForgeRegistries.ITEMS, modId);
		this.blockRegister = DeferredRegister.create(ForgeRegistries.BLOCKS, modId);
		this.featureRegister = DeferredRegister.create(ForgeRegistries.FEATURES, modId);
		this.structureRegister = DeferredRegister.create(ForgeRegistries.STRUCTURE_FEATURES, modId);
		this.placementDeferredRegister = DeferredRegister.create(ForgeRegistries.DECORATORS, modId);
	}
	
	public DeferredRegister<Item> getItemRegister() {
		return this.itemRegister;
	}
	
	public DeferredRegister<Block> getBlockRegister() {
		return this.blockRegister;
	}
	
	public DeferredRegister<Feature<?>> getFeatureRegister() {
		return this.featureRegister;
	}

	public DeferredRegister<Structure<?>> getStructureRegister() {
		return this.structureRegister;
	}

	public DeferredRegister<Placement<?>> getPlacementDeferredRegister() {
		return this.placementDeferredRegister;
	}

	public String getModId() {
		return this.modId;
	}
	
	/**
	 * Create an item
	 * @param name 		- The item's name
	 * @param supplier 	- The supplied Item
	 * @return 			- The customized Item
	 */
	public <I extends Item> RegistryObject<I> createItem(String name, Supplier<? extends I> supplier) {
		RegistryObject<I> item = this.itemRegister.register(name, supplier);
		return item;
	}
	
	/**
	 * Create a block with no BlockItem
	 * @param name 		- The block's name
	 * @param supplier 	- The supplied Block
	 * @return 			- The customized Block
	 */
	public <B extends Block> RegistryObject<B> createBlockNoItem(String name, Supplier<? extends B> supplier) {
		RegistryObject<B> block = this.blockRegister.register(name, supplier);
		return block;
	}
	
	/**
	 * Create a block with its BlockItem
	 * @param name 		- The block's name
	 * @param supplier 	- The supplied Block
	 * @param group 	- The ItemGroup for the BlockItem
	 * @return 			- The customized Block
	 */
	public <B extends Block> RegistryObject<B> createBlock(String name, Supplier<? extends B> supplier, @Nullable ItemGroup group) {
		RegistryObject<B> block = this.blockRegister.register(name, supplier);
		this.itemRegister.register(name, () -> new BlockItem(block.get(), new Item.Properties().group(group)));
		return block;
	}
	
	/**
	 * Create a feature
	 * @param name		- The feature's name
	 * @param supplier	- The supplied Feature
	 * @return			- The customized Feature
	 */
	public <F extends Feature<?>> RegistryObject<F> createFeature(String name, Supplier<? extends F> supplier) {
		RegistryObject<F> feature = this.featureRegister.register(name, supplier);
		return feature;
	}

	public <S extends Structure<?>> RegistryObject<S> createStructure(String name, S structure, GenerationStage.Decoration decoration) {
		Structure.field_236365_a_.put(DarkerDepths.MODID + ":" + name, structure);
		Structure.field_236385_u_.put(structure, decoration);

		if (decoration != GenerationStage.Decoration.UNDERGROUND_STRUCTURES) {
			Structure.field_236384_t_ = ImmutableList.<Structure<?>>builder().addAll(Structure.field_236384_t_).add(structure).build();
		}

		return this.structureRegister.register(name, () -> structure);
	}


	public <T extends IPlacementConfig, G extends Placement<T>> RegistryObject<G> registerPlacement(String key, Supplier<G> supplier) {
		return this.getPlacementDeferredRegister().register(key, supplier);
	}
}