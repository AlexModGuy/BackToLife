package com.github.backtolifemod.backtolife.client.render.tile.dummy;

import net.minecraft.tileentity.TileEntity;

import org.lwjgl.opengl.GL11;

import com.github.backtolifemod.backtolife.enums.EnumPrehistoricType;

public class RenderFossilSkull extends RenderDummy{

	public RenderFossilSkull(int meta) {
		super(meta);
	}

	@Override
	protected void applyRotations(EnumPrehistoricType prehistoric) {
		switch(prehistoric){
		default:
			break;
		case PROTOCERATOPS:
			GL11.glScalef(0.8F, 0.8F, 0.8F);
			GL11.glTranslatef(0, 0F, -0.3F);
			break;
		case TARBOSAURUS:
			GL11.glTranslatef(0, -0.1F, 0);
			GL11.glScalef(0.8F, 0.8F, 0.8F);
			break;
		}
	}

	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float partialTicks, int destroyStage) {
		this.renderPart(x, y, z, "Head1", true);
	}

}
