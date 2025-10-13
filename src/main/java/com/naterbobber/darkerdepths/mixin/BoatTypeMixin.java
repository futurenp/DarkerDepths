package com.naterbobber.darkerdepths.mixin;

import com.naterbobber.darkerdepths.init.DDBlocks;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.Arrays;

@Mixin(Boat.Type.class)
public class BoatTypeMixin {

    @Shadow
    @Final
    @Mutable
    private static Boat.Type[] $VALUES;

    @Invoker("<init>")
    private static Boat.Type newBoatType(String name, int internalId, Block planks, String modelName) {
        throw new AssertionError();
    }

    @Inject(method = "<clinit>", at = @At(value = "FIELD", target = "Lnet/minecraft/world/entity/vehicle/Boat$Type;$VALUES:[Lnet/minecraft/world/entity/vehicle/Boat$Type;", shift = At.Shift.AFTER))
    private static void addCustomBoatType(CallbackInfo ci) {
        var types = new ArrayList<>(Arrays.asList($VALUES));
        var petrified = newBoatType("PETRIFIED", types.size(), DDBlocks.PETRIFIED_PLANKS.get(), "petrified");
        types.add(petrified);
        $VALUES = types.toArray(new Boat.Type[0]);
    }
}
