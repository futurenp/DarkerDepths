package com.naterbobber.darkerdepths.client.render;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.client.model.BakedModelWrapper;
import net.neoforged.neoforge.client.model.data.ModelData;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DirectionalOffsetBakedModel extends BakedModelWrapper<BakedModel> {
    private final Settings settings;

    public DirectionalOffsetBakedModel(BakedModel originalModel, Settings settings) {
        super(originalModel);
        this.settings = settings;
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, RandomSource rand, ModelData extraData, @Nullable RenderType renderType) {
        var originalQuads = super.getQuads(state, side, rand, extraData, renderType);

        if (state == null || !state.hasProperty(BlockStateProperties.HORIZONTAL_FACING)) {
            return originalQuads;
        }

        var axis = state.getValue(BlockStateProperties.HORIZONTAL_FACING).getAxis();

        float shiftXZ = getRandomShift(rand, settings.maxShiftXZ);

        float shiftY = 0;
        float shiftX = 0;
        float shiftZ = 0;

        if(settings.offsetType.isHorizontal()) {
            if (axis == Direction.Axis.Z) {
                shiftX = shiftXZ;
            }
            else if (axis == Direction.Axis.X) {
                shiftZ = shiftXZ;
            }
        }

        if(settings.offsetType.isVertical()) {
            shiftY = getRandomShift(rand, settings.maxShiftY);
        }

        var shiftedQuads = new ArrayList<BakedQuad>(originalQuads.size());
        for (var quad : originalQuads) {
            shiftedQuads.add(translateQuad(quad, shiftX, shiftY, shiftZ));
        }

        return shiftedQuads;
    }

    private static float getRandomShift(RandomSource random, float maxShift) {
        return (random.nextFloat() * (maxShift * 2)) - maxShift;
    }

    private BakedQuad translateQuad(BakedQuad quad, float offsetX, float offsetY, float offsetZ) {
        int[] oldVerts = quad.getVertices();
        int[] newVerts = new int[oldVerts.length];

        System.arraycopy(oldVerts, 0, newVerts, 0, oldVerts.length);

        int vertexSize = oldVerts.length / 4;

        for (int i = 0; i < 4; i++) {
            int offset = i * vertexSize;

            float x = Float.intBitsToFloat(newVerts[offset]);
            float y = Float.intBitsToFloat(newVerts[offset + 1]);
            float z = Float.intBitsToFloat(newVerts[offset + 2]);

            x += offsetX;
            y += offsetY;
            z += offsetZ;

            newVerts[offset] = Float.floatToRawIntBits(x);
            newVerts[offset + 1] = Float.floatToRawIntBits(y);
            newVerts[offset + 2] = Float.floatToRawIntBits(z);
        }

        return new BakedQuad(newVerts, quad.getTintIndex(), quad.getDirection(), quad.getSprite(), quad.isShade(), quad.hasAmbientOcclusion());
    }

    public static class Settings implements BakedModelSettings {
        private float maxShiftXZ = 0.25F;
        private float maxShiftY = 0.25F;
        private OffsetType offsetType;

        protected Settings(OffsetType offsetType) {
            this.offsetType = offsetType;
        }

        public static Settings of(OffsetType offsetType) {
            return new Settings(offsetType);
        }

        public Settings maxShiftXZ(float maxShiftXZ) {
            this.maxShiftXZ = maxShiftXZ;
            return this;
        }

        public Settings maxShiftY(float maxShiftY) {
            this.maxShiftY = maxShiftY;
            return this;
        }

        @Override
        public DirectionalOffsetBakedModel model(BakedModel originalModel) {
            return new DirectionalOffsetBakedModel(originalModel, this);
        }
    }

    public enum OffsetType {
        XZ,
        XYZ,
        Y;

        public boolean isHorizontal() {
            return this == XZ || this == XYZ;
        }

        public boolean isVertical() {
            return this == Y || this == XYZ;
        }
    }
}