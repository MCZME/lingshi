package mczme.lingshi.common.block.entity;

import mczme.lingshi.common.registry.BlockEntitys;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.ticks.ContainerSingleItem;

public class ChoppingBoardBlockEntity extends BlockEntity implements ContainerSingleItem {

    private ItemStack item = ItemStack.EMPTY;

    public ChoppingBoardBlockEntity( BlockPos pPos, BlockState pBlockState) {
        super(BlockEntitys.CHOPPING_BOARD_BLOCKENTITY.get(), pPos, pBlockState);
    }



    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider pRegistries) {
        return this.saveCustomOnly(pRegistries);
    }

    @Override
    protected void loadAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
        if (pTag.contains("item", 10)) {
            this.item = ItemStack.parse(pRegistries, pTag.getCompound("item")).orElse(ItemStack.EMPTY);
        } else {
            this.item = ItemStack.EMPTY;
        }
    }

    @Override
    protected void saveAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
        super.saveAdditional(pTag, pRegistries);
        if(!this.item.isEmpty()){
            pTag.put("item", this.item.save(pRegistries));
        }
    }

    @Override
    public ItemStack getTheItem() {
        return this.item;
    }

    @Override
    public void setTheItem(ItemStack pItem) {
        this.item= pItem;
    }


    @Override
    public boolean stillValid(Player pPlayer) {
        return true;
    }
}
