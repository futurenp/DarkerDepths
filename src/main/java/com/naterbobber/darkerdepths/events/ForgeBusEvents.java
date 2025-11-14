package com.naterbobber.darkerdepths.events;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.config.DDConfigs;
import com.naterbobber.darkerdepths.init.DDBlocks;
import com.naterbobber.darkerdepths.init.DDDataComponents;
import com.naterbobber.darkerdepths.util.SuperchargeHelper;
import com.naterbobber.darkerdepths.component.SuperchargeInfo;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

@EventBusSubscriber(modid = DarkerDepths.MOD_ID)
public class ForgeBusEvents {

    @SubscribeEvent
    public static void onApplySupercharge(PlayerInteractEvent.RightClickItem event) {
        Player player = event.getEntity();
        Level level = event.getLevel();

        if (level.isClientSide()) {
            return;
        }
        if (event.getHand() != InteractionHand.MAIN_HAND) {
            return;
        }

        ItemStack mainHandStack = player.getMainHandItem();
        ItemStack offHandStack = player.getOffhandItem();

        boolean isEligible = mainHandStack.isDamageableItem() || mainHandStack.has(DataComponents.UNBREAKABLE);

        if (offHandStack.is(DDBlocks.CRYSTAL_MELON.get().asItem()) &&
                isEligible &&
                !(mainHandStack.getItem() instanceof ArmorItem) &&
                !isSupercharged(mainHandStack, level)) {

            SuperchargeHelper.applyUpgrades(mainHandStack, level);
            offHandStack.shrink(1);
//            level.playSound(null, player.blockPosition(), SoundEvents.BEACON_ACTIVATE, SoundSource.PLAYERS, 0.75f, 1.6f);
            player.playSound(SoundEvents.BEACON_ACTIVATE, 0.75f, 1.6f);

            event.setCancellationResult(InteractionResult.SUCCESS);
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onItemTooltip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        Player player = event.getEntity();

        if (player == null || !isSupercharged(stack, player.level())) {
            return;
        }

        int digSpeedBuff = DDConfigs.SUPERCHARGE_DIG_SPEED.get();
        int attackSpeedBuff = DDConfigs.SUPERCHARGE_ATTACK_SPEED.get();
        int attackDamageBuff = DDConfigs.SUPERCHARGE_ATTACK_DAMAGE.get();

        event.getToolTip().add(Component.empty());
        event.getToolTip().add(Component.literal("Supercharged:").withStyle(ChatFormatting.AQUA));
        event.getToolTip().add(Component.literal(" +"+ digSpeedBuff +"% Dig Speed").withStyle(ChatFormatting.GRAY));
        event.getToolTip().add(Component.literal(" +"+ attackDamageBuff +"% Attack Damage").withStyle(ChatFormatting.GRAY));
        event.getToolTip().add(Component.literal(" +"+ attackSpeedBuff +"% Attack Speed").withStyle(ChatFormatting.GRAY));
        if(DDConfigs.SUPERCHARGE_UNBREAKABLE.get()){
            event.getToolTip().add(Component.literal("Unbreakable").withStyle(ChatFormatting.BLUE));
        }

        SuperchargeInfo info = stack.get(DDDataComponents.SUPERCHARGE_INFO.get());
        if (info == null) return;

        long expirationTick = info.expirationTick();
        long currentTime = player.level().getGameTime();

        long remainingTicks = expirationTick - currentTime;
        long remainingSeconds = remainingTicks / 20;
        String timeFormatted = String.format("%02d:%02d", remainingSeconds / 60, remainingSeconds % 60);

        event.getToolTip().add(Component.literal("Time Remaining: " + timeFormatted)
                .withStyle(ChatFormatting.DARK_GRAY));
    }

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        if (event.getEntity() instanceof Player player && player.level() instanceof ServerLevel level) {
            if (!level.isClientSide) {
                isSupercharged(player.getMainHandItem(), level);
                isSupercharged(player.getOffhandItem(), level);
            }
        }
    }
    @SubscribeEvent
    public static void onBreakSpeed(PlayerEvent.BreakSpeed event) {
        ItemStack heldItem = event.getEntity().getMainHandItem();
        Level level = event.getEntity().level();

        if (isSupercharged(heldItem, level)) {
            float currentSpeed = event.getNewSpeed();
            event.setNewSpeed(currentSpeed * (1F + (DDConfigs.SUPERCHARGE_DIG_SPEED.get()) / 100F));
        }
    }

    private static boolean isSupercharged(ItemStack stack, Level level) {
        if (stack.isEmpty()) {
            return false;
        }

        SuperchargeInfo info = stack.get(DDDataComponents.SUPERCHARGE_INFO.get());
        if (info == null) {
            return false;
        }

        if (level.getGameTime() >= info.expirationTick()) {
            SuperchargeHelper.revertUpgrades(stack);
            return false;
        }
        return true;
    }
}

