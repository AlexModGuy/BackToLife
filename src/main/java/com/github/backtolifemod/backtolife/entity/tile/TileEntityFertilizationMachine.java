package com.github.backtolifemod.backtolife.entity.tile;

import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

import com.github.backtolifemod.backtolife.core.ModItems;
import com.github.backtolifemod.backtolife.item.ItemPrehistoricEgg;

public class TileEntityFertilizationMachine extends TileEntity implements ITickable, ISidedInventory {

	private ItemStack[] stacks = new ItemStack[3];
	private static final int[] slotsTop = new int[]{0, 1};
	private static final int[] slotsSides = new int[]{1};
	private static final int[] slotsBottom = new int[]{2};
	private int currentProgress;
	private int currentSyringe;
	private Random random = new Random();

	@Override
	public int getSizeInventory() {
		return stacks.length;
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		return this.stacks[index];
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
		if (this.stacks[index] != null) {
			ItemStack itemstack;

			if (this.stacks[index].stackSize <= count) {
				itemstack = this.stacks[index];
				this.stacks[index] = null;
				return itemstack;
			} else {
				itemstack = this.stacks[index].splitStack(count);

				if (this.stacks[index].stackSize == 0) {
					this.stacks[index] = null;
				}

				return itemstack;
			}
		} else {
			return null;
		}
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		return null;
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		boolean flag = stack != null && stack.isItemEqual(this.stacks[index]) && ItemStack.areItemStackTagsEqual(stack, this.stacks[index]);
		this.stacks[index] = stack;

		if (stack != null && stack.stackSize > this.getInventoryStackLimit()) {
			stack.stackSize = this.getInventoryStackLimit();
		}
		if (index == 0 && !flag) {
			this.currentProgress = 0;
			this.currentSyringe = 0;
			this.markDirty();
		}
		if (index == 1) {
			if (!flag) {
				this.currentSyringe = 0;
				this.markDirty();
			}
		}
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return true;
	}

	@Override
	public void openInventory(EntityPlayer player) {
	}

	@Override
	public void closeInventory(EntityPlayer player) {
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		return index != 2;
	}

	@Override
	public int getField(int id) {
		switch (id) {
			case 1 :
				return this.currentSyringe;
			default :
				return this.currentProgress;
		}
	}

	@Override
	public void setField(int id, int value) {
		switch (id) {
			case 1 :
				this.currentSyringe = value;
				break;
			default :
				this.currentProgress = value;
				break;
		}
	}

	@Override
	public int getFieldCount() {
		return 1;
	}

	@Override
	public void clear() {
		for (int i = 0; i < this.stacks.length; ++i) {
			this.stacks[i] = null;
		}
	}

	@Override
	public String getName() {
		return "tile.backtolife.fertilization_machine.name";
	}

	@Override
	public boolean hasCustomName() {
		return false;
	}

	@Override
	public ITextComponent getDisplayName() {
		return new TextComponentTranslation(this.getName(), new Object[0]);
	}

	@Override
	public int[] getSlotsForFace(EnumFacing side) {
		return side == EnumFacing.UP ? slotsTop : side == EnumFacing.DOWN ? slotsBottom : slotsSides;
	}

