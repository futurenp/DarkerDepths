package com.naterbobber.darkerdepths.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.Unbreakable;

import java.util.Optional;

public record SuperchargeInfo(
        long expirationTick,
        Optional<Unbreakable> originalUnbreakable,
        ItemAttributeModifiers originalModifiers
) {
    public static final Codec<SuperchargeInfo> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.LONG.fieldOf("expiration_tick").forGetter(SuperchargeInfo::expirationTick),
                    Unbreakable.CODEC.optionalFieldOf("original_unbreakable").forGetter(SuperchargeInfo::originalUnbreakable),
                    ItemAttributeModifiers.CODEC.fieldOf("original_modifiers").forGetter(SuperchargeInfo::originalModifiers)
            ).apply(instance, SuperchargeInfo::new)
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, SuperchargeInfo> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_LONG,
            SuperchargeInfo::expirationTick,
            ByteBufCodecs.optional(Unbreakable.STREAM_CODEC),
            SuperchargeInfo::originalUnbreakable,
            ItemAttributeModifiers.STREAM_CODEC,
            SuperchargeInfo::originalModifiers,
            SuperchargeInfo::new
    );
}

