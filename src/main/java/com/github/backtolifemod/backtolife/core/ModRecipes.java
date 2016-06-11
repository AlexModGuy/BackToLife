package com.github.backtolifemod.backtolife.core;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class ModRecipes {

	public static void init() {
		OreDictionary.registerOre("gearIron", new ItemStack(ModItems.gear));
		GameRegistry.addRecipe(new ItemStack(Blocks.SAND), new Object[]{"XX", "XX", 'X', ModItems.dust});
		GameRegistry.addRecipe(new ItemStack(Blocks.GRAVEL), new Object[]{"XX", "XX", 'X', ModItems.rocks});
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.laboratory_block, 16), new Object[]{" X ", "XYX", " X ", 'X', "ingotIron", 'Y', "cobblestone"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.laboratory_light_panel, 16), new Object[]{" X ", "XYX", " X ", 'X', "ingotIron", 'Y', Blocks.GLOWSTONE}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gear), new Object[]{" X ", "XYX", " X ", 'X', Blocks.IRON_BARS, 'Y', "ingotIron"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.fossil_slicer), new Object[]{"XXX", "YZY", "YYY", 'X', "paneGlass", 'Y', ModBlocks.laboratory_block, 'Z', "gearIron"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.tissue_analyzer), new Object[]{"VXV", "XZX", "XYX", 'X', ModBlocks.laboratory_block, 'Y', Items.WATER_BUCKET.setContainerItem(null), 'Z', "gearIron", 'V', ModBlocks.laboratory_light_panel}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.fertilization_machine), new Object[]{"VYV", "XZX", "XXX", 'X', ModBlocks.laboratory_block, 'Y', "gearIron", 'Z', Blocks.STONE_PRESSURE_PLATE, 'V', ModBlocks.laboratory_light_panel}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.magnifying_glass), new Object[]{" X ", "XYX", " XZ", 'X', "ingotIron", 'Y', "paneGlass", 'Z', "stickWood"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.magnifying_glass), new Object[]{" X ", "XYX", "ZX ", 'X', "ingotIron", 'Y', "paneGlass", 'Z', "stickWood"}));

	}
}
