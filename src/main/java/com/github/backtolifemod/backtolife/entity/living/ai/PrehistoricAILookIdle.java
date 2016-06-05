package com.github.backtolifemod.backtolife.entity.living.ai;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;

import com.github.backtolifemod.backtolife.entity.living.EntityLandPrehistoric;

public class PrehistoricAILookIdle extends EntityAIBase {
	private EntityLandPrehistoric prehistoric;
	private double lookX;
	private double lookZ;
	private int idleTime;

	public PrehistoricAILookIdle(EntityLandPrehistoric prehistoric) {
		this.prehistoric = prehistoric;
		this.setMutexBits(3);
	}

	public boolean shouldExecute() {
		if(this.prehistoric.isMovementCeased()){
			return false;
		}
		return this.prehistoric.getRNG().nextFloat() < 0.02F;
	}

	public boolean continueExecuting() {
		return this.idleTime >= 0;
	}

	public void startExecuting() {
		double d0 = (Math.PI * 2D) * this.prehistoric.getRNG().nextDouble();
		this.lookX = Math.cos(d0);
		this.lookZ = Math.sin(d0);
		this.idleTime = 20 + this.prehistoric.getRNG().nextInt(20);
	}

	public void updateTask() {
		--this.idleTime;
		this.prehistoric.getLookHelper().setLookPosition(this.prehistoric.posX + this.lookX, this.prehistoric.posY + (double) this.prehistoric.getEyeHeight(), this.prehistoric.posZ + this.lookZ, (float) this.prehistoric.getHorizontalFaceSpeed(), (float) this.prehistoric.getVerticalFaceSpeed());
	}
}