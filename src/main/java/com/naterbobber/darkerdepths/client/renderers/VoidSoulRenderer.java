package com.naterbobber.darkerdepths.client.renderers;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.client.models.VoidSoulModel;
import com.naterbobber.darkerdepths.client.renderers.layers.VoidSoulEmissiveLayer;
import com.naterbobber.darkerdepths.entities.VoidSoulEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

import java.util.HashMap;
import java.util.Map;

public class VoidSoulRenderer extends GeoEntityRenderer<VoidSoulEntity> {

    public record DebugData(Vector3f viewPos, Vector3f viewNormal, boolean wasRendered, float dotProduct) {}

    private static final ResourceLocation OUTER_TEXTURE = ResourceLocation.fromNamespaceAndPath(DarkerDepths.MODID, "textures/entity/void_soul/void_soul.png");

    private static final float FACE_Y_OFFSET = 0.35f;

    private static final Map<String, Vector4f[]> FACE_TRIANGLES = Map.of(
            "face_north",
            new Vector4f[]{
                    new Vector4f(0, FACE_Y_OFFSET,0,1),
                    new Vector4f(0,1 + FACE_Y_OFFSET,0,1),
                    new Vector4f(1, FACE_Y_OFFSET,0,1)
            },
            "face_south", new Vector4f[]{
                    new Vector4f(0, FACE_Y_OFFSET,0,1),
                    new Vector4f(1, FACE_Y_OFFSET,0,1), new
                    Vector4f(0,1 + FACE_Y_OFFSET,0,1)
            },
            "face_east",  new Vector4f[]{
                    new Vector4f(0, FACE_Y_OFFSET,0,1),
                    new Vector4f(0,1 + FACE_Y_OFFSET,0,1),
                    new Vector4f(0, FACE_Y_OFFSET,1,1)
            },
            "face_west",  new Vector4f[]{
                    new Vector4f(0, FACE_Y_OFFSET,0,1),
                    new Vector4f(0, FACE_Y_OFFSET,1,1), new
                    Vector4f(0,1 + FACE_Y_OFFSET,0,1)
            },
            "face_top",   new Vector4f[]{
                    new Vector4f(0, FACE_Y_OFFSET,0,1),
                    new Vector4f(0, FACE_Y_OFFSET,1,1), new
                    Vector4f(1, FACE_Y_OFFSET,0,1)},

            "face_bottom",new Vector4f[]{
                    new Vector4f(0, FACE_Y_OFFSET,0,1),
                    new Vector4f(1, FACE_Y_OFFSET,0,1), new
                    Vector4f(0, FACE_Y_OFFSET,1,1)
            }
    );

    private final Map<String, DebugData> frameDebugData = new HashMap<>();

    public VoidSoulRenderer(EntityRendererProvider.Context context) {
        super(context, new VoidSoulModel());
        addRenderLayer(new VoidSoulEmissiveLayer(this));
    }

    @Override
    public ResourceLocation getTextureLocation(VoidSoulEntity animatable) {
        return OUTER_TEXTURE;
    }

    @Override
    public void preRender(PoseStack poseStack, VoidSoulEntity animatable, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        super.preRender(poseStack, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
        this.frameDebugData.clear();
        model.getBone("inner").ifPresent(bone -> bone.setHidden(true));
        model.getBone("outer").ifPresent(bone -> bone.setHidden(false));
    }

    @Override
    public void renderRecursively(PoseStack poseStack, VoidSoulEntity animatable, GeoBone bone, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        boolean shouldRender = true;
        String boneName = bone.getName();

        if (FACE_TRIANGLES.containsKey(boneName)) {
            Matrix4f modelViewMatrix = new Matrix4f(poseStack.last().pose());
            Matrix4f projectionMatrix = RenderSystem.getProjectionMatrix();
            boolean isReflected = modelViewMatrix.determinant() < 0;
            Matrix4f mvpMatrix = new Matrix4f(projectionMatrix).mul(modelViewMatrix);

            Vector4f[] localVerts = FACE_TRIANGLES.get(boneName);
            Vector4f v0Clip = mvpMatrix.transform(new Vector4f(localVerts[0]));
            Vector4f v1Clip = mvpMatrix.transform(new Vector4f(localVerts[1]));
            Vector4f v2Clip = mvpMatrix.transform(new Vector4f(localVerts[2]));

            if (v0Clip.w() <= 0 || v1Clip.w() <= 0 || v2Clip.w() <= 0) {
                shouldRender = false;
            } else {
                v0Clip.div(v0Clip.w());
                v1Clip.div(v1Clip.w());
                v2Clip.div(v2Clip.w());

                float ab_x = v1Clip.x() - v0Clip.x();
                float ab_y = v1Clip.y() - v0Clip.y();
                float ac_x = v2Clip.x() - v0Clip.x();
                float ac_y = v2Clip.y() - v0Clip.y();
                float sign = ab_x * ac_y - ac_x * ab_y;

                //bandaid fix for some culling issues
                Vector4f bonePosInViewSpace = modelViewMatrix.transform(new Vector4f(0, 0, 0, 1));
                float distance = bonePosInViewSpace.length();
                float dynamicTolerance = (float) (1 / Math.pow(distance + .5, 4));

                boolean isBackFace = sign < dynamicTolerance;
                shouldRender = isBackFace ^ isReflected;
            }
        }

        if (shouldRender) {
            super.renderRecursively(poseStack, animatable, bone, renderType, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
        }
    }
}

