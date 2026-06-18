package com.naterbobber.darkerdepths.client.render.renderers;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.block.blockentities.ParanoiaAltarBlockEntity;
import com.naterbobber.darkerdepths.client.models.ParanoiaAltarModel;
import com.naterbobber.darkerdepths.client.render.DDRenderTypes;
import com.naterbobber.darkerdepths.client.render.renderers.layers.DDRenderLayer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

@OnlyIn(Dist.CLIENT)
public class ParanoiaAltarBlockEntityRenderer extends GeoBlockRenderer<ParanoiaAltarBlockEntity> {
    private static final ResourceLocation glowTexture = DarkerDepths.id("textures/entity/paranoia_altar/paranoia_altar_locked_glow.png");
    public ParanoiaAltarBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        super(new ParanoiaAltarModel());
        addRenderLayer(DDRenderLayer.withType(this, DDRenderTypes.emissiveTransparentFogOverride(glowTexture))
                .setRenderPredicate((animatable) -> animatable.getBlockState().getValue(BlockStateProperties.LOCKED)));
    }
}