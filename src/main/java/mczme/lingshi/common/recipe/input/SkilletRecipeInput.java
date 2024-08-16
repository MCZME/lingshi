package mczme.lingshi.common.recipe.input;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;
import net.neoforged.neoforge.fluids.FluidStack;

import java.util.List;

public class SkilletRecipeInput implements RecipeInput {

    private final List<ItemStack> items;
    private final FluidStack fluids;
    private final ItemStack container;

    public SkilletRecipeInput(List<ItemStack> items, FluidStack fluid, ItemStack container) {
        this.items = items;
        this.fluids = fluid;
        this.container = container;
    }

    @Override
    public ItemStack getItem(int pIndex) {
        return this.items.get(pIndex);
    }

    public FluidStack getFluid() {
        return this.fluids;
    }

    public ItemStack getContainer() {
        return container;
    }

    @Override
    public int size() {
        int i = fluids.isEmpty()?0:1;
        return items.size()+i;
    }

    @Override
    public boolean isEmpty() {
        return this.items.isEmpty() && this.fluids.isEmpty();
    }
}
