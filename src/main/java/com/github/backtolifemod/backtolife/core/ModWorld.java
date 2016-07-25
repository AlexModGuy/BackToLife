package com.github.backtolifemod.backtolife.core;

import com.github.backtolifemod.backtolife.world.biome.BiomeFlamingCliffsFossilSite;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.fml.common.registry.FMLControlledNamespacedRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.VillagerRegistry;
import net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerCareer;
import net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerProfession;

import com.github.backtolifemod.backtolife.world.ComponentScientistHouse;
import com.github.backtolifemod.backtolife.world.VillageHandler;

public class ModWorld {

	private static VillagerRegistry REGISTRY = VillagerRegistry.instance();
	public static VillagerProfession SCIENTIST;
	public static Biome FLAMING_CLIFFS_FOSSIL;

	public static void init() {

		SCIENTIST = new VillagerProfession("backtolife:scientist", "backtolife:textures/models/scientist.png");
		{
			((FMLControlledNamespacedRegistry) REGISTRY.getRegistry()).register(ModConfig.VILLAGER_ID, new ResourceLocation("backtolife:scientist"), SCIENTIST);
			VillagerCareer career = new VillagerCareer(SCIENTIST, "scientist");
			career.addTrade(1, new EntityVillager.ListItemForEmeralds(Item.getItemFromBlock(ModBlocks.laboratory_block), new EntityVillager.PriceInfo(32, 16)));
			career.addTrade(1, new EntityVillager.ListItemForEmeralds(Item.getItemFromBlock(ModBlocks.laboratory_light_panel), new EntityVillager.PriceInfo(24, 8)));
			career.addTrade(1, new EntityVillager.EmeraldForItems(Items.IRON_INGOT, new EntityVillager.PriceInfo(8, 10)));
			career.addTrade(1, new EntityVillager.EmeraldForItems(ModItems.dust, new EntityVillager.PriceInfo(20, 30)));
			career.addTrade(1, new EntityVillager.EmeraldForItems(ModItems.rocks, new EntityVillager.PriceInfo(20, 30)));
			career.addTrade(1, new EntityVillager.EmeraldForItems(Items.GLOWSTONE_DUST, new EntityVillager.PriceInfo(4, 6)));
			//career.addTrade(2, new EntityVillager.EmeraldForItems(ModItems.fossil_part, new EntityVillager.PriceInfo(3, 5)));
			//career.addTrade(3, new EntityVillager.ListItemForEmeralds(ModItems.fossil_part, new EntityVillager.PriceInfo(5, 3)));
			career.addTrade(4, new EntityVillager.ListItemForEmeralds(Item.getItemFromBlock(ModBlocks.fossil_slicer), new EntityVillager.PriceInfo(5, 6)));
			career.addTrade(4, new EntityVillager.ListItemForEmeralds(Item.getItemFromBlock(ModBlocks.tissue_analyzer), new EntityVillager.PriceInfo(5, 6)));
		}
		try {
			MapGenStructureIO.registerStructureComponent(ComponentScientistHouse.class, "ScientistHouse");
		} catch (Exception e) {
		}
		VillagerRegistry.instance().registerVillageCreationHandler(new VillageHandler());

		FLAMING_CLIFFS_FOSSIL = new BiomeFlamingCliffsFossilSite().setRegistryName("backtolife", "Flaming Cliffs Fossil Site");
		GameRegistry.register(FLAMING_CLIFFS_FOSSIL);
		BiomeDictionary.registerBiomeType(FLAMING_CLIFFS_FOSSIL, BiomeDictionary.Type.MESA, BiomeDictionary.Type.DRY, BiomeDictionary.Type.SANDY);
		BiomeManager.addSpawnBiome(FLAMING_CLIFFS_FOSSIL);
		BiomeManager.addBiome(BiomeManager.BiomeType.COOL, new BiomeManager.BiomeEntry(FLAMING_CLIFFS_FOSSIL, 100));
	}
}
