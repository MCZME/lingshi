package mczme.lingshi.common.block.entity;

import mczme.lingshi.client.menu.CookingItemStackHandler;
import mczme.lingshi.client.menu.SkilletMenu;
import mczme.lingshi.common.block.entity.baseblockentity.ICanBeHeated;
import mczme.lingshi.common.datamap.DataMapTypes;
import mczme.lingshi.common.datamap.ingredient.CookingFoodData;
import mczme.lingshi.common.recipe.SkilletRecipe;
import mczme.lingshi.common.recipe.input.SkilletRecipeInput;
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
import net.minecraft.world.Containers;
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
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Optional;

public class SkilletBlockEntity extends BlockEntity implements MenuProvider, ICanBeHeated {

    private final int MAX_SLOT = 5;

    private FluidStack fluidStacks = FluidStack.EMPTY;
    private final CookingItemStackHandler itemStackHandler = new CookingItemStackHandler(MAX_SLOT + 2);

    private final int[] cookingTime = new int[5];


    public SkilletBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(BlockEntityTypes.SKILLET_BLOCKENTITY.get(), pPos, pBlockState);

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

    public FluidStack getFluid() {
        return fluidStacks;
    }

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
        for (int i = 0; i < MAX_SLOT; i++) {
            itemStackHandler.setStackInSlot(i, ItemStack.EMPTY);
        }
    }

    public void consume() {
        for (int i = 0; i < MAX_SLOT; i++) {
            int count = itemStackHandler.getStackInSlot(i).getCount();
            itemStackHandler.getStackInSlot(i).setCount(count - 1);
        }
    }

    public int[] getCookingTime() {
        return cookingTime;
    }

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
        return tag;
    }

    @Override
    protected void loadAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
        itemStackHandler.deserializeNBT(pRegistries, pTag.getCompound("items"));
        if (pTag.get("fluid") != null) {
            fluidStacks = FluidStack.parse(pRegistries, pTag.getCompound("fluid")).orElse(FluidStack.EMPTY);
        }
        if (pTag.get("cookingTime") != null && pTag.getInt("Size") != 0) {
            loadCookingTime(pTag.getList("cookingTime", Tag.TAG_COMPOUND),pTag.getInt("Size"));
        }
    }

    @Override
    protected void saveAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
        super.saveAdditional(pTag, pRegistries);
        pTag.put("items", itemStackHandler.serializeNBT(pRegistries));
        if (!this.fluidStacks.isEmpty()) {
            pTag.put("fluid", fluidStacks.save(pRegistries));
        }
        saveCookingTime(pTag);
    }

    private void saveCookingTime(CompoundTag nbt){
        ListTag nbtTagList = new ListTag();
        for (int i = 0; i < MAX_SLOT; i++) {
                CompoundTag itemTag = new CompoundTag();
                itemTag.putInt("index", cookingTime[i]);
                nbtTagList.add(itemTag);
        }
        nbt.put("cookingTime", nbtTagList);
        nbt.putInt("Size", MAX_SLOT);
    }

    private void loadCookingTime(ListTag pTag,int size){
        for (int i = 0; i < size; i++) {
            cookingTime[i]= pTag.getCompound(i).getInt("index");
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("gui." + lingshi.MODID + ".skillet_menu");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new SkilletMenu(pContainerId, pPlayerInventory, this);
    }

    public static <T extends BlockEntity> void serverTick(Level pLevel, BlockPos pPos, BlockState blockState, T t) {
        SkilletBlockEntity blockEntity = (SkilletBlockEntity) t;
        boolean flag = blockEntity.isEmpty();
        boolean heat_flag = blockEntity.isHeated(pLevel, pPos);
        int MAX_SLOT = blockEntity.getMAX();
        ItemStackHandler itemStackHandler = blockEntity.getItemStacks();

        if (heat_flag) {
            if (!flag) {
                int cookedTime = 0, burntTime = 0;
                for (int i = 0; i < MAX_SLOT; i++) {
                    ItemStack itemStack = itemStackHandler.getStackInSlot(i);
                    if (itemStack.isEmpty()) {
                        continue;
                    }
                    CookingFoodData cookingFoodData = itemStack.getItemHolder().getData(DataMapTypes.COOKING_FOOD_ITEM);
                    if (cookingFoodData != null) {
                        cookedTime = cookingFoodData.cookedTime();
                        burntTime = cookingFoodData.burntTime();
                        blockEntity.cookingTime[i]++;
                    }
                }
                SkilletRecipeInput input = new SkilletRecipeInput(itemStackHandler, blockEntity.getFluid());
                Optional<RecipeHolder<SkilletRecipe>> optional = pLevel.getRecipeManager().getRecipeFor(
                        ModRecipes.SKILLET_RECIPE.get(),
                        input,
                        pLevel
                );
                ItemStack result = optional.map(RecipeHolder::value)
                        .map(e -> e.assemble(input, pLevel.registryAccess()))
                        .orElse(ItemStack.EMPTY);
                if (!result.isEmpty()) {
                    int size = 0;
                    for (int i = 0; i < MAX_SLOT; i++) {
                        if (blockEntity.cookingTime[i] > cookedTime * 20) {
                            size++;
                        }
                    }
                    if (size == blockEntity.size()) {
                        Containers.dropItemStack(pLevel, pPos.getX(), pPos.getY() + 0.2, pPos.getZ(), result);
                        Arrays.fill(blockEntity.cookingTime, 0);
                        blockEntity.consume();
                    }
                } else {
                    Arrays.fill(blockEntity.cookingTime, 0);
                }
                pLevel.sendBlockUpdated(pPos, blockState, blockState, Block.UPDATE_CLIENTS);
            }
        }
        blockEntity.setChanged();
    }
}
