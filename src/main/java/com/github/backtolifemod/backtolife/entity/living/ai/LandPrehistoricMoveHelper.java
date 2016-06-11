package com.github.backtolifemod.backtolife.entity.living.ai;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityMoveHelper;

import com.github.backtolifemod.backtolife.entity.living.EntityLandPrehistoric;

public class LandPrehistoricMoveHelper extends EntityMoveHelper {

	public LandPrehistoricMoveHelper(EntityLiving entitylivingIn) {
		super(entitylivingIn);
	}

	public boolean isUpdating() {
		return this.action == EntityMoveHelper.Action.MOVE_TO || (this.entity instanceof EntityLandPrehistoric && !((EntityLandPrehistoric) this.entity).isMovementCeased());
	}
}
