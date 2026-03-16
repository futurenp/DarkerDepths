package com.naterbobber.darkerdepths.advancements;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.init.DDBlocks;
import com.naterbobber.darkerdepths.init.DDItems;
import com.naterbobber.darkerdepths.util.DDResourceKeys;
import com.naterbobber.darkerdepths.util.DDTags;
import com.naterbobber.darkerdepths.worldgen.DDBiomes;
import net.minecraft.advancements.*;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.LocationPredicate;
import net.minecraft.advancements.critereon.PlayerTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.advancements.AdvancementSubProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.data.AdvancementProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.List;
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
        }

        private static void biomeNodes(HolderLookup.Provider registries, Consumer<AdvancementHolder> saver, ExistingFileHelper existingFileHelper) {
            var biomeRegistry = registries.lookupOrThrow(Registries.BIOME);
            DDResourceKeys.Biomes.BIOMES.forEach(biomeKey -> {
                var biomeName = biomeKey.location().getPath();
                var builder = Advancement.Builder.advancement();
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

                var criterion = PlayerTrigger.TriggerInstance.located(LocationPredicate.Builder.inBiome(biomeRegistry.getOrThrow(biomeKey)));
                var criterionName = "visited_" + biomeName;
                builder.addCriterion(criterionName, criterion);
                builder.requirements(AdvancementRequirements.allOf(List.of(criterionName)));
                builder.save(saver, DarkerDepths.id("visited/" + biomeName), existingFileHelper);
            });
        }

        private static void parentNode(Consumer<AdvancementHolder> saver, ExistingFileHelper existingFileHelper) {
            var builder = Advancement.Builder.advancement();
            var backgroundTexture = DarkerDepths.id("textures/block/" + DDBlocks.GRIMESTONE.getId().getPath() + ".png");

            builder.display(
                    new ItemStack(DDItems.GLOWSHROOM_CAP.get()),
                    Component.translatable("advancements.darkerdepths.parent.title"),
                    Component.translatable("advancements.darkerdepths.parent.description"),
                    backgroundTexture,
                    AdvancementType.GOAL,
                    true,
                    false,
                    false
            );
            builder.addCriterion("auto_grant", PlayerTrigger.TriggerInstance.tick());
            builder.requirements(AdvancementRequirements.allOf(List.of("auto_grant")));
            builder.save(saver, DarkerDepths.id("story/root"), existingFileHelper);
        }
    }
}