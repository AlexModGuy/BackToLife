package com.github.backtolifemod.backtolife.entity.living;

import javax.annotation.Nullable;

import net.ilexiconn.llibrary.server.animation.Animation;
import net.ilexiconn.llibrary.server.animation.AnimationHandler;
import net.ilexiconn.llibrary.server.animation.IAnimatedEntity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

import com.github.backtolifemod.backtolife.enums.EnumPrehistoricType;

public abstract class EntityPrehistoric extends EntityTameable implements IAnimatedEntity {

	public EnumPrehistoricType type;
	private Animation currentAnimation;
	private int animationTicks;
	protected float maximumModelSize;
	protected float minimumModelSize;
	private static final DataParameter<Integer> HUNGER = EntityDataManager.<Integer> createKey(EntityPrehistoric.class, DataSerializers.VARINT);
	private static final DataParameter<Integer> AGE_TICKS = EntityDataManager.<Integer> createKey(EntityPrehistoric.class, DataSerializers.VARINT);
	private static final DataParameter<Boolean> GENDER = EntityDataManager.<Boolean> createKey(EntityPrehistoric.class, DataSerializers.BOOLEAN);
	public double minimumDamage;
	public double maximumDamage;
	public double minimumHealth;
	public double maximumHealth;
	public double minimumSpeed;
	public double maximumSpeed;

	public EntityPrehistoric(World world, EnumPrehistoricType type, double minimumDamage, double maximumDamage, double minimumHealth, double maximumHealth, double minimumSpeed, double maximumSpeed) {
		super(world);
		this.type = type;
		this.minimumDamage = minimumDamage;
		this.maximumDamage = maximumDamage;
		this.minimumHealth = minimumHealth;
		this.maximumHealth = maximumHealth;
		this.minimumSpeed = minimumSpeed;
		this.maximumSpeed = maximumSpeed;
		updateSize();
	}

	protected void entityInit() {
		super.entityInit();
		this.dataManager.register(HUNGER, Integer.valueOf(0));
		this.dataManager.register(AGE_TICKS, Integer.valueOf(0));
		this.dataManager.register(GENDER, Boolean.valueOf(false));
	}

	public void writeEntityToNBT(NBTTagCompound compound) {
		super.writeEntityToNBT(compound);
		compound.setInteger("Hunger", this.getHunger());
		compound.setInteger("AgeTicks", this.getAgeInTicks());
		compound.setBoolean("Gender", this.isMale());
	}

	protected void onDeathUpdate() {
		++this.deathTime;

		if (this.deathTime == 80) {
			if (!this.worldObj.isRemote && (this.isPlayer() || this.recentlyHit > 0 && this.canDropLoot() && this.worldObj.getGameRules().getBoolean("doMobLoot"))) {
				int i = this.getExperiencePoints(this.attackingPlayer);
				i = net.minecraftforge.event.ForgeEventFactory.getExperienceDrop(this, this.attackingPlayer, i);
				while (i > 0) {
					int j = EntityXPOrb.getXPSplit(i);
					i -= j;
					this.worldObj.spawnEntityInWorld(new EntityXPOrb(this.worldObj, this.posX, this.posY, this.posZ, j));
				}
			}

			this.setDead();

			for (int k = 0; k < 20; ++k) {
				double d2 = this.rand.nextGaussian() * 0.02D;
				double d0 = this.rand.nextGaussian() * 0.02D;
				double d1 = this.rand.nextGaussian() * 0.02D;
				this.worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, this.posX + (double) (this.rand.nextFloat() * this.width * 2.0F) - (double) this.width, this.posY + (double) (this.rand.nextFloat() * this.height), this.posZ + (double) (this.rand.nextFloat() * this.width * 2.0F) - (double) this.width, d2, d0, d1, new int[0]);
			}
		}
	}

	public void readEntityFromNBT(NBTTagCompound compound) {
		super.readEntityFromNBT(compound);
		this.setHunger(compound.getInteger("Hunger"));
		this.setAgeInTicks(compound.getInteger("AgeTicks"));
		this.setGender(compound.getBoolean("Gender"));

	}

	@Override
	public EntityAgeable createChild(EntityAgeable ageable) {
		return null;
	}

	public void onUpdate() {
		this.setScale(getRenderSize());
		super.onUpdate();
		AnimationHandler.INSTANCE.updateAnimations(this);
	}

	public void onLivingUpdate() {
		super.onLivingUpdate();
		this.setAgeInTicks(this.getAgeInTicks() + 1);
		if (this.getAgeInTicks() % 24000 == 0) {
			this.updateSize();
		}
	}

	@Override
	public void setScaleForAge(boolean par1) {
		this.setScale(this.getRenderSize());
	}

	public float getRenderSize() {
		float step = (this.maximumModelSize - this.minimumModelSize) / ((this.getGrownAge() * 24000) + 1);

		if (this.getAgeInTicks() > this.getGrownAge() * 24000) {
			return this.minimumModelSize + ((step) * this.getGrownAge() * 24000);
		}
		return this.minimumModelSize + ((step * this.getAgeInTicks()));
	}

	@Nullable
	public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
		livingdata = super.onInitialSpawn(difficulty, livingdata);
		this.setGender(this.getRNG().nextBoolean());
		this.setAgeInDays(this.getGrownAge());
		this.setHunger(50);
		this.updateSize();
		return livingdata;
	}

	public String getTexture() {
		return "backtolife:textures/models/entity/" + this.type.toString().toLowerCase() + (this.isMale() ? "_male" : "_female") + ".png";
	}

	@Override
	public int getAnimationTick() {
		return animationTicks;
	}

	@Override
	public void setAnimationTick(int tick) {
		animationTicks = tick;
	}

	@Override
	public Animation getAnimation() {
		return currentAnimation;
	}

	@Override
	public void setAnimation(Animation animation) {
		currentAnimation = animation;
	}

	@Override
	public Animation[] getAnimations() {
		return new Animation[]{};
	}

	public int getHunger() {
		return ((Integer) this.dataManager.get(HUNGER)).intValue();
	}

	public void setHunger(int hunger) {
		this.dataManager.set(HUNGER, Integer.valueOf(hunger));
	}

	public int getAgeInDays() {
		return ((Integer) this.dataManager.get(AGE_TICKS)).intValue() / 24000;
	}

	public void setAgeInDays(int age) {
		this.dataManager.set(AGE_TICKS, Integer.valueOf(age * 24000));
	}

	public int getAgeInTicks() {
		return ((Integer) this.dataManager.get(AGE_TICKS)).intValue();
	}

	public void setAgeInTicks(int age) {
		this.dataManager.set(AGE_TICKS, Integer.valueOf(age));
	}

	public void setGender(boolean male) {
		this.dataManager.set(GENDER, Boolean.valueOf(male));
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.30000001192092896D);
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);
		getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
		getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(1.0D);
	}

	public void updateSize() {
		double healthStep = (maximumHealth - minimumHealth) / (this.getGrownAge() + 1);
		double attackStep = (maximumDamage - minimumDamage) / (this.getGrownAge() + 1);
		double speedStep = (maximumSpeed - minimumSpeed) / (this.getGrownAge() + 1);
		if (this.getAgeInDays() <= this.getGrownAge()) {
			this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(Math.round(minimumHealth + (healthStep * this.getAgeInDays())));
			this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(Math.round(minimumDamage + (attackStep * this.getAgeInDays())));
			this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(minimumSpeed + (speedStep * this.getAgeInDays()));
		}
	}

	public boolean isMale() {
		return ((Boolean) this.dataManager.get(GENDER)).booleanValue();
	}

	public abstract int getGrownAge();

}
