package mczme.lingshi.common.recipe.input;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;
import net.neoforged.neoforge.fluids.FluidStack;

import java.util.List;

public class SkilletRecipeInput implements RecipeInput {

    private final List<ItemStack> items;
    private final List<FluidStack> fluids;

    public SkilletRecipeInput(List<ItemStack> items, List<FluidStack> fluids) {
        this.items = items;
        this.fluids = fluids;
    }

    @Override
    public ItemStack getItem(int pIndex) {
        return this.items.get(pIndex);
    }

    public FluidStack getFluid(int pIndex) {
        return this.fluids.get(pIndex);
    }

    @Override
    public int size() {
        return items.size()+fluids.size();
    }

    @Override
    public boolean isEmpty() {
        return this.items.isEmpty() && this.fluids.isEmpty();
    }
}
