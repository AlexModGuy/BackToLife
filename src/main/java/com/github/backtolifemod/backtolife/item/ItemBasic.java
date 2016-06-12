package com.github.backtolifemod.backtolife.item;

import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;
import com.github.backtolifemod.backtolife.BackToLife;

public class ItemBasic extends Item {

	public ItemBasic(String name) {
		this.setUnlocalizedName("backtolife." + name);
		this.setCreativeTab(BackToLife.tab);
		GameRegistry.registerItem(this, name);
		BackToLife.PROXY.addItemRender(this, name);
	}
}
