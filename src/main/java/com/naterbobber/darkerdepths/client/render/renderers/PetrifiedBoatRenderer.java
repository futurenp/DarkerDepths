package com.naterbobber.darkerdepths.client.render.renderers;

import com.mojang.datafixers.util.Pair;
import com.naterbobber.darkerdepths.DarkerDepths;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.ChestBoatModel;
import net.minecraft.client.model.ListModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.vehicle.Boat;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class PetrifiedBoatRenderer extends BoatRenderer {
    private final Pair<ResourceLocation, ListModel<Boat>> boatResources;

    public PetrifiedBoatRenderer(EntityRendererProvider.Context context, boolean hasChest) {
        super(context, hasChest);
        this.boatResources = Pair.of(this.getTexture(hasChest), createBoatModel(context, hasChest));
    }

    private ListModel<Boat> createBoatModel(EntityRendererProvider.Context ctx, boolean chest) {
        ModelLayerLocation modellayerlocation = createModelLayerLocation(chest);
        ModelPart modelpart = ctx.bakeLayer(modellayerlocation);
        return (chest ? new ChestBoatModel(modelpart) : new BoatModel(modelpart));
    }

    @Override
    public Pair<ResourceLocation, ListModel<Boat>> getModelWithLocation(Boat boat) {
        return this.boatResources;
    }

    @NotNull
    public static ModelLayerLocation createModelLayerLocation(boolean hasChest) {
        return new ModelLayerLocation(DarkerDepths.id(hasChest ? "chest_boat/petrified" : "boat/petrified"), "main");
    }

    public ResourceLocation getTexture(boolean hasChest) {
        if (hasChest) {
            return DarkerDepths.id("textures/entity/chest_boat/petrified.png");
        }
        return DarkerDepths.id("textures/entity/boat/petrified.png");
    }
}
