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
import net.minecraft.entity.player.EntityPlayer;
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
import com.github.backtolifemod.backtolife.entity.living.ai.PrehistoricAILeapAtTarget;
import com.github.backtolifemod.backtolife.entity.living.ai.PrehistoricAILookIdle;
import com.github.backtolifemod.backtolife.entity.living.ai.PrehistoricAITargetNonTamed;
import com.github.backtolifemod.backtolife.entity.living.ai.PrehistoricAIWander;
import com.github.backtolifemod.backtolife.entity.living.ai.PrehistoricAIWatchClosest;
import com.github.backtolifemod.backtolife.enums.EnumPrehistoricType;
import com.google.common.base.Predicate;

public class EntityVelociraptor extends EntityLandPrehistoric {

	private int bloodTicks;
	private static final DataParameter<Boolean> BLOODY = EntityDataManager.<Boolean> createKey(EntityVelociraptor.class, DataSerializers.BOOLEAN);
	public static Animation ANIMATION_RIPAPART;
	private int attackTime = 0;
	public boolean isPouncing = false;

	public EntityVelociraptor(World worldIn) {
		super(worldIn, EnumPrehistoricType.VELOCIRAPTOR, 1, 5, 6, 18, 0.10000000149011612D, 0.4D);
		this.setSize(1.9F, 1.7F);
		this.maximumModelSize = 0.4F;
		this.minimumModelSize = 0.2F;
		EntityLandPrehistoric.ANIMATION_JUMP = Animation.create(30);
		EntityVelociraptor.ANIMATION_RIPAPART = Animation.create(20);
		EntityPrehistoric.ANIMATION_EAT = Animation.create(20);
		EntityLandPrehistoric.ANIMATION_SPEAK = Animation.create(20);
		this.obligitate_prey.add(EntityProtoceratops.class);
	}

	@Override
	protected void initEntityAI() {
		this.tasks.addTask(1, new EntityAISwimming(this));
		this.tasks.addTask(2, this.aiSit = new EntityAISit(this));
		this.tasks.addTask(3, new PrehistoricAILeapAtTarget(this, 0.5F));
		this.tasks.addTask(4, new PrehistoricAIAttackMelee(this, 1.5D, true));
		this.tasks.addTask(5, new PrehistoricAIWander(this, 1.0D));
		this.tasks.addTask(6, new PrehistoricAIWatchClosest(this, EntityLivingBase.class, 6.0F));
		this.tasks.addTask(7, new PrehistoricAILookIdle(this));
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
		this.dataManager.register(BLOODY, Boolean.valueOf(false));
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
		if(!this.isDead){
			if (this.isBloody() && this.bloodTicks < 1200) {
				this.bloodTicks++;
			}
			if (this.isBloody() && this.bloodTicks >= 1200) {
				this.setBloody(false);
				this.bloodTicks = 0;
			}
			if (this.getRidingEntity() != null && this.getRidingEntity() instanceof EntityLivingBase) {
				if (this.getAnimation() != ANIMATION_RIPAPART) {
					this.setAnimation(ANIMATION_RIPAPART);
				}
				attackTime++;
				if (attackTime < 80) {
					if (this.getRidingEntity().width < this.width) {
						((EntityLivingBase) this.getRidingEntity()).deathTime = 10;
					}
					if (attackTime % 40 == 0) {
						this.setBloody(true);
						((EntityLivingBase) this.getRidingEntity()).attackEntityFrom(DamageSource.causeMobDamage(this), ((int) this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue()));
					}
					if (attackTime % 10 == 0) {
						this.playSound(ModSounds.velociraptor_attack, this.getSoundVolume(), this.getSoundPitch());
					}
				} else {
					this.dismountRidingEntity();
					attackTime = 0;
				}

			} else if (!this.worldObj.isRemote && this.getAttackTarget() != null && this.getRidingEntity() == null) {
				if (this.getAnimation() != ANIMATION_JUMP) {
					this.setAnimation(ANIMATION_JUMP);
					this.playSound(ModSounds.velociraptor_threat, this.getSoundVolume(), this.getSoundPitch());
				}
				double distance = this.getDistanceSqToEntity(this.getAttackTarget());
				if (this.getAnimationTick() > 10 && this.getAnimationTick() < 12 && distance >= 4.0D && distance <= 16.0D) {
					double d0 = this.getAttackTarget().posX - this.posX;
					double d1 = this.getAttackTarget().posZ - this.posZ;
					float f = MathHelper.sqrt_double(d0 * d0 + d1 * d1);
					this.motionX += d0 / f * 0.5D * 0.800000011920929D + this.motionX * 0.20000000298023224D;
					this.motionZ += d1 / f * 0.5D * 0.800000011920929D + this.motionZ * 0.20000000298023224D;
					this.motionY = 0.4D;
					isPouncing = false;
				} else {
					isPouncing = true;
				}
			}

			if (this.getRidingEntity()== null && this.getAttackTarget() != null && this.getAttackTarget().isDead) {
				this.setAttackTarget(null);
			}
		}
	}

	@Override
	public boolean attackEntityAsMob(Entity entityIn) {
		if (entityIn instanceof EntityLivingBase && this.getAnimationTick() > 10 && entityIn.isBeingRidden()) {
			this.startRiding(entityIn, false);
		}
		return true;
	}

	@Override
	public double getYOffset() {
		return -(double) this.height * 0.25D;
	}

	@Override
	public boolean isMovementCeased() {
		return this.isSleeping() || this.isSitting() || this.getAnimation() == EntityLandPrehistoric.ANIMATION_JUMP || isPouncing;
	}

	@Override
	public String getTexture() {
		if (this.isSleeping()) {
			return "backtolife:textures/models/entity/" + this.type.toString().toLowerCase() + (this.isMale() ? "_male" : "_female") + (this.isSleeping() ? "_sleeping" : "");
		} else {
			return super.getTexture() + (this.isBloody() ? "_blood" : "");
		}
	}

	@Override
	protected void collideWithEntity(Entity entityIn) {
		if (this.getAttackTarget() != null && this.getAttackTarget() == entityIn && this.getAnimationTick() > 10) {
			if (this.width > entityIn.width || this.obligitate_prey.contains(entityIn.getClass())) {
				this.startRiding(entityIn);
			} else if(entityIn instanceof EntityPlayer){
				this.setBloody(true);
				((EntityPlayer) entityIn).attackEntityFrom(DamageSource.causeMobDamage(this), ((int) this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue()));
			}
		}
		entityIn.applyEntityCollision(this);
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return ModSounds.velociraptor_idle;
	}

	@Override
	protected SoundEvent getHurtSound() {
		return ModSounds.velociraptor_hurt;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return ModSounds.velociraptor_death;
	}

	@Override
	public void onEatFood(ItemStack stack) {
		this.setBloody(true);
	}

	@Override
	public Animation[] getAnimations() {
		return new Animation[] { NO_ANIMATION, ANIMATION_JUMP, ANIMATION_RIPAPART, ANIMATION_EAT };
	}
}
