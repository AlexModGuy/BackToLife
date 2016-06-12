package com.github.backtolifemod.backtolife.event;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import com.github.backtolifemod.backtolife.core.ModItems;
import com.github.backtolifemod.backtolife.entity.living.EntityPrehistoric;

public class ClientEvents {

	@SubscribeEvent
	public void postRenderLiving(RenderLivingEvent.Post e) {
		EntityPlayer player = Minecraft.getMinecraft().thePlayer;
		if(player.getHeldItemMainhand() != null && player.getHeldItemMainhand().getItem() != null && player.getHeldItemMainhand().getItem() == ModItems.magnifying_glass || player.getHeldItemOffhand() != null && player.getHeldItemOffhand().getItem() != null && player.getHeldItemOffhand().getItem() == ModItems.magnifying_glass)
		if (Minecraft.getMinecraft().objectMouseOver != null) {
			if (Minecraft.getMinecraft().objectMouseOver.entityHit != null) {
				if (Minecraft.getMinecraft().objectMouseOver.entityHit == e.getEntity()) {
					if (e.getEntity() instanceof EntityPrehistoric) {
						EntityPrehistoric prehistoric = (EntityPrehistoric) Minecraft.getMinecraft().objectMouseOver.entityHit;
						int ageMin = prehistoric.getAgeInTicks() / 1200;
						if(prehistoric.getOwner() != null){
							renderString(prehistoric, e.getRenderer(), I18n.format("entity.backtolife.owner") + prehistoric.getOwner().getName(), e.getX(), e.getY() + 0.75D, e.getZ());
						}
						renderString(prehistoric, e.getRenderer(), I18n.format("entity.backtolife.health") + (int)prehistoric.getHealth() + "/" + (int)prehistoric.getMaxHealth(), e.getX(), e.getY() + 0.5D, e.getZ());
						renderString(prehistoric, e.getRenderer(), I18n.format("entity.backtolife.hunger") + prehistoric.getHunger() + "%", e.getX(), e.getY() + 0.25D, e.getZ());
						renderString(prehistoric, e.getRenderer(), I18n.format("entity.backtolife.age") + prehistoric.getAgeInDays() + " (" + ageMin + " " + I18n.format("entity.backtolife.min") + ")", e.getX(), e.getY(), e.getZ());
					}
				}
			}
		}
	}

	public void renderString(EntityLivingBase entity, RenderLivingBase render, String string, double x, double y, double z) {
		double d0 = entity.getDistanceSqToEntity(render.getRenderManager().renderViewEntity);
		if (d0 < 8 * 8) {
			GlStateManager.alphaFunc(516, 0.1F);
			renderLivingLabel(render, entity, string, x, y, z, 64);
		}
	}

	protected void renderLivingLabel(RenderLivingBase render, EntityLivingBase entityIn, String str, double x, double y, double z, int maxDistance) {
		double d0 = entityIn.getDistanceSqToEntity(render.getRenderManager().renderViewEntity);

		if (d0 <= maxDistance * maxDistance) {
			boolean flag = entityIn.isSneaking();
			GlStateManager.pushMatrix();
			float f = flag ? 0.25F : 0.0F;
			GlStateManager.translate((float) x, (float) y + entityIn.height + 0.25F - f, (float) z);
			GlStateManager.glNormal3f(0.0F, 1.0F, 0.0F);
			GlStateManager.rotate(-render.getRenderManager().playerViewY, 0.0F, 1.0F, 0.0F);
			GlStateManager.rotate((render.getRenderManager().options.thirdPersonView == 2 ? -1 : 1) * render.getRenderManager().playerViewX, 1.0F, 0.0F, 0.0F);
			GlStateManager.scale(-0.025F, -0.025F, 0.025F);
			GlStateManager.disableLighting();
			GlStateManager.depthMask(false);

			if (!flag) {
				GlStateManager.disableDepth();
			}

			GlStateManager.enableBlend();
			GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
			FontRenderer fontrenderer = render.getFontRendererFromRenderManager();
			int j = fontrenderer.getStringWidth(str) / 2;
			GlStateManager.disableTexture2D();
			Tessellator tessellator = Tessellator.getInstance();
			VertexBuffer vertexbuffer = tessellator.getBuffer();
			vertexbuffer.begin(7, DefaultVertexFormats.POSITION_COLOR);
			vertexbuffer.pos(-j - 1, (-1), 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
			vertexbuffer.pos(-j - 1, (8), 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
			vertexbuffer.pos(j + 1, (8), 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
			vertexbuffer.pos(j + 1, (-1), 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
			tessellator.draw();
			GlStateManager.enableTexture2D();

			if (!flag) {
				fontrenderer.drawString(str, -fontrenderer.getStringWidth(str) / 2, 0, 0XD81000);
				GlStateManager.enableDepth();
			}

			GlStateManager.depthMask(true);
			fontrenderer.drawString(str, -fontrenderer.getStringWidth(str) / 2, 0, flag ? 553648127 : -1);
			GlStateManager.enableLighting();
			GlStateManager.disableBlend();
			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			GlStateManager.popMatrix();
		}
	}
}
