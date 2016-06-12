package com.github.backtolifemod.backtolife.client.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnaceOutput;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.github.backtolifemod.backtolife.core.ModItems;
import com.github.backtolifemod.backtolife.item.ItemPrehistoricEgg;

public class ContainerFertilizationMachine extends Container {
	private final IInventory fertilizationmachine;
	private int field_178154_h;
	private int syringetime;

	public ContainerFertilizationMachine(InventoryPlayer playerInv, IInventory tissueanalyzer) {
		this.fertilizationmachine = tissueanalyzer;
		this.addSlotToContainer(new Slot(tissueanalyzer, 0, 38, 17));
		this.addSlotToContainer(new Slot(tissueanalyzer, 1, 38, 53));
		this.addSlotToContainer(new SlotFurnaceOutput(playerInv.player, tissueanalyzer, 2, 132, 35));
		int i;
		for (i = 0; i < 3; ++i) {
			for (int j = 0; j < 9; ++j) {
				this.addSlotToContainer(new Slot(playerInv, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}

		for (i = 0; i < 9; ++i) {
			this.addSlotToContainer(new Slot(playerInv, i, 8 + i * 18, 142));
		}
	}

	@Override
	public void addListener(IContainerListener listener) {
		super.addListener(listener);
		listener.sendAllWindowProperties(this, this.fertilizationmachine);
	}

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();

		for (int i = 0; i < this.listeners.size(); ++i) {
			IContainerListener icrafting = this.listeners.get(i);

			if (this.field_178154_h != this.fertilizationmachine.getField(0)) {
				icrafting.sendProgressBarUpdate(this, 0, this.fertilizationmachine.getField(0));
			}
			if (this.syringetime != this.fertilizationmachine.getField(1)) {
				icrafting.sendProgressBarUpdate(this, 1, this.fertilizationmachine.getField(1));
			}
		}
		this.syringetime = this.fertilizationmachine.getField(1);
		this.field_178154_h = this.fertilizationmachine.getField(0);

	}

	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int id, int data) {
		this.fertilizationmachine.setField(id, data);
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return this.fertilizationmachine.isUseableByPlayer(playerIn);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {

		ItemStack itemstack = null;
		Slot slot = this.inventorySlots.get(index);

		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (index == 2) {
				if (!this.mergeItemStack(itemstack1, 3, 38, true)) {
					return null;
				}

				slot.onSlotChange(itemstack1, itemstack);
			} else if (index != 1 && index != 0) {
				boolean isTissue = itemstack1.getItem() == ModItems.fossil_cells;
				boolean isEgg = itemstack1.getItem() == Items.EGG || itemstack1.getItem() instanceof ItemPrehistoricEgg;

				if (itemstack1 != null && isTissue) {
					if (!this.mergeItemStack(itemstack1, 0, 1, false)) {
						return null;
					}
				} else if (itemstack1 != null && isEgg) {
					if (!this.mergeItemStack(itemstack1, 1, 2, false)) {
						return null;
					}
				} else if (index >= 3 && index < 30) {
					if (!this.mergeItemStack(itemstack1, 30, 38, false)) {
						return null;
					}
				} else if (index >= 30 && index < 39 && !this.mergeItemStack(itemstack1, 3, 30, false)) {
					return null;
				}
			} else if (!this.mergeItemStack(itemstack1, 3, 38, false)) {
				return null;
			}

			if (itemstack1.stackSize == 0) {
				slot.putStack((ItemStack) null);
			} else {
				slot.onSlotChanged();
			}

			if (itemstack1.stackSize == itemstack.stackSize) {
				return null;
			}

			slot.onPickupFromSlot(playerIn, itemstack1);
		}

		return itemstack;
	}

	private boolean isItemEgg(Item item) {
		return item == Items.EGG || item instanceof ItemPrehistoricEgg;
	}

}