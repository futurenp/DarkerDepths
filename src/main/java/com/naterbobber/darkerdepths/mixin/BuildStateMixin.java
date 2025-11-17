package com.naterbobber.darkerdepths.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.naterbobber.darkerdepths.DarkerDepths;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.resources.ResourceKey;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.List;

@Mixin(RegistrySetBuilder.BuildState.class)
public class BuildStateMixin {

    @WrapOperation(method = "reportNotCollectedHolders",
            at = @At(value = "INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)Z", ordinal = 0)
    )
    public <E> boolean G$reportNotCollectedHolders(List<RuntimeException> instance, E error, Operation<Boolean> original, @Local ResourceKey<Object> resourcekey) {
        if (resourcekey.location().getNamespace().equals(DarkerDepths.MOD_ID)) return false;

        return original.call(instance, error);
    }

}