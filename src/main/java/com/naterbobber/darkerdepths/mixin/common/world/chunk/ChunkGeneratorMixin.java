package com.naterbobber.darkerdepths.mixin.common.world.chunk;

import net.minecraft.crash.CrashReport;
import net.minecraft.crash.ReportedException;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.WorldGenRegion;
import net.minecraft.world.gen.feature.structure.StructureManager;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

//<>

/**
 * @author CorgiTaco
 */
@Mixin(ChunkGenerator.class)
public class ChunkGeneratorMixin {
    @Shadow @Final protected BiomeProvider biomeProvider;

    @Inject(at = @At("HEAD"), method = "func_230351_a_(Lnet/minecraft/world/gen/WorldGenRegion;Lnet/minecraft/world/gen/feature/structure/StructureManager;)V", cancellable = true)
    private void biomeDecorate(WorldGenRegion region, StructureManager manager, CallbackInfo ci) {
        int chunkX = region.getMainChunkX();
        int chunkZ = region.getMainChunkZ();
        int x = chunkX * 16;
        int z = chunkZ * 16;
        BlockPos blockpos = new BlockPos(x, 0, z);
        Biome surfaceBiome = this.biomeProvider.getNoiseBiome((chunkX << 2) + 2, 16, (chunkZ << 2) + 2);
        Biome caveBiome = this.biomeProvider.getNoiseBiome((chunkX << 2) + 2, 2, (chunkZ << 2) + 2);
        SharedSeedRandom sharedseedrandom = new SharedSeedRandom();
        long seed = sharedseedrandom.setDecorationSeed(region.getSeed(), x, z);

        if (caveBiome == surfaceBiome) {
            return;
        }

        for (GenerationStage.Decoration decoration : GenerationStage.Decoration.values()) {
            try {
                surfaceBiome.func_235061_a_(decoration, manager, (ChunkGenerator)(Object)this, region, seed, sharedseedrandom, blockpos);
            } catch (Exception exception) {
                CrashReport crashreport = CrashReport.makeCrashReport(exception, "Surface Biome decoration");
                crashreport.makeCategory("Generation").addDetail("CenterX", chunkX).addDetail("CenterZ", chunkZ).addDetail("Seed", seed).addDetail("Biome", surfaceBiome);
                throw new ReportedException(crashreport);
            }
        }
    }

    @Redirect(method = "func_230350_a_", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/biome/provider/BiomeProvider;getNoiseBiome(III)Lnet/minecraft/world/biome/Biome;"))
    private Biome generationSettings(BiomeProvider biomeProvider, int x, int y, int z) {
        return this.biomeProvider.getNoiseBiome(x, 16, z);
    }
}
