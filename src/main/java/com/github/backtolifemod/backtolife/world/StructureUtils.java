package com.github.backtolifemod.backtolife.world;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;

public class StructureUtils {

	public static Random rand = new Random();
	public static final ResourceLocation SCIENTIST_CHEST = LootTableList.register(new ResourceLocation("backtolife", "village_scientist"));

	public static void setBlock(World world, int x, int y, int z, Block block, int meta, int flags) {
		BlockPos pos = new BlockPos(x, y, z);
		world.setBlockState(pos, block.getStateFromMeta(meta), flags);
		if (block instanceof BlockChest) {
			TileEntity tileentity1 = world.getTileEntity(pos);
			if (tileentity1 instanceof TileEntityChest) {
				((TileEntityChest) tileentity1).setLootTable(SCIENTIST_CHEST, rand.nextLong());
			}
		}
	}

	public static Block getBlock(World world, int x, int y, int z) {
		BlockPos pos = new BlockPos(x, y, z);
		return world.getBlockState(pos).getBlock();
	}

	public static int getBlockMeta(World world, int x, int y, int z) {
		BlockPos pos = new BlockPos(x, y, z);
		return world.getBlockState(pos).getBlock().getMetaFromState(world.getBlockState(pos));
	}

	public static void setBlockDirectional(World world, int x, int y, int z, Block block, int meta, int flags, EnumFacing direction) {
		int newX = x;
		int newY = y;
		int newZ = z;
		switch (direction) {
			case SOUTH :
				newX = -x;
				newZ = -z;
				break;
			case WEST :
				newX = z;
				newZ = x;
				break;
			case EAST :
				newX = z;
				newZ = -x;
				break;
			default :
				break;
		}

		setBlock(world, newX, newY, newZ, block, meta, flags);
		BlockPos pos = new BlockPos(newX, newY, newZ);
		IBlockState state = world.getBlockState(pos);

		if (block instanceof BlockRotatedPillar) {
			if (state.getValue(BlockRotatedPillar.AXIS) == EnumFacing.Axis.X) {
				((BlockRotatedPillar) block).rotateBlock(world, pos, direction);
			}
		} else {
			block.withRotation(state, rotate(direction));
		}
	}

	public static Rotation rotate(EnumFacing direction) {
		switch (direction) {
			case SOUTH :
				return Rotation.CLOCKWISE_180;
			case WEST :
				return Rotation.COUNTERCLOCKWISE_90;
			case EAST :
				return Rotation.CLOCKWISE_90;
			default :
				return Rotation.NONE;
		}
	}
}
