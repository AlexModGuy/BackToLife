package com.github.backtolifemod.backtolife.entity.living.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.pathfinding.Path;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import com.github.backtolifemod.backtolife.entity.living.EntityLandPrehistoric;

public class PrehistoricAIAttackMelee extends EntityAIBase {
	private World worldObj;
	protected EntityLandPrehistoric prehistoric;
	private int attackTick;
	private double speedTowardsTarget;
	private boolean longMemory;
	private Path entityPathEntity;
	private int delayCounter;
	private double targetX;
	private double targetY;
	private double targetZ;
	protected final int attackInterval = 20;
	private int failedPathFindingPenalty = 0;
	private boolean canPenalize = false;

	public PrehistoricAIAttackMelee(EntityLandPrehistoric prehistoric, double speedIn, boolean useLongMemory) {
		this.prehistoric = prehistoric;
		this.worldObj = prehistoric.worldObj;
		this.speedTowardsTarget = speedIn;
		this.longMemory = useLongMemory;
		this.setMutexBits(3);
	}

	public boolean shouldExecute() {
		EntityLivingBase entitylivingbase = this.prehistoric.getAttackTarget();

		if (entitylivingbase == null) {
			return false;
		} else if (!entitylivingbase.isEntityAlive()) {
			return false;
		} else {
			if (canPenalize) {
				if (--this.delayCounter <= 0) {
					this.entityPathEntity = this.prehistoric.getNavigator().getPathToEntityLiving(entitylivingbase);
					this.delayCounter = 4 + this.prehistoric.getRNG().nextInt(7);
					return this.entityPathEntity != null;
				} else {
					return true;
				}
			}
			this.entityPathEntity = this.prehistoric.getNavigator().getPathToEntityLiving(entitylivingbase);
			return this.entityPathEntity != null;
		}
	}

	public boolean continueExecuting() {
		EntityLivingBase entitylivingbase = this.prehistoric.getAttackTarget();
		if (entitylivingbase != null && entitylivingbase.isDead) {
			this.resetTask();
			return false;
		}
		return entitylivingbase == null ? false : (!entitylivingbase.isEntityAlive() ? false : (!this.longMemory ? !this.prehistoric.getNavigator().noPath() : (!this.prehistoric.isWithinHomeDistanceFromPosition(new BlockPos(entitylivingbase)) ? false : !(entitylivingbase instanceof EntityPlayer) || !((EntityPlayer) entitylivingbase).isSpectator() && !((EntityPlayer) entitylivingbase).isCreative())));
	}

	public void startExecuting() {
		this.prehistoric.getNavigator().setPath(this.entityPathEntity, this.speedTowardsTarget);
		this.delayCounter = 0;
	}

	public void resetTask() {
		EntityLivingBase entitylivingbase = this.prehistoric.getAttackTarget();

		if (entitylivingbase instanceof EntityPlayer && (((EntityPlayer) entitylivingbase).isSpectator() || ((EntityPlayer) entitylivingbase).isCreative())) {
			this.prehistoric.setAttackTarget((EntityLivingBase) null);
		}

		this.prehistoric.getNavigator().clearPathEntity();
	}

	public void updateTask() {
		EntityLivingBase entitylivingbase = this.prehistoric.getAttackTarget();
		this.prehistoric.getLookHelper().setLookPositionWithEntity(entitylivingbase, 30.0F, 30.0F);
		double d0 = this.prehistoric.getDistanceSq(entitylivingbase.posX, entitylivingbase.getEntityBoundingBox().minY, entitylivingbase.posZ);
		double d1 = this.getAttackReachSqr(entitylivingbase);
		--this.delayCounter;

		if ((this.longMemory || this.prehistoric.getEntitySenses().canSee(entitylivingbase)) && this.delayCounter <= 0 && (this.targetX == 0.0D && this.targetY == 0.0D && this.targetZ == 0.0D || entitylivingbase.getDistanceSq(this.targetX, this.targetY, this.targetZ) >= 1.0D || this.prehistoric.getRNG().nextFloat() < 0.05F)) {
			this.targetX = entitylivingbase.posX;
			this.targetY = entitylivingbase.getEntityBoundingBox().minY;
			this.targetZ = entitylivingbase.posZ;
			this.delayCounter = 4 + this.prehistoric.getRNG().nextInt(7);

			if (this.canPenalize) {
				this.delayCounter += failedPathFindingPenalty;
				if (this.prehistoric.getNavigator().getPath() != null) {
					net.minecraft.pathfinding.PathPoint finalPathPoint = this.prehistoric.getNavigator().getPath().getFinalPathPoint();
					if (finalPathPoint != null && entitylivingbase.getDistanceSq(finalPathPoint.xCoord, finalPathPoint.yCoord, finalPathPoint.zCoord) < 1)
						failedPathFindingPenalty = 0;
					else
						failedPathFindingPenalty += 10;
				} else {
					failedPathFindingPenalty += 10;
				}
			}

			if (d0 > 1024.0D) {
				this.delayCounter += 10;
			} else if (d0 > 256.0D) {
				this.delayCounter += 5;
			}

			if (!this.prehistoric.getNavigator().tryMoveToEntityLiving(entitylivingbase, this.speedTowardsTarget) && !this.prehistoric.isMovementCeased()) {
				this.delayCounter += 15;
			}
		}

		this.attackTick = Math.max(this.attackTick - 1, 0);

		if (d0 <= d1 && this.attackTick <= 0) {
			this.attackTick = 20;
			this.prehistoric.swingArm(EnumHand.MAIN_HAND);
			this.prehistoric.attackEntityAsMob(entitylivingbase);
		}
	}

	protected double getAttackReachSqr(EntityLivingBase attackTarget) {
		return (double) (this.prehistoric.width * 2.0F * this.prehistoric.width * 2.0F + attackTarget.width);
	}
}