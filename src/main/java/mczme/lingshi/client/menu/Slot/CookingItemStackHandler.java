package mczme.lingshi.client.menu.Slot;

import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

public class CookingItemStackHandler extends ItemStackHandler {

    private final int max;

    public CookingItemStackHandler(int size,int max){
        super(size);
        this.max=max;
    }

    @Override
    protected int getStackLimit(int slot, @NotNull ItemStack stack) {
        return max;
    }
}
