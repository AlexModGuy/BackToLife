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
import com.github.backtolifemod.backtolife.enums.EnumFossil;

public class ItemFossil extends Item {

	private int fossil;

	public ItemFossil(EnumFossil fossil){
		this.maxStackSize = 1;
		this.fossil = fossil.ordinal();
		this.setUnlocalizedName("backtolife.unknown_fossil");
		for(int meta = 0; meta < fossil.fossilItems; meta++){
			//BackToLife.PROXY.addItemRenderWithMeta(this, meta, "unknown_fossil_" + fossil.name().toLowerCase() + "_" + meta);
		}
		this.setCreativeTab(BackToLife.tab);
		GameRegistry.registerItem(this, "unknown_fossil_" + fossil.name().toLowerCase());
	}

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced){
    	tooltip.add(I18n.translateToLocal("item.backtolife.unknown_fossil_" + EnumFossil.values()[fossil].name().toLowerCase() + ".desc"));
    }
    
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems){
		for(int meta = 0; meta < EnumFossil.values()[fossil].fossilItems; meta++){
			subItems.add(new ItemStack(itemIn, 1, meta));
		}
	}
}
