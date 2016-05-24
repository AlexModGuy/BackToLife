package com.github.backtolifemod.backtolife.enums;

import net.minecraft.item.Item;

public enum EnumFossil {
	HERBIVORE_DINOSAUR(3), CARNIVORE_DINOSAUR(3), PTEROSAUR(3);
	public Item fossil;
	public int fossilItems;
	private EnumFossil(int fossilItems){
		this.fossilItems = fossilItems;
	}
}
