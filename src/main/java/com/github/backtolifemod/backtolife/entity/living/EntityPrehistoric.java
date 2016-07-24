package com.github.backtolifemod.backtolife.entity.living;

import javax.annotation.Nullable;

import net.ilexiconn.llibrary.server.animation.Animation;
import net.ilexiconn.llibrary.server.animation.AnimationHandler;
import net.ilexiconn.llibrary.server.animation.IAnimatedEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

import com.github.backtolifemod.backtolife.enums.EnumPrehistoricType;

import fossilsarcheology.api.FoodMappings;

public abstract class EntityPrehistoric extends EntityTameable implements IAnimatedEntity {

	public EnumPrehistoricType type;
	private Animation currentAnimation;
	private int animationTicks;
	private static final DataParameter<Integer> HUNGER = EntityDataManager.<Integer> createKey(EntityPrehistoric.class, DataSerializers.VARINT);
	private static final DataParameter<Integer> AGE_TICKS = EntityDataManager.<Integer> createKey(EntityPrehistoric.class, DataSerializers.VARINT);
	private static final DataParameter<Boolean> GENDER = EntityDataManager.<Boolean> createKey(EntityPrehistoric.class, DataSerializers.BOOLEAN);

	public double minimumDamage;
	public double maximumDamage;
	public double minimumHealth;
	public double maximumHealth;
	public double minimumSpeed;
	public double maximumSpeed;
	public static Animation ANIMATION_EAT;

	public EntityPrehistoric(World world, EnumPrehistoricType type, double minimumDamage, double maximumDamage, double minimumHealth, double maximumHealth, double minimumSpeed, double maximumSpeed) {
		super(world);
		this.type = type;
		this.minimumDamage = minimumDamage;
		this.maximumDamage = maximumDamage;
		this.minimumHealth = minimumHealth;
		this.maximumHealth = maximumHealth;
		this.minimumSpeed = minimumSpeed;
		this.maximumSpeed = maximumSpeed;
		updateAttributes();
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		this.dataManager.register(HUNGER, Integer.valueOf(0));
		this.dataManager.register(AGE_TICKS, Integer.valueOf(0));
		this.dataManager.register(GENDER, Boolean.valueOf(false));
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound compound) {
		super.writeEntityToNBT(compound);
		compound.setInteger("Hunger", this.getHunger());
		compound.setInteger("AgeTicks", this.getAgeInTicks());
		compound.setBoolean("Gender", this.isMale());
	}

