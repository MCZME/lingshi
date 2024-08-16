package mczme.lingshi.common.recipe;

import mczme.lingshi.client.recipebook.CookingFoodRecipeLabel;
import mczme.lingshi.common.recipe.input.SkilletRecipeInput;
import mczme.lingshi.common.registry.ModRecipes;
import mczme.lingshi.common.registry.ModSerializer;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static net.neoforged.neoforge.common.util.RecipeMatcher.findMatches;

public class SkilletRecipe extends CookingFoodRecipe implements Recipe<SkilletRecipeInput>{

    public static final int MAX_SLOT = 5;

    private final List<Ingredient> items;
    private final FluidStack fluids;
    private final ItemStack container;
    private final ItemStack result;

    final String group;

    public SkilletRecipe(List<Ingredient> items, FluidStack fluids,ItemStack container, ItemStack result, String group, CookingFoodRecipeLabel label) {
        super(label);
        this.items = items;
        this.fluids = fluids;
        this.container = container;
        this.result = result;
        this.group = group;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> list = NonNullList.create();
        list.addAll(items);
        return list;
    }

    @Override
    public boolean matches(SkilletRecipeInput pInput, Level pLevel) {
        List<ItemStack> inputs = new ArrayList<>();
        int i = 0;
        if(!pInput.isEmpty()){
            for (int j = 0; j < pInput.size(); ++j) {
                ItemStack itemstack = pInput.getItem(j);
                if (!itemstack.isEmpty()) {
                    ++i;
                    inputs.add(itemstack);
                }
            }
        }

        return i == this.items.size() && findMatches(inputs, this.items) != null && pInput.getFluid()==FluidStack.EMPTY;
    }

    @Override
    public ItemStack assemble(SkilletRecipeInput pInput, HolderLookup.Provider pRegistries) {
        return this.result.copy();
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return pWidth * pHeight >= 1;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider pRegistries) {
        return this.result;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModSerializer.SKILLET_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.SKILLET_RECIPE.get();
    }

    public ItemStack getResult() {
        return result;
    }

    public FluidStack getFluid() {
        return fluids;
    }

    public List<Ingredient> getItems() {
        return items;
    }

    public ItemStack getContainer() {
        return container;
    }

    @Override
    public @NotNull String getGroup() {
        return this.group;
    }
}
