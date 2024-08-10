package mczme.lingshi.common.recipe.input;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;
import org.jetbrains.annotations.NotNull;

public record ChoppingBoardRecipeInput(ItemStack stack,ItemStack tool) implements RecipeInput {
    @Override
    public @NotNull ItemStack getItem(int slot) {
        if (slot != 0) throw new IllegalArgumentException("No item for index " + slot);
        return this.stack;
    }

    public ItemStack getTool() {
        return this.tool;
    }

    @Override
    public int size() {
        return 1;
    }
}
