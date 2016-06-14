package com.github.backtolifemod.backtolife.entity.living;

import javax.annotation.Nullable;

import net.ilexiconn.llibrary.server.animation.Animation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAIOwnerHurtByTarget;
import net.minecraft.entity.ai.EntityAIOwnerHurtTarget;
import net.minecraft.entity.ai.EntityAISit;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import com.github.backtolifemod.backtolife.core.ModSounds;
import com.github.backtolifemod.backtolife.entity.living.ai.PrehistoricAIAttackMelee;
import com.github.backtolifemod.backtolife.entity.living.ai.PrehistoricAIHuntItems;
import com.github.backtolifemod.backtolife.entity.living.ai.PrehistoricAILookIdle;
import com.github.backtolifemod.backtolife.entity.living.ai.PrehistoricAITargetNonTamed;
import com.github.backtolifemod.backtolife.entity.living.ai.PrehistoricAIWander;
import com.github.backtolifemod.backtolife.entity.living.ai.PrehistoricAIWatchClosest;
import com.github.backtolifemod.backtolife.enums.EnumPrehistoricType;
import com.google.common.base.Predicate;

public class EntityTarbosaurus extends EntityLandPrehistoric {

	private int bloodTicks;
	private static final DataParameter<Boolean> BLOODY = EntityDataManager.<Boolean> createKey(EntityLandPrehistoric.class, DataSerializers.BOOLEAN);
	public static Animation ANIMATION_ATTACK;
	public static Animation ANIMATION_SHAKE;

	public EntityTarbosaurus(World worldIn) {
		super(worldIn, EnumPrehistoricType.TARBOSAURUS, 1, 15, 10, 160, 0.10000000149011612D, 0.4D);
		this.setSize(1.9F, 1.7F);
		this.maximumModelSize = 2.4F;
		this.minimumModelSize = 0.2F;
		EntityTarbosaurus.ANIMATION_ATTACK = Animation.create(10);
		EntityTarbosaurus.ANIMATION_SHAKE = Animation.create(65);
		EntityPrehistoric.ANIMATION_EAT = Animation.create(20);
		EntityLandPrehistoric.ANIMATION_SPEAK = Animation.create(20);
	}

	@Override
	protected void initEntityAI() {
		this.tasks.addTask(1, new EntityAISwimming(this));
		this.tasks.addTask(2, this.aiSit = new EntityAISit(this));
		this.tasks.addTask(3, new PrehistoricAIAttackMelee(this, 1.5D, true));
		this.tasks.addTask(4, new PrehistoricAIWander(this, 1.0D));
		this.tasks.addTask(5, new PrehistoricAIWatchClosest(this, EntityLivingBase.class, 6.0F));
		this.tasks.addTask(6, new PrehistoricAILookIdle(this));
		this.targetTasks.addTask(1, new EntityAIOwnerHurtByTarget(this));
		this.targetTasks.addTask(2, new EntityAIOwnerHurtTarget(this));
		this.targetTasks.addTask(3, new EntityAIHurtByTarget(this, true, new Class[0]));
		this.targetTasks.addTask(4, new PrehistoricAITargetNonTamed(this, EntityLivingBase.class, false, new Predicate<Entity>() {
			@Override
			public boolean apply(@Nullable Entity entity) {
				return entity instanceof EntityLivingBase;
			}
		}));
		this.targetTasks.addTask(5, new PrehistoricAIHuntItems(this, false));
	}

	@Override
	public int getGrownAge() {
		return 5;
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		//this.dataManager.register(BLOODY, Boolean.valueOf(false));
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound compound) {
		super.writeEntityToNBT(compound);
		compound.setBoolean("Bloody", this.isBloody());
		compound.setInteger("BloodTicks", bloodTicks);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound compound) {
		super.readEntityFromNBT(compound);
		this.setBloody(compound.getBoolean("Bloody"));
		bloodTicks = compound.getInteger("BloodTicks");
	}

	public void setBloody(boolean bloody) {
		this.dataManager.set(BLOODY, Boolean.valueOf(bloody));
	}

