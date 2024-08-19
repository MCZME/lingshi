package mczme.lingshi.client.menu;

import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

public class CookingItemStackHandler extends ItemStackHandler {

    public CookingItemStackHandler(int size){
        super(size);
    }

    @Override
    protected int getStackLimit(int slot, @NotNull ItemStack stack) {
        return 1;
    }
}