	@Override
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
				this.worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, this.posX + this.rand.nextFloat() * this.width * 2.0F - this.width, this.posY + this.rand.nextFloat() * this.height, this.posZ + this.rand.nextFloat() * this.width * 2.0F - this.width, d2, d0, d1, new int[0]);
			}
		}
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound compound) {
		super.readEntityFromNBT(compound);
		this.setHunger(compound.getInteger("Hunger"));
		this.setAgeInTicks(compound.getInteger("AgeTicks"));
		this.setGender(compound.getBoolean("Gender"));

	}

	@Override
	public EntityAgeable createChild(EntityAgeable ageable) {
		Entity entity = null;
		if (Entity.class.isAssignableFrom(this.getClass())) {
			try {
				entity = this.getClass().getDeclaredConstructor(World.class).newInstance(worldObj);
			} catch (ReflectiveOperationException e) {
				e.printStackTrace();
			}
		}
		EntityPrehistoric prehistoric = (EntityPrehistoric) entity;
		prehistoric.setGender(this.getRNG().nextBoolean());
		prehistoric.updateAttributes();
		prehistoric.setAgeInDays(0);
		prehistoric.setHunger(50);
		prehistoric.setHealth((float)minimumHealth);
		return prehistoric;
	}

	@Override
	public void onUpdate() {
		this.setScale(getRenderSize());
		super.onUpdate();
		if (this.getAttackTarget() != null && this.getRidingEntity() == null && this.getAttackTarget().isDead) {
			this.setAttackTarget(null);
		}
	}

	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		AnimationHandler.INSTANCE.updateAnimations(this);
		this.setAgeInTicks(this.getAgeInTicks() + 1);
		if (this.getAgeInTicks() % 24000 == 0) {
			this.updateAttributes();
		}
		if (this.getAgeInTicks() % 1200 == 0) {
			if (this.getHunger() > 0) {
				this.setHunger(this.getHunger() - 1);
			}
		}
	}

	@Override
	public void setScaleForAge(boolean par1) {
		this.setScale(this.getRenderSize());
	}

	public float getRenderSize() {
		float step = (this.type.maximumModelSize - this.type.minimumModelSize) / ((this.getGrownAge() * 24000) + 1);

		if (this.getAgeInTicks() > this.getGrownAge() * 24000) {
			return this.type.minimumModelSize + ((step) * this.getGrownAge() * 24000);
		}
		return this.type.minimumModelSize + ((step * this.getAgeInTicks()));
	}

	@Override
	@Nullable
	public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
		livingdata = super.onInitialSpawn(difficulty, livingdata);
		this.setGender(this.getRNG().nextBoolean());
		this.setAgeInDays(this.getGrownAge());
		this.setHunger(50);
		this.updateAttributes();
		return livingdata;
	}

	public String getTexture() {
		return "backtolife:textures/models/entity/" + this.type.toString().toLowerCase() + (this.isAdult() ? "_adult" : this.isTeen() ? "_teen" : "_baby") + (!this.isBaby() ? (this.isMale() ? "_male" : "_female") : "");
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
		return new Animation[] {};
	}

	public int getHunger() {
		return this.dataManager.get(HUNGER).intValue();
	}

	public void setHunger(int hunger) {
		this.dataManager.set(HUNGER, Integer.valueOf(hunger));
	}

	public int getAgeInDays() {
		return this.dataManager.get(AGE_TICKS).intValue() / 24000;
	}

	public void setAgeInDays(int age) {
		this.dataManager.set(AGE_TICKS, Integer.valueOf(age * 24000));
	}

	public int getAgeInTicks() {
		return this.dataManager.get(AGE_TICKS).intValue();
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
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(100.0D);
		getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
		getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(1.0D);
	}

	public void updateAttributes() {
		double healthStep = (maximumHealth - minimumHealth) / (this.getGrownAge());
		double attackStep = (maximumDamage - minimumDamage) / (this.getGrownAge());
		double speedStep = (maximumSpeed - minimumSpeed) / (this.getGrownAge());
		if (this.getAgeInDays() <= this.getGrownAge()) {
			this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(Math.round(minimumHealth + (healthStep * this.getAgeInDays())));
			this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(Math.round(minimumDamage + (attackStep * this.getAgeInDays())));
			this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(minimumSpeed + (speedStep * this.getAgeInDays()));
		}
	}

	@Override
	public boolean attackEntityAsMob(Entity entityIn) {
		boolean flag = entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), ((int) this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue()));

		if (flag) {
			this.applyEnchantments(this, entityIn);
		}

		return flag;
	}

	@Override
	public boolean processInteract(EntityPlayer player, EnumHand hand, @Nullable ItemStack stack) {
		if (stack != null) {
			if (stack.getItem() != null) {
				if (FoodMappings.instance().getItemFoodAmount(stack.getItem(), type.dietType) > 0 && this.getHunger() < 100) {
					this.setHunger(Math.min(100, this.getHunger() + FoodMappings.instance().getItemFoodAmount(stack.getItem(), type.dietType)));
					if (EntityPrehistoric.ANIMATION_EAT != null && this.getAnimation() != EntityPrehistoric.ANIMATION_EAT) {
						this.setAnimation(EntityPrehistoric.ANIMATION_EAT);
					}
					this.setHealth(Math.min(this.getMaxHealth(), (int) (this.getHealth() + FoodMappings.instance().getItemFoodAmount(stack.getItem(), type.dietType) / 10)));
					this.playSound(SoundEvents.ENTITY_GENERIC_EAT, this.getSoundVolume(), this.getSoundPitch());
					spawnItemCrackParticles(stack.getItem());
					this.onEatFood(stack);
					if (!player.isCreative()) {
						stack.stackSize--;
					}
					return true;
				}
			}
		}
		return super.processInteract(player, hand, stack);
	}

	public void onEatFood(ItemStack stack) {

	}

	public void spawnItemCrackParticles(Item item) {
		double motionX = getRNG().nextGaussian() * 0.07D;
		double motionY = getRNG().nextGaussian() * 0.07D;
		double motionZ = getRNG().nextGaussian() * 0.07D;
		float f = (float) (getRNG().nextFloat() * (this.getEntityBoundingBox().maxX - this.getEntityBoundingBox().minX) + this.getEntityBoundingBox().minX);
		float f1 = (float) (getRNG().nextFloat() * (this.getEntityBoundingBox().maxY - this.getEntityBoundingBox().minY) + this.getEntityBoundingBox().minY);
		float f2 = (float) (getRNG().nextFloat() * (this.getEntityBoundingBox().maxZ - this.getEntityBoundingBox().minZ) + this.getEntityBoundingBox().minZ);
		this.worldObj.spawnParticle(EnumParticleTypes.ITEM_CRACK, f, f1, f2, motionX, motionY, motionZ, new int[] { Item.getIdFromItem(item) });
	}

	public void onKillEntity(EntityLivingBase entityLivingIn){
		int hunger = FoodMappings.instance().getEntityFoodAmount(entityLivingIn.getClass(), type.dietType);
		this.setHunger(Math.min(100, this.getHunger() + hunger));
		this.setHealth(Math.min(this.getMaxHealth(), this.getHealth() +  (int) (hunger / 10)));
		if(this.ANIMATION_EAT != null){
			this.setAnimation(EntityPrehistoric.ANIMATION_EAT);
		}
	}

	public boolean isMale() {
		return this.dataManager.get(GENDER).booleanValue();
	}

	public static float scaleFossil(){
		return 1;
	}
	
	public abstract int getGrownAge();

	public boolean isMovementCeased() {
		return false;
	}

	public boolean isAdult(){
		return this.getAgeInTicks() / 24000 >= this.getGrownAge();
	}

	public boolean isBaby(){
		return this.getAgeInTicks() / 24000 <= this.getGrownAge() / 2;
	}

	public boolean isTeen(){
		return !this.isAdult() && !this.isBaby();
	}
}
