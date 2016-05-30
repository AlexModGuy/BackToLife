package com.github.backtolifemod.backtolife.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

import com.github.backtolifemod.backtolife.BackToLife;
import com.github.backtolifemod.backtolife.entity.tile.TileEntityFossilSlicer;


public class BlockLaboratory extends Block {

	public BlockLaboratory(boolean light) {
		super(Material.IRON);
		this.setHardness(4.0F);
		this.setResistance(10.0F);
		this.setLightLevel(light ? 1 : 0);
		this.setSoundType(SoundType.METAL);
		this.setCreativeTab(BackToLife.tab);
		this.setUnlocalizedName(light ? "backtolife.laboratory_light_panel" : "backtolife.laboratory_block");
		GameRegistry.registerBlock(this, light ? "laboratory_light_panel" : "laboratory_block");
		BackToLife.PROXY.addItemRender(Item.getItemFromBlock(this), light ? "laboratory_light_panel" : "laboratory_block");
	}

}
