package com.github.backtolifemod.backtolife.entity;

import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.init.Items;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

import com.github.backtolifemod.backtolife.entity.living.EntityPrehistoric;
import com.github.backtolifemod.backtolife.entity.living.EntityVelociraptor;
import com.github.backtolifemod.backtolife.enums.EnumPrehistoricType;

public class EntityFossil extends EntityCreature {

	public EnumPrehistoricType type;
	public int rotation;
	public int age;

	private static final DataParameter<Integer> PLASTER = EntityDataManager.<Integer> createKey(EntityVelociraptor.class, DataSerializers.VARINT);

	public EntityFossil(World worldIn) {
		super(worldIn);
		if (type == null) {
			type = EnumPrehistoricType.values()[new Random().nextInt(EnumPrehistoricType.values().length)];
		}

		rotation = new Random().nextInt(3);
		age = getAge();
		this.setSize(type.fossilSize * 1.4F * this.getScale(), (type.fossilSize / 5) * this.getScale());
	}

	public void moveEntity(double x, double y, double z) {
	}

	@Nullable
	public AxisAlignedBB getCollisionBoundingBox() {
		return this.getEntityBoundingBox();
	}

	public boolean isAIDisabled() {
		return true;
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		this.dataManager.register(PLASTER, Integer.valueOf(0));
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound compound) {
		super.writeEntityToNBT(compound);
		compound.setInteger("Plaster", this.getPlastered());
		compound.setInteger("Rotation", this.rotation);
		compound.setInteger("PrehistoricAge", this.age);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound compound) {
		super.readEntityFromNBT(compound);
		this.setPlastered(compound.getInteger("Plaster"));
		this.rotation = compound.getInteger("Rotation");
		this.age = compound.getInteger("PrehistoricAge");
	}

	public void setPlastered(int plaster) {
		this.dataManager.set(PLASTER, Integer.valueOf(plaster));
	}

	public int getPlastered() {
		return this.dataManager.get(PLASTER).intValue();
	}

	public Entity invokeClass() {
		Entity entity = null;
		if (Entity.class.isAssignableFrom(type.entityClass)) {
			try {
				entity = (Entity) type.entityClass.getDeclaredConstructor(World.class).newInstance(this.worldObj);
			} catch (ReflectiveOperationException e) {
				e.printStackTrace();
			}
		}
		return entity;
	}

	public float getScale() {
		Entity entity = invokeClass();
		if (entity instanceof EntityPrehistoric) {
			EntityPrehistoric prehistoric = (EntityPrehistoric) invokeClass();
			if (prehistoric != null) {
				float step = (prehistoric.maximumModelSize - prehistoric.minimumModelSize) / ((prehistoric.getGrownAge()) + 1);
				return prehistoric.minimumModelSize + ((step * age));
			}
		}
		return 0;
	}

	public int getAge() {
		Entity entity = invokeClass();
		if (entity instanceof EntityPrehistoric) {
			EntityPrehistoric prehistoric = (EntityPrehistoric) invokeClass();
			return this.getRNG().nextInt(prehistoric.getGrownAge());
		}
		return 0;
	}

	public boolean canBeCollidedWith() {
		return true;
	}

	public boolean canBePushed() {
		return false;
	}

	public void onUpdate() {
		super.onUpdate();
		if (this.getPlastered() >= 10) {
			this.dropItem(Items.BREAD, 1);
			this.setDead();
		}

	}
}
