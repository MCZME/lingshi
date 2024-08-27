package mczme.lingshi.common.block.entity;

import mczme.lingshi.client.menu.Slot.CookingItemStackHandler;
import mczme.lingshi.client.menu.CookingPotMenu;
import mczme.lingshi.common.block.entity.baseblockentity.ICanBeHeated;
import mczme.lingshi.common.datamap.DataMapTypes;
import mczme.lingshi.common.datamap.ingredient.CookingFoodData;
import mczme.lingshi.common.recipe.CookingPotRecipe;
import mczme.lingshi.common.recipe.input.CookingFoodRecipeInput;
import mczme.lingshi.common.registry.BlockEntityTypes;
import mczme.lingshi.common.registry.ModRecipes;
import mczme.lingshi.lingshi;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Optional;

import static mczme.lingshi.common.block.CookingPotBlock.COVER;

public class CookingPotBlockEntity extends BlockEntity implements ICanBeHeated, MenuProvider {

    private final int MAX_SLOT = 6;

    private FluidStack fluidStacks = FluidStack.EMPTY;
    private final CookingItemStackHandler itemStackHandler = new CookingItemStackHandler(MAX_SLOT + 2,16);

    private final int[] cookingTime = new int[MAX_SLOT + 1];
    public ItemStack result = ItemStack.EMPTY;
    public int stewingTime = 0;

