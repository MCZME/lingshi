package mczme.lingshi.common.recipe;

import mczme.lingshi.common.recipe.input.ChoppingBoardRecipeInput;
import mczme.lingshi.common.registry.ModRecipes;
import mczme.lingshi.common.registry.ModSerializer;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

public class ChoppingBoardRecipe implements Recipe<ChoppingBoardRecipeInput> {

    private final Ingredient inputItem;
    private final ItemStack result;

    public ChoppingBoardRecipe(Ingredient inputItem, ItemStack result) {
        this.inputItem = inputItem;
        this.result = result;
    }

    @Override
    public boolean matches(ChoppingBoardRecipeInput pInput, Level pLevel) {
        return this.inputItem.test(pInput.stack());
    }

    @Override
    public ItemStack assemble(ChoppingBoardRecipeInput pInput, HolderLookup.Provider pRegistries) {
        return this.result.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= 1;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider registries) {
        return this.result;
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

    public ItemStack getResult() {
        return result;
    }

}
