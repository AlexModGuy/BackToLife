package com.github.backtolifemod.backtolife.client.render.entity;

import java.lang.reflect.InvocationTargetException;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

import org.lwjgl.opengl.GL11;

import com.github.backtolifemod.backtolife.client.model.ModelPrehistoric;
import com.github.backtolifemod.backtolife.entity.EntityFossil;

public class RenderFossil extends Render<EntityFossil> {

	public RenderFossil() {
		super(Minecraft.getMinecraft().getRenderManager());
	}

	public void doRender(EntityFossil entity, double x, double y, double z, float entityYaw, float partialTicks){
		BlockRendererDispatcher dispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
		IBlockState state;
		if(entity.blockID != 0){
		state = Block.getBlockById(entity.blockID).getStateFromMeta(entity.blockMeta);
		}else{
			state = Block.getBlockById(1).getStateFromMeta(0);	
		}
		TextureAtlasSprite atlas = dispatcher.getModelForState(state).getParticleTexture();
		ResourceLocation blockTexture = null;
		try {
			blockTexture = (ResourceLocation) ReflectionHelper.findMethod(TextureMap.class, Minecraft.getMinecraft().getTextureMapBlocks(), new String[]{"getResourceLocation", "func_184396_a"}, TextureAtlasSprite.class).invoke(Minecraft.getMinecraft().getTextureMapBlocks(), atlas);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		AxisAlignedBB axisalignedbb1 = new AxisAlignedBB(entity.getRenderBoundingBox().minX - entity.posX + x, entity.getRenderBoundingBox().minY - entity.posY + y, entity.getRenderBoundingBox().minZ - entity.posZ + z, entity.getRenderBoundingBox().maxX - entity.posX + x, entity.getRenderBoundingBox().maxY - entity.posY + y, entity.getRenderBoundingBox().maxZ - entity.posZ + z);
		if(blockTexture != null){
			GL11.glPushMatrix();
			this.bindTexture(blockTexture);
			this.renderAABB(axisalignedbb1, x, y, z);
			GL11.glPopMatrix();
		}
		if(entity.getBreak() > 0){
			GL11.glPushMatrix();
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			this.bindTexture(new ResourceLocation("textures/blocks/destroy_stage_" + (entity.getBreak() - 1) + ".png"));
			this.renderAABB(axisalignedbb1, x, y, z);
			GL11.glDisable(GL11.GL_BLEND);
			GL11.glPopMatrix();
		}
		GL11.glPushMatrix();
		GL11.glTranslated(x, y + entity.type.fossilSize * entity.getScale() * 0.25F, z);
		GL11.glRotatef(90 * entity.rotation, 0, 1, 0);
		GL11.glRotatef(180, 1, 0, 0);
		GL11.glRotatef(-90, 0, 0, 1);
		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_CULL_FACE);
		this.bindEntityTexture(entity);
		Render render = this.renderManager.getEntityClassRenderObject(entity.type.entityClass);
		if(render instanceof RenderLiving){
			RenderLiving renderLiving = (RenderLiving)render;
			ModelBase model = null;
			try {
				model = renderLiving.getMainModel().getClass().newInstance();
			} catch (InstantiationException e1) {
				e1.printStackTrace();
			} catch (IllegalAccessException e1) {
				e1.printStackTrace();
			}
			if(model instanceof ModelPrehistoric){
				float scale = entity.getScale();
				GL11.glScalef(scale, scale, scale);
				((ModelPrehistoric)model).renderFossil(entity);
			}
		}
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glPopMatrix();
		GL11.glPopMatrix();
	}

	public static void renderAABB(AxisAlignedBB boundingBox, double x, double y, double z){
		Tessellator tessellator = Tessellator.getInstance();
		VertexBuffer vertexbuffer = tessellator.getBuffer();
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX_NORMAL);
		double maxX = boundingBox.maxX * 0.625F;
		double minX = boundingBox.minX * 0.625F;
		double maxY = boundingBox.maxY * 0.625F;
		double minY = boundingBox.minY * 0.625F;
		double maxZ = boundingBox.maxZ * 0.625F;
		double minZ = boundingBox.minZ * 0.625F;

