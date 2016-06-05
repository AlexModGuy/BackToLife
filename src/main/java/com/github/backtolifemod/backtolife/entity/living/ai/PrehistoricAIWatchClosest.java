package com.github.backtolifemod.backtolife.entity.living.ai;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIWatchClosest;

import com.github.backtolifemod.backtolife.entity.living.EntityLandPrehistoric;

public class PrehistoricAIWatchClosest extends EntityAIWatchClosest {

	public PrehistoricAIWatchClosest(EntityLiving entitylivingIn, Class<? extends Entity> watchTargetClass, float maxDistance) {
		super(entitylivingIn, watchTargetClass, maxDistance);
	}

	public boolean shouldExecute(){
		if(this.theWatcher instanceof EntityLandPrehistoric && ((EntityLandPrehistoric)this.theWatcher).isSleeping()){
			return false;
		}
		return super.shouldExecute();
	}
}
