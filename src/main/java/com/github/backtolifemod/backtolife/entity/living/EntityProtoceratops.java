package com.github.backtolifemod.backtolife.entity.living;

import net.ilexiconn.llibrary.server.animation.Animation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAIOwnerHurtByTarget;
import net.minecraft.entity.ai.EntityAISit;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

import com.github.backtolifemod.backtolife.core.ModSounds;
import com.github.backtolifemod.backtolife.entity.living.ai.PrehistoricAIAttackMelee;
import com.github.backtolifemod.backtolife.entity.living.ai.PrehistoricAIEatBlocks;
import com.github.backtolifemod.backtolife.entity.living.ai.PrehistoricAIHuntItems;
import com.github.backtolifemod.backtolife.entity.living.ai.PrehistoricAILookIdle;
import com.github.backtolifemod.backtolife.entity.living.ai.PrehistoricAIWander;
import com.github.backtolifemod.backtolife.entity.living.ai.PrehistoricAIWatchClosest;
import com.github.backtolifemod.backtolife.enums.EnumPrehistoricType;

public class EntityProtoceratops extends EntityLandPrehistoric{

	public static Animation ANIMATION_THROWOFF;
	public static Animation ANIMATION_ATTACK;

	public EntityProtoceratops(World world) {
		super(world, EnumPrehistoricType.PROTOCERATOPS, 1, 2, 8, 25, 0.10000000149011612D, 0.3D);
		this.setSize(1.9F, 1.7F);
		ANIMATION_EAT = Animation.create(35);
		ANIMATION_SPEAK = Animation.create(20);
		ANIMATION_THROWOFF = Animation.create(30);
		ANIMATION_ATTACK = Animation.create(15);
		EntityLandPrehistoric.obligitate_predators.add(EntityVelociraptor.class);
	}

	@Override
	protected void initEntityAI() {
		this.tasks.addTask(1, new EntityAISwimming(this));
		this.tasks.addTask(2, this.aiSit = new EntityAISit(this));
		this.tasks.addTask(3, new PrehistoricAIEatBlocks(this, 1.0D));
		this.tasks.addTask(4, new PrehistoricAIAttackMelee(this, 1.5D, true));
		this.tasks.addTask(5, new PrehistoricAIWander(this, 1.0D));
		this.tasks.addTask(6, new PrehistoricAIWatchClosest(this, EntityLivingBase.class, 6.0F));
		this.tasks.addTask(7, new PrehistoricAILookIdle(this));
		this.targetTasks.addTask(1, new EntityAIOwnerHurtByTarget(this));
		this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, true, new Class[0]));
		this.targetTasks.addTask(3, new PrehistoricAIHuntItems(this, false));
	}

	@Override
	public int getGrownAge() {
		return 7;
	}

	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();

		if (!this.getPassengers().isEmpty() && this.getAgeInTicks() % 60 == 0) {
			if (this.getAnimation() != ANIMATION_THROWOFF) {
				this.setAnimation(ANIMATION_THROWOFF);
				this.playSound(ModSounds.protoceratops_attack, this.getSoundVolume(), this.getSoundPitch());
			}
		}
		if (this.getAnimation() == ANIMATION_THROWOFF && !this.getPassengers().isEmpty() && this.getAnimationTick() == 15) {
			for(Entity passanger : this.getPassengers()){
				passanger.attackEntityFrom(DamageSource.causeMobDamage(this), (float) this.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue());
			}
			this.removePassengers();
		}
	}

	@Override
	public boolean attackEntityAsMob(Entity entityIn) {
		if (this.getAnimation() != ANIMATION_ATTACK && this.getAnimation() != ANIMATION_THROWOFF) {
			this.setAnimation(ANIMATION_ATTACK);
			this.playSound(ModSounds.protoceratops_attack, this.getSoundVolume(), this.getSoundPitch());
		}
		entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), (float) this.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue());
		return true;
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return ModSounds.protoceratops_idle;
	}

	@Override
	protected SoundEvent getHurtSound() {
		return ModSounds.protoceratops_hurt;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return ModSounds.protoceratops_death;
	}
	
	@Override
	public Animation[] getAnimations() {
		return new Animation[] { NO_ANIMATION, ANIMATION_JUMP, ANIMATION_THROWOFF, ANIMATION_ATTACK, ANIMATION_EAT };
	}
}
