package com.naterbobber.darkerdepths.data.assets;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.init.*;
import com.naterbobber.darkerdepths.util.DDResourceKeys;
import net.minecraft.data.PackOutput;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.WallHangingSignBlock;
import net.minecraft.world.level.block.WallSignBlock;
import net.minecraft.world.level.block.WallTorchBlock;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.HashSet;
import java.util.Set;

public class DDLanguageProviderENUS extends LanguageProvider {
    public DDLanguageProviderENUS(PackOutput output) {
        super(output, DarkerDepths.MOD_ID, "en_us");
    }
    private final Set<Block> blockOverrides = new HashSet<>();
    private final Set<Item> itemOverrides = new HashSet<>();
    private final Set<EntityType<?>> entityTypeOverrides = new HashSet<>();
    private final Set<MobEffect> mobEffectOverrides = new HashSet<>();

    private static final String TOOLTIP = ddString("tooltip");
    private static final String ENCHANTMENT = ddString("enchantment");
    private static final String BIOME = ddString("biome");
    private static final String ITEM_GROUP = ddString("itemGroup");
    private static final String ADVANCEMENT = ddString("advancements");

    @Override
    protected void addTranslations() {
        // Items
        add(DDItems.PETRIFIED_CHEST_BOAT, "Petrified Boat with Chest");
        add(DDItems.GLOWSHROOM_CHEST_BOAT, "Glowshroom Boat with Chest");
//        // Blocks
        add(DDBlocks.CRYSTAL_HUSK, "Crystal Husk");
        add(DDBlocks.FORSAKEN_BRONZE_BLOCK, "Block of Forsaken Bronze");
        add(DDBlocks.SCORCHED_REMAINS_BLOCK, "Block of Scorched Remains");

        // Entities
        add(DDEntityTypes.PETRIFIED_CHEST_BOAT, "Petrified Boat with Chest");
        add(DDEntityTypes.GLOWSHROOM_CHEST_BOAT, "Glowshroom Boat with Chest");

        // Enchantments
        add(ENCHANTMENT, "swift_strike", "Swift Strike");
        add(ENCHANTMENT, "quick_dash", "Quick Dash");

        // Effects
        add(DDMobEffects.SOUL_BINDING, "Soul Binding");
        add(DDMobEffects.PARANOIA, "Paranoia");

        // Biomes
        add(BIOME, "molten_cavern", "Molten Cavern");
        add(BIOME, "sandy_catacombs", "Sandy Catacombs");
        add(BIOME, "glowshroom_forest", "Glowshroom Forest");

        // Death Messages
        add("death.attack.soul_binding_damage", "%1$s's soul was consumed by the Death Anchor.");
        add("death.attack.void_soul_damage", "%1$s's soul was reclaimed.");
        add("death.attack.glowshroom_slam_damage", "%1$s was squashed to death.");

        // Tooltips
        add(TOOLTIP, "press_shift", "Press [SHIFT]");
        add(TOOLTIP, "glowshroom_cap.shift_desc_1", "Provides light and haste when worn.");
        add(TOOLTIP, "glowshroom_cap.shift_desc_2", "Found on the back of the Glowshroom Monster.");
        add(TOOLTIP, "stiletto.shift_desc", "Use [RIGHT CLICK] to dash.");
        add(TOOLTIP, "crystal_melon.shift_desc_1", "Used to supercharge any tool or weapon.");
        add(TOOLTIP, "crystal_melon.shift_desc_2", "Right click when holding in the offhand to supercharge.");
        add(TOOLTIP, "death_anchor.shift_desc_1", "Used to set a death location.");
        add(TOOLTIP, "death_anchor.shift_desc_2", "Charge with a Void Soul Requiem.");
        add(TOOLTIP, "legacy.shift_desc", "Legacy item no longer obtainable.");

        // Creative Tab
        add(ITEM_GROUP, "creative_tab", "Darker Depths");

        //Advancements
        addAdvancement("parent", "Darker Depths",
                "Thank you for installing Darker Depths!");

        var moltenCavern = DDResourceKeys.Biomes.MOLTEN_CAVERN.location().getPath();
        var sandyCatacombs = DDResourceKeys.Biomes.SANDY_CATACOMBS.location().getPath();
        var glowshroomCavern = DDResourceKeys.Biomes.GLOWSHROOM_FOREST.location().getPath();

        addAdvancement(sandyCatacombs, "Desert but Underground?",
                "Visit the Sandy Catacombs biome");
        addAdvancement(moltenCavern, "It's Getting Hot in Here!",
                "Visit the Molten Cavern biome");
        addAdvancement(glowshroomCavern, "Luminous Depths",
                "Visit the Glowshroom Forest biome");

        var catacombs = DDResourceKeys.Structures.CATACOMBS.location().getPath();
        addAdvancement(catacombs, "The Lost Souls", "Visit the Catacombs Structure");

        addAdvancement("elytra_boosted_by_geyser", "Inverse Rocketry",
                "Fly through a geyser burst with an Elytra");

        addAdvancement("insert_diamond_into_crystal_husk", "Back From the Dead!",
                "Give a Crystal Husk a Diamond");

        addAdvancement("obtain_glowshroom_cap", "Glow In the Dark",
                "Obtain a Glowshroom Cap from a Glowshroom Monster");

        addAdvancement("set_death_anchor", "Soul Pact",
                "Set a Death Anchor with a Void Soul Requiem");

        addAdvancement("obtain_forsaken_bronze_scrap", "Titan's Slag",
                "Obtain a Forsaken Bronze Scrap from the Catacombs");

        addAdvancement("bottle_void_soul", "Bottle O'Void Souls",
                "Bottle a Void Soul with a Glass Bottle");

        addAdvancement("use_crystal_melon", "Feel the POWAH!",
                "Use a Crystal Melon");

        autoParseFromId();
    }

