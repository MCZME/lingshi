package mczme.lingshi.common.block.entity;

import mczme.lingshi.common.registry.BlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;

public class GlassJarBlockEntity extends BlockEntity{

    private FluidStack fluidStack = FluidStack.EMPTY;
    private final int MAX_AMOUNT = 1000;

    public GlassJarBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(BlockEntityTypes.GLASS_JAR_BLOCK_ENTITY.get(), pPos, pBlockState);
    }

    public FluidStack getFluidStack() {
        return fluidStack;
    }

    public void setFluidStack(FluidStack fluidStack) {
        this.fluidStack = fluidStack;
    }

    public boolean addFluidStack(FluidStack fluidStack) {
        if (this.fluidStack.isEmpty()) {
            this.fluidStack = fluidStack;
            return true;
        } else if (this.fluidStack.is(fluidStack.getFluid())) {
            if (this.fluidStack.getAmount() + fluidStack.getAmount() <= MAX_AMOUNT) {
                this.fluidStack.setAmount(this.fluidStack.getAmount() + fluidStack.getAmount());
                return true;
            }
        }
        return false;
    }

    public FluidStack removeFluidStack(int amount) {
        if (this.fluidStack.getAmount() > amount) {
            this.fluidStack.setAmount(this.fluidStack.getAmount() - amount);
            return new FluidStack(this.fluidStack.getFluid(), amount);
        } else if (this.fluidStack.getAmount() == amount) {
            FluidStack temp = this.fluidStack.copy();
            this.fluidStack = FluidStack.EMPTY;
            return temp;
        }
        return FluidStack.EMPTY;
    }

    public int getMaxAmount() {
        return MAX_AMOUNT;
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public @NotNull CompoundTag getUpdateTag(HolderLookup.@NotNull Provider pRegistries){
        CompoundTag tag = new CompoundTag();
        if(!fluidStack.isEmpty()){
            tag.put("fluidStack", fluidStack.save(pRegistries));
        }
        return tag;
    }

    @Override
    protected void loadAdditional(CompoundTag pTag, HolderLookup.@NotNull Provider pRegistries) {
        if(pTag.get("fluidStack") != null){
            fluidStack = FluidStack.parse(pRegistries, pTag.getCompound("fluidStack")).orElse(FluidStack.EMPTY);
        }else {
            fluidStack = FluidStack.EMPTY;
        }
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag pTag, HolderLookup.@NotNull Provider pRegistries) {
        if(!fluidStack.isEmpty()){
            pTag.put("fluidStack", fluidStack.save(pRegistries));
        }
    }

}
