package com.github.backtolifemod.backtolife.entity.living.ai;

import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.util.math.Vec3d;

import com.github.backtolifemod.backtolife.entity.living.EntityPrehistoric;

public class PrehistoricAIWander extends EntityAIBase {
	private EntityPrehistoric entity;
	private double xPosition;
	private double yPosition;
	private double zPosition;
	private double speed;
	private int executionChance;
	private boolean mustUpdate;

	public PrehistoricAIWander(EntityPrehistoric creatureIn, double speedIn) {
		this(creatureIn, speedIn, 120);
	}

	public PrehistoricAIWander(EntityPrehistoric creatureIn, double speedIn, int chance) {
		this.entity = creatureIn;
		this.speed = speedIn;
		this.executionChance = chance;
		this.setMutexBits(1);
	}

	public boolean shouldExecute() {
		if(entity.isMovementCeased()){
			return false;
		}
		if (!this.mustUpdate) {
			if (this.entity.getAge() >= 100) {
				return false;
			}

			if (this.entity.getRNG().nextInt(this.executionChance) != 0) {
				return false;
			}
		}

		Vec3d vec3d = RandomPositionGenerator.findRandomTarget(this.entity, 10, 7);

		if (vec3d == null) {
			return false;
		} else {
			this.xPosition = vec3d.xCoord;
			this.yPosition = vec3d.yCoord;
			this.zPosition = vec3d.zCoord;
			this.mustUpdate = false;
			return true;
		}
	}

	public boolean continueExecuting() {
		return !this.entity.getNavigator().noPath();
	}

	public void startExecuting() {
		this.entity.getNavigator().tryMoveToXYZ(this.xPosition, this.yPosition, this.zPosition, this.speed);
	}

	public void makeUpdate() {
		this.mustUpdate = true;
	}

	public void setExecutionChance(int newchance) {
		this.executionChance = newchance;
	}
}