    public CookingPotBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(BlockEntityTypes.COOKING_POT_BLOCKENTITY.get(), pPos, pBlockState);
    }

    public boolean isFull() {
        for (int i = 0; i < MAX_SLOT; i++) {
            if (itemStackHandler.getStackInSlot(i).isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public boolean isEmpty() {
        for (int i = 0; i < MAX_SLOT; i++) {
            if (!itemStackHandler.getStackInSlot(i).isEmpty()) {
                return false;
            }
        }
        return fluidStacks.isEmpty();
    }

    public ItemStack dropItem() {
        for (int i = MAX_SLOT - 1; i >= 0; i--) {
            if (!itemStackHandler.getStackInSlot(i).isEmpty()) {
                ItemStack stack = itemStackHandler.getStackInSlot(i).copy();
                itemStackHandler.setStackInSlot(i, ItemStack.EMPTY);
                return stack;
            }
        }
        return ItemStack.EMPTY;
    }

    @Override
    public FluidStack getFluid() {
        return fluidStacks;
    }

    @Override
    public CookingItemStackHandler getItemStacks() {
        return itemStackHandler;
    }

    public void setItem(ItemStack item) {
        for (int i = 0; i < MAX_SLOT; i++) {
            if (itemStackHandler.getStackInSlot(i).isEmpty()) {
                itemStackHandler.setStackInSlot(i, item);
                break;
            }
        }
    }

    public void setItem(ItemStack item, int slot) {
        itemStackHandler.insertItem(slot, item, false);
    }

    public void setFluid(FluidStack fluid) {
        fluidStacks = fluid;
    }

    public int size() {
        int size = 0;
        for (int i = 0; i < MAX_SLOT; i++) {
            if (!itemStackHandler.getStackInSlot(i).isEmpty()) {
                size++;
            }
        }
        return size;
    }

    public void clear() {
        result = ItemStack.EMPTY;
        for (int i = 0; i < MAX_SLOT; i++) {
            itemStackHandler.setStackInSlot(i, ItemStack.EMPTY);
        }
        fluidStacks = FluidStack.EMPTY;
        clearTime();
        stewingTime=0;
    }

    public void clearTime() {
        Arrays.fill(cookingTime, 0);
    }

    @Override
    public int[] getCookingTime() {
        return cookingTime;
    }

    @Override
    public ItemStack getResult(){
        return result;
    }

    public void consume(int count){
        if (count > 0) {
            boolean t = true;
            for (int i = 0; i < MAX_SLOT; i++) {
                if (!itemStackHandler.getStackInSlot(i).isEmpty()) {
                    itemStackHandler.getStackInSlot(i).shrink(count);
                    if(!itemStackHandler.getStackInSlot(i).isEmpty()){
                        t=false;
                    }
                }
            }
            if(t){
                clear();
            }
        }

    }

    @Override
    public int getMAX() {
        return MAX_SLOT;
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider pRegistries) {
        CompoundTag tag = new CompoundTag();
        tag.put("items", itemStackHandler.serializeNBT(pRegistries));
        if (!fluidStacks.isEmpty()) {
            tag.put("fluid", fluidStacks.save(pRegistries));
        }
        saveCookingTime(tag);
        if (!result.isEmpty()) {
            tag.put("result", result.save(pRegistries));
        }
        tag.putInt("count", stewingTime);
        return tag;
    }

    @Override
    protected void loadAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
        itemStackHandler.deserializeNBT(pRegistries, pTag.getCompound("items"));
        if (pTag.get("fluid") != null) {
            fluidStacks = FluidStack.parse(pRegistries, pTag.getCompound("fluid")).orElse(FluidStack.EMPTY);
        } else {
            fluidStacks = FluidStack.EMPTY;
        }
        if (pTag.get("cookingTime") != null && pTag.getInt("Size") != 0) {
            loadCookingTime(pTag.getList("cookingTime", Tag.TAG_COMPOUND), pTag.getInt("Size"));
        }
        if (pTag.get("result") != null) {
            result = ItemStack.parse(pRegistries, pTag.getCompound("result")).orElse(ItemStack.EMPTY);
        } else {
            result = ItemStack.EMPTY;
        }
        stewingTime = pTag.getInt("count");
    }

    @Override
    protected void saveAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
        super.saveAdditional(pTag, pRegistries);
        pTag.put("items", itemStackHandler.serializeNBT(pRegistries));
        if (!this.fluidStacks.isEmpty()) {
            pTag.put("fluid", fluidStacks.save(pRegistries));
        }
        saveCookingTime(pTag);
        if (!result.isEmpty()) {
            pTag.put("result", result.save(pRegistries));
        }
        pTag.putInt("count", stewingTime);
    }

    private void saveCookingTime(CompoundTag nbt) {
        ListTag nbtTagList = new ListTag();
        for (int i = 0; i < MAX_SLOT + 1; i++) {
            CompoundTag itemTag = new CompoundTag();
            itemTag.putInt("index", cookingTime[i]);
            nbtTagList.add(itemTag);
        }
        nbt.put("cookingTime", nbtTagList);
        nbt.putInt("Size", MAX_SLOT + 1);
    }

    private void loadCookingTime(ListTag pTag, int size) {
        for (int i = 0; i < size; i++) {
            cookingTime[i] = pTag.getCompound(i).getInt("index");
        }
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.translatable("gui." + lingshi.MODID + ".cooking_pot_menu");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new CookingPotMenu(pContainerId, pPlayerInventory, this);
    }

    public static <T extends BlockEntity> void serverTick(Level pLevel, BlockPos pPos, BlockState blockState, T t) {
        CookingPotBlockEntity blockEntity = (CookingPotBlockEntity) t;
        boolean flag = blockEntity.isEmpty();
        boolean heat_flag = blockEntity.isHeated(pLevel, pPos);
        int MAX_SLOT = blockEntity.getMAX();
        CookingItemStackHandler itemStackHandler = blockEntity.getItemStacks();
        ItemStackHandler inputStackHandler = new ItemStackHandler(MAX_SLOT);
        for (int i = 0; i < MAX_SLOT; i++) {
            inputStackHandler.setStackInSlot(i, itemStackHandler.getStackInSlot(i));
        }
        if (heat_flag) {
            if (!flag) {
                int cookedTime = 0;
                int cooked_size = 0;
                boolean fluid_heated= blockEntity.getFluid().isEmpty();
//              获取配方
                CookingFoodRecipeInput input = new CookingFoodRecipeInput(inputStackHandler, blockEntity.getFluid());
                Optional<RecipeHolder<CookingPotRecipe>> optional = pLevel.getRecipeManager().getRecipeFor(
                        ModRecipes.COOKING_POT_RECIPE.get(),
                        input,
                        pLevel
                );
                ItemStack result1 = optional.map(RecipeHolder::value)
                        .map(e -> e.assemble(input, pLevel.registryAccess()))
                        .orElse(ItemStack.EMPTY);
                int stewTime = optional.map(RecipeHolder::value)
                        .map(e->e.getContainer().braisingTime())
                        .orElse(0);
                //cookProgress
                if(!blockState.getValue(COVER)&&(itemStackHandler.getStackInSlot(7).isEmpty()||(itemStackHandler.getStackInSlot(7).is(result1.getItem())&&itemStackHandler.getStackInSlot(7).getCount()<64))){
                    for (int i = 0; i < MAX_SLOT + 1; i++) {
                        //fluid
                        if (i == MAX_SLOT) {
                            if (!blockEntity.getFluid().isEmpty()) {
                                CookingFoodData cookingFoodData = blockEntity.getFluid().getFluidHolder().getData(DataMapTypes.COOKING_FOOD_FLUID);
                                if (cookingFoodData != null) {
                                    cookedTime = cookingFoodData.cookedTime();
                                    blockEntity.cookingTime[i]++;
                                }
                            } else {
                                blockEntity.cookingTime[i] = 0;
                            }
                            if (blockEntity.cookingTime[i] > cookedTime * 20) {
                                fluid_heated = true;
                            }
                            continue;
                        }
                        //item
                        ItemStack itemStack = itemStackHandler.getStackInSlot(i);
                        if (itemStack.isEmpty()) {
                            blockEntity.cookingTime[i] = 0;
                            continue;
                        }
                        CookingFoodData cookingFoodData = itemStack.getItemHolder().getData(DataMapTypes.COOKING_FOOD_ITEM);
                        if (cookingFoodData != null) {
                            cookedTime = cookingFoodData.cookedTime();
                            blockEntity.cookingTime[i]++;
                        }
                        if (blockEntity.cookingTime[i] > cookedTime * 20) {
                            cooked_size++;
                        }
                    }
                }else {
                    blockEntity.stewingTime++;
                }
//              结果判断
                blockEntity.result=result1;
                if (cooked_size == blockEntity.size()  && !blockEntity.result.isEmpty() && fluid_heated && itemStackHandler.getStackInSlot(7).getCount()<=64&&blockEntity.stewingTime/20>= stewTime) {
                    itemStackHandler.setStackInSlot(6,optional.map(RecipeHolder::value)
                            .map(e -> e.getContainer().container())
                            .orElse(ItemStack.EMPTY));
                    if(!itemStackHandler.getStackInSlot(7).isEmpty()){
                        itemStackHandler.getStackInSlot(7).grow(blockEntity.getResult().getCount());
                    }else {
                        blockEntity.setItem(blockEntity.result,7);
                    }
                    blockEntity.consume(1);
                }
            } else {
                if(itemStackHandler.getStackInSlot(7).isEmpty()){
                    itemStackHandler.setStackInSlot(6, ItemStack.EMPTY);
                }
                blockEntity.clearTime();
                blockEntity.result = ItemStack.EMPTY;
            }
            pLevel.sendBlockUpdated(pPos, blockState, blockState, Block.UPDATE_CLIENTS);
            blockEntity.setChanged();
        }
    }

}
