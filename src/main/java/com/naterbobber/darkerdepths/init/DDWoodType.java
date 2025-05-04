package com.naterbobber.darkerdepths.init;

import com.naterbobber.darkerdepths.DarkerDepths;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class DDWoodType {

    public static final WoodType PETRIFIED = WoodType.register(new WoodType(DarkerDepths.MODID + ":petrified", DDBlockSetTypes.PETRIFIED));

    @OnlyIn(Dist.CLIENT)
    public static void setupWoodTypes() {
        Sheets.addWoodType(PETRIFIED);
    }

}