		vertexbuffer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.minZ).tex(minX - maxX, maxY - minY).normal(0.0F, 0.0F, -1.0F).endVertex();
		vertexbuffer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ).tex(maxX - minX, maxY - minY).normal(0.0F, 0.0F, -1.0F).endVertex();
		vertexbuffer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.minZ).tex(maxX - minX, minY - maxY).normal(0.0F, 0.0F, -1.0F).endVertex();
		vertexbuffer.pos(boundingBox.minX, boundingBox.minY, boundingBox.minZ).tex(minX - maxX, minY - maxY).normal(0.0F, 0.0F, -1.0F).endVertex();

		vertexbuffer.pos(boundingBox.minX, boundingBox.minY, boundingBox.maxZ).tex(minX - maxX, minY - maxY).normal(0.0F, 0.0F, 1.0F).endVertex();
		vertexbuffer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ).tex(maxX - minX, minY - maxY).normal(0.0F, 0.0F, 1.0F).endVertex();
		vertexbuffer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ).tex(maxX - minX, maxY - minY).normal(0.0F, 0.0F, 1.0F).endVertex();
		vertexbuffer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ).tex(minX - maxX, maxY - minY).normal(0.0F, 0.0F, 1.0F).endVertex();

		vertexbuffer.pos(boundingBox.minX, boundingBox.minY, boundingBox.minZ).tex(minX - maxX, minY - maxY).normal(0.0F, -1.0F, 0.0F).endVertex();
		vertexbuffer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.minZ).tex(maxX - minX, minY - maxY).normal(0.0F, -1.0F, 0.0F).endVertex();
		vertexbuffer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ).tex(maxX - minX, maxZ - minZ).normal(0.0F, -1.0F, 0.0F).endVertex();
		vertexbuffer.pos(boundingBox.minX, boundingBox.minY, boundingBox.maxZ).tex(minX - maxX, maxZ - minZ).normal(0.0F, -1.0F, 0.0F).endVertex();

		vertexbuffer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ).tex(minX - maxX, minY - maxY).normal(0.0F, 1.0F, 0.0F).endVertex();
		vertexbuffer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ).tex(maxX - minX, minY - maxY).normal(0.0F, 1.0F, 0.0F).endVertex();
		vertexbuffer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ).tex(maxX - minX, maxZ - minZ).normal(0.0F, 1.0F, 0.0F).endVertex();
		vertexbuffer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.minZ).tex(minX - maxX, maxZ - minZ).normal(0.0F, 1.0F, 0.0F).endVertex();

		vertexbuffer.pos(boundingBox.minX, boundingBox.minY, boundingBox.maxZ).tex(minX - maxX, minY - maxY).normal(-1.0F, 0.0F, 0.0F).endVertex();
		vertexbuffer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ).tex(minX - maxX, maxY - minY).normal(-1.0F, 0.0F, 0.0F).endVertex();
		vertexbuffer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.minZ).tex(maxX - minX, maxY - minY).normal(-1.0F, 0.0F, 0.0F).endVertex();
		vertexbuffer.pos(boundingBox.minX, boundingBox.minY, boundingBox.minZ).tex(maxX - minX, minY - maxY).normal(-1.0F, 0.0F, 0.0F).endVertex();

		vertexbuffer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.minZ).tex(minX - maxX, minY - maxY).normal(1.0F, 0.0F, 0.0F).endVertex();
		vertexbuffer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ).tex(minX - maxX, maxY - minY).normal(1.0F, 0.0F, 0.0F).endVertex();
		vertexbuffer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ).tex(maxX - minX, maxY - minY).normal(1.0F, 0.0F, 0.0F).endVertex();
		vertexbuffer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ).tex(maxX - minX, minY - maxY).normal(1.0F, 0.0F, 0.0F).endVertex();
		tessellator.draw();
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityFossil entity) {
		return new ResourceLocation("backtolife:textures/models/entity/" + entity.type.name().toLowerCase() + "_fossil.png");
	}

}
