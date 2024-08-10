package mczme.lingshi.common.recipe;

import mczme.lingshi.common.registry.ModRecipes;
import mczme.lingshi.common.registry.ModSerializer;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ChoppingBoardRecipe implements Recipe<SingleRecipeInput> {

    public static final int MAX_SLOT=3;
    private final Ingredient inputItem;
    private final Ingredient tool;
    private final List<ItemStack> result;

    public ChoppingBoardRecipe(Ingredient inputItem, Ingredient tool, List<ItemStack> result) {
        this.inputItem = inputItem;
        this.tool = tool;
        this.result = result;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> list = NonNullList.create();
        list.add(this.inputItem);
        return list;
    }

    @Override
    public boolean matches(SingleRecipeInput pInput, Level pLevel) {
        return this.inputItem.test(pInput.item());
    }

    @Override
    public ItemStack assemble(SingleRecipeInput pInput, HolderLookup.Provider pRegistries) {
        return this.result.getFirst().copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= 1;
    }

    @Override
    public @NotNull ItemStack getResultItem(HolderLookup.Provider registries) {
        return this.result.getFirst();
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModSerializer.CHOPPING_BOARD__SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.CHOPPING_BOARD_RECIPE.get();
    }

    public Ingredient getInputItem() {
        return inputItem;
    }

    public Ingredient getTool() {
        return tool;
    }

    public List<ItemStack> getResult() {
        return result;
    }

    public List<ItemStack> getResults() {
        List<ItemStack> Result = new ArrayList<>();
        if(this.result.isEmpty()){
            return Result;
        }
        result.forEach(itemStack -> Result.add(itemStack.copy()));
        return Result;
    }
}
