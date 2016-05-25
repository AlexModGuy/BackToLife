package com.github.backtolifemod.backtolife;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

import com.github.backtolifemod.backtolife.core.ModItems;

public class ClientProxy extends CommonProxy{

	public void preInit(){
		ModelBakery.registerItemVariants(ModItems.unknown_fossil_carnivore_dinosaur, new ResourceLocation("backtolife:unknown_fossil_carnivore_dinosaur_0"), new ResourceLocation("backtolife:unknown_fossil_carnivore_dinosaur_1"), new ResourceLocation("backtolife:unknown_fossil_carnivore_dinosaur_2"));
		ModelBakery.registerItemVariants(ModItems.unknown_fossil_herbivore_dinosaur, new ResourceLocation("backtolife:unknown_fossil_herbivore_dinosaur_0"), new ResourceLocation("backtolife:unknown_fossil_herbivore_dinosaur_1"), new ResourceLocation("backtolife:unknown_fossil_herbivore_dinosaur_2"));
		ModelBakery.registerItemVariants(ModItems.unknown_fossil_pterosaur, new ResourceLocation("backtolife:unknown_fossil_pterosaur_0"), new ResourceLocation("backtolife:unknown_fossil_pterosaur_1"), new ResourceLocation("backtolife:unknown_fossil_pterosaur_2"));
		addItemRenderWithMeta(ModItems.unknown_fossil_carnivore_dinosaur, 0, "unknown_fossil_carnivore_dinosaur_0");
		addItemRenderWithMeta(ModItems.unknown_fossil_carnivore_dinosaur, 1, "unknown_fossil_carnivore_dinosaur_1");
		addItemRenderWithMeta(ModItems.unknown_fossil_carnivore_dinosaur, 2, "unknown_fossil_carnivore_dinosaur_2");
		addItemRenderWithMeta(ModItems.unknown_fossil_herbivore_dinosaur, 0, "unknown_fossil_herbivore_dinosaur_0");
		addItemRenderWithMeta(ModItems.unknown_fossil_herbivore_dinosaur, 1, "unknown_fossil_herbivore_dinosaur_1");
		addItemRenderWithMeta(ModItems.unknown_fossil_herbivore_dinosaur, 2, "unknown_fossil_herbivore_dinosaur_2");
		addItemRenderWithMeta(ModItems.unknown_fossil_herbivore_dinosaur, 0, "unknown_fossil_pterosaur_0");
		addItemRenderWithMeta(ModItems.unknown_fossil_herbivore_dinosaur, 1, "unknown_fossil_pterosaur_1");
		addItemRenderWithMeta(ModItems.unknown_fossil_herbivore_dinosaur, 2, "unknown_fossil_pterosaur_2");	
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
