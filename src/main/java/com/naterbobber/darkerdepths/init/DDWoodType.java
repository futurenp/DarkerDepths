package com.naterbobber.darkerdepths.init;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.block.DDBlockSetTypes;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class DDWoodType {

    public static final WoodType PETRIFIED = WoodType.register(new WoodType(DarkerDepths.MOD_ID + ":petrified", DDBlockSetTypes.PETRIFIED));

    @OnlyIn(Dist.CLIENT)
    public static void setupWoodTypes() {
        Sheets.addWoodType(PETRIFIED);
    }

}
