package com.naterbobber.darkerdepths.advancements;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.advancements.criteria.CrystalMelonTrigger;
import com.naterbobber.darkerdepths.advancements.criteria.GeyserBoostTrigger;
import com.naterbobber.darkerdepths.init.DDBlocks;
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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.data.AdvancementProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class DDAdvancementProvider extends AdvancementProvider {
    public DDAdvancementProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, existingFileHelper, List.of(new MainAdvancementGenerator()));
    }

    private static final class MainAdvancementGenerator implements AdvancementProvider.AdvancementGenerator {
        @Override
        public void generate(HolderLookup.Provider registries, Consumer<AdvancementHolder> saver, ExistingFileHelper existingFileHelper) {
            parentNode(saver, existingFileHelper);
            biomeNodes(registries, saver, existingFileHelper);
            catacombsNode(registries, saver, existingFileHelper);
            geyserNode(saver, existingFileHelper);
            deadLivingCrystalNode(saver, existingFileHelper);
            obtainGlowshroomCapNode(saver, existingFileHelper);
            obtainForsakenBronzeScrap(saver, existingFileHelper);
            setDeathAnchorNode(saver, existingFileHelper);
            bottleVoidSoulNode(saver, existingFileHelper);
            crystalMelonNode(saver, existingFileHelper);
        }

        private static void crystalMelonNode(Consumer<AdvancementHolder> saver, ExistingFileHelper existingFileHelper) {
            var builder = Advancement.Builder.advancement();
            var criterionName = "use_crystal_melon";

            builder.parent(AdvancementSubProvider.createPlaceholder(("darkerdepths:activities/insert_diamond_into_crystal_husk")));
            builder.display(
                    DDBlocks.CRYSTAL_MELON.toStack(),
                    Component.translatable("advancements.darkerdepths." + criterionName + ".title"),
                    Component.translatable("advancements.darkerdepths." + criterionName + ".description"),
                    null,
                    AdvancementType.TASK,
                    true,
                    true,
                    false
            );

            var criterion = CrystalMelonTrigger.TriggerInstance.used();

            addRequirementsAndSave("activities", criterionName, builder, criterion, saver, existingFileHelper);
        }

        private static void bottleVoidSoulNode(Consumer<AdvancementHolder> saver, ExistingFileHelper existingFileHelper) {
            var builder = Advancement.Builder.advancement();
            var biomeName = DDResourceKeys.Biomes.SANDY_CATACOMBS.location().getPath();
            var criterionName = "bottle_void_soul";

            builder.parent(AdvancementSubProvider.createPlaceholder(("darkerdepths:visited/" + biomeName)));
            builder.display(
                    DDItems.VOID_SOUL_JAR.get(),
                    Component.translatable("advancements.darkerdepths." + criterionName + ".title"),
                    Component.translatable("advancements.darkerdepths." + criterionName + ".description"),
                    null,
                    AdvancementType.TASK,
                    true,
                    true,
                    false
            );

            var criterion = PlayerInteractTrigger.TriggerInstance.itemUsedOnEntity(
                    ItemPredicate.Builder.item().of(Items.GLASS_BOTTLE),
                    Optional.of(EntityPredicate.wrap(EntityPredicate.Builder.entity().of(DDEntityTypes.VOID_SOUL.get())))
            );

            addRequirementsAndSave("activities", criterionName, builder, criterion, saver, existingFileHelper);

        }

        private static void obtainForsakenBronzeScrap(Consumer<AdvancementHolder> saver, ExistingFileHelper existingFileHelper) {
            var builder = Advancement.Builder.advancement();
            var criterionName = "obtain_forsaken_bronze_scrap";

            builder.parent(AdvancementSubProvider.createPlaceholder(("darkerdepths:visited/structure/visited_catacombs")));
            builder.display(
                    DDItems.FORSAKEN_BRONZE_SCRAP.get(),
                    Component.translatable("advancements.darkerdepths." + criterionName + ".title"),
                    Component.translatable("advancements.darkerdepths." + criterionName + ".description"),
                    null,
                    AdvancementType.TASK,
                    true,
                    true,
                    false
            );

            var criterion = InventoryChangeTrigger.TriggerInstance.hasItems(DDItems.FORSAKEN_BRONZE_SCRAP.get());

            addRequirementsAndSave("activities", criterionName, builder, criterion, saver, existingFileHelper);
        }

        private static void setDeathAnchorNode(Consumer<AdvancementHolder> saver, ExistingFileHelper existingFileHelper) {
            var builder = Advancement.Builder.advancement();
            var criterionName = "set_death_anchor";

            builder.parent(AdvancementSubProvider.createPlaceholder(("darkerdepths:activities/obtain_forsaken_bronze_scrap")));
            builder.display(
                    DDBlocks.DEATH_ANCHOR.toStack(),
                    Component.translatable("advancements.darkerdepths." + criterionName + ".title"),
                    Component.translatable("advancements.darkerdepths." + criterionName + ".description"),
                    null,
                    AdvancementType.TASK,
                    true,
                    true,
                    false
            );

            var criterion = ItemUsedOnLocationTrigger.TriggerInstance.itemUsedOnBlock(
                    LocationPredicate.Builder.location().setBlock(
                            BlockPredicate.Builder.block().of(DDBlocks.DEATH_ANCHOR.get())
                    ),
                    ItemPredicate.Builder.item().of(DDItems.VOID_SOUL_REQUIEM)
            );

            addRequirementsAndSave("activities", criterionName, builder, criterion, saver, existingFileHelper);
        }

        private static void obtainGlowshroomCapNode(Consumer<AdvancementHolder> saver, ExistingFileHelper existingFileHelper) {
            var builder = Advancement.Builder.advancement();
            var biomeName = DDResourceKeys.Biomes.GLOWSHROOM_FOREST.location().getPath();
            var criterionName = "obtain_glowshroom_cap";

            builder.parent(AdvancementSubProvider.createPlaceholder(("darkerdepths:visited/" + biomeName)));
            builder.display(
                    DDItems.GLOWSHROOM_CAP.get(),
                    Component.translatable("advancements.darkerdepths." + criterionName + ".title"),
                    Component.translatable("advancements.darkerdepths." + criterionName + ".description"),
                    null,
                    AdvancementType.TASK,
                    true,
                    true,
                    false
            );

            var criterion = InventoryChangeTrigger.TriggerInstance.hasItems(DDItems.GLOWSHROOM_CAP.get());

            addRequirementsAndSave("activities", criterionName, builder, criterion, saver, existingFileHelper);
        }

        private static void deadLivingCrystalNode(Consumer<AdvancementHolder> saver, ExistingFileHelper existingFileHelper) {
            var builder = Advancement.Builder.advancement();
            var biomeName = DDResourceKeys.Biomes.MOLTEN_CAVERN.location().getPath();
            var criterionName = "insert_diamond_into_crystal_husk";

            builder.parent(AdvancementSubProvider.createPlaceholder(("darkerdepths:visited/" + biomeName)));
            builder.display(
                    DDBlocks.LIVING_CRYSTAL.toStack(),
                    Component.translatable("advancements.darkerdepths." + criterionName + ".title"),
                    Component.translatable("advancements.darkerdepths." + criterionName + ".description"),
                    null,
                    AdvancementType.TASK,
                    true,
                    true,
                    false
            );

            var criterion = ItemUsedOnLocationTrigger.TriggerInstance.itemUsedOnBlock(
                    LocationPredicate.Builder.location().setBlock(
                            BlockPredicate.Builder.block().of(DDBlocks.CRYSTAL_HUSK.get())
                    ),
                    ItemPredicate.Builder.item().of(Items.DIAMOND)
            );

            addRequirementsAndSave("activities", criterionName, builder, criterion, saver, existingFileHelper);
        }

        private static void geyserNode(Consumer<AdvancementHolder> saver, ExistingFileHelper existingFileHelper) {
            var builder = Advancement.Builder.advancement();
            var biomeName = DDResourceKeys.Biomes.MOLTEN_CAVERN.location().getPath();
            var criterionName = "elytra_boosted_by_geyser";

            builder.parent(AdvancementSubProvider.createPlaceholder(("darkerdepths:visited/" + biomeName)));
            builder.display(
                    DDBlocks.GEYSER.toStack(),
                    Component.translatable("advancements.darkerdepths." + criterionName + ".title"),
                    Component.translatable("advancements.darkerdepths." + criterionName + ".description"),
                    null,
                    AdvancementType.TASK,
                    true,
                    true,
                    false
            );

            var criterion = GeyserBoostTrigger.TriggerInstance.elytraBoosted();

            addRequirementsAndSave("activities", criterionName, builder, criterion, saver, existingFileHelper);
        }

        private static void catacombsNode(HolderLookup.Provider registries, Consumer<AdvancementHolder> saver, ExistingFileHelper existingFileHelper) {
            var builder = Advancement.Builder.advancement();
            var structureRegistry = registries.lookupOrThrow(Registries.STRUCTURE);
            var biomeName = DDResourceKeys.Biomes.SANDY_CATACOMBS.location().getPath();
            var key = DDResourceKeys.Structures.CATACOMBS;
            var structureName = key.location().getPath();
            var criterionName = "visited_" + structureName;

            builder.parent(AdvancementSubProvider.createPlaceholder(("darkerdepths:visited/" + biomeName)));
            builder.display(
                    DDItems.VOID_SOUL_REQUIEM.get(),
                    Component.translatable("advancements.darkerdepths." + structureName + ".title"),
                    Component.translatable("advancements.darkerdepths." + structureName + ".description"),
                    null,
                    AdvancementType.TASK,
                    true,
                    true,
                    false
            );

            var criterion = PlayerTrigger.TriggerInstance.located(
                    LocationPredicate.Builder.inStructure(structureRegistry.getOrThrow(key))
            );

            addRequirementsAndSave("visited/structure", criterionName, builder, criterion, saver, existingFileHelper);
        }

        private static void biomeNodes(HolderLookup.Provider registries, Consumer<AdvancementHolder> saver, ExistingFileHelper existingFileHelper) {
            var biomeRegistry = registries.lookupOrThrow(Registries.BIOME);
            DDResourceKeys.Biomes.BIOMES.forEach(biomeKey -> {
                var biomeName = biomeKey.location().getPath();
                var builder = Advancement.Builder.advancement();
                var criterionName = "visited_" + biomeName;
                builder.parent(AdvancementSubProvider.createPlaceholder(("darkerdepths:story/root")));

                ItemStack item;
                if(biomeKey.equals(DDResourceKeys.Biomes.MOLTEN_CAVERN)) {
                    item = DDBlocks.DARKSLATE.toStack();
                } else if(biomeKey.equals(DDResourceKeys.Biomes.SANDY_CATACOMBS)) {
                    item = DDBlocks.ARIDROCK.toStack();
                } else {
                    item = DDBlocks.GRIMESTONE.toStack();
                }

                builder.display(
                        item,
                        Component.translatable("advancements.darkerdepths." + biomeName + ".title"),
                        Component.translatable("advancements.darkerdepths." + biomeName + ".description"),
                        null,
                        AdvancementType.TASK,
                        true,
                        false,
                        false
                );

                var criterion = PlayerTrigger.TriggerInstance.located(
                        LocationPredicate.Builder.inBiome(biomeRegistry.getOrThrow(biomeKey)));

                builder.addCriterion(criterionName, criterion);
                builder.requirements(AdvancementRequirements.allOf(List.of(criterionName)));
                builder.save(saver, DarkerDepths.id("visited/" + biomeName), existingFileHelper);
            });
        }

        private static void parentNode(Consumer<AdvancementHolder> saver, ExistingFileHelper existingFileHelper) {
            var builder = Advancement.Builder.advancement();
            var backgroundTexture = DarkerDepths.id("textures/block/" + DDBlocks.DUSKROCK.getId().getPath() + ".png");
            var criterionName = "auto_grant";

            builder.display(
                    DDBlocks.GLOWSHROOM.toStack(),
                    Component.translatable("advancements.darkerdepths.parent.title"),
                    Component.translatable("advancements.darkerdepths.parent.description"),
                    backgroundTexture,
                    AdvancementType.GOAL,
                    true,
                    false,
                    false
            );

            builder.addCriterion(criterionName, PlayerTrigger.TriggerInstance.tick());
            builder.requirements(AdvancementRequirements.allOf(List.of(criterionName)));
            builder.save(saver, DarkerDepths.id("story/root"), existingFileHelper);
        }
    }

    private static <T extends CriterionTriggerInstance> void addRequirementsAndSave(
            String category,
            String criterionName,
            Advancement.Builder builder,
            Criterion<T> criterion,
            Consumer<AdvancementHolder> saver,
            ExistingFileHelper existingFileHelper) {
        builder.addCriterion(criterionName, criterion);
        builder.requirements(AdvancementRequirements.allOf(List.of(criterionName)));
        builder.save(saver, DarkerDepths.id(category + "/" + criterionName), existingFileHelper);
    }
}