package mczme.lingshi.client.menu;

import mczme.lingshi.client.menu.Slot.CookingItemStackHandler;
import mczme.lingshi.client.menu.Slot.ResultSlot;
import mczme.lingshi.common.block.entity.CookingPotBlockEntity;
import mczme.lingshi.common.recipe.CookingPotRecipe;
import mczme.lingshi.common.recipe.input.CookingFoodRecipeInput;
import mczme.lingshi.common.registry.ModMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.RecipeBookMenu;
import net.minecraft.world.inventory.RecipeBookType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.SlotItemHandler;

import static mczme.lingshi.client.recipebook.ModRecipeBookType.COOKING_POT;

public class CookingPotMenu extends RecipeBookMenu<CookingFoodRecipeInput, CookingPotRecipe> {

    public CookingPotBlockEntity blockEntity;
    protected final Level level;
    private final CookingItemStackHandler itemStackHandler;

    public CookingPotMenu(int pContainerId, Inventory pPlayerInventory, FriendlyByteBuf pContext) {
        this(pContainerId, pPlayerInventory,(CookingPotBlockEntity) pPlayerInventory.player.level().getBlockEntity(pContext.readBlockPos()));
    }

    public CookingPotMenu(int pContainerId,Inventory pPlayerInventory, CookingPotBlockEntity blockEntity) {
        super(ModMenuTypes.COOKING_POT_MENU.get(), pContainerId);
        checkContainerSize(pPlayerInventory, 1);
        this.blockEntity = blockEntity;
        this.level = pPlayerInventory.player.level();
        this.itemStackHandler = blockEntity.getItemStacks();
        this.addWorkSlot(itemStackHandler);
        layoutPlayerInventorySlots(pPlayerInventory);
    }

    private void addWorkSlot(ItemStackHandler itemStacks) {
        int[] X = {33, 51, 69,33, 51, 69, 93, 127};
        int[] Y = {29, 29, 29,47, 47, 47, 29, 47};
        for (int i = 0; i < blockEntity.getMAX(); i++) {
            SlotItemHandler slot = new SlotItemHandler(itemStackHandler, i, X[i], Y[i]);
            slot.set(itemStacks.getStackInSlot(i));
            this.addSlot(slot);
        }
        this.addSlot(new ResultSlot(itemStackHandler, 6, X[6], Y[6]));
        this.addSlot(new ResultSlot(itemStackHandler, 7, X[7], Y[7]));
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
    public void fillCraftSlotsStackedContents(StackedContents pItemHelper) {
        for (int i = 0; i < blockEntity.getMAX(); i++) {
            pItemHelper.accountSimpleStack(itemStackHandler.getStackInSlot(i));
        }
    }

    @Override
    public void clearCraftingContent() {
        for (int i = 0; i < blockEntity.getMAX()-2; i++) {
            this.getSlot(i).set(ItemStack.EMPTY);
        }
    }

    @Override
    public boolean recipeMatches(RecipeHolder<CookingPotRecipe> pRecipe) {
        return pRecipe.value().matches(new CookingFoodRecipeInput(itemStackHandler,blockEntity.getFluid()), this.level);
    }

    @Override
    public int getResultSlotIndex() {
        return 7;
    }

    @Override
    public int getGridWidth() {
        return 6;
    }

    @Override
    public int getGridHeight() {
        return 1;
    }

    @Override
    public int getSize() {
        return 8;
    }

    @Override
    public RecipeBookType getRecipeBookType() {
        return COOKING_POT;
    }

    @Override
    public boolean shouldMoveToInventory(int pSlotIndex) {
        return pSlotIndex != this.getResultSlotIndex();
    }

    @Override
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        ItemStack quickMovedStack = ItemStack.EMPTY;
        Slot quickMovedSlot = this.slots.get(pIndex);
        if (quickMovedSlot.hasItem()) {
            ItemStack rawStack = quickMovedSlot.getItem();
            quickMovedStack = rawStack.copy();
            if (pIndex >= 8 && pIndex < 43) {
                if (!this.moveItemStackTo(rawStack, 0, 6, false)) {
                    if (pIndex < 32) {
                        if (!this.moveItemStackTo(rawStack, 34, 43, false)) {
                            return ItemStack.EMPTY;
                        }
                    } else if (!this.moveItemStackTo(rawStack, 8, 34, false)) {
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
}
