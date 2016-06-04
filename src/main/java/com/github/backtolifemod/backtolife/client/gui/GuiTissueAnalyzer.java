package com.github.backtolifemod.backtolife.client.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.github.backtolifemod.backtolife.client.inventory.ContainerTissueAnalyzer;
import com.github.backtolifemod.backtolife.entity.tile.TileEntityTissueAnalyzer;

@SideOnly(Side.CLIENT)
public class GuiTissueAnalyzer extends GuiContainer {
	private static final ResourceLocation texture = new ResourceLocation("backtolife:textures/gui/tissue_analyzer.png");
	private final InventoryPlayer playerInventory;
	private IInventory tissue_analyzer;

	public GuiTissueAnalyzer(InventoryPlayer playerInv, IInventory fossilslicer) {
		super(new ContainerTissueAnalyzer(playerInv, fossilslicer));
		this.playerInventory = playerInv;
		this.tissue_analyzer = fossilslicer;
	}

	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		if (tissue_analyzer != null) {
			String s = this.tissue_analyzer.getDisplayName().getUnformattedText();
			this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
		}
		this.fontRendererObj.drawString(this.playerInventory.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
	}

	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(texture);
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
		int i1;
		i1 = this.getGrindProg(60);
		this.drawTexturedModalRect(k + 62, l + 34, 176, 0, 60 - i1, 18);
	}

	private int getGrindProg(int pixels) {
		return ((TileEntityTissueAnalyzer) this.tissue_analyzer).isProgressing() ? this.tissue_analyzer.getField(0) * pixels / 300 : 60;
	}
}