package com.github.backtolifemod.backtolife.client;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import com.github.backtolifemod.backtolife.client.gui.GuiFossilSlicer;
import com.github.backtolifemod.backtolife.client.gui.GuiTissueAnalyzer;
import com.github.backtolifemod.backtolife.client.inventory.ContainerFossilSlicer;
import com.github.backtolifemod.backtolife.client.inventory.ContainerTissueAnalyzer;
import com.github.backtolifemod.backtolife.entity.tile.TileEntityFossilSlicer;
import com.github.backtolifemod.backtolife.entity.tile.TileEntityTissueAnalyzer;

public class GuiHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tileentity = world.getTileEntity(new BlockPos(x, y, z));
		switch(ID){
		case 0:
			return new ContainerFossilSlicer(player.inventory, (TileEntityFossilSlicer)tileentity);
		case 1:
			return new ContainerTissueAnalyzer(player.inventory, (TileEntityTissueAnalyzer)tileentity);
		}
		return null;
		
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tileentity = world.getTileEntity(new BlockPos(x, y, z));
		switch(ID){
		case 0:
			return new GuiFossilSlicer(player.inventory, (TileEntityFossilSlicer)tileentity);
		case 1:
			return new GuiTissueAnalyzer(player.inventory, (TileEntityTissueAnalyzer)tileentity);
		}
		return null;
	}

}
