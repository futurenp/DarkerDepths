package com.naterbobber.darkerdepths.client.render.renderers;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.client.models.DDDefaultBlockItemModel;
import com.naterbobber.darkerdepths.common.block.blockentities.ParanoiaAltarBlockEntity;
import com.naterbobber.darkerdepths.client.render.DDRenderTypes;
import com.naterbobber.darkerdepths.client.render.renderers.layers.DDRenderLayer;
import com.naterbobber.darkerdepths.common.init.DDBlockEntityTypes;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

@OnlyIn(Dist.CLIENT)
public class ParanoiaAltarBlockEntityRenderer extends GeoBlockRenderer<ParanoiaAltarBlockEntity> {
    private static final ResourceLocation glowTexture = DarkerDepths.id("textures/entity/paranoia_altar/paranoia_altar_locked_glow.png");
    public ParanoiaAltarBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        super(new DDDefaultBlockItemModel<>(DDBlockEntityTypes.PARANOIA_ALTAR.getId().getPath()));
        addRenderLayer(DDRenderLayer.withType(this, DDRenderTypes.emissiveTransparentFogOverride(glowTexture))
                .setRenderPredicate((animatable) -> animatable.getBlockState().getValue(BlockStateProperties.LOCKED)));
    }
}