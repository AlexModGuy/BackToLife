package com.github.backtolifemod.backtolife;

import net.ilexiconn.llibrary.server.network.NetworkWrapper;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.GameRegistry;

import com.github.backtolifemod.backtolife.client.GuiHandler;
import com.github.backtolifemod.backtolife.core.ModBlocks;
import com.github.backtolifemod.backtolife.core.ModConfig;
import com.github.backtolifemod.backtolife.core.ModEntities;
import com.github.backtolifemod.backtolife.core.ModFoods;
import com.github.backtolifemod.backtolife.core.ModItems;
import com.github.backtolifemod.backtolife.core.ModRecipes;
import com.github.backtolifemod.backtolife.core.ModSounds;
import com.github.backtolifemod.backtolife.core.ModWorld;
import com.github.backtolifemod.backtolife.message.MessageSetDay;
import com.github.backtolifemod.backtolife.world.BackToLifeGeneration;

@Mod(modid = BackToLife.MODID, name = BackToLife.NAME, version = BackToLife.VERSION)
public class BackToLife {
	public static final String MODID = "backtolife";
	public static final String NAME = "Back To Life";
	public static final String VERSION = "1.0";
	@Instance(value = MODID)
	public static BackToLife INSTANCE;
	@SidedProxy(clientSide = "com.github.backtolifemod.backtolife.ClientProxy", serverSide = "com.github.backtolifemod.backtolife.CommonProxy")
	public static CommonProxy PROXY;
	public static CreativeTabs tab;
    @NetworkWrapper({MessageSetDay.class})
    public static SimpleNetworkWrapper NETWORK_WRAPPER;
    
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		tab = new CreativeTabs(MODID) {

			@Override
			public Item getTabIconItem() {
				return ModItems.fossil_skull;
			}

			@Override
			public int getIconItemDamage() {
				return 0;
			}

		};
		ModItems.init();
		ModBlocks.init();
		ModRecipes.init();
		ModEntities.init();
		ModWorld.init();
		ModSounds.init();
		ModFoods.init();
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();
		ModConfig.load(config);
		config.save();
        GameRegistry.registerWorldGenerator(new BackToLifeGeneration(), 20);
		PROXY.preInit();
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		PROXY.init();
		NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		PROXY.postInit();

	}
}
