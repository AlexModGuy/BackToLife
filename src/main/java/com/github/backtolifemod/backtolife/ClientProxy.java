package com.github.backtolifemod.backtolife;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;

import com.github.backtolifemod.backtolife.core.ModItems;

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
	}
	
	public void init(){
		
	}
	
	public void postInit(){

	}

	public void addItemRender(Item item, String name){
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, new ModelResourceLocation("backtolife:" + name, "inventory"));
	}

	public void addItemRenderWithMeta(Item item, String name, int meta){
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, meta, new ModelResourceLocation("backtolife:" + name, "inventory"));
	}
}
