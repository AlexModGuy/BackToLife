package com.github.backtolifemod.backtolife.entity.living.ai;

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

	@Override
	public boolean shouldExecute() {
		if(this.prehistoric.isMovementCeased()){
			return false;
		}
		return this.prehistoric.getRNG().nextFloat() < 0.02F;
	}

	@Override
	public boolean continueExecuting() {
		return this.idleTime >= 0;
	}

	@Override
	public void startExecuting() {
		double d0 = (Math.PI * 2D) * this.prehistoric.getRNG().nextDouble();
		this.lookX = Math.cos(d0);
		this.lookZ = Math.sin(d0);
		this.idleTime = 20 + this.prehistoric.getRNG().nextInt(20);
	}

	@Override
	public void updateTask() {
		--this.idleTime;
		this.prehistoric.getLookHelper().setLookPosition(this.prehistoric.posX + this.lookX, this.prehistoric.posY + this.prehistoric.getEyeHeight(), this.prehistoric.posZ + this.lookZ, this.prehistoric.getHorizontalFaceSpeed(), this.prehistoric.getVerticalFaceSpeed());
	}
}