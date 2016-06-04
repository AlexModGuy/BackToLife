package com.github.backtolifemod.backtolife.client.render.tile;

import net.ilexiconn.llibrary.LLibrary;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.github.backtolifemod.backtolife.client.model.ModelFertilizationMachine;
import com.github.backtolifemod.backtolife.entity.tile.TileEntityFertilizationMachine;

public class RenderFertilizationMachine extends TileEntitySpecialRenderer<TileEntityFertilizationMachine> {

	private static final ResourceLocation TEXTURE = new ResourceLocation("backtolife:textures/models/fertilization_machine.png");
	private static final ModelFertilizationMachine MODEL = new ModelFertilizationMachine();

	@Override
	public void renderTileEntityAt(TileEntityFertilizationMachine tile, double x, double y, double z, float partialTicks, int destroyStage) {
		GL11.glPushMatrix();
		GL11.glTranslatef((float) x + 0.5F, (float) y +  1.475F, (float) z + 0.5F);
		GL11.glRotatef(-180, 1.0F, 0.0F, 0.0F);
		GL11.glRotatef(this.getRotation(tile), 0.0F, 1.0F, 0.0F);
		GL11.glPushMatrix();
		this.bindTexture(TEXTURE);
		MODEL.render(0.0625F);
		GL11.glPopMatrix();
		if(tile.isSyringeProgressing()){
			GL11.glRotatef(-180, 1.0F, 0.0F, 0.0F);
            float f3 = (((float)Minecraft.getMinecraft().thePlayer.ticksExisted) / 20.0F) * (180F / (float)Math.PI);
			GL11.glTranslatef((float)0F, (float)-1.02F, (float)0.23F);
			GL11.glRotatef(f3, 0.0F, 1.0F, 0.0F);
			GL11.glScalef(0.4F, 0.4F, 0.4F);
	        Minecraft.getMinecraft().getRenderItem().renderItem(new ItemStack(Items.EGG), ItemCameraTransforms.TransformType.FIXED);
		}
		GL11.glPopMatrix();
		
	}

	private float getRotation(TileEntityFertilizationMachine tile) {
		switch(tile.getBlockMetadata()){
		default: return 0;
		case 1: return 90;
		case 2: return 180;
		case 3: return -90;
		}
	}
}
