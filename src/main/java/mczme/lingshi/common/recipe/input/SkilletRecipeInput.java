package mczme.lingshi.common.recipe.input;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;


public class SkilletRecipeInput implements RecipeInput {

    private final ItemStackHandler items;
    private final FluidStack fluids;

    public SkilletRecipeInput(ItemStackHandler items, FluidStack fluid) {
        this.items = items;
        this.fluids = fluid;
    }

    @Override
    public @NotNull ItemStack getItem(int pIndex) {
        return this.items.getStackInSlot(pIndex);
    }

    public FluidStack getFluid() {
        return this.fluids;
    }

    @Override
    public int size() {
        return items.getSlots();
    }

    @Override
    public boolean isEmpty() {
        for (int i = 0; i < 5; i++) {
            if(!items.getStackInSlot(i).isEmpty()){
                return false;
            }
        }
        return this.fluids.isEmpty();
    }
}
