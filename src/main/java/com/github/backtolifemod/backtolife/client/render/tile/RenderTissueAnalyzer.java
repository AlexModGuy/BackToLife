package com.github.backtolifemod.backtolife.client.render.tile;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.github.backtolifemod.backtolife.client.model.ModelTissueAnalyzer;
import com.github.backtolifemod.backtolife.core.ModItems;
import com.github.backtolifemod.backtolife.entity.tile.TileEntityFossilSlicer;
import com.github.backtolifemod.backtolife.entity.tile.TileEntityTissueAnalyzer;

public class RenderTissueAnalyzer extends TileEntitySpecialRenderer<TileEntityTissueAnalyzer> {

	private static final ResourceLocation TEXTURE = new ResourceLocation("backtolife:textures/models/tissue_analyzer.png");
	private static final ModelTissueAnalyzer MODEL = new ModelTissueAnalyzer();

	@Override
	public void renderTileEntityAt(TileEntityTissueAnalyzer tile, double x, double y, double z, float partialTicks, int destroyStage) {
		GL11.glPushMatrix();
		GL11.glTranslatef((float) x + 0.5F, (float) y +  1.475F, (float) z + 0.5F);
		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glPushMatrix();
		GL11.glRotatef(-180, 1.0F, 0.0F, 0.0F);
		GL11.glRotatef(this.getRotation(tile), 0.0F, 1.0F, 0.0F);
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glPushMatrix();
		this.bindTexture(TEXTURE);
		MODEL.render(0.0625F);
		GL11.glPopMatrix();
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glPopMatrix();
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glPopMatrix();
		GL11.glPopMatrix();
	}

	private float getRotation(TileEntityTissueAnalyzer tile) {
		switch(tile.getBlockMetadata()){
		default: return 0;
		case 1: return 90;
		case 2: return 180;
		case 3: return -90;
		}
	}
}
