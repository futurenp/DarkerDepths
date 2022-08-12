package com.naterbobber.darkerdepths.mixin;

import com.naterbobber.darkerdepths.init.DDFluidTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;move(Lnet/minecraft/world/entity/MoverType;Lnet/minecraft/world/phys/Vec3;)V", ordinal = 1), method = "travel")
    private void DD$travel(Vec3 velocity, CallbackInfo ci) {
        LivingEntity $this = (LivingEntity) (Object) this;
        double d0 = 0.08D;
        boolean flag = $this.getDeltaMovement().y <= 0.0D;
        if ($this.getFluidTypeHeight(DDFluidTypes.AMBER_FLUID_TYPE.get()) <= $this.getFluidJumpThreshold()) {
            $this.setDeltaMovement($this.getDeltaMovement().multiply(0.5D, 0.8F, 0.5D));
            Vec3 vec33 = $this.getFluidFallingAdjustedMovement(d0, flag, $this.getDeltaMovement());
            $this.setDeltaMovement(vec33);
        }
    }

    @Inject(at = @At(value = "HEAD"), method = "baseTick")
    private void DD$baseTick(CallbackInfo ci) {
        LivingEntity $this = (LivingEntity) (Object) this;
        if ($this.isInFluidType(DDFluidTypes.AMBER_FLUID_TYPE.get()) && !($this instanceof Player)) {
            $this.discard();
            $this.playSound(SoundEvents.HONEY_BLOCK_PLACE, 1.0F, 1.0F);
        }
    }

}
