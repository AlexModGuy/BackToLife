package com.github.backtolifemod.backtolife.message;

import io.netty.buffer.ByteBuf;
import net.ilexiconn.llibrary.server.network.AbstractMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.github.backtolifemod.backtolife.entity.living.EntityLandPrehistoric;

public class MessageSetDay extends AbstractMessage<MessageSetDay> {

    public int entityId;
    public boolean isDay;

    public MessageSetDay(int dinosaurID, boolean isDay) {
        this.entityId = dinosaurID;
        this.isDay = isDay;
    }

    public MessageSetDay() {}

    @Override
    @SideOnly(Side.CLIENT)
    public void onClientReceived(Minecraft client, MessageSetDay message, EntityPlayer player, MessageContext messageContext) {
        Entity entity = player.worldObj.getEntityByID(message.entityId);
        if (entity instanceof EntityLandPrehistoric) {
        	EntityLandPrehistoric prehistoric = (EntityLandPrehistoric) entity;
            prehistoric.isDaytime = message.isDay;
        }
    }

    @Override
    public void onServerReceived(MinecraftServer server, MessageSetDay message, EntityPlayer player, MessageContext messageContext) {}

    @Override
    public void fromBytes(ByteBuf buf) {
        entityId = buf.readInt();
        isDay = buf.readBoolean();

    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(entityId);
        buf.writeBoolean(isDay);
    }
}