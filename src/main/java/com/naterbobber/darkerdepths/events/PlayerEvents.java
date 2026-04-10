package com.naterbobber.darkerdepths.events;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.config.DDConfig;
import com.naterbobber.darkerdepths.init.DDBlocks;
import com.naterbobber.darkerdepths.util.SuperchargeHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DarkerDepths.MOD_ID)
public class PlayerEvents {

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
        boolean isEligible = mainHandStack.isDamageableItem() || (mainHandStack.hasTag() && mainHandStack.getTag().getBoolean("Unbreakable"));

        if (offHandStack.is(DDBlocks.CRYSTAL_MELON.get().asItem()) &&
                isEligible &&
                !(mainHandStack.getItem() instanceof ArmorItem) &&
                !isSuperchargedAndNotExpired(mainHandStack, level)) {

            SuperchargeHelper.applyUpgrades(mainHandStack, level);
            offHandStack.shrink(1);
            level.playSound(null, player.blockPosition(), SoundEvents.BEACON_ACTIVATE, SoundSource.PLAYERS, 0.75f, 1.6f);

            event.setCancellationResult(InteractionResult.SUCCESS);
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onItemTooltip(ItemTooltipEvent event) {
        int digSpeedBuff = DDConfig.SUPERCHARGE_DIG_SPEED.get();
        int attackSpeedBuff = DDConfig.SUPERCHARGE_ATTACK_SPEED.get();
        int attackDamageBuff = DDConfig.SUPERCHARGE_ATTACK_DAMAGE.get();
        ItemStack stack = event.getItemStack();
        Player player = event.getEntity();

        if (player == null || !isSuperchargedAndNotExpired(stack, player.level())) {
            return;
        }

        event.getToolTip().add(Component.empty()); // Adds a blank line for spacing
        event.getToolTip().add(Component.literal("Supercharged:").withStyle(ChatFormatting.AQUA));
        event.getToolTip().add(Component.literal(" +"+ digSpeedBuff +"% Dig Speed").withStyle(ChatFormatting.GRAY));
        event.getToolTip().add(Component.literal(" +"+ attackSpeedBuff +"% Attack Damage").withStyle(ChatFormatting.GRAY));
        event.getToolTip().add(Component.literal(" +"+ attackDamageBuff +"% Attack Speed").withStyle(ChatFormatting.GRAY));
        if(DDConfig.SUPERCHARGE_UNBREAKABLE.get()){
            event.getToolTip().add(Component.literal("Unbreakable").withStyle(ChatFormatting.BLUE));
        }

        CompoundTag upgradeTag = stack.getTag().getCompound(SuperchargeHelper.TAG_UPGRADE_MAIN);
        long expirationTick = upgradeTag.getLong(SuperchargeHelper.TAG_EXPIRATION);
        long currentTime = player.level().getGameTime();

        long remainingTicks = expirationTick - currentTime;
        long remainingSeconds = remainingTicks / 20;
        String timeFormatted = String.format("%02d:%02d", remainingSeconds / 60, remainingSeconds % 60);

        event.getToolTip().add(Component.literal("Time Remaining: " + timeFormatted)
                .withStyle(ChatFormatting.DARK_GRAY));
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END && !event.player.level().isClientSide) {
            Player player = event.player;
            Level level = player.level();

            isSuperchargedAndNotExpired(player.getMainHandItem(), level);
            isSuperchargedAndNotExpired(player.getOffhandItem(), level);
        }
    }
    @SubscribeEvent
    public static void onBreakSpeed(PlayerEvent.BreakSpeed event) {
        ItemStack heldItem = event.getEntity().getMainHandItem();
        Level level = event.getEntity().level();

        if (isSuperchargedAndNotExpired(heldItem, level)) {

            float currentSpeed = event.getNewSpeed();
            event.setNewSpeed(currentSpeed * (1F + (DDConfig.SUPERCHARGE_DIG_SPEED.get()) / 100F));
        }
    }

    private static boolean isSuperchargedAndNotExpired(ItemStack stack, Level level) {
        if (stack.isEmpty() || !stack.hasTag() || !stack.getTag().contains(SuperchargeHelper.TAG_UPGRADE_MAIN)) {
            return false;
        }

        CompoundTag upgradeTag = stack.getTag().getCompound(SuperchargeHelper.TAG_UPGRADE_MAIN);
        long expirationTick = upgradeTag.getLong(SuperchargeHelper.TAG_EXPIRATION);

        if (level.getGameTime() >= expirationTick) {
            SuperchargeHelper.revertUpgrades(stack);
            return false;
        }
        return true;
    }
}