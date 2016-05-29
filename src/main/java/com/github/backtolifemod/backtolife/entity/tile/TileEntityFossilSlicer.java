package com.github.backtolifemod.backtolife.entity.tile;

import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.ISidedInventory;
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
import com.github.backtolifemod.backtolife.enums.EnumPrehistoricType;
import com.github.backtolifemod.backtolife.enums.EnumPrehistoricType.EnumPrehistoricFossilType;

public class TileEntityFossilSlicer extends TileEntity implements ITickable, ISidedInventory{

	private ItemStack[] stacks = new ItemStack[10];
	private static final int[] slotsTop = new int[] {0};
	private static final int[] slotsBottom = new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9};
	private int currentGrind;
	public int gearTurnTimer;
	private Random random = new Random();

	@Override
	public int getSizeInventory() {
		return stacks.length;
	}

	@Override
	public ItemStack getStackInSlot(int index){
		return this.stacks[index];
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
		if (this.stacks[index] != null){
			ItemStack itemstack;

			if (this.stacks[index].stackSize <= count){
				itemstack = this.stacks[index];
				this.stacks[index] = null;
				return itemstack;
			}
			else{
				itemstack = this.stacks[index].splitStack(count);

				if (this.stacks[index].stackSize == 0){
					this.stacks[index] = null;
				}

				return itemstack;
			}
		}
		else{
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

		if (stack != null && stack.stackSize > this.getInventoryStackLimit()){
			stack.stackSize = this.getInventoryStackLimit();
		}
		if (index == 0 && !flag){
			this.currentGrind = 0;
			this.markDirty();
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
	public void openInventory(EntityPlayer player) {}

	@Override
	public void closeInventory(EntityPlayer player) {}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		return index == 0;
	}

	@Override
	public int getField(int id) {
		return this.currentGrind;
	}

	@Override
	public void setField(int id, int value){
		this.currentGrind = value;
	}

	@Override
	public int getFieldCount() {
		return 1;
	}

	@Override
	public void clear() {
		for (int i = 0; i < this.stacks.length; ++i){
			this.stacks[i] = null;
		}
	}

	@Override
	public String getName() {
		return "tile.backtolife.fossil_slicer.name";
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
		return side == EnumFacing.UP ? slotsTop : slotsBottom;
	}

	@Override
	public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction) {
		return this.isItemValidForSlot(index, itemStackIn);
	}

	@Override
	public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
		return index != 0;
	}

	@Override
	public void update() {
		boolean flag = this.isGrinding();
		boolean flag1 = false;

		if(this.currentGrind == 1){
			worldObj.playSound((EntityPlayer)null, this.getPos(), SoundEvents.ENTITY_SKELETON_HURT, SoundCategory.BLOCKS, 0.5F, 0.2F);
		}
		if(isGrinding()){
			this.gearTurnTimer++;

			if(gearTurnTimer % 5 == 0){
				worldObj.playSound((EntityPlayer)null, this.getPos(), SoundEvents.BLOCK_SAND_BREAK, SoundCategory.BLOCKS, 0.7F, 0.7F);
			}
		}

		if (!this.worldObj.isRemote)
		{
			if (!this.isGrinding() && (this.stacks[0] == null))
			{
				if (!this.isGrinding())
				{
					this.currentGrind = 0;
				}
			}
			else
			{
				if (!this.isGrinding() && this.canMakeFossil())
				{
					this.currentGrind = 300;
					if (this.isGrinding())
					{
						flag1 = true;
					}
				}

				if (this.isGrinding() && this.canMakeFossil())
				{
					--this.currentGrind;

					if (currentGrind == 0)
					{
						this.makeFossil();
						flag1 = true;
					}
				}
				else
				{
					this.currentGrind = 0;
				}
			}

			if (flag != this.isGrinding())
			{
				flag1 = true;
			}
		}

		if (flag1)
		{
			this.markDirty();
		}		
	}

	private void makeFossil() {
		ItemStack stack = this.stacks[0];
		ItemStack result = null;
		if(stack != null && stack.getItem() != null){
			boolean isCarnivoreFossil = stack.getItem() == ModItems.unknown_fossil_carnivore_dinosaur;
			boolean isHerbivoreFossil = stack.getItem() == ModItems.unknown_fossil_herbivore_dinosaur;
			boolean isPterosaurFossil = stack.getItem() == ModItems.unknown_fossil_pterosaur;
			boolean isAnyFossil = stack.getItem().getUnlocalizedName().contains("fossil") || stack.getItem().getUnlocalizedName().contains("Fossil") || stack.getItem().getUnlocalizedName().contains("FOSSIL");
			boolean isNondescriptFossil = !isCarnivoreFossil && !isHerbivoreFossil && !isPterosaurFossil && isAnyFossil;
			int chanceInt = random.nextInt(99) + 1;
			if(chanceInt <= 50){
				result = new ItemStack(random.nextBoolean() ? ModItems.dust : ModItems.rocks, random.nextInt(1) + 1);
			}else if(chanceInt <= 75){
				result = new ItemStack(Items.DYE, random.nextInt(1) + 1, 15);
			}else if(chanceInt <= 85){
				result = new ItemStack(Items.BONE, random.nextInt(1) + 1);
			}else if(chanceInt <= 95){
				EnumPrehistoricType type = null;
				if(isCarnivoreFossil){
					type = EnumPrehistoricType.getOneOfFossilType(EnumPrehistoricFossilType.CARNIVORE_DINOSAUR);
				}
				else if(isHerbivoreFossil){
					type = EnumPrehistoricType.getOneOfFossilType(EnumPrehistoricFossilType.HERBIVORE_DINOSAUR);
				}
				else if(isPterosaurFossil){
					type = EnumPrehistoricType.getOneOfFossilType(EnumPrehistoricFossilType.PTEROSAUR);
				}
				else{
					type = EnumPrehistoricType.getOneOfFossilType(EnumPrehistoricFossilType.values()[random.nextInt(EnumPrehistoricFossilType.values().length)]);
				}
				result = new ItemStack(ModItems.soft_tissue, 1, type.ordinal());
			}else{
				result = new ItemStack(Items.SKULL, 1, random.nextInt(1));
			}
		}
		if(result != null){
			for(int slots = 1; slots < 10; slots++){
				ItemStack stackInSlot = this.stacks[slots];
				if(stackInSlot != null){
					if(stackInSlot.isItemEqual(result) && stackInSlot.stackSize + result.stackSize < 64){
						stackInSlot.stackSize += result.stackSize;
						break;
					}
				}
				if(stackInSlot == null){
					this.stacks[slots] = result;
					break;
				}
			}
		}
		this.decrStackSize(0, 1);
	}



	public boolean isGrinding(){
		return this.currentGrind > 0;
	}
	
	public void readFromNBT(NBTTagCompound compound){
		super.readFromNBT(compound);
		NBTTagList nbttaglist = compound.getTagList("Items", 10);
		this.stacks = new ItemStack[this.getSizeInventory()];
		for (int i = 0; i < nbttaglist.tagCount(); ++i){
			NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
			int j = nbttagcompound.getByte("Slot");
			if (j >= 0 && j < this.stacks.length){
				this.stacks[j] = ItemStack.loadItemStackFromNBT(nbttagcompound);
			}
		}
		this.currentGrind = compound.getInteger("GrindTime");
	}
	
	public NBTTagCompound writeToNBT(NBTTagCompound compound){
        super.writeToNBT(compound);
        compound.setInteger("GrindTime", this.currentGrind);
        NBTTagList nbttaglist = new NBTTagList();
        for (int i = 0; i < this.stacks.length; ++i){
            if (this.stacks[i] != null){
                NBTTagCompound nbttagcompound = new NBTTagCompound();
                nbttagcompound.setByte("Slot", (byte)i);
                this.stacks[i].writeToNBT(nbttagcompound);
                nbttaglist.appendTag(nbttagcompound);
            }
        }
        compound.setTag("Items", nbttaglist);
        return compound;
    }

	public boolean canMakeFossil(){
		for(int slots = 1; slots < 10; slots++){
			ItemStack stackInSlot = this.stacks[slots];
			if(stackInSlot != null){
				if(stackInSlot.isStackable() && stackInSlot.stackSize < 64){
					break;
				}else{
					return false;
				}
			}
		}
		ItemStack stack = this.stacks[0];
		if(stack != null && stack.getItem() != null){
			return stack.getItem().getUnlocalizedName().contains("fossil") || stack.getItem().getUnlocalizedName().contains("Fossil") || stack.getItem().getUnlocalizedName().contains("FOSSIL");
		}
		return false;
	}
}
