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

public class ItemFossil extends Item {

	private String fossil;

	public ItemFossil(String name){
		this.maxStackSize = 1;
		this.fossil = name;
		this.setUnlocalizedName("backtolife.unknown_fossil");
		this.setCreativeTab(BackToLife.tab);
		GameRegistry.registerItem(this, "unknown_fossil_" + name);
	}

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced){
    	tooltip.add(I18n.translateToLocal("item.backtolife.unknown_fossil_" + fossil + ".desc"));
    }
    
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems){
			subItems.add(new ItemStack(itemIn, 1, 0));
			subItems.add(new ItemStack(itemIn, 1, 1));
			subItems.add(new ItemStack(itemIn, 1, 2));
	}
}
