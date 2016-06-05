package com.github.backtolifemod.backtolife.entity.living;

import net.ilexiconn.llibrary.server.animation.Animation;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.World;

import com.github.backtolifemod.backtolife.BackToLife;
import com.github.backtolifemod.backtolife.enums.EnumPrehistoricType;
import com.github.backtolifemod.backtolife.message.MessageSetDay;

public abstract class EntityLandPrehistoric extends EntityPrehistoric {

	public boolean isSitting;
	public int sittingTicks;
	public float sitProgress;
	public boolean isSleeping;
	public int sleepingTicks;
	public float sleepProgress;
	public boolean isDaytime;
	private Animation SPEAK_ANIMATION;
	private static final DataParameter<Boolean> SLEEPING = EntityDataManager.<Boolean> createKey(EntityLandPrehistoric.class, DataSerializers.BOOLEAN);

	public EntityLandPrehistoric(World world, EnumPrehistoricType type, double minimumDamage, double maximumDamage, double minimumHealth, double maximumHealth, double minimumSpeed, double maximumSpeed) {
		super(world, type, minimumDamage, maximumDamage, minimumHealth, maximumHealth, minimumSpeed, maximumSpeed);
	}

	protected void entityInit() {
		super.entityInit();
		this.dataManager.register(SLEEPING, Boolean.valueOf(false));
	}

	public void writeEntityToNBT(NBTTagCompound compound) {
		super.writeEntityToNBT(compound);
		compound.setBoolean("Sleeping", this.isSleeping());
	}

	public void readEntityFromNBT(NBTTagCompound compound) {
		super.readEntityFromNBT(compound);
		this.setSleeping(compound.getBoolean("Sleeping"));
	}

	public void setSleeping(boolean sleeping) {
		this.dataManager.set(SLEEPING, Boolean.valueOf(sleeping));
		if (!worldObj.isRemote) {
			this.isSleeping = sleeping;
		}
	}

	public boolean isSleeping() {
		if (worldObj.isRemote) {
			boolean isSleeping = ((Boolean) this.dataManager.get(SLEEPING)).booleanValue();

			if ((isSleeping != this.isSleeping)) {
				sleepingTicks = 0;
			}

			this.isSleeping = isSleeping;

			return isSleeping;
		}

		return isSleeping;
	}

	@Override
	public boolean isSitting() {
		if (worldObj.isRemote) {
			boolean isSitting = (((Byte) this.dataManager.get(TAMED)).byteValue() & 1) != 0;

			if ((isSitting != this.isSitting)) {
				sittingTicks = 0;
			}

			this.isSitting = isSitting;

			return isSitting;
		}

		return isSitting;
	}

	@Override
	public void setSitting(boolean sitting) {
		super.setSitting(sitting);

		if (!worldObj.isRemote) {
			this.isSitting = sitting;
		}
	}
	
	@Override
	public void onLivingUpdate(){
		super.onLivingUpdate();
		if (!worldObj.isRemote && !this.isSitting() && this.getRNG().nextInt(100) == 1 && !this.isRiding() && (this.getAnimation() == NO_ANIMATION || this.getAnimation() == SPEAK_ANIMATION) && !this.isSleeping()) {
			this.setSitting(true);
			sittingTicks = 0;
		}

		if (!worldObj.isRemote && (this.isSitting() && sittingTicks > 100 && this.getRNG().nextInt(100) == 1 || this.getAttackTarget() != null) && !this.isSleeping()) {
			this.setSitting(false);
			sittingTicks = 0;
		}
		if (!worldObj.isRemote && this.getRNG().nextInt(500) == 1 && this.canSleep() && (this.getAnimation() == NO_ANIMATION || this.getAnimation() == SPEAK_ANIMATION)) {
			this.setSitting(false);
			this.setSleeping(true);
			sleepingTicks = 0;
		}

		if (!worldObj.isRemote && ( !this.canSleep() || (this.isSleeping() && sleepingTicks > 200 && this.getRNG().nextInt(1000) == 1 || this.getAttackTarget() != null))) {
			this.setSitting(false);
			this.setSleeping(false);
			sleepingTicks = 0;
		}
	}
	
	private boolean canSleep() {
		return !this.isDaytime();
	}

	public String getTexture() {
		return super.getTexture() + (this.isSleeping() ? "_sleeping" : "");
	}
	
	public boolean isDaytime(){
		if(worldObj.isRemote){
			return isDaytime;
		}else{
			BackToLife.NETWORK_WRAPPER.sendToAll(new MessageSetDay(this.getEntityId(),this.worldObj.isDaytime()));
			return this.worldObj.isDaytime();
		}
	}
	
	@Override
	public void onUpdate(){
		super.onUpdate();
		boolean sitting = isSitting();
		if (sitting && sitProgress < 20.0F) {
			sitProgress += 0.5F;
			if(sleepProgress != 0)sleepProgress = 0F;
		} else if (!sitting && sitProgress > 0.0F) {
			sitProgress -= 0.5F;
			if(sleepProgress != 0)sleepProgress = 0F;
		}
		boolean sleeping = isSleeping();
		if (sleeping && sleepProgress < 20.0F) {
			sleepProgress += 0.5F;
			if(sitProgress != 0)sitProgress = 0F;
		} else if (!sleeping && sleepProgress > 0.0F) {
			sleepProgress -= 0.5F;
			if(sitProgress != 0)sitProgress = 0F;
		}
		if(sleeping)sleepingTicks++;
		if(sitting)sittingTicks++;
	}
}