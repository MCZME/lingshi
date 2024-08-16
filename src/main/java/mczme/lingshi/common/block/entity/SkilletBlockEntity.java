package mczme.lingshi.common.block.entity;

import mczme.lingshi.client.menu.SkilletMenu;
import mczme.lingshi.common.registry.BlockEntityTypes;
import mczme.lingshi.lingshi;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SkilletBlockEntity extends BlockEntity implements MenuProvider {

    private List<ItemStack> itemStacks = new ArrayList<>();
    private FluidStack fluidStacks = FluidStack.EMPTY;

    private final int MAX_SLOT = 5;

    private final ItemStackHandler inventory = new ItemStackHandler(MAX_SLOT + 2);

    public SkilletBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(BlockEntityTypes.SKILLET_BLOCKENTITY.get(), pPos, pBlockState);
    }

    public boolean isFull() {
        return itemStacks.size() >= MAX_SLOT;
    }

    public ItemStack getItem(int slot) {
        return itemStacks.get(slot);
    }

    public ItemStack dropItem() {
        return itemStacks.removeLast();
    }

    public FluidStack getFluid() {
        return fluidStacks;
    }

    public List<ItemStack> getItemStacks() {
        return itemStacks;
    }

    public void setItem(ItemStack item) {
        itemStacks.add(item);
    }

    public void setFluid(FluidStack fluid) {
        fluidStacks = fluid;
    }

    public int getMAX() {
        return MAX_SLOT;
    }

    public ItemStackHandler getInventory() {
        return inventory;
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider pRegistries) {
        CompoundTag tag = new CompoundTag();
        ListTag listtag = new ListTag();
        if (!itemStacks.isEmpty()) {
            for (int i = 0; i < itemStacks.size(); i++) {
                ItemStack itemstack = itemStacks.get(i);
                if (!itemstack.isEmpty()) {
                    CompoundTag compoundtag = new CompoundTag();
                    compoundtag.putByte("Slot", (byte) i);
                    listtag.add(itemstack.save(pRegistries, compoundtag));
                }
            }
        }
        tag.put("skillet", listtag);
        if (!fluidStacks.isEmpty()) {
            tag.put("fluid", fluidStacks.save(pRegistries));
        }
        return tag;
    }

    @Override
    protected void loadAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
        ListTag listtag = (ListTag) pTag.get("skillet");
        if (listtag != null) {
            for (int i = 0; i < listtag.size(); i++) {
                CompoundTag compoundtag = listtag.getCompound(i);
                int j = compoundtag.getByte("Slot") & 255;
                itemStacks.add(j, ItemStack.parse(pRegistries, compoundtag).orElse(ItemStack.EMPTY));
            }
        }
        if (pTag.get("fluid") != null) {
            fluidStacks = FluidStack.parse(pRegistries, pTag.getCompound("fluid")).orElse(FluidStack.EMPTY);
        }
    }

    @Override
    protected void saveAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
        super.saveAdditional(pTag, pRegistries);
        ListTag listtag = new ListTag();
        if (!this.itemStacks.isEmpty()) {
            for (int i = 0; i < itemStacks.size(); i++) {
                ItemStack itemstack = itemStacks.get(i);
                if (!itemstack.isEmpty()) {
                    CompoundTag compoundtag = new CompoundTag();
                    compoundtag.putByte("Slot", (byte) i);
                    listtag.add(itemstack.save(pRegistries, compoundtag));
                }
            }
        }
        if (!this.fluidStacks.isEmpty()) {
            pTag.put("fluid", fluidStacks.save(pRegistries));
        }
        pTag.put("skillet", listtag);
    }

    private void loadItem(CompoundTag pTag, HolderLookup.Provider pLevelRegistry) {
        ListTag listtag = (ListTag) pTag.get("item");
        if (listtag != null) {
            for (int i = 0; i < listtag.size(); i++) {
                CompoundTag compoundtag = listtag.getCompound(i);
                int j = compoundtag.getByte("Slot") & 255;
                itemStacks.add(j, ItemStack.parse(pLevelRegistry, compoundtag).orElse(ItemStack.EMPTY));
            }
        } else if (this.level != null && this.level.isClientSide()) {
            for (int i = 0; i < pTag.size(); i++) {
                itemStacks.add(i, ItemStack.parse(pLevelRegistry, pTag.getCompound("Item" + i)).orElse(ItemStack.EMPTY));
            }
        } else {
            itemStacks = new ArrayList<>();
            setChanged();
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

    public static void tick(Level level, BlockPos pos, BlockState state, SkilletBlockEntity blockEntity) {

    }
}
