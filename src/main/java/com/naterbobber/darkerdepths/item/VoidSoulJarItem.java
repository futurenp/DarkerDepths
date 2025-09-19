package com.naterbobber.darkerdepths.item;

import com.naterbobber.darkerdepths.client.render.renderers.VoidSoulJarItemRenderer;
import com.naterbobber.darkerdepths.entities.VoidSoulEntity;
import com.naterbobber.darkerdepths.init.DDEntityTypes;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.function.Consumer;

public class VoidSoulJarItem extends BlockItem implements GeoItem {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public VoidSoulJarItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack heldStack = pPlayer.getItemInHand(pUsedHand);

        pLevel.playSound(pPlayer, pPlayer.blockPosition(), SoundEvents.WITCH_DRINK, SoundSource.PLAYERS, 1.0F, 1.0F);

        if (!pLevel.isClientSide()) {
            VoidSoulEntity voidSoul = new VoidSoulEntity(DDEntityTypes.VOID_SOUL.get(), pLevel);

            Vec3 spawnPos = pPlayer.getEyePosition().add(pPlayer.getLookAngle().scale(1.5));
            voidSoul.setPos(spawnPos);
            pLevel.addFreshEntity(voidSoul);


            if (!pPlayer.getAbilities().instabuild) {
                heldStack.shrink(1);

                ItemStack bottleStack = new ItemStack(Items.GLASS_BOTTLE);

                if (heldStack.isEmpty()) {
                    return InteractionResultHolder.success(bottleStack);
                } else {
                    if (!pPlayer.getInventory().add(bottleStack)) {
                        pPlayer.drop(bottleStack, false);
                    }
                }
            }
        }

        pPlayer.awardStat(Stats.ITEM_USED.get(this));
        pPlayer.getCooldowns().addCooldown(this, 10);

        return InteractionResultHolder.success(heldStack);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            private VoidSoulJarItemRenderer renderer;

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                if (this.renderer == null)
                    this.renderer = new VoidSoulJarItemRenderer();

                return this.renderer;
            }
        });
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar registrar) {
        registrar.add(new AnimationController<>(this, "controller", 0, this::animationPredicate));
    }
    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    private PlayState animationPredicate(AnimationState<VoidSoulJarItem> state) {
        state.getController().setAnimation(RawAnimation.begin().then("idle", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;
    }
}