    private void addAdvancement(String key, String title, String description) {
        if (title != null && !title.isBlank()) {
            add(ADVANCEMENT, key + ".title", title);
        }
        if (description != null && !description.isBlank()) {
            add(ADVANCEMENT, key + ".description", description);
        }
    }

    private void add(String type, String key, String value) {
        key = type + key;
        super.add(key, value);
    }

    private void add(DeferredHolder<?, ?> holder, String value) {
        switch (holder.get()) {
            case Item item -> add(item, value);
            case Block block -> add(block, value);
            case EntityType<?> entityType -> add(entityType, value);
            case MobEffect mobEffect -> add(mobEffect, value);
            default -> {
            }
        }

        skip(holder);
    }

    private void skip(DeferredHolder<?, ?> holder) {
        switch (holder.get()) {
            case Item item -> itemOverrides.add(item);
            case Block block -> {
                blockOverrides.add(block);
                itemOverrides.add(block.asItem());
            }
            case EntityType<?> entityType -> entityTypeOverrides.add(entityType);
            case MobEffect mobEffect -> mobEffectOverrides.add(mobEffect);
            default -> {
            }
        }
    }

    private static boolean filterBlock(Block block) {
        return !(block instanceof WallSignBlock) &&
                !(block instanceof WallHangingSignBlock) &&
                !(block instanceof WallTorchBlock);
    }

    private static String ddString(String str) {
        return String.format("%s.%s.", str, DarkerDepths.MOD_ID);
    }

    private void autoParseFromId() {
        DDBlocks.BLOCKS.getEntries()
                .stream()
                .filter(holder -> filterBlock(holder.get()))
                .filter(holder -> !blockOverrides.contains(holder.get()))
                .forEach(holder -> {
                    var block = holder.get();
                    add(block, toStartCase(holder.getId().getPath()));
                    itemOverrides.add(block.asItem());
                });

        DDItems.ITEMS.getEntries()
                .stream()
                .filter(holder -> !itemOverrides.contains(holder.get()))
                .forEach(holder -> {
                    var item = holder.get();
                    add(item, toStartCase(holder.getId().getPath()));
                });

        DDEntityTypes.ENTITY_TYPES.getEntries().stream()
                .filter(holder -> !entityTypeOverrides.contains(holder.get()))
                .forEach(holder -> {
                    var entityType = holder.get();
                    add(entityType, toStartCase(holder.getId().getPath()));
                });
    }

    private static String toStartCase(String str) {
        String[] words = str.split("_");
        StringBuilder startCaseString = new StringBuilder();

        for (String word : words) {
            if (!word.isEmpty()) {
                String capitalizedWord = Character.toUpperCase(word.charAt(0)) + word.substring(1).toLowerCase();
                startCaseString.append(capitalizedWord).append(" ");
            }
        }
        return startCaseString.substring(0, startCaseString.length() - 1);
    }
}