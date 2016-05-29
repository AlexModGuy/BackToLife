package com.github.backtolifemod.backtolife.core;

import com.github.backtolifemod.backtolife.block.BlockFossilSlicer;

import net.minecraft.block.Block;

public class ModBlocks {
	
	public static Block fossil_slicer;
	
	public static void init(){
		fossil_slicer = new BlockFossilSlicer();
	}
}
