package mczme.lingshi.common.block.entity;

import mczme.lingshi.common.registry.BlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.fluids.FluidStack;

public class SkilletBlockEntity extends BlockEntity {

    private NonNullList<ItemStack> itemStacks = NonNullList.withSize(5, ItemStack.EMPTY);
    private NonNullList<FluidStack> fluidStacks = NonNullList.withSize(5, FluidStack.EMPTY);
    private final int MAX_SLOT = 5;

    public SkilletBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(BlockEntityTypes.SKILLET_BLOCKENTITY.get(), pPos, pBlockState);
    }

    public boolean isFull(){
        return itemStacks.size()+fluidStacks.size() >= MAX_SLOT;
    }

    public ItemStack getItem(int slot){
        return itemStacks.get(slot);
    }

    public FluidStack getFluid(int slot){
        return fluidStacks.get(slot);
    }

    public NonNullList<ItemStack> getItemStacks(){
        return itemStacks;
    }

    public NonNullList<FluidStack> getFluidStacks(){
        return fluidStacks;
    }

    public void setItem(ItemStack item){
        itemStacks.add(item);
    }

    public void setFluid(FluidStack fluid){
        fluidStacks.add(fluid);
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider pRegistries) {
        return this.saveCustomOnly(pRegistries);
    }

    @Override
    protected void loadAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
        try{
            ContainerHelper.loadAllItems(pTag, itemStacks, pRegistries);
            loadFluid(pTag,fluidStacks,pRegistries);
        }catch (Exception e){
            itemStacks=NonNullList.withSize(5, ItemStack.EMPTY);
            fluidStacks=NonNullList.withSize(5, FluidStack.EMPTY);
        }
    }

    @Override
    protected void saveAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
        super.saveAdditional(pTag, pRegistries);
        if (!this.itemStacks.isEmpty()) {
            ContainerHelper.saveAllItems(pTag, this.itemStacks, pRegistries);
        }
        if (!this.fluidStacks.isEmpty()) {
            saveFluid(pTag, this.fluidStacks, true, pRegistries);
        }
    }

    private void loadFluid(CompoundTag pTag, NonNullList<FluidStack> pItems, HolderLookup.Provider pLevelRegistry){
        ListTag listtag = (ListTag)pTag.get("Fluids");

        for (int i = 0; i < listtag.size(); i++) {
            CompoundTag compoundtag = listtag.getCompound(i);
            int j = compoundtag.getByte("Slot") & 255;
            if (j >= 0 && j < pItems.size()) {
                pItems.set(j, FluidStack.parse(pLevelRegistry, compoundtag).orElse(FluidStack.EMPTY));
            }
        }
    }

    private CompoundTag saveFluid(CompoundTag pTag, NonNullList<FluidStack> pItems, boolean pAlwaysPutTag, HolderLookup.Provider pLevelRegistry){
        ListTag listtag = new ListTag();

        for (int i = 0; i < pItems.size(); i++) {
            FluidStack fluidstack = pItems.get(i);
            if (!fluidstack.isEmpty()) {
                CompoundTag compoundtag = new CompoundTag();
                compoundtag.putByte("Slot", (byte)i);
                listtag.add(fluidstack.save(pLevelRegistry, compoundtag));
            }
        }

        if (!listtag.isEmpty() || pAlwaysPutTag) {
            pTag.put("Fluids", listtag);
        }

        return pTag;
    }
}
