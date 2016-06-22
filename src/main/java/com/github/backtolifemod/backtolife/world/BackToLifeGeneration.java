package com.github.backtolifemod.backtolife.world;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.fml.common.IWorldGenerator;

import com.github.backtolifemod.backtolife.entity.EntityFossil;
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
					if (BiomeDictionary.isBiomeOfType(world.getBiomeGenForCoords(new BlockPos(x, 0, z)), Type.DRY) && BiomeDictionary.isBiomeOfType(world.getBiomeGenForCoords(new BlockPos(x, 0, z)), Type.SANDY)) {
						if (time.ordinal() >= EnumPrehistoricTimeType.CRETACEOUS.ordinal()) {
							this.placeFossilAtCoord(world, world.getHeight(new BlockPos(x, 0, z)), type);
						}
					} else {
						this.placeFossilAtCoord(world, new BlockPos(x, time.lowerDepth + random.nextInt(time.upperDepth - time.lowerDepth), z), type);
					}
				}
			}
		}
	}

	private void placeFossilAtCoord(World world, BlockPos position, EnumPrehistoricType type) {
		Block block = world.getBlockState(position).getBlock();
		int meta = block.getMetaFromState(world.getBlockState(position));
		EntityFossil fossil = new EntityFossil(world);
		fossil.setPosition(position.getX(), position.getY() + 1, position.getZ());
		fossil.type = type;
		fossil.blockID = Block.getIdFromBlock(Blocks.STONE);
		for (int i = 0; i < EntityFossil.BLOCKS.length; i++) {
			if (block == EntityFossil.BLOCKS[i] && meta == EntityFossil.METAS[i]) {
				fossil.blockID = Block.getIdFromBlock(block);
				fossil.blockMeta = EntityFossil.METAS[i];
			}
		}
		if (!world.isRemote) {
			world.spawnEntityInWorld(fossil);
		}
	}
}
