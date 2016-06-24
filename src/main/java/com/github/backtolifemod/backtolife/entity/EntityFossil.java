package com.github.backtolifemod.backtolife.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import com.github.backtolifemod.backtolife.enums.EnumPrehistoricType;

public class EntityFossil extends EntityCreature {

	public EnumPrehistoricType type;
	public int rotation;
	public float age;
	public int blockID;
	public int blockMeta;

	private static final DataParameter<Integer> BREAK = EntityDataManager.<Integer> createKey(EntityFossil.class, DataSerializers.VARINT);
	private List<Block> blocks = new ArrayList<Block>();

	public EntityFossil(World worldIn) {
		super(worldIn);
		blocks.add(Blocks.STONE);
		blocks.add(Blocks.BEDROCK);
		blocks.add(Blocks.SAND);
		blocks.add(Blocks.SANDSTONE);
		if (type == null) {
			type = EnumPrehistoricType.values()[new Random().nextInt(EnumPrehistoricType.values().length)];
		}
		rotation = new Random().nextInt(3);
		age = new Random().nextFloat();
		this.setSize(type.fossilSize * 1.4F * this.getScale(), (type.fossilSize / 5) * this.getScale());
	}

	@Override
	public boolean isAIDisabled() {
		return true;
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		if (blockID == 0) {
			int i = MathHelper.floor_double(this.posX);
			int j = MathHelper.floor_double(this.posY) - 1;
			int k = MathHelper.floor_double(this.posZ);
			BlockPos blockpos = new BlockPos(i, j, k);
			int newBlockID = Block.getIdFromBlock(worldObj.getBlockState(blockpos).getBlock());
			if (blocks.contains(Block.getBlockById(newBlockID))) {
				this.blockID = newBlockID;
				this.blockMeta = ((Block) worldObj.getBlockState(blockpos).getBlock()).getMetaFromState(worldObj.getBlockState(blockpos));
			}else{
				this.blockID = Block.getIdFromBlock(Blocks.STONE);
				this.blockMeta = 0;
			}
		}
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		this.dataManager.register(BREAK, Integer.valueOf(0));
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound compound) {
		super.writeEntityToNBT(compound);
		compound.setInteger("Break", this.getBreak());
		compound.setInteger("Rotation", this.rotation);
		compound.setFloat("PrehistoricAge", this.age);
		compound.setFloat("BlockID", this.blockID);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound compound) {
		super.readEntityFromNBT(compound);
		this.setBreak(compound.getInteger("Break"));
		this.rotation = compound.getInteger("Rotation");
		this.age = compound.getFloat("PrehistoricAge");
		this.blockID = compound.getInteger("BlockID");
	}

	public void setBreak(int plaster) {
		this.dataManager.set(BREAK, Integer.valueOf(plaster));
	}

	public int getBreak() {
		return this.dataManager.get(BREAK).intValue();
	}

	public float getScale() {
		float step = (type.maximumModelSize - type.minimumModelSize);
		return type.minimumModelSize + ((step * age));
	}

	@Override
	protected void damageEntity(DamageSource damageSrc, float damageAmount) {
		if (!this.isEntityInvulnerable(damageSrc) && damageSrc.getEntity() != null && damageSrc.getEntity() instanceof EntityPlayer && isItemPickaxe(((EntityPlayer) damageSrc.getEntity()).getHeldItem(this.getActiveHand()))) {
			this.setBreak(this.getBreak() + 1);
			((EntityPlayer) damageSrc.getEntity()).getHeldItem(this.getActiveHand()).damageItem(1, (EntityLivingBase) damageSrc.getEntity());
			if (this.getBreak() >= 10) {
				if (!worldObj.isRemote) {
					this.dropItem(Items.BREAD, 1);
				}
				this.setDead();
				this.playSound(getDeathSound(), 1, 1);
				for (int i = 0; i < 105; i++) {
					double motionX = getRNG().nextGaussian() * 0.07D;
					double motionY = getRNG().nextGaussian() * 0.07D;
					double motionZ = getRNG().nextGaussian() * 0.07D;
					float f = (float) (getRNG().nextFloat() * (this.getEntityBoundingBox().maxX - this.getEntityBoundingBox().minX) + this.getEntityBoundingBox().minX);
					float f1 = (float) (getRNG().nextFloat() * (this.getEntityBoundingBox().maxY - this.getEntityBoundingBox().minY) + this.getEntityBoundingBox().minY);
					float f2 = (float) (getRNG().nextFloat() * (this.getEntityBoundingBox().maxZ - this.getEntityBoundingBox().minZ) + this.getEntityBoundingBox().minZ);
					this.worldObj.spawnParticle(EnumParticleTypes.ITEM_CRACK, f, f1 + (type.fossilSize * 0.4), f2, motionX, motionY, motionZ, new int[] { blockID });
				}
			}
			this.playSound(getDeathSound(), 1, 1);
		}
	}

	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		damageEntity(source, amount);
		return false;
	}

	@Override
	protected SoundEvent getHurtSound() {
		return Block.getBlockById(blockID).getSoundType().getHitSound();
	}

	@Override
	@Nullable
	protected SoundEvent getDeathSound() {
		return Block.getBlockById(blockID).getSoundType().getBreakSound();
	}

	protected boolean isItemPickaxe(ItemStack stack) {
		return stack != null && stack.getItem() != null && stack.getItem() instanceof ItemPickaxe;
	}

	protected boolean canTriggerWalking() {
		return false;
	}

	@Nullable
	public AxisAlignedBB getCollisionBox(Entity entityIn) {
		return entityIn.getEntityBoundingBox();
	}

	@Nullable
	public AxisAlignedBB getCollisionBoundingBox() {
		return this.getEntityBoundingBox();
	}

	public boolean canBePushed() {
		return true;
	}

	public boolean canBeCollidedWith() {
		return !this.isDead;
	}

}
