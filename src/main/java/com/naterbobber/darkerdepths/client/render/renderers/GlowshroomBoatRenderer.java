package com.naterbobber.darkerdepths.client.render.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.datafixers.util.Pair;
import com.mojang.math.Axis;
import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.client.render.DDRenderTypes;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.ChestBoatModel;
import net.minecraft.client.model.ListModel;
import net.minecraft.client.model.WaterPatchModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Leashable;
import net.minecraft.world.entity.vehicle.Boat;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.event.RenderNameTagEvent;
import net.neoforged.neoforge.common.NeoForge;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaternionf;

@OnlyIn(Dist.CLIENT)
public class GlowshroomBoatRenderer extends BoatRenderer {
    private final Pair<ResourceLocation, ListModel<Boat>> boatResources;
    private final ResourceLocation glowTexture;

    public GlowshroomBoatRenderer(EntityRendererProvider.Context context, boolean hasChest) {
        super(context, hasChest);
        this.boatResources = Pair.of(this.getTexture(hasChest), createBoatModel(context, hasChest));
        this.glowTexture = this.getGlowTexture(hasChest);
    }

    private ListModel<Boat> createBoatModel(EntityRendererProvider.Context ctx, boolean chest) {
        ModelLayerLocation modellayerlocation = createModelLayerLocation(chest);
        ModelPart modelpart = ctx.bakeLayer(modellayerlocation);
        return (chest ? new ChestBoatModel(modelpart) : new BoatModel(modelpart));
    }

    @Override
    public void render(Boat entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();
        poseStack.translate(0.0F, 0.375F, 0.0F);
        poseStack.mulPose(Axis.YP.rotationDegrees(180.0F - entityYaw));
        float f = (float)entity.getHurtTime() - partialTicks;
        float f1 = entity.getDamage() - partialTicks;
        if (f1 < 0.0F) {
            f1 = 0.0F;
        }

        if (f > 0.0F) {
            poseStack.mulPose(Axis.XP.rotationDegrees(Mth.sin(f) * f * f1 / 10.0F * (float)entity.getHurtDir()));
        }

        float f2 = entity.getBubbleAngle(partialTicks);
        if (!Mth.equal(f2, 0.0F)) {
            poseStack.mulPose((new Quaternionf()).setAngleAxis(entity.getBubbleAngle(partialTicks) * ((float)Math.PI / 180F), 1.0F, 0.0F, 1.0F));
        }

        var pair = this.getModelWithLocation(entity);
        var resourcelocation = pair.getFirst();
        var listmodel = pair.getSecond();
        poseStack.scale(-1.0F, -1.0F, 1.0F);
        poseStack.mulPose(Axis.YP.rotationDegrees(90.0F));
        listmodel.setupAnim(entity, partialTicks, 0.0F, -0.1F, 0.0F, 0.0F);


        var vertexconsumer = buffer.getBuffer(listmodel.renderType(resourcelocation));
        listmodel.renderToBuffer(poseStack, vertexconsumer, packedLight, OverlayTexture.NO_OVERLAY);

        var vertexconsumerGlow = buffer.getBuffer(DDRenderTypes.emissiveTransparent(glowTexture));
        listmodel.renderToBuffer(poseStack, vertexconsumerGlow, packedLight, OverlayTexture.NO_OVERLAY);

        if (!entity.isUnderWater()) {
            VertexConsumer vertexconsumer1 = buffer.getBuffer(RenderType.waterMask());
            if (listmodel instanceof WaterPatchModel) {
                WaterPatchModel waterpatchmodel = (WaterPatchModel)listmodel;
                waterpatchmodel.waterPatch().render(poseStack, vertexconsumer1, packedLight, OverlayTexture.NO_OVERLAY);
            }
        }

        poseStack.popPose();

        var entityLeashHolder = entity.getLeashHolder();
        if (entityLeashHolder != null) {
            this.renderLeash(entity, partialTicks, poseStack, buffer, entity);
        }

        var event = new RenderNameTagEvent(entity, entity.getDisplayName(), this, poseStack, buffer, packedLight, partialTicks);
        NeoForge.EVENT_BUS.post(event);
        if (event.canRender().isTrue() || event.canRender().isDefault() && this.shouldShowName(entity)) {
            this.renderNameTag(entity, event.getContent(), poseStack, buffer, packedLight, partialTicks);
        }
    }

    @Override
    public Pair<ResourceLocation, ListModel<Boat>> getModelWithLocation(Boat boat) {
        return this.boatResources;
    }

    @NotNull
    public static ModelLayerLocation createModelLayerLocation(boolean hasChest) {
        return new ModelLayerLocation(DarkerDepths.id(hasChest ? "chest_boat/glowshroom" : "boat/glowshroom"), "main");
    }

    public ResourceLocation getTexture(boolean hasChest) {
        if (hasChest) {
            return DarkerDepths.id("textures/entity/chest_boat/glowshroom.png");
        }
        return DarkerDepths.id("textures/entity/boat/glowshroom.png");
    }

    public ResourceLocation getGlowTexture(boolean hasChest) {
        if (hasChest) {
            return DarkerDepths.id("textures/entity/chest_boat/glowshroom_glow.png");
        }
        return DarkerDepths.id("textures/entity/boat/glowshroom_glow.png");
    }
}
