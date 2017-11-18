package com.github.backtolifemod.backtolife.event;

import com.github.backtolifemod.backtolife.entity.living.EntityPrehistoric;
import com.github.backtolifemod.backtolife.enums.EnumPrehistoricType;
import com.github.backtolifemod.backtolife.item.ItemPrehistoricEgg;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.item.ItemExpireEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Random;

public class CommonEvents {
    @SubscribeEvent
    public void itemExpire(ItemExpireEvent e) {
        if(!e.getEntity().worldObj.isRemote){
            if(e.getEntityItem().getEntityItem() != null){
                ItemStack stack = e.getEntityItem().getEntityItem();
                if(stack != null && stack.getItem() != null && stack.getItem() instanceof ItemPrehistoricEgg){
                    EnumPrehistoricType type = EnumPrehistoricType.values()[stack.getMetadata()];
                    Class clazz = type.entityClass;
                    Entity entity = null;
                    if (Entity.class.isAssignableFrom(clazz)) {
                        try {
                            entity = (Entity)clazz.getDeclaredConstructor(World.class).newInstance(e.getEntity().worldObj);
                        } catch (ReflectiveOperationException exeption) {
                            exeption.printStackTrace();
                        }
                    }
                    if(entity != null){
                        World world = e.getEntity().worldObj;
                        entity.setLocationAndAngles(e.getEntity().posX, e.getEntity().posY + 1, e.getEntity().posZ, world.rand.nextFloat() * 360.0F, 0.0F);
                        if(!world.isRemote){
                            world.spawnEntityInWorld(entity);
                        }
                        if (entity instanceof EntityPrehistoric) {
                            EntityPrehistoric prehistoric = (EntityPrehistoric) entity;
                               // prehistoric.setTamed(true);
                                prehistoric.setAgeInDays(1);
                                prehistoric.setGender(new Random().nextBoolean());
                               // prehistoric.setOwner(world.getClosestPlayerToEntity(prehistoric, 10).getDisplayName());
                        }
                    }
                }
            }
        }
    }
}
