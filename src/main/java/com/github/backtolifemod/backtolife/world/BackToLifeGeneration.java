package com.github.backtolifemod.backtolife.world;

import java.util.Random;

import com.github.backtolifemod.backtolife.core.ModBlocks;
import net.minecraft.block.state.pattern.BlockMatcher;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.fml.common.IWorldGenerator;

public class BackToLifeGeneration implements IWorldGenerator {

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
		for (int i = 0; i < 200; i++) {
			int oreHeight = 73 + random.nextInt(183);
			int xOre = (chunkX * 16) + random.nextInt(16);
			int zOre = (chunkZ * 16) + random.nextInt(16);
			new WorldGenMinable(ModBlocks.flaming_cliffs_animal_fossil.getDefaultState(), 4 + random.nextInt(4), BlockMatcher.forBlock(Blocks.HARDENED_CLAY)).generate(world, random, new BlockPos(xOre, oreHeight, zOre));
		}
		for (int i = 0; i < 200; i++) {
			int oreHeight = 73 + random.nextInt(183);
			int xOre = (chunkX * 16) + random.nextInt(16);
			int zOre = (chunkZ * 16) + random.nextInt(16);
			new WorldGenMinable(ModBlocks.flaming_cliffs_plant_fossil.getDefaultState(), 4 + random.nextInt(4), BlockMatcher.forBlock(Blocks.HARDENED_CLAY)).generate(world, random, new BlockPos(xOre, oreHeight, zOre));
		}
	}
}
