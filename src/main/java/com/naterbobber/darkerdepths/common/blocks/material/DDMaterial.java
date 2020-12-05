package com.naterbobber.darkerdepths.common.blocks.material;

import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;

//<>

public class DDMaterial {
	public static final Material AMBER = (new Material.Builder(MaterialColor.GOLD)).notSolid().pushDestroys().build();
}