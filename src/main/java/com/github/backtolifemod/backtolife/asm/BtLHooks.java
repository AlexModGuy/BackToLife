package com.github.backtolifemod.backtolife.asm;

import net.ilexiconn.llibrary.client.event.PlayerModelEvent;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;

public class BtLHooks {

    public static void renderSkullItems(ItemStack stack) {
        System.out.println("kachow");
    }
}
