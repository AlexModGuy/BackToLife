package com.github.backtolifemod.backtolife.core;

import net.minecraft.item.Item;

import com.github.backtolifemod.backtolife.item.ItemBasic;
import com.github.backtolifemod.backtolife.item.ItemFossil;
import com.github.backtolifemod.backtolife.item.ItemPrehistoricEgg;
import com.github.backtolifemod.backtolife.item.ItemSoftTissue;

public class ModItems {

	public static Item unknown_fossil_carnivore_dinosaur;
	public static Item unknown_fossil_herbivore_dinosaur;
	public static Item unknown_fossil_pterosaur;
	public static Item gear;
	public static Item dust;
	public static Item rocks;
	public static Item soft_tissue;
	public static Item fossil_cells;
	public static Item prehistoric_egg;

	public static void init() {
		unknown_fossil_carnivore_dinosaur = new ItemFossil("carnivore_dinosaur");
		unknown_fossil_herbivore_dinosaur = new ItemFossil("herbivore_dinosaur");
		unknown_fossil_pterosaur = new ItemFossil("pterosaur");
		gear = new ItemBasic("gear");
		dust = new ItemBasic("dust");
		rocks = new ItemBasic("rocks");
		soft_tissue = new ItemSoftTissue(false);
		fossil_cells = new ItemSoftTissue(true);
		prehistoric_egg = new ItemPrehistoricEgg();

	}
}
