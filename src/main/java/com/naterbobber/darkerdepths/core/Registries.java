package com.naterbobber.darkerdepths.core;

import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.particles.ParticleType;
import net.minecraft.potion.Effect;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.carver.WorldCarver;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

//<>

public class Registries {
    public final DeferredRegister<TileEntityType<?>> tileEntities;
    public final DeferredRegister<SoundEvent> soundEvents;
    public final DeferredRegister<Effect> effects;
    public final DeferredRegister<Block> blocks;
    public final DeferredRegister<Enchantment> enchantments;
    public final DeferredRegister<EntityType<?>> entityTypes;
    public final DeferredRegister<Item> items;
    public final DeferredRegister<WorldCarver<?>> carvers;
    public final DeferredRegister<SurfaceBuilder<?>> surfaceBuilders;
    public final DeferredRegister<Feature<?>> features;
    public final DeferredRegister<Placement<?>> placements;
    public final DeferredRegister<Biome> biomes;
    public final DeferredRegister<ParticleType<?>> particleTypes;

    /**
     * creates a new Registry instance
     */
    public Registries() {
        this.blocks                     = DeferredRegister.create(ForgeRegistries.BLOCKS, DarkerDepths.MODID);
        this.biomes                     = DeferredRegister.create(ForgeRegistries.BIOMES, DarkerDepths.MODID);
        this.carvers                    = DeferredRegister.create(ForgeRegistries.WORLD_CARVERS, DarkerDepths.MODID);
        this.effects                    = DeferredRegister.create(ForgeRegistries.POTIONS, DarkerDepths.MODID);
        this.entityTypes                = DeferredRegister.create(ForgeRegistries.ENTITIES, DarkerDepths.MODID);
        this.enchantments               = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, DarkerDepths.MODID);
        this.features                   = DeferredRegister.create(ForgeRegistries.FEATURES, DarkerDepths.MODID);
        this.items                      = DeferredRegister.create(ForgeRegistries.ITEMS, DarkerDepths.MODID);
        this.placements                 = DeferredRegister.create(ForgeRegistries.DECORATORS, DarkerDepths.MODID);
        this.particleTypes              = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, DarkerDepths.MODID);
        this.soundEvents                = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, DarkerDepths.MODID);
        this.surfaceBuilders            = DeferredRegister.create(ForgeRegistries.SURFACE_BUILDERS, DarkerDepths.MODID);
        this.tileEntities               = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, DarkerDepths.MODID);
    }

    public DeferredRegister<Block> getBlocks() {
        return this.blocks;
    }

    public DeferredRegister<Biome> getBiomes() {
        return this.biomes;
    }

    public DeferredRegister<WorldCarver<?>> getCarvers() {
        return this.carvers;
    }

    public DeferredRegister<Effect> getEffects() {
        return this.effects;
    }

    public DeferredRegister<EntityType<?>> getEntityTypes() {
        return this.entityTypes;
    }

    public DeferredRegister<Enchantment> getEnchantments() {
        return this.enchantments;
    }

    public DeferredRegister<Feature<?>> getFeatures() {
        return this.features;
    }

    public DeferredRegister<Item> getItems() {
        return this.items;
    }

    public DeferredRegister<Placement<?>> getPlacements() {
        return this.placements;
    }

    public DeferredRegister<ParticleType<?>> getParticleTypes() {
        return this.particleTypes;
    }

    public DeferredRegister<SoundEvent> getSoundEvents() {
        return this.soundEvents;
    }

    public DeferredRegister<SurfaceBuilder<?>> getSurfaceBuilders() {
        return this.surfaceBuilders;
    }

    public DeferredRegister<TileEntityType<?>> getTileEntities(){return this.tileEntities;}

}