package com.naterbobber.darkerdepths.core.api;

import com.naterbobber.darkerdepths.core.DarkerDepths;
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
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

//<>

public class CoreRegistries {
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
    public final DeferredRegister<TileEntityType<?>> tileEntities;

    public CoreRegistries() {
        this.soundEvents                = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, DarkerDepths.MODID);
        this.effects                    = DeferredRegister.create(ForgeRegistries.POTIONS, DarkerDepths.MODID);
        this.blocks                     = DeferredRegister.create(ForgeRegistries.BLOCKS, DarkerDepths.MODID);
        this.enchantments               = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, DarkerDepths.MODID);
        this.entityTypes                = DeferredRegister.create(ForgeRegistries.ENTITIES, DarkerDepths.MODID);
        this.items                      = DeferredRegister.create(ForgeRegistries.ITEMS, DarkerDepths.MODID);
        this.carvers                    = DeferredRegister.create(ForgeRegistries.WORLD_CARVERS, DarkerDepths.MODID);
        this.surfaceBuilders            = DeferredRegister.create(ForgeRegistries.SURFACE_BUILDERS, DarkerDepths.MODID);
        this.features                   = DeferredRegister.create(ForgeRegistries.FEATURES, DarkerDepths.MODID);
        this.placements                 = DeferredRegister.create(ForgeRegistries.DECORATORS, DarkerDepths.MODID);
        this.biomes                     = DeferredRegister.create(ForgeRegistries.BIOMES, DarkerDepths.MODID);
        this.particleTypes              = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, DarkerDepths.MODID);
        this.tileEntities               = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, DarkerDepths.MODID);
    }

    public void initializeRegistries(IEventBus bus) {
        this.soundEvents.register(bus);
        this.effects.register(bus);
        this.blocks.register(bus);
        this.enchantments.register(bus);
        this.entityTypes.register(bus);
        this.items.register(bus);
        this.carvers.register(bus);
        this.surfaceBuilders.register(bus);
        this.features.register(bus);
        this.placements.register(bus);
        this.biomes.register(bus);
        this.particleTypes.register(bus);
        this.tileEntities.register(bus);
    }
}