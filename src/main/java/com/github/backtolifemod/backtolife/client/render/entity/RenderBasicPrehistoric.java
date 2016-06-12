package com.github.backtolifemod.backtolife.client.render.entity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.github.backtolifemod.backtolife.entity.living.EntityPrehistoric;

public class RenderBasicPrehistoric extends RenderLiving<EntityPrehistoric> {

	public RenderBasicPrehistoric(ModelBase model, float shadowsize) {
		super(Minecraft.getMinecraft().getRenderManager(), model, shadowsize);
	}

	@Override
	protected void preRenderCallback(EntityPrehistoric entity, float f) {
		GL11.glScalef(entity.getRenderSize(), entity.getRenderSize(), entity.getRenderSize());
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityPrehistoric entity) {
		return new ResourceLocation(entity.getTexture() + ".png");
	}

}
