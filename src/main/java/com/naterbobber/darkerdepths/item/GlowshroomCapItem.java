package com.naterbobber.darkerdepths.item;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.client.render.renderers.GlowshroomCapRenderer;
import com.naterbobber.darkerdepths.init.DDItems;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.renderer.GeoArmorRenderer;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;
import java.util.function.Consumer;

public class GlowshroomCapItem extends ArmorItem implements GeoItem {
    private static final GlowshroomArmorMaterial MATERIAL = new GlowshroomArmorMaterial();
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public GlowshroomCapItem(Properties properties) {
        super(MATERIAL, Type.HELMET, properties);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            private GeoArmorRenderer<?> renderer;

            @Override
            public @NotNull HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
                if (this.renderer == null)
                    this.renderer = new GlowshroomCapRenderer();

                this.renderer.prepForRender(livingEntity, itemStack, equipmentSlot, original);
                return this.renderer;
            }
        });
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.translatable("tooltip.darkerdepths.press_shift").withStyle(ChatFormatting.GRAY));

        if (Screen.hasShiftDown()) {
            pTooltipComponents.add(Component.translatable("tooltip.darkerdepths.glowshroom_cap.shift_desc_1").withStyle(ChatFormatting.AQUA));
            pTooltipComponents.add(Component.translatable("tooltip.darkerdepths.glowshroom_cap.shift_desc_2").withStyle(ChatFormatting.GREEN));
        }

        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        return DarkerDepths.id("textures/item/armor/glowshroom_cap.png").toString();

    }

    private static class GlowshroomArmorMaterial implements ArmorMaterial {
        @Override
        public int getDurabilityForType(Type type) {
            return 13 * 12;
        }

        @Override
        public int getDefenseForType(Type type) {
            return 1;
        }

        @Override
        public int getEnchantmentValue() {
            return 0;
        }

        @Override
        public SoundEvent getEquipSound() {
            return SoundEvents.ARMOR_EQUIP_LEATHER;
        }

        @Override
        public Ingredient getRepairIngredient() {
            return Ingredient.of(DDItems.GLOW_GRIME.get());
        }

        @Override
        public String getName() {
            return "glowshroom";
        }

        @Override
        public float getToughness() {
            return 0.0F;
        }

        @Override
        public float getKnockbackResistance() {
            return 0.0F;
        }
    }
}