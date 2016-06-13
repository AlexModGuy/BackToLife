package com.github.backtolifemod.backtolife.entity.living.ai;

import net.minecraft.block.Block;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.math.BlockPos;

import com.github.backtolifemod.backtolife.entity.living.EntityLandPrehistoric;
import com.github.backtolifemod.backtolife.entity.living.EntityPrehistoric;

import fossilsarcheology.api.FoodMappings;

public class PrehistoricAIEatBlocks extends EntityAIBase {
	private BlockPos targetBlock;
	private EntityLandPrehistoric prehistoric;
	private double speed;

	public PrehistoricAIEatBlocks(EntityLandPrehistoric prehistoric, double speed) {
		super();
		this.prehistoric = prehistoric;
		this.speed = speed;
		this.setMutexBits(1);
	}

	@Override
	public boolean shouldExecute() {
		if (prehistoric.getHunger() >= 100) {
			return false;
		}
		if (prehistoric.isMovementCeased()) {
			return false;
		}
		if (prehistoric.getRNG().nextInt(20) != 0) {
			return false;
		}
		int radius = 16;
		for (int x = (int) (prehistoric.posX) - (radius / 2); x < (int) (prehistoric.posX) + (radius / 2); x++) {
			for (int y = (int) (prehistoric.posY) - (radius / 2); y < (int) (prehistoric.posY) + (radius / 2); y++) {
				for (int z = (int) (prehistoric.posZ) - (radius / 2); z < (int) (prehistoric.posZ) + (radius / 2); z++) {
					if (FoodMappings.instance().getBlockFoodAmount(prehistoric.worldObj.getBlockState(new BlockPos(x, y, z)).getBlock(), prehistoric.type.dietType) > 0) {
						targetBlock = new BlockPos(x, y, z);
						return true;
					}
				}
			}
		}
		if (prehistoric.getHunger() <= 100) {
			return false;
		}
		return false;
	}

	@Override
	public boolean continueExecuting() {
		if (targetBlock == null) {
			return false;
		}
		if (prehistoric.getHunger() >= 100) {

			return false;
		}
		if (prehistoric.isMovementCeased()) {
			return false;
		}
		return true;
	}

	@Override
	public void startExecuting() {
		if (targetBlock != null) {
			this.prehistoric.getNavigator().tryMoveToXYZ(this.targetBlock.getX(), this.targetBlock.getY(), this.targetBlock.getZ(), speed);
		}
		super.startExecuting();
	}

	@Override
	public void updateTask() {
		if (targetBlock != null) {
			Block block = prehistoric.worldObj.getBlockState(targetBlock).getBlock();
			if (FoodMappings.instance().getBlockFoodAmount(block, prehistoric.type.dietType) > 0) {
				double d0 = prehistoric.getDistance(this.targetBlock.getX(), this.targetBlock.getY(), this.targetBlock.getZ());
				if (d0 * d0 < 6) {
					if (EntityPrehistoric.ANIMATION_EAT != null && prehistoric.getAnimation() != EntityPrehistoric.ANIMATION_EAT) {
						prehistoric.setAnimation(EntityPrehistoric.ANIMATION_EAT);
					}
					prehistoric.setHunger(Math.min(100, prehistoric.getHunger() + FoodMappings.instance().getBlockFoodAmount(block, prehistoric.type.dietType)));
					prehistoric.setHealth(Math.min(prehistoric.getMaxHealth(), (int) (prehistoric.getHealth() + FoodMappings.instance().getBlockFoodAmount(block, prehistoric.type.dietType) / 10)));
					prehistoric.worldObj.destroyBlock(targetBlock, false);
					targetBlock = null;
					resetTask();
					return;
				}
			}
		}
	}

}
