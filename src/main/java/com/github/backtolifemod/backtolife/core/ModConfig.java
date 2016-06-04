package com.github.backtolifemod.backtolife.core;

import net.minecraftforge.common.config.Configuration;

public class ModConfig {

	public static int VILLAGER_ID;

	public static void load(Configuration config) {
		VILLAGER_ID = config.get("ID's", "Scientist Villager ID", 18).getInt();
	}

}
