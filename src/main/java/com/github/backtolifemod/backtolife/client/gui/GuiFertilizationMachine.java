package com.github.backtolifemod.backtolife.client.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.github.backtolifemod.backtolife.client.inventory.ContainerFertilizationMachine;
import com.github.backtolifemod.backtolife.entity.tile.TileEntityFertilizationMachine;

@SideOnly(Side.CLIENT)
public class GuiFertilizationMachine extends GuiContainer{
	private static final ResourceLocation texture = new ResourceLocation("backtolife:textures/gui/fertilization_machine.png");
	private final InventoryPlayer playerInventory;
	private IInventory fertilization_machine;

	public GuiFertilizationMachine(InventoryPlayer playerInv, IInventory fossilslicer){
		super(new ContainerFertilizationMachine(playerInv, fossilslicer));
		this.playerInventory = playerInv;
		this.fertilization_machine = fossilslicer;
	}

	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY){
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		if(fertilization_machine != null){
			String s = this.fertilization_machine.getDisplayName().getUnformattedText();
			this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
		}
		this.fontRendererObj.drawString(this.playerInventory.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
	}

	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY){
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(texture);
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
		int i1 = this.getGrindProg(60);
		this.drawTexturedModalRect(k + 62, l + 34, 176, 0, 60 - i1, 18);
		int i2 = this.getSyringeProg(18);
		this.drawTexturedModalRect(k + 37, l + 34, 176, 18, 18, i2);
	}

	private int getGrindProg(int pixels){
		return ((TileEntityFertilizationMachine) this.fertilization_machine).isProgressing() ? this.fertilization_machine.getField(0) * pixels / 500 : 60;
	}

	private int getSyringeProg(int pixels){
		return this.fertilization_machine.getField(1) * pixels / 100;
	}
}