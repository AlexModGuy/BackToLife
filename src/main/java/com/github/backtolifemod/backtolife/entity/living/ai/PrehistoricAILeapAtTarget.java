package com.github.backtolifemod.backtolife.entity.living.ai;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.math.MathHelper;

import com.github.backtolifemod.backtolife.entity.living.EntityLandPrehistoric;

public class PrehistoricAILeapAtTarget extends EntityAIBase {
	private EntityLandPrehistoric prehistoric;
	private EntityLivingBase leapTarget;
	float leapMotionY;

	public PrehistoricAILeapAtTarget(EntityLandPrehistoric prehistoric, float leapMotionYIn) {
		this.prehistoric = prehistoric;
		this.leapMotionY = leapMotionYIn;
		this.setMutexBits(5);
	}

	public boolean shouldExecute() {
		this.leapTarget = this.prehistoric.getAttackTarget();
		if(prehistoric.isMovementCeased()){
			return false;
		}
		if (this.leapTarget == null) {
			return false;
		} else {
			double d0 = this.prehistoric.getDistanceSqToEntity(this.leapTarget);
			return d0 >= 6.0D && d0 <= 16.0D ? (!this.prehistoric.onGround ? false : this.prehistoric.getRNG().nextInt(5) == 0) : false;
		}
	}

	public boolean continueExecuting() {
		return !this.prehistoric.onGround;
	}

	public void startExecuting() {
		if(prehistoric.getAnimationTick() > 10){
			double d0 = this.leapTarget.posX - this.prehistoric.posX;
			double d1 = this.leapTarget.posZ - this.prehistoric.posZ;
			float f = MathHelper.sqrt_double(d0 * d0 + d1 * d1);
			this.prehistoric.motionX += d0 / (double) f * 0.5D * 0.800000011920929D + this.prehistoric.motionX * 0.20000000298023224D;
			this.prehistoric.motionZ += d1 / (double) f * 0.5D * 0.800000011920929D + this.prehistoric.motionZ * 0.20000000298023224D;
			this.prehistoric.motionY = (double) this.leapMotionY;
		}
	}
}