package com.github.backtolifemod.backtolife.core;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

public class ModRecipes {
	
	public static void init(){
		OreDictionary.registerOre("gearIron", new ItemStack(ModItems.gear));
        GameRegistry.addRecipe(new ItemStack(Blocks.SAND), new Object[]{"XX", "XX", 'X', ModItems.dust});
        GameRegistry.addRecipe(new ItemStack(Blocks.GRAVEL), new Object[]{"XX", "XX", 'X', ModItems.rocks});
        GameRegistry.addRecipe(new ItemStack(ModItems.gear), new Object[]{" X ", "XYX", " X ", 'X', Blocks.IRON_BARS, 'Y', Items.IRON_INGOT});

	}
}
