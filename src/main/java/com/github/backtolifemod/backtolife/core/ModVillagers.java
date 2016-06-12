package com.github.backtolifemod.backtolife.core;

import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraftforge.fml.common.registry.FMLControlledNamespacedRegistry;
import net.minecraftforge.fml.common.registry.VillagerRegistry;
import net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerCareer;
import net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerProfession;

import com.github.backtolifemod.backtolife.world.ComponentScientistHouse;
import com.github.backtolifemod.backtolife.world.VillageHandler;

public class ModVillagers {

	private static VillagerRegistry registry = VillagerRegistry.instance();
	public static VillagerProfession proffesion;

	public static void init() {

		proffesion = new VillagerProfession("backtolife:scientist", "backtolife:textures/models/scientist.png");
		{
			((FMLControlledNamespacedRegistry) registry.getRegistry()).register(ModConfig.VILLAGER_ID, new ResourceLocation("backtolife:scientist"), proffesion);
			VillagerCareer career = new VillagerCareer(proffesion, "scientist");
			career.addTrade(1, new EntityVillager.ListItemForEmeralds(Item.getItemFromBlock(ModBlocks.laboratory_block), new EntityVillager.PriceInfo(32, 16)));
			career.addTrade(1, new EntityVillager.ListItemForEmeralds(Item.getItemFromBlock(ModBlocks.laboratory_light_panel), new EntityVillager.PriceInfo(24, 8)));
			career.addTrade(1, new EntityVillager.EmeraldForItems(Items.IRON_INGOT, new EntityVillager.PriceInfo(8, 10)));
			career.addTrade(1, new EntityVillager.EmeraldForItems(ModItems.dust, new EntityVillager.PriceInfo(20, 30)));
			career.addTrade(1, new EntityVillager.EmeraldForItems(ModItems.rocks, new EntityVillager.PriceInfo(20, 30)));
			career.addTrade(1, new EntityVillager.EmeraldForItems(Items.GLOWSTONE_DUST, new EntityVillager.PriceInfo(4, 6)));
			career.addTrade(2, new EntityVillager.EmeraldForItems(ModItems.unknown_fossil_carnivore_dinosaur, new EntityVillager.PriceInfo(3, 5)));
			career.addTrade(2, new EntityVillager.EmeraldForItems(ModItems.unknown_fossil_herbivore_dinosaur, new EntityVillager.PriceInfo(3, 5)));
			career.addTrade(2, new EntityVillager.EmeraldForItems(ModItems.unknown_fossil_pterosaur, new EntityVillager.PriceInfo(3, 5)));
			career.addTrade(3, new EntityVillager.ListItemForEmeralds(ModItems.unknown_fossil_carnivore_dinosaur, new EntityVillager.PriceInfo(3, 5)));
			career.addTrade(3, new EntityVillager.ListItemForEmeralds(ModItems.unknown_fossil_herbivore_dinosaur, new EntityVillager.PriceInfo(3, 5)));
			career.addTrade(3, new EntityVillager.ListItemForEmeralds(ModItems.unknown_fossil_pterosaur, new EntityVillager.PriceInfo(3, 5)));
			career.addTrade(4, new EntityVillager.ListItemForEmeralds(Item.getItemFromBlock(ModBlocks.fossil_slicer), new EntityVillager.PriceInfo(5, 6)));
			career.addTrade(4, new EntityVillager.ListItemForEmeralds(Item.getItemFromBlock(ModBlocks.tissue_analyzer), new EntityVillager.PriceInfo(5, 6)));
		}
		try {
			MapGenStructureIO.registerStructureComponent(ComponentScientistHouse.class, "ScientistHouse");
		} catch (Exception e) {
		}
		VillagerRegistry.instance().registerVillageCreationHandler(new VillageHandler());
	}
}
