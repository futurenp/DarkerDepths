package com.naterbobber.darkerdepths.core;

import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.potion.Effect;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.carver.ConfiguredCarver;
import net.minecraft.world.gen.carver.ICarverConfig;
import net.minecraft.world.gen.carver.WorldCarver;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.surfacebuilders.ISurfaceBuilderConfig;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraftforge.fml.RegistryObject;

import java.util.function.Supplier;

//<>

public class CoreRegistries extends Registries {
    /**
     * creates a new Registry instance
     * @param modId your mod ID
     */
    public CoreRegistries(String modId) {
        super(modId);
    }

    /**
     * register a customized sound event
     *
     * @param key   sound ID
     * @return      the customized sound event
     */
    public RegistryObject<SoundEvent> registerSoundEvent(String key) {
        return this.getSoundEvents().register(key, () -> new SoundEvent(new ResourceLocation(this.id, key)));
    }

    /**
     * register a customized effect
     *
     * @param key       effect ID
     * @param effect    customized effect class
     * @return          the customized effect
     */
    public <E extends Effect> RegistryObject<E> registerEffect(String key, Supplier<? extends E> effect) {
        return this.getEffects().register(key, effect);
    }

    /**
     * register a customized block
     *
     * @param key   block ID
     * @param block the customized block class
     * @return      the customized block
     */
    public <B extends Block> RegistryObject<B> registerBlock(String key, Supplier<? extends B> block) {
        return this.getBlocks().register(key, block);
    }

    /**
     * register a customized block with its block item
     *
     * @param key       block ID
     * @param block     the customized block class
     * @param group the block's group id
     * @return          the customized block with its block item
     */
    public <B extends Block> RegistryObject<B> registerBlock(String key, Supplier<? extends B> block, ItemGroup group) {
        RegistryObject<B> blocks = this.blocks.register(key, block);
        this.items.register(key, () -> new BlockItem(blocks.get(), new Item.Properties().group(group)));
        return blocks;
    }

    /**
     * register a customized enchantment
     *
     * @param key           enchantment ID
     * @param enchantment   the customized enchantment class
     * @return              the customized enchantment
     */
    public <E extends Enchantment> RegistryObject<E> registerEnchantment(String key, Supplier<? extends E> enchantment) {
        return this.getEnchantments().register(key, enchantment);
    }

    /**
     * register a customized entity
     *
     * @param key       entity ID
     * @param builder   the entity property builder
     * @return          the customized entity
     */
    public <E extends Entity> RegistryObject<EntityType<E>> registerEntity(String key, EntityType.Builder<E> builder) {
        return this.getEntityTypes().register(key, () -> builder.build(new ResourceLocation(this.id, key).toString()));
    }

    /**
     * register a customized item
     *
     * @param key   item ID
     * @param item  the customized item class
     * @return      the customized item
     */
    public <I extends Item> RegistryObject<I> registerItem(String key, Supplier<? extends I> item) {
        return this.getItems().register(key, item);
    }

    /**
     * register a customized world carver
     *
     * @param key       carver ID
     * @param carver    the customized carver class
     * @return          the customized carver
     */
    public <C extends ICarverConfig, W extends WorldCarver<C>> RegistryObject<W> registerCarver(String key, Supplier<? extends W> carver) {
        return this.getCarvers().register(key, carver);
    }

    /**
     * register a customized configured feature
     *
     * @param key               configured feature ID
     * @param configuredFeature the configured feature properties
     * @return                  the configured feature
     */
    public <C extends ICarverConfig, CC extends ConfiguredCarver<C>> CC registerConfiguredCarver(String key, CC configuredFeature) {
        ResourceLocation ID = new ResourceLocation(this.id, key);
        if (WorldGenRegistries.CONFIGURED_CARVER.keySet().contains(ID)) {
            throw new IllegalStateException("The Configured Carver " + key + "already exists in the registry");
        }
        net.minecraft.util.registry.Registry.register(WorldGenRegistries.CONFIGURED_CARVER, ID, configuredFeature);
        return configuredFeature;
    }

    /**
     * register a customized surface builder
     *
     * @param key               surface builder ID
     * @param surfaceBuilder    the customized surface builder class
     * @return                  the customized surface builder
     */
    public <C extends ISurfaceBuilderConfig, S extends SurfaceBuilder<C>> RegistryObject<S> registerSurfaceBuilder(String key, Supplier<? extends S> surfaceBuilder) {
        return this.getSurfaceBuilders().register(key, surfaceBuilder);
    }

    /**
     * register a customized feature
     *
     * @param key       feature ID
     * @param feature   the customized feature class
     * @return          the customized feature
     */
    public <C extends IFeatureConfig, F extends Feature<C>> RegistryObject<F> registerFeature(String key, Supplier<? extends F> feature) {
        return this.getFeatures().register(key, feature);
    }

    /**
     * register a customized configured feature
     *
     * @param key               configured feature ID
     * @param configuredFeature the configured feature properties
     * @return                  the configured feature
     */
    public <C extends IFeatureConfig, F extends Feature<C>, CF extends ConfiguredFeature<C, F>> CF registerConfiguredFeature(String key, CF configuredFeature) {
        ResourceLocation ID = new ResourceLocation(this.id, key);
        if (WorldGenRegistries.CONFIGURED_FEATURE.keySet().contains(ID)) {
            throw new IllegalStateException("The Configured Feature " + key + "already exists in the registry");
        }
        net.minecraft.util.registry.Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, ID, configuredFeature);
        return configuredFeature;
    }

    /**
     * register a customized feature placement
     *
     * @param key       placement ID
     * @param placement the customized placement class
     * @return          the customized placement
     */
    public <C extends IPlacementConfig, P extends Placement<C>> RegistryObject<P> registerPlacement(String key, Supplier<? extends P> placement) {
        return this.getPlacements().register(key, placement);
    }

    /**
     * register a customized biome
     *
     * @param key   biome ID
     * @param biome the customized biome class
     * @return      the customized biome
     */
    public <B extends Biome> RegistryObject<B> registerBiome(String key, Supplier<? extends B> biome) {
        return this.getBiomes().register(key, biome);
    }

    /**
     * register a customized simple particle
     *
     * @param key           particle ID
     * @param alwaysShow    toggle if the particle should always be shown
     * @return              the customized particle
     */
    public RegistryObject<BasicParticleType> registerParticle(String key, boolean alwaysShow) {
        return this.getParticleTypes().register(key, () -> new BasicParticleType(alwaysShow));
    }

    /**
     * register a customized block tag
     *
     * @param key   the block tag name
     * @return      the customized block tag
     */
    public ITag.INamedTag<Block> registerBlockTag(String key) {
        return BlockTags.makeWrapperTag(new ResourceLocation(this.id, key).toString());
    }

    /**
     * register a customized item tag
     *
     * @param key   the item tag name
     * @return      the customized item tag
     */
    public ITag.INamedTag<Item> registerItemTag(String key) {
        return ItemTags.makeWrapperTag(new ResourceLocation(this.id, key).toString());
    }

    /**
     * register a customized block tag
     *
     * @param key   the block tag name
     * @return      the customized block tag
     */
    public ITag.INamedTag<Fluid> registerFluidTag(String key) {
        return FluidTags.makeWrapperTag(new ResourceLocation(this.id, key).toString());
    }

    /**
     * register a customized entity tag
     *
     * @param key   the entity tag name
     * @return      the customized entity tag
     */
    public ITag.INamedTag<EntityType<?>> registerEntityTag(String key) {
        return EntityTypeTags.getTagById(new ResourceLocation(this.id, key).toString());
    }
}