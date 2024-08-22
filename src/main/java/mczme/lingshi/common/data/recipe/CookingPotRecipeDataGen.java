package mczme.lingshi.common.data.recipe;

import mczme.lingshi.client.recipebook.CookingFoodRecipeLabel;
import mczme.lingshi.common.data.builder.CookingPotRecipeBuilder;
import mczme.lingshi.lingshi;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.fluids.FluidStack;

import java.util.List;

public class CookingPotRecipeDataGen {
    public CookingPotRecipeDataGen(RecipeOutput output) {
        buildRecipes(output);
    }

    protected void buildRecipes(RecipeOutput output) {

    }

    private CookingPotRecipeBuilder build(List<Ingredient> items, FluidStack fluid, ItemStack result, CookingFoodRecipeLabel label){
        return new CookingPotRecipeBuilder(items, fluid, result).setLabel(label).group("CookingPot");
    }

    private ResourceLocation create(String s){
        return ResourceLocation.fromNamespaceAndPath(lingshi.MODID,s);
    }

}
