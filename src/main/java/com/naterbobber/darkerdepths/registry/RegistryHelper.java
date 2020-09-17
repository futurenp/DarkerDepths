package com.naterbobber.darkerdepths.registry;

import java.util.function.Supplier;

import com.mojang.datafixers.util.Pair;
import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.blocks.sign.DDStandingSignBlock;
import com.naterbobber.darkerdepths.blocks.sign.DDWallSignBlock;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.Item;
import net.minecraft.item.SignItem;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

//<>

public class RegistryHelper {

	private final String modId = DarkerDepths.MODID;
	private final DeferredRegister<Block> blockRegister;
	private final DeferredRegister<Item> itemRegister;

	public RegistryHelper(String modId) {
		modId = this.modId;
		this.itemRegister = DeferredRegister.create(ForgeRegistries.ITEMS, modId);
		this.blockRegister = DeferredRegister.create(ForgeRegistries.BLOCKS, modId);
	}
	
	public DeferredRegister<Item> getItemRegister() {
		return this.itemRegister;
	}
	
	public DeferredRegister<Block> getBlockRegister(){
		return this.blockRegister;
	}
	
	public <I extends Item> RegistryObject<I> createItem(String name, Supplier<? extends I> supplier) {
		RegistryObject<I> item = this.itemRegister.register(name, supplier);
		return item;
	}
	
	public Pair<RegistryObject<DDStandingSignBlock>, RegistryObject<DDWallSignBlock>> createSignBlock(String name, MaterialColor color) {
		ResourceLocation texture = new ResourceLocation(DarkerDepths.MODID, "textures/entity/signs/" + name + ".png");
		RegistryObject<DDStandingSignBlock> standing = this.blockRegister.register(name + "_sign", () -> new DDStandingSignBlock(AbstractBlock.Properties.create(Material.WOOD).doesNotBlockMovement().hardnessAndResistance(1.0f).sound(SoundType.WOOD), texture));
		RegistryObject<DDWallSignBlock> wall = this.blockRegister.register(name + "_wall_sign", () -> new DDWallSignBlock(AbstractBlock.Properties.create(Material.WOOD, color).doesNotBlockMovement().hardnessAndResistance(1.0f).sound(SoundType.WOOD).lootFrom(standing.get()), texture));
		this.createItem(name + "_sign", () -> new SignItem(new Item.Properties().maxStackSize(16).group(Registry.DARKER_DEPTHS), standing.get(), wall.get()));
		return Pair.of(standing, wall);
	}
}
