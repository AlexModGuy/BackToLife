package com.github.backtolifemod.backtolife;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

import com.github.backtolifemod.backtolife.client.model.ModelProtoceratops;
import com.github.backtolifemod.backtolife.client.model.ModelTarbosaurus;
import com.github.backtolifemod.backtolife.client.model.ModelVelociraptor;
import com.github.backtolifemod.backtolife.client.render.entity.RenderBasicPrehistoric;
import com.github.backtolifemod.backtolife.client.render.tile.RenderFertilizationMachine;
import com.github.backtolifemod.backtolife.client.render.tile.RenderFossilSlicer;
import com.github.backtolifemod.backtolife.client.render.tile.RenderTissueAnalyzer;
import com.github.backtolifemod.backtolife.core.ModItems;
import com.github.backtolifemod.backtolife.entity.living.EntityProtoceratops;
import com.github.backtolifemod.backtolife.entity.living.EntityTarbosaurus;
import com.github.backtolifemod.backtolife.entity.living.EntityVelociraptor;
import com.github.backtolifemod.backtolife.entity.tile.TileEntityFertilizationMachine;
import com.github.backtolifemod.backtolife.entity.tile.TileEntityFossilSlicer;
import com.github.backtolifemod.backtolife.entity.tile.TileEntityTissueAnalyzer;
import com.github.backtolifemod.backtolife.enums.EnumPrehistoricType;
import com.github.backtolifemod.backtolife.event.ClientEvents;

public class ClientProxy extends CommonProxy {

	@Override
	public void preInit() {
		for (EnumPrehistoricType prehistoric : EnumPrehistoricType.values()) {
			ModelLoader.setCustomModelResourceLocation(ModItems.soft_tissue, prehistoric.ordinal(), new ModelResourceLocation("backtolife:soft_tissue"));
			ModelLoader.setCustomModelResourceLocation(ModItems.fossil_cells, prehistoric.ordinal(), new ModelResourceLocation("backtolife:fossil_cells"));
			ModelLoader.setCustomModelResourceLocation(ModItems.prehistoric_egg, prehistoric.ordinal(), new ModelResourceLocation("backtolife:egg_" + prehistoric.eggType.toString().toLowerCase()));
		}
	}
	
	@Override
	public void init() {
		MinecraftForge.EVENT_BUS.register(new ClientEvents());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityFossilSlicer.class, new RenderFossilSlicer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTissueAnalyzer.class, new RenderTissueAnalyzer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityFertilizationMachine.class, new RenderFertilizationMachine());
		RenderingRegistry.registerEntityRenderingHandler(EntityVelociraptor.class, new RenderBasicPrehistoric(new ModelVelociraptor(), 0.3F));
		RenderingRegistry.registerEntityRenderingHandler(EntityProtoceratops.class, new RenderBasicPrehistoric(new ModelProtoceratops(), 0.4F));
		RenderingRegistry.registerEntityRenderingHandler(EntityTarbosaurus.class, new RenderBasicPrehistoric(new ModelTarbosaurus(), 0.8F));
	}

	@Override
	public void postInit() {

	}

	@Override
	public void addItemRender(Item item, String name) {
		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation("backtolife:" + name));
	}

}
