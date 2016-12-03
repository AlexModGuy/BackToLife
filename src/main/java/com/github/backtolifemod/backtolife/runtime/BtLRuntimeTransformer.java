package com.github.backtolifemod.backtolife.runtime;

import com.github.backtolifemod.backtolife.asm.BtLHooks;
import net.ilexiconn.llibrary.server.asm.RuntimePatcher;
import net.ilexiconn.llibrary.server.core.patcher.LLibraryHooks;
import net.ilexiconn.llibrary.server.world.TickRateHandler;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraft.server.MinecraftServer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

public class BtLRuntimeTransformer extends RuntimePatcher {

    @Override
    public void onInit() {
        this.patchClass(TileEntityItemStackRenderer.class)
                .patchMethod("renderByItem", ItemStack.class, void.class)
                .apply(Patch.BEFORE, this.at(At.RETURN), method -> {
                    method.var(ALOAD, 0);
                    method.method(INVOKESTATIC, BtLHooks.class, "renderSkullItems", ItemStack.class, void.class);
                }).pop();
    }
}
