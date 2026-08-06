package com.naterbobber.darkerdepths.common.util;

import com.naterbobber.darkerdepths.DarkerDepths;
import net.minecraft.client.gui.Gui;
import net.neoforged.fml.common.asm.enumextension.EnumProxy;

public class DDEnumProxies {
    public static final EnumProxy<Gui.HeartType> GLOWING_MYCOSES_PROXY = new EnumProxy<>(
            Gui.HeartType.class,
            DarkerDepths.id("hud/heart/glowing_mycoses/full"),
            DarkerDepths.id("hud/heart/glowing_mycoses/full_blinking"),
            DarkerDepths.id("hud/heart/glowing_mycoses/half"),
            DarkerDepths.id("hud/heart/glowing_mycoses/half_blinking"),
            DarkerDepths.id("hud/heart/glowing_mycoses/hardcore_full"),
            DarkerDepths.id("hud/heart/glowing_mycoses/hardcore_full_blinking"),
            DarkerDepths.id("hud/heart/glowing_mycoses/hardcore_half"),
            DarkerDepths.id("hud/heart/glowing_mycoses/hardcore_half_blinking")
    );
}