	public boolean isBloody() {
		return this.dataManager.get(BLOODY).booleanValue();
	}

	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		if (!this.isDead) {
			if (this.isBloody() && this.bloodTicks < 1200) {
				this.bloodTicks++;
			}
			if (this.isBloody() && this.bloodTicks >= 1200) {
				this.setBloody(false);
				this.bloodTicks = 0;
			}
		}
		if (this.getAttackTarget() != null && this.getPassengers().contains(this.getAttackTarget())) {
			this.playSound(SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, this.getSoundVolume(), this.getSoundPitch());
		}
		if (this.getAnimation() == EntityTarbosaurus.ANIMATION_ATTACK && this.getAnimationTick() > 5 && this.getAttackTarget() != null && this.getDistanceSqToEntity(this.getAttackTarget()) < (5 * this.getRenderSize()) && !this.getPassengers().contains(this.getAttackTarget())) {
			this.getAttackTarget().attackEntityFrom(DamageSource.causeMobDamage(this), ((int) this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue()));
		}
		if (this.getAnimation() == EntityTarbosaurus.ANIMATION_SHAKE && this.getAnimationTick() > 10 && this.getAttackTarget() != null && this.getDistanceSqToEntity(this.getAttackTarget()) < (5 * this.getRenderSize())) {
			this.getAttackTarget().startRiding(this);
		}
	}

	@Override
	public void updatePassenger(Entity passenger) {
		if(passenger instanceof EntityLivingBase){
			if (this.isPassenger(passenger)) {
				if(this.getAnimationTick() > 55 && this.getAttackTarget() != null){
					this.getAttackTarget().attackEntityFrom(DamageSource.causeMobDamage(this), this.getAttackTarget().getMaxHealth());
					this.onKillEntity(this.getAttackTarget());
					this.setBloody(true);
				}
				passenger.setPosition(this.posX, this.posY + this.getMountedYOffset() + passenger.getYOffset(), this.posZ);
				float modTick_0 = this.getAnimationTick() - 15;
				float modTick_1 = this.getAnimationTick() > 15 ? 5 * MathHelper.sin((float) (Math.PI + (modTick_0 * 0.275F))) : 0;
				float modTick_2 = this.getAnimationTick() > 15 ? 15 : this.getAnimationTick();
				this.rotationYaw *= 0;
				passenger.rotationYaw = this.rotationYaw + this.rotationYawHead + 180;
				rotationYaw = renderYawOffset;
				float radius = 0.9F * (0.7F * getRenderSize()) * -3;
				float angle = (0.01745329251F * this.renderYawOffset) + 3.15F + (modTick_1 * 1.75F) * 0.05F;
				double extraX = (double) (radius * MathHelper.sin((float) (Math.PI + angle)));
				double extraZ = (double) (radius * MathHelper.cos(angle));
				double extraY = 0.7F * (getRenderSize() + (modTick_1 * 0.05) + (modTick_2 * 0.15) - 2);
				passenger.setPosition(this.posX + extraX, this.posY + extraY, this.posZ + extraZ);				
			}
			
		}
	}

	@Override
	public boolean attackEntityAsMob(Entity entityIn) {
		if (this.getAnimation() != EntityTarbosaurus.ANIMATION_ATTACK && this.getAnimation() != EntityTarbosaurus.ANIMATION_SHAKE) {
			this.setAnimation(this.getRNG().nextInt(3) == 0 ? entityIn.width < 2.5 ? EntityTarbosaurus.ANIMATION_SHAKE : EntityTarbosaurus.ANIMATION_ATTACK : EntityTarbosaurus.ANIMATION_ATTACK);
		}
		return true;
	}

	@Override
	public double getYOffset() {
		return -(double) this.height * 0.25D;
	}

	@Override
	public String getTexture() {
		if (this.isSleeping()) {
			return "backtolife:textures/models/entity/tarbosaurus" + (this.isSleeping() ? "_sleeping" : "");
		} else {
			return "backtolife:textures/models/entity/tarbosaurus" + (this.isBloody() ? "_blood" : "");
		}
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return ModSounds.tarbosaurus_idle;
	}

	@Override
	protected SoundEvent getHurtSound() {
		return ModSounds.tarbosaurus_hurt;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return ModSounds.tarbosaurus_death;
	}

	@Override
	public void onEatFood(ItemStack stack) {
		this.setBloody(true);
	}

	@Override
	public Animation[] getAnimations() {
		return new Animation[] { NO_ANIMATION, ANIMATION_ATTACK, ANIMATION_SHAKE, ANIMATION_EAT };
	}
}
