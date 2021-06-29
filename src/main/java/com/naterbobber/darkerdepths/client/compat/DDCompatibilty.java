package com.naterbobber.darkerdepths.client.compat;

import com.google.gson.JsonObject;
import com.naterbobber.darkerdepths.core.DarkerDepths;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.IConditionSerializer;
import net.minecraftforge.fml.ModList;

public class DDCompatibilty implements ICondition {
    private final String flag;
    private final ResourceLocation resourceLocation;

    public DDCompatibilty(String flag, ResourceLocation resourceLocation) {
        this.flag = flag;
        this.resourceLocation = resourceLocation;
    }

    @Override
    public ResourceLocation getID() {
        return this.resourceLocation;
    }

    @Override
    public boolean test() {
        if (ModList.get() != null && ModList.get().getModContainerById("quark").isPresent()) {
            JsonObject jsonDummy = new JsonObject();
            jsonDummy.addProperty("type", "quark:flag");
            jsonDummy.addProperty("flag", this.flag);
            return CraftingHelper.getCondition(jsonDummy).test();
        }
        return false;
    }

    public static class Serializer implements IConditionSerializer<DDCompatibilty> {
        private final ResourceLocation resourceLocation;

        public Serializer() {
            this.resourceLocation = new ResourceLocation(DarkerDepths.MODID, "quark_flag");
        }

        @Override
        public void write(JsonObject json, DDCompatibilty value) {
            json.addProperty("flag", value.flag);
        }

        @Override
        public DDCompatibilty read(JsonObject json) {
            return new DDCompatibilty(json.getAsJsonPrimitive("flag").getAsString(), this.resourceLocation);
        }

        @Override
        public ResourceLocation getID() {
            return this.resourceLocation;
        }
    }
}
