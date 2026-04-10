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

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class DDAdvancementProvider extends ForgeAdvancementProvider {
    public DDAdvancementProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, existingFileHelper, List.of(new MainAdvancementGenerator()));
    }

    private static final class MainAdvancementGenerator implements AdvancementGenerator {
        @Override
        public void generate(HolderLookup.Provider registries, Consumer<Advancement> saver, ExistingFileHelper existingFileHelper) {
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

        private static void crystalMelonNode(Consumer<Advancement> saver, ExistingFileHelper existingFileHelper) {
            var builder = Advancement.Builder.advancement();
            var criterionName = "use_crystal_melon";

            builder.parent(new ResourceLocation("darkerdepths:activities/insert_diamond_into_crystal_husk"));
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

            addRequirementsAndSave("activities", criterionName, builder, criterion, saver, existingFileHelper);
        }

        private static void bottleVoidSoulNode(Consumer<Advancement> saver, ExistingFileHelper existingFileHelper) {
            var builder = Advancement.Builder.advancement();
            var biomeName = DDResourceKeys.Biomes.SANDY_CATACOMBS.location().getPath();
            var criterionName = "bottle_void_soul";

            builder.parent(new ResourceLocation("darkerdepths:visited/" + biomeName));
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

            addRequirementsAndSave("activities", criterionName, builder, criterion, saver, existingFileHelper);
        }

        private static void obtainForsakenBronzeScrap(Consumer<Advancement> saver, ExistingFileHelper existingFileHelper) {
            var builder = Advancement.Builder.advancement();
            var criterionName = "obtain_forsaken_bronze_scrap";

            builder.parent(new ResourceLocation("darkerdepths:visited/structure/visited_catacombs"));
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

            addRequirementsAndSave("activities", criterionName, builder, criterion, saver, existingFileHelper);
        }

        private static void setDeathAnchorNode(Consumer<Advancement> saver, ExistingFileHelper existingFileHelper) {
            var builder = Advancement.Builder.advancement();
            var criterionName = "set_death_anchor";

            builder.parent(new ResourceLocation("darkerdepths:activities/obtain_forsaken_bronze_scrap"));
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

            // 1.20.1 requires Builders here, NO .build()
            var criterion = ItemUsedOnLocationTrigger.TriggerInstance.itemUsedOnBlock(
                    LocationPredicate.Builder.location().setBlock(
                            BlockPredicate.Builder.block().of(DDBlocks.DEATH_ANCHOR.get()).build()
                    ),
                    ItemPredicate.Builder.item().of(DDItems.VOID_SOUL_REQUIEM.get())
            );

            addRequirementsAndSave("activities", criterionName, builder, criterion, saver, existingFileHelper);
        }

        private static void obtainGlowshroomCapNode(Consumer<Advancement> saver, ExistingFileHelper existingFileHelper) {
            var builder = Advancement.Builder.advancement();
            var biomeName = DDResourceKeys.Biomes.GLOWSHROOM_FOREST.location().getPath();
            var criterionName = "obtain_glowshroom_cap";

            builder.parent(new ResourceLocation("darkerdepths:visited/" + biomeName));
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

            addRequirementsAndSave("activities", criterionName, builder, criterion, saver, existingFileHelper);
        }

        private static void deadLivingCrystalNode(Consumer<Advancement> saver, ExistingFileHelper existingFileHelper) {
            var builder = Advancement.Builder.advancement();
            var biomeName = DDResourceKeys.Biomes.MOLTEN_CAVERN.location().getPath();
            var criterionName = "insert_diamond_into_crystal_husk";

            builder.parent(new ResourceLocation("darkerdepths:visited/" + biomeName));
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

            addRequirementsAndSave("activities", criterionName, builder, criterion, saver, existingFileHelper);
        }

        private static void geyserNode(Consumer<Advancement> saver, ExistingFileHelper existingFileHelper) {
            var builder = Advancement.Builder.advancement();
            var biomeName = DDResourceKeys.Biomes.MOLTEN_CAVERN.location().getPath();
            var criterionName = "elytra_boosted_by_geyser";

            builder.parent(new ResourceLocation("darkerdepths:visited/" + biomeName));
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

            addRequirementsAndSave("activities", criterionName, builder, criterion, saver, existingFileHelper);
        }

        private static void catacombsNode(HolderLookup.Provider registries, Consumer<Advancement> saver, ExistingFileHelper existingFileHelper) {
            var builder = Advancement.Builder.advancement();
            var biomeName = DDResourceKeys.Biomes.SANDY_CATACOMBS.location().getPath();
            var key = DDResourceKeys.Structures.CATACOMBS;
            var structureName = key.location().getPath();
            var criterionName = "visited_" + structureName;

            builder.parent(new ResourceLocation("darkerdepths:visited/" + biomeName));
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

            addRequirementsAndSave("visited/structure", criterionName, builder, criterion, saver, existingFileHelper);
        }

        private static void biomeNodes(HolderLookup.Provider registries, Consumer<Advancement> saver, ExistingFileHelper existingFileHelper) {
            var biomeRegistry = registries.lookupOrThrow(Registries.BIOME);
            DDResourceKeys.Biomes.BIOMES.forEach(biomeKey -> {
                var biomeName = biomeKey.location().getPath();
                var builder = Advancement.Builder.advancement();
                var criterionName = "visited_" + biomeName;
                builder.parent(new ResourceLocation("darkerdepths:story/root"));

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
                builder.save(saver, DarkerDepths.id("visited/" + biomeName), existingFileHelper);
            });
        }

        private static void parentNode(Consumer<Advancement> saver, ExistingFileHelper existingFileHelper) {
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
            builder.save(saver, DarkerDepths.id("story/root"), existingFileHelper);
        }

        @Override
        public AdvancementSubProvider toSubProvider(ExistingFileHelper existingFileHelper) {
            return AdvancementGenerator.super.toSubProvider(existingFileHelper);
        }
    }

    private static void addRequirementsAndSave(
            String category,
            String criterionName,
            Advancement.Builder builder,
            CriterionTriggerInstance criterion,
            Consumer<Advancement> saver,
            ExistingFileHelper existingFileHelper) {

        builder.addCriterion(criterionName, criterion);
        builder.save(saver, DarkerDepths.id(category + "/" + criterionName), existingFileHelper);
    }
}