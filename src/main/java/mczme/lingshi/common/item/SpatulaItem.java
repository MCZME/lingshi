package mczme.lingshi.common.item;

import net.minecraft.world.item.Item;
import net.neoforged.neoforge.fluids.FluidStack;

public class SpatulaItem extends Item {

    FluidStack fluidStack = FluidStack.EMPTY;

    public SpatulaItem(Properties properties) {
        super(properties);
    }
}
