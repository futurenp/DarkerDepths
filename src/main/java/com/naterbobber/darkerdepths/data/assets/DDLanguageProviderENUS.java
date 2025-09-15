package com.naterbobber.darkerdepths.data.assets;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.init.*;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;

public class DDLanguageProviderENUS extends LanguageProvider {
    public DDLanguageProviderENUS(PackOutput output) {
        super(output, DarkerDepths.MODID, "en_us");
    }

    @Override
    protected void addTranslations() {
        // Items
        add(DDItems.GLOWSHROOM_MONSTER_SPAWN_EGG.get(), "Glowshroom Monster Spawn Egg");
        add(DDItems.BODY_SNATCHER_SPAWN_EGG.get(), "Body Snatcher Spawn Egg");
        add(DDItems.VOID_SOUL_KNIGHT_SPAWN_EGG.get(), "Void Soul Knight Spawn Egg");
        add(DDItems.VOID_SOUL_SPAWN_EGG.get(), "Void Soul Spawn Egg");
        add(DDItems.GLOWSHROOM_CAP.get(), "Glowshroom Cap");
        add(DDItems.GLOW_GRIME.get(), "Glow Grime");
        add(DDItems.STILETTO.get(), "Stiletto");
//        add(DDItems.QUICKROPE.get(), "Quick Rope");
        add(DDItems.VOID_SOUL_REQUIEM.get(), "Void Soul Requiem");
        add(DDItems.PETRIFIED_BOAT.get(), "Petrified Boat");
        add(DDItems.PETRIFIED_CHEST_BOAT.get(), "Petrified Boat with Chest");
        add(DDItems.AMBER.get(), "Amber");
        add(DDItems.FORSAKEN_BRONZE_SCRAP.get(), "Forsaken Bronze Scrap");
        add(DDItems.FORSAKEN_BRONZE_INGOT.get(), "Forsaken Bronze Ingot");

        // Blocks
        add(DDBlocks.VOID_SOUL_JAR.get(), "Void Soul Jar");
        add(DDBlocks.DUSKROCK.get(), "Duskrock");
        add(DDBlocks.POLISHED_DUSKROCK.get(), "Polished Duskrock");
        add(DDBlocks.DUSKROCK_WALL.get(), "Duskrock Wall");
        add(DDBlocks.POLISHED_DUSKROCK_STAIRS.get(), "Polished Duskrock Stairs");
        add(DDBlocks.DUSKROCK_STAIRS.get(), "Duskrock Stairs");
        add(DDBlocks.POLISHED_DUSKROCK_SLAB.get(), "Polished Duskrock Slab");
        add(DDBlocks.DUSKROCK_SLAB.get(), "Duskrock Slab");
        add(DDBlocks.DUSKROCK_BRICKS.get(), "Duskrock Bricks");
        add(DDBlocks.CHISELED_DUSKROCK_BRICKS.get(), "Chiseled Duskrock Bricks");
        add(DDBlocks.DUSKROCK_BRICKS_STAIRS.get(), "Duskrock Brick Stairs");
        add(DDBlocks.DUSKROCK_BRICKS_SLAB.get(), "Duskrock Brick Slab");
        add(DDBlocks.DUSKROCK_BRICKS_VERTICAL_SLAB.get(), "Duskrock Brick Vertical Slab");
        add(DDBlocks.CRACKED_DUSKROCK_BRICKS.get(), "Cracked Duskrock Bricks");
        add(DDBlocks.PETRIFIED_PLANKS.get(), "Petrified Planks");
        add(DDBlocks.PETRIFIED_LOG.get(), "Petrified Log");
        add(DDBlocks.STRIPPED_PETRIFIED_LOG.get(), "Stripped Petrified Log");
        add(DDBlocks.STRIPPED_PETRIFIED_WOOD.get(), "Stripped Petrified Wood");
        add(DDBlocks.PETRIFIED_WOOD.get(), "Petrified Wood");
        add(DDBlocks.PETRIFIED_SLAB.get(), "Petrified Slab");
        add(DDBlocks.PETRIFIED_PRESSURE_PLATE.get(), "Petrified Pressure Plate");
        add(DDBlocks.PETRIFIED_FENCE.get(), "Petrified Fence");
        add(DDBlocks.PETRIFIED_TRAPDOOR.get(), "Petrified Trapdoor");
        add(DDBlocks.PETRIFIED_FENCE_GATE.get(), "Petrified Fence Gate");
        add(DDBlocks.PETRIFIED_BUTTON.get(), "Petrified Button");
        add(DDBlocks.PETRIFIED_STAIRS.get(), "Petrified Stairs");
        add(DDBlocks.PETRIFIED_DOOR.get(), "Petrified Door");
        add(DDBlocks.GLOWSHROOM.get(), "Glowshroom");
        add(DDBlocks.DARKSLATE.get(), "Darkslate");
        add(DDBlocks.POLISHED_DARKSLATE.get(), "Polished Darkslate");
        add(DDBlocks.DARKSLATE_WALL.get(), "Darkslate Wall");
        add(DDBlocks.POLISHED_DARKSLATE_STAIRS.get(), "Polished Darkslate Stairs");
        add(DDBlocks.DARKSLATE_STAIRS.get(), "Darkslate Stairs");
        add(DDBlocks.POLISHED_DARKSLATE_SLAB.get(), "Polished Darkslate Slab");
        add(DDBlocks.DARKSLATE_SLAB.get(), "Darkslate Slab");
        add(DDBlocks.DARKSLATE_BRICKS.get(), "Darkslate Bricks");
        add(DDBlocks.CHISELED_DARKSLATE_BRICKS.get(), "Chiseled Darkslate Bricks");
        add(DDBlocks.CRACKED_DARKSLATE_BRICKS.get(), "Cracked Darkslate Bricks");
//        add(DDBlocks.SPELEOTHEM.get(), "Speleothem");
        add(DDBlocks.ASH_BLOCK.get(), "Ash Block");
        add(DDBlocks.ASH.get(), "Ash");
        add(DDBlocks.ROPE.get(), "Rope");
//        add(DDBlocks.QUICKROPE.get(), "Quickrope");
        add(DDBlocks.STONE_MELON.get(), "Stone Melon");
        add(DDBlocks.CRYSTAL_MELON.get(), "Crystal Melon");
        add(DDBlocks.AMBER_CLUSTER.get(), "Amber Cluster");
        add(DDBlocks.ARIDROCK.get(), "Aridrock");
//        add(DDBlocks.GRIME_DEEPSLATE.get(), "Grime Deepslate");
        add(DDBlocks.ARID_DEEPSLATE.get(), "Arid Deepslate");
        add(DDBlocks.ARIDROCK_STAIRS.get(), "Aridrock Stairs");
        add(DDBlocks.ARIDROCK_SLAB.get(), "Aridrock Slab");
        add(DDBlocks.ARIDROCK_WALL.get(), "Aridrock Wall");
        add(DDBlocks.POLISHED_ARIDROCK.get(), "Polished Aridrock");
        add(DDBlocks.POLISHED_ARIDROCK_STAIRS.get(), "Polished Aridrock Stairs");
        add(DDBlocks.POLISHED_ARIDROCK_SLAB.get(), "Polished Aridrock Slab");
        add(DDBlocks.ARIDROCK_PILLAR.get(), "Aridrock Pillar");
        add(DDBlocks.PETRIFIED_ROOTS.get(), "Petrified Roots");
        add(DDBlocks.DRY_SPROUTS.get(), "Dry Sprouts");
        add(DDBlocks.MOSSY_SPROUTS.get(), "Mossy Sprouts");
        add(DDBlocks.POROUS_PETRIFIED_LOG.get(), "Porous Petrified Log");
        add(DDBlocks.GLOWSHROOM_STEM.get(), "Glowshroom Stem");
        add(DDBlocks.GLOWSHROOM_BLOCK.get(), "Glowshroom Block");
        add(DDBlocks.GRIMESTONE.get(), "Grimestone");
        add(DDBlocks.GRIMESTONE_SLAB.get(), "Grimestone Slab");
        add(DDBlocks.GRIMESTONE_STAIRS.get(), "Grimestone Stairs");
        add(DDBlocks.GRIMESTONE_WALL.get(), "Grimestone Wall");
        add(DDBlocks.POLISHED_GRIMESTONE.get(), "Polished Grimestone");
        add(DDBlocks.POLISHED_GRIMESTONE_STAIRS.get(), "Polished Grimestone Stairs");
        add(DDBlocks.POLISHED_GRIMESTONE_SLAB.get(), "Polished Grimestone Slab");
        add(DDBlocks.MOSSY_GRIMESTONE.get(), "Mossy Grimestone");
        add(DDBlocks.GLOWSPURS.get(), "Glowspurs");
        add(DDBlocks.GLIMMERING_VINES.get(), "Glimmering Vines");
        add(DDBlocks.GEYSER.get(), "Geyser");
        add(DDBlocks.PETRIFIED_VERTICAL_SLAB.get(), "Petrified Vertical Slab");
        add(DDBlocks.PETRIFIED_SIGN.get(), "Petrified Sign");
        add(DDBlocks.PETRIFIED_HANGING_SIGN.get(), "Petrified Hanging Sign");
        add(DDBlocks.DARKSLATE_VERTICAL_SLAB.get(), "Darkslate Vertical Slab");
        add(DDBlocks.POLISHED_DARKSLATE_VERTICAL_SLAB.get(), "Polished Darkslate Vertical Slab");
        add(DDBlocks.DUSKROCK_VERTICAL_SLAB.get(), "Duskrock Vertical Slab");
        add(DDBlocks.POLISHED_DUSKROCK_VERTICAL_SLAB.get(), "Polished Duskrock Vertical Slab");
        add(DDBlocks.ARIDROCK_VERTICAL_SLAB.get(), "Aridrock Vertical Slab");
        add(DDBlocks.POLISHED_ARIDROCK_VERTICAL_SLAB.get(), "Polished Aridrock Vertical Slab");
        add(DDBlocks.GRIMESTONE_VERTICAL_SLAB.get(), "Grimestone Vertical Slab");
        add(DDBlocks.POLISHED_GRIMESTONE_VERTICAL_SLAB.get(), "Polished Grimestone Vertical Slab");
        add(DDBlocks.CHISELED_GRIMESTONE_BRICKS.get(), "Chiseled Grimestone Bricks");
        add(DDBlocks.VERTICAL_PETRIFIED_PLANKS.get(), "Vertical Petrified Planks");
        add(DDBlocks.PETRIFIED_POST.get(), "Petrified Post");
        add(DDBlocks.STRIPPED_PETRIFIED_POST.get(), "Stripped Petrified Post");
        add(DDBlocks.ARIDROCK_BRICKS.get(), "Aridrock Bricks");
        add(DDBlocks.CHISELED_ARIDROCK_BRICKS.get(), "Chiseled Aridrock Bricks");
        add(DDBlocks.CRACKED_ARIDROCK_BRICKS.get(), "Cracked Aridrock Bricks");
        add(DDBlocks.SKULL_WALL.get(), "Skull Wall");
        add(DDBlocks.GRIMESTONE_BRICKS.get(), "Grimestone Bricks");
        add(DDBlocks.CRACKED_GRIMESTONE_BRICKS.get(), "Cracked Grimestone Bricks");
        add(DDBlocks.ARIDROCK_BRICKS_SLAB.get(), "Aridrock Brick Slab");
        add(DDBlocks.ARIDROCK_BRICKS_STAIRS.get(), "Aridrock Brick Stairs");
        add(DDBlocks.GRIMESTONE_BRICKS_SLAB.get(), "Grimestone Brick Slab");
        add(DDBlocks.GRIMESTONE_BRICKS_STAIRS.get(), "Grimestone Brick Stairs");
        add(DDBlocks.GRIMESTONE_BRICKS_VERTICAL_SLAB.get(), "Grimestone Brick Vertical Slab");
        add(DDBlocks.ARIDROCK_BRICKS_VERTICAL_SLAB.get(), "Aridrock Brick Vertical Slab");
        add(DDBlocks.DARKSLATE_BRICKS_SLAB.get(), "Darkslate Brick Slab");
        add(DDBlocks.DARKSLATE_BRICKS_STAIRS.get(), "Darkslate Brick Stairs");
        add(DDBlocks.DARKSLATE_BRICKS_VERTICAL_SLAB.get(), "Darkslate Brick Vertical Slab");
        add(DDBlocks.DARKSLATE_BRICKS_WALL.get(), "Darkslate Brick Wall");
        add(DDBlocks.ARIDROCK_BRICKS_WALL.get(), "Aridrock Brick Wall");
        add(DDBlocks.DUSKROCK_BRICKS_WALL.get(), "Duskrock Brick Wall");
        add(DDBlocks.GRIMESTONE_BRICKS_WALL.get(), "Grimestone Brick Wall");
//        add(DDBlocks.DARKSLATE_SPELEOTHEM.get(), "Darkslate Speleothem");
//        add(DDBlocks.ARIDROCK_SPELEOTHEM.get(), "Aridrock Speleothem");
//        add(DDBlocks.DUSKROCK_SPELEOTHEM.get(), "Duskrock Speleothem");
//        add(DDBlocks.GRIMESTONE_SPELEOTHEM.get(), "Grimestone Speleothem");
        add(DDBlocks.AMBER_BLOCK.get(), "Amber Block");
        add(DDBlocks.GLOWSHROOM_LANTERN.get(), "Glowshroom Lantern");
        add(DDBlocks.GLOWSHROOM_LAMP.get(), "Glowshroom Lamp");
        add(DDBlocks.DEATH_ANCHOR.get(), "Death Anchor");
        add(DDBlocks.MAGMA_PAD.get(), "Magma Pad");
        add(DDBlocks.LIVING_CRYSTAL.get(), "Living Crystal");
        add(DDBlocks.DEAD_LIVING_CRYSTAL.get(), "Dead Living Crystal");
        add(DDBlocks.TOMB.get(), "Tomb");
        add(DDBlocks.FORSAKEN_BRONZE_BLOCK.get(), "Block of Forsaken Bronze");
        add(DDBlocks.PARANOIA_ALTAR.get(), "Paranoia Altar");
        add(DDBlocks.VOID_SOUL_TORCH.get(), "Void Soul Torch");
        add(DDBlocks.GLOWSHROOM_HEART.get(), "Glowshroom Heart");
        // Entities
        add(DDEntityTypes.GLOWSHROOM_MONSTER.get(), "Glowshroom Monster");
        add(DDEntityTypes.BODY_SNATCHER.get(), "Body Snatcher");
        add(DDEntityTypes.VOID_SOUL_KNIGHT.get(), "Void Soul Knight");
        add(DDEntityTypes.VOID_SOUL.get(), "Void Soul");
        add(DDEntityTypes.PETRIFIED_BOAT.get(), "Petrified Boat");
        add(DDEntityTypes.PETRIFIED_CHEST_BOAT.get(), "Boat with Chest");

        // Enchantments
        add(DDEnchantments.SWIFT_STRIKE.get(), "Swift Strike");
        add(DDEnchantments.QUICK_DASH.get(), "Quick Dash");

        // Effects
        add(DDMobEffects.SOUL_BINDING.get(), "Soul Binding");
        add(DDMobEffects.PARANOIA.get(), "Paranoia");

        // Biomes
        add("biome.darkerdepths.molten_cavern", "Molten Cavern");
        add("biome.darkerdepths.sandy_catacombs", "Sandy Catacombs");
        add("biome.darkerdepths.glowshroom_forest", "Glowshroom Forest");

        // Death Messages
        add("death.attack.soul_binding_damage", "%1$s's soul was consumed by the Death Anchor.");

        // Tooltips
        add("tooltip.darkerdepths.press_shift", "Press [SHIFT]");
        add("tooltip.darkerdepths.glowshroom_cap.shift_desc_1", "Provides light and haste when worn.");
        add("tooltip.darkerdepths.glowshroom_cap.shift_desc_2", "Found on the back of the Glowshroom Monster.");
        add("tooltip.darkerdepths.stiletto.shift_desc", "Use [RIGHT CLICK] to dash.");
        add("tooltip.darkerdepths.crystal_melon.shift_desc_1", "Used to supercharge any tool or weapon.");
        add("tooltip.darkerdepths.crystal_melon.shift_desc_2", "Right click when holding in the offhand to supercharge.");
        add("tooltip.darkerdepths.death_anchor.shift_desc_1", "Used to set a death location.");
        add("tooltip.darkerdepths.death_anchor.shift_desc_2", "Charge with a Void Soul Requiem.");

        // Creative Tab
        add("itemGroup.darkerdepths.creative_tab", "Darker Depths");
    }
}