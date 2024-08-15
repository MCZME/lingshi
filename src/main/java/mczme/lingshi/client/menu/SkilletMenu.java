package mczme.lingshi.client.menu;

import mczme.lingshi.common.block.entity.SkilletBlockEntity;
import mczme.lingshi.common.recipe.SkilletRecipe;
import mczme.lingshi.common.recipe.input.SkilletRecipeInput;
import mczme.lingshi.common.registry.ModMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.SlotItemHandler;

import java.util.List;

import static mczme.lingshi.client.recipebook.ModRecipeBookType.SKILLET;
import static mczme.lingshi.common.utility.ListUtility.copy;

public class SkilletMenu extends RecipeBookMenu<SkilletRecipeInput, SkilletRecipe> {

    SkilletBlockEntity blockEntity;
    public final ItemStackHandler inventory;
    protected final Level level;

    public SkilletMenu(int pContainerId, Inventory pPlayerInventory, FriendlyByteBuf pContext) {
        this(pContainerId, pPlayerInventory, (SkilletBlockEntity) pPlayerInventory.player.level().getBlockEntity(pContext.readBlockPos()));
    }

    public SkilletMenu(int pContainerId, Inventory pPlayerInventory,
                       SkilletBlockEntity blockEntity) {
        super(ModMenuTypes.SKILLET_MENU.get(), pContainerId);
        checkContainerSize(pPlayerInventory, 1);
        this.blockEntity = blockEntity;
        this.inventory = blockEntity.getInventory();
        this.level = pPlayerInventory.player.level();
        this.addWorkSlot(copy(blockEntity.getItemStacks()));
        layoutPlayerInventorySlots(pPlayerInventory);
    }

    private void addWorkSlot(List<ItemStack> itemStacks) {
        if (itemStacks.size() < blockEntity.getMAX()) {
            for (int i = itemStacks.size(); i < blockEntity.getMAX(); i++)
                itemStacks.add(ItemStack.EMPTY);
        }
        int[] X = {42, 60, 33, 51, 69, 98, 127};
        int[] Y = {29, 29, 47, 47, 47, 29, 47};
        for (int i = 0; i < blockEntity.getMAX(); i++) {
            SlotItemHandler slot = new SlotItemHandler(inventory, i, X[i], Y[i]);
            slot.set(itemStacks.get(i));
            this.addSlot(slot);
        }
        this.addSlot(new SlotItemHandler(inventory, 5, X[5], Y[5]));
        this.addSlot(new SlotItemHandler(inventory, 6, X[6], Y[6]));
    }

    private void layoutPlayerInventorySlots(Inventory playerInventory) {
        // Player inventory
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 84 + i * 18));
            }
        }
        // Hotbar
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }

    @Override
    public void removed(Player pPlayer) {
        blockEntity.getItemStacks().clear();
        for (int i = 0; i < blockEntity.getMAX(); i++) {
            if (!this.getSlot(i).getItem().isEmpty()) {
                blockEntity.setItem(this.getSlot(i).getItem());
            }
        }
        blockEntity.setChanged();
        super.removed(pPlayer);
    }

    @Override
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        ItemStack quickMovedStack = ItemStack.EMPTY;
        Slot quickMovedSlot = this.slots.get(pIndex);
        if (quickMovedSlot.hasItem()) {
            ItemStack rawStack = quickMovedSlot.getItem();
            quickMovedStack = rawStack.copy();
            if (pIndex >= 7 && pIndex < 43) {
                if (!this.moveItemStackTo(rawStack, 0, 5, false)) {
                    if (pIndex < 32) {
                        if (!this.moveItemStackTo(rawStack, 34, 43, false)) {
                            return ItemStack.EMPTY;
                        }
                    } else if (!this.moveItemStackTo(rawStack, 7, 34, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            } else if (!this.moveItemStackTo(rawStack, 7, 43, false)) {
                return ItemStack.EMPTY;
            }

            if (rawStack.isEmpty()) {
                quickMovedSlot.set(ItemStack.EMPTY);
            } else {
                quickMovedSlot.setChanged();
            }
            if (rawStack.getCount() == quickMovedStack.getCount()) {
                return ItemStack.EMPTY;
            }
            quickMovedSlot.onTake(pPlayer, rawStack);
        }
        return quickMovedStack;
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return true;
    }

    @Override
    public void fillCraftSlotsStackedContents(StackedContents pItemHelper) {

    }

    @Override
    public void clearCraftingContent() {
        this.getSlot(0).set(ItemStack.EMPTY);
        this.getSlot(2).set(ItemStack.EMPTY);
    }

    @Override
    public boolean recipeMatches(RecipeHolder<SkilletRecipe> pRecipe) {
        return true;
    }

    @Override
    public int getResultSlotIndex() {
        return 6;
    }

    @Override
    public int getGridWidth() {
        return 1;
    }

    @Override
    public int getGridHeight() {
        return 7;
    }

    @Override
    public int getSize() {
        return 7;
    }

    @Override
    public RecipeBookType getRecipeBookType() {
        return SKILLET;
    }

    @Override
    public boolean shouldMoveToInventory(int pSlotIndex) {
        return pSlotIndex != 1;
    }
}
