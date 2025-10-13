package com.naterbobber.darkerdepths.init;

import com.naterbobber.darkerdepths.entities.VoidSoulEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.DispenserBlockEntity;

public class DDVanillaIntegration {

    public static void init() {
        registerFlammables();
        registerCompostables();
        registerDispenserBehaviors();
    }

    private static void registerFlammables() {
        FireBlock fireBlock = (FireBlock) Blocks.FIRE;
        fireBlock.setFlammable(DDBlocks.ROPE.get(), 60, 100);
        fireBlock.setFlammable(DDBlocks.PETRIFIED_ROOTS.get(), 60, 100);
        fireBlock.setFlammable(DDBlocks.DRY_SPROUTS.get(), 60, 100);
        fireBlock.setFlammable(DDBlocks.MOSSY_SPROUTS.get(), 60, 100);
    }

    private static void registerCompostables() {
//        ComposterBlock.add(0.5F, DDBlocks.GLOWSHROOM.get().asItem());
//        ComposterBlock.add(0.85F, DDBlocks.GLOWSHROOM_BLOCK.get().asItem());
//        ComposterBlock.add(0.5F, DDBlocks.GLOWSPURS.get().asItem());
//        ComposterBlock.add(0.3F,  DDBlocks.GLIMMERING_VINES.get().asItem());
//        ComposterBlock.add(0.3F, DDBlocks.MOSSY_SPROUTS.get().asItem());
//        ComposterBlock.add(0.2F,  DDBlocks.PETRIFIED_ROOTS.get().asItem());
    }

    private static void registerDispenserBehaviors() {
        DispenserBlock.registerBehavior(DDItems.VOID_SOUL_JAR.get(), (source, stack) -> {
            ServerLevel level = source.level();
            Direction direction = source.state().getValue(DispenserBlock.FACING);
            BlockPos pos = source.pos();

            VoidSoulEntity entity = new VoidSoulEntity(DDEntityTypes.VOID_SOUL.get(), level);

            entity.setPos(pos.getX() + 0.5 + direction.getStepX(),
                    pos.getY() + 0.5 + direction.getStepY(),
                    pos.getZ() + 0.5 + direction.getStepZ());
            level.addFreshEntity(entity);

            level.playSound(null, pos, SoundEvents.WITCH_DRINK, SoundSource.BLOCKS, 1.0F, 1.0F);

            stack.shrink(1);

            if (source.blockEntity() instanceof DispenserBlockEntity dispenser) {
                ItemStack bottleStack = new ItemStack(Items.GLASS_BOTTLE);
                boolean wasBottlePlaced = false;

                for (int i = 0; i < dispenser.getContainerSize(); ++i) {
                    ItemStack slotStack = dispenser.getItem(i);
                    if (slotStack.is(Items.GLASS_BOTTLE) && slotStack.getCount() < slotStack.getMaxStackSize()) {
                        slotStack.grow(1);
                        wasBottlePlaced = true;
                        break;
                    }
                }

                if (!wasBottlePlaced) {
                    for (int i = 0; i < dispenser.getContainerSize(); ++i) {
                        if (dispenser.getItem(i).isEmpty()) {
                            dispenser.setItem(i, bottleStack);
                            wasBottlePlaced = true;
                            break;
                        }
                    }
                }

                if (!wasBottlePlaced) {
                    DefaultDispenseItemBehavior fallback = new DefaultDispenseItemBehavior();
                    fallback.dispense(source, bottleStack);
                }
            }

            return stack;
        });
    }
}

