package com.github.backtolifemod.backtolife;

import com.github.backtolifemod.backtolife.event.CommonEvents;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;

public class CommonProxy {

	public void preInit() {}

	public void init() {
		MinecraftForge.EVENT_BUS.register(new CommonEvents());

	}

	public void postInit() {}

	public void addItemRender(Item item, String name) {}

}
