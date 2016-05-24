package com.github.backtolifemod.backtolife.core;

import com.github.backtolifemod.backtolife.enums.EnumFossil;
import com.github.backtolifemod.backtolife.item.ItemFossil;

public class ModItems {

	public static void init(){
		for(EnumFossil fossil : EnumFossil.values()){
			fossil.fossil = new ItemFossil(fossil);
		}
	}
}
