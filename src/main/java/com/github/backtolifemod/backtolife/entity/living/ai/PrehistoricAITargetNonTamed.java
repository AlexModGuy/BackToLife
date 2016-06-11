package com.github.backtolifemod.backtolife.entity.living.ai;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;

import com.github.backtolifemod.backtolife.entity.living.EntityPrehistoric;
import com.google.common.base.Predicate;

import fossilsarcheology.api.FoodMappings;

public class PrehistoricAITargetNonTamed<T extends EntityLivingBase> extends EntityAINearestAttackableTarget<T> {
	private EntityPrehistoric prehistoric;

	public PrehistoricAITargetNonTamed(EntityPrehistoric entityIn, Class<T> classTarget, boolean checkSight, Predicate<? super T> targetSelector) {
		super(entityIn, classTarget, 10, checkSight, false, targetSelector);
		this.prehistoric = entityIn;
	}

	public boolean shouldExecute() {
		if(super.shouldExecute() && this.targetEntity != null && this.prehistoric.getHunger() < 100 && !this.targetEntity.getClass().equals(this.prehistoric.getClass())){	
			if(this.prehistoric.width >= this.targetEntity.width){
				if(this.targetEntity != prehistoric.getOwner() && FoodMappings.instance().getEntityFoodAmount(this.targetEntity.getClass(), this.prehistoric.type.dietType) > 0){
					return true;
				}
			}
		}
		return false;
	}
}