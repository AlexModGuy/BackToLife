package com.github.backtolifemod.backtolife.entity.living.ai;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAITarget;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;

import com.github.backtolifemod.backtolife.entity.living.EntityPrehistoric;
import com.google.common.base.Predicate;

import fossilsarcheology.api.FoodMappings;

public class PrehistoricAIHuntItems<T extends EntityItem> extends EntityAITarget {
	private final int targetChance;
	protected final PrehistoricAIHuntItems.Sorter theNearestAttackableTargetSorter;
	protected final Predicate<? super EntityItem> targetEntitySelector;
	protected EntityItem targetEntity;

	public PrehistoricAIHuntItems(EntityCreature creature, boolean checkSight) {
		this(creature, checkSight, false);
	}

	public PrehistoricAIHuntItems(EntityCreature creature, boolean checkSight, boolean onlyNearby) {
		this(creature, 10, checkSight, onlyNearby, (Predicate<? super EntityItem>) null);
	}

	public PrehistoricAIHuntItems(EntityCreature creature, int chance, boolean checkSight, boolean onlyNearby, @Nullable final Predicate<? super T> targetSelector) {
		super(creature, checkSight, onlyNearby);
		this.targetChance = chance;
		this.theNearestAttackableTargetSorter = new PrehistoricAIHuntItems.Sorter(creature);
		this.setMutexBits(1);
		this.targetEntitySelector = new Predicate<EntityItem>() {
			@Override
			public boolean apply(@Nullable EntityItem item) {
				return item instanceof EntityItem && item.getEntityItem() != null && item.getEntityItem().getItem() != null && FoodMappings.instance().getItemFoodAmount(item.getEntityItem().getItem(), ((EntityPrehistoric)PrehistoricAIHuntItems.this.taskOwner).type.dietType) > 0;
			}
		};
	}

	@Override
	public boolean shouldExecute() {
		if(((EntityPrehistoric)this.taskOwner).getHunger() >= 100){
			return false;
		}
		if(((EntityPrehistoric)this.taskOwner).isMovementCeased()){
			return false;
		}
		if (this.targetChance > 0 && this.taskOwner.getRNG().nextInt(this.targetChance) != 0) {
			return false;
		} else {
			List<EntityItem> list = this.taskOwner.worldObj.<EntityItem> getEntitiesWithinAABB(EntityItem.class, this.getTargetableArea(this.getTargetDistance()), this.targetEntitySelector);

			if (list.isEmpty()) {
				return false;
			} else {
				Collections.sort(list, this.theNearestAttackableTargetSorter);
				this.targetEntity = list.get(0);
				return true;
			}
		}
	}

	protected AxisAlignedBB getTargetableArea(double targetDistance) {
		return this.taskOwner.getEntityBoundingBox().expand(targetDistance, 4.0D, targetDistance);
	}

	@Override
	public void startExecuting() {
		this.taskOwner.getNavigator().tryMoveToXYZ(this.targetEntity.posX, this.targetEntity.posY, this.targetEntity.posZ, 1);
		super.startExecuting();
	}

	@Override
	public void updateTask() {
		super.updateTask();
		if(this.targetEntity == null || this.targetEntity != null && this.targetEntity.isDead){
			this.resetTask();
		}
		if(this.targetEntity != null && !this.targetEntity.isDead && this.taskOwner.getDistanceSqToEntity(this.targetEntity) < 1){
			this.targetEntity.getEntityItem().stackSize--;
			this.taskOwner.playSound(SoundEvents.ENTITY_GENERIC_EAT, 1, 1);
			int hunger = FoodMappings.instance().getItemFoodAmount(this.targetEntity.getEntityItem().getItem(), ((EntityPrehistoric)this.taskOwner).type.dietType);
			((EntityPrehistoric)this.taskOwner).setHunger(Math.min(100, ((EntityPrehistoric)this.taskOwner).getHunger() + hunger));
			((EntityPrehistoric)this.taskOwner).onEatFood(this.targetEntity.getEntityItem());
			this.taskOwner.setHealth(Math.min(this.taskOwner.getMaxHealth(), (int) (this.taskOwner.getHealth() + FoodMappings.instance().getItemFoodAmount(this.targetEntity.getEntityItem().getItem(), ((EntityPrehistoric)this.taskOwner).type.dietType) / 10)));
			if(EntityPrehistoric.ANIMATION_EAT != null){
				((EntityPrehistoric)this.taskOwner).setAnimation(EntityPrehistoric.ANIMATION_EAT);
			}
			for(int i = 0; i < 4; i++){
				((EntityPrehistoric)this.taskOwner).spawnItemCrackParticles(this.targetEntity.getEntityItem().getItem());
			}
			resetTask();
		}
	}

	@Override
	public boolean continueExecuting() {
		return !this.taskOwner.getNavigator().noPath();
	}

	public static class Sorter implements Comparator<Entity> {
		private final Entity theEntity;

		public Sorter(Entity theEntityIn) {
			this.theEntity = theEntityIn;
		}

		@Override
		public int compare(Entity entity_1, Entity entity_2) {
			double d0 = this.theEntity.getDistanceSqToEntity(entity_1);
			double d1 = this.theEntity.getDistanceSqToEntity(entity_2);
			return d0 < d1 ? -1 : (d0 > d1 ? 1 : 0);
		}
	}
}