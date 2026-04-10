package com.naterbobber.darkerdepths.advancements.criteria;

import com.google.gson.JsonObject;
import com.naterbobber.darkerdepths.DarkerDepths;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public class DDTrigger extends SimpleCriterionTrigger<DDTrigger.TriggerInstance> {
    private final ResourceLocation id;

    public DDTrigger(String name) {
        this.id = DarkerDepths.id(name);
    }

    public void trigger(ServerPlayer player) {
        this.trigger(player, instance -> true);
    }

    @Override
    protected TriggerInstance createInstance(JsonObject pJson, ContextAwarePredicate pPredicate, DeserializationContext pDeserializationContext) {
        return new TriggerInstance(id, pPredicate);
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    public static class TriggerInstance extends AbstractCriterionTriggerInstance {

        public TriggerInstance(ResourceLocation resourceLocation, ContextAwarePredicate composite) {
            super(resourceLocation, composite);
        }

    }
}