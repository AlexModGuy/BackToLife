package com.github.backtolifemod.backtolife.client.render.entity;

import com.github.backtolifemod.backtolife.entity.living.EntityPrehistoric;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderGrowingPrehistoric extends RenderBasicPrehistoric {

	public ModelBase babyModel;
	public ModelBase teenModel;
	public ModelBase adultModel;

	public RenderGrowingPrehistoric(ModelBase babyModel, ModelBase teenModel, ModelBase adultModel, float shadowsize) {
		super(babyModel, shadowsize);
		this.babyModel = babyModel;
		this.teenModel = teenModel;
		this.adultModel = adultModel;
	}

	@Override
	protected void preRenderCallback(EntityPrehistoric entity, float f) {
		GL11.glScalef(entity.getRenderSize(), entity.getRenderSize(), entity.getRenderSize());
		setModelForAge(entity);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityPrehistoric entity) {
		return new ResourceLocation(entity.getTexture() + ".png");
	}

	public void setModelForAge(EntityPrehistoric entity){
		if(entity.isBaby()){
			this.mainModel = babyModel;
		}
		if(entity.isTeen()){
			this.mainModel = teenModel;
		}
		if(entity.isAdult()){
			this.mainModel = adultModel;
		}
	}

}
