package com.naterbobber.darkerdepths.client.render;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.naterbobber.darkerdepths.DarkerDepths;
import net.minecraft.client.renderer.ShaderInstance;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.event.RegisterShadersEvent;

import java.io.IOException;
import java.util.function.Consumer;

@OnlyIn(Dist.CLIENT)
public class DDShaders {
    private static ShaderInstance renderTypeGlowThroughFog;

    public static void registerShaders(RegisterShadersEvent event) {
        register(event, "rendertype_glow_through_fog", DefaultVertexFormat.NEW_ENTITY,
                shader -> renderTypeGlowThroughFog = shader);
    }

    public static ShaderInstance getRenderTypeGlowThroughFog() {
        return renderTypeGlowThroughFog;
    }

    private static void register(
            RegisterShadersEvent event,
            String name,
            VertexFormat vertexFormat,
            Consumer<ShaderInstance> instanceConsumer
    ) {
        var resourceProvider = event.getResourceProvider();
        try {
            event.registerShader(
                    new ShaderInstance(resourceProvider, DarkerDepths.id(name), vertexFormat), instanceConsumer
            );

        } catch (IOException e){
            DarkerDepths.LOGGER.error(e.getMessage());
        }
    }
}
