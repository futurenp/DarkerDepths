package com.naterbobber.darkerdepths.client.renderers;

import com.mojang.datafixers.util.Pair;
import com.naterbobber.darkerdepths.DarkerDepths;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class PetrifiedBoatRenderer extends BoatRenderer {
    private final Pair<ResourceLocation, BoatModel> boatResources;

    public PetrifiedBoatRenderer(EntityRendererProvider.Context context, boolean hasChest) {
        super(context, hasChest);
        this.boatResources = Pair.of(this.getTexture(hasChest), new BoatModel(context.bakeLayer(createModelLayerLocation(hasChest)), hasChest));
    }

    @Override
    public Pair<ResourceLocation, BoatModel> getModelWithLocation(Boat boat) {
        return this.boatResources;
    }

    @NotNull
    public static ModelLayerLocation createModelLayerLocation(boolean hasChest) {
        return new ModelLayerLocation(new ResourceLocation(DarkerDepths.MODID, hasChest ? "chest_boat/petrified" : "boat/petrified"), "main");
    }

    public ResourceLocation getTexture(boolean hasChest) {
        if (hasChest) {
            return new ResourceLocation(DarkerDepths.MODID, "textures/entity/chest_boat/petrified.png");
        }
        return new ResourceLocation(DarkerDepths.MODID, "textures/entity/boat/petrified.png");
    }
}
