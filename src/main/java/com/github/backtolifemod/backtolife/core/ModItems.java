package com.github.backtolifemod.backtolife.core;

import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

import com.github.backtolifemod.backtolife.entity.tile.TileEntityDummySkull;
import com.github.backtolifemod.backtolife.item.ItemBasic;
import com.github.backtolifemod.backtolife.item.ItemFossilPart;
import com.github.backtolifemod.backtolife.item.ItemPrehistoricEgg;
import com.github.backtolifemod.backtolife.item.ItemSoftTissue;

public class ModItems {

	public static Item fossil_skull;
	public static Item fossil_ribcage;
	public static Item fossil_foot;
	public static Item fossil_limb;
	public static Item fossil_tail;
	public static Item gear;
	public static Item dust;
	public static Item rocks;
	public static Item soft_tissue;
	public static Item fossil_cells;
	public static Item prehistoric_egg;
	public static Item magnifying_glass;

	public static void init() {
		GameRegistry.registerTileEntity(TileEntityDummySkull.class, "fossil_skull_item_dummy");
		fossil_skull = new ItemFossilPart("fossil_skull");
		fossil_ribcage = new ItemFossilPart("fossil_ribcage");
		fossil_foot = new ItemFossilPart("fossil_foot");
		fossil_limb = new ItemFossilPart("fossil_limb");
		fossil_tail = new ItemFossilPart("fossil_tail");
		gear = new ItemBasic("gear");
		dust = new ItemBasic("dust");
		rocks = new ItemBasic("rocks");
		soft_tissue = new ItemSoftTissue(false);
		fossil_cells = new ItemSoftTissue(true);
		prehistoric_egg = new ItemPrehistoricEgg();
		magnifying_glass = new ItemBasic("magnifying_glass");
	}
}
