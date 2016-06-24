package com.github.backtolifemod.backtolife.item;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.github.backtolifemod.backtolife.BackToLife;
import com.github.backtolifemod.backtolife.entity.tile.TileEntityDummySkull;
import com.github.backtolifemod.backtolife.enums.EnumPrehistoricType;

public class ItemFossilPart extends Item {

	public ItemFossilPart(String bodyPart) {
		this.setHasSubtypes(true);
		this.setUnlocalizedName("backtolife." + bodyPart);
		this.setCreativeTab(BackToLife.tab);
		GameRegistry.registerItem(this, bodyPart);
	}

	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
		String fossil = EnumPrehistoricType.values()[stack.getItemDamage()].name().toLowerCase();
		tooltip.add(I18n.translateToLocal("entity.backtolife." + fossil + ".name"));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems) {
		for (EnumPrehistoricType prehistoric : EnumPrehistoricType.values()) {
			subItems.add(new ItemStack(itemIn, 1, prehistoric.ordinal()));
		}
	}
}
