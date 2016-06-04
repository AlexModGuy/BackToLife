package com.github.backtolifemod.backtolife.client.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnaceOutput;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.github.backtolifemod.backtolife.core.ModItems;

public class ContainerFossilSlicer extends Container {
	private final IInventory fossilsplicer;
	private int field_178154_h;

	public ContainerFossilSlicer(InventoryPlayer playerInv, IInventory fossilsplicer) {
		this.fossilsplicer = fossilsplicer;
		this.addSlotToContainer(new Slot(fossilsplicer, 0, 17, 35));
		this.addSlotToContainer(new SlotFurnaceOutput(playerInv.player, fossilsplicer, 1, 108, 17));
		this.addSlotToContainer(new SlotFurnaceOutput(playerInv.player, fossilsplicer, 2, 126, 17));
		this.addSlotToContainer(new SlotFurnaceOutput(playerInv.player, fossilsplicer, 3, 144, 17));
		this.addSlotToContainer(new SlotFurnaceOutput(playerInv.player, fossilsplicer, 4, 108, 35));
		this.addSlotToContainer(new SlotFurnaceOutput(playerInv.player, fossilsplicer, 5, 126, 35));
		this.addSlotToContainer(new SlotFurnaceOutput(playerInv.player, fossilsplicer, 6, 144, 35));
		this.addSlotToContainer(new SlotFurnaceOutput(playerInv.player, fossilsplicer, 7, 108, 53));
		this.addSlotToContainer(new SlotFurnaceOutput(playerInv.player, fossilsplicer, 8, 126, 53));
		this.addSlotToContainer(new SlotFurnaceOutput(playerInv.player, fossilsplicer, 9, 144, 53));

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

	public void addListener(IContainerListener listener) {
		super.addListener(listener);
		listener.sendAllWindowProperties(this, this.fossilsplicer);
	}

	public void detectAndSendChanges() {
		super.detectAndSendChanges();

		for (int i = 0; i < this.listeners.size(); ++i) {
			IContainerListener icrafting = (IContainerListener) this.listeners.get(i);

			if (this.field_178154_h != this.fossilsplicer.getField(0)) {
				icrafting.sendProgressBarUpdate(this, 0, this.fossilsplicer.getField(0));
			}
		}

		this.field_178154_h = this.fossilsplicer.getField(0);

	}

	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int id, int data) {
		this.fossilsplicer.setField(id, data);
	}

	public boolean canInteractWith(EntityPlayer playerIn) {
		return this.fossilsplicer.isUseableByPlayer(playerIn);
	}

	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
		ItemStack itemstack = null;
		Slot slot = (Slot) this.inventorySlots.get(index);

		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (index == 2) {
				if (!this.mergeItemStack(itemstack1, 3, 39, true)) {
					return null;
				}

				slot.onSlotChange(itemstack1, itemstack);
			} else if (index != 1 && index != 0) {
				boolean isAnyFossil = itemstack1.getItem().getUnlocalizedName().contains("fossil") || itemstack1.getItem().getUnlocalizedName().contains("Fossil") || itemstack1.getItem().getUnlocalizedName().contains("FOSSIL");

				if (itemstack1 != null && isAnyFossil) {
					if (!this.mergeItemStack(itemstack1, 0, 1, false)) {
						return null;
					}
				} else if (index >= 3 && index < 30) {
					if (!this.mergeItemStack(itemstack1, 30, 39, false)) {
						return null;
					}
				} else if (index >= 30 && index < 39 && !this.mergeItemStack(itemstack1, 3, 30, false)) {
					return null;
				}
			} else if (!this.mergeItemStack(itemstack1, 3, 39, false)) {
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
}