package com.github.backtolifemod.backtolife;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;

import com.github.backtolifemod.backtolife.client.render.tile.RenderFossilSlicer;
import com.github.backtolifemod.backtolife.client.render.tile.RenderTissueAnalyzer;
import com.github.backtolifemod.backtolife.core.ModItems;
import com.github.backtolifemod.backtolife.entity.tile.TileEntityFossilSlicer;
import com.github.backtolifemod.backtolife.entity.tile.TileEntityTissueAnalyzer;
import com.github.backtolifemod.backtolife.enums.EnumPrehistoricType;

public class ClientProxy extends CommonProxy{

	public void preInit(){
		ModelLoader.setCustomModelResourceLocation(ModItems.unknown_fossil_carnivore_dinosaur, 0, new ModelResourceLocation("backtolife:unknown_fossil_carnivore_dinosaur_0"));
		ModelLoader.setCustomModelResourceLocation(ModItems.unknown_fossil_carnivore_dinosaur, 1, new ModelResourceLocation("backtolife:unknown_fossil_carnivore_dinosaur_1"));
		ModelLoader.setCustomModelResourceLocation(ModItems.unknown_fossil_carnivore_dinosaur, 2, new ModelResourceLocation("backtolife:unknown_fossil_carnivore_dinosaur_2"));
		ModelLoader.setCustomModelResourceLocation(ModItems.unknown_fossil_herbivore_dinosaur, 0, new ModelResourceLocation("backtolife:unknown_fossil_herbivore_dinosaur_0"));
		ModelLoader.setCustomModelResourceLocation(ModItems.unknown_fossil_herbivore_dinosaur, 1, new ModelResourceLocation("backtolife:unknown_fossil_herbivore_dinosaur_1"));
		ModelLoader.setCustomModelResourceLocation(ModItems.unknown_fossil_herbivore_dinosaur, 2, new ModelResourceLocation("backtolife:unknown_fossil_herbivore_dinosaur_2"));
		ModelLoader.setCustomModelResourceLocation(ModItems.unknown_fossil_pterosaur, 0, new ModelResourceLocation("backtolife:unknown_fossil_pterosaur_0"));
		ModelLoader.setCustomModelResourceLocation(ModItems.unknown_fossil_pterosaur, 1, new ModelResourceLocation("backtolife:unknown_fossil_pterosaur_1"));
		ModelLoader.setCustomModelResourceLocation(ModItems.unknown_fossil_pterosaur, 2, new ModelResourceLocation("backtolife:unknown_fossil_pterosaur_2"));
		for(EnumPrehistoricType prehistoric : EnumPrehistoricType.values()){
			ModelLoader.setCustomModelResourceLocation(ModItems.soft_tissue, prehistoric.ordinal(), new ModelResourceLocation("backtolife:soft_tissue"));
			ModelLoader.setCustomModelResourceLocation(ModItems.fossil_cells, prehistoric.ordinal(), new ModelResourceLocation("backtolife:fossil_cells"));
			ModelLoader.setCustomModelResourceLocation(ModItems.prehistoric_egg, prehistoric.ordinal(), new ModelResourceLocation("backtolife:egg_" + prehistoric.eggType.toString().toLowerCase()));
		}
	}
	
	public void init(){
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityFossilSlicer.class, new RenderFossilSlicer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTissueAnalyzer.class, new RenderTissueAnalyzer());
	}
	
	public void postInit(){

	}

	public void addItemRender(Item item, String name){
		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation("backtolife:" + name));
	}
}