	@Override
	public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction) {
		return this.isItemValidForSlot(index, itemStackIn);
	}

	@Override
	public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
		return index == 2;
	}

	@Override
	public void update() {
		boolean flag = this.isProgressing();
		boolean flag1 = false;
		if (this.currentProgress == 1) {
			worldObj.playSound((EntityPlayer) null, this.getPos(), SoundEvents.ENTITY_CHICKEN_EGG, SoundCategory.BLOCKS, 0.5F, 0.7F);
		}
		if (currentProgress % 3 == 0 && this.isProgressing()) {
			worldObj.playSound((EntityPlayer) null, this.getPos(), SoundEvents.ENTITY_ELDER_GUARDIAN_AMBIENT, SoundCategory.BLOCKS, 0.1F, 0.4F);
		}
		if (this.currentSyringe == 99) {
			worldObj.playSound((EntityPlayer) null, this.getPos(), SoundEvents.BLOCK_LAVA_POP, SoundCategory.BLOCKS, 0.5F, 1.7F);
		}

		if (!this.worldObj.isRemote) {
			if (!this.isProgressing() && (this.stacks[0] == null)) {
				this.currentProgress = 0;
			} else {
				if (this.canMakeEgg(false)) {
					if (this.currentSyringe < 100) {
						currentSyringe++;
					}
				}
				if (!this.canMakeEgg(false)) {
					if (this.currentSyringe != 0) {
						currentSyringe = 0;
					}
				}
				if (!this.isProgressing() && this.canMakeEgg(true)) {
					this.currentProgress = 500;
					if (this.isProgressing()) {
						flag1 = true;
					}
				}

				if (this.isProgressing() && this.canMakeEgg(true)) {
					--this.currentProgress;

					if (currentProgress == 0) {
						this.makeEgg();
						this.currentSyringe = 0;
						flag1 = true;
					}
				} else {
					this.currentProgress = 0;
				}
			}

			if (flag != this.isProgressing()) {
				flag1 = true;
			}
		}

		if (flag1) {
			this.markDirty();
		}
	}

	private void makeEgg() {
		ItemStack stack = this.stacks[0];
		ItemStack result = null;
		if (stack != null && stack.getItem() != null && stack.getItem() == ModItems.fossil_cells) {
			result = new ItemStack(ModItems.prehistoric_egg, 1, stack.getMetadata());
		}
		if (result != null) {
			ItemStack stackInSlot = this.stacks[2];
			if (stackInSlot != null) {
				if (stackInSlot.isItemEqual(result) && stackInSlot.stackSize + result.stackSize < 64) {
					stackInSlot.stackSize += result.stackSize;
				}
			}
			if (stackInSlot == null) {
				this.stacks[2] = result;
			}
		}
		this.decrStackSize(0, 1);
		this.decrStackSize(1, 1);
	}

	public boolean isProgressing() {
		return this.currentProgress > 0;
	}

	public boolean isSyringeProgressing() {
		return this.currentSyringe > 0;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		NBTTagList nbttaglist = compound.getTagList("Items", 10);
		this.stacks = new ItemStack[this.getSizeInventory()];
		for (int i = 0; i < nbttaglist.tagCount(); ++i) {
			NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
			int j = nbttagcompound.getByte("Slot");
			if (j >= 0 && j < this.stacks.length) {
				this.stacks[j] = ItemStack.loadItemStackFromNBT(nbttagcompound);
			}
		}
		this.currentProgress = compound.getInteger("ProgressTime");
		this.currentSyringe = compound.getInteger("SyringeTime");

	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setInteger("ProgressTime", this.currentProgress);
		compound.setInteger("SyringeTime", this.currentSyringe);
		NBTTagList nbttaglist = new NBTTagList();
		for (int i = 0; i < this.stacks.length; ++i) {
			if (this.stacks[i] != null) {
				NBTTagCompound nbttagcompound = new NBTTagCompound();
				nbttagcompound.setByte("Slot", (byte) i);
				this.stacks[i].writeToNBT(nbttagcompound);
				nbttaglist.appendTag(nbttagcompound);
			}
		}
		compound.setTag("Items", nbttaglist);
		return compound;
	}

	public boolean canMakeEgg(boolean needsSyringe) {
		if (needsSyringe) {
			if (this.currentSyringe != 100) {
				return false;
			}
		}
		ItemStack stackInResult = this.stacks[2];
		if (stackInResult != null) {
			if (!stackInResult.isStackable() || stackInResult.stackSize >= 64) {
				return false;
			}
		}

		ItemStack stack = this.stacks[0];
		ItemStack stack2 = this.stacks[1];
		if (stack != null && stack.getItem() != null && stack2 != null && stack2.getItem() != null) {
			return stack.getItem() == ModItems.fossil_cells && isItemEgg(stack2.getItem());
		}
		return false;
	}

	private boolean isItemEgg(Item item) {
		return item == Items.EGG || item instanceof ItemPrehistoricEgg;
	}
}
