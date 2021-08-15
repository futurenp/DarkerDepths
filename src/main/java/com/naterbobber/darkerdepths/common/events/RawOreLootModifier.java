package com.naterbobber.darkerdepths.common.events;

import com.google.gson.JsonObject;
import com.naterbobber.darkerdepths.core.DarkerDepthsConfig;
import com.naterbobber.darkerdepths.core.registries.DDItems;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameters;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//<>

public class RawOreLootModifier extends LootModifier {
    public RawOreLootModifier(ILootCondition[] conditionsIn) {
        super(conditionsIn);
    }

    @Nonnull
    @Override
    protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
        if (DarkerDepthsConfig.dropSilverRawOre.get()) {
            Random random = new Random();

            ItemStack ctxTool = context.get(LootParameters.TOOL);
            if (generatedLoot.size() == 0) return generatedLoot;

            ArrayList<ItemStack> returnable = checkLoot(generatedLoot);

            if (generatedLoot.get(0).getItem() == returnable.get(0).getItem() || EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH, ctxTool) != 0) {
                return generatedLoot;
            } else {
                int enchantmentLevel = EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, ctxTool);
                ArrayList<ItemStack> rawOre = new ArrayList<>();
                int size = generatedLoot.size();
                rawOre.add(new ItemStack(returnable.get(0).getItem(), size + random.nextInt(1 + enchantmentLevel)));
                return rawOre;
            }
        } else {
            return generatedLoot;
        }
    }

    private static ArrayList<ItemStack> getRawSilverOre() {
        ArrayList<ItemStack> returnable = new ArrayList<>();
        returnable.add(new ItemStack(DDItems.RAW_SILVER.get()));
        return returnable;
    }

    private ArrayList<ItemStack> checkLoot(List<ItemStack> generatedLoot) {
        Item rawOre = generatedLoot.get(0).getItem();

        if (rawOre.getTags().contains(new ResourceLocation("forge", "ores/silver"))) {
            return getRawSilverOre();
        }

        return (ArrayList<ItemStack>)generatedLoot;
    }

    public static class Serializer extends GlobalLootModifierSerializer<RawOreLootModifier> {
        @Override
        public RawOreLootModifier read(ResourceLocation location, JsonObject object, ILootCondition[] ailootcondition) {
            return new RawOreLootModifier(ailootcondition);
        }

        @Override
        public JsonObject write(RawOreLootModifier instance) {
            return new JsonObject();
        }
    }
}