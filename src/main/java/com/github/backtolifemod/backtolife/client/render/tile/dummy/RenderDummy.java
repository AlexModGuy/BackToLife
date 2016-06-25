package com.github.backtolifemod.backtolife.client.render.tile.dummy;

import java.lang.reflect.Field;

import net.ilexiconn.llibrary.client.model.tools.AdvancedModelRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.github.backtolifemod.backtolife.client.model.ModelFossilSlicer;
import com.github.backtolifemod.backtolife.enums.EnumPrehistoricType;

public abstract class RenderDummy extends TileEntitySpecialRenderer {
	public int meta;

	public RenderDummy(int meta) {
		super();
		this.meta = meta;
	}

	public void renderPart(double x, double y, double z, String partname, boolean renderWithChildren) {
		if (meta == -1) {
			meta = 0;
		}
		EnumPrehistoricType prehistoric = EnumPrehistoricType.values()[meta];
		ResourceLocation texture = new ResourceLocation("backtolife:textures/models/entity/" + prehistoric.toString().toLowerCase() + "_fossil.png");
		Render render = Minecraft.getMinecraft().getRenderManager().getEntityClassRenderObject(prehistoric.entityClass);
		GL11.glPushMatrix();
		GL11.glTranslatef((float) x + 0.5F, (float) y + 1.475F, (float) z + 0.5F);
		GL11.glPushMatrix();
		GL11.glRotatef(-180, 1.0F, 0.0F, 0.0F);
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glPushMatrix();
		if (render instanceof RenderLiving) {
			RenderLiving renderLiving = (RenderLiving) render;
			ModelBase model = null;
			try {
				model = renderLiving.getMainModel().getClass().newInstance();
			} catch (InstantiationException e1) {
				e1.printStackTrace();
			} catch (IllegalAccessException e1) {
				e1.printStackTrace();
			}
			Class modelClass = model.getClass();
			Field modelHead = null;
			try {
				modelHead = modelClass.getField(partname);
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			}
			AdvancedModelRenderer modelPart = null;
			if (modelHead != null) {
				try {
					modelPart = (AdvancedModelRenderer) modelHead.get(model);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
				if (modelPart != null) {
					GL11.glPushMatrix();
					GL11.glRotatef(180, 0, 1, 0);
					GL11.glTranslatef(0, 1.3F, 0.4F);
					this.applyRotations(prehistoric);
					this.bindTexture(texture);
					modelPart.setRotationPoint(0, 0, 0);
					modelPart.render(0.0625F);
					if (!renderWithChildren) {
						for (ModelRenderer child : modelPart.childModels) {
							child.isHidden = true;
							if (partname.equals("Body")) {
								Field body2Field = null;
								try {
									body2Field = modelClass.getField("Body2");
								} catch (NoSuchFieldException e) {
									e.printStackTrace();
								} catch (SecurityException e) {
									e.printStackTrace();
								}
								if (body2Field != null) {
									AdvancedModelRenderer modelBody2 = null;
									try {
										modelBody2 = (AdvancedModelRenderer) body2Field.get(model);
									} catch (IllegalArgumentException e) {
										e.printStackTrace();
									} catch (IllegalAccessException e) {
										e.printStackTrace();
									}
									if (child == modelBody2 && modelBody2 != null) {
										modelBody2.isHidden = false;
									}
								}
							}
						}
					}
					modelPart.resetToDefaultPose();
					GL11.glPopMatrix();
				}
			}
		}
		GL11.glPopMatrix();
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glPopMatrix();
		GL11.glPopMatrix();
	}

	private static Object cloneObject(Object obj) {
		try {
			Object clone = obj.getClass().newInstance();
			for (Field field : obj.getClass().getDeclaredFields()) {
				field.setAccessible(true);
				field.set(clone, field.get(obj));
			}
			return clone;
		} catch (Exception e) {
			return null;
		}
	}

	protected abstract void applyRotations(EnumPrehistoricType prehistoric);
}
