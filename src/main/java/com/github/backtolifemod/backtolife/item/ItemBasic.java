package com.github.backtolifemod.backtolife.item;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.github.backtolifemod.backtolife.BackToLife;

public class ItemBasic extends Item {

	public ItemBasic(String name){
		this.setUnlocalizedName("backtolife." + name);
		this.setCreativeTab(BackToLife.tab);
		GameRegistry.registerItem(this, "unknown_fossil_" + name);
		BackToLife.PROXY.addItemRender(this, name);
	}
}
