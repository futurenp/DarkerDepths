package com.naterbobber.darkerdepths.advancements.criteria;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.naterbobber.darkerdepths.init.DDCriteria;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.server.level.ServerPlayer;

import java.util.Optional;

public class GeyserBoostTrigger extends SimpleCriterionTrigger<GeyserBoostTrigger.TriggerInstance> {

    @Override
    public Codec<TriggerInstance> codec() {
        return TriggerInstance.CODEC;
    }

    public void trigger(ServerPlayer player) {
        this.trigger(player, instance -> true);
    }

    public record TriggerInstance(Optional<ContextAwarePredicate> player) implements SimpleCriterionTrigger.SimpleInstance {
        public static final Codec<TriggerInstance> CODEC = RecordCodecBuilder.create(
                instance -> instance.group(
                        EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(TriggerInstance::player)
                ).apply(instance, TriggerInstance::new)
        );

        public static Criterion<TriggerInstance> elytraBoosted() {
            return DDCriteria.GEYSER_ELYTRA_BOOST.get().createCriterion(new TriggerInstance(Optional.empty()));
        }

        public static Criterion<TriggerInstance> boosted() {
            return DDCriteria.GEYSER_BOOST.get().createCriterion(new TriggerInstance(Optional.empty()));
        }

    }
}