package com.github.backtolifemod.backtolife;

import com.github.backtolifemod.backtolife.core.ModItems;
import com.github.backtolifemod.backtolife.enums.EnumFossil;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = BackToLife.MODID, name = BackToLife.NAME, version = BackToLife.VERSION)
public class BackToLife
{
    public static final String MODID = "backtolife";
    public static final String NAME = "Back To Life";
    public static final String VERSION = "1.0";
    @Instance(value = MODID)
    public static BackToLife INSTANCE;
    
    @SidedProxy(clientSide = "com.github.backtolifemod.backtolife.ClientProxy", serverSide = "com.github.backtolifemod.backtolife.CommonProxy")
    public static CommonProxy PROXY;
    public static CreativeTabs tab;
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event){
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event){
    	tab = new CreativeTabs(MODID){

			@Override
			public Item getTabIconItem() {
				return EnumFossil.CARNIVORE_DINOSAUR.fossil;
			}
			
		    public int getIconItemDamage(){
		        return 2;
		    }
    		
    	};
    	ModItems.init();
    	PROXY.render();
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent event){
    	
    }
}
