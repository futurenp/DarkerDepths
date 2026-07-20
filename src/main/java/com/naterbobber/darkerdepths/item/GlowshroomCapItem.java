package com.naterbobber.darkerdepths.item;

import com.naterbobber.darkerdepths.client.render.renderers.GlowshroomCapRenderer;
import com.naterbobber.darkerdepths.init.DDArmorMaterials;
import com.naterbobber.darkerdepths.init.DDMobEffects;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.client.GeoRenderProvider;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.renderer.GeoArmorRenderer;
import software.bernie.geckolib.util.GeckoLibUtil;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Consumer;

public class GlowshroomCapItem extends ArmorItem implements GeoItem {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);


    public GlowshroomCapItem(Properties properties) {
        super(DDArmorMaterials.GLOWSHROOM_MATERIAL, Type.HELMET, properties);
    }

    @Override
    public void createGeoRenderer(Consumer<GeoRenderProvider> consumer) {
        consumer.accept(new GeoRenderProvider() {
            private GeoArmorRenderer<?> renderer;

            @Override
            public <T extends LivingEntity> HumanoidModel<?> getGeoArmorRenderer(@Nullable T livingEntity, ItemStack itemStack, @Nullable EquipmentSlot equipmentSlot, @Nullable HumanoidModel<T> original) {
                if(this.renderer == null)
                    this.renderer = new GlowshroomCapRenderer();

                return this.renderer;
            }
        });
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        if(level.isClientSide) return;
        if(!(entity instanceof LivingEntity livingEntity)) return;

        if(!livingEntity.getItemBySlot(EquipmentSlot.HEAD).is(this)) {
            return;
        }

        if(!livingEntity.hasEffect(DDMobEffects.GLOWING_MYCOSES)) {
            livingEntity.addEffect(new MobEffectInstance(DDMobEffects.GLOWING_MYCOSES, MobEffectInstance.INFINITE_DURATION, 0, false, false, true));
        }

        if(slotId == 39 && level.isDay()) {
            if(livingEntity instanceof Player player) {
                if(level.getGameTime() % 20 != 0) return;

                if(level.canSeeSky(livingEntity.blockPosition())) {
                    level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.CANDLE_EXTINGUISH, SoundSource.PLAYERS, 1.0F, 1.0F);

                    var cap = livingEntity.getItemBySlot(EquipmentSlot.HEAD);
                    var damage = cap.getDamageValue();
                    if(damage + 1 < cap.getMaxDamage()) {
                        cap.setDamageValue(cap.getDamageValue() + 2);
                    } else {
                        livingEntity.setItemSlot(EquipmentSlot.HEAD, ItemStack.EMPTY);
                    }
                }
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack pStack, TooltipContext context, List<Component> pTooltipComponents, TooltipFlag tooltipFlag) {
        pTooltipComponents.add(Component.translatable("tooltip.darkerdepths.press_shift").withStyle(ChatFormatting.GRAY));

        if (Screen.hasShiftDown()) {
            pTooltipComponents.add(Component.translatable("tooltip.darkerdepths.glowshroom_cap.shift_desc_1").withStyle(ChatFormatting.AQUA));
            pTooltipComponents.add(Component.translatable("tooltip.darkerdepths.glowshroom_cap.shift_desc_2").withStyle(ChatFormatting.GREEN));
        }

        super.appendHoverText(pStack, context, pTooltipComponents, tooltipFlag);
    }


    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {

    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
}