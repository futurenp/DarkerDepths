package com.naterbobber.darkerdepths.data;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.init.DDBlocks;
import com.naterbobber.darkerdepths.init.DDCriteria;
import com.naterbobber.darkerdepths.init.DDEntityTypes;
import com.naterbobber.darkerdepths.init.DDItems;
import com.naterbobber.darkerdepths.util.DDResourceKeys;
import net.minecraft.advancements.*;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.advancements.AdvancementSubProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeAdvancementProvider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class DDAdvancementProvider extends ForgeAdvancementProvider {
    public DDAdvancementProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, existingFileHelper, List.of(new MainAdvancementGenerator()));
    }

    private static final class MainAdvancementGenerator implements AdvancementGenerator {
        @Override
        public void generate(HolderLookup.Provider registries, Consumer<Advancement> saver, ExistingFileHelper existingFileHelper) {
            // 1. Generate the root
            Advancement root = parentNode(saver, existingFileHelper);

            // 2. Generate the biome nodes and capture them in a map
            Map<ResourceLocation, Advancement> biomeAdvancements = biomeNodes(root, registries, saver, existingFileHelper);

            // 3. Extract the specific biome parents needed for the rest of the tree
            Advancement sandyCatacombs = biomeAdvancements.get(DDResourceKeys.Biomes.SANDY_CATACOMBS.location());
            Advancement moltenCavern = biomeAdvancements.get(DDResourceKeys.Biomes.MOLTEN_CAVERN.location());
            Advancement glowshroomForest = biomeAdvancements.get(DDResourceKeys.Biomes.GLOWSHROOM_FOREST.location());

            // 4. Generate the branches sequentially by passing the Advancement objects
            Advancement catacombs = catacombsNode(sandyCatacombs, registries, saver, existingFileHelper);
            Advancement forsakenScrap = obtainForsakenBronzeScrap(catacombs, saver, existingFileHelper);
            Advancement deathAnchor = setDeathAnchorNode(forsakenScrap, saver, existingFileHelper);

            Advancement bottleVoidSoul = bottleVoidSoulNode(sandyCatacombs, saver, existingFileHelper);

            Advancement geyser = geyserNode(moltenCavern, saver, existingFileHelper);
            Advancement deadLivingCrystal = deadLivingCrystalNode(moltenCavern, saver, existingFileHelper);
            Advancement crystalMelon = crystalMelonNode(deadLivingCrystal, saver, existingFileHelper);

            Advancement glowshroomCap = obtainGlowshroomCapNode(glowshroomForest, saver, existingFileHelper);
        }

        private static Advancement crystalMelonNode(Advancement parent, Consumer<Advancement> saver, ExistingFileHelper existingFileHelper) {
            var builder = Advancement.Builder.advancement();
            var criterionName = "use_crystal_melon";

            builder.parent(parent);
            builder.display(
                    DDBlocks.CRYSTAL_MELON.get().asItem(),
                    Component.translatable("advancements.darkerdepths." + criterionName + ".title"),
                    Component.translatable("advancements.darkerdepths." + criterionName + ".description"),
                    null,
                    FrameType.TASK,
                    true,
                    true,
                    false
            );

            var criterion = DDCriteria.USED_CRYSTAL_MELON.instance();

            return addRequirementsAndSave("activities", criterionName, builder, criterion, saver, existingFileHelper);
        }

        private static Advancement bottleVoidSoulNode(Advancement parent, Consumer<Advancement> saver, ExistingFileHelper existingFileHelper) {
            var builder = Advancement.Builder.advancement();
            var criterionName = "bottle_void_soul";

            builder.parent(parent);
            builder.display(
                    DDItems.VOID_SOUL_JAR.get(),
                    Component.translatable("advancements.darkerdepths." + criterionName + ".title"),
                    Component.translatable("advancements.darkerdepths." + criterionName + ".description"),
                    null,
                    FrameType.TASK,
                    true,
                    true,
                    false
            );

            var criterion = PlayerInteractTrigger.TriggerInstance.itemUsedOnEntity(
                    ContextAwarePredicate.ANY,
                    ItemPredicate.Builder.item().of(Items.GLASS_BOTTLE),
                    EntityPredicate.wrap(EntityPredicate.Builder.entity().of(DDEntityTypes.VOID_SOUL.get()).build())
            );

            return addRequirementsAndSave("activities", criterionName, builder, criterion, saver, existingFileHelper);
        }

        private static Advancement obtainForsakenBronzeScrap(Advancement parent, Consumer<Advancement> saver, ExistingFileHelper existingFileHelper) {
            var builder = Advancement.Builder.advancement();
            var criterionName = "obtain_forsaken_bronze_scrap";

            builder.parent(parent);
            builder.display(
                    DDItems.FORSAKEN_BRONZE_SCRAP.get(),
                    Component.translatable("advancements.darkerdepths." + criterionName + ".title"),
                    Component.translatable("advancements.darkerdepths." + criterionName + ".description"),
                    null,
                    FrameType.TASK,
                    true,
                    true,
                    false
            );

            var criterion = InventoryChangeTrigger.TriggerInstance.hasItems(DDItems.FORSAKEN_BRONZE_SCRAP.get());

            return addRequirementsAndSave("activities", criterionName, builder, criterion, saver, existingFileHelper);
        }

        private static Advancement setDeathAnchorNode(Advancement parent, Consumer<Advancement> saver, ExistingFileHelper existingFileHelper) {
            var builder = Advancement.Builder.advancement();
            var criterionName = "set_death_anchor";

            builder.parent(parent);
            builder.display(
                    DDBlocks.DEATH_ANCHOR.get(),
                    Component.translatable("advancements.darkerdepths." + criterionName + ".title"),
                    Component.translatable("advancements.darkerdepths." + criterionName + ".description"),
                    null,
                    FrameType.TASK,
                    true,
                    true,
                    false
            );

            var criterion = ItemUsedOnLocationTrigger.TriggerInstance.itemUsedOnBlock(
                    LocationPredicate.Builder.location().setBlock(
                            BlockPredicate.Builder.block().of(DDBlocks.DEATH_ANCHOR.get()).build()
                    ),
                    ItemPredicate.Builder.item().of(DDItems.VOID_SOUL_REQUIEM.get())
            );

            return addRequirementsAndSave("activities", criterionName, builder, criterion, saver, existingFileHelper);
        }

        private static Advancement obtainGlowshroomCapNode(Advancement parent, Consumer<Advancement> saver, ExistingFileHelper existingFileHelper) {
            var builder = Advancement.Builder.advancement();
            var criterionName = "obtain_glowshroom_cap";

            builder.parent(parent);
            builder.display(
                    DDItems.GLOWSHROOM_CAP.get(),
                    Component.translatable("advancements.darkerdepths." + criterionName + ".title"),
                    Component.translatable("advancements.darkerdepths." + criterionName + ".description"),
                    null,
                    FrameType.TASK,
                    true,
                    true,
                    false
            );

            var criterion = InventoryChangeTrigger.TriggerInstance.hasItems(DDItems.GLOWSHROOM_CAP.get());

            return addRequirementsAndSave("activities", criterionName, builder, criterion, saver, existingFileHelper);
        }

        private static Advancement deadLivingCrystalNode(Advancement parent, Consumer<Advancement> saver, ExistingFileHelper existingFileHelper) {
            var builder = Advancement.Builder.advancement();
            var criterionName = "insert_diamond_into_crystal_husk";

            builder.parent(parent);
            builder.display(
                    DDBlocks.LIVING_CRYSTAL.get(),
                    Component.translatable("advancements.darkerdepths." + criterionName + ".title"),
                    Component.translatable("advancements.darkerdepths." + criterionName + ".description"),
                    null,
                    FrameType.TASK,
                    true,
                    true,
                    false
            );

            var criterion = ItemUsedOnLocationTrigger.TriggerInstance.itemUsedOnBlock(
                    LocationPredicate.Builder.location().setBlock(
                            BlockPredicate.Builder.block().of(DDBlocks.CRYSTAL_HUSK.get()).build()
                    ),
                    ItemPredicate.Builder.item().of(Items.DIAMOND)
            );

            return addRequirementsAndSave("activities", criterionName, builder, criterion, saver, existingFileHelper);
        }

        private static Advancement geyserNode(Advancement parent, Consumer<Advancement> saver, ExistingFileHelper existingFileHelper) {
            var builder = Advancement.Builder.advancement();
            var criterionName = "elytra_boosted_by_geyser";

            builder.parent(parent);
            builder.display(
                    DDBlocks.GEYSER.get(),
                    Component.translatable("advancements.darkerdepths." + criterionName + ".title"),
                    Component.translatable("advancements.darkerdepths." + criterionName + ".description"),
                    null,
                    FrameType.TASK,
                    true,
                    true,
                    false
            );

            var criterion = DDCriteria.GEYSER_ELYTRA_BOOST.instance();

            return addRequirementsAndSave("activities", criterionName, builder, criterion, saver, existingFileHelper);
        }

        private static Advancement catacombsNode(Advancement parent, HolderLookup.Provider registries, Consumer<Advancement> saver, ExistingFileHelper existingFileHelper) {
            var builder = Advancement.Builder.advancement();
            var key = DDResourceKeys.Structures.CATACOMBS;
            var structureName = key.location().getPath();
            var criterionName = "visited_" + structureName;

            builder.parent(parent);
            builder.display(
                    DDItems.VOID_SOUL_REQUIEM.get(),
                    Component.translatable("advancements.darkerdepths." + structureName + ".title"),
                    Component.translatable("advancements.darkerdepths." + structureName + ".description"),
                    null,
                    FrameType.TASK,
                    true,
                    true,
                    false
            );

            var criterion = PlayerTrigger.TriggerInstance.located(LocationPredicate.inStructure(key));

            return addRequirementsAndSave("visited/structure", criterionName, builder, criterion, saver, existingFileHelper);
        }

        private static Map<ResourceLocation, Advancement> biomeNodes(Advancement root, HolderLookup.Provider registries, Consumer<Advancement> saver, ExistingFileHelper existingFileHelper) {
            var biomeRegistry = registries.lookupOrThrow(Registries.BIOME);
            Map<ResourceLocation, Advancement> generatedBiomes = new HashMap<>();

            DDResourceKeys.Biomes.BIOMES.forEach(biomeKey -> {
                var biomeName = biomeKey.location().getPath();
                var builder = Advancement.Builder.advancement();
                var criterionName = "visited_" + biomeName;

                builder.parent(root);

                ItemLike item;
                if(biomeKey.equals(DDResourceKeys.Biomes.MOLTEN_CAVERN)) {
                    item = DDBlocks.DARKSLATE.get();
                } else if(biomeKey.equals(DDResourceKeys.Biomes.SANDY_CATACOMBS)) {
                    item = DDBlocks.ARIDROCK.get();
                } else {
                    item = DDBlocks.GRIMESTONE.get();
                }

                builder.display(
                        item,
                        Component.translatable("advancements.darkerdepths." + biomeName + ".title"),
                        Component.translatable("advancements.darkerdepths." + biomeName + ".description"),
                        null,
                        FrameType.TASK,
                        true,
                        false,
                        false
                );

                var criterion = PlayerTrigger.TriggerInstance.located(LocationPredicate.inBiome(biomeKey));
                builder.addCriterion(criterionName, criterion);

                Advancement savedAdvancement = builder.save(saver, DarkerDepths.id("visited/" + biomeName), existingFileHelper);
                generatedBiomes.put(biomeKey.location(), savedAdvancement);
            });

            return generatedBiomes;
        }

        private static Advancement parentNode(Consumer<Advancement> saver, ExistingFileHelper existingFileHelper) {
            var builder = Advancement.Builder.advancement();
            var backgroundTexture = DarkerDepths.id("textures/block/" + DDBlocks.DUSKROCK.getId().getPath() + ".png");
            var criterionName = "auto_grant";

            builder.display(
                    DDBlocks.GLOWSHROOM.get(),
                    Component.translatable("advancements.darkerdepths.parent.title"),
                    Component.translatable("advancements.darkerdepths.parent.description"),
                    backgroundTexture,
                    FrameType.GOAL,
                    true,
                    false,
                    false
            );

            builder.addCriterion(criterionName, PlayerTrigger.TriggerInstance.tick());
            return builder.save(saver, DarkerDepths.id("story/root"), existingFileHelper);
        }

        @Override
        public AdvancementSubProvider toSubProvider(ExistingFileHelper existingFileHelper) {
            return AdvancementGenerator.super.toSubProvider(existingFileHelper);
        }
    }

    private static Advancement addRequirementsAndSave(
            String category,
            String criterionName,
            Advancement.Builder builder,
            CriterionTriggerInstance criterion,
            Consumer<Advancement> saver,
            ExistingFileHelper existingFileHelper) {

        builder.addCriterion(criterionName, criterion);
        return builder.save(saver, DarkerDepths.id(category + "/" + criterionName), existingFileHelper);
    }
}