package com.naterbobber.darkerdepths.init;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.advancements.criteria.DDTrigger;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DarkerDepths.MOD_ID)
public class DDCriteria {
    public static final DDTrigger GEYSER_ELYTRA_BOOST = CriteriaTriggers.register(new DDTrigger("geyser_elytra_boost"));
    public static final DDTrigger GEYSER_BOOST = CriteriaTriggers.register(new DDTrigger("geyser_boost"));
    public static final DDTrigger USED_CRYSTAL_MELON = CriteriaTriggers.register(new DDTrigger("used_crystal_melon"));
}