package com.github.backtolifemod.backtolife.entity.living;

import javax.annotation.Nullable;

import net.ilexiconn.llibrary.server.animation.Animation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAIOwnerHurtByTarget;
import net.minecraft.entity.ai.EntityAIOwnerHurtTarget;
import net.minecraft.entity.ai.EntityAISit;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITargetNonTamed;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.World;

import com.github.backtolifemod.backtolife.entity.living.ai.PrehistoricAIAttackMelee;
import com.github.backtolifemod.backtolife.entity.living.ai.PrehistoricAILeapAtTarget;
import com.github.backtolifemod.backtolife.entity.living.ai.PrehistoricAILookIdle;
import com.github.backtolifemod.backtolife.entity.living.ai.PrehistoricAIWander;
import com.github.backtolifemod.backtolife.entity.living.ai.PrehistoricAIWatchClosest;
import com.github.backtolifemod.backtolife.enums.EnumPrehistoricType;
import com.github.backtolifemod.backtolife.enums.EnumPrehistoricType.EnumPrehistoricDietType;
import com.google.common.base.Predicate;

public class EntityVelociraptor extends EntityLandPrehistoric {

	private int bloodTicks;
	private static final DataParameter<Boolean> BLOODY = EntityDataManager.<Boolean> createKey(EntityVelociraptor.class, DataSerializers.BOOLEAN);
	public static Animation ANIMATION_RIPAPART;

	public EntityVelociraptor(World worldIn) {
		super(worldIn, EnumPrehistoricType.VELOCIRAPTOR, 1, 3, 6, 18, 0.10000000149011612D, 0.4D, EnumPrehistoricDietType.CARNIVORE);
		this.setSize(1.9F, 1.7F);
		this.maximumModelSize = 0.6F;
		this.minimumModelSize = 0.2F;
		this.ANIMATION_JUMP = Animation.create(30);
		this.ANIMATION_RIPAPART = Animation.create(20);
	}

	protected void initEntityAI() {
		this.tasks.addTask(1, new EntityAISwimming(this));
		this.tasks.addTask(2, this.aiSit = new EntityAISit(this));
		this.tasks.addTask(3, new PrehistoricAILeapAtTarget(this, 0.5F));
		this.tasks.addTask(4, new PrehistoricAIAttackMelee(this, 1.0D, true));
		this.tasks.addTask(5, new PrehistoricAIWander(this, 1.0D));
		this.tasks.addTask(6, new PrehistoricAIWatchClosest(this, EntityLivingBase.class, 6.0F));
		this.tasks.addTask(7, new PrehistoricAILookIdle(this));
		this.targetTasks.addTask(1, new EntityAIOwnerHurtByTarget(this));
		this.targetTasks.addTask(2, new EntityAIOwnerHurtTarget(this));
		this.targetTasks.addTask(3, new EntityAIHurtByTarget(this, true, new Class[0]));
		this.targetTasks.addTask(4, new EntityAITargetNonTamed(this, EntityAnimal.class, false, new Predicate<Entity>() {
			public boolean apply(@Nullable Entity entity) {
				return entity instanceof EntitySheep || entity instanceof EntityRabbit;
			}
		}));
	}

	@Override
	public int getGrownAge() {
		return 5;
	}

	protected void entityInit() {
		super.entityInit();
		this.dataManager.register(BLOODY, Boolean.valueOf(false));
	}

	public void writeEntityToNBT(NBTTagCompound compound) {
		super.writeEntityToNBT(compound);
		compound.setBoolean("Bloody", this.isBloody());
		compound.setInteger("BloodTicks", bloodTicks);
	}

	public void readEntityFromNBT(NBTTagCompound compound) {
		super.readEntityFromNBT(compound);
		this.setBloody(compound.getBoolean("Bloody"));
		bloodTicks = compound.getInteger("BloodTicks");
	}

	public void setBloody(boolean bloody) {
		this.dataManager.set(BLOODY, Boolean.valueOf(bloody));
	}

	public boolean isBloody() {
		return ((Boolean) this.dataManager.get(BLOODY)).booleanValue();
	}

	public void onLivingUpdate() {
		super.onLivingUpdate();
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
			((EntityLivingBase)this.getRidingEntity()).deathTime = 10;
			if(this.getRNG().nextInt(60) == 0){
				this.getRidingEntity().setDead();
			}
			if (!this.isBloody()) {
				this.setBloody(true);
			}

		} else if (!this.worldObj.isRemote && this.getAttackTarget() != null && this.getRidingEntity() == null) {
			if (this.getAnimation() != ANIMATION_JUMP) {
				this.setAnimation(ANIMATION_JUMP);
			}
		}
	}

	public boolean attackEntityAsMob(Entity entityIn) {
		// boolean flag =
		// entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), (float)
		// ((int)
		// this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue()));

		// if (flag) {
		// this.applyEnchantments(this, entityIn);
		// }

		if (entityIn.width < this.width && entityIn instanceof EntityLivingBase && this.getAnimationTick() > 10 && entityIn.getPassengers().isEmpty()) {
			this.startRiding(entityIn, true);
		}

		return false;
	}

	public double getYOffset() {
		return -(double) this.height * 0.25D;
	}

	public boolean isMovementCeased() {
		return this.isSleeping() || this.isSitting() || this.getAnimation() == this.ANIMATION_JUMP;
	}

	public String getTexture() {
		if (this.isSleeping()) {
			return "backtolife:textures/models/entity/" + this.type.toString().toLowerCase() + (this.isMale() ? "_male" : "_female") + (this.isSleeping() ? "_sleeping" : "");
		} else {
			return super.getTexture() + (this.isBloody() ? "_blood" : "");
		}
	}

	@Override
	public Animation[] getAnimations() {
		return new Animation[] { NO_ANIMATION, ANIMATION_JUMP, ANIMATION_RIPAPART };
	}
}
