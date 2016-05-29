package com.github.backtolifemod.backtolife.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.lwjgl.opengl.GL11;

import com.github.backtolifemod.backtolife.client.inventory.ContainerFossilSlicer;
import com.github.backtolifemod.backtolife.core.ModItems;
import com.github.backtolifemod.backtolife.entity.tile.TileEntityFossilSlicer;

@SideOnly(Side.CLIENT)
public class GuiFossilSlicer extends GuiContainer
{
	private static final ResourceLocation texture = new ResourceLocation("backtolife:textures/gui/fossil_slicer.png");
	private final InventoryPlayer playerInventory;
	private IInventory fossilslicer;

	public GuiFossilSlicer(InventoryPlayer playerInv, IInventory fossilslicer){
		super(new ContainerFossilSlicer(playerInv, fossilslicer));
		this.playerInventory = playerInv;
		this.fossilslicer = fossilslicer;
	}

	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY){
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		if(fossilslicer != null){
			String s = this.fossilslicer.getDisplayName().getUnformattedText();
			this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
		}
		this.fontRendererObj.drawString(this.playerInventory.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);

		GL11.glPushMatrix();
		GL11.glTranslatef(70, 43, 0);
		GL11.glScalef(2.55F, 2.55F, 2.55F);
		GL11.glRotatef(Minecraft.getMinecraft().thePlayer.ticksExisted * 4, 0, 0, 1);
		GL11.glTranslatef(-8, -8, 0);
		this.drawItemStack(new ItemStack(ModItems.gear), 0, 0, (String)null);
		GL11.glPopMatrix();
	}

	private void drawItemStack(ItemStack stack, int x, int y, String altText){
		GlStateManager.translate(0.0F, 0.0F, 32.0F);
		this.zLevel = 200.0F;
		this.itemRender.zLevel = 200.0F;
		net.minecraft.client.gui.FontRenderer font = null;
		if (stack != null) font = stack.getItem().getFontRenderer(stack);
		if (font == null) font = fontRendererObj;
		this.itemRender.renderItemAndEffectIntoGUI(stack, x, y);
		this.itemRender.renderItemOverlayIntoGUI(font, stack, x, y, altText);
		this.zLevel = 0.0F;
		this.itemRender.zLevel = 0.0F;
	}

	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY){
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(texture);
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
		int i1;
		i1 = this.getGrindProg(72);
		this.drawTexturedModalRect(k + 35, l + 21, 176, 0, 72 - i1, 44);
	}

	private int getGrindProg(int pixels){
		return ((TileEntityFossilSlicer) this.fossilslicer).isGrinding() ? this.fossilslicer.getField(0) * pixels / 300 : 72;
	}
}