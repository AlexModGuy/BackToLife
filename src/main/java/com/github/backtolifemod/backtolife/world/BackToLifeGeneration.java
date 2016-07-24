package com.github.backtolifemod.backtolife.world;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.fml.common.IWorldGenerator;

import com.github.backtolifemod.backtolife.enums.EnumPrehistoricType;
import com.github.backtolifemod.backtolife.enums.EnumPrehistoricType.EnumPrehistoricTimeType;

public class BackToLifeGeneration implements IWorldGenerator {

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
		int x = (chunkX * 16) + random.nextInt(16);
		int z = (chunkZ * 16) + random.nextInt(16);
		if (random.nextInt(4) == 0) {
			for (EnumPrehistoricTimeType time : EnumPrehistoricTimeType.values()) {
				EnumPrehistoricType type = EnumPrehistoricType.getOneOfPeriodType(time);
				if (type != null) {
				}
			}
		}
	}
}
