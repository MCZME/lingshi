package mczme.lingshi.common.item.baseitem;

import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.fluids.FluidStack;

public interface IFluidStackItem {
    FluidStack getFluidStack(ItemStack pStack);
}
